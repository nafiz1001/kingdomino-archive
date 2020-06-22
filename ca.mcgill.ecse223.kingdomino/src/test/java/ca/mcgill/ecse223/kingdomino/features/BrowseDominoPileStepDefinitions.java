package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import helper.HelperFunctions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * These Step defintions are for Feature 4 (Browse domino pile) and this feature is responsible for browing through the dominos
 * It deals with the displaying of browsed dominoes with increasing order identifiers.Also deals with obitaining individual domino
 * properties eg: number of crowns,left terrain and right terrain.And also deals with  browsing dominoes by terrain type
 * and displaying the "filtered by terrain" dominoes with increasing order identifiers.This feature has a lot of connection
 * with the User Interface of this game(while the game is going on i.e while users are playing) 
 * @author  Mohammad Salman Mesam
 *
 */

public class BrowseDominoPileStepDefinitions {
	
	@Given("the program is started and ready for browsing dominoes")
	public void the_program_is_started_and_ready_for_browsing_dominoes() {
		Kingdomino kingdomino = new Kingdomino();
		KingdominoApplication.setKingdomino(kingdomino);
		Game game = new Game(48,kingdomino);
		kingdomino.setCurrentGame(game);
		HelperFunctions.createAllDominoes(game);
	}
//	private ArrayList<Domino> checklist ;
	
private List<Map<String, String>> checklist;
private Map<String, String> myMap;
private List<Map<String, String>> checkTerrainList;

	@When("I initiate the browsing of all dominoes")
	public void i_initiate_the_browsing_of_all_dominoes() {
	  checklist = KingdominoController.startBrowsingDominoes();
	}

	@Then("all the dominoes are listed in increasing order of identifiers")
	public void all_the_dominoes_are_listed_in_increasing_order_of_identifiers() {
	for(int i=0;i<checklist.size()-2;++i) {
		if(Integer.valueOf(checklist.get(i).get("id")) < Integer.valueOf(checklist.get(i+1).get("id"))) {
			continue;
		}else {
			fail();
		}
	}
	}

	@When("I provide a domino ID {int}")
	public void i_provide_a_domino_ID(Integer int1) {
	  myMap = KingdominoController.getDominoInfo(int1);
	}

	@Then("the listed domino has {string} left terrain")
	public void the_listed_domino_has_left_terrain(String string) {
		switch (string) {
		case "wheat":
		string = TerrainType.WheatField.toString();
		break;
		case "lake":
	    string= TerrainType.Lake.toString();
	    break;
		case "forest":
			string = TerrainType.Forest.toString();
			break;
		case "swamp":
			string = TerrainType.Swamp.toString();
			break;
		case "mountain":
			string = TerrainType.Mountain.toString();
			break;
		case "grass":
			string = TerrainType.Grass.toString();
			break;
		}
	    assertEquals(myMap.get("lefttile"),string); 
	}

	@Then("the listed domino has {string} right terrain")
	public void the_listed_domino_has_right_terrain(String string) {
		switch (string) {
		case "wheat":
		string = TerrainType.WheatField.toString();
		break;
		case "lake":
	    string= TerrainType.Lake.toString();
	    break;
		case "forest":
			string = TerrainType.Forest.toString();
			break;
		case "swamp":
			string = TerrainType.Swamp.toString();
			break;
		case "mountain":
			string = TerrainType.Mountain.toString();
			break;
		case "grass":
			string = TerrainType.Grass.toString();
			break;
		}
		assertEquals(myMap.get("righttile"),string); 	    
	}

	@Then("the listed domino has {int} crowns")
	public void the_listed_domino_has_crowns(Integer int1) {
		 
		assertEquals(Integer.valueOf(myMap.get("crowns")),int1);
	}

	@When("I initiate the browsing of all dominoes of {string} terrain type")
	public void i_initiate_the_browsing_of_all_dominoes_of_terrain_type(String string) {
		 checkTerrainList = KingdominoController.browseDominoesByTerrain(string);
	}

	@Then("list of dominoes with IDs {string} should be shown")
	public void list_of_dominoes_with_IDs_should_be_shown(String string) {
		String[] ids = string.split(",");
		
		for(int i=0;i<checkTerrainList.size();++i) {
			if(ids[i].equals(checkTerrainList.get(i).get("id"))) {
				continue;
			}else {
				fail();
			}
		}
	}


	

}
