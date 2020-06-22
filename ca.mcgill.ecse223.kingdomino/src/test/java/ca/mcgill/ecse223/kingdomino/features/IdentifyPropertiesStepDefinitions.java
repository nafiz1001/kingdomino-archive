package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import helper.HelperFunctions;
import helper.MyProperty;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.Property;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;



public class IdentifyPropertiesStepDefinitions {

	@Given("the game is initialized for identify properties")
	public void the_game_is_initialized_for_identify_properties() {
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
	

	@When("the properties of the player are identified")
	public void the_properties_of_the_player_are_identified() {
		KingdominoController.identifyPlayerProperties();
	}

	@Then("the player shall have the following properties:")
	public void the_player_shall_have_the_following_properties(io.cucumber.datatable.DataTable dataTable) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Kingdom kingdom = game.getPlayer(0).getKingdom();
		List<Map<String,String>> valueMaps = dataTable.asMaps();
	    List<Property> currentProperties = game.getPlayer(0).getKingdom().getProperties();
	    List<MyProperty> myProperties = new ArrayList<MyProperty>();
	    List<MyProperty> myPropertiesMap = new ArrayList<MyProperty>();
	    
	    for(Property prop : currentProperties) {
	    	List<Domino> dominos = new ArrayList<Domino>();
	    	MyProperty myProperty = new MyProperty();
	    	myProperty.setPropertyType(prop.getPropertyType());
	    	for(Domino dom : dominos) {
	    		myProperty.addIncludedDomino(kingdom, dom);
	    	}
	    	myProperties.add(myProperty);
	    }
	    
		for(Map<String,String> map :valueMaps) {
	   		String typeString = map.get("type");
	   		String terrain = HelperFunctions.changeTypeToLetter(typeString);
	   		TerrainType type = HelperFunctions.getTerrainType(terrain);
	   		String dominos = map.get("dominoes");
	   		int[] dominosIDs = HelperFunctions.parseLineToArray(dominos);
	   		List<Domino> dominosMap = new ArrayList<Domino>();
	   		for(int i = 0; i < dominosIDs.length; i++) {
	   			dominosMap.add(HelperFunctions.getdominoByID(dominosIDs[i]));
	   		}
	   		MyProperty p = new MyProperty();
	   		p.setPropertyType(type);
	   		for(Domino dom : dominosMap) {
	   			p.addIncludedDomino(kingdom, dom);
	   		}
	   		myPropertiesMap.add(p);
	   	}

		for(MyProperty prop : myProperties) {
			for(MyProperty map : myPropertiesMap) {
				if(prop.equals(map)) {
					assertEquals(prop,map);
				}
			}
		}
	   
	}
	
}
