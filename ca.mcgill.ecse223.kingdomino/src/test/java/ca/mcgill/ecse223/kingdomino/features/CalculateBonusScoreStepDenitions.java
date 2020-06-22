package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.BonusOption;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import helper.HelperFunctions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CalculateBonusScoreStepDenitions {
	@Given("the game is initialized for calculate bonus scores")
	public void the_game_is_initialized_for_calculate_bonus_scores() {
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

	@Given("Middle Kingdom is selected as bonus option")
	public void middle_Kingdom_is_selected_as_bonus_option() {
		KingdominoController.addBonusOption(KingdominoController.MIDDLE_KINGDOM);
	}

	@When("calculate bonus score is initiated")
	public void calculate_bonus_score_is_initiated() {
	    KingdominoController.calculateBonusScore();
	}

	@Then("the bonus score should be {int}")
	public void the_bonus_score_should_be(Integer int1) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Integer bonusScore = game.getPlayer(0).getBonusScore();
		assertEquals(int1, bonusScore);
	}

	@Given("Harmony is selected as bonus option")
	public void harmony_is_selected_as_bonus_option() {
		KingdominoController.addBonusOption(KingdominoController.HARMONY);
	}
}
