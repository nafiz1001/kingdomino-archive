package ca.mcgill.ecse223.kingdomino.features;

import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Property;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import helper.HelperFunctions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ResolveTiebreakStepDefinitions {
	public static boolean testtiebreak = false;
	
	@Given("the game is initialized for resolve tiebreak")
	public void the_game_is_initialized_for_resolve_tiebreak() {
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
		
		testtiebreak = true;
	}

	@Given("the players have the following two dominoes in their respective kingdoms:")
	public void the_players_have_the_following_two_dominoes_in_their_respective_kingdoms(
			io.cucumber.datatable.DataTable dataTable) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		int i = 0;
		System.err.println(valueMaps);
		for (Map<String, String> map : valueMaps) {
			String player = map.get("player");
			Integer domino1ID = Integer.decode(map.get("domino1"));
			String domino1Dir = map.get("dominodir1");
			Integer domino1X = Integer.decode(map.get("posx1"));
			Integer domino1Y = Integer.decode(map.get("posy1"));

			Integer domino2ID = Integer.decode(map.get("domino2"));
			String domino2Dir = map.get("dominodir2");
			Integer domino2X = Integer.decode(map.get("posx2"));
			Integer domino2Y = Integer.decode(map.get("posy2"));

			game.getPlayer(i).getUser().setName(player);

			Domino dominoToPlace1 = HelperFunctions.getdominoByID(domino1ID);
			Kingdom kingdom1 = game.getPlayer(i).getKingdom();
			DominoInKingdom domInKingdom = new DominoInKingdom(domino1X, domino1Y, kingdom1, dominoToPlace1);
			switch (domino1Dir) {
			case "up":
				domInKingdom.setDirection(DirectionKind.Up);
				break;
			case "down":
				domInKingdom.setDirection(DirectionKind.Down);
				break;
			case "left":
				domInKingdom.setDirection(DirectionKind.Left);
				break;
			case "right":
				domInKingdom.setDirection(DirectionKind.Right);
				break;
			default:
				break;
			}
			dominoToPlace1.setStatus(DominoStatus.PlacedInKingdom);

			Domino dominoToPlace2 = HelperFunctions.getdominoByID(domino2ID);
			Kingdom kingdom2 = game.getPlayer(i).getKingdom();
			DominoInKingdom domInKingdom2 = new DominoInKingdom(domino2X, domino2Y, kingdom2, dominoToPlace2);
			switch (domino2Dir) {
			case "up":
				domInKingdom2.setDirection(DirectionKind.Up);
				break;
			case "down":
				domInKingdom2.setDirection(DirectionKind.Down);
				break;
			case "left":
				domInKingdom2.setDirection(DirectionKind.Left);
				break;
			case "right":
				domInKingdom2.setDirection(DirectionKind.Right);
				break;
			default:
				break;
			}
			dominoToPlace2.setStatus(DominoStatus.PlacedInKingdom);
			i++;
		}

	}

//	@When("calculate ranking is initiated")
//	public void calculate_ranking_is_initiated() {
//		KingdominoController.resolveTiebreak();
//	}

	@Then("player standings should be the followings:")
	public void player_standings_should_be_the_followings(io.cucumber.datatable.DataTable dataTable) {
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
	}

}