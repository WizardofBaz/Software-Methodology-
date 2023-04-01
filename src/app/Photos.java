package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.io.Serializable;


// TODO: Auto-generated Javadoc
/**
 * The Class Photos.
 * 
 * @author Osama Syed
 * @author Arbaz Pathan
 */
public class Photos extends Application {

	/** Stage for the application*/
	private static Stage window;

	/**
	 * Changes the scene to the next scene in the application.
	 *
	 * @param nextScene 
	 */
	public static void changeScene(Scene nextScene) {
		window.setScene(nextScene);
	}

	/**
	 * Pop up message alerting they did something no allowed in the application.
	 *
	 * @param message 
	 */
	public static void popUp(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Alert");
		alert.setContentText(message);
		alert.showAndWait();
	}

	/**
	 * Starts the application
	 *
	 * @param primaryStage 
	 * @throws Exception
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		window = primaryStage;

		Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
		window.setTitle("Osama Syed and Arbazkhan Pathan Photo Album");
		window.setScene(new Scene(root, 800, 450));
		window.setResizable(true);
		window.show();
	}

	/**
	 * The main method.
	 *
	 * @param args 
	 */
	public static void main(String[] args) {
		
		launch(args);
	}
}
