package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import helper.HelperFunctions;
import io.cucumber.java.en.*;

public class CalculatingPlayerScoreStepDefinitions {
	@Given("the game is initialized for calculating player score")
	public void the_game_is_initialized_for_calculating_player_score() {
		HelperFunctions.initializeGame();
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		game.setCurrentPlayer(game.getPlayer(1));
		KingdominoApplication.getGameplay().setGamestatus("CreatingNextDraft");
		KingdominoApplication.getGameplay().createNextDraft();
		KingdominoApplication.getGameplay().setGamestatus("PlacingOrDiscardingSelectedDomino");
	}

	@Given("the current player has no dominoes in his\\/her kingdom yet")
	public void the_current_player_has_no_dominoes_in_his_her_kingdom_yet() {
		
	}
	
	@Given("the score of the current player is {int}")
	public void the_score_of_the_current_player_is(Integer int1) {
		Player player_0 = KingdominoApplication.getKingdomino().getCurrentGame().getCurrentPlayer();
		player_0.setPropertyScore(int1);
	}
	
	
	@Given("the game has no bonus options selected")
	public void the_game_has_no_bonus_options_selected() {
	    
	}
	
	
	@Given("the current player is placing his\\/her domino with ID {int}")
	public void the_current_player_is_placing_his_her_domino_with_ID(Integer int1) {
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getCurrentPlayer();
		Draft draft = KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft();
		Domino dom = HelperFunctions.getdominoByID(int1);
		player.setDominoSelection(new DominoSelection(player, dom, draft));
		KingdominoApplication.getGameplay().setGamestatus("PlacingOrDiscardingSelectedDomino");
		
	}
	
	@When("the current player places his\\/her domino")
	public void the_current_player_places_his_her_domino() {

	    KingdominoApplication.getGameplay().placeDomino();
	}
	
	
	@Then("the score of the current player shall be {int}")
	public void the_score_of_the_current_player_shall_be(int score) {
		Player currentPlayer = KingdominoApplication.getKingdomino().getCurrentGame().getCurrentPlayer();
		assertEquals(score, currentPlayer.getTotalScore());
	}
}
