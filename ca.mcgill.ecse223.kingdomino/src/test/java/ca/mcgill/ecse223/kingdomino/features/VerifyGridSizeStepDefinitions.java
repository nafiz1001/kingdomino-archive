package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import helper.HelperFunctions;
import io.cucumber.java.en.*;

public class VerifyGridSizeStepDefinitions {
	String result = null;
	
	@Given("the game is initialized for verify grid size")
	public void the_game_is_initialized_for_verify_grid_size() {
		// Intialize empty game
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		HelperFunctions.addDefaultUsersAndPlayers(game);
		HelperFunctions.createAllDominoes(game);
        game.setNextPlayer(game.getPlayer(0));
        game.setCurrentPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
	}

	@When("validation of the grid size is initiated")
	public void validation_of_the_grid_size_is_initiated() {
	    result = KingdominoController.isKingdomSizeValid() ? "valid" : "invalid";
	}

	@Then("the grid size of the player's kingdom shall be {string}")
	public void the_grid_size_of_the_player_s_kingdom_shall_be(String string) {
	    assertEquals(result, string);
	}
}
