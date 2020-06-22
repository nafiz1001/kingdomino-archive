package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import helper.HelperFunctions;
import io.cucumber.java.en.*;

public class SelectingDominoStepDefinitions {
	@Given("the game has been initialized for selecting domino")
	public void the_game_has_been_initialized_for_selecting_domino() {
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
		
		KingdominoApplication.getGameplay().setGamestatus(Gameplay.GamestatusMidGame.SelectingNextDomino.toString());
	}
	
	@Given("the order of players is {string}")
	public void the_order_of_players_is(String string) {
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
	
	@Given("the next draft has the dominoes with ID {string}")
	public void the_next_draft_has_the_dominoes_with_ID(String string) {
		Game game = ca.mcgill.ecse223.kingdomino.KingdominoApplication.getKingdomino().getCurrentGame();
		game.setCurrentDraft(game.getNextDraft());
        game.setNextDraft(new Draft(Draft.DraftStatus.FaceDown, game));
        
        String[] idss = string.split(",");
        int[] ids = new int[idss.length];
        for (int i = 0; i < ids.length; ++i) {
        	ids[i] = Integer.valueOf(idss[i]);
        }
        
        for (int id : ids) {
        	game.getNextDraft().addIdSortedDomino(HelperFunctions.getdominoByID(id));
        	HelperFunctions.getdominoByID(id).setStatus(DominoStatus.InNextDraft);
        }
	}
	
	// Already defined in ChooseNextDominoStepDefinitions.java
//	@Given("player's domino selection {string}") // work 
//	public void player_s_domino_selection(String string) {
//	}
	
	@Given("the {string} is selecting his\\/her domino with ID {int}")
	public void the_is_selecting_his_her_domino_with_ID(String string, Integer int1) {
		KingdominoApplication.getGameplay().setId(int1);
		KingdominoApplication.getKingdomino().getCurrentGame().setCurrentPlayer(HelperFunctions.findPlayer(string));
		
		int nextPlayerIndex = KingdominoApplication.getKingdomino().getCurrentGame().getPlayers().indexOf(HelperFunctions.findPlayer(string));
		if (nextPlayerIndex < KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers() - 1)
			nextPlayerIndex += 1;
		KingdominoApplication.getKingdomino().getCurrentGame().setNextPlayer(
				KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(nextPlayerIndex)
		);
	}
	
	// Already defined in SelectingFirstDominoStepDefinitions
//	@And("the validation of domino selection returns {string}")
//	public void the_validation_of_domino_selection_returns(String expectedValidationResultString) {
//	}
	
	@When("the {string} player completes his\\/her domino selection")
	public void the_player_completes_his_her_domino_selection(String string) {
		KingdominoApplication.getGameplay().selectDomino();
	}
	
	@Then("the {string} player shall be {string} his\\/her domino")
	public void the_player_shall_be_his_her_domino(String string, String string2) {
		System.err.println(KingdominoApplication.getGameplay().getGamestatusMidGame());
		switch (string2) {
		case "placing":
			assertTrue(KingdominoApplication.getGameplay().getGamestatusMidGame() == Gameplay.GamestatusMidGame.PlacingOrDiscardingSelectedDomino);
			break;
		case "selecting":
			if (KingdominoApplication.getGameplay().getGamestatus() == Gameplay.Gamestatus.MidGame) {
				assertTrue(KingdominoApplication.getGameplay().getGamestatusMidGame() == Gameplay.GamestatusMidGame.SelectingNextDomino);
			} else if(KingdominoApplication.getGameplay().getGamestatus() == Gameplay.Gamestatus.Initializing) {
				assertTrue(KingdominoApplication.getGameplay().getGamestatusInitializing() == Gameplay.GamestatusInitializing.SelectingFirstDomino);
			}
			break;
		}
		assertEquals(KingdominoApplication.getKingdomino().getCurrentGame().getCurrentPlayer().getColor().toString().toLowerCase(), string);
	}

	// this method got removed thanks to a new update for SelectingDomino feature
//	@Then("the {string} player shall be placing his\\/her domino")
//	public void the_player_shall_be_placing_his_her_domino(String string) {
//		System.err.println(KingdominoApplication.getGameplay().getGamestatusMidGame());
//		if (isExpectedToSucceed) {
//			assertTrue(KingdominoApplication.getGameplay().getGamestatusMidGame() == Gameplay.GamestatusMidGame.PlacingOrDiscardingSelectedDomino);
//		} else {
//			assertTrue(KingdominoApplication.getGameplay().getGamestatusMidGame() == Gameplay.GamestatusMidGame.SelectingNextDomino);
//		}
//		assertEquals(KingdominoApplication.getKingdomino().getCurrentGame().getCurrentPlayer().getColor().toString().toLowerCase(), string);
//	}
	
	@Given("the {string} player is selecting his\\/her first domino of the game with ID {int}")
	public void the_player_is_selecting_his_her_first_domino_of_the_game_with_ID(String string, Integer int1) {
		the_is_selecting_his_her_domino_with_ID(string, int1);
	}
	
	@When("the {string} player completes his\\/her domino selection of the game")
	public void the_player_completes_his_her_domino_selection_of_the_game(String string) {
		the_player_completes_his_her_domino_selection(string);
	}
	
	@Then("a new draft shall be available, face down")
	public void a_new_draft_shall_be_available_face_down() {
	    assertTrue(KingdominoApplication.getGameplay().getGamestatusMidGame() == Gameplay.GamestatusMidGame.CreatingNextDraft);
	    assertTrue(KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getDraftStatus() == DraftStatus.Sorted);
	}
}
