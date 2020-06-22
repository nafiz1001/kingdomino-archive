package ca.mcgill.ecse223.kingdomino.features;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import helper.HelperFunctions;
import io.cucumber.java.en.*;

/**
 * @author Zhanna Klimanova
 */

public class DiscardingDominoStepDefintions {
	@Given("the game is initialized for discarding domino")
	public void the_game_is_initialized_for_discarding_domino() {
		HelperFunctions.initializeGame();
		
		KingdominoApplication.resetGameplay();
		KingdominoApplication.getGameplay().setGamestatus("PlacingOrDiscardingSelectedDomino");

	}
	
//	@Given("it is not the last turn of the game")
//	public void it_is_not_the_last_turn_of_the_game() {
//		// Write code here that turns the phrase above into concrete actions
//		throw new cucumber.api.PendingException();
//	}
	
//	@Given("the current player is not the last player in the turn")
//	public void the_current_player_is_not_the_last_player_in_the_turn() {
//		// Write code here that turns the phrase above into concrete actions
//		throw new cucumber.api.PendingException();
//	}
//	
//	// `the player's kingdom has the following dominoes` is already defined
//	
//	@Given("the current player is preplacing his\\/her domino with ID {int} at location {int}:{int} with direction {string}")
//	public void the_current_player_is_preplacing_his_her_domino_with_ID_at_location_with_direction(Integer int1, Integer int2, Integer int3, String string) {
//		// Write code here that turns the phrase above into concrete actions
//		throw new cucumber.api.PendingException();
//	}
	
	@Given("it is impossible to place the current domino in his\\/her kingdom")
	public void it_is_impossible_to_place_the_current_domino_in_his_her_kingdom() {
		
		
		
	}
	
	@When("the current player discards his\\/her domino")
	public void the_current_player_discards_his_her_domino() {
		KingdominoApplication.getGameplay().discardDomino();

	}
	
	
//	@Then("this player now shall be making his\\/her domino selection")
//	public void this_player_now_shall_be_making_his_her_domino_selection() {
//		// Write code here that turns the phrase above into concrete actions
//		throw new cucumber.api.PendingException();
//	}
	
//	@Given("the current player is the last player in the turn")
//	public void the_current_player_is_the_last_player_in_the_turn() {
//		// Write code here that turns the phrase above into concrete actions
//		throw new cucumber.api.PendingException();
//	}
//	
//	@Then("a new draft shall be available")
//	public void a_new_draft_shall_be_available() {
//		assertEquals(KingdominoApplication.getGameplay().createNextDraft())i;
//	}
//	
//	@Then("the draft shall be revealed")
//	public void the_draft_shall_be_revealed() {
//		KingdominoApplication.getGameplay().revealNextDraft();
//	}
}
