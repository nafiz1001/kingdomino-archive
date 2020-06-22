package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.*;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import helper.HelperFunctions;
import io.cucumber.java.en.*;

public class VerifyNeighborAdjacencyStepDefinitions {
	String result = null;
	
	@Given("the game is initialized for neighbor adjacency")
	public void the_game_is_initialized_for_neighbor_adjacency() {
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

	@When("check current preplaced domino adjacency is initiated")
	public void check_current_preplaced_domino_adjacency_is_initiated() {
		result = KingdominoController.isPreplacedDominoCorrectlyNeighbored() ? "valid" : "invalid";
	}

	@Then("the current-domino\\/existing-domino adjacency is {string}")
	public void the_current_domino_existing_domino_adjacency_is(String string) {
		assertEquals(result, string);
	}
}
