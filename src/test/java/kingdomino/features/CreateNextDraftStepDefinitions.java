package kingdomino.features;

import io.cucumber.java.en.*;
import kingdomino.KingdominoApplication;
import kingdomino.helper.Helper;
import kingdomino.model.Domino;
import kingdomino.model.Draft;
import kingdomino.model.Game;
import org.junit.Assert;

public class CreateNextDraftStepDefinitions {
    private Draft formerNextDraft;

    @Given("the game is initialized to create next draft")
    public void the_game_is_initialized_to_create_next_draft() {
        Helper.initializeEmptyGame();
    }

    @Given("there has been {int} drafts created")
    public void there_has_been_drafts_created(Integer numOfDrafts) {
        final Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        for (int i = 0; i < numOfDrafts; ++i) {
            game.addAllDraft(new Draft(Draft.DraftStatus.FaceUp, game));
        }
    }

    @Given("there is a current draft")
    public void there_is_a_current_draft() {
        final Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        final Draft beforeLastDraft = game.getAllDraft(game.getAllDrafts().size() - 2);
        game.setCurrentDraft(beforeLastDraft);
    }

    @Given("there is a next draft")
    public void there_is_a_next_draft() {
        final Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        formerNextDraft = game.getAllDraft(game.getAllDrafts().size() - 1);
        game.setNextDraft(formerNextDraft);
    }

    @Given("the top {int} dominoes in my pile have the IDs {string}")
    public void the_top_dominoes_in_my_pile_have_the_IDs(Integer int1, String list_of_ids) {
        final Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        final String[] ids = list_of_ids.split(",");
        game.setTopDominoInPile(Helper.getdominoByID(Integer.parseInt(ids[0])));

        Domino prevDomino = Helper.getdominoByID(Integer.parseInt(ids[0]));
        for (int i = 1; i < int1; ++i) {
            prevDomino.setNextDomino(Helper.getdominoByID(Integer.parseInt(ids[i])));
            prevDomino = Helper.getdominoByID(Integer.parseInt(ids[i]));
        }
    }

    @When("create next draft is initiated")
    public void create_next_draft_is_initiated() {
        Helper.create_next_draft_is_initiated();
    }

    @Then("a new draft is created from dominoes {string}")
    public void a_new_draft_is_created_from_dominoes(String draft_ids) {
        final Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        final String[] ids = draft_ids.split(",");

        for (int i = 0; i < ids.length; ++i) {
            Assert.assertEquals(Integer.parseInt(ids[i]), game.getNextDraft().getIdSortedDomino(i).getId());
        }
    }

    @Then("the next draft now has the dominoes {string}")
    public void the_next_draft_now_has_the_dominoes(String draft_ids) {
        this.a_new_draft_is_created_from_dominoes(draft_ids);
    }

    @Then("the dominoes in the next draft are face down")
    public void the_dominoes_in_the_next_draft_are_face_down() {
        Assert.assertEquals(Draft.DraftStatus.FaceDown, KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getDraftStatus());
    }

    @Then("the top domino of the pile is ID {int}")
    public void the_top_domino_of_the_pile_is_ID(Integer topId) {
        Assert.assertEquals(topId.intValue(), KingdominoApplication.getKingdomino().getCurrentGame().getTopDominoInPile().getId());
    }

    @Then("the former next draft is now the current draft")
    public void the_former_next_draft_is_now_the_current_draft() {
        Assert.assertEquals(this.formerNextDraft, Helper.getCurrentGame().getCurrentDraft());
    }

    @Given("this is a {int} player game")
    public void this_is_a_player_game(Integer num_players) {
        Helper.getCurrentGame().setNumberOfPlayers(num_players);
    }

    @Then("the pile is empty")
    public void the_pile_is_empty() {
        Assert.assertNull(Helper.getCurrentGame().getTopDominoInPile());
    }

    @Then("there is no next draft")
    public void there_is_no_next_draft() {
        Assert.assertNull(Helper.getCurrentGame().getNextDraft());
    }
}