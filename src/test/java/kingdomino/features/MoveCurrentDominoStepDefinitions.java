package kingdomino.features;

import io.cucumber.java.en.*;

public class MoveCurrentDominoStepDefinitions {
    @Given("the game is initialized for move current domino")
    public void the_game_is_initialized_for_move_current_domino() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Given("it is {string}'s turn")
    public void it_is_s_turn(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Given("{string} has selected domino {int}")
    public void has_selected_domino(String string, Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("{string} removes his king from the domino {int}")
    public void removes_his_king_from_the_domino(String string, Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("domino {int} should be tentative placed at position {int}:{int} of {string}'s kingdom with ErroneouslyPreplaced status")
    public void domino_should_be_tentative_placed_at_position_of_s_kingdom_with_ErroneouslyPreplaced_status(Integer int1, Integer int2, Integer int3, String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Given("{string}'s kingdom has following dominoes:")
    public void s_kingdom_has_following_dominoes(String string, io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        throw new io.cucumber.java.PendingException();
    }

    @Given("domino {int} is tentatively placed at position {int}:{int} with direction {string}")
    public void domino_is_tentatively_placed_at_position_with_direction(Integer int1, Integer int2, Integer int3, String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("{string} requests to move the domino {string}")
    public void requests_to_move_the_domino(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the domino {int} should be tentatively placed at position {int}:{int} with direction {string}")
    public void the_domino_should_be_tentatively_placed_at_position_with_direction(Integer int1, Integer int2, Integer int3, String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the new status of the domino is {string}")
    public void the_new_status_of_the_domino_is(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Given("domino {int} has status {string}")
    public void domino_has_status(Integer int1, String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the domino {int} is still tentatively placed at position {int}:{int}")
    public void the_domino_is_still_tentatively_placed_at_position(Integer int1, Integer int2, Integer int3) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the domino should still have status {string}")
    public void the_domino_should_still_have_status(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
