package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.Serializable;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Album;
import model.User;


// TODO: Auto-generated Javadoc
/**
 * The Class AdminHomeController. It gives the admin the ability to
 * add users and delete users. 
 * 
 * 
 * 
 * @author Osama Syed
 * @author Arbaz Pathan
 * 
 * 
 */
public class AdminHomeController implements Initializable {

	@FXML
	private Button addUser, deleteUser, logout, exit;
	
	@FXML
	private TextField addText;
	
	@FXML
	private ListView<User> userList;

	public static ObservableList<User> users = FXCollections.observableArrayList();
	
	public ObservableList<User> serializedUsers = users;
	

/**
 * Initializes the user list, and updates it accordingly
 *
 * @param arg0 the arg 0
 * @param arg1 the arg 1
 */
@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		userList.setItems(users);

	}

	/**
	 * Adds users to the user list.
	 *
	 * @param a 
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void add(ActionEvent a) throws IOException {

		User newUser = new User(addText.getText());

		if (users != null) {
			for (User alreadyUser : users) {
				if (alreadyUser.getUsername().equals(newUser.getUsername())) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setContentText("Already User");
					alert.showAndWait();
					return;
				}
			}

		}

		users.add(newUser);

	}

	/**
	 * Deletes a user from the user list.
	 */
	public void delete() {
		User clicked = userList.getSelectionModel().getSelectedItem();
		users.remove(clicked);

	}

	/**
	 * Logs you out of the program. Takes you to login screen.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void logout() throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
		Scene nextScene = new Scene(root, 800, 450);
		Photos.changeScene(nextScene);

	}
	
	public void quit() throws IOException {
		Platform.exit();
		System.exit(0);
	}


}
