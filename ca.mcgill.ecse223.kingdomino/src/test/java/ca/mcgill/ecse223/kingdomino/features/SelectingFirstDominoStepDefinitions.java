package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import helper.HelperFunctions;
import io.cucumber.java.en.*;

public class SelectingFirstDominoStepDefinitions {
	
	@Given("the game has been initialized for selecting first domino")
	public void the_game_has_been_initialized_for_selecting_first_domino() {
		KingdominoApplication.setKingdomino(new Kingdomino());
		KingdominoApplication.getKingdomino().addAllGame((new Game(48, KingdominoApplication.getKingdomino())));
		KingdominoApplication.getKingdomino().setCurrentGame(KingdominoApplication.getKingdomino().getAllGame(0));
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		HelperFunctions.addDefaultUsersAndPlayers(game);
		HelperFunctions.createAllDominoes(game);
		
		for(int i =0; i<47; i++) {
			game.getAllDominos().get(i).setNextDomino(game.getAllDominos().get(i+1));
		}
		game.setTopDominoInPile(game.getAllDominos().get(0));
		
		KingdominoApplication.getGameplay().setGamestatus(Gameplay.GamestatusInitializing.SelectingFirstDomino.toString());
	}
	
	@Given("the initial order of players is {string}")
	public void the_initial_order_of_players_is(String string) {
		// sort player in player list inside Game
		String[] colours = string.split(",");
		List<Player> players = new ArrayList<>();
		for (String c : colours) {
			for (Player p : KingdominoApplication.getKingdomino().getCurrentGame().getPlayers()) {
				if (c.equalsIgnoreCase(p.getColor().toString())) {
					players.add(p);
				}
			}
		}
		for (int i = 0; i < players.size(); ++i) {
			KingdominoApplication.getKingdomino().getCurrentGame().addOrMovePlayerAt(players.get(i), i);
		}
	}
	
	@Given("the current draft has the dominoes with ID {string}")
	public void the_current_draft_has_the_dominoes_with_ID(String string) {
		(new SelectingDominoStepDefinitions()).the_next_draft_has_the_dominoes_with_ID(string);
	}
	
	@Given("player's first domino selection of the game is {string}")
	public void player_s_first_domino_selection_of_the_game_is(String string) {
	    (new ChooseNextDominoStepDefinitions()).player_s_domino_selection(string);
	}
	
	@Given("the {string} player is selecting his\\/her first domino with ID {int}")
	public void the_player_is_selecting_his_her_first_domino_with_ID(String string, Integer int1) {
	    (new SelectingDominoStepDefinitions()).the_is_selecting_his_her_domino_with_ID(string, int1);
	}
	
	@Given("the {string} player is selecting his\\/her domino with ID {int}")
	public void the_player_is_selecting_his_her_domino_with_ID(String string, Integer int1) {
		(new SelectingDominoStepDefinitions()).the_is_selecting_his_her_domino_with_ID(string, int1);
	}

	// We use the annotation @And to signal precondition check instead of
	// initialization (which is done in @Given methods)
	@And("the validation of domino selection returns {string}")
	public void the_validation_of_domino_selection_returns(String expectedValidationResultString) {
		boolean expectedValidationResult = true;
		if ("success".equalsIgnoreCase(expectedValidationResultString.trim())) {
			expectedValidationResult = true;
		} else if ("error".equalsIgnoreCase(expectedValidationResultString.trim())) {
			expectedValidationResult = false;
		} else {
			throw new IllegalArgumentException(
					"Unknown validation result string \"" + expectedValidationResultString + "\"");
		}
		boolean actualValidationResult = KingdominoApplication.getGameplay().isSelectionValid();

		// Check the precondition prescribed by the scenario
		assertEquals(expectedValidationResult, actualValidationResult);
	}

}
