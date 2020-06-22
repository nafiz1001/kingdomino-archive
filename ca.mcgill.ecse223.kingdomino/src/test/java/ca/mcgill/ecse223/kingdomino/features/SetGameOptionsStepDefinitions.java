package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.BonusOption;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import io.cucumber.java.en.*;

/**
 * These Step defintions are for Feature 1 (Set Game Options feature ) and thi feature is responsible for bonus options
 * and number of players in the game.This feature has a lot of connection with the User Interface of this game(the menu
 *  part where users decide number of players and whether or not they want to play bonus option and which bonus option 
 *  they want to play.
 * @author  Mohammad Salman Mesam
 *
 */

public class SetGameOptionsStepDefinitions {
	
	@Given("the game is initialized for set game options")
	public void the_game_is_initialized_for_set_game_options() {
		// Intialize empty game
		Kingdomino kingdomino = new Kingdomino();
		KingdominoApplication.setKingdomino(kingdomino);
		Game game = new Game(48, kingdomino);
		kingdomino.setCurrentGame(game);
		
		
	}

	@When("set game options is initiated")
	public void set_game_options_is_initiated() {
		
		//sounds like  UI CODE and so I believe that Given is doing what is required hence leaving this empty

	
	}

	@When("the number of players is set to {int}")
	public void the_number_of_players_is_set_to(Integer int1) {
		KingdominoController.setPlayerCount(int1);
	}

	@When("Harmony {string} selected as bonus option")
	public void harmony_selected_as_bonus_option(String string) {
		
		if (string.equals("is")) {
			KingdominoController.addBonusOption(KingdominoController.HARMONY);
		}
	}

	@When("Middle Kingdom {string} selected as bonus option")
	public void middle_Kingdom_selected_as_bonus_option(String string) {
		
		if (string.equals("is")) {
			KingdominoController.addBonusOption(KingdominoController.MIDDLE_KINGDOM);
		}
	}

	@Then("the number of players shall be {int}")
	public void the_number_of_players_shall_be(Integer int1) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		assertEquals(game.getNumberOfPlayers(), int1.intValue());
	}

	@Then("Harmony {string} an active bonus")
	public void harmony_an_active_bonus(String string) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		boolean containsHarmony = KingdominoController.currentGameHasBonusOption(KingdominoController.HARMONY);
		
		switch (string) {
		case "is":
			assertTrue(containsHarmony);
			break;
		case "is not":
			assertFalse(containsHarmony);
			break;
		}
	}

	@Then("Middle Kingdom {string} an active bonus")
	public void middle_Kingdom_an_active_bonus(String string) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		boolean containsMiddleKingdom = KingdominoController.currentGameHasBonusOption(KingdominoController.MIDDLE_KINGDOM);
		
		switch (string) {
		case "is":
			assertTrue(containsMiddleKingdom);
			break;
		case "is not":
			assertFalse(containsMiddleKingdom);
			break;
		}
	  
	}


	
	
}
