package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import helper.HelperFunctions;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.en.*;

/**
 * 
 * @author antonianistor
 *
 */

public class SuffleDominosStepDefinitions {

	public static HashMap<String, Player> color2player;
	public Draft draft;
	
	@Given("the game is initialized for shuffle dominoes")
	public void the_game_is_initialized_for_shuffle_dominoes() {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		HelperFunctions.addDefaultUsersAndPlayers(game);
		
		color2player = new HashMap<String, Player>();
		color2player.put("blue", game.getPlayer(0));
		color2player.put("yellow", game.getPlayer(1));
		color2player.put("pink", game.getPlayer(2));
		color2player.put("green", game.getPlayer(3));
		
		kingdomino.addAllGame(game);
		HelperFunctions.createAllDominoes(game);
		
		for(int i =0; i<47; i++) {
			game.getAllDominos().get(i).setNextDomino(game.getAllDominos().get(i+1));
		}
		game.setTopDominoInPile(game.getAllDominos().get(0));
		
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
		
	}

	@Given("there are {int} players playing")
	public void there_are_players_playing(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		
		KingdominoController.setPlayerNumber(int1);
	}

	@When("the shuffling of dominoes is initiated")
	public void the_shuffling_of_dominoes_is_initiated() {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		
		KingdominoController.shuffleDominos();
		
	}

	@Then("the first draft shall exist")
	public void the_first_draft_shall_exist() {
	    // Write code here that turns the phrase above into concrete actions
		// throw new cucumber.api.PendingException();
		
		KingdominoController.revealFirstDraft();
		
		
	}

	@Then("the first draft should have {int} dominoes on the board face down")
	public void the_first_draft_should_have_dominoes_on_the_board_face_down(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Draft draft = new Draft(DraftStatus.FaceDown, game);
		
		int dominoNum = draft.maximumNumberOfIdSortedDominos();
		int expectedDomNum = int1;
		assertEquals(expectedDomNum, dominoNum);
		
		
	}

	@Then("there should be {int} dominoes left in the draw pile")
	public void there_should_be_dominoes_left_in_the_draw_pile(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Draft draft = new Draft(DraftStatus.FaceDown, game);
		
		int dominoLeft= game.getMaxPileSize() - draft.maximumNumberOfIdSortedDominos();
		int expectedDomLeft = int1;
		
		assertEquals(expectedDomLeft, dominoLeft);
		
	}
	
	List<Domino> pile = new ArrayList<Domino>();
	
	@When("I initiate to arrange the domino in the fixed order {string}")
	public void i_initiate_to_arrange_the_domino_in_the_fixed_order(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		
		Game game= KingdominoApplication.getKingdomino().getCurrentGame();
		
		String[] arrangement = string.split(", ");
		
		int[] idArrangement = new int[arrangement.length+1];
		
		for (int i =0; i<idArrangement.length-1;i++) {
			idArrangement[i]=Integer.valueOf(arrangement[i]);
		}
		
		System.out.println(arrangement.toString());
		
		for (int i =0; i< idArrangement.length-1; i++) {
			pile.add(HelperFunctions.getdominoByID(idArrangement[i]));
		}
		
		Domino first = pile.get(0);
		game.setTopDominoInPile(first);
		first.setNextDomino(pile.get(1));
		first.setStatus(DominoStatus.InPile);
		
		Domino last = pile.get(pile.size()-1);
		last.setPrevDomino(pile.get(pile.size()-2));
		last.setStatus(DominoStatus.InPile);
		
		for(int i =1; i< pile.size()-1;i++) {
			Domino current = pile.get(i);
			current.setNextDomino(pile.get(i+1));
			current.setPrevDomino(pile.get(i-1));
			current.setStatus(DominoStatus.InPile);
		}
		
	}

	@Then("the draw pile should consist of everything in {string} except the first {int} dominoes with their order preserved")
	public void the_draw_pile_should_consist_of_everything_in_except_the_first_dominoes_with_their_order_preserved(String string, Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		String arrangement= "";
		
		//List<Domino> shuffled =KingdominoController.shuffleDominos();
		
		for(int i =0; i<pile.size()-1;i++) {
			arrangement += String.valueOf(pile.get(i).getId()) + ", ";
		}
		
		arrangement += String.valueOf(pile.get(pile.size()-1).getId());
		
		//System.out.println(pile.toString());
		//System.out.println(arrangement);
		
		Draft draft = new Draft(DraftStatus.FaceDown, game);
		int dominoNum = draft.maximumNumberOfIdSortedDominos();
		
		assertEquals(string, arrangement);
		assertEquals(int1.intValue(), dominoNum);
		
	}

	
	
}
