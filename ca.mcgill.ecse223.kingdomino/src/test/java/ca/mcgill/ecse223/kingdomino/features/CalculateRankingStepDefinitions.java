package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import helper.HelperFunctions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CalculateRankingStepDefinitions {

	public static boolean testplayerranking = false;

	List<String> playerRanking;

	@Given("the game is initialized for calculate ranking")
	public void the_game_is_initialized_for_calculate_ranking() {
		KingdominoApplication.getKingdomino().delete();
		// Initialize empty game
		Kingdomino kingdomino = new Kingdomino();
		KingdominoApplication.setKingdomino(kingdomino);
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		HelperFunctions.addDefaultUsersAndPlayers(game);
		HelperFunctions.createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);

		ResolveTiebreakStepDefinitions.testtiebreak = false;
	}

//     @Given("the players have the following two dominoes in their respective kingdoms:")
//     public void the_players_have_the_following_two_dominoes_in_their_respective_kingdoms(io.cucumber.datatable.DataTable dataTable) {
//
//     	List<Map<String, String>> valueMaps = dataTable.asMaps();
// 		for (Map<String, String> map : valueMaps) {
// //			// Get values from cucumber table
//			
// 			Integer posx1 = Integer.decode(map.get("posx1"));
// 			Integer posy1 = Integer.decode(map.get("posy1"));
// 			Integer domino1 = Integer.decode(map.get("domino1"));
// 			KingdominoController.addDominoToKingdom(map.get("player"), domino1, posx1, posy1, map.get("dominodir1")); 
//			
// 			Integer posx2 = Integer.decode(map.get("posx2"));
// 			Integer posy2 = Integer.decode(map.get("posy2"));
// 			Integer domino2 = Integer.decode(map.get("domino2"));
// 			KingdominoController.addDominoToKingdom(map.get("player"), domino2, posx2, posy2, map.get("dominodir2")); 
//			
//// 			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom); //don't need here b/c its already being done in controller. We're placing the domino in a kingdom
// 		}
//		
// 		testplayerranking = true;
//     }

	@Given("the players have no tiebreak")
	public void the_players_have_no_tiebreak() {

	}

	@When("calculate ranking is initiated")
	public void calculate_ranking_is_initiated() {
		if (ResolveTiebreakStepDefinitions.testtiebreak) {
			KingdominoController.resolveTiebreak();
		} else {
			playerRanking = KingdominoController.calculateRanking();
		}

	}

	@Then("player standings shall be the followings:")
	public void player_standings_shall_be_the_followings(io.cucumber.datatable.DataTable dataTable) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		System.err.println(valueMaps);
		for (Map<String, String> map : valueMaps) {

			String playerName = map.get("player");
			int standing = Integer.decode(map.get("standing"));
			for (Player player : game.getPlayers()) {
				if (player.getUser().getName().equals(playerName)) {
					if (player.getCurrentRanking() != standing) {
						System.out.println("player " + playerName + " should be ranked " + standing);
						System.out.println("player " + player.getUser().getName() +" is ranked " + player.getCurrentRanking());
						throw new RuntimeException("Wrong standing");
						
					}
				}
			}

		}
//		List<Map<String, String>> valueMaps = dataTable.asMaps();
//		System.err.println(valueMaps);
//		for (Map<String, String> map : valueMaps) {
//			String color = map.get("player");
//			Integer standing = Integer.decode(map.get("standing"));
//			assertEquals(playerRanking.get(standing - 1), color);
//		}
	}

}
