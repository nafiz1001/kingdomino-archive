package kingdomino.features;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import kingdomino.helper.ControllerHelper;
import kingdomino.helper.Helper;
import kingdomino.model.Domino;
import kingdomino.model.Draft;
import kingdomino.model.Game;
import org.junit.Assert;

import java.util.List;

public class OrderAndRevealNextDraftStepDefinitions {
    @Given("the game is initialized for order next draft of dominoes")
    public void the_game_is_initialized_for_order_next_draft_of_dominoes() {
        Helper.initializeEmptyGame();
        Helper.getCurrentGame().setNextDraft(new Draft(Draft.DraftStatus.FaceDown, Helper.getCurrentGame()));
    }

    @Given("the next draft is {string}")
    public void the_next_draft_is(String _ids) {
        final Game game = Helper.getCurrentGame();
        String[] ids = _ids.split(",");
        final Draft nextDraft = game.getNextDraft();
        for (String id : ids) {
            nextDraft.addIdSortedDomino(Helper.getdominoByID(Integer.parseInt(id)));
        }
    }

    @Given("the dominoes in next draft are facing down")
    public void the_dominoes_in_next_draft_are_facing_down() {
        Helper.getCurrentGame().getNextDraft().setDraftStatus(Draft.DraftStatus.FaceDown);
    }

    @When("the ordering of the dominoes in the next draft is initiated")
    public void the_ordering_of_the_dominoes_in_the_next_draft_is_initiated() {
        ControllerHelper.the_ordering_of_the_dominoes_in_the_next_draft_is_initiated();
    }

    @Then("the status of the next draft is sorted")
    public void the_status_of_the_next_draft_is_sorted() {
        Assert.assertEquals(Draft.DraftStatus.Sorted, Helper.getCurrentGame().getNextDraft().getDraftStatus());
    }

    @Then("the order of dominoes in the draft will be {string}")
    public void the_order_of_dominoes_in_the_draft_will_be(String orderedids) {
        final List<Domino> dominos = Helper.getCurrentGame().getNextDraft().getIdSortedDominos();
        final String[] ids = orderedids.split(",");
        for (int i = 0; i < ids.length; ++i) {
            Assert.assertEquals(String.format("Integer.parseInt(ids[%d]) != dominos.get(i).getId(%d)", i, i), Integer.parseInt(ids[i]), dominos.get(i).getId());
        }
    }

    @Given("the game is initialized for reveal next draft of dominoes")
    public void the_game_is_initialized_for_reveal_next_draft_of_dominoes() {
        Helper.initializeEmptyGame();
        Helper.getCurrentGame().setNextDraft(new Draft(Draft.DraftStatus.Sorted, Helper.getCurrentGame()));
    }

    @Given("the dominoes in next draft are sorted")
    public void the_dominoes_in_next_draft_are_sorted() {
        // Useless step definition
    }

    @When("the revealing of the dominoes in the next draft is initiated")
    public void the_revealing_of_the_dominoes_in_the_next_draft_is_initiated() {
        ControllerHelper.the_revealing_of_the_dominoes_in_the_next_draft_is_initiated();
    }

    @Then("the status of the next draft is face up")
    public void the_status_of_the_next_draft_is_face_up() {
        Assert.assertEquals(Draft.DraftStatus.FaceUp, Helper.getCurrentGame().getNextDraft().getDraftStatus());
    }
}
