package app;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.io.Serializable;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Album;
import model.Photo;

// TODO: Auto-generated Javadoc
/**
 * The Class Search allows the user to search by date.
 * 
 * @author Osama Syed
 * @author Arbaz Pathan
 * 
 */

public class Search implements Initializable {

	/** The search results. */
	ObservableList<Photo> searchResults = FXCollections.observableArrayList();
	
	/** The photo details of the search results. */
	ObservableList<String> photoDetails = FXCollections.observableArrayList();

	/** The current photo user has selected. */
	Photo currentPhoto;

	@FXML
	ListView<Photo> searchResultsList;

	@FXML
	ListView<String> photoDetailsList;

	@FXML
	AnchorPane searchResultsAnchor;

	@FXML
	TextField newAlbumName;

	@FXML
	ImageView photo;

	@FXML
	VBox prompt;

	@FXML
	DatePicker fromDate,toDate;



	/**
	 * Initializes the search.
	 *
	 * @param arg0
	 * @param arg1 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		searchResultsAnchor.setVisible(false);
		prompt.setVisible(false);

		searchResultsList.setItems(searchResults);
		photoDetailsList.setItems(photoDetails);
		;
		photo.setPreserveRatio(true);

		searchResultsList.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> {

			currentPhoto = searchResultsList.getSelectionModel().getSelectedItem();
			photoDetailsList.getItems().clear();
			photoDetailsList.getItems().add("Photo Caption: " + currentPhoto.toString());
			photoDetailsList.getItems().add("Date Taken: " + currentPhoto.lastModified);
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
	 * Finds photos in the range of date selected by user.
	 */
	public void findInDateRange() {

		LocalDate from = fromDate.getValue();
		LocalDate to = toDate.getValue();

		for (Album a : LoginController.currentUser.getAlbums()) {
			for (Photo p : a.getPhotos()) {

				if (p.lastModified.compareTo(from) >= 0 && p.lastModified.compareTo(to) <= 0) {

					searchResults.add(p);

				}

			}
		}

		searchResultsList.getSelectionModel().selectFirst();
		searchResultsAnchor.setVisible(true);

	}

	/**
	 * Creates the prompt.
	 */
	public void createPrompt() {

		prompt.setVisible(true);

	}

	/**
	 * Creates the prompt that tells the user to enter the name of new album
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
	 * Takes user back to previous screen
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void goBack() throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("/view/UserHome.fxml"));
		Scene nextScene = new Scene(root, 800, 450);
		Photos.changeScene(nextScene);

	}

}
