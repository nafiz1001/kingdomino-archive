package ca.mcgill.ecse223.kingdomino.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.BonusOption;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.User;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.BonusOption;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Property;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.User;
import helper.HelperFunctions;
import helper.MyProperty;
import helper.Tile;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.Property;

public class KingdominoController {
	/**
	 * @param textFileOrigin static String. indicates the path to the file whether
	 *                       for save or for load
	 */
	private static String textFileOrigin = "savedGameOutput.mov";

	public static final String HARMONY = "Harmony"; // Constant created to make my code neater @Mohammad Salman Mesam
	public static final String MIDDLE_KINGDOM = "Middle Kingdom"; // Constant created to make my code neater @Mohammad
																	// Salman Mesam

	/**
	 * @author Mohammad Salman Mesam Feature #1 : This method sets the number of
	 *         players Gherkin Feature : SetGameOptions
	 * @param numberOfPlayers
	 */
	public static void setPlayerNumber(int numberOfPlayers) {
		KingdominoApplication.getKingdomino().getCurrentGame().setNumberOfPlayers(numberOfPlayers);
	}

	/**
	 * Feature #1 : This method is to check for whether Middle Kingdom or Harmony
	 * bonus option is selected Gherkin Feature : SetGameOptions
	 * 
	 * @author Mohammad Salman Mesam
	 * @param bonusOptionName
	 * @return boolean indicating wheather or not the specific bonus option is
	 *         selected or not
	 */
	public static boolean currentGameHasBonusOption(String bonusOptionName) {
		List<BonusOption> bonusOptions = KingdominoApplication.getKingdomino().getCurrentGame()
				.getSelectedBonusOptions();
		for (int i = 0; i < bonusOptions.size(); ++i) {
			if (bonusOptions.get(i).getOptionName().equalsIgnoreCase(bonusOptionName))
				return true;
		}

		return false;
	}

	public static boolean hasUser(String username) {
		for (User user : KingdominoApplication.getKingdomino().getUsers()) {
			if (username.equalsIgnoreCase(user.getName()))
				return true;
		}
		return false;
	}

	/**
	 * Feature #2 : This method is to add a new user and it ensures all input
	 * validation Gherkin Feature : ProvideUserProfile
	 * 
	 * @author Mohammad Salman Mesam
	 * @param username
	 */

	public static void addNewUser(String username) throws IllegalArgumentException {
		if (hasUser(username)) {
			throw new IllegalArgumentException("Name exists already: " + username);
		}

		if (username.length() == 0)
			throw new IllegalArgumentException("Username cannot be empty");

		char[] chars = new char[username.length()];
		username.getChars(0, username.length(), chars, 0);

		for (char c : chars) {
			if (Character.isAlphabetic(c) || Character.isDigit(c)) {
				continue;
			} else {
				throw new IllegalArgumentException("Name contains illegal character: " + c);
			}
		}

		KingdominoApplication.getKingdomino().addUser(new User(username, KingdominoApplication.getKingdomino()));

	}

	/**
	 * method changes the textFileOrigin to match the desired file to write to/read
	 * from
	 * 
	 * @author Delia Bretan
	 * @param fileOrigin String. location of desired file to read/write
	 */
	public static void setFile(String fileOrigin) {
		textFileOrigin = fileOrigin;
	}

	/**
	 * method creates an empty file at the specified location textFileOrigin. method
	 * deletes the file if it already exists & then creates it anew. prevents
	 * overriding problems obtaining dominoInKingdom information code inspired from
	 * helper methods
	 * 
	 * @author Delia Bretan
	 * @throws Exception. if file cannot be created
	 */
	public static void createSaveFile() throws Exception {
		try {
			File saving = new File(textFileOrigin);
			if (saving.exists()) {
				saving.delete();
			}
			saving.createNewFile();
		} catch (Exception e) {
			throw new Exception("file cannot be created");
		}
	}

	/**
	 * method saves the current game in a file
	 * 
	 * @author Delia Bretan
	 * @param kingdomino Kingdomino. the current kingdomino instance
	 */
	public static void saveGame(Kingdomino kingdomino) {
		// obtaining the player's selection
		String finalSave = "C: ";
		for (Player player : kingdomino.getCurrentGame().getPlayers()) {
			if (player.getDominoSelection() != null) {
				finalSave += player.getDominoSelection().getDomino().getId() + ", ";
			} else {
				finalSave += ", ";
			}
		}
		// obtaining unclaimed dominoes
		finalSave = finalSave.substring(0, finalSave.length() - 2);
		finalSave += "\nU: ";
		for (Domino selectedDomino : kingdomino.getCurrentGame().getNextDraft().getIdSortedDominos()) {
			boolean isSelected = false;
			for (Player player : kingdomino.getCurrentGame().getPlayers()) {
				if (player.getDominoSelection().getDomino().getId() == selectedDomino.getId()) {
					isSelected = true;
					break;
				}
			}
			if (!isSelected) {
				finalSave += selectedDomino.getId() + ", ";
			}
		}
		finalSave = finalSave.substring(0, finalSave.length() - 2);
		finalSave += "\n";

		// obtaining player information
		for (Player player : kingdomino.getCurrentGame().getPlayers()) {
			finalSave += player.getUser().getName() + ": ";
			// inspired from Helper Methods
			for (KingdomTerritory territory : player.getKingdom().getTerritories()) {
				if (territory instanceof DominoInKingdom) {
					DominoInKingdom dominoInKingdom = (DominoInKingdom) territory;
					Domino domino = dominoInKingdom.getDomino();

					String direction = "U";
					switch (dominoInKingdom.getDirection()) {
					case Up:
						direction = "U";
						break;
					case Down:
						direction = "D";
						break;
					case Left:
						direction = "L";
						break;
					case Right:
						direction = "R";
						break;

					}

					finalSave += domino.getId() + "@(" + dominoInKingdom.getX() + "," + dominoInKingdom.getY() + ","
							+ direction + "), ";
				}
			}
			finalSave = finalSave.substring(0, finalSave.length() - 2);
			finalSave += "\n";
		}

		// writing the information to the file
		FileWriter writer;
		try {
			writer = new FileWriter(textFileOrigin);
			writer.write(finalSave);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Feature #2 : This method is to add a new user and it ensures all input
	 * validation Gherkin Feature : ProvideUserProfile
	 * 
	 * @author Mohammad Salman Mesam
	 * @param username
	 */

	/**
	 * The method loads a game from a save file. Throws an exception if dominoes in
	 * player kingdoms are not placed correctly or if a domino in a player's kingdom
	 * is equal to the currently claimed domino
	 * 
	 * @author Delia Bretan
	 * @param kingdomino Kingdomino. the current kingdomino instace
	 * @return true if method is successful.
	 * @throws Exception.
	 */
	public static boolean loadGame(Kingdomino kingdomino) throws Exception {
		Game game = new Game(48, kingdomino);
		HelperFunctions.createAllDominoes(game);
		// updating kingdomino
		kingdomino.setCurrentGame(game);

		// holds the index of the current player
		int currentUserIndex = 0;
		// reads info from save file
		StringBuilder contentBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(textFileOrigin))) {

			String sCurrentLine;
			ArrayList<Integer> tempU = new ArrayList<Integer>();
			ArrayList<Integer> tempC = new ArrayList<Integer>();
			while ((sCurrentLine = br.readLine()) != null) {
				contentBuilder.append(sCurrentLine).append("\n");
				char indicatorOfNextAction = sCurrentLine.charAt(0);

				// reads line with the claimed pieces info
				if (indicatorOfNextAction == 'C') {
					sCurrentLine.trim();
					String[] tokensC = sCurrentLine.split("C\\:|\\,");

					int i = 0;
					// creates new users and adds the id of the claimed domino in the tempC list
					for (String tokenValue : tokensC) {
						if (!tokenValue.equals("")) {
							int value = Integer.parseInt(tokenValue.substring(1));
							// add to tempC list
							tempC.add(value);
							// creates new users
							User user = game.getKingdomino().addUser(tokenValue);
							Player player = new Player(game);
							player.setUser(user);
							player.setColor(PlayerColor.values()[i]);
							Kingdom kingdom = new Kingdom(player);
							new Castle(0, 0, kingdom, player);
						}
					}

					// reads line with unclaimed dominoes
				} else if (indicatorOfNextAction == 'U') {
					sCurrentLine.trim();
					String[] tokensU = sCurrentLine.split("U\\:|\\,");
					// adds id of the unclaimed domino to the tempU list
					for (String tokenValue : tokensU) {
						if (!tokenValue.equals("")) {
							int value = Integer.parseInt(tokenValue.substring(1));
							tempU.add(value);
						}
					}
					// create 2 drafts: current and next
					Draft currentDraft = new Draft(DraftStatus.FaceUp, game);
					Draft nextdraft = new Draft(DraftStatus.Sorted, game);

					// assigns dominoes to either the current draft or the next draft
					if (tempU.size() != tempC.size()) {
						boolean oldBatch = false;
						for (int c = 0; c < tempC.size(); c++) {
							if (c == (tempC.size() - tempU.size())) {
								for (int u = 0; u < tempU.size(); u++) {
									nextdraft.addIdSortedDomino(getdominoByID(game, tempU.get(u)));
									oldBatch = true;
								}
							}
							// assigns users the claimed dominoes
							if (oldBatch) {
								Domino nowPlacing = getdominoByID(game, tempC.get(c));
								game.getPlayer(c).setDominoSelection(
										new DominoSelection(game.getPlayer(c), nowPlacing, currentDraft));
							} else {
								Domino nowPlacing = getdominoByID(game, tempC.get(c));
								game.getPlayer(c).setDominoSelection(
										new DominoSelection(game.getPlayer(c), nowPlacing, nextdraft));
							}

						}
						// case that no domino has been claimed
					} else {
						// assigns users the claimed dominoes
						for (int c = 0; c < tempC.size(); c++) {
							Domino currentDomino = getdominoByID(game, tempC.get(c));
							game.getPlayer(c).setDominoSelection(
									new DominoSelection(game.getPlayer(c), currentDomino, currentDraft));
						}
						for (int u = 0; u < tempU.size(); u++) {
							nextdraft.addIdSortedDomino(getdominoByID(game, tempU.get(u)));
						}
					}

					// sets current player
					if (tempU.size() == game.getPlayers().size()) {
						Player currentPlayer = game.getPlayer(0);
						int smallId = currentPlayer.getDominoSelection().getDomino().getId();

						for (int index = 1; index < game.getPlayers().size(); index++) {
							if (smallId > game.getPlayer(index).getDominoSelection().getDomino().getId()) {
								currentPlayer = game.getPlayer(index);
								smallId = currentPlayer.getDominoSelection().getDomino().getId();
							}
						}
						game.setCurrentPlayer(currentPlayer);

					} else {
						// sets current player
						game.setCurrentPlayer(game.getPlayer(tempC.size() - tempU.size()));
					}
					// sets current draft
					game.setCurrentDraft(currentDraft);
					// sets next draft
					game.setNextDraft(nextdraft);
					// update player's info about user's name; kingdom
				} else {
					// obtaining domino in kindom information (xpos, ypos, direction)
					sCurrentLine.trim();
					String[] tokensPlayer = sCurrentLine.split(":");
					game.getPlayer(currentUserIndex).getUser().setName(tokensPlayer[0]);

					String sCurrentKingdom = tokensPlayer[1];
					String[] tokensPlayerKingdom = sCurrentKingdom.split("\\)\\,");

					// table in order to keep track of domino position validity
					boolean[][] validKingdom = new boolean[9][9];
					// castle coordinates
					validKingdom[4][4] = true;
					for (String currentDominoCoordinates : tokensPlayerKingdom) {
						String[] tokensDomino = currentDominoCoordinates.trim().split("\\@");
						String dominoID = tokensDomino[0].trim();
						Domino currentDomino = getdominoByID(game, Integer.parseInt(dominoID));

						String[] tokensXYDir = tokensDomino[1].trim().split("\\(|\\,|\\)");
						int posx = Integer.parseInt(tokensXYDir[1].trim());
						int posy = Integer.parseInt(tokensXYDir[2].trim());
						char dir = tokensXYDir[3].trim().charAt(0);

						DirectionKind direction = DirectionKind.Up;
						switch (dir) {
						case 'U':
							direction = DirectionKind.Up;
							break;
						case 'D':
							direction = DirectionKind.Down;
							break;
						case 'L':
							direction = DirectionKind.Left;
							break;
						case 'R':
							direction = DirectionKind.Right;
							break;
						default:
							break;
						}

						// updating dominoInKingdom information
						DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy,
								game.getPlayer(currentUserIndex).getKingdom(), currentDomino);
						domInKingdom.setDirection(direction);
						// checking if no overlap or missing neighbours
						checkIfKingdomIsValid(validKingdom, domInKingdom, game.getPlayer(currentUserIndex));
						// change domino status
						currentDomino.setStatus(DominoStatus.PlacedInKingdom);

						// if a domino in kingdom has the same id as a claimed domino, throw exception!
						// & destroy everything
						if (Integer.parseInt(dominoID.trim()) == game.getPlayer(currentUserIndex).getDominoSelection()
								.getDomino().getId()) {
							game.delete();
							throw new Exception("Invalid save file, my boi");
						}
					}
					// updating user index
					currentUserIndex++;
				}
			}
		} catch (IOException e) {
			game.delete();
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * The method will check if a domino is placed correctly. Throws exception if
	 * problems occur
	 * 
	 * @author Delia Bretan
	 * @param kingdomGrid   boolean[][]. table containing the location of the placed
	 *                      dominoes in kingdom
	 * @param domInKingdom. the current domino that is being placed in kingdom
	 * @return boolean[][]. the updated table containing the location of the placed
	 *         dominoes in kingdom
	 * @throws Exception. if domino is not correctly placed
	 */
	private static boolean[][] checkIfKingdomIsValid(boolean[][] kingdomGrid, DominoInKingdom domInKingdom,
			Player player) throws Exception {
		// transforming coordinated from -4 to 4 system to 0 to 8 system
		int leftX = domInKingdom.getX() + 4;
		int leftY = 4 - domInKingdom.getY();
		int rightX;
		int rightY;

		// updating right end position according to direction
		switch (domInKingdom.getDirection()) {
		case Right:
			rightY = leftY;
			rightX = leftX + 1;
			break;
		case Left:
			rightY = leftY;
			rightX = leftX - 1;
			break;
		case Up:
			rightY = leftY - 1;
			rightX = leftX;
			break;
		case Down:
			rightY = leftY + 1;
			rightX = leftX;
			break;
		default:
			rightY = leftY;
			rightX = leftX;
			break;
		}
		try {
			// check if domino to place has neighbours
			if (!checkAroundKingdomLoading(kingdomGrid, leftX, leftY, rightX, rightY, domInKingdom)) {
				throw new Exception("Invalid save file, my boi");

			} else {
				// check for overlap left
				if (!kingdomGrid[leftY][leftX]) {
					kingdomGrid[leftY][leftX] = true;

				} else {
					throw new Exception("Invalid save file, my boi");
				}
				// check for overlap right
				if (!kingdomGrid[rightY][rightX]) {
					kingdomGrid[rightY][rightX] = true;
				} else {
					throw new Exception("Invalid save file, my boi");
				}
			}

		} catch (Exception e) {
			throw new Exception("Invalid save file, my boi");
		}
		return kingdomGrid;
	}

	/**
	 * Method checks if the domino has correct neighbours(according to terrain
	 * type). throws exception if not
	 * 
	 * @author Delia Bretan
	 * @param kingdomGrid     boolean[][]. table containing the location of the
	 *                        placed dominoes in kingdom
	 * @param leftX           int. position X of left side of domino
	 * @param leftY           int. position Y of left side of domino
	 * @param rightX          int. position X of right side of domino
	 * @param rightY          int. position Y of right side of domino
	 * @param player          Player. current player in the game
	 * @param dominoInKingdom DominoInKingdom. domino in kingdom that will be
	 *                        discarded or not
	 * @return boolean. true if domino has correct neighbour
	 */

	private static boolean checkAroundDomino(boolean[][] kingdomGrid, int leftX, int leftY, int rightX, int rightY,
			Player player, DominoInKingdom dominoInKingdom) {
		boolean isPlacedCorrectly = false;
		// left side of domino that we want to place
		for (int leftIndexY = leftY - 1; leftIndexY <= leftY + 1; leftIndexY++) {
			for (int leftIndexX = leftX - 1; leftIndexX <= leftX + 1; leftIndexX++) {
				if (((leftIndexY == leftY - 1) && (leftIndexX == leftX - 1))
						|| ((leftIndexY == leftY - 1) && (leftIndexX == leftX + 1))
						|| ((leftIndexY == leftY + 1) && (leftIndexX == leftX - 1))
						|| ((leftIndexY == leftY + 1) && (leftIndexX == leftX + 1))) {
					// invalid cases, discard (corners)
				} else {
					try {
						// tile exists
						if (kingdomGrid[leftIndexY][leftIndexX]) {
							KingdomTerritory k = HelperFunctions.getTerritoryAtLocation(leftIndexX - 4, 4 - leftIndexY,
									player.getKingdom());
							if (k instanceof DominoInKingdom) {
								DominoInKingdom newK = (DominoInKingdom) k;
								// dealing with left side of domino
								if ((newK.getX() == leftIndexX - 4) && (newK.getY() == 4 - leftIndexY)) {
									if (newK.getDomino().getLeftTile()
											.equals(dominoInKingdom.getDomino().getLeftTile())) {
										isPlacedCorrectly = true;
									}
								} else {
									if (newK.getDomino().getRightTile()
											.equals(dominoInKingdom.getDomino().getLeftTile())) {
										isPlacedCorrectly = true;
									}
								}
							} else {
								isPlacedCorrectly = true;
							}

						}
					} catch (Exception e) {

					}
				}
			}
		}
		// right side of domino we want to place
		for (int rightIndexY = rightY - 1; rightIndexY <= rightY + 1; rightIndexY++) {
			for (int rightIndexX = rightX - 1; rightIndexX <= rightX + 1; rightIndexX++) {
				if (((rightIndexY == rightY - 1) && (rightIndexX == rightX - 1))
						|| ((rightIndexY == rightY - 1) && (rightIndexX == rightX + 1))
						|| ((rightIndexY == rightY + 1) && (rightIndexX == rightX - 1))
						|| ((rightIndexY == rightY + 1) && (rightIndexX == rightX + 1))) {
					// invalid cases, discard (corners)
				} else {
					try {
						// tile exists
						if (kingdomGrid[rightIndexY][rightIndexX]) {
							KingdomTerritory k = HelperFunctions.getTerritoryAtLocation(rightIndexX - 4,
									4 - rightIndexY, player.getKingdom());
							if (k instanceof DominoInKingdom) {
								DominoInKingdom newK = (DominoInKingdom) k;
								// dealing with left side of domino
								if ((newK.getX() == rightIndexX - 4) && (newK.getY() == 4 - rightIndexY)) {
									if (newK.getDomino().getLeftTile()
											.equals(dominoInKingdom.getDomino().getRightTile())) {
										isPlacedCorrectly = true;
									}
								}
								// right ride
								else {
									if (newK.getDomino().getRightTile()
											.equals(dominoInKingdom.getDomino().getRightTile())) {
										isPlacedCorrectly = true;
									}
								}
							} else {
								isPlacedCorrectly = true;
							}

						}
					} catch (Exception e) {

					}
				}
			}
		}
		return isPlacedCorrectly;
	}

	/**
	 * Method checks if neighbours exists. (doesn't take in account terrain type)
	 * 
	 * @author Delia Bretan
	 * @param kingdomGrid     boolean[][]. table containing the location of the
	 *                        placed dominoes in kingdom
	 * @param leftX           int. position X of left side of domino
	 * @param leftY           int. position Y of left side of domino
	 * @param rightX          int. position X of right side of domino
	 * @param rightY          int. position Y of right side of domino
	 * @param dominoInKingdom DominoInKingdom. domino in kingdom that will be
	 *                        discarded or not
	 * @return boolean. true if domino has neighbours
	 */
	private static boolean checkAroundKingdomLoading(boolean[][] kingdomGrid, int leftX, int leftY, int rightX,
			int rightY, DominoInKingdom dominoInKingdom) {

		int beginY = 0;
		int beginX = 0;
		int endY = 0;
		int endX = 0;
		// updates the beginning and end coordinates according to direction
		switch (dominoInKingdom.getDirection()) {
		case Up:
			beginY = rightY - 1;
			beginX = rightX - 1;
			endY = leftY;
			endX = beginX + 2;
			break;
		case Down:
			beginY = leftY - 1;
			beginX = leftX - 1;
			endY = rightY + 1;
			endX = beginX + 2;
			break;
		case Left:
			beginY = rightY - 1;
			beginX = rightX - 1;
			endX = leftX + 1;
			endY = leftY + 1;
			break;
		case Right:
			beginY = leftY - 1;
			beginX = leftX - 1;
			endY = beginY + 2;
			endX = rightX + 1;
			break;
		default:
			break;
		}

		// true if a neighbour has been found
		boolean hasNeighbour = false;

		// check every adjacent tile
		for (int y = beginY; y <= endY; y++) {
			for (int x = beginX; x <= endX; x++) {
				// discard corners since corners dont count
				if ((y == beginY && x == beginX) || (y == beginY && x == endX) || (y == endY && x == beginX)
						|| (y == endY && x == endX)) {

				} else {
					try {
						// test for neighbour presence
						if (kingdomGrid[y][x]) {
							hasNeighbour = true;

						}
					} catch (Exception e) {

					}
				}

			}
			if (hasNeighbour) {
				break;
			}
		}

		return hasNeighbour;
	}

	/**
	 * Feature #2 : This method checks if the username being entered exists in the
	 * list of users or not Gherkin Feature : ProvideUserProfile
	 * 
	 * @author Mohammad Salman Mesam
	 * @param username
	 * @return boolean indicating if a user with the username already exists or not
	 *         in the list of users or not
	 */

	/**
	 * @param noNoLocation int[][]. keeps tabs on the grid size
	 *                     (minX,maxX,minY,maxY)
	 */
	private static int[][] noNoLocation = new int[2][2];

	/**
	 * Mathod will check if a domino can be discarded and sets its status to
	 * <Discarded>
	 * 
	 * @author Delia Bretan
	 * @param player Player. The player that wants to discard domino
	 * @return boolean. true if domino is discarded
	 */
	public static boolean discardDomino(Player player) {
		// set the grid size to 8 * 8, centered at castle (default)
		noNoLocation[0][0] = 0;
		noNoLocation[0][1] = 8;
		noNoLocation[1][0] = 0;
		noNoLocation[1][1] = 8;

		// creates an empty kingdomGrid to store occupied tiles
		boolean[][] kingdomGrid = new boolean[9][9];
		kingdomGrid[4][4] = true;

		// updates kingdomGrid with all the dominoes (except the last domino, the one to
		// be discarded)
		for (int index = 1; index < player.getKingdom().getTerritories().size() - 1; index++) {
			if (player.getKingdom().getTerritories().get(index) instanceof DominoInKingdom) {
				DominoInKingdom dominoInKingdom = (DominoInKingdom) player.getKingdom().getTerritories().get(index);
				Domino domino = dominoInKingdom.getDomino();
				try {
					kingdomGrid = buildKingdom(kingdomGrid, dominoInKingdom);
				} catch (Exception e) {
				}
			}
		}
		// gets the domino to be discarded
		DominoInKingdom toBeDiscardedKingdom = HelperFunctions.getPreplacedDominoInKingdom(player);
		Domino toBeDiscarded = toBeDiscardedKingdom.getDomino();

		// if domino to be discarded can be physically be placed in game(any direction),
		// return true
		boolean directionFound = false;
		for (DirectionKind direction : DirectionKind.values()) {
			for (int leftY = noNoLocation[1][0]; leftY <= noNoLocation[1][1]; leftY++) {
				for (int leftX = noNoLocation[0][0]; leftX <= noNoLocation[0][1]; leftX++) {

					int minX = noNoLocation[0][0];
					int maxX = noNoLocation[0][1];
					int minY = noNoLocation[1][0];
					int maxY = noNoLocation[1][1];

					int rightX = 0;
					int rightY = 0;
					// update right side of domino coordinates
					switch (direction) {
					case Right:
						rightY = leftY;
						rightX = leftX + 1;
						break;
					case Left:
						rightY = leftY;
						rightX = leftX - 1;
						break;
					case Up:
						rightY = leftY - 1;
						rightX = leftX;
						break;
					case Down:
						rightY = leftY + 1;
						rightX = leftX;
						break;
					default:
						rightY = leftY;
						rightX = leftX;
						break;
					}
					// do nothing, invalid coordinated (inside no no section)
					if ((rightX < minX) || (rightX > maxX) || (rightY < minY) || (rightY > maxY)) {

					} else {
						// check if a domino can be placed (terrain type not taken in account)
						if (!kingdomGrid[leftY][leftX] && !kingdomGrid[rightY][rightX]) {
							// check if type of domino matches any neighbour
							if (checkAroundDomino(kingdomGrid, leftX, leftY, rightX, rightY, player,
									toBeDiscardedKingdom)) {
								directionFound = true;
							}
						}
					}
				}
			}
		}
		// if cannot be placed, discard domino
		if (!directionFound) {
			toBeDiscarded.setStatus(DominoStatus.Discarded);
		}
		return !directionFound;
	}

	/**
	 * Feature #2 : This method is to browse and get all the users Gherkin Feature :
	 * ProvideUserProfile
	 * 
	 * @author Mohammad Salman Mesam
	 * @return map of all the users
	 */

	public static List<Map<String, String>> getAllUsers() {
		List<Map<String, String>> users = new ArrayList<Map<String, String>>();
		for (User user : KingdominoApplication.getKingdomino().getUsers()) {
			Map<String, String> map = new HashMap<>();
			map.put("name", user.getName());
			map.put("wongames", Integer.toString(user.getWonGames()));
			map.put("playedgames", Integer.toString(user.getPlayedGames()));

			users.add(map);
		}
		Comparator<Map<String, String>> comparator = (Map<String, String> a, Map<String, String> b) -> {
			return a.get("name").compareToIgnoreCase(b.get("name"));
		};
		Collections.sort(users, comparator);

		return users;
	}

	/**
	 * method will register if a tile has been occupied or not
	 * 
	 * @author Delia Bretan
	 * @param kingdomGrid  boolean[][]. table containing the location of the placed
	 *                     dominoes in kingdom
	 * @param domInKingdom DominoInKingdom. domino that is considered for determined
	 *                     location in kingdomGrid
	 * @return boolean [][]. updated kingdomGrid
	 */
	private static boolean[][] buildKingdom(boolean[][] kingdomGrid, DominoInKingdom domInKingdom) {

		// transforming coordinated from -4 to 4 system to 0 to 8 system
		int leftX = domInKingdom.getX() + 4;
		int leftY = 4 - domInKingdom.getY();
		int rightX;
		int rightY;

		int startNoX = 0;
		int endNoX = 0;
		int startNoY = 0;
		int endNoY = 0;

		// updating grid (the one that will enforce 5*5 kingdom size) according to
		// direction
		// updating right end position according to direction
		switch (domInKingdom.getDirection()) {
		case Right:
			startNoX = (leftX - 3) < 0 ? 0 : (leftX - 3);
			noNoLocation[0][0] = (startNoX > noNoLocation[0][0]) ? startNoX : noNoLocation[0][0];
			endNoX = (leftX + 4) > 8 ? 8 : (leftX + 4);
			noNoLocation[0][1] = (endNoX < noNoLocation[0][1]) ? endNoX : noNoLocation[0][1];
			startNoY = (leftY - 4) < 0 ? 0 : (leftY - 4);
			noNoLocation[1][0] = (startNoY > noNoLocation[1][0]) ? startNoY : noNoLocation[1][0];
			endNoY = (leftY + 4) > 8 ? 8 : (leftY + 4);
			noNoLocation[1][1] = (endNoY < noNoLocation[1][1]) ? endNoY : noNoLocation[1][1];

			rightY = leftY;
			rightX = leftX + 1;
			break;
		case Left:
			startNoX = (leftX - 4) < 0 ? 0 : (leftX - 4);
			noNoLocation[0][0] = (startNoX > noNoLocation[0][0]) ? startNoX : noNoLocation[0][0];
			endNoX = (leftX + 3) > 8 ? 8 : (leftX + 3);
			noNoLocation[0][1] = (endNoX < noNoLocation[0][1]) ? endNoX : noNoLocation[0][1];
			startNoY = (leftY - 4) < 0 ? 0 : (leftY - 4);
			noNoLocation[1][0] = (startNoY > noNoLocation[1][0]) ? startNoY : noNoLocation[1][0];
			endNoY = (leftY + 4) > 8 ? 8 : (leftY + 4);
			noNoLocation[1][1] = (endNoY < noNoLocation[1][1]) ? endNoY : noNoLocation[1][1];

			rightY = leftY;
			rightX = leftX - 1;
			break;
		case Up:
			startNoX = (leftX - 4) < 0 ? 0 : (leftX - 4);
			noNoLocation[0][0] = (startNoX > noNoLocation[0][0]) ? startNoX : noNoLocation[0][0];
			endNoX = (leftX + 4) > 8 ? 8 : (leftX + 4);
			noNoLocation[0][1] = (endNoX < noNoLocation[0][1]) ? endNoX : noNoLocation[0][1];
			startNoY = (leftY - 4) < 0 ? 0 : (leftY - 4);
			noNoLocation[1][0] = (startNoY > noNoLocation[1][0]) ? startNoY : noNoLocation[1][0];
			endNoY = (leftY + 3) > 8 ? 8 : (leftY + 3);
			noNoLocation[1][1] = (endNoY < noNoLocation[1][1]) ? endNoY : noNoLocation[1][1];

			rightY = leftY - 1;
			rightX = leftX;
			break;
		case Down:
			startNoX = (leftX - 4) < 0 ? 0 : (leftX - 4);
			noNoLocation[0][0] = (startNoX > noNoLocation[0][0]) ? startNoX : noNoLocation[0][0];
			endNoX = (leftX + 4) > 8 ? 8 : (leftX + 4);
			noNoLocation[0][1] = (endNoX < noNoLocation[0][1]) ? endNoX : noNoLocation[0][1];
			startNoY = (leftY - 3) < 0 ? 0 : (leftY - 3);
			noNoLocation[1][0] = (startNoY > noNoLocation[1][0]) ? startNoY : noNoLocation[1][0];
			endNoY = (leftY + 4) > 8 ? 8 : (leftY + 4);
			noNoLocation[1][1] = (endNoY < noNoLocation[1][1]) ? endNoY : noNoLocation[1][1];

			rightY = leftY + 1;
			rightX = leftX;
			break;
		default:
			rightY = leftY;
			rightX = leftX;
			break;
		}
		try {
			// check for overlap left
			if (!kingdomGrid[leftY][leftX]) {
				kingdomGrid[leftY][leftX] = true;

			} else {
				throw new Exception();
			}
			// check for overlap right
			if (!kingdomGrid[rightY][rightX]) {
				kingdomGrid[rightY][rightX] = true;
			} else {
				throw new Exception();
			}

		} catch (Exception e) {
			return null;
		}
		return kingdomGrid;
	}

	/**
	 * Updates the player ranking using tiebreaker rules
	 * 
	 * @PAY_ATTENTION player.getPropertyScore contains size of biggest property!
	 * @PAY_ATTENTION player.getBonusScore contains number of crowns!
	 * 
	 * @author Delia Bretan
	 * @param void
	 * @return void
	 */
	public static void resolveTiebreak() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		// gets all players
		ArrayList<Player> players = new ArrayList(KingdominoApplication.getKingdomino().getCurrentGame().getPlayers());

		// comparator to sort max size of property in decreasing order
		Comparator<Player> c = (Player a, Player b) -> {
			return b.getPropertyScore() - a.getPropertyScore();
		};
		Comparator<Player> e = (Player a, Player b) -> {
			return a.getPropertyScore() - b.getPropertyScore();
		};
		// comparator to sort number of crowns in decreasing order
		Collections.sort(players, c);
		Comparator<Player> d = (Player a, Player b) -> {
			return b.getBonusScore() - a.getBonusScore();
		};

		for (Player player : players) {
			largestProperty(player);
		}
		Collections.sort(players, c);

		// will hold players with same propertyScore
		ArrayList<Player> sameProp = new ArrayList<Player>();
		// will hold temp ranking of players
		ArrayList<Player> tempFinal = new ArrayList<Player>();
		// will hold players that have the same score
		ArrayList<Player> sameScore = new ArrayList<Player>();
		// final ranking of players
		ArrayList<Player> finalScore = new ArrayList<Player>();

		sameProp.add(players.get(0));
		for (int p = 1; p < players.size(); p++) {
			if (players.get(p).getPropertyScore() == players.get(p - 1).getPropertyScore()) {
				sameProp.add(players.get(p));
			} else {
				for (Player ppp : sameProp) {
					ppp.setPropertyScore(largestProperty(ppp));
					ppp.setBonusScore(countCrowns(ppp));
				}
				Collections.sort(sameProp, c);
				sameProp.forEach(action -> tempFinal.add(action));
				sameProp.clear();
				sameProp.add(players.get(p));
			}
		}
		if (!sameProp.isEmpty()) {
			Collections.sort(sameProp, c);
			sameProp.forEach(action -> tempFinal.add(action));
		}

		sameScore.add(tempFinal.get(0));
		for (int p = 1; p < tempFinal.size(); p++) {
			// updates sameScore if player p has same maxSize as player p-1
			if (tempFinal.get(p).getPropertyScore() == tempFinal.get(p - 1).getPropertyScore()) {
				sameScore.add(tempFinal.get(p));
			}
			// sorts sameScore according to crowns and adds the result to finalScore
			// begins a new sameScore list
			else {
				Collections.sort(sameScore, d);
				sameScore.forEach(action -> finalScore.add(action));
				sameScore.clear();
				sameScore.add(tempFinal.get(p));
			}
		}
		// if any leftover sameScore players, sort and update finalScore
		if (!sameScore.isEmpty()) {
			Collections.sort(sameScore, d);
			sameScore.forEach(action -> finalScore.add(action));
		}

		int rankingDuck = 1;
		int[] maxSize = new int[4];

		int indexx = 0;
		for (Player player : finalScore) {
			maxSize[indexx] = player.getPropertyScore();
			indexx++;
			largestProperty(player);
		}

		for (int ii = 0; ii < finalScore.size() - 1; ii++) {
			for (int jj = ii + 1; jj < ii + 2; jj++) {
				finalScore.get(ii).setCurrentRanking(rankingDuck);
				if ((maxSize[ii] == maxSize[jj])
						&& (finalScore.get(ii).getPropertyScore() == finalScore.get(jj).getPropertyScore())
						&& (finalScore.get(ii).getBonusScore() == finalScore.get(jj).getBonusScore())) {

				} else {
					rankingDuck++;
				}
			}
		}
		finalScore.get(finalScore.size() - 1).setCurrentRanking(rankingDuck);

		// assigns ranking to players
		for (Player player : game.getPlayers()) {
			for (int ranking = 0; ranking < finalScore.size(); ranking++) {
				if (player.equals(finalScore.get(ranking))) {
					player.setCurrentRanking(finalScore.get(ranking).getCurrentRanking());
				}
			}
		}
		calculatePlayerScore();
		calculateBonusScore();

	}

	/**
	 * visitedTiles static boolean[][]. table containing the location of the placed
	 * dominoes in kingdom
	 * 
	 * valueTile static int. the size of a property
	 * 
	 * crownsOnProperty static int. the total number of crowns
	 */
	private static boolean[][] visitedTiles = new boolean[9][9];
	static int valueTile = 0;
	private static int crownsOnProperty = 0;

	/**
	 * Method will check every property in kingdom and return their size
	 * 
	 * @author Delia Bretan
	 * @param player. the current player
	 * @return int. the size of property
	 */
	private static int largestProperty(Player player) {
		Tile[][] tiles = HelperFunctions.getKingdomAsTiles(player, DominoStatus.PlacedInKingdom);
		visitedTiles = new boolean[9][9];
		int largestSize = 0;

		for (int y = 0; y < tiles.length; y++) {
			for (int x = 0; x < tiles[y].length; x++) {
				// check every tile if it contains a domino
				try {
					// finds largest property size & number of total crowns
					tiles[y][x].getTerrainType();
					if (!visitedTiles[y][x]) {
						valueTile = 0;
						crownsOnProperty = 0;
						recursiveProperty(tiles, x, y);
						player.setPropertyScore(player.getPropertyScore() + valueTile * crownsOnProperty);

						if (!KingdominoApplication.getKingdomino().getBonusOptions().isEmpty()) {
							player.setPropertyScore(player.getPropertyScore() + player.getBonusScore());
						}
						largestSize = largestSize > valueTile ? largestSize : valueTile;

					}

				} catch (Exception e) {
				}

			}
		}
		return largestSize;
	}

	/**
	 * method will use recusion to determine the size of a property. method updates
	 * visitedTiles and crownsOnProperty
	 * 
	 * @author Delia Bretan
	 * @param grid           boolean[][]. table containing the location of the
	 *                       placed dominoes in kingdom
	 * @param startingPointX int. the current tile being examined X
	 * @param startingPointY int. the current tile being examined Y
	 */
	private static void recursiveProperty(Tile[][] grid, int startingPointX, int startingPointY) {
		if (visitedTiles[startingPointY][startingPointX]) {
		} else {
			valueTile++;
			visitedTiles[startingPointY][startingPointX] = true;
			crownsOnProperty += grid[startingPointY][startingPointX].getCrowns();
			// check up
			try {
				if (grid[startingPointY - 1][startingPointX].getTerrainType()
						.equals(grid[startingPointY][startingPointX].getTerrainType())
						&& !visitedTiles[startingPointY - 1][startingPointX]) {
					recursiveProperty(grid, startingPointX, startingPointY - 1);
				}

			} catch (Exception e) {

			}
			// check down
			try {
				if (grid[startingPointY + 1][startingPointX].getTerrainType()
						.equals(grid[startingPointY][startingPointX].getTerrainType())
						&& !visitedTiles[startingPointY + 1][startingPointX]) {
					recursiveProperty(grid, startingPointX, startingPointY + 1);
				}

			} catch (Exception e) {

			}
			// check left
			try {
				if (grid[startingPointY][startingPointX - 1].getTerrainType()
						.equals(grid[startingPointY][startingPointX].getTerrainType())
						&& !visitedTiles[startingPointY][startingPointX - 1]) {
					recursiveProperty(grid, startingPointX - 1, startingPointY);
				}

			} catch (Exception e) {

			}
			// check right
			try {
				if (grid[startingPointY][startingPointX + 1].getTerrainType()
						.equals(grid[startingPointY][startingPointX].getTerrainType())
						&& !visitedTiles[startingPointY][startingPointX + 1]) {
					recursiveProperty(grid, startingPointX + 1, startingPointY);
				}

			} catch (Exception e) {

			}
		}
	}

	/**
	 * Mathod counts all the crowns in kingdom
	 * 
	 * @author Delia Bretan
	 * @param player Player. the current player
	 * @return int. the number of crowns in a kingdom
	 */
	private static int countCrowns(Player player) {
		Tile[][] grid = HelperFunctions.getKingdomAsTiles(player, DominoStatus.PlacedInKingdom);
		int numberOfCrowns = 0;
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[y].length; x++) {
				try {
					numberOfCrowns += (grid[y][x].getCrowns());
				} catch (Exception e) {

				}
			}
		}
		return numberOfCrowns;
	}

//------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * @author Zhanna Klimanova Controller method used in
	 *         CalculateRankingStepDefinitions.java. Goes through the entire map of
	 *         the kingdom, calculates the total score and establishes ranking based
	 *         on the number of crowns and size of property.
	 * @return
	 */

	public static List<String> calculateRanking() {

		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		// gets all players
		ArrayList<Player> players = new ArrayList(KingdominoApplication.getKingdomino().getCurrentGame().getPlayers());

		// comparator to sort max size of property in decreasing order
		Comparator<Player> c = (Player a, Player b) -> {
			return b.getPropertyScore() - a.getPropertyScore();
		};
		Comparator<Player> e = (Player a, Player b) -> {
			return a.getPropertyScore() - b.getPropertyScore();
		};
		// comparator to sort number of crowns in decreasing order
		Collections.sort(players, c);
		Comparator<Player> d = (Player a, Player b) -> {
			return b.getBonusScore() - a.getBonusScore();
		};

		for (Player player : players) {
			largestProperty(player);
		}
		Collections.sort(players, c);

		players.forEach(arg0 -> System.out.println(arg0.getUser().getName()));
		// assigns ranking to players
		for (Player player : game.getPlayers()) {
			for (int ranking = 0; ranking < players.size(); ranking++) {
				if (player.equals(players.get(ranking))) {
					player.setCurrentRanking(ranking + 1);
				}
			}
		}

//		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
//		List<Player> players = new ArrayList(KingdominoApplication.getKingdomino().getCurrentGame().getPlayers());
		List<String> playerList = new ArrayList<>();
//		for (Player player : players) {
//			HashSet<MyProperty> properties = new HashSet<>();
//			for (int y = -4; y <= 4; ++y) {
//				for (int x = -4; x <= 4; ++x) {
//					try {
//						properties.add(HelperFunctions.getProperty(player, x, y, DominoStatus.PlacedInKingdom));
//					} catch (Exception e) {
//						continue;
//					}
//				}
//			}
//
//			for (MyProperty eachProperty : properties) {
//				eachProperty.getSize();
//				player.setPropertyScore(player.getPropertyScore() + eachProperty.getSize() * eachProperty.getCrowns());
//			}
//		}
//
//		Comparator<Player> c = (Player a, Player b) -> {
//			return b.getTotalScore() - a.getTotalScore();
//		};
//
//		Collections.sort(players, c);
//

//		
//		
//
//		for (int i = 0; i < players.size(); i++) {
//			players.get(i).setCurrentRanking(i + 1);
//			playerList.add(players.get(i).getColor().toString().toLowerCase());
//		}
		return playerList;
	}

	/**
	 * author Zhanna Klimanova Controller method used in
	 * CreateNextDraftStepDefinitions.java to take top domino in the pile. If there
	 * is no top domino, should throw exception.
	 * 
	 * @param quantityToTake
	 * @return
	 */

	public static List<Domino> takeTopDominosFromPile(int quantityToTake) {
		Domino firstDomino = KingdominoApplication.getKingdomino().getCurrentGame().getTopDominoInPile();

		if (firstDomino == null) {
			return null;
		}
		List<Domino> dominos = new ArrayList<>();
		dominos.add(firstDomino);
		for (int i = 1; i < quantityToTake; i++) {
			if (firstDomino.getNextDomino() == null) {
				break; // should throw exception maybe
			}
			firstDomino = firstDomino.getNextDomino();
			dominos.add(firstDomino);

		}
		KingdominoApplication.getKingdomino().getCurrentGame().setTopDominoInPile(firstDomino.getNextDomino());
		return dominos;

//		Domino firstDomino = KingdominoApplication.getKingdomino().getCurrentGame().getTopDominoInPile();
//		List<Domino> dominos = new ArrayList<>();
//		dominos.add(firstDomino);
//		if (firstDomino.equals(null)) {
//			return null;
//		}
//		for (int i = 1; i < quantityToTake; i++) {
//			dominos.add(firstDomino.getNextDomino());
//			firstDomino = firstDomino.getNextDomino();
//			if (firstDomino == null) {
//				break; // should throw exception maybe
//			}
//		}
//		System.out.println("-------" + dominos.size());
//		if (firstDomino.getNextDomino() != null) {
//			KingdominoApplication.getKingdomino().getCurrentGame().setTopDominoInPile(firstDomino.getNextDomino());
//		} else {
//			throw new NullPointerException();
//		}
//		return dominos;
	}

	/**
	 * @author Zhanna Klimanova Controller method used in
	 *         CreateNextDraftStepDefinitions.java to set next draft of dominos.
	 * @return
	 */
	public static List<Domino> setNextDraft() {
		Draft nextDraft = KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft();

		if (nextDraft == null) {
			return null;
		}
		List<Domino> dominoDraft = takeTopDominosFromPile(4);

		for (Domino domino : dominoDraft) {
			domino.setStatus(DominoStatus.InNextDraft);
			nextDraft.addIdSortedDomino(domino);
		}
		return dominoDraft;
	}

	/**
	 * @author Zhanna Klimanova Controller method used in
	 *         OrderAndRevealNextDraftStepDefinitions.java to order the dominos from
	 *         smallest to larged. It also sets the DraftStatus to Sorted ones the
	 *         dominos have been place in order.
	 * @return
	 */
	public static List<Integer> initiateOrderingOfNextDraft() {
		Draft unsortedDraft = KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft();
		List<Integer> dominoIDs = new ArrayList<>();

		List<Domino> dominos = new ArrayList<>(unsortedDraft.getIdSortedDominos());

		Comparator<Domino> c = (Domino A, Domino B) -> {
			return A.getId() - B.getId();
		};
		Collections.sort(dominos, c);

		for (Domino domino : dominos) {
			dominoIDs.add(domino.getId());
		}
		unsortedDraft.setDraftStatus(DraftStatus.Sorted);

		for (Domino domino : dominos) {
			unsortedDraft.removeIdSortedDomino(domino);
		}

		for (int i = 0; i < dominos.size(); i++) {

			unsortedDraft.addIdSortedDomino(dominos.get(i));
		}

		return dominoIDs;
	}

	/**
	 * @author Zhanna Klimanova Controller method used in
	 *         OrderAndRevealNextDraftStepDefinitions.java to set the DraftStatus of
	 *         the dominos in the draft to face up.
	 * @return
	 */
	public static void revealNextDraft() {

		KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().setDraftStatus(DraftStatus.FaceUp);
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

	public static Domino getdominoByID(Game game, int id) {
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}

	/**
	 * @author Nafiz Islam feature: VerifyNoOverlapping
	 * @return
	 */
	public static boolean isPreplacedDominoOverlapping() {
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getCurrentPlayer();

		DominoInKingdom preplacedDominoInKingdom = null;
//		for (KingdomTerritory territory : player.getKingdom().getTerritories()) {
//			if (territory instanceof DominoInKingdom) {
//				DominoInKingdom dominoInKingdom = (DominoInKingdom) territory;
//				Domino domino = dominoInKingdom.getDomino();
//				if (domino.getStatus().equals(DominoStatus.CorrectlyPreplaced)
//						|| domino.getStatus().equals(DominoStatus.ErroneouslyPreplaced)) {
//					currDominoInKingdom = dominoInKingdom;
//					break;
//				}
//			}
//		}
		preplacedDominoInKingdom = HelperFunctions.getPreplacedDominoInKingdom(player);

		if (preplacedDominoInKingdom == null) {
			return true;
		} else {
			Tile[][] tiles = HelperFunctions.getKingdomAsTiles(player, DominoStatus.PlacedInKingdom);
			int il = preplacedDominoInKingdom.getX() + 4, jl = 4 - preplacedDominoInKingdom.getY();

			// is left tile overlapping?
			if (tiles[jl][il] != null) {
				preplacedDominoInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
				return true;
			}

			// is right tile overlapping?
			switch (preplacedDominoInKingdom.getDirection()) {
			case Right:
				if (il < 8 && tiles[jl][il + 1] != null) {
					preplacedDominoInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
					return true;
				}
				break;
			case Up:
				if (jl > 0 && tiles[jl - 1][il] != null) {
					preplacedDominoInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
					return true;
				}
				break;
			case Left:
				if (il > 0 && tiles[jl][il - 1] != null) {
					preplacedDominoInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
					return true;
				}
				break;
			case Down:
				if (jl < 8 && tiles[jl + 1][il] != null) {
					preplacedDominoInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
					return true;
				}
				break;
			}
		}

		// Left or right or both are not overlapping
		return false;
	}

	/**
	 * @author Nafiz Islam feature: VerifyCastleAdjacency
	 * @return
	 */
	public static boolean isPreplacedDominoAdjacentToCastle() {
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getCurrentPlayer();

		DominoInKingdom preplacedDominoInKingdom = null;
//		for (KingdomTerritory territory : player.getKingdom().getTerritories()) {
//			if (territory instanceof DominoInKingdom) {
//				DominoInKingdom dominoInKingdom = (DominoInKingdom) territory;
//				Domino domino = dominoInKingdom.getDomino();
//				if (domino.getStatus().equals(DominoStatus.CorrectlyPreplaced)
//						|| domino.getStatus().equals(DominoStatus.ErroneouslyPreplaced)) {
//					currDominoInKingdom = dominoInKingdom;
//					break;
//				}
//			}
//		}
		preplacedDominoInKingdom = HelperFunctions.getPreplacedDominoInKingdom(player);

		if (preplacedDominoInKingdom == null) {
			return false;
		} else {
			// get adjacent tiles around the castle (at 0,0)
			List<Tile> adjacentTiles = HelperFunctions.getAdjacentTiles(player, 0, 0, DominoStatus.ErroneouslyPreplaced,
					DominoStatus.CorrectlyPreplaced);
			for (Tile tile : adjacentTiles) {
				if ((DominoInKingdom) tile.getKingdomTerritory() == preplacedDominoInKingdom) {
					int il = preplacedDominoInKingdom.getX() + 4, jl = 4 - preplacedDominoInKingdom.getY();
					// verify that the left tile is not overlapping the castle
					if (!(il == 4 && jl == 4)) {
						int ir = il, jr = jl;
						switch (preplacedDominoInKingdom.getDirection()) {
						case Right:
							ir = il + 1;
							break;
						case Up:
							jr = jl - 1;
							break;
						case Left:
							ir = il - 1;
							break;
						case Down:
							jr = jl + 1;
							break;
						}

						// verify that the right tile is not overlapping the castle
						if (!(ir == 4 && jr == 4))
							return true;
					}
				}
			}
		}

		preplacedDominoInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
		return false;
	}

	/**
	 * @author Nafiz Islam feature: VerifyGridSize
	 * @return
	 */
	public static boolean isKingdomSizeValid() {
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getCurrentPlayer();
		Tile[][] tiles = HelperFunctions.getKingdomAsTiles(player, DominoStatus.PlacedInKingdom);

		int minI = 0;
		int maxI = 0;
		int minJ = 0;
		int maxJ = 0;
		boolean firstInit = true;
		for (int j = 0; j < 9; ++j) {
			for (int i = 0; i < 9; ++i) {
				if (tiles[j][i] != null) {
					if (firstInit) {
						minI = i;
						maxI = i;
						minJ = j;
						maxJ = j;

						firstInit = false;
					}
					minI = Math.min(i, minI);
					maxI = Math.max(i, maxI);
					minJ = Math.min(j, minJ);
					maxJ = Math.max(j, maxJ);
				}
			}
		}

//		if (maxI - minI >= 5)
//			return false;
//		if (maxJ - minJ >= 5)
//			return false;

		DominoInKingdom preplacedDominoInKingdom = null;
//		for (KingdomTerritory territory : player.getKingdom().getTerritories()) {
//			if (territory instanceof DominoInKingdom) {
//				DominoInKingdom dominoInKingdom = (DominoInKingdom) territory;
//				Domino domino = dominoInKingdom.getDomino();
//				if (domino.getStatus().equals(DominoStatus.CorrectlyPreplaced)
//						|| domino.getStatus().equals(DominoStatus.ErroneouslyPreplaced)) {
//					currDominoInKingdom = dominoInKingdom;
//					break;
//				}
//			}
//		}
		preplacedDominoInKingdom = HelperFunctions.getPreplacedDominoInKingdom(player);

		if (preplacedDominoInKingdom == null) {
			return false;
		} else {
			int il = preplacedDominoInKingdom.getX() + 4, jl = 4 - preplacedDominoInKingdom.getY();

			minI = Math.min(il, minI);
			maxI = Math.max(il, maxI);
			minJ = Math.min(jl, minJ);
			maxJ = Math.max(jl, maxJ);

//			if (maxI - minI >= 5)
//				return false;
//			if (maxJ - minJ >= 5)
//				return false;

			if ((maxI - minI >= 5) || (maxJ - minJ >= 5)) {
				preplacedDominoInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
				return false;
			}

			int ir = il, jr = jl;
			switch (preplacedDominoInKingdom.getDirection()) {
			case Right:
				ir = il + 1;
				break;
			case Up:
				jr = jl - 1;
				break;
			case Left:
				ir = il - 1;
				break;
			case Down:
				jr = jl + 1;
				break;
			}

			minI = Math.min(ir, minI);
			maxI = Math.max(ir, maxI);
			minJ = Math.min(jr, minJ);
			maxJ = Math.max(jr, maxJ);

//			if (maxI - minI >= 5)
//				return false;
//			if (maxJ - minJ >= 5)
//				return false;

			if ((maxI - minI >= 5) || (maxJ - minJ >= 5)) {
				preplacedDominoInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
				return false;
			}
		}

		return true;
	}

	public static boolean isPreplacedDominoInsideGrid() {
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getCurrentPlayer();
		Tile[][] tiles = HelperFunctions.getKingdomAsTiles(player, DominoStatus.PlacedInKingdom);

		DominoInKingdom preplacedDominoInKingdom = null;

		preplacedDominoInKingdom = HelperFunctions.getPreplacedDominoInKingdom(player);

		if (preplacedDominoInKingdom == null) {
			return true;
		} else {
			int il = preplacedDominoInKingdom.getX() + 4, jl = 4 - preplacedDominoInKingdom.getY();

			if ((il > 8) || (il < 0) || (jl > 8) || (jl < 0)) {
				preplacedDominoInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
				return false;
			}

			int ir = il, jr = jl;
			switch (preplacedDominoInKingdom.getDirection()) {
			case Right:
				ir = il + 1;
				break;
			case Up:
				jr = jl - 1;
				break;
			case Left:
				ir = il - 1;
				break;
			case Down:
				jr = jl + 1;
				break;
			}

			if ((ir > 8) || (ir < 0) || (jr > 8) || (jr < 0)) {
				preplacedDominoInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
				return false;
			}
		}

		return true;
	}

	/**
	 * @author Zhanna Klimanova Controller method used in
	 *         CreateNextDraftStepDefinitions.java,
	 *         OrderAndRevealNextDraftStepDefinitions.java,
	 *         ChooseNextDominoStepDefinitions.java to parse a string of integers
	 *         and return it as an array of integers. Used for comparing ID's of
	 *         dominos.
	 * @param draftElements
	 * @param string
	 * @return
	 */
	public static int[] splitDraftString(int draftElements, String string) {
		String[] splitted;
		int[] elements = new int[draftElements];
		splitted = string.split(",");
		for (int i = 0; i < elements.length; i++) {
			elements[i] = Integer.parseInt(splitted[i]);
		}
		return elements;
	}

	/**
	 * @author Zhanna Klimanova Controller method (overloaded to only take as input
	 *         a string) used in CreateNextDraftStepDefinitions.java,
	 *         OrderAndRevealNextDraftStepDefinitions.java,
	 *         ChooseNextDominoStepDefinitions.java to parse a string of integers
	 *         and return it as an array of integers. Used for comparing ID's of
	 *         dominos.
	 * @param draftElements
	 * @param string
	 * @return
	 */
	public static int[] splitDraftString(String string) {

		String[] elements = string.split(",");
		int[] arrayOfElements = new int[elements.length];
		string.split(",");
		for (int i = 0; i < elements.length; i++) {
			arrayOfElements[i] = Integer.parseInt(elements[i]);

		}
		return arrayOfElements;
	}

	/**
	 * @author Nafiz Islam feature: VerifyNeighborAdjacency
	 * @return
	 */
	public static boolean isPreplacedDominoCorrectlyNeighbored() {
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getCurrentPlayer();

		DominoInKingdom preplacedDominoInKingdom = null;
//		for (KingdomTerritory territory : player.getKingdom().getTerritories()) {
//			if (territory instanceof DominoInKingdom) {
//				DominoInKingdom dominoInKingdom = (DominoInKingdom) territory;
//				Domino domino = dominoInKingdom.getDomino();
//				if (domino.getStatus().equals(DominoStatus.CorrectlyPreplaced)
//						|| domino.getStatus().equals(DominoStatus.ErroneouslyPreplaced)) {
//					currDominoInKingdom = dominoInKingdom;
//					break;
//				}
//			}
//		}
		preplacedDominoInKingdom = HelperFunctions.getPreplacedDominoInKingdom(player);

		if (preplacedDominoInKingdom == null) {
			return false;
		} else {
			// verify for the left tile
			List<Tile> leftAdjacentTiles = HelperFunctions.getAdjacentTiles(player, preplacedDominoInKingdom.getX(),
					preplacedDominoInKingdom.getY(), DominoStatus.PlacedInKingdom);
			for (Tile tile : leftAdjacentTiles) {
				// make sure it is not part of its domino
				if (tile.getKingdomTerritory() != preplacedDominoInKingdom) {
					if (tile.getTerrainType() == null
							|| preplacedDominoInKingdom.getDomino().getLeftTile().equals(tile.getTerrainType())) {
						preplacedDominoInKingdom.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
						return true;
					}
				}
			}

			int xr = preplacedDominoInKingdom.getX(), yr = preplacedDominoInKingdom.getY();
			switch (preplacedDominoInKingdom.getDirection()) {
			case Right:
				xr = xr + 1;
				break;
			case Up:
				yr = yr + 1;
				break;
			case Left:
				xr = xr - 1;
				break;
			case Down:
				yr = yr - 1;
				break;
			}
			List<Tile> rightAdjacentTiles = HelperFunctions.getAdjacentTiles(player, xr, yr,
					DominoStatus.PlacedInKingdom);
			for (Tile tile : rightAdjacentTiles) {
				if (tile.getKingdomTerritory() != preplacedDominoInKingdom) {
					if (tile.getTerrainType() == null
							|| preplacedDominoInKingdom.getDomino().getRightTile().equals(tile.getTerrainType())) {
						preplacedDominoInKingdom.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
						return true;
					}
				}
			}
		}

		preplacedDominoInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);

		return false;
	}

	/**
	 * Feature #5: Method to shuffle pile of dominos
	 * 
	 * @author antonianistor
	 * 
	 */
	public static List<Domino> shuffleDominos() {

		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Domino> dominos = game.getAllDominos();

		List<Domino> shuffledDominos = new ArrayList<>();

		for (Domino domino : dominos) {

			if (domino.getStatus().equals(DominoStatus.InPile)) {
				shuffledDominos.add(domino);

			}
		}

		Collections.shuffle(shuffledDominos);

		// set "pointers"

		Domino first = shuffledDominos.get(0);
		game.setTopDominoInPile(first);
		first.setNextDomino(shuffledDominos.get(1));
		first.setStatus(DominoStatus.InPile);

		Domino last = shuffledDominos.get(shuffledDominos.size() - 1);
		last.setPrevDomino(shuffledDominos.get(shuffledDominos.size() - 2));
		last.setStatus(DominoStatus.InPile);

		for (int i = 1; i < shuffledDominos.size() - 1; i++) {
			Domino current = shuffledDominos.get(i);
			current.setNextDomino(shuffledDominos.get(i + 1));
			current.setPrevDomino(shuffledDominos.get(i - 1));
			current.setStatus(DominoStatus.InPile);
		}

		return shuffledDominos;

	}

	/**
	 * Feature #12 : Method to rotate a domino Gherkin Feature:
	 * RotateCurrentDominoStepDefinitions.java
	 * 
	 * @author antonianistor
	 * 
	 * @param dir : direction of rotation
	 */

	public static void rotateDomino(String dir) {

		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player player = game.getCurrentPlayer();
		Domino domino = player.getDominoSelection().getDomino();
		DominoInKingdom dominoToRotate = null;
		Kingdom kingdom = player.getKingdom();

		List<KingdomTerritory> territory = kingdom.getTerritories();

		for (KingdomTerritory terr : territory)
			if (terr instanceof DominoInKingdom) {
				DominoInKingdom dom = (DominoInKingdom) terr;
				if (dom.getDomino() == domino)
					dominoToRotate = dom;

			}

		// if you want to know why, go to line 728
		DirectionKind oldDirection = dominoToRotate.getDirection();
		DominoStatus oldStatus = domino.getStatus();

		if (dir.equalsIgnoreCase("clockwise")) {

			if (oldDirection == DirectionKind.Up)
				dominoToRotate.setDirection(DirectionKind.Right);
			else if (oldDirection == DirectionKind.Right)
				dominoToRotate.setDirection(DirectionKind.Down);
			else if (oldDirection == DirectionKind.Down)
				dominoToRotate.setDirection(DirectionKind.Left);
			else if (oldDirection == DirectionKind.Left)
				dominoToRotate.setDirection(DirectionKind.Up);

		}

		else if (dir.equalsIgnoreCase("counterclockwise")) {

			if (oldDirection == DirectionKind.Up)
				dominoToRotate.setDirection(DirectionKind.Left);
			else if (oldDirection == DirectionKind.Right)
				dominoToRotate.setDirection(DirectionKind.Up);
			else if (oldDirection == DirectionKind.Down)
				dominoToRotate.setDirection(DirectionKind.Right);
			else if (oldDirection == DirectionKind.Left)
				dominoToRotate.setDirection(DirectionKind.Down);

		}

		else
			throw new IllegalArgumentException("invalid roatation direction");

		// you were supposed to prevent the rotation from happening if it goes of the
		// grid
		if (!isPreplacedDominoInsideGrid()) {
			dominoToRotate.setDirection(oldDirection);
			domino.setStatus(oldStatus);
		}

		HelperFunctions.setPreplacedStatus(dominoToRotate.getDomino());
	}

	/**
	 * Feature #11 Method to move a domino up, down, left or right <br>
	 * Gherkin Feature: PlaceCurrentDominoStepDefinitions.java
	 * 
	 * @author antonianistor
	 * 
	 * @param dir : direction of movement
	 */

	public static void moveDomino(String dir) {

		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player player = game.getCurrentPlayer();
		Domino domino = player.getDominoSelection().getDomino();
		DominoInKingdom dominoToMove = null;
		Kingdom kingdom = player.getKingdom();

		List<KingdomTerritory> territory = kingdom.getTerritories();

		for (KingdomTerritory terr : territory)
			if (terr instanceof DominoInKingdom) {
				DominoInKingdom dom = (DominoInKingdom) terr;
				if (dom.getDomino() == domino)
					dominoToMove = dom;

			}

		int oldPosX = dominoToMove.getX();
		int oldPosY = dominoToMove.getY();
		DominoStatus oldStatus = domino.getStatus();

		if (dir.equalsIgnoreCase(DirectionKind.Down.toString())) {
			dominoToMove.setX(dominoToMove.getX());
			dominoToMove.setY(dominoToMove.getY() - 1);
		}

		else if (dir.equalsIgnoreCase(DirectionKind.Left.toString())) {
			dominoToMove.setX(dominoToMove.getX() - 1);
			dominoToMove.setY(dominoToMove.getY());
		}

		else if (dir.equalsIgnoreCase(DirectionKind.Up.toString())) {
			dominoToMove.setX(dominoToMove.getX());
			dominoToMove.setY(dominoToMove.getY() + 1);
		}

		else if (dir.equalsIgnoreCase(DirectionKind.Right.toString())) {
			dominoToMove.setX(dominoToMove.getX() + 1);
			dominoToMove.setY(dominoToMove.getY());
		}

		else
			throw new IllegalArgumentException("can't move in this direction");

		if (!isPreplacedDominoInsideGrid()) {
			dominoToMove.setX(oldPosX);
			dominoToMove.setY(oldPosY);
			domino.setStatus(oldStatus);
		}

		HelperFunctions.setPreplacedStatus(dominoToMove.getDomino());

	}

	public static void removeKing(int id) {

	}

	/**
	 * Feature #13 method to confirm placement of domino in kingdom <br>
	 *
	 * Gherkin Feature: PlaceDominoStepDefinitions.java
	 * 
	 * @author antonianistor <br>
	 * 
	 * @param id : domino id
	 */

	public static void placeDomino(int id) {

		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player player = game.getCurrentPlayer();
		Domino domino = HelperFunctions.getdominoByID(id);
		DominoInKingdom dominoToPlace = null;
		Kingdom kingdom = player.getKingdom();

		List<KingdomTerritory> territory = kingdom.getTerritories();

		for (KingdomTerritory terr : territory)
			if (terr instanceof DominoInKingdom) {
				DominoInKingdom dom = (DominoInKingdom) terr;
				if (dom.getDomino() == domino)
					dominoToPlace = dom;

			}
		if (dominoToPlace.getDomino().getStatus() == DominoStatus.CorrectlyPreplaced)
			dominoToPlace.getDomino().setStatus(DominoStatus.PlacedInKingdom);

	}

	public static void setPlayerCount(int numberOfPlayers) {
		KingdominoApplication.getKingdomino().getCurrentGame().setNumberOfPlayers(numberOfPlayers);
	}

	/**
	 * @author Mohammad Salman Mesam Feature #1 : This method adds bonus options
	 *         Gherkin Feature : SetGameOptions
	 * @param bonusOptionName
	 */
	public static void addBonusOption(String bonusOptionName) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		kingdomino.getCurrentGame().addSelectedBonusOption(new BonusOption(bonusOptionName, kingdomino));
	}

	/**
	 * @author Mohammad Salman Mesam Feature #2 : This methode sets the number of
	 *         played and won games for all users. Gherkin Feature :
	 *         ProvideUserProfile
	 * @param username
	 * @param playedGames
	 * @param wonGames
	 */
	public static void setGameStatistics(String username, int playedGames, int wonGames) {
		User user = null;
		for (User u : KingdominoApplication.getKingdomino().getUsers()) {
			if (username.equalsIgnoreCase(u.getName())) {
				user = u;
				break;
			}
		}

		user.setPlayedGames(playedGames);
		user.setWonGames(wonGames);

	}

	/**
	 * @author Mohammad Salman Mesam Feature #2 : This is my enum to make my code
	 *         more readable Gherkin Feature : ProvideUserProfile
	 */

	public enum UserState {
		PLAYED_GAMES, WON_GAMES
	}

	/**
	 * Feature #2 : This methode obtains the game statistics for all users Gherkin
	 * Feature : ProvideUserProfile
	 * 
	 * @param username
	 * @author Mohammad Salman Mesam
	 * @return Map containing game statistics for each user
	 */

	public static Map<UserState, Integer> getGameStatistics(String username) {
		User user = null;
		for (User u : KingdominoApplication.getKingdomino().getUsers()) {
			if (username.equalsIgnoreCase(u.getName())) {
				user = u;
				break;
			}
		}

		HashMap<UserState, Integer> myMap = new HashMap();
		myMap.put(UserState.PLAYED_GAMES, user.getPlayedGames());
		myMap.put(UserState.WON_GAMES, user.getWonGames());
		return myMap;
	}

	/**
	 * Feature #3: This method starts a new Game Gherkin Feature : StartANewGame
	 * 
	 * @param numOfPlayers
	 * @param bonusOptions
	 * @author Mohammad Salman Mesam
	 */
	public static void startNewGame(int numOfPlayers, List<String> bonusOptions) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = new Game(48, kingdomino);
		kingdomino.setCurrentGame(game);

		setPlayerCount(numOfPlayers);
		for (String bonus : bonusOptions) {
			BonusOption bonusOption = new BonusOption(bonus, kingdomino);
			game.addSelectedBonusOption(bonusOption);
		}

		kingdomino.setCurrentGame(game);
		// Populate game
		HelperFunctions.addDefaultUsersAndPlayers(game);
		game.setNextPlayer(game.getPlayer(0));
		HelperFunctions.createAllDominoes(game);

		Domino lastDomino = null;
		for (Domino domino : game.getAllDominos()) {
			if (lastDomino == null) {
				game.setTopDominoInPile(domino);
			} else {
				lastDomino.setNextDomino(domino);
			}
			lastDomino = domino;
		}

		KingdominoApplication.setKingdomino(kingdomino);
	}

	/**
	 * Feature #3 : This methode reveals the first draft of the 4 dominoes Gherkin
	 * Feature : StartANewGame
	 * 
	 * @author Mohammad Salman Mesam
	 */
	public static void revealFirstDraft() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Draft draft = new Draft(DraftStatus.FaceUp, game);

		for (int i = 0; i < 4; ++i) {
			Domino domino = game.getTopDominoInPile();
			domino.setStatus(DominoStatus.InCurrentDraft);
			draft.addIdSortedDomino(domino);
			game.setTopDominoInPile(domino.getNextDomino());
		}
	}

	/**
	 * Feature #4 : This method browses the domino pile Gherkin Feature :
	 * BrowseDominoPile
	 * 
	 * @author Mohammad Salman Mesam
	 * @return Map containing browsed dominoes
	 */
	public static List<Map<String, String>> startBrowsingDominoes() {
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		HelperFunctions.createAllDominoes(game);
		ArrayList<Domino> dominos = new ArrayList<>(game.getAllDominos());
		Comparator<Domino> comparator = (Domino a, Domino b) -> {
			return a.getId() - b.getId();
		};
		Collections.sort(dominos, comparator);

		ArrayList<Map<String, String>> mapDominoes = new ArrayList<>();
		for (Domino domino : dominos) {
			mapDominoes.add(getDominoInfo(domino.getId()));
		}

		return mapDominoes;
	}

	/**
	 * Feature #4 : This method browses the domino pile by Terraintype Gherkin
	 * Feature : BrowseDominoPile
	 * 
	 * @param terrainType
	 * @author Mohammad Salman Mesam
	 * @return Map containing sorted dominoes by terrain
	 */
	public static List<Map<String, String>> browseDominoesByTerrain(String terrainType) {
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		HelperFunctions.createAllDominoes(game);
		ArrayList<Domino> dominos = new ArrayList<>(game.getAllDominos());
		Comparator<Domino> comparator = (Domino a, Domino b) -> {
			return a.getId() - b.getId();
		};
		Collections.sort(dominos, comparator);

		ArrayList<Domino> dominosTerrain = new ArrayList<>();
		for (Domino domino : dominos) {
			if (domino.getLeftTile().toString().equalsIgnoreCase(terrainType)
					|| domino.getRightTile().toString().equalsIgnoreCase(terrainType)) {
				dominosTerrain.add(domino);
			}

		}
		ArrayList<Map<String, String>> mapOfTerrainDominoes = new ArrayList<>();
		for (Domino domino : dominosTerrain) {
			mapOfTerrainDominoes.add(getDominoInfo(domino.getId()));
		}

		return mapOfTerrainDominoes;

	}

	/**
	 * Feature #4 : This method obtains information for a domino when the domino ID
	 * is entered Gherkin Feature : BrowseDominoPile
	 * 
	 * @author Mohammad Salman Mesam
	 * @param ID
	 * @return Map containing information about the domino
	 */

	public static Map<String, String> getDominoInfo(int ID) {

		Domino domino = null;
		for (Domino d : KingdominoApplication.getKingdomino().getCurrentGame().getAllDominos()) {
			if (ID == (d.getId())) {
				domino = d;
				break;
			}
		}
		if (domino == null)
			throw new IllegalArgumentException("ID is outside the range (0,48]");

		HashMap<String, String> myMap = new HashMap();
		myMap.put("id", Integer.toString(domino.getId()));
		myMap.put("lefttile", domino.getLeftTile().toString());
		myMap.put("righttile", domino.getRightTile().toString());
		myMap.put("crowns", Integer.toString(domino.getLeftCrown() + domino.getRightCrown()));
		return myMap;
	}

	/**
	 * Feature #19 Method to identify the properties of a given player's kingdom
	 * <br>
	 * Gherkin Feature: IdentifyPropertiesStepDefinition.java <br>
	 * 
	 * @author Eric Pelletier <br>
	 */

	public static void identifyPlayerProperties() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Player> players = game.getPlayers();
		for (Player aPlayer : players) {
			Kingdom kingdom = game.getPlayer(game.indexOfPlayer(aPlayer)).getKingdom();
			List<Tile> playerTiles = HelperFunctions.KingdomTiles(aPlayer);
			MyProperty potentialProperty = null;
			List<MyProperty> potentialProperties = new ArrayList<MyProperty>();
			List<MyProperty> smallerOne = new ArrayList<MyProperty>();

			int size = playerTiles.size();

			for (int h = 0; h < size; h++) {
				boolean notEqual = true;
				boolean bigger = false;
				potentialProperty = HelperFunctions.makeMyProperty(aPlayer, h);
				if (!(potentialProperties.contains(potentialProperty))) {
					if (potentialProperties.size() == 0) {
						potentialProperties.add(potentialProperty);
						HelperFunctions.makeProperty(potentialProperty, kingdom);
						continue;
					}
					for (MyProperty prop : potentialProperties) {
						if (HelperFunctions.propertyEquality(prop, potentialProperty)) {
							notEqual = false;
						}
						if (HelperFunctions.isBiggerProperty(prop, potentialProperty)) {
							bigger = true;
							smallerOne.add(prop);
						}
						if (HelperFunctions.isSmallerProperty(prop, potentialProperty)) {
							notEqual = false;
						}
					}
					if (notEqual) {
						if (bigger) {
							for (MyProperty p : smallerOne) {
								potentialProperties.remove(p);
							}
						}
						potentialProperties.add(potentialProperty);
					}
				}

			}
			for (MyProperty myProperty : potentialProperties) {
				HelperFunctions.makeProperty(myProperty, kingdom);
			}
		}

	}

	/**
	 * Feature #20 Method to determine and update the number of crowns, size and
	 * score of a player's properties <br>
	 * Gherkin Feature: CalculatePropertyAttributesStepDefinition.java <br>
	 * 
	 * @author Eric Pelletier <br>
	 */

	public static void calculatePropertyAttributes() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Player> players = game.getPlayers();
		for (Player aPlayer : players) {
			Kingdom kingdom = game.getPlayer(game.indexOfPlayer(aPlayer)).getKingdom();
			identifyPlayerProperties();

			List<Property> properties = aPlayer.getKingdom().getProperties();

			for (Property prop : properties) {
				HelperFunctions.getPropertyAttributes(prop);
			}
		}
	}

	/**
	 * Feature #21 Method to determine and update a player's bonus score based on
	 * the selected bonus option <br>
	 * Gherkin Feature: calculateBonusScoreStepDefinition.java <br>
	 * 
	 * @author Eric Pelletier <br>
	 */

	public static void calculateBonusScore() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Player> players = game.getPlayers();
		for (Player aPlayer : players) {
			int bonusScore = 0;
			List<BonusOption> options = aPlayer.getGame().getSelectedBonusOptions();
			for (BonusOption op : options) {
				if (op.getOptionName().equalsIgnoreCase("Middle Kingdom")) {
					if (HelperFunctions.middleKingdomCheck(aPlayer)) {
						bonusScore += 10;
					}
				}
				if (op.getOptionName().equalsIgnoreCase("Harmony")) {
					if (HelperFunctions.harmonyCheck(aPlayer)) {
						bonusScore += 5;
					}
				}
			}
			aPlayer.setBonusScore(bonusScore);
		}
	}

	/**
	 * Feature #22 Method to determine and update a player's total score based on
	 * the selected bonus option <br>
	 * Gherkin Feature: calculatePlayerScoreStepDefinition.java <br>
	 * 
	 * @author Eric Pelletier <br>
	 */

	public static void calculatePlayerScore() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Player> players = game.getPlayers();
		for (Player aPlayer : players) {
			calculatePropertyAttributes();
			calculateBonusScore();
			int propertyScore = HelperFunctions.getPropertyScore(aPlayer);
			aPlayer.setPropertyScore(propertyScore);
		}
	}

	public static void createPileFromIDs(int... IDs) {
		Domino currentDomino;
		Domino previousDomino;

		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		game.setTopDominoInPile(HelperFunctions.getdominoByID(IDs[0]));

		previousDomino = game.getTopDominoInPile();
		for (int i = 1; i < IDs.length; i++) {
			currentDomino = HelperFunctions.getdominoByID(IDs[i]);
			previousDomino.setNextDomino(currentDomino);
			currentDomino.setPrevDomino(previousDomino);
			previousDomino = currentDomino;
		}
	}

	/**
	 * @author Zhanna Klimanova Controller method used in
	 *         CreateNextDraftStepDefinitions.java to print/output the pile of
	 *         dominos for debugging purposes.
	 */
	public static void printPile() {

		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Domino current = game.getTopDominoInPile();
		Domino next = current.getNextDomino();
		System.out.println("Top of pile domino 1 " + current);
		int counter = 2;

		while (current.hasNextDomino()) {
			current = next;
			next = current.getNextDomino();
			System.out.println("Domino " + counter + " " + current);
			counter++;
		}

	}

	/**
	 * @author Zhanna Klimanova Controller method used in
	 *         CalculateRankingStepDefinitions.java to add the two dominoes in the
	 *         map to the kingdom
	 * 
	 */
	public static void addDominoToKingdom(String playerColor, int DominoID, int posX, int posY, String direction) {
//		playerColor = playerColor.toLowerCase();
		Player player = null;
		List<Player> listOfPlayers = KingdominoApplication.getKingdomino().getCurrentGame().getPlayers();

		for (Player eachPlayer : listOfPlayers) {
			String eachPlayerColor = eachPlayer.getColor().toString();
			if (eachPlayerColor.equalsIgnoreCase(playerColor)) {
				player = eachPlayer;
			}
		}

		if (player == null) {
			throw new IllegalArgumentException("Player color is not valid");
		} else {
			// createAllDominoes(currentGame);
			Domino domino = HelperFunctions.getdominoByID(DominoID);
			DominoInKingdom dominoInKingdom = new DominoInKingdom(posX, posY, player.getKingdom(), domino);
			dominoInKingdom.setDirection(HelperFunctions.getDirection(direction));
			domino.setStatus(DominoStatus.PlacedInKingdom);
		}
	}

	/**
	 * @author Zhanna Klimanova Controller method used in
	 *         ChooseNextDominoStepDefinitions.java to place a players king on a
	 *         chosen domino. If the domino is already occupied the controller asks
	 *         the player to choose a different domino as the one they have chosen
	 *         is occupied by another player. This method returns an array of the
	 *         draft showing which dominos are occupied and which are not.
	 * @param ID
	 * @return
	 */
	public static String[] placeCurrentKingOnDominoID(int ID, String[] arrayPlayerColors) {

		if (HelperFunctions.getdominoByID(ID).getDominoSelection() == null) {
			DominoSelection selection = new DominoSelection(
					KingdominoApplication.getKingdomino().getCurrentGame().getCurrentPlayer(),
					HelperFunctions.getdominoByID(ID),
					KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft());

			for (Player player : KingdominoApplication.getKingdomino().getCurrentGame().getPlayers()) {
				if (player.equals(selection.getPlayer())) {
					player.setDominoSelection(selection);
				}
				int counter = 0;
				for (Domino domino : KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft()
						.getIdSortedDominos()) {

					if (arrayPlayerColors[counter] == "none") {
						arrayPlayerColors[counter] = "none";
					}
					if (domino.getId() == ID) {
//					
						arrayPlayerColors[counter] = KingdominoApplication.getKingdomino().getCurrentGame()
								.getCurrentPlayer().getColor().toString();

					}
					counter++;
				}

			}
		} else {
			System.out.println("You must pick a different domino, as the one you chose has already been selected");

		}
		return arrayPlayerColors;
	}
	
	public static List<Map<String, String>> loadUsers() {
		String nameOfFileU = "src/main/java/ca/mcgill/ecse223/kingdomino/controller/Saves/outputUser.txt";
		List<Map<String,String>> userProfiles = new ArrayList<>();
		
			try {
				File file = new File(nameOfFileU);
				Scanner in = new Scanner(file);
				
				
				while (in.hasNextLine()) {
					Map<String, String> map = new HashMap<>();
					
					if (in.hasNextLine()) {
						map.put("name", in.nextLine());
					}
					
					if (in.hasNextLine()) {
						map.put("playedGames", in.nextLine());
					}
					
					if (in.hasNextLine()) {
						map.put("wonGames", in.nextLine());
					}
					userProfiles.add(map);
				}
				
				in.close();
			} catch (Exception e) {
				System.out.println("error with load user profile");
			}
			
			return userProfiles; 
	}
	
	public static void saveUsers() {
		final String nameOfFileU = "src/main/java/ca/mcgill/ecse223/kingdomino/controller/Saves/outputUser.txt";
		
		try {
			List<Map<String,String>> profiles = loadUsers();
			
			File file = new File(nameOfFileU);
			PrintWriter out = new PrintWriter(file);
			
			List<User> users = new ArrayList<>(KingdominoApplication.getKingdomino().getUsers());
			
			for (User u : users) {
				boolean contains = false;
				
				for (Map<String, String> p : profiles) {
					if (p.get("name").equalsIgnoreCase(u.getName())) {
						// there is a map that contains the name 
						contains = true;
						
						p.put("playedGames", "" + u.getPlayedGames());
						p.put("wonGames", "" + u.getWonGames());
					}
				}
				
				if (!contains) {
					// otherwise
					Map<String, String> p = new HashMap<>();
					p.put("name", u.getName());
					p.put("playedGames", "" + u.getPlayedGames());
					p.put("wonGames", "" + u.getWonGames());
					
					profiles.add(p);
				}
			}
			
			// update user data file
			for (Map<String, String> m : profiles) {
				out.println(m.get("name"));
				out.println(m.get("playedGames"));
				out.println(m.get("wonGames"));
			}
			
			out.flush();
			out.close();
		} catch (Exception e) {
			System.out.println("error with save user profile");
		}
	}

	/**
	 * @author Nafiz Islam
	 * @method stops current game by setting current game to null
	 */
	public static void stopCurrentGame() {
		KingdominoApplication.getKingdomino().setCurrentGame(null);
	}

	/**
	 * @author Zhanna
	 * @method string that has credit of developers
	 */
	public static String getCredit() {
		return KingdominoApplication.getKingdomino().CREDIT;
	}
	
	/**
	 * @author antonia nistor
	 * @method gets score of a player after you input its color
	 * 
	 */
	public static int color2PlayerScore(String color) {
		Player player = HelperFunctions.findPlayer(color);
		int score;
		score = player.getPropertyScore();
		return score;
	}
	
	/**
	 * @author Eric Pelletier
	 * @method adds the bonus option to kingdomino
	 * @param name
	 */
	
	public static void AddBonusOption(String name) {
		KingdominoApplication.getKingdomino().addBonusOption(name);
	}
	
	
	/**
	 * @author Eric Pelletier
	 * @method removes selected bonus option
	 * @param name
	 */
	
	public static void RemoveBonusOption(String name) {
		BonusOption bonus = new BonusOption(name, new Kingdomino());
		KingdominoApplication.getKingdomino().removeBonusOption(bonus);
	}
	
	/**
	 * Feature: Method to order players based on previous draft 
	 * selection and reveal the next draft <br>
	 * Gherkin Feature: SortAndRevealDraftStepDefinitions.java <br>
	 * 
	 * @author Eric Pelletier <br>
	 */
	
	
	public static void orderPlayerAndRevealDraft() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Player> players = game.getPlayers();
		List<Player> orderedPlayers = new ArrayList<Player>();
		List<Integer> playersOrder = new ArrayList<Integer>();
		for(Player p : players) {
			DominoSelection domSelect = p.getDominoSelection();
			int id = domSelect.getDomino().getId();
			playersOrder.add(id);
 		}
		Collections.sort(playersOrder);
		int place = 0;
		for(Integer i : playersOrder) {
			for(Player d : players) {
				if(d.getDominoSelection().getDomino().getId() == i) {
					orderedPlayers.add(d);
					break;
				}
			}
		}
		for(Player p : orderedPlayers) {
			game.addOrMovePlayerAt(p, place);
			if(place == 0) {
				game.setCurrentPlayer(p);
			}
			if(place ==1) {
				game.setNextPlayer(p);
			}
			place++;
		}
		revealNextDraft();
		
	}
	
	
}
