package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Property;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoadGameStepDefinitions {

	@Given("the game is initialized for load game")
	public void the_game_is_initialized_for_load_game() {
		KingdominoApplication.getKingdomino().delete();
		// Initialize empty game
		Kingdomino kingdomino = new Kingdomino();
	}

	@When("I initiate loading a saved game from {string}")
	public void i_initiate_loading_a_saved_game_from(String string) throws Exception {
		KingdominoController.setFile(string);
		try {
			KingdominoController.loadGame(KingdominoApplication.getKingdomino());
		} catch (Exception e) {
			
			System.out.println("INVALID SAVE FILE, my boi");
		}
	}

	@When("each tile placement is valid")
	public void each_tile_placement_is_valid() {
		for (Domino domino : KingdominoApplication.getKingdomino().getCurrentGame().getAllDominos()) {
			if (domino.getStatus().equals(DominoStatus.ErroneouslyPreplaced)) {
				throw new RuntimeException("Invalid tile placement!");
			}
		}
	}

	@When("the game result is not yet final")
	public void the_game_result_is_not_yet_final() {
		if (!KingdominoApplication.getKingdomino().getCurrentGame().hasAllDominos()) {
			throw new RuntimeException("game is over!");
		}
	}

	@Then("it shall be player {int}'s turn")
	public void it_shall_be_player_s_turn(Integer int1) {

		if (!KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(int1 - 1).getUser().getName()
				.equals(KingdominoApplication.getKingdomino().getCurrentGame().getCurrentPlayer().getUser().getName())) {
			throw new RuntimeException("Wrong next player!");
		}
	}

	@Then("each of the players should have the corresponding tiles on their grid:")
	public void each_of_the_players_should_have_the_corresponding_tiles_on_their_grid(
			io.cucumber.datatable.DataTable dataTable) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			Integer playerNumber = Integer.decode(map.get("playerNumber"));
			String playerTilesRaw = map.get("playerTiles");
			String[] tokensTiles = playerTilesRaw.split("\\,");
			for (String playerTile : tokensTiles) {
				int dominoId = Integer.parseInt(playerTile.trim());

				for (Property prop : game.getPlayer(playerNumber - 1).getKingdom().getProperties()) {
					boolean dominoContained = false;
					for (Domino domino : prop.getIncludedDominos()) {
						if (domino.getId() == dominoId) {
							dominoContained = true;
							break;
						}
					}
					if (!dominoContained) {
						throw new RuntimeException("Domino not contained in grid!!");
					}
				}

			}
		}

	}

	@Then("each of the players should have claimed the corresponding tiles:")
	public void each_of_the_players_should_have_claimed_the_corresponding_tiles(
			io.cucumber.datatable.DataTable dataTable) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			Integer playerNumber = Integer.decode(map.get("playerNumber"));
			Integer claimedTile = Integer.decode(map.get("claimedTile"));

			if (KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(playerNumber - 1).getDominoSelection()
					.getDomino().getId() != claimedTile) {
				throw new RuntimeException("Claimed domino mismatch!");
			}

		}

	}

	@Then("tiles {string} shall be unclaimed on the board")
	public void tiles_shall_be_unclaimed_on_the_board(String string) {
		String[] tiles = string.split("\\,");
		for (String s : tiles) {
			int unclaimedId = Integer.parseInt(s.trim());
			for (DominoSelection nextDominoes : KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft()
					.getSelections()) {
				if (nextDominoes.getDomino().getId() == unclaimedId) {
					throw new RuntimeException("Domino must be unclaimed!");
				}
			}
		}

	}

	@Then("the game shall become ready to start")
	public void the_game_shall_become_ready_to_start() {
		try {
			KingdominoController.loadGame(KingdominoApplication.getKingdomino());
		} catch (Exception e) {
			throw new RuntimeException("invalid save file, my boi");
		}
	}

	@Then("the game shall notify the user that the loaded game is invalid")
	public void the_game_shall_notify_the_user_that_the_loaded_game_is_invalid() {
		try {
			KingdominoController.loadGame(KingdominoApplication.getKingdomino());

		} catch (Exception e) {
			System.err.println("invalid save file, my boi");
		}
	}

}