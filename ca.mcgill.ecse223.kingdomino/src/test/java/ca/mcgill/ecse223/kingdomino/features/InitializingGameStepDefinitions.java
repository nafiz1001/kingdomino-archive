package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import helper.HelperFunctions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class InitializingGameStepDefinitions {
	List<Player> oldPlayers;

@Given("the game has not been started")
public void the_game_has_not_been_started() {
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
	
}

@When("start of the game is initiated")
public void start_of_the_game_is_initiated() {
	oldPlayers = KingdominoApplication.getKingdomino().getCurrentGame().getPlayers();
    KingdominoApplication.resetGameplay();
}

@Then("the pile shall be shuffled")
public void the_pile_shall_be_shuffled() {
   List<Domino> pileOfDominoes = KingdominoApplication.getKingdomino().getCurrentGame().getAllDominos();
   List<Domino> shuffledOne = new ArrayList<>();
   
   Domino domino = KingdominoApplication.getKingdomino().getCurrentGame().getTopDominoInPile();
   while (domino != null) {
	   shuffledOne.add(domino);
	   domino = domino.getNextDomino();
   }
   
   assertNotEquals(pileOfDominoes, shuffledOne);
}

@Then("the first draft shall be on the table")
public void the_first_draft_shall_be_on_the_table() {
    if(KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft()==null) {
    	fail();
    }
}

@Then("the first draft shall be revealed")
public void the_first_draft_shall_be_revealed() {
	KingdominoApplication.getGameplay().draftReady();
	if(KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getDraftStatus()!=Draft.DraftStatus.FaceUp) {
		fail();
	}
}

@Then("the initial order of players shall be determined")
public void the_initial_order_of_players_shall_be_determined() {
	if(!oldPlayers.equals(KingdominoApplication.getKingdomino().getCurrentGame().getPlayers())){
		fail();
	}
}

@Then("the first player shall be selecting his\\/her first domino of the game")
public void the_first_player_shall_be_selecting_his_her_first_domino_of_the_game() {
   if(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(0)!=KingdominoApplication.getKingdomino().getCurrentGame().getCurrentPlayer()){
    	fail();
    }
}

@Then("the second draft shall be on the table, face down")
public void the_second_draft_shall_be_on_the_table_face_down() {
}


}
