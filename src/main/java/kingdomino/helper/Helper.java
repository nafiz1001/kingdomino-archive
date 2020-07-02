package kingdomino.helper;

import java.io.*;

import kingdomino.KingdominoApplication;
import kingdomino.model.*;
import kingdomino.model.Domino.DominoStatus;
import kingdomino.model.DominoInKingdom.DirectionKind;
import kingdomino.model.Player.PlayerColor;

public class Helper {
    
    //*********************************************************************/
    //  Add method for representing @When here 
    // (if it's not provided here and if it's not a controller method)
    //*********************************************************************/
    
    public static void calculate_bonus_score_is_initiated() {
        // Write code here that turns the phrase above into concrete actions
    }

    public static void calculate_player_score_is_initiated() {
        // Write code here that turns the phrase above into concrete actions
    }

    public static void calculate_property_attributes_is_initiated() {
        // Write code here that turns the phrase above into concrete actions
    }

    public static void calculate_ranking_is_initiated() {
        // Write code here that turns the phrase above into concrete actions
    }

    public static void create_next_draft_is_initiated() {
        // Write code here that turns the phrase above into concrete actions
    }

    public static void the_properties_of_the_player_are_identified() {
        // Write code here that turns the phrase above into concrete actions
    }

    public static void each_tile_placement_is_valid() {
        // Write code here that turns the phrase above into concrete actions
    }

    public static void the_game_result_is_not_yet_final() {
        // Write code here that turns the phrase above into concrete actions
    }

    public static void the_ordering_of_the_dominoes_in_the_next_draft_is_initiated() {
        // Write code here that turns the phrase above into concrete actions
    }

    public static void the_revealing_of_the_dominoes_in_the_next_draft_is_initiated() {
        // Write code here that turns the phrase above into concrete actions
    }

    public static void i_initiate_to_arrange_the_domino_in_the_fixed_order(String string) {
        // Write code here that turns the phrase above into concrete actions
    }

    public static void next_draft_is_sorted() {
        // Write code here that turns the phrase above into concrete actions
    }

    public static void next_draft_is_revealed() {
        // Write code here that turns the phrase above into concrete actions
    }

    public static void reveal_first_draft_is_initiated() {
        // Write code here that turns the phrase above into concrete actions
    }

    public static void check_castle_adjacency_is_initiated() {
        // Write code here that turns the phrase above into concrete actions
    }

    public static void validation_of_the_grid_size_is_initiated() {
        // Write code here that turns the phrase above into concrete actions
    }

    public static void check_current_preplaced_domino_adjacency_is_initiated() {
        // Write code here that turns the phrase above into concrete actions
    }

    public static void check_current_preplaced_domino_overlapping_is_initiated() {
        // Write code here that turns the phrase above into concrete actions
    }

    public static class Controller {
        
        //*********************************************************************/
        //  Add method for representing @When here if it's a controller method 
        // (and if it's not provided here)
        //*********************************************************************/
        
        public static void i_initiate_the_browsing_of_all_dominoes() {
            // Write code here that turns the phrase above into concrete actions
        }
    
        public static void i_provide_a_domino_ID(Integer int1) {
            // Write code here that turns the phrase above into concrete actions
        }
    
        public static void i_initiate_the_browsing_of_all_dominoes_of_terrain_type(String string) {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void the_current_player_places_his_her_domino() {
            // Write code here that turns the phrase above into concrete actions
        }
    
        public static void the_current_player_discards_his_her_domino() {
            // Write code here that turns the phrase above into concrete actions
        }
    
        public static void current_player_chooses_to_place_king_on(Integer int1) {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void start_of_the_game_is_initiated() {
            // Write code here that turns the phrase above into concrete actions
        }
    
        public static void i_initiate_loading_a_saved_game_from(String string) {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void removes_his_king_from_the_domino(String string, Integer int1) {
            // Write code here that turns the phrase above into concrete actions
        }
    
        public static void requests_to_move_the_domino(String string, String string2) {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void requests_to_place_the_selected_domino(String string, Integer int1) {
            // Write code here that turns the phrase above into concrete actions
        }
    
        public static void i_provide_my_username_and_initiate_creating_a_new_user(String string) {
            // Write code here that turns the phrase above into concrete actions
        }
    
        public static void i_initiate_the_browsing_of_all_users() {
            // Write code here that turns the phrase above into concrete actions
        }
    
        public static void i_initiate_querying_the_game_statistics_for_a_user(String string) {
            // Write code here that turns the phrase above into concrete actions
        }
    
        public static void requests_to_rotate_the_domino_with(String string, String string2) {
            // Write code here that turns the phrase above into concrete actions
        }
    
        public static void the_user_initiates_saving_the_game_to_a_file_named(String string) {
            // Write code here that turns the phrase above into concrete actions
        }
    
        public static void the_user_agrees_to_overwrite_the_existing_file_named(String string) {
            // Write code here that turns the phrase above into concrete actions
        }
    
        public static void the_player_completes_his_her_domino_selection(String string) {
            // Write code here that turns the phrase above into concrete actions
        }
    
        public static void the_player_completes_his_her_domino_selection_of_the_game(String string) {
            // Write code here that turns the phrase above into concrete actions
        }
    
        public static void set_game_options_is_initiated() {
            // Write code here that turns the phrase above into concrete actions
        }
    
        public static void the_number_of_players_is_set_to(Integer int1) {
            // Write code here that turns the phrase above into concrete actions
        }
    
        public static void harmony_selected_as_bonus_option(String string) {
            // Write code here that turns the phrase above into concrete actions
        }
    
        public static void middle_Kingdom_selected_as_bonus_option(String string) {
            // Write code here that turns the phrase above into concrete actions
        }
    
        public static void the_shuffling_of_dominoes_is_initiated() {
            // Write code here that turns the phrase above into concrete actions
        }
        
        public static void starting_a_new_game_is_initiated() {
            // Write code here that turns the phrase above into concrete actions
        }
    }
    
    //*********************************************************************/
    //  Everything else
    //*********************************************************************/

    public static void addDefaultUsersAndPlayers(Game game) {
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

	public static void createAllDominoes(Game game) {
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

	public static Domino getdominoByID(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}

	public static TerrainType getTerrainType(String terrain) {
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

	public static DirectionKind getDirection(String dir) {
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

	public static DominoStatus getDominoStatus(String status) {
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
    
    /**
     * Initalizes kingdomino and current game with 4 players.
     * Great for tesing!
     * @author Nafiz Islam (nafiz1001)
     * @return the current game
     */
    public static Game initializeEmptyGame() {
        Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		Helper.addDefaultUsersAndPlayers(game);
		Helper.createAllDominoes(game);
        KingdominoApplication.setKingdomino(kingdomino);
        
        return game;
    }
}