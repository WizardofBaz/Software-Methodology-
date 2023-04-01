package app;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Album;
import model.Photo;
import model.User;
import javafx.scene.Parent;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginController.
 */
public class LoginController {

	@FXML
	Button login,quit;

	@FXML
	private TextField usernameTxt;

	/** The current user. */
	public static User currentUser;
	
	/** The first login. */
	static boolean firstLogin = true;

	/** The stock user. */
	public static User stockUser = new User("stock");
	
	/** The stock album. */
	Album stockAlbum = new Album("stock");

	/**
	 * Handle login button.
	 *
	 * @param e 
	 * @throws IOException
	 */
	public void handleLoginButton(ActionEvent e) throws IOException {

		boolean userFound = false;

		String username = usernameTxt.getText();
		if (username.equals("admin")) {

			Parent root = FXMLLoader.load(getClass().getResource("/view/AdminHome.fxml"));
			Scene nextScene = new Scene(root, 800, 450);
			Photos.changeScene(nextScene);
			return;

		} else if (username.equals("stock")) {
			userFound = true;
			currentUser = stockUser;

			// Creates an File array of all files in the stockPhotos folder

			if (firstLogin) {
				stockUser.addAlbum(stockAlbum);
				File[] stockPhotos = new File("stockPhotos/").listFiles();
				// Iterates through File array and adds photo to the album
				for (File file : stockPhotos) {

					stockAlbum.addPhoto(new Photo(file, file.getName().split("\\.")[0]));

				}

				firstLogin = false;
			}

		} else {

			if (AdminHomeController.users != null) {

				for (User user : AdminHomeController.users) {

					if (user.getUsername().equals(username)) {
						userFound = true;
						currentUser = user;

					}

				}

			}

		}

		if (userFound) {

			Parent root = FXMLLoader.load(getClass().getResource("/view/UserHome.fxml"));
			Scene nextScene = new Scene(root, 800, 450);
			Photos.changeScene(nextScene);

		} else {
			Photos.popUp("User Does not Exist!");
		}

	}
	
	/**
	 * Quit. Closes the program
	 */
	public void quit() {
		System.exit(0);
	}

}
