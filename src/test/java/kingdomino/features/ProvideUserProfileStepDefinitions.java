package kingdomino.features;

import java.util.List;
import java.util.Map;

import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import kingdomino.KingdominoApplication;
import kingdomino.controller.KingdominoController;
import kingdomino.model.User;

public class ProvideUserProfileStepDefinitions {
    private boolean creationStatus = false;
    private List<User> users;
    private User user;

    @Given("the program is started and ready for providing user profile")
    public void the_program_is_started_and_ready_for_providing_user_profile() {
        KingdominoApplication.getKingdomino();
    }

    @Given("there are no users exist")
    public void there_are_no_users_exist() {}

    @When("I provide my username {string} and initiate creating a new user")
    public void i_provide_my_username_and_initiate_creating_a_new_user(String name) {
        creationStatus = KingdominoController.Helper.i_provide_my_username_and_initiate_creating_a_new_user(name);
    }

    @Then("the user {string} shall be in the list of users")
    public void the_user_shall_be_in_the_list_of_users(String name) {
        boolean userFound = false;
        for (User u : KingdominoApplication.getKingdomino().getUsers()) {
            if (u.getName().equals(name)) {
                userFound = true;
                break;
            }
        }

        Assert.assertTrue(userFound);
    }

    @Given("the following users exist:")
    public void the_following_users_exist(io.cucumber.datatable.DataTable dataTable) {
		final List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
            final String name = map.get("name");
            KingdominoApplication.getKingdomino().addUser(name);
		}
    }

    @Then("the user creation shall {string}")
    public void the_user_creation_shall(String status) {
        switch(status) {
            case "succeed":
                Assert.assertTrue(creationStatus);
                break;
            case "fail":
                Assert.assertFalse(creationStatus);
                break;
        }
    }

    @When("I initiate the browsing of all users")
    public void i_initiate_the_browsing_of_all_users() {
        users = KingdominoController.Helper.i_initiate_the_browsing_of_all_users();
    }

    @Then("the users in the list shall be in the following alphabetical order:")
    public void the_users_in_the_list_shall_be_in_the_following_alphabetical_order(io.cucumber.datatable.DataTable dataTable) {
        final List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
            final String expectedName = map.get("name");
            final Integer placeInList = Integer.decode(map.get("placeinlist"));

            Assert.assertEquals(expectedName, this.users.get(placeInList-1).getName());
		}
    }

    @Given("the following users exist with their game statistics:")
    public void the_following_users_exist_with_their_game_statistics(io.cucumber.datatable.DataTable dataTable) {
        final List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
            final String name = map.get("name");
            final Integer playedGames = Integer.decode(map.get("playedGames"));
            final Integer wonGames = Integer.decode(map.get("wonGames"));

            final User u = new User(name, KingdominoApplication.getKingdomino());
            u.setPlayedGames(playedGames);
            u.setWonGames(wonGames);
		}
    }

    @When("I initiate querying the game statistics for a user {string}")
    public void i_initiate_querying_the_game_statistics_for_a_user(String name) {
        user = KingdominoController.Helper.i_initiate_querying_the_game_statistics_for_a_user(name);
    }

    @Then("the number of games played by {string} shall be {int}")
    public void the_number_of_games_played_by_shall_be(String name, Integer playedGames) {
        Assert.assertEquals(playedGames.intValue(), user.getPlayedGames());
    }

    @Then("the number of games won by {string} shall be {int}")
    public void the_number_of_games_won_by_shall_be(String name, Integer wonGames) {
        Assert.assertEquals(wonGames.intValue(), user.getWonGames());
    }
}