package kingdomino.features;

import static org.junit.jupiter.api.Assertions.fail;

import io.cucumber.java.en.*;

public class ShuffleDominosStepDefinitions {
    @Given("the game is initialized for shuffle dominoes")
    public void the_game_is_initialized_for_shuffle_dominoes() {
        // TODO: Write code here that turns the phrase above into concrete actions
    }

    @Given("there are {int} players playing")
    public void there_are_players_playing(Integer int1) {
        // TODO: Write code here that turns the phrase above into concrete actions
    }

    @When("the shuffling of dominoes is initiated")
    public void the_shuffling_of_dominoes_is_initiated() {
        // TODO: Call your Controller method here.
        throw new io.cucumber.java.PendingException(); // Remove this line once your controller method is implemented
    }

    @Then("the first draft shall exist")
    public void the_first_draft_shall_exist() {
        fail();
    }

    @Then("the first draft should have {int} dominoes on the board face down")
    public void the_first_draft_should_have_dominoes_on_the_board_face_down(Integer int1) {
        fail();
    }

    @Then("there should be {int} dominoes left in the draw pile")
    public void there_should_be_dominoes_left_in_the_draw_pile(Integer int1) {
        fail();
    }

    @When("I initiate to arrange the domino in the fixed order {string}")
    public void i_initiate_to_arrange_the_domino_in_the_fixed_order(String string) {
        // TODO: Call your Controller method here.
        throw new io.cucumber.java.PendingException(); // Remove this line once your controller method is implemented
    }

    @Then("the draw pile should consist of everything in {string} except the first {int} dominoes with their order preserved")
    public void the_draw_pile_should_consist_of_everything_in_except_the_first_dominoes_with_their_order_preserved(String string, Integer int1) {
        fail();
    }
}