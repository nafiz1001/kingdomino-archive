package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController.*;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
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


public class RotateCurrentDominoStepDefinitions {
	
	//public  HashMap<String, Player> color2player;
	
	@Given("the game is initialized for rotate current domino")
	public void the_game_is_initialized_for_rotate_current_domino() {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		
		HelperFunctions.initializeGame();
		

	}

	@Given("it is {string}'s turn")
	public void it_is_s_turn(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		Player player = HelperFunctions.color2player.get(string);
		game.setCurrentPlayer(player);
		
		
	}

	@Given("{string}'s kingdom has following dominoes:")
	public void s_kingdom_has_following_dominoes(String string, io.cucumber.datatable.DataTable dataTable) {
//	    // Write code here that turns the phrase above into concrete actions
//	    // For automatic transformation, change DataTable to one of
//	    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
//	    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
//	    // Double, Byte, Short, Long, BigInteger or BigDecimal.
//	    //
//	    // For other transformations you can register a DataTableType.
//	    //throw new cucumber.api.PendingException();
//		
//		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player player = HelperFunctions.color2player.get(string);
		
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer id = Integer.decode(map.get("id"));
			DirectionKind dir = HelperFunctions.getDirection(map.get("dir"));
			Integer posx = Integer.decode(map.get("posx"));
			Integer posy = Integer.decode(map.get("posy"));

			// Add the domino to a player's kingdom
			Domino dominoToPlace = HelperFunctions.getdominoByID(id);
			
			Kingdom kingdom = player.getKingdom();
			DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
			domInKingdom.setDirection(dir);
			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
					
				
				
 			}
			
			
		}
		
	

	@Given("{string} has selected domino {int}")
	public void has_selected_domino(String string, Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Domino domino = HelperFunctions.getdominoByID(int1);
		Player player = HelperFunctions.color2player.get(string);
		
		Draft draft = new Draft(DraftStatus.Sorted, game);
		draft.addIdSortedDomino(domino);
		game.setNextDraft(draft);
		player.setDominoSelection(new DominoSelection(player, domino, draft));
		
		
		
		
	}

	@Given("domino {int} is tentatively placed at position {int}:{int} with direction {string}")
	public void domino_is_tentatively_placed_at_position_with_direction(Integer int1, Integer int2, Integer int3, String string) {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getCurrentPlayer();
		DominoInKingdom dominoInKingdom = new DominoInKingdom(int2, int3, player.getKingdom(), HelperFunctions.getdominoByID(int1));
		dominoInKingdom.setDirection(DirectionKind.valueOf(Character.toUpperCase(string.charAt(0))+string.substring(1).toLowerCase()));
		
		System.out.println(dominoInKingdom.getDomino().getStatus().toString());
//		System.out.println(dominoInKingdom.getX());
//		System.out.println(dominoInKingdom.getY());
		System.out.println(dominoInKingdom.getDirection().toString());
		
		if((dominoInKingdom.getX()<-2 || dominoInKingdom.getX() >2) || (dominoInKingdom.getY()<-2 || dominoInKingdom.getY() >2)){
			dominoInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
		}
		
		else if((dominoInKingdom.getX()==-2 && dominoInKingdom.getDirection()==DirectionKind.Down)|| (dominoInKingdom.getX()==2 && dominoInKingdom.getDirection()==DirectionKind.Up)
				|| (dominoInKingdom.getY()==-2 && dominoInKingdom.getDirection()==DirectionKind.Left) || (dominoInKingdom.getY()==2 && dominoInKingdom.getDirection()==DirectionKind.Right)) {
			dominoInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
		}
	
		
		else dominoInKingdom.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
			
		System.out.println(dominoInKingdom.getDomino().getStatus().toString());
	}

	@When("{string} requests to rotate the domino with {string}")
	public void requests_to_rotate_the_domino_with(String string, String string2) {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
	
		Player player = HelperFunctions.color2player.get(string);
		KingdominoApplication.getKingdomino().getCurrentGame().setCurrentPlayer(player);
		
		KingdominoController.rotateDomino(string2);
		
		
	}

	@Then("the domino {int} is still tentatively placed at {int}:{int} but with new direction {string}")
	public void the_domino_is_still_tentatively_placed_at_but_with_new_direction(Integer id, Integer posx, Integer posy, String dir) {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player player = game.getCurrentPlayer();
		Kingdom kingdom = player.getKingdom();
		List<KingdomTerritory> territory = kingdom.getTerritories();
		
		for(KingdomTerritory terr : territory) {
			if (terr instanceof DominoInKingdom) {
				DominoInKingdom dominoToRotate = (DominoInKingdom) terr;
				if (dominoToRotate.getDomino().getStatus() == DominoStatus.CorrectlyPreplaced || dominoToRotate.getDomino().getStatus() == DominoStatus.ErroneouslyPreplaced) {
					assertEquals(dominoToRotate.getDomino().getId(), id.intValue());
					assertEquals(dominoToRotate.getX(), posx.intValue());
					assertEquals(dominoToRotate.getY(), posy.intValue());

					// the 1st argument is the expected, the 2nd is the actual
					
					System.out.println(dominoToRotate.getDirection().toString().toLowerCase());
					
					assertEquals(dir, dominoToRotate.getDirection().toString().toLowerCase());
					break;
				}
			}
		}
	}
				
				

	@Then("the domino {int} should have status {string}")
	public void the_domino_should_have_status(Integer int1, String string) {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		
		Domino domino = HelperFunctions.getdominoByID(int1);
		assertEquals(domino.getStatus().toString(), string);
	}

	@Given("domino {int} has status {string}")
	public void domino_has_status(Integer int1, String string) {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		Domino domino = HelperFunctions.getdominoByID(int1);
		domino.setStatus(HelperFunctions.getDominoStatus(string));
//		
	}
	

	@Then("domino {int} is tentatively placed at the same position {int}:{int} with the same direction {string}")
	public void domino_is_tentatively_placed_at_the_same_position_with_the_same_direction(Integer id, Integer posx, Integer posy, String dir) {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
	 Domino domino = HelperFunctions.getdominoByID(id);
	 Player player = domino.getDominoSelection().getPlayer();
	 Kingdom kingdom = player.getKingdom();
	 DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, domino );
	 domInKingdom.setDirection(HelperFunctions.getDirection(dir));
			
		
	}

	@Then("domino {int} should still have status {string}")
	public void domino_should_still_have_status(Integer int1, String string) {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		
		Domino domino = HelperFunctions.getdominoByID(int1);
		assertEquals(domino.getStatus(), HelperFunctions.getDominoStatus(string));
	}
	
}
