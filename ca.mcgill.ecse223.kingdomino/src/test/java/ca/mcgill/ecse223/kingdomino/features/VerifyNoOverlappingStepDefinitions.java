package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.*;
import helper.HelperFunctions;
import io.cucumber.java.en.*;

public class VerifyNoOverlappingStepDefinitions {
    String result = null;

    @Given("the game is initialized to check domino overlapping")
    public void the_game_is_initialized_to_check_domino_overlapping() {
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

    @When("check current preplaced domino overlapping is initiated")
    public void check_current_preplaced_domino_overlapping_is_initiated() {
        result = KingdominoController.isPreplacedDominoOverlapping() ? "invalid" : "valid";
    }

    @Then("the current-domino\\/existing-domino overlapping is {string}")
    public void the_current_domino_existing_domino_overlapping_is(String string) {
        assertEquals(result, string);
    }
}