package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import helper.HelperFunctions;

/**
 * These Step defintions are for Feature 3 (Start a new Game ) and this feature has a lot of connection with the UI
 * of the game(the UI at the point of starting a new game) as it deals with players in the game,bonus options selected , the single Castle placement in the 
 * right kingdom and also the reveal of the first draft with dominoes facing up.
 * @author  Mohammad Salman Mesam
 *
 */

public class StartANewGameStepDefinitions {
	
	private int numOfPlayers;
	private List<String> bonusOptions;

	@Given("the program is started and ready for starting a new game")
	public void the_program_is_started_and_ready_for_starting_a_new_game() {
		Kingdomino kingdomino = new Kingdomino();
		KingdominoApplication.setKingdomino(kingdomino);
	}
	
	@Given("there are four selected players")
	public void there_are_four_selected_players() {
	  numOfPlayers = 4;
	}

	@Given("bonus options Harmony and MiddleKingdom are selected")
	public void bonus_options_Harmony_and_MiddleKingdom_are_selected() {
		bonusOptions = new ArrayList<>();
		bonusOptions.add("Harmony");
		bonusOptions.add("MiddleKingdom");
	}
	
	@When("starting a new game is initiated")
	public void starting_a_new_game_is_initiated() {
		KingdominoController.startNewGame(numOfPlayers, bonusOptions);
	}

	@When("reveal first draft is initiated")
	public void reveal_first_draft_is_initiated() {
		KingdominoController.revealFirstDraft();
	}

	@Then("all kingdoms shall be initialized with a single castle")
	public void all_kingdoms_shall_be_initialized_with_a_single_castle() {
		for (Player player : KingdominoApplication.getKingdomino().getCurrentGame().getPlayers()) {
			List<KingdomTerritory> territory = player.getKingdom().getTerritories();
			if (territory.size() == 1) {
				if (territory.get(0) instanceof Castle)
					continue;
			}
			
			fail();
		}
	}

	@Then("all castle are placed at {int}:{int} in their respective kingdoms")
	public void all_castle_are_placed_at_in_their_respective_kingdoms(Integer int1, Integer int2) {
		for (Player player : KingdominoApplication.getKingdomino().getCurrentGame().getPlayers()) {
			if (HelperFunctions.getKingdomAsTiles(player, DominoStatus.PlacedInKingdom)[4][4].getKingdomTerritory() instanceof Castle) {
				continue;
			}
			
			fail();
		}
	}

	@Then("the first draft of dominoes is revealed")
	public void the_first_draft_of_dominoes_is_revealed() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		if (!game.getAllDrafts().get(0).getDraftStatus().equals(DraftStatus.FaceUp)) {
			fail();
		}
	}

	@Then("all the dominoes form the first draft are facing up")
	public void all_the_dominoes_form_the_first_draft_are_facing_up() {		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		for (Domino domino : game.getAllDrafts().get(0).getIdSortedDominos()) {
			if (!domino.getStatus().equals(DominoStatus.InCurrentDraft))
				fail();
		}
	}

	@Then("all the players have no properties")
	public void all_the_players_have_no_properties() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		for (Player player : game.getPlayers()) {
			if (player.getKingdom().getProperties().size() == 0) {
				continue;
			} else {
				fail();
			}
		}
	}

	@Then("all player scores are initialized to zero")
	public void all_player_scores_are_initialized_to_zero() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		for (Player player : game.getPlayers()) {
			if (player.getTotalScore() == 0) {
				continue;
			} else {
				fail();
			}
		}
	}


}
