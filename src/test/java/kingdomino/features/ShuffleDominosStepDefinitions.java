package kingdomino.features;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import kingdomino.controller.KingdominoController;
import kingdomino.helper.Helper;
import kingdomino.model.Domino;

public class ShuffleDominosStepDefinitions {
    @Given("the game is initialized for shuffle dominoes")
    public void the_game_is_initialized_for_shuffle_dominoes() {
        Helper.initializeEmptyGame();

        Domino currDomino = Helper.getdominoByID(1);
        Helper.getCurrentGame().setTopDominoInPile(currDomino);
        for (int i = 2; i <= 48; ++i) {
            final Domino nextDomino = Helper.getdominoByID(i);
            currDomino.setNextDomino(nextDomino);
            currDomino = nextDomino;
        }
    }

    @Given("there are {int} players playing")
    public void there_are_players_playing(Integer nplayers) {
        Helper.getCurrentGame().setNumberOfPlayers(nplayers);
    }

    @When("the shuffling of dominoes is initiated")
    public void the_shuffling_of_dominoes_is_initiated() {
        KingdominoController.Helper.the_shuffling_of_dominoes_is_initiated();
    }

    @Then("the first draft shall exist")
    public void the_first_draft_shall_exist() {
        // this is retarded. THIS IS SUFFLE DOMINOS, NOT CREATE FIRST DRAFT!
        throw new io.cucumber.java.PendingException();
    }

    @Then("the first draft should have {int} dominoes on the board face down")
    public void the_first_draft_should_have_dominoes_on_the_board_face_down(Integer int1) {
        // this is also retarded
        throw new io.cucumber.java.PendingException();
    }

    @Then("there should be {int} dominoes left in the draw pile")
    public void there_should_be_dominoes_left_in_the_draw_pile(Integer int1) {
        // this is also retarded
        throw new io.cucumber.java.PendingException();
    }

    @When("I initiate to arrange the domino in the fixed order {string}")
    public void i_initiate_to_arrange_the_domino_in_the_fixed_order(String string) {
        KingdominoController.Helper.i_initiate_to_arrange_the_domino_in_the_fixed_order(string);
    }

    @Then("the draw pile should consist of everything in {string} except the first {int} dominoes with their order preserved")
    public void the_draw_pile_should_consist_of_everything_in_except_the_first_dominoes_with_their_order_preserved(String string, Integer int1) {
        // this is also retarded
        throw new io.cucumber.java.PendingException();
    }
}