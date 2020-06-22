package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import helper.HelperFunctions;
import io.cucumber.java.en.*;

/**
 * 
 * @author antonianistor
 *
 */


public class PlaceDominoStepDefinitions {
	

	@Given("the {string}'s kingdom has the following dominoes:")
	public void the_s_kingdom_has_the_following_dominoes(String string, io.cucumber.datatable.DataTable dataTable) {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.
    //throw new cucumber.api.PendingException();
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player player = HelperFunctions.color2player.get(string);
		
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer id = Integer.decode(map.get("domino"));
			DirectionKind dir = HelperFunctions.getDirection(map.get("dominodir"));
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
	
	@Given("domino {int} is in {string} status")
	public void domino_is_in_status(Integer int1, String string) {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		
		Domino domino = HelperFunctions.getdominoByID(int1);
		domino.setStatus(HelperFunctions.getDominoStatus(string));
	}

	@When("{string} requests to place the selected domino {int}")
	public void requests_to_place_the_selected_domino(String string, Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		
		KingdominoController.placeDomino(int1);
	}

	@Then("{string}'s kingdom should now have domino {int} at position {int}:{int} with direction {string}")
	public void s_kingdom_should_now_have_domino_at_position_with_direction(String string, Integer int1, Integer int2, Integer int3, String string2) {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		Player player = HelperFunctions.color2player.get(string);
		Domino domino = HelperFunctions.getdominoByID(int1);
		Kingdom kingdom = player.getKingdom();
		
		DominoInKingdom actual = (DominoInKingdom) HelperFunctions.getTerritoryAtLocation(int2, int3, kingdom);
		Domino actualDomi = actual.getDomino();
		
		int expectedID = int1;
		int actualID = actualDomi.getId();
		
		int expectedX = int2;
		int actualX = actual.getX();
		
		int expectedY = int3;
		int actualY =actual.getY();
		
		DirectionKind expectedDir = HelperFunctions.getDirection(string2);
		DirectionKind actualDir = actual.getDirection();
		
		assertEquals(expectedID, actualID);
		assertEquals(expectedX, actualX);
		assertEquals(expectedY, actualY);
		assertEquals(expectedDir, actualDir);
		
		
		
	
	}

}
