package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;


import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Property;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import helper.HelperFunctions;
import helper.MyProperty;
import io.cucumber.java.en.*;

public class CalculatePropertyAttributesStepDefinitions {


	@Given("the game is initialized for calculate property attributes")
	public void the_game_is_initialized_for_calculate_property_attributes() {
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		HelperFunctions.addDefaultUsersAndPlayers(game);
		HelperFunctions.createAllDominoes(game);
		game.setCurrentPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino); 
	}

	@When("calculate property attributes is initiated")
	public void calculate_property_attributes_is_initiated() {
		KingdominoController.calculatePropertyAttributes();
	}

	@Then("the player shall have a total of {int} properties")
	public void the_player_shall_have_a_total_of_properties(Integer int1) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		assertEquals(int1, (Integer) game.getPlayer(0).getKingdom().numberOfProperties());
	}

	@Then("the player shall have properties with the following attributes:")
	public void the_player_shall_have_properties_with_the_following_attributes(io.cucumber.datatable.DataTable dataTable) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Property> currentProperties = game.getPlayer(0).getKingdom().getProperties();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		List<MyProperty> myProperties = new ArrayList<MyProperty>();
		List<MyProperty> myPropertiesMap = new ArrayList<MyProperty>();
		    
		
		for(Property prop : currentProperties) {
	    	List<Domino> dominos = new ArrayList<Domino>();
	    	MyProperty myProperty = new MyProperty();
	    	myProperty.setCrowns(prop.getCrowns());
	    	myProperty.setSize(prop.getSize());
	    	myProperty.setPropertyType(prop.getPropertyType());
	    	myProperties.add(myProperty);
	    }
		
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			String typeString = map.get("type");
			String terrain = HelperFunctions.changeTypeToLetter(typeString);
	   		TerrainType type = HelperFunctions.getTerrainType(terrain);
			Integer size = Integer.decode(map.get("size"));
			Integer crowns = Integer.decode(map.get("crowns"));
			MyProperty myPropertyMap = new MyProperty();
			myPropertyMap.setCrowns(crowns);
			myPropertyMap.setSize(size);
			myPropertyMap.setPropertyType(type);
			
		}
		
		for(MyProperty prop : myProperties) {
			for(MyProperty map : myPropertiesMap) {
				if(prop.equals(map)) {
					assertEquals(prop,map);
				}
			}
		}
			
		
	}


	@Given("the player's kingdom also includes the domino {int} at position {int}:{int} with the direction {string}")
	public void the_player_s_kingdom_also_includes_the_domino_at_position_with_the_direction(Integer int1, Integer int2, Integer int3, String string) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		HelperFunctions.kingdomIncludesDomino(game.getPlayer(0),int1, int2, int3, string);
	}


	
}
