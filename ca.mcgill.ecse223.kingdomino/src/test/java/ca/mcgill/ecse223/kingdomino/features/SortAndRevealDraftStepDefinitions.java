package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.fail;

import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import helper.HelperFunctions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SortAndRevealDraftStepDefinitions {

	@Given("there is a next draft, face down")
	public void there_is_a_next_draft_face_down() {
		HelperFunctions.initializeGame();
		KingdominoApplication.getGameplay().setGamestatus("CreatingNextDraft");
		KingdominoApplication.getGameplay().createNextDraft();
		
	}
	
	@And("all dominoes in current draft are selected")
	public void all_dominoes_in_current_draft_are_selected() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Draft currentDraft = new Draft(DraftStatus.FaceUp, game);
		currentDraft.addIdSortedDomino(game.getAllDomino(10));
		currentDraft.addIdSortedDomino(game.getAllDomino(1));
		currentDraft.addIdSortedDomino(game.getAllDomino(2));
		currentDraft.addIdSortedDomino(game.getAllDomino(3));
		Domino dom0 = HelperFunctions.getdominoByID(10);
		Domino dom1 = HelperFunctions.getdominoByID(1);
		Domino dom2 = HelperFunctions.getdominoByID(2);
		Domino dom3 = HelperFunctions.getdominoByID(3);
		DominoSelection one = new DominoSelection(game.getPlayer(0),dom0, currentDraft);
		DominoSelection two = new DominoSelection(game.getPlayer(1),dom1, currentDraft);
		DominoSelection three = new DominoSelection(game.getPlayer(2),dom2, currentDraft);
		DominoSelection four = new DominoSelection(game.getPlayer(3),dom3, currentDraft);
		currentDraft.addSelection(one);
		currentDraft.addSelection(two);
		currentDraft.addSelection(three);
		currentDraft.addSelection(four);
		game.setCurrentDraft(currentDraft);
	}
	
	@When("next draft is sorted")
	public void next_draft_is_sorted() {
		KingdominoApplication.getGameplay().orderNextDraft();
	}
	
	@When("next draft is revealed")
	public void next_draft_is_revealed() {
	    KingdominoApplication.getGameplay().draftReady();
	}
	
	@Then("the next draft shall be sorted")
	public void the_next_draft_shall_be_sorted() {
	    Draft nextDraft = KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft();
	    int id = 0;
	    for(int i = 0; i < 4; i++) {
	    	if(id < nextDraft.getIdSortedDomino(i).getId()) {
	    		id = nextDraft.getIdSortedDomino(i).getId();
	    	} else {
	    		fail();
	    	}
	    }
	}

	@Then("the next draft shall be facing up")
	public void the_next_draft_shall_be_facing_up() {
		Draft nextDraft = KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft();
		if(nextDraft.getDraftStatus() != DraftStatus.FaceUp) {
			fail();
		}
	}

	@Then("it shall be the player's turn with the lowest domino ID selection")
	public void it_shall_be_the_player_s_turn_with_the_lowest_domino_ID_selection() {
	    Game game = KingdominoApplication.getKingdomino().getCurrentGame();
	    List<Player> players = game.getPlayers();
	    int i = 0;
	    Player player = null;
	    for(Player p : players) {
	    	if(i == 0) {
	    		i = p.getDominoSelection().getDomino().getId();
	    		player = p;
	    	}
	    	if(p.getDominoSelection().getDomino().getId() < i) {
	    		i = p.getDominoSelection().getDomino().getId();
	    		player = p;
	    	}
	    }
	    if(game.getCurrentPlayer() != player) {
	    	fail();
	    }
	    
	}
	
}
