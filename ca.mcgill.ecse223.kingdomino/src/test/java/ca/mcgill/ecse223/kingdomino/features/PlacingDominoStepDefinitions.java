package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import helper.HelperFunctions;
import io.cucumber.java.en.*;

/**
 * 
 * @author antonianistor
 *
 */


public class PlacingDominoStepDefinitions {
	@Given("the game has been initialized for placing domino")
	public void the_game_has_been_initialized_for_placing_domino() {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		
		HelperFunctions.initializeGame();
		
		KingdominoApplication.resetGameplay();
		KingdominoApplication.getGameplay().setGamestatus("PlacingOrDiscardingSelectedDomino");
	}
	
	// some methods are missing it's ok
	
	@Given("it is not the last turn of the game")
	public void it_is_not_the_last_turn_of_the_game() {
		// Write code here that turns the phrase above into concrete actions
		//throw new cucumber.api.PendingException();
	}
	
	@Then("this player now shall be making his\\/her domino selection")
	public void this_player_now_shall_be_making_his_her_domino_selection() {
		// Write code here that turns the phrase above into concrete actions
		//throw new cucumber.api.PendingException();
		
		//KingdominoApplication.getGameplay().setGamestatus("SelectingNextDomino");
		
		assertEquals(KingdominoApplication.getGameplay().getGamestatusMidGame().toString(), "SelectingNextDomino");
		
	}
	
}
