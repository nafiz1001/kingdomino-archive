package kingdomino.features;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import kingdomino.helper.ControllerHelper;
import kingdomino.helper.Helper;
import kingdomino.model.*;
import org.junit.Assert;

import java.util.List;

public class ChooseNextDominoStepDefinitions {
    @Given("the game is initialized for choose next domino")
    public void the_game_is_initialized_for_choose_next_domino() {
        Helper.initializeEmptyGame();
        Helper.getCurrentGame().setNextDraft(new Draft(Draft.DraftStatus.FaceUp, Helper.getCurrentGame()));
    }

    @Given("the next draft is sorted with dominoes {string}")
    public void the_next_draft_is_sorted_with_dominoes(String _ids) {
        for (String id : _ids.split(",")) {
            Helper.getCurrentGame().getNextDraft().addIdSortedDomino(Helper.getdominoByID(Integer.parseInt(id)));
        }
    }

    @Given("player's domino selection {string}")
    public void player_s_domino_selection(String _selection) {
        String[] selection = _selection.split(",");
        for (int i = 0; i < selection.length; ++i) {
            if (!selection[i].equalsIgnoreCase("none")) {
                final String color = Character.toUpperCase(selection[i].charAt(0)) + selection[i].substring(1);
                Player player = Helper.findPlayer(Player.PlayerColor.valueOf(color));
                final Draft nextDraft = Helper.getCurrentGame().getNextDraft();
                new DominoSelection(player, nextDraft.getIdSortedDomino(i), nextDraft);
            }
        }
    }

    @Given("the current player is {string}")
    public void the_current_player_is(String currentplayer) {
        final String color = Character.toUpperCase(currentplayer.charAt(0)) + currentplayer.substring(1);
        Helper.getCurrentGame().setCurrentPlayer(Helper.findPlayer(Player.PlayerColor.valueOf(color)));
    }

    @When("current player chooses to place king on {int}")
    public void current_player_chooses_to_place_king_on(Integer dominoId) {
        ControllerHelper.current_player_chooses_to_place_king_on(dominoId);
    }

    @Then("current player king now is on {string}")
    public void current_player_king_now_is_on(String chosendominoid) {
        Assert.assertNotNull(Helper.getCurrentGame().getCurrentPlayer().getDominoSelection());
        Assert.assertEquals(Integer.parseInt(chosendominoid), Helper.getCurrentGame().getCurrentPlayer().getDominoSelection().getDomino().getId());
    }

    @Then("the selection for next draft is now equal to {string}")
    public void the_selection_for_next_draft_is_now_equal_to(String _newselection) {
        final String[] newselection = _newselection.split(",");
        final List<Domino> dominos = Helper.getCurrentGame().getNextDraft().getIdSortedDominos();

        for (int i = 0; i < newselection.length; ++i) {
            if (newselection[i].equalsIgnoreCase("none")) {
                Assert.assertNull(dominos.get(i).getDominoSelection());
            } else {
                Assert.assertNotNull(dominos.get(i).getDominoSelection());
                Assert.assertEquals(newselection[i], dominos.get(i).getDominoSelection().getPlayer().getColor().name().toLowerCase());
            }
        }
    }

    @Then("the selection for the next draft selection is still {string}")
    public void the_selection_for_the_next_draft_selection_is_still(String selection) {
        this.the_selection_for_next_draft_is_now_equal_to(selection);
    }
}
