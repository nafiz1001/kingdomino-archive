package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import helper.HelperFunctions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ChooseNextDominoStepDefinitions {
	
	
	Player[] arrayPlayers = new Player[4]; // for player's domino selectio {string}
	String[] arrayPlayerColors = new String[4]; //for player's domino selectio {string}
	
	@Given("the game is initialized for choose next domino")
	public void the_game_is_initialized_for_choose_next_domino() {
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		HelperFunctions.addDefaultUsersAndPlayers(game);
		HelperFunctions.createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
		Draft nextDraft = new Draft(DraftStatus.FaceUp, game);
		game.setNextDraft(nextDraft);
		// fine
	}
	
	@Given("the next draft is sorted with dominoes {string}")
	public void the_next_draft_is_sorted_with_dominoes(String string) {
		
		Draft nextDraft = KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft(); 
		
		for (int id : KingdominoController.splitDraftString(string)) {
//			System.out.println("penis" + id);
			nextDraft.addIdSortedDomino(HelperFunctions.getdominoByID(id));

		}
	}
	
	@Given("player's domino selection {string}") // work 
	public void player_s_domino_selection(String string) {
		String[] splittedString = string.split(","); 

		int i = 0;
		for (String s: splittedString) {
			if (s.equals("none")) {
				arrayPlayers[i] = null; 
				arrayPlayerColors[i] = "none";
				i++;
				continue;
			}
			else {
				for (Player player: KingdominoApplication.getKingdomino().getCurrentGame().getPlayers()) {
					if (s.equals(player.getColor().toString().toLowerCase())) {
						arrayPlayers[i] = player;
						arrayPlayerColors[i] = player.getColor().toString();
						i++;
					
					} else {
						arrayPlayers[i] = null;
						arrayPlayerColors[i] = "none"; 
						
					}
				}
			}
		}
	
		int[] dominoIDs = {1,2,3,4};

		for (int q = 0; q < dominoIDs.length; q++) {
			if (arrayPlayers[q] != null) {
				DominoSelection selection = new DominoSelection( 
					arrayPlayers[q],
					HelperFunctions.getdominoByID(dominoIDs[q]),
					KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft());
				arrayPlayers[q].setDominoSelection(selection);
			
			} else {
				continue;
			}
		}	
	
	}
	
	@Given("the current player is {string}")
	public void the_current_player_is(String string) {
		
		for (Player player: KingdominoApplication.getKingdomino().getCurrentGame().getPlayers()) {
			if (player.getColor().toString().equalsIgnoreCase(string)) {
				KingdominoApplication.getKingdomino().getCurrentGame().setCurrentPlayer(player);
				break;
			}

		}
	}
	
	@When("current player chooses to place king on {int}")
	public void current_player_chooses_to_place_king_on(Integer ID) {
	
	   arrayPlayerColors = KingdominoController.placeCurrentKingOnDominoID(ID, arrayPlayerColors);
	   for (String colors: arrayPlayerColors) {
		   System.out.println("The Current Selection is " + colors);
	   }
	}
	
	@Then("current player king now is on {string}")
	public void current_player_king_now_is_on(String string) {
		assertEquals(KingdominoApplication.getKingdomino().getCurrentGame().getCurrentPlayer().getDominoSelection().getDomino().getId(), Integer.valueOf(string).intValue());
		
	}
	
	@Then("the selection for next draft is now equal to {string}")
	public void the_selection_for_next_draft_is_now_equal_to(String string) {
		String[] colours = string.split(",");
		  
		for (int i = 0; i < colours.length; i++) {

			if (arrayPlayerColors[i].equalsIgnoreCase(colours[i])) {
				continue;
			}
			else {
				fail();
			}
			
		}
		System.out.println("The player has selected a new domino in the draft");
	}
	
	@Then("the selection for the next draft selection is still {string}")
	public void the_selection_for_the_next_draft_selection_is_still(String string) {
		String[] colours = string.split(",");
		for (int i = 0; i < colours.length; i++) { 
			
			if(arrayPlayerColors[i].equalsIgnoreCase(colours[i])) {
				continue;
			}
			else {
				fail();
			}
			
		
		}
		System.out.println("The selection for the next draft selection has not changed");
	}

}
