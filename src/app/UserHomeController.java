package app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.Serializable;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Album;
import javafx.scene.Parent;

// TODO: Auto-generated Javadoc
/**
 * The Class UserHomeController allows the user add album, re-name album, open selected album, delete selected
 * album, and search by both tags, and date.
 * 
 * @author Osama Syed
 * @author Arbaz Pathan
 * 
 */
public class UserHomeController implements Initializable {

	@FXML
	TextField newAlbumName;
	@FXML
	Button createAlbumButton,openAlbumButton,deleteAlbumBtn,reName,quit,searchTags;

	@FXML
	ListView<Album> albumList;
	@FXML
	TextField rField;

	@FXML
	ListView<String> albumDetails;


	/** The current album. */
	public static Album currentAlbum;

	/**
	 * Initializes the userHome.Updating the lists based on what the 
	 * user clicks.
	 *
	 * @param arg0 
	 * @param arg1 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Populating the Listview<Album> albumList with the current user's albums
		albumList.setItems(LoginController.currentUser.getAlbums());
		albumList.getSelectionModel().selectFirst();
		currentAlbum = albumList.getSelectionModel().getSelectedItem();
		showDetails();

		if (LoginController.currentUser.getAlbums().size() == 0) {
			toggleButtons(false);
			rField.setVisible(false);
			reName.setVisible(false);
		}

		// Changing currentAlbum variable to whichever album is clicked and showing
		// Details of that Album
		albumList.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> {
			currentAlbum = albumList.getSelectionModel().getSelectedItem();
			showDetails();
		});

	}

	/**
	 * Shows details of album selected.
	 */
	public void showDetails() {

		int index = albumList.getSelectionModel().getSelectedIndex();

		if (index != -1) {
			albumDetails.getItems().clear();

			albumDetails.getItems().add("Name: " + currentAlbum.getName());
			albumDetails.getItems().add("Number of Photos: " + currentAlbum.getPhotos().size());
			albumDetails.getItems().add("Earliest: " + currentAlbum.getEarliest());
			albumDetails.getItems().add("Latest: " + currentAlbum.getLatest());

		} else {
			albumDetails.getItems().clear();
		}

	}

	/**
	 * Creates the new album.
	 */
	public void createAlbum() {

		String albumName = newAlbumName.getText();

		if (albumNameMatch(albumName)) {
			Photos.popUp("Album with that name already exists");
			return;
		}

		Album insert = new Album(albumName);
		// here

		LoginController.currentUser.addAlbum(insert);
		albumList.getSelectionModel().select(insert);

		toggleButtons(true);
		rField.setVisible(true);
		reName.setVisible(true);

		showDetails();

	}

	/**
	 * Opens the album the user has selected.
	 *
	 * @param e 
	 * @throws IOException
	 */
	public void openAlbum(ActionEvent e) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("/view/AlbumHome.fxml"));
		Scene nextScene = new Scene(root, 800, 450);
		Photos.changeScene(nextScene);

	}

	/**
	 * Logouts the user. Takes them back to login screen.
	 *
	 * @throws IOException
	 */
	public void logout() throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
		Scene nextScene = new Scene(root, 800, 450);
		Photos.changeScene(nextScene);

	}

	/**
	 * Activates when the user clicks on a search option.
	 *
	 * @throws IOException 
	 */
	public void search() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/view/Search.fxml"));
		Scene nextScene = new Scene(root, 800, 450);
		Photos.changeScene(nextScene);

	}
	
	/**
	 * Searches tages based on what the user inputed.
	 *
	 * @throws IOException 
	 */
	public void searchTags() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/view/SearchTags.fxml"));
		Scene nextScene = new Scene(root, 800, 450);
		Photos.changeScene(nextScene);

	}

	/**
	 * Deletes the album from the album list
	 */
	public void delete() {

		Album album = albumList.getSelectionModel().getSelectedItem();
		// User.getAlbums().remove(album);
		albumList.getItems().remove(album);
		LoginController.currentUser.deleteAlbum(album);

		albumList.getSelectionModel().selectFirst();
		showDetails();

		if (LoginController.currentUser.getAlbums().size() == 0) {

			toggleButtons(false);
			rField.setVisible(false);
			reName.setVisible(false);

		}

	}

	/**
	 * Re names the album based on what the user entered in the proper field.
	 */
	public void reName() {

		String proposedName = rField.getText();

		if (albumNameMatch(proposedName)) {
			Photos.popUp("Album with that name already exists");
			return;
		}

		currentAlbum.setName(rField.getText());

		albumList.refresh();
		showDetails();

	}

	/**
	 * Checks if two albums have the same name. It is not allowed.
	 *
	 * @param albumName 
	 * @return boolean
	 */
	public static boolean albumNameMatch(String albumName) {

		for (Album a : LoginController.currentUser.getAlbums()) {
			if (a.getName().equals(albumName))
				return true;

		}

		return false;
	}

	/**
	 * Toggle buttons, whether or not they will appear.
	 *
	 * @param show
	 */
	public void toggleButtons(Boolean show) {

		openAlbumButton.setVisible(show);
		deleteAlbumBtn.setVisible(show);
	}
	
	/**
	 * Closes the application.
	 */
	public void quit() {
		System.exit(0);
	}

}
