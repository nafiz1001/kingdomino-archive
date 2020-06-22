package fxml;

import ca.mcgill.ecse223.kingdomino.model.Player;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class Cursor {
	/**
	 * @author Delia Bretan
	 */
	/**
	 * @param cursor      Rectangle[]. array that will hold both parts of the cursor
	 * @param domino      BoardDomino. the domino that will be set as cursor
	 * @param Orientation enum. all the possible orientations the cursor can have
	 * @param direction   Orientation. the current orientation
	 */
	private Rectangle[] cursor = new Rectangle[2];
	private BoardDomino domino;

	private enum Orientation {
		ToTheLeft, Down, ToTheRight, Up
	};

	private Orientation direction = Orientation.ToTheLeft;

	/**
	 * @author Delia Bretan
	 * @param panel AnchorPane. the anchorpane on which the cursor will be
	 */
	public Cursor(AnchorPane panel) {
		// left cursor will always be displayed. it will either domino half or sword
		// left cursor initialized to red sword
		cursor[0] = new Rectangle();
		cursor[0].setWidth(68);
		cursor[0].setHeight(102);
		cursor[0].setFill(AssetManager.getSwordRed());
		panel.getChildren().add(cursor[0]);

		// right cursor will be the other domino half.
		// initializes to nothing. not visible
		cursor[1] = new Rectangle();
		cursor[1].setVisible(false);
		panel.getChildren().add(cursor[1]);

		// cursor behaviour depending on orientation
		panel.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				// left cursor always at these coordinates
				cursor[0].setLayoutX(t.getSceneX() + 1);
				cursor[0].setLayoutY(t.getSceneY() + 1);

				switch (direction) {
				case ToTheLeft:
				case ToTheRight: {
					cursor[1].setLayoutX(cursor[0].getLayoutX() + 100);
					cursor[1].setLayoutY(cursor[0].getLayoutY());

				}
					break;
				case Up:
				case Down: {
					cursor[1].setLayoutX(cursor[0].getLayoutX());
					cursor[1].setLayoutY(cursor[0].getLayoutY() + 100);

				}
					break;
				}
			}
		});
	}

	/**
	 * @author Delia Bretan
	 * @Method changes cursor type. sets it to the current domino selection of the
	 *         current player
	 * @param domino BoardDomino. the current domino selection of the current player
	 */
	public void changeToDominoOfPlayer(BoardDomino domino) {
		this.domino = domino;
		direction = Orientation.ToTheLeft;
		cursor[1].setVisible(true);

		cursor[0].setWidth(100);
		cursor[0].setHeight(100);
		cursor[0].setFill(domino.getLeftSkin());

		cursor[1].setWidth(100);
		cursor[1].setHeight(100);
		cursor[1].setFill(domino.getRightSkin());
	}

	/**
	 * @author Delia Bretan
	 * @Method changes cursor type. sets it to the sword color of the current player
	 * @param player Player. the current player
	 */
	public void changeToSwordOfPlayer(Player player) {
		cursor[0].setWidth(68);
		cursor[0].setHeight(102);
		switch (player.getColor()) {
		case Pink:
			cursor[0].setFill(AssetManager.getSwordRed());
			break;
		case Blue:
			cursor[0].setFill(AssetManager.getSwordBlue());
			break;
		case Green:
			cursor[0].setFill(AssetManager.getSwordGreen());
			break;
		case Yellow:
			cursor[0].setFill(AssetManager.getSwordYellow());
			break;
		}
		cursor[1].setVisible(false);
	}

	/**
	 * @author Delia Bretan
	 * @Method changes orientation of the domino cursor
	 * @param i int. the desired orientation of the domino cursor
	 */
	public void changeOrientation(int i) {
		switch (i % 4) {
		// if to the right
		case 0: {
			direction = Orientation.ToTheRight;
			cursor[0].setFill(domino.getLeftSkin());
			cursor[1].setFill(domino.getRightSkin());
			cursor[1].setLayoutX(cursor[0].getLayoutX() + 100);
			cursor[1].setLayoutY(cursor[0].getLayoutY());
			break;
		}
		// down
		case 1: {
			direction = Orientation.Down;
			cursor[0].setFill(domino.getLeftSkin());
			cursor[1].setFill(domino.getRightSkin());
			cursor[1].setLayoutX(cursor[0].getLayoutX());
			cursor[1].setLayoutY(cursor[0].getLayoutY() + 100);
			break;
		}
		// left
		case 2: {
			direction = Orientation.ToTheLeft;
			cursor[0].setFill(domino.getRightSkin());
			cursor[1].setFill(domino.getLeftSkin());
			cursor[1].setLayoutX(cursor[0].getLayoutX() + 100);
			cursor[1].setLayoutY(cursor[0].getLayoutY());
			break;
		}
		// right
		case 3: {
			direction = Orientation.Up;
			cursor[0].setFill(domino.getRightSkin());
			cursor[1].setFill(domino.getLeftSkin());
			cursor[1].setLayoutX(cursor[0].getLayoutX());
			cursor[1].setLayoutY(cursor[0].getLayoutY() + 100);
			break;
		}
		}
	}

	public Rectangle[] getCursor() {
		return cursor;
	}

}
