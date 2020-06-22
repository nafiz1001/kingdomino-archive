package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import helper.HelperFunctions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

/**
 * @author Zhanna Klimanova
 */

public class DiscardingLastDomino {
	@Given("the game is initialized for discarding last domino")
	public void the_game_is_initialized_for_discarding_last_domino() {
		HelperFunctions.initializeGame();
		
		KingdominoApplication.resetGameplay();
		KingdominoApplication.getGameplay().setGamestatus("PlacingOrDiscardingSelectedDomino");
	}

	@Then("the final results after discard shall be computed")
	public void the_final_results_after_discard_shall_be_computed() {
		assertEquals(KingdominoApplication.getGameplay().getGamestatusEndGame().toString(), "Ranking");
	}
	
}
