package fxml;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.ImagePattern;

public class AssetManager {
	/**
	 * @author Delia Bretan
	 */
	
	// backgrounds
	static final BackgroundSize backgroundSize = new BackgroundSize(1800, 950, false, false, false, false);
	static private Image welcomeBackground;
	static private Image startBackground;
	static private Image gameBackground;
	// kingdomino logo
	static private ImagePattern kingdominoLogo;

	// scrolls
	static private ImagePattern scroll_red;
	static private ImagePattern scroll_green;
	static private ImagePattern scroll_purple;
	static private ImagePattern scroll_yellow;
	// swords
	static private ImagePattern sword_Red;
	static private ImagePattern sword_Green;
	static private ImagePattern sword_Yellow;
	static private ImagePattern sword_Blue;
	// kings
	static private ImagePattern king_Red;
	static private ImagePattern king_Blue;
	static private ImagePattern king_Green;
	static private ImagePattern king_Yellow;
	static private ImagePattern king_Green_Flipped;
	static private ImagePattern king_Yellow_Flipped;

	// dominoes
	static private ImagePattern castle_red;
	static private ImagePattern castle_blue;
	static private ImagePattern castle_yellow;
	static private ImagePattern castle_green;
	static private ImagePattern wheat_0;
	static private ImagePattern wheat_1;
	static private ImagePattern lake_0;
	static private ImagePattern lake_1;
	static private ImagePattern forest_0;
	static private ImagePattern forest_1;
	static private ImagePattern grass_0;
	static private ImagePattern grass_1;
	static private ImagePattern grass_2;
	static private ImagePattern mountain_0;
	static private ImagePattern mountain_1;
	static private ImagePattern mountain_2;
	static private ImagePattern mountain_3;
	static private ImagePattern swamp_0;
	static private ImagePattern swamp_1;
	static private ImagePattern swamp_2;

	// empty space selected & not selected
	static private ImagePattern emptyTile_Unselected;
	static private ImagePattern emptyTile_Selected;

	/**
	 * @author Delia Bretan
	 * @Method loads all image assets
	 */
	static public void preloadAllGameAssets() {
		welcomeBackground = new Image(fileURL("Background/wecomeBackground.jpg"));
		startBackground = new Image(fileURL("Background/startBackground.jpg"));
		gameBackground = new Image(fileURL("Background/stoneWall.jpg"));
		kingdominoLogo = new ImagePattern(new Image(fileURL("King/Kingdomino-Logo.png")));

		scroll_red = new ImagePattern(new Image(fileURL("Scroll/redScroll.png")));
		scroll_green = new ImagePattern(new Image(fileURL("Scroll/greenScroll.png")));
		scroll_purple = new ImagePattern(new Image(fileURL("Scroll/purpleScroll.png")));
		scroll_yellow = new ImagePattern(new Image(fileURL("Scroll/normalScroll.png")));

		sword_Red = new ImagePattern(new Image(fileURL("Sword/Sword-Red.png")));
		sword_Green = new ImagePattern(new Image(fileURL("Sword/Sword-Green.png")));
		sword_Blue = new ImagePattern(new Image(fileURL("Sword/Sword-Blue.png")));
		sword_Yellow = new ImagePattern(new Image(fileURL("Sword/Sword-Yellow.png")));

		king_Red = new ImagePattern(new Image(fileURL("King/KingRed.png")));
		king_Blue = new ImagePattern(new Image(fileURL("King/KingBlue.png")));
		king_Green = new ImagePattern(new Image(fileURL("King/KingGreen.png")));
		king_Yellow = new ImagePattern(new Image(fileURL("King/KingYellow.png")));
		king_Green_Flipped = new ImagePattern(new Image(fileURL("King/KingGreenFlipped.png")));
		king_Yellow_Flipped = new ImagePattern(new Image(fileURL("King/KingYellowFlipped.png")));

		castle_red = new ImagePattern(new Image(fileURL("Domino/C-R.png")));
		castle_blue = new ImagePattern(new Image(fileURL("Domino/C-B.png")));
		castle_green = new ImagePattern(new Image(fileURL("Domino/C-G.png")));
		castle_yellow = new ImagePattern(new Image(fileURL("Domino/C-Y.png")));

		wheat_0 = new ImagePattern(new Image(fileURL("Domino/W-0.png")));
		wheat_1 = new ImagePattern(new Image(fileURL("Domino/W-1.png")));

		lake_0 = new ImagePattern(new Image(fileURL("Domino/L-0.png")));
		lake_1 = new ImagePattern(new Image(fileURL("Domino/L-1.png")));

		forest_0 = new ImagePattern(new Image(fileURL("Domino/F-0.png")));
		forest_1 = new ImagePattern(new Image(fileURL("Domino/F-1.png")));

		grass_0 = new ImagePattern(new Image(fileURL("Domino/G-0.png")));
		grass_1 = new ImagePattern(new Image(fileURL("Domino/G-1.png")));
		grass_2 = new ImagePattern(new Image(fileURL("Domino/G-2.png")));

		mountain_0 = new ImagePattern(new Image(fileURL("Domino/M-0.png")));
		mountain_1 = new ImagePattern(new Image(fileURL("Domino/M-1.png")));
		mountain_2 = new ImagePattern(new Image(fileURL("Domino/M-2.png")));
		mountain_3 = new ImagePattern(new Image(fileURL("Domino/M-3.png")));

		swamp_0 = new ImagePattern(new Image(fileURL("Domino/S-0.png")));
		swamp_1 = new ImagePattern(new Image(fileURL("Domino/S-1.png")));
		swamp_2 = new ImagePattern(new Image(fileURL("Domino/S-2.png")));

		emptyTile_Unselected = new ImagePattern(new Image(fileURL("Domino/Empty-Unselected.png")));
		emptyTile_Selected = new ImagePattern(new Image(fileURL("Domino/Empty-Selected.png")));
	}

	/**
	 * @author Delia Bretan
	 * @Method getters for all images destined to be backgrounds
	 * @return Background. desired image as a background object
	 */
	static public Background getWelcomeBackground() {
		return new Background(new BackgroundImage(welcomeBackground, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize));
	}

	static public Background getStartBackground() {
		return new Background(new BackgroundImage(startBackground, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize));
	}

	static public Background getGameBackground() {
		return new Background(new BackgroundImage(gameBackground, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize));
	}

	/**
	 * @author Delia Bretan
	 * @Method getters for all ImagePattern objects
	 * @return ImagePattern. correspongind object
	 */
	static public ImagePattern getKingdominoLogo() {
		return kingdominoLogo;
	}

	static public ImagePattern getScrollRed() {
		return scroll_red;
	}

	static public ImagePattern getScrollGreen() {
		return scroll_green;
	}

	static public ImagePattern getScrollPurple() {
		return scroll_purple;
	}

	static public ImagePattern getScrollYellow() {
		return scroll_yellow;
	}

	static public ImagePattern getSwordRed() {
		return sword_Red;
	}

	static public ImagePattern getSwordGreen() {
		return sword_Green;
	}

	static public ImagePattern getSwordBlue() {
		return sword_Blue;
	}

	static public ImagePattern getSwordYellow() {
		return sword_Yellow;
	}

	static public ImagePattern getKingRed() {
		return king_Red;
	}

	static public ImagePattern getKingBlue() {
		return king_Blue;
	}

	static public ImagePattern getKingGreen() {
		return king_Green;
	}

	static public ImagePattern getKingGreenFlipped() {
		return king_Green_Flipped;
	}

	static public ImagePattern getKingYellow() {
		return king_Yellow;
	}

	static public ImagePattern getKingYellowFlipped() {
		return king_Yellow_Flipped;
	}

	static public ImagePattern getCastleRed() {
		return castle_red;
	}

	static public ImagePattern getCastleBlue() {
		return castle_blue;
	}

	static public ImagePattern getCastleGreen() {
		return castle_green;
	}

	static public ImagePattern getCastleYellow() {
		return castle_yellow;
	}

	static public ImagePattern getWheat0() {
		return wheat_0;
	}

	static public ImagePattern getWheat1() {
		return wheat_1;
	}

	static public ImagePattern getLake0() {
		return lake_0;
	}

	static public ImagePattern getLake1() {
		return lake_1;
	}

	static public ImagePattern getForest0() {
		return forest_0;
	}

	static public ImagePattern getForest1() {
		return forest_1;
	}

	static public ImagePattern getGrass0() {
		return grass_0;
	}

	static public ImagePattern getGrass1() {
		return grass_1;
	}

	static public ImagePattern getGrass2() {
		return grass_2;
	}

	static public ImagePattern getMountain0() {
		return mountain_0;
	}

	static public ImagePattern getMountain1() {
		return mountain_1;
	}

	static public ImagePattern getMountain2() {
		return mountain_2;
	}

	static public ImagePattern getMountain3() {
		return mountain_3;
	}

	static public ImagePattern getSwamp0() {
		return swamp_0;
	}

	static public ImagePattern getSwamp1() {
		return swamp_1;
	}

	static public ImagePattern getSwamp2() {
		return swamp_2;
	}

	static public ImagePattern getEmptyTileUnselected() {
		return emptyTile_Unselected;
	}

	static public ImagePattern getEmptyTileSelected() {
		return emptyTile_Selected;
	}

	static private String fileURL(String relativePath) {
		return new File("src/main/java/fxml/Assets/" + relativePath).toURI().toString();
	}
}
