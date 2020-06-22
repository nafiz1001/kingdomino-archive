package ca.mcgill.ecse223.kingdomino.features;


import static org.junit.Assert.assertEquals;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import helper.HelperFunctions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CalculatePlayerScoreStepDefinitions {

	@Given("the game is initialized for calculate player score")
	public void the_game_is_initialized_for_calculate_player_score() {
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		HelperFunctions.addDefaultUsersAndPlayers(game);
		HelperFunctions.createAllDominoes(game);
		game.setCurrentPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino); 
	}

	@Given("the game has {string} bonus option")
	public void the_game_has_bonus_option(String string) {
		KingdominoController.addBonusOption(string);
	}

	@When("calculate player score is initiated")
	public void calculate_player_score_is_initiated() {
		KingdominoController.calculatePlayerScore();
	}

	@Then("the total score should be {int}")
	public void the_total_score_should_be(Integer int1) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
	    assertEquals(int1, (Integer) game.getPlayer(0).getTotalScore());
	}

}
