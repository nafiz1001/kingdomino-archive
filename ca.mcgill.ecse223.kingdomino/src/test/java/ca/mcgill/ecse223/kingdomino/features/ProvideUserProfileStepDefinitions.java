package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.BonusOption;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * These Step defintions are for Feature 2 (Provide User Profile) and this feature is responsible for 
 * creation of new userin the game.Also Deals with input validation of the username.Also deals with 
 * browsing of all users and displaying them sorted in alphabetical order.This method also deals with game statistics 
 * of users , for example the quering of user game statistics i.e the number of played games and won games.
 * This feature has a lot of connection with the User Interface of this game(the beginning part before the user starts playing
 * the game.
 * @author  Mohammad Salman Mesam
 *
 */

public class ProvideUserProfileStepDefinitions {
	private List<String> strings = new ArrayList<String>();
	private List<Map<String, String>> users = new ArrayList<>();

	@Given("the program is started and ready for providing user profile")
	public void the_program_is_started_and_ready_for_providing_user_profile() {

		Kingdomino kingdomino = new Kingdomino();
		KingdominoApplication.setKingdomino(kingdomino);
	}

	@Given("there are no users exist")
	public void there_are_no_users_exist() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		List<User> users = kingdomino.getUsers();
		for (User user : users) {
			kingdomino.removeUser(user);
		}
	}

	@When("I provide my username {string} and initiate creating a new user")
	public void i_provide_my_username_and_initiate_creating_a_new_user(String username) {

		strings.clear(); // just doing a precaution to ensure that the list is empty before populating
		try {
			KingdominoController.addNewUser(username);

			strings.add("success");
		} catch (Exception e) {
			strings.add("fail");
		}
	}

	@Then("the user {string} shall be in the list of users")
	public void the_user_shall_be_in_the_list_of_users(String string) {

		List<User> user = KingdominoApplication.getKingdomino().getUsers();
		assertTrue(KingdominoController.hasUser(string));
	}

	@Given("the following users exist:")
	public void the_following_users_exist(io.cucumber.datatable.DataTable dataTable) {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
		// Map<K, List<V>>. E,K,V must be a String, Integer, Float,
		// Double, Byte, Short, Long, BigInteger or BigDecimal.
		//
		// For other transformations you can register a DataTableType.
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			KingdominoController.addNewUser(map.get("name"));
		}
	}

	@Then("the user creation shall {string}")
	public void the_user_creation_shall(String string) {
		string.equals(strings.remove(0));
	}

	@When("I initiate the browsing of all users")
	public void i_initiate_the_browsing_of_all_users() {
		users = KingdominoController.getAllUsers();
	}

	@Then("the users in the list shall be in the following alphabetical order:")
	public void the_users_in_the_list_shall_be_in_the_following_alphabetical_order(io.cucumber.datatable.DataTable dataTable) {
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			int i = Integer.valueOf(map.get("placeinlist")) - 1;

			if (map.get("name").equalsIgnoreCase(users.get(i).get("name")))
				continue;
			else
				fail();
		}
	}

	@Given("the following users exist with their game statistics:")
	public void the_following_users_exist_with_their_game_statistics(io.cucumber.datatable.DataTable dataTable) {
		// For other transformations you can register a DataTableType.
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			KingdominoController.addNewUser(map.get("name"));
			KingdominoController.setGameStatistics(map.get("name"), Integer.valueOf(map.get("playedGames")),Integer.valueOf(map.get("wonGames")));

		}
	}

	
	private Map<KingdominoController.UserState, Integer> myMap;
	@When("I initiate querying the game statistics for a user {string}")
	public void i_initiate_querying_the_game_statistics_for_a_user(String string) {
		myMap = KingdominoController.getGameStatistics(string);
	}
	
	@Then("the number of games played by {string} shall be {int}")
	public void the_number_of_games_played_by_shall_be(String string, Integer int1) {
	    assertEquals(myMap.get(KingdominoController.UserState.PLAYED_GAMES), int1);
	}

	@Then("the number of games won by {string} shall be {int}")
	public void the_number_of_games_won_by_shall_be(String string, Integer int1) {
		 assertEquals(myMap.get(KingdominoController.UserState.WON_GAMES), int1);
	}
    
//	@Then("the number of games played by and games won by the user shall be the following:")
//	public void the_number_of_games_played_by_and_games_won_by_the_user_shall_be_the_following(io.cucumber.datatable.DataTable dataTable) {
//		
//		List<Map<String, String>> valueMaps = dataTable.asMaps();
//		for (Map<String, String> map : valueMaps) {
//			int numberofgamesplayed = Integer.valueOf(map.get("playedGames"));
//			int numberofgameswon = Integer.valueOf(map.get("wonGames"));
//			
//			if(myMap.get(KingdominoController.UserState.PLAYED_GAMES) == numberofgamesplayed &&
//					myMap.get(KingdominoController.UserState.WON_GAMES) == numberofgameswon)
//				continue;
//			else
//				fail();
//		}
//
//	}

}
