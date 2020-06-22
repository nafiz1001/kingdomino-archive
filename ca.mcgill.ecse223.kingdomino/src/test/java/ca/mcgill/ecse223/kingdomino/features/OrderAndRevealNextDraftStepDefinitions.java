package ca.mcgill.ecse223.kingdomino.features;

import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.User;
import helper.HelperFunctions;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.fail;

public class OrderAndRevealNextDraftStepDefinitions {
	List<Integer> sortedDominoIDs; 
	
	@Given("the game is initialized for order next draft of dominoes")
	public void the_game_is_initialized_for_order_next_draft_of_dominoes() {
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		
		HelperFunctions.addDefaultUsersAndPlayers(game);
		HelperFunctions.createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
		
		game.setNextDraft(new Draft(DraftStatus.FaceDown, game));

	}
	
	@Given("the next draft is {string}")
	public void the_next_draft_is(String string) {
		Draft nextDraft = KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft();
		
		int[] arrayOfInputIDs = KingdominoController.splitDraftString(string); 
		for (int ID: arrayOfInputIDs) {
			nextDraft.addIdSortedDomino(HelperFunctions.getdominoByID(ID));
		}
		 
	}
	
	@Given("the dominoes in next draft are facing down")
	public void the_dominoes_in_next_draft_are_facing_down() {
	   KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().setDraftStatus(DraftStatus.FaceDown); 

	}
	
	@When("the ordering of the dominoes in the next draft is initiated")
	public void the_ordering_of_the_dominoes_in_the_next_draft_is_initiated() {
	    sortedDominoIDs = KingdominoController.initiateOrderingOfNextDraft(); 
	   
	}
	
	@Then("the status of the next draft is sorted")
	public void the_status_of_the_next_draft_is_sorted() {
		if (DraftStatus.Sorted.equals(KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getDraftStatus())) {
			System.out.println("The next draft is sorted");
		} else {
		// have a failing case here 
			fail();
		}
	}
	
	@Then("the order of dominoes in the draft will be {string}")
	public void the_order_of_dominoes_in_the_draft_will_be(String string) {
		
		int[] arrayOfInputIDs = KingdominoController.splitDraftString(string); 
		for (int i = 0; i < sortedDominoIDs.size(); i++) {
			if (arrayOfInputIDs[i] == sortedDominoIDs.get(i)) {
				System.out.println(sortedDominoIDs.get(i) + "=" + arrayOfInputIDs[i]);
			}
			else {
				fail();
			}
		}
	}
	
	// second test
	
	@Given("the game is initialized for reveal next draft of dominoes")
	public void the_game_is_initialized_for_reveal_next_draft_of_dominoes() {
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		
		HelperFunctions.addDefaultUsersAndPlayers(game);
		HelperFunctions.createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
		
		game.setNextDraft(new Draft(DraftStatus.Sorted, game));
	}
	
	@Given("the dominoes in next draft are sorted")
	public void the_dominoes_in_next_draft_are_sorted() {
	    KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().setDraftStatus(DraftStatus.Sorted); 
	}
	
	@When("the revealing of the dominoes in the next draft is initiated")
	public void the_revealing_of_the_dominoes_in_the_next_draft_is_initiated() {
	    KingdominoController.revealNextDraft();
	}
	
	@Then("the status of the next draft is face up")
	public void the_status_of_the_next_draft_is_face_up() {
		if (DraftStatus.FaceUp.equals(KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getDraftStatus())) {
			System.out.println("Draft status is face up");
		} else {
			System.out.println("System failed");
			fail(); 
		}
	
	}
}
