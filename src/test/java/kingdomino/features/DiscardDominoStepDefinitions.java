package kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import kingdomino.KingdominoApplication;
import kingdomino.helper.ControllerHelper;
import kingdomino.model.*;
import kingdomino.model.Domino.DominoStatus;
import kingdomino.model.DominoInKingdom.DirectionKind;
import io.cucumber.java.en.*;

public class DiscardDominoStepDefinitions {

	/*
	 * Note that these step definitions and helper methods just serve as a guide to help
	 * you get started. You may change the code if required.
	 */

	@Given("the game is initialized for discard domino")
	public void the_game_is_initialized_for_discard_domino() {
		// Intialize empty game
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		ControllerHelper.addDefaultUsersAndPlayers(game);
		ControllerHelper.createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
	}

	@Given("the player's kingdom has the following dominoes:")
	public void the_player_s_kingdom_has_the_following_dominoes(io.cucumber.datatable.DataTable dataTable) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer id = Integer.decode(map.get("id"));
			DirectionKind dir = ControllerHelper.getDirection(map.get("dominodir"));
			Integer posx = Integer.decode(map.get("posx"));
			Integer posy = Integer.decode(map.get("posy"));

			// Add the domino to a player's kingdom
			Domino dominoToPlace = ControllerHelper.getdominoByID(id);
			Kingdom kingdom = game.getPlayer(0).getKingdom();
			DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
			domInKingdom.setDirection(dir);
			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
		}
	}

	@Given("domino {int} is in the current draft")
	public void domino_is_in_the_current_draft(Integer domID) {
		// TODO: Write code here that turns the phrase above into concrete actions
	}

	@Given("the current player has selected domino {int}")
	public void the_current_player_has_selected_domino(Integer domID) {
		// TODO: Write code here that turns the phrase above into concrete actions
	}

	@Given("the player preplaces domino {int} at its initial position")
	public void the_player_preplaces_domino_at_its_initial_position(Integer domID) {
		// TODO: Write code here that turns the phrase above into concrete actions
	}

	@When("the player attempts to discard the selected domino")
	public void the_player_attempts_to_discard_the_selected_domino() {
		// TODO: Call your Controller method here.
		throw new io.cucumber.java.PendingException(); // Remove this line once your controller method is implemented
	}

	@Then("domino {int} shall have status {string}")
	public void domino_shall_have_status(Integer domID, String domStatus) {
		DominoStatus actualStatus = ControllerHelper.getdominoByID(domID).getStatus();
		DominoStatus expectedStatus = ControllerHelper.getDominoStatus(domStatus);
		assertEquals(expectedStatus, actualStatus);
	}
}
