package app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Album;
import model.Photo;
import model.Tag;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchTags.
 */
public class SearchTags implements Initializable {

	/** The search results. */
	ObservableList<Photo> searchResults = FXCollections.observableArrayList();

	/** The photo details. */
	ObservableList<String> photoDetails = FXCollections.observableArrayList();

	/** The current photo. */
	Photo currentPhoto;

	@FXML
	ListView<Photo> searchResultsList;

	@FXML
	ListView<String> photoDetailsList;

	@FXML
	AnchorPane searchResultsAnchor, secondPair;

	@FXML
	TextField newAlbumName;

	@FXML
	TextField tagTypeField, tagType2Field;

	@FXML
	TextField tagValueField, tagValue2Field;

	@FXML
	ImageView photo;

	@FXML
	VBox prompt;

	@FXML
	Button back, andButton,orButton;

	@FXML
	Button logout;

	@FXML
	Button createAlbum;

	Boolean searching2 = false;
	Boolean and = false;
	Boolean or = false;

	/**
	 * Initialize.
	 *
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		searchResultsAnchor.setVisible(false);
		prompt.setVisible(false);
		secondPair.setVisible(false);

		searchResultsList.setItems(searchResults);
		photoDetailsList.setItems(photoDetails);
		;
		photo.setPreserveRatio(true);

		searchResultsList.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> {

			currentPhoto = searchResultsList.getSelectionModel().getSelectedItem();

			photoDetailsList.getItems().clear();
			photoDetailsList.getItems().add("Photo Caption: " + currentPhoto.toString());
			photoDetailsList.getItems().add("Date Taken: " + currentPhoto.lastModified);

			for (Tag t : currentPhoto.getTags()) {
				photoDetailsList.getItems().add(t.toString());
			}

			photo.setImage(currentPhoto.getImage());

		});

		searchResultsList.setCellFactory(param -> new ListCell<Photo>() {

			ImageView img = new ImageView();

			@Override
			public void updateItem(Photo photo, boolean empty) {

				super.updateItem(photo, empty);

				if (photo != null) {

					img.setImage(photo.getImage());
					img.setFitHeight(20);
					img.setFitWidth(20);
					img.setPreserveRatio(true);
					setGraphic(img);
					setText(photo.toString());

				} else {
					setGraphic(null);
					setText(null);
				}

			}

		});

	}

	/**
	 * Find by tag.
	 */
	public void findByTag() {
		
		searchResults.clear();

		String tagType = tagTypeField.getText();
		String tagValue = tagValueField.getText();
		String tagType2 = tagType2Field.getText();
		String tagValue2 = tagValue2Field.getText();

		if (!searching2) {
			for (Album a : LoginController.currentUser.getAlbums()) {
				for (Photo p : a.getPhotos()) {
					for (Tag t : p.tags) {

						if (t.getName().equals(tagType) && t.getValue().equals(tagValue)) {
							searchResults.add(p);

						}
					}

				}
			}

		} else {

			if (or) {
	
				for (Album a : LoginController.currentUser.getAlbums()) {
					for (Photo p : a.getPhotos()) {
						Boolean alreadyAdded = false;
						for (Tag t : p.tags) {

							if ((t.getName().equals(tagType2) && t.getValue().equals(tagValue2))) {
								alreadyAdded = true;
								searchResults.add(p);

							}
						}
						
						for (Tag t : p.tags) {
							if (t.getName().equals(tagType) && t.getValue().equals(tagValue)) {
								if(!alreadyAdded) {
									searchResults.add(p);;

								}
							}
						}

					}
				}

	

			}

			if (and) {

				for (Album a : LoginController.currentUser.getAlbums()) {
					for (Photo p : a.getPhotos()) {
						Boolean matchesfirst = false;
						Boolean matchesSecond = false;
						for (Tag t : p.tags) {

							if (t.getName().equals(tagType) && t.getValue().equals(tagValue)) {
								matchesfirst = true;
							}
						}

						for (Tag t : p.tags) {
							if (t.getName().equals(tagType2) && t.getValue().equals(tagValue2)) {
								matchesSecond = true;
							}
						}

						if (matchesfirst && matchesSecond) {
							searchResults.add(p);

						}

					}
				}

			}

		}

		searchResultsList.getSelectionModel().selectFirst();
		searchResultsAnchor.setVisible(true);

	}

	public void andClicked() {
		orButton.setVisible(false);
		secondPair.setVisible(true);
		searching2 = true;
		and = true;
	}

	public void orClicked() {
		andButton.setVisible(false);
		secondPair.setVisible(true);
		searching2 = true;
		or = true;
	}

	/**
	 * Creates the prompt.
	 */
	public void createPrompt() {

		prompt.setVisible(true);

	}

	/**
	 * Creates the album based on results.
	 */
	public void create() {

		if (newAlbumName.getText().trim().equals("")) {
			Photos.popUp("Please enter a name");
			return;

		}

		if (UserHomeController.albumNameMatch(newAlbumName.getText())) {
			Photos.popUp("Album with that name already exists");
			return;

		}

		LoginController.currentUser.addAlbum(new Album(newAlbumName.getText(), searchResults));
		Photos.popUp("Album Successfully Created!");

		prompt.setVisible(false);
	}

	/**
	 * Go back.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void goBack() throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("/view/UserHome.fxml"));
		Scene nextScene = new Scene(root, 800, 450);
		Photos.changeScene(nextScene);

	}

}
