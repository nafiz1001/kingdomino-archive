package fxml;

import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class BoardDomino {
	/**
	 * @author Delia Bretan
	 */
	/**
	 * @param dominoPane AnchorPane. this will hold all the visual info about domino
	 * @param left       Rectangle. left side of domino
	 * @param right      Rectangle. right side of domino
	 * @param domino     Domino. the domino object represented
	 */
	private AnchorPane dominoPane;
	private Rectangle left;
	private Rectangle right;
	private Domino domino;

	/**
	 * @author Delia Bretan
	 * @param domino domino. the domino this object will represent
	 */
	public BoardDomino(Domino domino) {
		//initialization
		this.domino = domino;
		dominoPane = new AnchorPane();
		//default rectangle size 200*200
		left = new Rectangle(200, 200);
		right = new Rectangle(200, 200);
		right.setLayoutX(200);

		//sets the fill of the rectangle to their respective assets
		left.setFill(getProperTileAsset(domino.getLeftTile(), domino.getLeftCrown()));
		right.setFill(getProperTileAsset(domino.getRightTile(), domino.getRightCrown()));

		addToDomino(left);
		addToDomino(right);
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
	 * @author Delia Bretan
	 * @param size double. new witdh and height value of rectangles 
	 * @Method chnages the size of the rectangles in this object. also changes their layout 
	 */
	public void setSize(double size) {
		left.setWidth(size);
		left.setHeight(size);
		right.setLayoutX(size);
		right.setWidth(size);
		right.setHeight(size);
	}

	/**
	 * @author Delia Bretan 
	 * @return ImagePattern. the asset of the left rectangle
	 */
	public ImagePattern getLeftSkin() {
		return getProperTileAsset(domino.getLeftTile(), domino.getLeftCrown());
	}

	/**
	 * @author Delia Bretan 
	 * @return ImagePattern. the asset of the right rectangle
	 */
	public ImagePattern getRightSkin() {
		return getProperTileAsset(domino.getRightTile(), domino.getRightCrown());
	}

	public Domino getDomino() {
		return domino;
	}

	public AnchorPane getNode() {
		return dominoPane;
	}

	private void addToDomino(Node node) {
		dominoPane.getChildren().add(node);
	}
}
