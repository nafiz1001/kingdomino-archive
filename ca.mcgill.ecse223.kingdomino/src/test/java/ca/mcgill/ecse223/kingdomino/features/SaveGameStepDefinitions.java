package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.Property;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import helper.HelperFunctions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SaveGameStepDefinitions {
	@Given("the game is initialized for save game")
	public void the_game_is_initialized_for_save_game() {
		// Destroy previous game
		KingdominoApplication.getKingdomino().delete();
		// Intialize empty game
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		// creates all dominoes
		createAllDominoes(game);
		// creates the current draft
		Draft currentDraft = new Draft(DraftStatus.FaceUp, game);
		currentDraft.addIdSortedDomino(getdominoByID(game, 5));
		currentDraft.addIdSortedDomino(getdominoByID(game, 3));
		currentDraft.addIdSortedDomino(getdominoByID(game, 4));
		currentDraft.addIdSortedDomino(getdominoByID(game, 44));
		game.setCurrentDraft(currentDraft);
		// creates the next draft
		Draft nextDraft = new Draft(DraftStatus.FaceUp, game);
		nextDraft.addIdSortedDomino(getdominoByID(game, 34));
		nextDraft.addIdSortedDomino(getdominoByID(game, 37));
		nextDraft.addIdSortedDomino(getdominoByID(game, 29));
		nextDraft.addIdSortedDomino(getdominoByID(game, 22));
		game.setNextDraft(nextDraft);
		// sets next player
		game.setNextPlayer(game.getPlayer(3));
		// sets player selections
		// TODO
		game.getPlayer(3).setDominoSelection(
				new DominoSelection(game.getPlayer(3), game.getAllDomino(43), new Draft(DraftStatus.Sorted, game)));
		game.getPlayer(0).setDominoSelection(
				new DominoSelection(game.getPlayer(0), game.getAllDomino(33), new Draft(DraftStatus.Sorted, game)));
		game.getPlayer(1).setDominoSelection(
				new DominoSelection(game.getPlayer(1), game.getAllDomino(36), new Draft(DraftStatus.Sorted, game)));
		game.getPlayer(2).setDominoSelection(
				new DominoSelection(game.getPlayer(2), game.getAllDomino(28), new Draft(DraftStatus.Sorted, game)));

		Domino dominoToPlace = game.getAllDomino(1);
		Kingdom kingdom = game.getPlayer(0).getKingdom();
		DominoInKingdom domInKingdom = new DominoInKingdom(0, 1, kingdom, dominoToPlace);
		domInKingdom.setDirection(DirectionKind.Right);
		dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);

		Domino dominoToPlace2 = game.getAllDomino(2);
		Kingdom kingdom2 = game.getPlayer(1).getKingdom();
		DominoInKingdom domInKingdom2 = new DominoInKingdom(1, 0, kingdom2, dominoToPlace2);
		domInKingdom2.setDirection(DirectionKind.Right);
		dominoToPlace2.setStatus(DominoStatus.PlacedInKingdom);

		Domino dominoToPlace3 = game.getAllDomino(3);
		Kingdom kingdom3 = game.getPlayer(2).getKingdom();
		DominoInKingdom domInKingdom3 = new DominoInKingdom(0, -1, kingdom3, dominoToPlace3);
		domInKingdom3.setDirection(DirectionKind.Down);
		dominoToPlace3.setStatus(DominoStatus.PlacedInKingdom);
		KingdominoApplication.setKingdomino(kingdomino);
	}

	@Given("the game is still in progress")
	public void the_game_is_still_in_progress() {
		if (!KingdominoApplication.getKingdomino().getCurrentGame().hasAllDominos()) {
			throw new RuntimeException("cannot save a finished game!");
		}
	}

	@Given("no file named {string} exists in the filesystem")
	public void no_file_named_exists_in_the_filesystem(String string) {
		File desiredFile = new File(string);
		if (desiredFile.exists()) {
			desiredFile.delete();
		}
	}

	@When("the user initiates saving the game to a file named {string}")
	public void the_user_initiates_saving_the_game_to_a_file_named(String string) {
		KingdominoController.setFile(string);
	}

	@Then("a file named {string} shall be created in the filesystem")
	public void a_file_named_shall_be_created_in_the_filesystem(String string) {
		try {
			KingdominoController.setFile(string);
			KingdominoController.createSaveFile();
			KingdominoController.saveGame(KingdominoApplication.getKingdomino());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("error with creating file");

		}
	}

	@Given("the file named {string} exists in the filesystem")
	public void the_file_named_exists_in_the_filesystem(String string) {
		try {
			KingdominoController.createSaveFile();
		} catch (IOException e) {
			System.out.println("error creating file");
		} catch (Exception e) {
			System.out.println("error");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@When("the user agrees to overwrite the existing file named {string}")
	public void the_user_agrees_to_overwrite_the_existing_file_named(String string) {
		try {
			KingdominoController.createSaveFile();
		} catch (IOException e) {
			System.out.println("error creating file");
		} catch (Exception e) {
			System.out.println("error");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Then("the file named {string} shall be updated in the filesystem")
	public void the_file_named_shall_be_updated_in_the_filesystem(String string) {
		try {
			KingdominoController.createSaveFile();
			KingdominoController.saveGame(KingdominoApplication.getKingdomino());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//////////////////////// ------------------------------------------------------------------
	private void addDefaultUsersAndPlayers(Game game) {
		String[] userNames = { "User1", "User2", "User3", "User4" };
		for (int i = 0; i < userNames.length; i++) {
			User user = game.getKingdomino().addUser(userNames[i]);
			Player player = new Player(game);
			player.setUser(user);
			player.setColor(PlayerColor.values()[i]);
			Kingdom kingdom = new Kingdom(player);
			new Castle(0, 0, kingdom, player);
		}
	}

	private void createAllDominoes(Game game) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/alldominoes.dat"));
			String line = "";
			String delimiters = "[:\\+()]";
			while ((line = br.readLine()) != null) {
				String[] dominoString = line.split(delimiters); // {id, leftTerrain, rightTerrain, crowns}
				int dominoId = Integer.decode(dominoString[0]);
				TerrainType leftTerrain = getTerrainType(dominoString[1]);
				TerrainType rightTerrain = getTerrainType(dominoString[2]);
				int numCrown = 0;
				if (dominoString.length > 3) {
					numCrown = Integer.decode(dominoString[3]);
				}
				new Domino(dominoId, leftTerrain, rightTerrain, numCrown, game);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new java.lang.IllegalArgumentException(
					"Error occured while trying to read alldominoes.dat: " + e.getMessage());
		}
	}

	private TerrainType getTerrainType(String terrain) {
		switch (terrain) {
		case "W":
			return TerrainType.WheatField;
		case "F":
			return TerrainType.Forest;
		case "M":
			return TerrainType.Mountain;
		case "G":
			return TerrainType.Grass;
		case "S":
			return TerrainType.Swamp;
		case "L":
			return TerrainType.Lake;
		default:
			throw new java.lang.IllegalArgumentException("Invalid terrain type: " + terrain);
		}
	}

	private static Domino getdominoByID(Game game, int id) {
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}

}