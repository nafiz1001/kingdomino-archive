package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import helper.HelperFunctions;
import io.cucumber.java.en.*;

/**
 * 
 * @author antonianistor
 *
 */

public class MoveCurrentDominoStepDefinitions {
	
	 //public  HashMap<String, Player> color2player;
	
	@Given("the game is initialized for move current domino")
	public static void the_game_is_initialized_for_move_current_domino() {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		
		HelperFunctions.initializeGame();
		
	}

	
	
	
	@When("{string} removes his king from the domino {int}")
	public void removes_his_king_from_the_domino(String string, Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Domino domino = HelperFunctions.getdominoByID(int1);
		for (Player player: game.getPlayers() ) {
			if (player.getColor().toString().equalsIgnoreCase(string)) {
				//
			}
		}
				
	}

	@Then("domino {int} should be tentative placed at position {int}:{int} of {string}'s kingdom with ErroneouslyPreplaced status")
	public void domino_should_be_tentative_placed_at_position_of_s_kingdom_with_ErroneouslyPreplaced_status(Integer int1, Integer int2, Integer int3, String string) {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		Player player = HelperFunctions.color2player.get(string);
		Kingdom kingdom = player.getKingdom();
		Domino domino = HelperFunctions.getdominoByID(int1);
		DominoInKingdom domInKingdom = new DominoInKingdom(int2,int3, kingdom, domino);
		domInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
		
	}

	@When("{string} requests to move the domino {string}")
	public void requests_to_move_the_domino(String string, String string2) {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		//Player player = RotateCurrentDominoStepDefinitions.color2player.get(string);
		KingdominoApplication.getKingdomino().getCurrentGame().setCurrentPlayer(HelperFunctions.color2player.get(string));
		KingdominoController.moveDomino(string2);
		
	}

	@Then("the domino {int} should be tentatively placed at position {int}:{int} with direction {string}")
	public void the_domino_should_be_tentatively_placed_at_position_with_direction(Integer int1, Integer int2, Integer int3, String string) {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		Domino domino = HelperFunctions.getdominoByID(int1);
		Player player = domino.getDominoSelection().getPlayer();
		Kingdom kingdom = player.getKingdom();
		DominoInKingdom domInKingdom = new DominoInKingdom(int2, int3, kingdom, domino );
		domInKingdom.setDirection(HelperFunctions.getDirection(string));
	}

	@Then("the new status of the domino is {string}")
	public void the_new_status_of_the_domino_is(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player player = game.getCurrentPlayer();
		Domino domino = player.getDominoSelection().getDomino();
		domino.setStatus(HelperFunctions.getDominoStatus(string));
		
	}

	@Then("the domino {int} is still tentatively placed at position {int}:{int}")
	public void the_domino_is_still_tentatively_placed_at_position(Integer int1, Integer int2, Integer int3) {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player player = game.getCurrentPlayer();
		Kingdom kingdom = player.getKingdom();
		Domino domino = HelperFunctions.getdominoByID(int1);
		DominoInKingdom domInKingdom = new DominoInKingdom(int2, int3,kingdom, domino );
		assertEquals(domInKingdom.getX(), int2.intValue());
		assertEquals(domInKingdom.getY(), int3.intValue());
		
	}

	@Then("the domino should still have status {string}")
	public void the_domino_should_still_have_status(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player player = game.getCurrentPlayer();
		Domino domino = player.getDominoSelection().getDomino();
		assertEquals(domino.getStatus().toString(), string);
	}



}
