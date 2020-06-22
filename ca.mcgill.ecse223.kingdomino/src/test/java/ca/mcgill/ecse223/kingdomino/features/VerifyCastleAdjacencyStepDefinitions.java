package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;

import helper.HelperFunctions;

import io.cucumber.java.en.*;

public class VerifyCastleAdjacencyStepDefinitions {
	String result = null;
	
	@Given("the game is initialized for castle adjacency")
	public void the_game_is_initialized_for_castle_adjacency() {
		// Intialize empty game
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		HelperFunctions.addDefaultUsersAndPlayers(game);
		HelperFunctions.createAllDominoes(game);
        game.setCurrentPlayer(game.getPlayer(0));
        game.setCurrentPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
	}

	@When("check castle adjacency is initiated")
	public void check_castle_adjacency_is_initiated() {
		result = KingdominoController.isPreplacedDominoAdjacentToCastle() ? "valid" : "invalid";
	}

	@Then("the castle\\/domino adjacency is {string}")
	public void the_castle_domino_adjacency_is(String string) {
		assertEquals(result, string);
	}
}
