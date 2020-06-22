package fxml;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class CustomButton {
	/**
	 * @author Delia Bretan
	 */
	/**
	 * @param button         AnchorPane. the pane that will hold all the visual
	 *                       elements
	 * @param textLabel      Label. the label holding the button's text
	 * @param background     Rectangle. the background of the button. will determine
	 *                       the size of button and the fill
	 * @param defaultFont    String. my fav font again
	 * @param fontSize       int. the default text size
	 * @param myEventHandler EventHandler<MouseEvent>. the event fired when clicking
	 *                       the button
	 */
	private AnchorPane button;
	private Label textLabel;
	private Rectangle background;
	private String defaultFont = "Matura MT Script Capitals";
	private int fontSize = 80;
	private EventHandler<MouseEvent> myEventHandler;

	public CustomButton(int width, int height, String text, String colorOfText, double textLocationX,
			double textLocationY) {
		button = new AnchorPane();

		// sets the text of the button
		textLabel = new Label(text);
		textLabel.setFont(new Font(defaultFont, fontSize));
		textLabel.setTextFill(Paint.valueOf(colorOfText));
		textLabel.setLayoutX(textLocationX);
		textLabel.setLayoutY(textLocationY);

		// sets the recangle size and all + default fill to yellow
		background = new Rectangle();
		background.setHeight(height);
		background.setWidth(width);
		background.setFill(Paint.valueOf("white"));

		addToButton(background);
		addToButton(textLabel);

	}

	/**
	 * @author Delia Bretan
	 * @param color String. the new color of the text
	 */
	public void setColorOfText(String color) {
		textLabel.setTextFill(Paint.valueOf(color));
	}

	/**
	 * @author Delia Bretan
	 * @param name String. the new font name
	 * @param size int. the size of the text
	 */
	public void setFont(String name, int size) {
		defaultFont = name;
		fontSize = size;
		textLabel.setFont(new Font(defaultFont, fontSize));
	}

	/**
	 * @author Delia Bretan
	 * @param event ventHandler<MouseEvent>. the event attacked to this button
	 */
	public void setNewEvent(EventHandler<MouseEvent> event) {

		button.setOnMouseClicked(event);

	}

	public void setLayoutX(double x) {
		button.setLayoutX(x);
	}

	public void setLayoutY(double y) {
		button.setLayoutY(y);
	}

	/**
	 * @author Delia Bretan
	 * @param image ImagePattern. the new background fill of this button
	 */
	public void setImageFill(ImagePattern image) {
		background.setFill(image);
	}

	public AnchorPane getNode() {
		return button;
	}

	private void addToButton(Node node) {
		button.getChildren().add(node);
	}

}
