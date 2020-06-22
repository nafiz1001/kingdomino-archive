package fxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameBoard extends Application{
	/**
	 * @author Delia Bretan
	 */
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("GameBoard.fxml"));
		Scene scene = new Scene(root, 1790, 940);
		stage.setScene(scene);     
		stage.setResizable(false);
		stage.requestFocus();
        stage.show();
	}
	public static void main(String[] args) {
        launch(args);
    }

}
