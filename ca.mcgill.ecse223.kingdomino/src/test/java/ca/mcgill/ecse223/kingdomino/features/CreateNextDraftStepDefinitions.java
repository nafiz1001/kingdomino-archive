package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CreateNextDraftStepDefinitions {
	
	List<Domino> draftDominos;
	
	
	@Given("the game is initialized to create next draft")
	public void the_game_is_initialized_to_create_next_draft() {
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
	}
	
	@Given("there has been {int} drafts created")
	public void there_has_been_drafts_created(Integer int1) {
		
		for (int i = 0; i < int1; i++) {
			Draft draftsCreated = new Draft(DraftStatus.FaceUp, KingdominoApplication.getKingdomino().getCurrentGame());
		}
		System.out.println("Number of drafts created = " + KingdominoApplication.getKingdomino().getCurrentGame().getAllDrafts().size());
	}
	
	@Given("there is a current draft")
	public void there_is_a_current_draft() {
		Draft draft = KingdominoApplication.getKingdomino().getCurrentGame().getAllDrafts().get(1);
		draft.setDraftStatus(DraftStatus.Sorted);
	}
	
	@Given("there is a next draft")
	public void there_is_a_next_draft() {
		KingdominoApplication.getKingdomino().getCurrentGame().setNextDraft(
				new Draft(DraftStatus.FaceDown, KingdominoApplication.getKingdomino().getCurrentGame())
		);
	}
	
	@Given("the top {int} dominoes in my pile have the IDs {string}")
	public void the_top_dominoes_in_my_pile_have_the_IDs(Integer int1, String string) {
		
		int elements[];
		elements = KingdominoController.splitDraftString(int1, string);
//		String[] splitted; 
//		int[] elements = new int[int1];
//		splitted = string.split(","); 
//		for (int i = 0; i < elements.length; i++) {
//			elements[i] = Integer.parseInt(splitted[i]); 
//		}
//		System.out.println(Arrays.toString(elements));
		KingdominoController.createPileFromIDs(elements);
		KingdominoController.printPile(); 
	}
	
	@When("create next draft is initiated")
	public void create_next_draft_is_initiated() {
//		Draft nextDraft = new Draft(DraftStatus.FaceDown, KingdominoApplication.getKingdomino().getCurrentGame()); 
		// erase top part: call only controller in when's . SetUpNextDraft  method in controller
		// this should contain KingdominoApplication.getKingdomino.getCurrentGame.getDraft(newest/largest index)
		//populate that draft w/ dominoes 
		

		draftDominos = KingdominoController.setNextDraft(); 
	
	}
	
	@Then("a new draft is created from dominoes {string}")
	public void a_new_draft_is_created_from_dominoes(String string) {
		int[] elements;
		elements = KingdominoController.splitDraftString(4, string);
		int i = 0;
		for (Domino domino: draftDominos) {
			int dominoID = domino.getId();
			if (dominoID == elements[i]) {
				i++;
			} else {
				fail();
			}
			
		}
	}
	
	@Then("the next draft now has the dominoes {string}")
	public void the_next_draft_now_has_the_dominoes(String string) {
		int[] elements;
		elements = KingdominoController.splitDraftString(4, string);
		
		Draft draftDominoConfirm = KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft();
		
		for (int i = 0; i < 4; i++) {
			int dominoID = draftDominoConfirm.getIdSortedDomino(i).getId();

			if (dominoID == elements[i]) {
				continue;
			} else {
				break;
			}
		}
	}
	
	@Then("the dominoes in the next draft are face down")
	public void the_dominoes_in_the_next_draft_are_face_down() {
		DraftStatus checkStatus = KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getDraftStatus();
		System.out.println("Next Draft dominos are: " + checkStatus);
	}
	
	@Then("the top domino of the pile is ID {int}")
	public void the_top_domino_of_the_pile_is_ID(Integer int1) {
		Domino firstDomino = KingdominoApplication.getKingdomino().getCurrentGame().getTopDominoInPile(); 
		if (firstDomino.getId() == int1) {
			System.out.println("Top domino in pile " + firstDomino.getId());
		}
		else {
			System.out.println("Something went wrong, this is your current top domino " + firstDomino.getId());
		}
		
	}
	
	@Then("the former next draft is now the current draft")
	public void the_former_next_draft_is_now_the_current_draft() {
		Draft currentNextDraft = KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft();
		KingdominoApplication.getKingdomino().getCurrentGame().setCurrentDraft(currentNextDraft);

	}
	
	
	// second scenario 

	@Given("this is a {int} player game")
	public void this_is_a_player_game(Integer int1) {
		KingdominoApplication.getKingdomino().getCurrentGame().setNumberOfPlayers(int1); 
		System.out.println("Number of players = " + KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers());
	}
	
	@Then("the pile is empty")
	public void the_pile_is_empty() {
		Domino domino = KingdominoApplication.getKingdomino().getCurrentGame().getTopDominoInPile();
		if (domino == null) {
			System.out.println("The pile is empty");
		}
		else {
			fail();
		}
	}
	
	@Then("there is no next draft")
	public void there_is_no_next_draft() {
	    if (KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft() == null) {
	    	System.out.println("There is no next draft");
	    }
	    else {
	    	fail(); 
	    }
	}

	//temporary
	
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
	
	private Domino getdominoByID(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
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

	private DirectionKind getDirection(String dir) {
		switch (dir) {
		case "up":
			return DirectionKind.Up;
		case "down":
			return DirectionKind.Down;
		case "left":
			return DirectionKind.Left;
		case "right":
			return DirectionKind.Right;
		default:
			throw new java.lang.IllegalArgumentException("Invalid direction: " + dir);
		}
	}

	private DominoStatus getDominoStatus(String status) {
		switch (status) {
		case "inPile":
			return DominoStatus.InPile;
		case "excluded":
			return DominoStatus.Excluded;
		case "inCurrentDraft":
			return DominoStatus.InCurrentDraft;
		case "inNextDraft":
			return DominoStatus.InNextDraft;
		case "erroneouslyPreplaced":
			return DominoStatus.ErroneouslyPreplaced;
		case "correctlyPreplaced":
			return DominoStatus.CorrectlyPreplaced;
		case "placedInKingdom":
			return DominoStatus.PlacedInKingdom;
		case "discarded":
			return DominoStatus.Discarded;
		default:
			throw new java.lang.IllegalArgumentException("Invalid domino status: " + status);
		}
	}
}
