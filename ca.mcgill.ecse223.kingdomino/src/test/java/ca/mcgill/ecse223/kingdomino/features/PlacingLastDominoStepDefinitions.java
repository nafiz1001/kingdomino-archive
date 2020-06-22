package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import helper.HelperFunctions;
import io.cucumber.java.en.*;


/**
 * 
 * @author antonianistor
 *
 */


public class PlacingLastDominoStepDefinitions {
	
	public static HashMap<String, Player> color2player;
	Game game;
	
	@Given("the game has been initialized for placing last domino")
	public void the_game_has_been_initialized_for_placing_last_domino() {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		Kingdomino kingdomino = new Kingdomino();
		game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		HelperFunctions.addDefaultUsersAndPlayers(game);
		
		color2player = new HashMap<String, Player>();

		// be careful, addDefaultUsers orders colors in very specific way
		// look up addDefaultUsersAndPlayers to know why
		color2player.put("blue", game.getPlayer(0));
		color2player.put("green", game.getPlayer(1));
		color2player.put("yellow", game.getPlayer(2));
		color2player.put("pink", game.getPlayer(3));

		
		kingdomino.addAllGame(game);
		HelperFunctions.createAllDominoes(game);
		
		for(int i =0; i<47; i++) {
			game.getAllDominos().get(i).setNextDomino(game.getAllDominos().get(i+1));
		}
		game.setTopDominoInPile(game.getAllDominos().get(0));
		
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
		
		KingdominoApplication.resetGameplay();
		KingdominoApplication.getGameplay().setGamestatus("PlacingOrDiscardingSelectedDomino");
	}

	@Given("it is the last turn of the game")
	public void it_is_the_last_turn_of_the_game() {
	    KingdominoApplication.getKingdomino().getCurrentGame().setTopDominoInPile(null);
	}
	
	Player currentPlayer ;
	@Given("the current player is not the last player in the turn")
	public void the_current_player_is_not_the_last_player_in_the_turn() {
		// Write code here that turns the phrase above into concrete actions
		//throw new cucumber.api.PendingException();
		
		KingdominoApplication.getKingdomino().getCurrentGame().setCurrentPlayer(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(2));
		KingdominoApplication.getKingdomino().getCurrentGame().setNextPlayer(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(3));
		currentPlayer = KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(2);
	}
	
	
	
	@Given("the player's kingdom has the following dominoes:")
	public void the_player_s_kingdom_has_the_following_dominoes(io.cucumber.datatable.DataTable dataTable) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer id = Integer.decode(map.get("id"));
			DirectionKind dir = HelperFunctions.getDirection(map.get("dominodir"));
			Integer posx = Integer.decode(map.get("posx"));
			Integer posy = Integer.decode(map.get("posy"));

			// Add the domino to a player's kingdom
			Domino dominoToPlace = HelperFunctions.getdominoByID(id);
			Kingdom kingdom = KingdominoApplication.getKingdomino().getCurrentGame().getCurrentPlayer().getKingdom();
			DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
			domInKingdom.setDirection(dir);
			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
			
			
			
			
		}
	}
	
		Domino dom;
	@Given("the current player is preplacing his\\/her domino with ID {int} at location {int}:{int} with direction {string}")
	public void the_current_player_is_preplacing_his_her_domino_with_ID_at_location_with_direction(Integer int1, Integer int2, Integer int3, String string) {
		// Write code here that turns the phrase above into concrete actions
		//throw new cucumber.api.PendingException();
		dom = HelperFunctions.getdominoByID(int1);
		dom.setStatus(DominoStatus.ErroneouslyPreplaced);
		//current = game.getCurrentPlayer();
		//Kingdom kingdom = player.getKingdom();
		//DominoInKingdom domInKingdom = new DominoInKingdom(int2, int3, kingdom , dom);
		//domInKingdom.setDirection(HelperFunctions.getDirection(string));
		
		Draft draft = new Draft(DraftStatus.FaceUp, KingdominoApplication.getKingdomino().getCurrentGame()); 
		
		DominoSelection sel = new DominoSelection(KingdominoApplication.getKingdomino().getCurrentGame().getCurrentPlayer(), dom, draft);
		
		KingdominoApplication.getGameplay().setDir(string);
		KingdominoApplication.getGameplay().setId(int1);
		KingdominoApplication.getGameplay().setX(int2);
		KingdominoApplication.getGameplay().setY(int3);
		
		KingdominoApplication.getGameplay().isPlacementValid();
	}
	
	@Given("the preplaced domino has the status {string}")
	public void the_preplaced_domino_has_the_status(String string) {
		// Write code here that turns the phrase above into concrete actions
		//throw new cucumber.api.PendingException();
	
		
		//if (KingdominoApplication.getGameplay().isPlacementValid() == true) {
			assertEquals(HelperFunctions.getDominoStatus(string), KingdominoApplication.getKingdomino().getCurrentGame().getCurrentPlayer().getDominoSelection().getDomino().getStatus());	
		//}
		
		
	}
	

	@Then("the next player shall be placing his\\/her domino")
	public void the_next_player_shall_be_placing_his_her_domino() {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		assertFalse(KingdominoApplication.getKingdomino().getCurrentGame().getCurrentPlayer() == currentPlayer);
	}
	
	@Given("the current player is the last player in the turn")
	public void the_current_player_is_the_last_player_in_the_turn() {
		// Write code here that turns the phrase above into concrete actions
		//throw new cucumber.api.PendingException();
		
		KingdominoApplication.getKingdomino().getCurrentGame().setCurrentPlayer(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(3));
		
		
		
	}

	@Then("the game shall be finished")
	public void the_game_shall_be_finished() {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		//KingdominoApplication.getGameplay().setGamestatus("EndGame");
		//assertEquals(game.hasNextDraft(), false);
		//assertEquals(game.hasNextPlayer(), false);
		assertEquals(KingdominoApplication.getGameplay().getGamestatus().toString(), "EndGame");
		
	}
	
	

	@Then("the final results after successful placement shall be computed")
	public void the_final_results_after_successful_placement_shall_be_computed() {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		
		//KingdominoApplication.getGameplay().setGamestatus("Ranking");
		
		assertEquals(KingdominoApplication.getGameplay().getGamestatusEndGame().toString(), "Ranking");
	}
	
	// some methods are missing it's ok
}
