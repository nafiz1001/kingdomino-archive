package ca.mcgill.ecse223.kingdomino.features;

import io.cucumber.java.en.*;

import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import helper.HelperFunctions;

import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;

public class GlobalStepDefinitions {
    @Given("the following dominoes are present in a player's kingdom:")
    public void the_following_dominoes_are_present_in_a_player_s_kingdom(io.cucumber.datatable.DataTable dataTable) {
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
			Kingdom kingdom = game.getPlayer(0).getKingdom();
			DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
			domInKingdom.setDirection(dir);
			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
		}
    }
    
    @Given("the current player preplaced the domino with ID {int} at position {int}:{int} and direction {string}")
    public void the_current_player_preplaced_the_domino_with_ID_at_position_and_direction(Integer int1, Integer int2, Integer int3, String string) {
    	Player player = KingdominoApplication.getKingdomino().getCurrentGame().getCurrentPlayer();
    	
    	DominoInKingdom dominoInKingdom = null;
    	for (KingdomTerritory territory : player.getKingdom().getTerritories()) {
    		if (territory instanceof DominoInKingdom) {
    			if (((DominoInKingdom)territory).getDomino().getId() == int1) {
    				dominoInKingdom = ((DominoInKingdom)territory);
    			}
    			
    		}
    	}
    	
    	if (dominoInKingdom == null) {
    		dominoInKingdom = new DominoInKingdom(int2, int3, player.getKingdom(), HelperFunctions.getdominoByID(int1));
    	} else {
    		dominoInKingdom.setX(int2);
    		dominoInKingdom.setY(int3);
    	}
    	
    	dominoInKingdom.setDirection(DirectionKind.valueOf(
    			Character.toUpperCase(string.charAt(0)) + string.substring(1).toLowerCase()
		));
    	dominoInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
    }
    
    @Given("the  player preplaces domino {int} to their kingdom at position {int}:{int} with direction {string}")
	public void the_player_preplaces_domino_to_their_kingdom_at_position_with_direction(Integer int1, Integer int2, Integer int3, String string) {
	    the_current_player_preplaced_the_domino_with_ID_at_position_and_direction(int1, int2, int3, string);
	}
}
