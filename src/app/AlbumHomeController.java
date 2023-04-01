package app;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.Album;
import model.Photo;
import model.Tag;

// TODO: Auto-generated Javadoc
/**
 * The Class AlbumHomeController.
 */
public class AlbumHomeController implements Initializable {

	@FXML
	Button selectPhotoBtn;

	@FXML
	AnchorPane actionButtons, copyMoveAnchor, addPhotoAnchor, tagPane;

	@FXML
	Button photoDisplayBtn, deleteTag, addTag, quit;

	@FXML
	TextField capField, captionField, tagEntered, tagType;

	@FXML
	ButtonBar recaptionPane;

	@FXML
	Text selectedFileText, photoListTitle;

	@FXML
	ListView<Tag> tags;

	@FXML
	ListView<Photo> photoList;

	@FXML
	ChoiceBox<Album> targetList;

	/** The current photo. */
	public static Photo currentPhoto;
	
	/** The chosen file. */
	private static File chosenFile;
	
	/** The current tag. */
	public static Tag currentTag;
//	public static String currentPhotoTags;

	/**
 * Initialize.
 *
 * @param arg0 
 * @param arg1 
 */
@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		photoListTitle.setText("Photos in Album : " + UserHomeController.currentAlbum.getName());
		photoList.setItems(UserHomeController.currentAlbum.getPhotos());
		photoList.getSelectionModel().selectFirst();

		currentPhoto = photoList.getSelectionModel().getSelectedItem();

		if (UserHomeController.currentAlbum.getPhotos().size() == 0) {
			actionButtons.setVisible(false);
			tagType.setVisible(true);
			tagEntered.setVisible(true);
			tags.setVisible(true);
			recaptionPane.setVisible(false);

		}

		addPhotoAnchor.setVisible(false);
		copyMoveAnchor.setVisible(false);
		tagPane.setVisible(false);

		photoList.setCellFactory(param -> new ListCell<Photo>() {

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

		photoList.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> {

			currentPhoto = photoList.getSelectionModel().getSelectedItem();

			if (currentPhoto != null) {
				tags.setItems(currentPhoto.getTags());

			}

		});

		if (photoList.getSelectionModel().getSelectedItem() != null) {
			tags.setItems(photoList.getSelectionModel().getSelectedItem().getTags());

		}

	}

	/**
	 * Selects the photo from the user.
	 */
	public void selectPhoto() {

		FileChooser fc = new FileChooser();
		File selectedFile = fc.showOpenDialog(null);
		chosenFile = selectedFile;

		try {

			if (selectedFile == null)
				return;

			selectedFileText.setText("Photo to be added: " + selectedFile.getName());
			captionField.setText("");
			addPhotoAnchor.setVisible(true);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/**
	 * Adds the photo to the program.
	 *
	 * @throws MalformedURLException
	 */
	public void addPhoto() throws MalformedURLException {

		if (!captionField.getText().trim().isEmpty()) {
			if (chosenFile != null) {

				Photo newPhoto = new Photo(chosenFile, captionField.getText());
				UserHomeController.currentAlbum.addPhoto(newPhoto);
				photoList.getSelectionModel().select(newPhoto);

				addPhotoAnchor.setVisible(false);
				actionButtons.setVisible(true);
				recaptionPane.setVisible(true);

			}

		} else {

			Photos.popUp("Caption is empty!");

		}

	}

	/**
	 * Delete photo from the album.
	 */
	public void deletePhoto() {

		Photo clicked = photoList.getSelectionModel().getSelectedItem();
		UserHomeController.currentAlbum.removePhoto(clicked);

		if (UserHomeController.currentAlbum.getPhotos().size() == 0) {
			actionButtons.setVisible(false);
			tagPane.setVisible(false);
			recaptionPane.setVisible(false);
		}

	}

	/**
	 * Copy or move photo.
	 */
	public void copyOrMove() {
		copyMoveAnchor.setVisible(true);
		targetList.setItems(LoginController.currentUser.getAlbums());
		targetList.getSelectionModel().select(UserHomeController.currentAlbum);

		targetList.getSelectionModel().selectFirst();

	}

	/**
	 * Copy the photo to another album.
	 */
	public void copy() {
		Album targetAlbum = targetList.getSelectionModel().getSelectedItem();
		targetAlbum.addPhoto(currentPhoto);

		copyMoveAnchor.setVisible(false);
		Photos.popUp("Selected Photo has been Copied to target Album");

	}

	/**
	 * Moves the photo to another album.
	 */
	public void move() {
		Album targetAlbum = targetList.getSelectionModel().getSelectedItem();
		targetAlbum.addPhoto(currentPhoto);
		UserHomeController.currentAlbum.removePhoto(currentPhoto);

		copyMoveAnchor.setVisible(false);
		Photos.popUp("Selected Photo has been Moved");

	}

	/**
	 * Open photo display. Takes user to a different scene
	 *
	 * @throws IOException 
	 */
	public void openPhotoDisplay() throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("/view/PhotoDisplay.fxml"));
		Scene nextScene = new Scene(root, 800, 450);
		Photos.changeScene(nextScene);
	}

	/**
	 * Go back to previous screen.
	 *
	 * @throws IOException 
	 */
	public void goBack() throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("/view/UserHome.fxml"));
		Scene nextScene = new Scene(root, 800, 450);
		Photos.changeScene(nextScene);

	}

	/**
	 * Re captionj photo.
	 */
	public void reCaption() {
		photoList.getSelectionModel().getSelectedItem().setCaption(capField.getText());
		photoList.refresh();

	}

	/**
	 * Show tag pane.
	 */
	public void showTagPane() {
		tagPane.setVisible(true);
	}

	/**
	 * Adds the tags.
	 */
	public void addTags() {

		// ObservableList<Tag> tagList =
		// photoList.getSelectionModel().getSelectedItem().getTags();
		Photo photo = photoList.getSelectionModel().getSelectedItem();

		// Tag newTag = new Tag(tagType.getText(), tagEntered.getText());
		photo.addTag(tagType.getText(), tagEntered.getText());
		tags.setItems(photo.getTags());

		tagType.clear();
		tagEntered.clear();

//		System.out.println("clicked");

		// photo.getTags().add(newTag);

		tags.refresh();
		tags.getSelectionModel().select(0);

		photoList.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> {
			// Tag currentTag = photoList.getSelectionModel().getSelectedItem();

			if (photoList.getSelectionModel().getSelectedItem() != null) {
				showDetails();

			}
		});

		Photos.popUp("Tag has been added");
		tagPane.setVisible(false);

	}

	/**
	 * Show details.
	 */
	public void showDetails() {
		tags.setItems(photoList.getSelectionModel().getSelectedItem().getTags());
		int index = tags.getSelectionModel().getSelectedIndex();

		if (index != -1) {
			// tags.getItems().clear();

			tags.getItems().add(currentTag);

		} else {
			// tags.getItems().clear();
		}

	}

	/**
	 * Delete tags.
	 */
	public void deleteTags() {

		tags.getItems().remove(tags.getSelectionModel().getSelectedItem());
		tags.refresh();

	}

	/**
	 * Quit.
	 */
	public void quit() {
		System.exit(0);
	}

}
