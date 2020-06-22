package fxml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.controller.SaveAndLoadController;
import ca.mcgill.ecse223.kingdomino.model.BonusOption;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Gameplay.GamestatusMidGame;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import helper.HelperFunctions;
import helper.Tile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.input.MouseEvent;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;

/***
 * 
 * @author Delia Bretan
 *
 */

public class GameBoardController implements Initializable {
	/**
	 * @param panel                AnchorPane. everything to be displayed on screen
	 *                             will be on this object
	 * @param cursor               fxml.Cursor. the cursor used in project. can
	 *                             either be the current player's sword or the
	 *                             selected domino
	 * @param font                 String. my favorite font. ill use it everywhere
	 * @param static               kingdomino Kingdomino. the kingdomino object
	 * @param static               gameplay Gameplay. the gameplay object
	 * @param static               game Game. the current game object
	 * @param currentOrientation   int. the current orientation of the domino
	 * @param tilesInKingdom       Rectangle[][]. the tiles a player has in his
	 *                             respective kingdom
	 * @param isSelectionPreplaced boolean. true if player preplaced a domino in his
	 *                             kingdom
	 * @param location             int. the coordinate of the preplaced domino
	 * @param isHarmony            boolean. true if the game is set to be in harmony
	 * @param isMiddleKingdom	   boolean. true if the game is set to implement MiddleKingdom
	 */
	@FXML
	public AnchorPane panel;
	private fxml.Cursor cursor;
	private String font = "Matura MT Script Capitals";
	private static Kingdomino kingdomino;
	private static Gameplay gameplay;
	private static Game game;
	private int currentOrientation = 0;
	private Rectangle[][] tilesInKingdom;
	boolean isSelectionPreplaced = false;
	int location = 0;
	boolean isHarmony = false;
	boolean isMiddleKingdom = false;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// preloads all images
		AssetManager.preloadAllGameAssets();
		// display welcome page
		setupWelcomePage();
	}

	/**
	 * @author Delia Bretan
	 * @Method displays the welcome page (page with kingdomino logo)
	 * @goesTo setupNewOrLoadGamePage();
	 */
	private void setupWelcomePage() {
		// changes background
		panel.setBackground(AssetManager.getWelcomeBackground());
		// sets up the cursor to the red sword
		haveCursorRunning();

		// display kingdomino logo
		Rectangle kingdominoLogo = new Rectangle();
		kingdominoLogo.setHeight(348);
		kingdominoLogo.setWidth(1516);
		kingdominoLogo.setLayoutX(900 - (kingdominoLogo.getWidth() / 2));
		kingdominoLogo.setLayoutY(250 - kingdominoLogo.getHeight() / 2);
		kingdominoLogo.setFill(AssetManager.getKingdominoLogo());
		addToPane(kingdominoLogo);

		// display greeting message
		Label startText = new Label("Click Anywhere to Play!");
		startText.setFont(new Font(font, 80));
		startText.setTextFill(Paint.valueOf("gold"));
		startText.setLayoutX(450);
		startText.setLayoutY(700);
		addToPane(startText);

		// event click. display page with load/new game option
		panel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				panel.setOnMouseClicked(null);
				setupNewOrLoadGamePage();
			}
		});
	}
	

	
//	private void provideUserProfile() {
//		// displays the load button
//		CustomButton userProfileButton = new CustomButton(540, 170, "User Profiles", "gold", 50, 0);
//		userProfileButton.setLayoutX(0);
//		userProfileButton.setLayoutY(0);
//		userProfileButton.setImageFill(AssetManager.getKingBlue());
//		addToPane(userProfileButton.getNode());
//		
//		EventHandler<MouseEvent> userProfileEvent = (MouseEvent me) -> {
//			String nameOfFileU = "src/main/java/ca/mcgill/ecse223/kingdomino/controller/Saves/outputUser.txt";
//			List<Map<String,String>> userProfiles = new ArrayList<>();
//			
//			try {
//				try {
//					File file = new File(nameOfFileU);
//					Scanner in = new Scanner(file);
//					
//					
//					while (in.hasNextLine()) {
//						Map<String, String> map = new HashMap<>();
//						userProfiles.add(map);
//						if (in.hasNextLine()) {
//							map.put("name", in.nextLine());
//						}
//						
//						if (in.hasNextLine()) {
//							map.put("playedGames", in.nextLine());
//						}
//						
//						if (in.hasNextLine()) {
//							map.put("wonGames", in.nextLine());
//						}
//					}
//					
//					in.close();
//				} catch (Exception e) {
//					System.out.println("error with load user profile");
//				}
//				
//				StringBuffer sb = new StringBuffer();
//				
//				for (Map<String, String> m : userProfiles) {
//					sb.append(m.toString());
//					sb.append('\n');
//				}
//				
//				CustomButton userHistory = new CustomButton(1200, 600, sb.toString(),
//						"gold", 130, 200);
//				
//				userHistory.setImageFill(AssetManager.getScrollGreen());
//				userHistory.setFont(font, 50);
//				userHistory.setLayoutX(300);
//				userHistory.setLayoutY(175);
//				EventHandler<MouseEvent> disable = new EventHandler<MouseEvent>() {
//
//					@Override
//					public void handle(MouseEvent event) {
//						userHistory.getNode().setVisible(false);
//					}
//				};
//				userHistory.setNewEvent(disable);
//				addToPane(userHistory.getNode());
//			} catch (Exception e) {
//				System.out.println(e.getCause());
//				newGamePrep();
//				CustomButton error = new CustomButton(1200, 600, "No user profile file found!" + "\n Click to dismiss",
//						"gold", 130, 200);
//				error.setImageFill(AssetManager.getScrollRed());
//				error.setFont(font, 50);
//				error.setLayoutX(300);
//				error.setLayoutY(175);
//				EventHandler<MouseEvent> disable = new EventHandler<MouseEvent>() {
//
//					@Override
//					public void handle(MouseEvent event) {
//						error.getNode().setVisible(false);
//					}
//				};
//				error.setNewEvent(disable);
//				addToPane(error.getNode());
//
//			}
//		};
//		
//		userProfileButton.setNewEvent(userProfileEvent);
//	}

	private void showAllUsers() {
		clearPane();

		// creates the back button.
		// will destroy the kingdom
		// takes user back to the newGamePrep();
		CustomButton back = new CustomButton(100, 70, "Back", "gold", 10, 0);
		back.setLayoutX(10);
		back.setLayoutY(10);
		back.setFont(font, 30);
		back.setImageFill(AssetManager.getScrollPurple());
		addToPane(back.getNode());
		EventHandler<MouseEvent> backToGamePrep = new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					setupNewOrLoadGamePage();
				}
			};
			back.setNewEvent(backToGamePrep);
	
			// sets title of the page
			Label title = new Label("hither are thy Kings");
			title.setFont(new Font(font, 80));
			title.setTextFill(Paint.valueOf("gold"));
			title.setLayoutX(580);
			addToPane(title);
	
			// creates the scroll pane which will hold gridPane
			// user can scroll down to see all dominoes
			ScrollPane scrollPane = new ScrollPane();
			scrollPane.pannableProperty().set(true);
			// disables horizontal scrolling
			scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
			scrollPane.setPrefHeight(750);
			scrollPane.setPrefWidth(1800);
			scrollPane.setLayoutX(0);
			scrollPane.setLayoutY(200);
			scrollPane.setStyle("-fx-background: #EE7E66");
	
			// gridPane holds all dominoes, sorted and all
			VBox box = new VBox();
			box.setSpacing(80);
			box.setPadding(new Insets(10, 450, 0, 420));
	
			// adding dominoes to gridpane
			for (Map<String,String> p : KingdominoController.loadUsers()) {
				VBox b = new VBox();
				
				Label name = new Label(p.get("name"));
				name.setAlignment(Pos.CENTER);
				name.setTextAlignment(TextAlignment.CENTER);
				name.setTextFill(Paint.valueOf("gold"));
				name.setFont(new Font(font, 50));
				
				Label playedGames = new Label("Games played : " + p.get("playedGames"));
				playedGames.setAlignment(Pos.CENTER);
				playedGames.setTextAlignment(TextAlignment.CENTER);
				playedGames.setFont(new Font(50));
				
				Label wonGames = new Label("Games won: " + p.get("wonGames"));
				wonGames.setAlignment(Pos.CENTER);
				wonGames.setTextAlignment(TextAlignment.CENTER);
				wonGames.setFont(new Font(50));
				
				b.getChildren().addAll(name, playedGames, wonGames);
				box.getChildren().add(b);
			}
			// adding gridpane to scrollpane
			scrollPane.setContent(box);
			// adding scroll pane to page
			addToPane(scrollPane);
	}

	/**
	 * @author Delia Bretan
	 * @Method displays page with 2 options: load game or start new game
	 * @goesTo newGamePrep();
	 * @todo load
	 * 
	 */
	private void setupNewOrLoadGamePage() {
		clearPane();
		panel.setBackground(AssetManager.getStartBackground());
		
		// displays the credits of Kindomino Game creators
		showCredit();
		
		// displays the load button
		CustomButton userProfileButton = new CustomButton(540, 170, "User Profile", "gold", 50, 0);
		userProfileButton.setLayoutX(630);
		userProfileButton.setLayoutY(400);
		userProfileButton.setImageFill(AssetManager.getScrollPurple());
		addToPane(userProfileButton.getNode());
		
		userProfileButton.setNewEvent((MouseEvent me) -> {
			// displays user profile button
			showAllUsers();
		});
		
		// displays the load button
		CustomButton loadGame = new CustomButton(540, 170, "Load Game", "gold", 50, 0);
		loadGame.setLayoutX(630);
		loadGame.setLayoutY(200);
		loadGame.setImageFill(AssetManager.getScrollRed());
		addToPane(loadGame.getNode());
		EventHandler<MouseEvent> loadEvent = new EventHandler<MouseEvent>() { 
			@Override
			public void handle(MouseEvent event) {
				try {
					kingdomino = SaveAndLoadController.loadKingdomino();
					game = kingdomino.getCurrentGame();
					gameplay = SaveAndLoadController.loadGameplay();
					ca.mcgill.ecse223.kingdomino.KingdominoApplication.setKingdomino(kingdomino);
					ca.mcgill.ecse223.kingdomino.KingdominoApplication.setGameplay(gameplay);
					selectDominoPage();
 
				} catch (Exception e) {
					System.out.println(e.getCause());
					newGamePrep();
					CustomButton error = new CustomButton(1200, 600, "No load file found!" + "\n Click to dismiss",
							"gold", 130, 200);
					error.setImageFill(AssetManager.getScrollRed());
					error.setFont(font, 50);
					error.setLayoutX(300);
					error.setLayoutY(175);
					EventHandler<MouseEvent> disable = new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent event) {
							error.getNode().setVisible(false);
						}
					};
					error.setNewEvent(disable);
					addToPane(error.getNode());

				}

			}
		};
		loadGame.setNewEvent(loadEvent);

		// displays the new game button
		CustomButton startNewGame = new CustomButton(540, 170, "New Game", "gold", 50, 0);
		startNewGame.setLayoutX(630);
		startNewGame.setLayoutY(600);
		startNewGame.setImageFill(AssetManager.getScrollGreen());
		addToPane(startNewGame.getNode());
		EventHandler<MouseEvent> newGameEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				newGamePrep();
			}
		};
		startNewGame.setNewEvent(newGameEvent);

	}

	/** 
	 * @author Zhanna Klimanova
	 * @Method to show the developers that worked on the project 
	 * @goesTo displayCredits()
	 */
	private void showCredit() {
		// displays the load button
		CustomButton showCredit = new CustomButton(540, 170, "Show Credit", "gold", 50, 0);
		showCredit.setLayoutX(630);
		showCredit.setLayoutY(0);
		showCredit.setImageFill(AssetManager.getScrollYellow());
		addToPane(showCredit.getNode());
		
		showCredit.setNewEvent((MouseEvent event) -> {
			displayCredits();
		});

	}
	
	/**
	 * @author Zhanna Klimanova
	 * @Method creditPage 
	 */
	private void displayCredits() {
		clearPane(); 
		
		CustomButton backButton = new CustomButton(100, 70, "Back", "gold", 10, 0);
		backButton.setLayoutX(10);
		backButton.setLayoutY(10);
		backButton.setFont(font, 30);
		backButton.setImageFill(AssetManager.getScrollYellow());
		addToPane(backButton.getNode());
		
		EventHandler<MouseEvent> backToMainPage = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				setupNewOrLoadGamePage(); 
			}
		};
		backButton.setNewEvent(backToMainPage);
		
		Label credits = new Label("The Kingdomino Developers");
			credits.setFont(new Font(font, 80));
			credits.setTextFill(Paint.valueOf("gold"));
			credits.setLayoutX(250);
			addToPane(credits);
			
		Label showNames = new Label(KingdominoController.getCredit());
			showNames.setFont(new Font(font, 50));
			showNames.setTextFill(Paint.valueOf("gold"));
			showNames.setLayoutX(250);
			showNames.setLayoutY(100);
			addToPane(showNames);
	}
	
		
	/**
	 * @author Delia Bretan
	 * @Method displays the new game settings (name, character selection and bonus)
	 * @goesTo setBonusOptions();
	 * @goesTo showAllDominoes();
	 * @goesTo perpNewGame(String[] { blue, green, yellow, red });
	 */
	private void newGamePrep() {
		clearPane();

		// sets title of page
		Label enterName = new Label("Enter thy name");
		enterName.setFont(new Font(font, 80));
		enterName.setLayoutX(650);
		enterName.setLayoutY(50);
		enterName.setTextFill(Paint.valueOf("gold"));
		addToPane(enterName);

		// sets the red king image + his text field (for name)
		Rectangle redKingImage = new Rectangle(302, 302);
		redKingImage.setLayoutX(145);
		redKingImage.setLayoutY(250);
		redKingImage.setFill(AssetManager.getKingRed());
		addToPane(redKingImage);

		TextField redKingName = new TextField();
		redKingName.setText("Red King");
		redKingName.setFont(new Font(font, 20));
		redKingName.setLayoutX(165);
		redKingName.setLayoutY(570);
		redKingName.setMinSize(262, 15);
		redKingName.setMaxSize(262, 50);
		addToPane(redKingName);

		// sets the blue king image + his text field (for name)
		Rectangle blueKingImage = new Rectangle(302, 302);
		blueKingImage.setLayoutX(547);
		blueKingImage.setLayoutY(250);
		blueKingImage.setFill(AssetManager.getKingBlue());
		addToPane(blueKingImage);

		TextField blueKingName = new TextField();
		blueKingName.setText("Blue King");
		blueKingName.setFont(new Font(font, 20));
		blueKingName.setLayoutX(567);
		blueKingName.setLayoutY(570);
		blueKingName.setMinSize(262, 15);
		blueKingName.setMaxSize(262, 50);
		addToPane(blueKingName);

		// sets the green king image + his text field (for name)
		Rectangle greenKingImage = new Rectangle(302, 302);
		greenKingImage.setLayoutX(949);
		greenKingImage.setLayoutY(250);
		greenKingImage.setFill(AssetManager.getKingGreen());
		addToPane(greenKingImage);

		TextField greenKingName = new TextField();
		greenKingName.setText("Green King");
		greenKingName.setFont(new Font(font, 20));
		greenKingName.setLayoutX(969);
		greenKingName.setLayoutY(570);
		greenKingName.setMinSize(262, 15);
		greenKingName.setMaxSize(262, 50);
		addToPane(greenKingName);

		// sets the yellow king image + his text field (for name)
		Rectangle yellowKingImage = new Rectangle(302, 302);
		yellowKingImage.setLayoutX(1351);
		yellowKingImage.setLayoutY(250);
		yellowKingImage.setFill(AssetManager.getKingYellow());
		addToPane(yellowKingImage);

		TextField yellowKingName = new TextField();
		yellowKingName.setText("Yellow King");
		yellowKingName.setFont(new Font(font, 20));
		yellowKingName.setLayoutX(1371);
		yellowKingName.setLayoutY(570);
		yellowKingName.setMinSize(262, 15);
		yellowKingName.setMaxSize(262, 50);
		addToPane(yellowKingName);

		// sets up the button to go to page with all domino
		CustomButton viewDominos = new CustomButton(370, 100, "View Domino", "gold", 50, 10);
		viewDominos.setFont(font, 40);
		viewDominos.setLayoutX(150);
		viewDominos.setLayoutY(800);
		viewDominos.setImageFill(AssetManager.getScrollPurple());
		addToPane(viewDominos.getNode());
		EventHandler<MouseEvent> viewDominoEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				showAllDominoes();
			}
		};
		viewDominos.setNewEvent(viewDominoEvent);

		// sets up the button to go to page with bonus options
		CustomButton extraOptions = new CustomButton(370, 100, "Bonus", "gold", 120, 10);
		extraOptions.setFont(font, 40);
		extraOptions.setLayoutX(1280);
		extraOptions.setLayoutY(800);
		extraOptions.setImageFill(AssetManager.getScrollPurple());
		addToPane(extraOptions.getNode());
		extraOptions.getNode().setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				setBonusOptions();
			}
		});

		// sets up the button to verify names and start game
		CustomButton play = new CustomButton(580, 200, "Start Game", "black", 80, 10);
		play.setFont(font, 80);
		play.setLayoutX(620);
		play.setLayoutY(750);
		play.setImageFill(AssetManager.getScrollYellow());
		addToPane(play.getNode());
		EventHandler<MouseEvent> selectDominoScreen = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					// names have to contain at least 1 character that is not a space
					// names cannot be longer than 22 characters (not counting spaces at beggining
					// and end)
					String red = redKingName.getText().trim();
					String blue = blueKingName.getText().trim();
					String green = greenKingName.getText().trim();
					String yellow = yellowKingName.getText().trim();
					if (red.equals("")) {
						throw new Exception("Invalid Player Name: Red King");
					} else if (red.length() > 21) {
						throw new Exception("Player Name Too Long: Red King");
					} else if (blue.equals("")) {
						throw new Exception("Invalid Player Name: Blue King");
					} else if (blue.length() > 21) {
						throw new Exception("Player Name Too Long: Blue King");
					} else if (green.equals("")) {
						throw new Exception("Invalid Player Name: Green King");
					} else if (green.length() > 21) {
						throw new Exception("Player Name Too Long: Green King");
					} else if (yellow.equals("")) {
						throw new Exception("Invalid Player Name: Yellow King");
					} else if (yellow.length() > 21) {
						throw new Exception("Player Name Too Long: Yellow King");
					} else if (red.equals(blue) || red.equals(green) || red.equals(yellow) || blue.equals(green)
							|| blue.equals(yellow) || green.equals(yellow)) {
						throw new Exception("Invalid Names: Existance of Duplicates");
					}

					perpNewGame(new String[] { blue, green, yellow, red });

				} catch (Exception e) {
					// error message if anything goes wrong
					CustomButton error = new CustomButton(1200, 600, e.getMessage() + "\n Click to dismiss", "gold",
							130, 200);
					error.setImageFill(AssetManager.getScrollRed());
					error.setFont(font, 50);
					error.setLayoutX(300);
					error.setLayoutY(175);
					EventHandler<MouseEvent> disable = new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent event) {
							error.getNode().setVisible(false);
						}
					};
					error.setNewEvent(disable);
					addToPane(error.getNode());
				}
			}
		};
		play.setNewEvent(selectDominoScreen);
	}

	/**
	 * @author Delia Bretan & Eric Pelletier
	 * @Method displays the page for the bonus options
	 * @goesTo setBonusOptions();
	 */
	private void setBonusOptions() {
		clearPane();
	
		// sets title of the page
		Label title = new Label("Choose thy bonus");
		title.setFont(new Font(font, 80));
		title.setTextFill(Paint.valueOf("gold"));
		title.setLayoutX(570);
		addToPane(title);

		// game count info
		Label harmony = new Label("Harmony    ");
		harmony.setFont(new Font(font, 80));
		harmony.setTextFill(Paint.valueOf("gold"));
		harmony.setLayoutX(450);
		harmony.setLayoutY(400);
		addToPane(harmony);
		
		Label MiddleKingdom = new Label("Middle Kingdom");
		MiddleKingdom.setFont(new Font(font, 80));
		MiddleKingdom.setTextFill(Paint.valueOf("gold"));
		MiddleKingdom.setLayoutX(450);
		MiddleKingdom.setLayoutY(600);
		addToPane(MiddleKingdom);

		CustomButton harmonyCheck;
		
		// if isHarmony true, box will be ticked
		//Eric: fixed the method to make it change the game's bonus options
		if (isHarmony) {
			harmonyCheck = new CustomButton(100, 100, "X", "black", 32, 15);
			KingdominoController.AddBonusOption("Harmony");
		} else {
			harmonyCheck = new CustomButton(100, 100, "", "black", 10, -10);
			KingdominoController.RemoveBonusOption("Harmony");
		}
		
		harmonyCheck.setFont(font, 55);
		harmonyCheck.setLayoutX(1150);
		harmonyCheck.setLayoutY(410);
		addToPane(harmonyCheck.getNode());
		harmonyCheck.getNode().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				isHarmony = !isHarmony;
				setBonusOptions();
			}
		});
		
		//Eric:Created a second bonus option
		CustomButton MiddleKingdomCheck;
		
		// if isMiddleKingdom true, box will be ticked
		if (isMiddleKingdom) {
			MiddleKingdomCheck = new CustomButton(100, 100, "X", "black",32,15);
			KingdominoController.AddBonusOption("Middle Kingdom");
		} else {
			MiddleKingdomCheck = new CustomButton(100, 100, "", "black", 10, -10);
			KingdominoController.RemoveBonusOption("Middle Kingdom");
		}
		MiddleKingdomCheck.setFont(font, 55);
		MiddleKingdomCheck.setLayoutX(1150);
		MiddleKingdomCheck.setLayoutY(600);
		addToPane(MiddleKingdomCheck.getNode());
		MiddleKingdomCheck.getNode().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				isMiddleKingdom = !isMiddleKingdom;
				setBonusOptions();
			}
		});
		

		// creates the back button.
		// will destroy the kingdom
		// takes user back to the newGamePrep();
		CustomButton back = new CustomButton(100, 70, "Back", "gold", 10, 0);
		back.setLayoutX(10);
		back.setLayoutY(10);
		back.setFont(font, 30);
		back.setImageFill(AssetManager.getScrollPurple());
		addToPane(back.getNode());
		EventHandler<MouseEvent> backToGamePrep = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				newGamePrep();
			}
		};
		back.setNewEvent(backToGamePrep);

	}

	/**
	 * @author Delia Bretan
	 * @Method displays all the dominoes in a scroll panel
	 * @goesTo back to the newGamePrep();
	 */
	private void showAllDominoes() {
		clearPane();
		// creates a kingdomino just for the sake of having the dominoes.
		// will be deleted once the user leaves the page
		Kingdomino kingdomino = new Kingdomino();
		kingdomino.addAllGame((new Game(48, kingdomino)));
		kingdomino.setCurrentGame(kingdomino.getAllGame(0));
		Game game = kingdomino.getCurrentGame();
		HelperFunctions.createAllDominoes(game);

		// creates the back button.
		// will destroy the kingdom
		// takes user back to the newGamePrep();
		CustomButton back = new CustomButton(100, 70, "Back", "gold", 10, 0);
		back.setLayoutX(10);
		back.setLayoutY(10);
		back.setFont(font, 30);
		back.setImageFill(AssetManager.getScrollPurple());
		addToPane(back.getNode());
		EventHandler<MouseEvent> backToGamePrep = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				kingdomino.delete();
				newGamePrep();
			}
		};
		back.setNewEvent(backToGamePrep);

		// sets title of the page
		Label title = new Label("hither are thy domino");
		title.setFont(new Font(font, 80));
		title.setTextFill(Paint.valueOf("gold"));
		title.setLayoutX(580);
		addToPane(title);

		// creates the scroll pane which will hold gridPane
		// user can scroll down to see all dominoes
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.pannableProperty().set(true);
		// disables horizontal scrolling
		scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setPrefHeight(750);
		scrollPane.setPrefWidth(1800);
		scrollPane.setLayoutX(0);
		scrollPane.setLayoutY(200);
		scrollPane.setStyle("-fx-background: #EE7E66");

		// gridPane holds all dominoes, sorted and all
		GridPane box = new GridPane();
		box.setVgap(20);
		box.setHgap(20);
		box.setPadding(new Insets(10, 450, 0, 420));

		// adding dominoes to gridpane
		for (Domino domino : game.getAllDominos()) {
			BoardDomino domin = new BoardDomino(domino);
			domin.setSize(400);

			Label id = new Label("" + domino.getId());
			id.setFont(new Font(font, 80));
			id.setTextFill(Paint.valueOf("black"));

			box.add(domin.getNode(), 1, domino.getId() - 1);
			box.add(id, 0, domino.getId() - 1);
		}
		// adding gridpane to scrollpane
		scrollPane.setContent(box);
		// adding scroll pane to page
		addToPane(scrollPane);
	}

	/**
	 * @author Delia Bretan
	 * @Method will setup a new game with the input users and players
	 * @param userNames. the names of the users
	 * @goto selectDominoPage();
	 */
	private void perpNewGame(String[] userNames) {
		// it works. dont touch it
		KingdominoApplication.getItFromTheHolySource(userNames);
		gameplay = KingdominoApplication.resetGameplay();
		kingdomino = KingdominoApplication.getKingdomino();
		game = kingdomino.getCurrentGame();
		
		selectDominoPage();
	}

	/**
	 * @author Delia Bretan
	 * @Method displays the correct pages according to the game status
	 * @goesTo displayKingdomPage(); if
	 *         GamestatusMidGame.PlacingOrDiscardingSelectedDomino
	 * @goesTo displayNextDraft(); if GamestatusInitializing.SelectingFirstDomino or
	 *         GamestatusMidGame.SelectingNextDomino
	 * @goesTo displayEndPage(); if Gamestatus.EndGame
	 */
	private void selectDominoPage() {
		List<Map<String, String>> profiles = KingdominoController.loadUsers();
		for (Map<String, String> p : profiles) {
			for (User u : KingdominoApplication.getKingdomino().getUsers()) {
				if (p.get("name").equalsIgnoreCase(u.getName())) {
					u.setPlayedGames(Integer.valueOf(p.get("playedGames")));
					u.setWonGames(Integer.valueOf(p.get("wonGames")));
				}
			}
		}
		
		clearPane();
		panel.setBackground(AssetManager.getGameBackground());
		System.out.println(gameplay.getGamestatusFullName());

		// checks if its the endgame or not
		if (gameplay.getGamestatus() != Gameplay.Gamestatus.EndGame) {
			// gets draft ready
			gameplay.draftReady();
			// if its time to place/discard domino, go to displayKingdomPage();
			if (gameplay.getGamestatusMidGame() == GamestatusMidGame.PlacingOrDiscardingSelectedDomino) {
				displayKingdomPage();
			} else {

				// if its time to select domino, display next draft
//				if (gameplay.getGamestatusInitializing() == Gameplay.GamestatusInitializing.SelectingFirstDomino
//						|| gameplay.getGamestatusMidGame() == Gameplay.GamestatusMidGame.SelectingNextDomino) {
				displayAllKings();
				displayNextDraft();
				displayPlayerScores(); //added my method here to display scores -antonia
//				}
			}
		} else {
			// game is over, display end page
			displayEndPage();
		}

	}

	/**
	 * @author Delia Bretan
	 * @Method displays the kingdom of 1 player. player can place/discard domino
	 * @goesTo displayUserPage(). the user info for that player
	 * @goesTo displayKingdomPage(); resets the page (and the board) for the player
	 * @calls confirmPreplacedDomino(); calls a method to confirm if the placed
	 *        domino is valid
	 * @todo save
	 */
	private void displayKingdomPage() {
		List<Map<String, String>> profiles = KingdominoController.loadUsers();
		for (Map<String, String> p : profiles) {
			for (User u : KingdominoApplication.getKingdomino().getUsers()) {
				if (p.get("name").equalsIgnoreCase(u.getName())) {
					u.setPlayedGames(Integer.valueOf(p.get("playedGames")));
					u.setWonGames(Integer.valueOf(p.get("wonGames")));
				}
			}
		}
		
		clearPane();

		// adds the castle
		Rectangle castle = new Rectangle(100, 100);
		// places the title of the page
		Label placeDo = new Label("Click And Place thy Domino");
		placeDo.setLayoutX(20);
		placeDo.setFont(new Font(font, 50));
		placeDo.setMaxWidth(450);
		placeDo.setWrapText(true);
		placeDo.setTextFill(Paint.valueOf("gold"));
		addToPane(placeDo);

		// places the user image and kingdom name
		// enables the method to display user info
		String color = "gold";
		CustomButton king = new CustomButton(302, 302, "", "black", 0, 0);
		addToPane(king.getNode());
		switch (game.getCurrentPlayer().getColor()) {
		case Pink: {
			king.setImageFill(AssetManager.getKingRed());
			color = "#ff0707";
			castle.setFill(AssetManager.getCastleRed());
			king.setNewEvent(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					displayUserPage(game.getCurrentPlayer().getUser(), AssetManager.getKingRed());

				}
			});
			break;
		}
		case Blue: {
			king.setImageFill(AssetManager.getKingBlue());
			color = "blue";
			castle.setFill(AssetManager.getCastleBlue());
			king.setNewEvent(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					displayUserPage(game.getCurrentPlayer().getUser(), AssetManager.getKingBlue());

				}
			});
			break;
		}
		case Green: {
			king.setImageFill(AssetManager.getKingGreen());
			color = "#00FF00";
			castle.setFill(AssetManager.getCastleGreen());
			king.setNewEvent(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					displayUserPage(game.getCurrentPlayer().getUser(), AssetManager.getKingGreen());

				}
			});
			break;
		}
		case Yellow: {
			king.setImageFill(AssetManager.getKingYellow());
			color = "yellow";
			castle.setFill(AssetManager.getCastleYellow());
			king.setNewEvent(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					displayUserPage(game.getCurrentPlayer().getUser(), AssetManager.getKingYellow());

				}
			});
			break;
		}
		}

		king.setLayoutX(1424);
		king.setLayoutY(100);
//		addToPane(king.getNode());
		Ellipse blush = new Ellipse(10, 5);
		blush.setFill(Paint.valueOf("pink"));
		blush.setVisible(false);

		king.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				blush.setVisible(true);
				blush.setLayoutX(king.getNode().getLayoutX() + 125);
				blush.setLayoutY(king.getNode().getLayoutY() + 130);

			}
		});
		king.getNode().setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				blush.setVisible(false);
			}
		});

		// displays kingdom name
		Label title = new Label(game.getCurrentPlayer().getColor() + " Kingdom");
		title.setFont(new Font(font, 35));
		title.setTextFill(Paint.valueOf(color));
		HBox container = new HBox();
		container.setMinWidth(302);
		container.setMaxWidth(302);
		container.setLayoutX(1424);
		container.setLayoutY(400);
		container.getChildren().add(title);
		container.setAlignment(Pos.CENTER);
		addToPane(container);

//		Label score = new Label("Score: " + game.getCurrentPlayer().getPropertyScore());
//		score.setFont(new Font(font,50));
//		score.setTextFill(Paint.valueOf(color));
//		score.setLayoutX(1424);
//		score.setLayoutY(500);
//		addToPane(score);

		// resets the orientation of the domino
		currentOrientation = 0;
		// add the domino on the page
		BoardDomino selected = new BoardDomino(game.getCurrentPlayer().getDominoSelection().getDomino());
		selected.setSize(150);
		selected.getNode().setLayoutX(75);
		selected.getNode().setLayoutY(275);
		addToPane(selected.getNode());
		cursor.changeToDominoOfPlayer(selected);
		selected.getNode().setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				currentOrientation = 0;
				cursor.changeOrientation(currentOrientation);
				displayKingdomPage();
			}
		});

		// creates rotate button and enables that action
		CustomButton rotate = new CustomButton(400, 100, "Rotate Domino", "gold", 15, 0);
		rotate.setFont(font, 50);
		rotate.setLayoutX(25);
		rotate.setLayoutY(675);
		rotate.setImageFill(AssetManager.getScrollPurple());
		addToPane(rotate.getNode());
		EventHandler<MouseEvent> rotateEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (!isSelectionPreplaced) {
					++currentOrientation;
					cursor.changeOrientation(currentOrientation);
				}

			}
		};
		rotate.setNewEvent(rotateEvent);
		rotate.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				rotate.setColorOfText("black");
			}
		});
		rotate.getNode().setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				rotate.setColorOfText("gold");
			}
		});

		// creates confirmLocationOfDomino button and enables that action
		CustomButton confirm = new CustomButton(400, 100, "Confirm Location", "gold", 15, 0);
		confirm.setFont(font, 45);
		confirm.setLayoutX(25);
		confirm.setLayoutY(525);
		confirm.setImageFill(AssetManager.getScrollGreen());
		addToPane(confirm.getNode());
		EventHandler<MouseEvent> confirmEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				confirmPreplacedDomino(selected, location);
			}
		};
		confirm.setNewEvent(confirmEvent);
		confirm.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				confirm.setColorOfText("black");
			}
		});
		confirm.getNode().setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				confirm.setColorOfText("gold");
			}
		});

		// creates resetBoard button and enables that action
		CustomButton reset = new CustomButton(400, 100, "Reset Board", "black", 50, 0);
		reset.setFont(font, 45);
		reset.setLayoutX(1375);
		reset.setLayoutY(675);
		reset.setImageFill(AssetManager.getScrollYellow());
		addToPane(reset.getNode());
		EventHandler<MouseEvent> resetEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				displayKingdomPage();
			}
		};
		reset.setNewEvent(resetEvent);
		reset.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				reset.setColorOfText("gold");
			}
		});
		reset.getNode().setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				reset.setColorOfText("black");
			}
		});

		// creates discardDomino button and enables that action
		CustomButton discard = new CustomButton(400, 100, "Discard Domino", "gold", 15, 0);
		discard.setFont(font, 45);
		discard.setLayoutX(25);
		discard.setLayoutY(825);
		discard.setImageFill(AssetManager.getScrollRed());
		addToPane(discard.getNode());
		EventHandler<MouseEvent> discardEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				if (!isSelectionPreplaced) {
					CustomButton error = new CustomButton(1200, 600,
							"Domino Not On Board!\nClick A Tile To Place " + "\n Click to dismiss", "gold", 130, 130);
					error.setImageFill(AssetManager.getScrollRed());
					error.setFont(font, 50);
					error.setLayoutX(300);
					error.setLayoutY(175);
					EventHandler<MouseEvent> disable = new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent event) {
							error.getNode().setVisible(false);
						}
					};
					error.setNewEvent(disable);
					addToPane(error.getNode());

				} else if (!gameplay.isDiscardValid()) {
					// displays error message
					CustomButton error = new CustomButton(1200, 600,
							"Cannot Discard Domino!\nReset The Board Place Thy Domino" + "\n Click to dismiss", "gold",
							130, 130);
					error.setImageFill(AssetManager.getScrollRed());
					error.setFont(font, 50);
					error.setLayoutX(300);
					error.setLayoutY(175);
					EventHandler<MouseEvent> disable = new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent event) {
							error.getNode().setVisible(false);
						}
					};
					error.setNewEvent(disable);
					addToPane(error.getNode());

				} else {
					gameplay.discardDomino();
					selectDominoPage();
				}

			}
		};
		discard.setNewEvent(discardEvent);

		discard.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				discard.setColorOfText("black");
			}
		});
		discard.getNode().setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				discard.setColorOfText("gold");
			}
		});

		// creates save button and enables that action
		CustomButton save = new CustomButton(400, 100, "Save Game", "black", 80, 0);
		save.setFont(font, 45);
		save.setLayoutX(1375);
		save.setLayoutY(825);
		save.setImageFill(AssetManager.getScrollPurple());
		addToPane(save.getNode());
		EventHandler<MouseEvent> saveEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				SaveAndLoadController.save(kingdomino, gameplay);
				CustomButton error = new CustomButton(1200, 600,
						" GameSaved!" + "\n Click to dismiss", "gold", 130, 130);
				error.setImageFill(AssetManager.getScrollGreen());
				error.setFont(font, 80);
				error.setLayoutX(300);
				error.setLayoutY(175);
				EventHandler<MouseEvent> disable = new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						error.getNode().setVisible(false);
					}
				};
				error.setNewEvent(disable);
				addToPane(error.getNode());
			}
		};
		save.setNewEvent(saveEvent);
		save.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				save.setColorOfText("gold");
			}
		});
		save.getNode().setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				save.setColorOfText("black");
			}
		});

		addToPane(blush);
		// sets the kingdom board
		setRawKingdomTiles(castle);

	}

	/**
	 * @author Delia Bretan
	 * @param castle Rectangle. the castle of the player
	 */
	private void setRawKingdomTiles(Rectangle castle) {
		isSelectionPreplaced = false;

		// creates a 2d array of the tiles.
		tilesInKingdom = new Rectangle[9][9];
		// gets the info about the tiles of the player's kingdom
		Tile[][] tiles = HelperFunctions.getKingdomAsTiles(game.getCurrentPlayer(), DominoStatus.PlacedInKingdom);
		// gridpane to place all the tiles
		GridPane kingdom = new GridPane();
		// current tile we're working with
		Rectangle tile;
		for (int i = 0; i < 81; i++) {
			final Integer innerMi = new Integer(i);
			// sets the current tile to the corresponding tile of the kingdom
			tile = new Rectangle(100, 100);
			tilesInKingdom[i / 9][i % 9] = tile;

			// if the tile at a certain location is empty, display default tile
			if (tiles[i / 9][i % 9] != null) {
				// check if tile is actually a castle
				if (tiles[i / 9][i % 9].getTerrainType() == null) {
					tile = castle;
				} else {
					// if not empty, display the current tile's corresponding image
					tile.setFill(
							getProperTileAsset(tiles[i / 9][i % 9].getTerrainType(), tiles[i / 9][i % 9].getCrowns()));
				}
			} else {
				// if the tile at a certain location is empty, display default tile
				tile.setFill(AssetManager.getEmptyTileUnselected());
				// if empty tile is hovered, the tile will change skin
				// also, depending on the orientation of the domino, one adjacent tile will also
				// change skin if it is also empty
				tile.setOnMouseEntered(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						// resets all the tiles to default
						for (int i = 0; i < 81; i++) {
							final Integer innerMi2 = new Integer(i);
							if (tiles[innerMi2 / 9][innerMi2 % 9] == null) {
								tilesInKingdom[innerMi2 / 9][innerMi2 % 9]
										.setFill(AssetManager.getEmptyTileUnselected());
							}
						}

						// sets current tile to empty/selected
						tilesInKingdom[innerMi / 9][innerMi % 9].setFill(AssetManager.getEmptyTileSelected());
						// sets the good adjacent tile to empty/selected depending on your orientation
						switch (currentOrientation % 4) {
						case 0:
							try {
								if (tiles[(innerMi.intValue() / 9)][(innerMi % 9) + 1] == null) {
									tilesInKingdom[(innerMi.intValue() / 9)][(innerMi % 9) + 1]
											.setFill(AssetManager.getEmptyTileSelected());
								}

							} catch (Exception e) {
							}
							break;
						case 1:
							try {
								if (tiles[(innerMi.intValue() / 9) + 1][(innerMi % 9)] == null) {
									tilesInKingdom[(innerMi.intValue() / 9) + 1][(innerMi % 9)]
											.setFill(AssetManager.getEmptyTileSelected());
								}

							} catch (Exception e) {
							}
							break;
						case 2:
							try {
								if (tiles[(innerMi.intValue() / 9)][(innerMi % 9) + 1] == null) {
									tilesInKingdom[(innerMi.intValue() / 9)][(innerMi % 9) + 1]
											.setFill(AssetManager.getEmptyTileSelected());
								}

							} catch (Exception e) {
							}
							break;
						case 3:
							try {
								if (tiles[(innerMi.intValue() / 9) + 1][(innerMi % 9)] == null) {

									tilesInKingdom[(innerMi.intValue() / 9) + 1][(innerMi % 9)]
											.setFill(AssetManager.getEmptyTileSelected());
								}

							} catch (Exception e) {
							}
							break;
						}
					}

				});
			}
			// if clicked, preplace domino there if domino hasn't been preplaced
			tile.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if (!isSelectionPreplaced) {
						location = innerMi;
						preplaceDomino(kingdom, innerMi, currentOrientation);
						isSelectionPreplaced = true;
					} else {
						// domino has already been preplaced, throwns error
						CustomButton error = new CustomButton(1200, 600,
								"Domino Already On Board!\nReset Board To Place Again" + "\n Click to dismiss", "gold",
								130, 130);
						error.setImageFill(AssetManager.getScrollRed());
						error.setFont(font, 50);
						error.setLayoutX(300);
						error.setLayoutY(175);
						EventHandler<MouseEvent> disable = new EventHandler<MouseEvent>() {

							@Override
							public void handle(MouseEvent event) {
								error.getNode().setVisible(false);
							}
						};
						error.setNewEvent(disable);
						addToPane(error.getNode());
					}

				}
			});
			// add current tile
			kingdom.add(tile, i % 9, i / 9);
		}
		// add kingdom to page
		kingdom.setLayoutY(25);
		kingdom.setLayoutX(450);
		addToPane(kingdom);
	}

	/**
	 * @Author Delia Bretan
	 * @Method adds the domino on the board according to orientation and tile
	 *         clicked
	 * @param kingdom     GridPane. the current kingdom of player with all tiles
	 *                    filled in
	 * @param location    int. the tile you clicked
	 * @param orientation int. the orientation of your domino
	 */
	private void preplaceDomino(GridPane kingdom, int location, int orientation) {
		Domino domino = game.getCurrentPlayer().getDominoSelection().getDomino();
		Rectangle left = new Rectangle(100, 100);
		left.setFill(getProperTileAsset(domino.getLeftTile(), domino.getLeftCrown()));
		Rectangle right = new Rectangle(100, 100);
		right.setFill(getProperTileAsset(domino.getRightTile(), domino.getRightCrown()));

		switch (orientation % 4) {
		case 0:
			kingdom.add(left, location % 9, location / 9);
			kingdom.add(right, (location % 9) + 1, location / 9);
			break;
		case 1:
			kingdom.add(left, location % 9, location / 9);
			kingdom.add(right, (location % 9), (location / 9) + 1);
			break;
		case 2:
			kingdom.add(left, (location % 9) + 1, location / 9);
			kingdom.add(right, (location % 9), (location / 9));
			break;
		case 3:
			kingdom.add(left, location % 9, (location / 9) + 1);
			kingdom.add(right, (location % 9), (location / 9));
			break;
		}

	}

	/**
	 * @author Delia Bretan
	 * @method confirms is domino is placed correctly. calls gameplay method
	 * @param selected BoardDomino. your current domino selection
	 * @param location int. location where you placed your domino
	 * @goesTo selectDominoPage() is domino correctly placed
	 */
	private void confirmPreplacedDomino(BoardDomino selected, int location) {
		// check if there is a domino paced on board. throws error message
		if (isSelectionPreplaced) {
			// sets the X and Y of left end of domino
			gameplay.setX(((location % 9) - 4));
			gameplay.setY((4 - (location / 9)));
			// sets the X and Y of right end of domino depending on your orientation
			switch (currentOrientation % 4) {
			case 0:
				gameplay.setDir("right");
				break;
			case 1:
				gameplay.setDir("down");
				break;
			case 2:
				gameplay.setX(gameplay.getX() + 1);
				gameplay.setDir("left");
				break;
			case 3:
				gameplay.setY(gameplay.getY() - 1);
				gameplay.setDir("up");
				break;
			}

			// if domino is places correctly, go to selectDominoPage()
			if (gameplay.placeDomino()) {
				selectDominoPage();
			} else {
				// error message, placement not valid
				CustomButton error = new CustomButton(1200, 600,
						"Domino Placement Not Valid\nReset Board And Try Again" + "\n Click to dismiss", "gold", 130,
						130);
				error.setImageFill(AssetManager.getScrollRed());
				error.setFont(font, 50);
				error.setLayoutX(300);
				error.setLayoutY(175);
				EventHandler<MouseEvent> disable = new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						error.getNode().setVisible(false);
					}
				};
				error.setNewEvent(disable);
				addToPane(error.getNode());
			}
		} else {
			// error message. domino not on board
			CustomButton error = new CustomButton(1200, 600, "Selected Domino Not On Board!" + "\n Click to dismiss",
					"gold", 130, 200);
			error.setImageFill(AssetManager.getScrollRed());
			error.setFont(font, 50);
			error.setLayoutX(300);
			error.setLayoutY(175);
			EventHandler<MouseEvent> disable = new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					error.getNode().setVisible(false);
				}
			};
			error.setNewEvent(disable);
			addToPane(error.getNode());
		}

	}

	/**
	 * @author Delia Bretan
	 * @method displays all the kings and their name on board + if it is their turn
	 *         or not
	 * @goesTo displayUserPage. displays the user page of the clicked user
	 */
	private void displayAllKings() {
		Ellipse blush = new Ellipse(10, 5);
		blush.setFill(Paint.valueOf("pink"));
		blush.setVisible(false);

		// sets blue king and its name on board + if it is this player's turn
		// enables player to see this user's profile
		Rectangle blueKingImage = new Rectangle(302, 302);
		blueKingImage.setLayoutX(1498);
		blueKingImage.setFill(AssetManager.getKingBlue());
		addToPane(blueKingImage);
		blueKingImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				displayUserPage(HelperFunctions.findPlayer("blue").getUser(), AssetManager.getKingBlue());
			}
		});

		blueKingImage.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				blush.setLayoutX(blueKingImage.getLayoutX() + 125);
				blush.setLayoutY(blueKingImage.getLayoutY() + 130);
				blush.setVisible(true);
			}
		});
		blueKingImage.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				blush.setVisible(false);
			}
		});

		String blueText = HelperFunctions.findPlayer("blue").getUser().getName();
		blueText += (HelperFunctions.findPlayer("blue").getUser().equals(game.getCurrentPlayer().getUser())) ? "\nChoose!" : "\nWait";
		Label blueKingName = new Label(blueText);
		blueKingName.setLayoutX(1498);
		blueKingName.setLayoutY(290);
		blueKingName.setMaxWidth(302);
		blueKingName.setFont(new Font(font, 30));
		blueKingName.setTextFill(Paint.valueOf("blue"));
		blueKingName.setWrapText(true);
		addToPane(blueKingName);

		// sets green king and its name on board + if it is this player's turn
		// enables player to see this user's profile
		Rectangle greenKingImage = new Rectangle(302, 302);
		greenKingImage.setLayoutX(0);
		greenKingImage.setLayoutY(0);
		greenKingImage.setFill(AssetManager.getKingGreenFlipped());
		addToPane(greenKingImage);
		greenKingImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				displayUserPage(HelperFunctions.findPlayer("green").getUser(), AssetManager.getKingGreen());
			}
		});
		greenKingImage.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				blush.setLayoutX(greenKingImage.getLayoutX() + 180);
				blush.setLayoutY(greenKingImage.getLayoutY() + 130);
				blush.setVisible(true);
			}
		});
		greenKingImage.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				blush.setVisible(false);
			}
		});

		String greenText = HelperFunctions.findPlayer("green").getUser().getName();
		greenText += (HelperFunctions.findPlayer("green").getUser().equals(game.getCurrentPlayer().getUser())) ? "\nChoose!" : "\nWait";
		Label greenKingName = new Label(greenText);
		greenKingName.setLayoutX(5);
		greenKingName.setLayoutY(290);
		greenKingName.setMaxWidth(302);
		greenKingName.setFont(new Font(font, 30));
		greenKingName.setTextFill(Paint.valueOf("#00FF00"));
		greenKingName.setWrapText(true);
		addToPane(greenKingName);

		// sets yellow king and its name on board + if it is this player's turn
		// enables player to see this user's profile
		Rectangle yellowKingImage = new Rectangle(302, 302);
		yellowKingImage.setLayoutX(0);
		yellowKingImage.setLayoutY(648);
		yellowKingImage.setFill(AssetManager.getKingYellowFlipped());
		addToPane(yellowKingImage);
		yellowKingImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				displayUserPage(HelperFunctions.findPlayer("yellow").getUser(), AssetManager.getKingYellow());
			}
		});
		yellowKingImage.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				blush.setLayoutX(yellowKingImage.getLayoutX() + 180);
				blush.setLayoutY(yellowKingImage.getLayoutY() + 130);
				blush.setVisible(true);
			}
		});
		yellowKingImage.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				blush.setVisible(false);
			}
		});

		String yellowText = HelperFunctions.findPlayer("yellow").getUser().getName();
		yellowText += (HelperFunctions.findPlayer("yellow").getUser().equals(game.getCurrentPlayer().getUser())) ? "\nChoose!" : "\nWait";
		Label yellowKingName = new Label(yellowText);
		yellowKingName.setLayoutX(5);
		yellowKingName.setLayoutY(500);
		yellowKingName.setMaxWidth(302);
		yellowKingName.setFont(new Font(font, 30));
		yellowKingName.setTextFill(Paint.valueOf("yellow"));
		yellowKingName.setWrapText(true);
		addToPane(yellowKingName);

		// sets red king and its name on board + if it is this player's turn
		// enables player to see this user's profile
		Rectangle redKingImage = new Rectangle(302, 302);
		redKingImage.setLayoutX(1498);
		redKingImage.setLayoutY(648);
		redKingImage.setFill(AssetManager.getKingRed());
		addToPane(redKingImage);
		redKingImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				displayUserPage(HelperFunctions.findPlayer("pink").getUser(), AssetManager.getKingRed());
			}
		});
		redKingImage.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				blush.setLayoutX(redKingImage.getLayoutX() + 125);
				blush.setLayoutY(redKingImage.getLayoutY() + 130);
				blush.setVisible(true);
			}
		});
		redKingImage.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				blush.setVisible(false);
			}
		});

		String redText = HelperFunctions.findPlayer("pink").getUser().getName();
		redText += (HelperFunctions.findPlayer("pink").getUser().equals(game.getCurrentPlayer().getUser())) ? "\nChoose!" : "\nWait";
		Label redKingName = new Label(redText);
		redKingName.setLayoutX(1498);
		redKingName.setLayoutY(500);
		redKingName.setMaxWidth(302);
		redKingName.setFont(new Font(font, 30));
		redKingName.setTextFill(Paint.valueOf("#ff0707"));
		redKingName.setWrapText(true);
		addToPane(redKingName);

		addToPane(blush);
		// change the sword cursor to match the current player
		cursor.changeToSwordOfPlayer(game.getCurrentPlayer());
	}

	/**
	 * @author Delia Bretan
	 * @method displays the next draft on board. current player can select the
	 *         domino they want
	 * @goesTo selectDominoPage(); when choice is made
	 */
	private void displayNextDraft() {
		int locationY = 30;
		gameplay.draftReady();
		// display on screen the next draft
		for (Domino nextDraft : game.getNextDraft().getIdSortedDominos()) {
			BoardDomino domin = new BoardDomino(nextDraft);
			domin.getNode().setLayoutX(700);
			domin.getNode().setLayoutY(locationY);
			addToPane(domin.getNode());

			// if domino is already selected, display an X over it
			if (nextDraft.hasDominoSelection()) {
				Label x = new Label("X");
				x.setFont(new Font(font, 90));
				x.setLayoutX(825);
				x.setLayoutY(locationY);
				x.toFront();
				x.setTextFill(Paint.valueOf("black"));
				addToPane(x);
			}
			locationY += 230;

			// when a domino is clicked & it is not selected already, call gameplay and go
			// to selectDominoPage();
			domin.getNode().setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					KingdominoApplication.getGameplay().setId(Integer.valueOf(domin.getDomino().getId()));
					if (KingdominoApplication.getGameplay().selectDomino()) {
						selectDominoPage();
					}

				}
			});

		}

	}
	
	/**
	 * @author Nafiz Islam
	 * @method adds a button on the top right corner for restarting the game which goes back to the game creation menu
	 * @goesTo newGamePrep()
	 */
	public void addRestartButton() {
		CustomButton restart = new CustomButton(350, 150, "Restart", "gold", 50, 0);
		restart.setLayoutX(1450);
		restart.setLayoutY(0);
		restart.setImageFill(AssetManager.getScrollRed());
		
		restart.setNewEvent((MouseEvent me) -> {
			KingdominoController.stopCurrentGame();
			newGamePrep();
		});
		
		addToPane(restart.getNode());
	}

	/**
	 * @author Delia Bretan
	 * @Method displays the end screen. ranks players and displays player score
	 */
	private void displayEndPage() {
		panel.setBackground(AssetManager.getGameBackground());
		clearPane();
		cursor.changeToSwordOfPlayer(game.getPlayer(3));

		// displays title
		Label results = new Label("Results");
		results.setFont(new Font(font, 120));
		results.setTextFill(Paint.valueOf("gold"));

		// will hold all player labels
		HBox box = new HBox();
		box.setAlignment(Pos.CENTER);
		box.setLayoutY(-50);
		box.setMinWidth(1800);
		box.getChildren().add(results);
		addToPane(box);

		// gets the list of players
		ArrayList<Player> ranking = new ArrayList<Player>(
				KingdominoApplication.getKingdomino().getCurrentGame().getPlayers());

		// creates a comparator that will sort based on ranking
		Comparator<Player> compareRanking = (Player a, Player b) -> {
			return a.getCurrentRanking() - b.getCurrentRanking();
		};
		// sorts the list
		Collections.sort(ranking, compareRanking);

		// implements the colors according to rank
		ArrayList<String> colors = new ArrayList<String>();
		colors.add("gold");
		colors.add("dbe4eb");
		colors.add("cd7f32");
		colors.add("green");
		int y = 200;

		// displays players info accroding to ranking
		for (int playerNumber = 0; playerNumber < ranking.size(); playerNumber++) {
			HBox nameAndAll = new HBox();
			nameAndAll.setSpacing(100);
			nameAndAll.setLayoutX((y / 2.5) - 50);
			nameAndAll.setLayoutY(y);

			// player name and rank
			Label rank = new Label("" + ranking.get(playerNumber).getCurrentRanking() + ": "
					+ ranking.get(playerNumber).getUser().getName());
			rank.setFont(new Font(font, 50));
			rank.setMaxWidth(600);
			rank.setTextFill(Paint.valueOf(colors.get(ranking.get(playerNumber).getCurrentRanking() - 1)));
			nameAndAll.getChildren().add(rank);

			// player score
			Label calculations = new Label("Score: " + ranking.get(playerNumber).getPropertyScore() + "     Bonus: "
					+ ranking.get(playerNumber).getBonusScore() + "     Total: "
					+ (ranking.get(playerNumber).getPropertyScore() + ranking.get(playerNumber).getBonusScore()));
			calculations.setFont(new Font(font, 50));
			calculations.setTextFill(Paint.valueOf(colors.get(ranking.get(playerNumber).getCurrentRanking() - 1)));
			nameAndAll.getChildren().add(calculations);

			addToPane(nameAndAll);
			y += 200;
		}
    
		addRestartButton();

		for(Player p : ranking) {
			p.getUser().setPlayedGames(p.getUser().getPlayedGames()+1);
			if(ranking.indexOf(p) == 0) {
				p.getUser().setWonGames(p.getUser().getWonGames()+1);
			}
		}	
		
		KingdominoController.saveUsers();
	}

	/**
	 * @Author Delia Bretan
	 * @Method displays the user info
	 * @param user User. the user whose profile is observed
	 * @param king ImagePattern. the king image that will be displayed
	 * @goesTo selectDominoPage
	 */
	private void displayUserPage(User user, ImagePattern king) {
		clearPane();
		panel.setBackground(AssetManager.getGameBackground());

		// change the cursor to the current player's sword
		cursor.changeToSwordOfPlayer(game.getCurrentPlayer());

		// centers the title (user name)
		HBox nameBox = new HBox();
		nameBox.setAlignment(Pos.CENTER);
		Label nameUser = new Label(user.getName());

		nameUser.setTextFill(Paint.valueOf("gold"));
		nameUser.setMinWidth(1600);
		nameUser.setFont(new Font(font, 80));
		nameUser.setAlignment(Pos.CENTER);

		nameBox.setMaxWidth(1600);
		nameBox.setMinWidth(1600);
		nameBox.setLayoutX(100);
		nameBox.getChildren().add(nameUser);
		addToPane(nameBox);

		// adds the king image to screen
		Rectangle frame = new Rectangle(302, 302);
		frame.setLayoutX(749);
		frame.setLayoutY(200);
		frame.setFill(king);

		// gridpane will hold all labels, making sure everything is aligned
		GridPane info = new GridPane();
		info.setLayoutY(600);
		info.setLayoutX(600);
		info.setPadding(new Insets(20, 20, 20, 20));

		// game count info
		Label gameCount = new Label("Game count   ");
		gameCount.setFont(new Font(font, 50));
		gameCount.setTextFill(Paint.valueOf("gold"));
		info.add(gameCount, 0, 0);
		Label gameCountNumber = new Label("     " + user.getPlayedGames());
		gameCountNumber.setFont(new Font(font, 50));
		gameCountNumber.setTextFill(Paint.valueOf("gold"));
		info.add(gameCountNumber, 1, 0);

		// win count info
		Label winCount = new Label("Win count");
		winCount.setFont(new Font(font, 50));
		winCount.setTextFill(Paint.valueOf("gold"));
		info.add(winCount, 0, 1);
		Label winCountNumber = new Label("     " + user.getWonGames());
		winCountNumber.setFont(new Font(font, 50));
		winCountNumber.setTextFill(Paint.valueOf("gold"));
		info.add(winCountNumber, 1, 1);

//		Label gamesPlayed = new Label("Games Played");
//		gamesPlayed.setFont(new Font(font, 50));
//		gamesPlayed.setTextFill(Paint.valueOf("gold"));
//		info.add(gamesPlayed, 0, 2);
//		Label gamesPlayedNumber = new Label("" + player.getUser().get);
//		gamesPlayed.setFont(new Font(font, 50));
//		gamesPlayed.setTextFill(Paint.valueOf("gold"));
//		info.add(gamesPlayed, 0, 2);

		// displays back button and enables that option
		CustomButton back = new CustomButton(100, 70, "Back", "gold", 10, 0);
		back.setLayoutX(10);
		back.setLayoutY(10);
		back.setFont(font, 30);
		back.setImageFill(AssetManager.getScrollPurple());
		addToPane(back.getNode());
		EventHandler<MouseEvent> backToKingdom = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				selectDominoPage();
			}
		};
		back.setNewEvent(backToKingdom);

		addToPane(info);
		addToPane(frame);
	}

	/**
	 * @author Delia Bretan
	 * @method sets up the cursor
	 */
	private void haveCursorRunning() {
		panel.setCursor(Cursor.NONE);
		cursor = new fxml.Cursor(panel);
	}

	/**
	 * @author Delia Bretan
	 * @param terrain TerrainType. the TerrainType of the tile we desire
	 * @param crowns  int. the number of crowns on that tile
	 * @return ImagePattern. the asset of the corresponding tile
	 */
	private ImagePattern getProperTileAsset(TerrainType terrain, int crowns) {
		switch (terrain) {
		case Forest:
			switch (crowns) {
			case 0:
				return AssetManager.getForest0();
			default:
				return AssetManager.getForest1();
			}
		case Grass:
			switch (crowns) {
			case 0:
				return AssetManager.getGrass0();
			case 1:
				return AssetManager.getGrass1();
			default:
				return AssetManager.getGrass2();
			}
		case Lake:
			switch (crowns) {
			case 0:
				return AssetManager.getLake0();
			default:
				return AssetManager.getLake1();
			}
		case Mountain:
			switch (crowns) {
			case 0:
				return AssetManager.getMountain0();
			case 1:
				return AssetManager.getMountain1();
			case 2:
				return AssetManager.getMountain2();
			default:
				return AssetManager.getMountain3();
			}
		case Swamp:
			switch (crowns) {
			case 0:
				return AssetManager.getSwamp0();
			case 1:
				return AssetManager.getSwamp1();
			default:
				return AssetManager.getSwamp2();
			}
		case WheatField:
			switch (crowns) {
			case 0:
				return AssetManager.getWheat0();
			default:
				return AssetManager.getWheat1();
			}
		default:
			return AssetManager.getKingBlue();
		}
	}
	
/**
 * @author Antonia Nistor
 * @method shows score of each player and gets updated every time a domino is placed  </br>
 * 
 */
	
	
	private void displayPlayerScores() {
		
		
		int green = KingdominoController.color2PlayerScore("green");
		
		Label scoreGreen = new Label("Score : "+green);
		scoreGreen.setLayoutX(300);
		scoreGreen.setLayoutY(150);
		scoreGreen.setFont(new Font(font, 30));
		scoreGreen.setTextFill(Paint.valueOf("#00FF00"));
		addToPane(scoreGreen);
		
		
		int yellow = KingdominoController.color2PlayerScore("yellow");
		
		Label scoreYellow = new Label("Score : "+yellow);
		scoreYellow.setLayoutX(300);
		scoreYellow.setLayoutY(850);
		scoreYellow.setFont(new Font(font, 30));
		scoreYellow.setTextFill(Paint.valueOf("yellow"));
		addToPane(scoreYellow);

		
		int blue = KingdominoController.color2PlayerScore("blue");
			
		Label scoreBlue = new Label("Score : "+blue);
		scoreBlue.setLayoutX(1365);
		scoreBlue.setLayoutY(150);
		scoreBlue.setFont(new Font(font, 30));
		scoreBlue.setTextFill(Paint.valueOf("blue"));
		addToPane(scoreBlue);
		
		
		
		int red = KingdominoController.color2PlayerScore("pink");
		
		Label scoreRed = new Label("Score : "+red);
		scoreRed.setLayoutX(1365);
		scoreRed.setLayoutY(850);
		scoreRed.setFont(new Font(font, 30));
		scoreRed.setTextFill(Paint.valueOf("red"));
		addToPane(scoreRed);
		
		
		
	}		
	

	// ----------------------
	/**
	 * @author Delia Bretan
	 * @Method clears the anchropane of any children. adds the cursor back
	 */
	public void clearPane() {
		panel.getChildren().clear();
		addToPane(cursor.getCursor()[1]);
		addToPane(cursor.getCursor()[0]);
	}

	/**
	 * @author Delia Bretan
	 * @param node Node. adds the node to the anchorpane. sets the cursor to be in
	 *             front
	 */
	public void addToPane(Node node) {
		panel.getChildren().add(node);
		cursor.getCursor()[0].toFront();
		cursor.getCursor()[1].toFront();
	}

	/**
	 * @author Delia Bretan
	 * @param node Node. removes the node from the anchropane
	 */
	public void removeFromPane(Node node) {
		panel.getChildren().remove(node);
	}
}
