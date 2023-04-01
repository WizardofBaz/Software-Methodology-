package app;

import java.io.IOException;
import java.io.Serializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.Tag;

// TODO: Auto-generated Javadoc
/**
 * The Class PhotoDisplayController. Allows the user to see the image in full screen
 * and see the tags associated with the photo.
 * 
 * @author Osama Syed
 * @author Arbaz Pathan
 */
public class PhotoDisplayController implements Initializable {

	@FXML
	ImageView displayedPhoto;

	@FXML
	Button prevPhotoBtn,nextPhotoBtn;


	@FXML
	Text photoName,photoDate;


	@FXML
	ListView<Tag> tags;


	/** The current index of photo */
	static int currentIndex;

	/**
	 * Initializes photo display.
	 *
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		if (UserHomeController.currentAlbum.getPhotos().size() == 1) {
			prevPhotoBtn.setVisible(false);
			nextPhotoBtn.setVisible(false);

		}

		updateFields();
		displayedPhoto.setFitHeight(300);
		displayedPhoto.setFitWidth(300);
		displayedPhoto.setPreserveRatio(true);

	}

	/**
	 * Allows the user to switch between photos as a slideshow. 
	 *
	 * @param e
	 */
	public void switchPhoto(ActionEvent e) {

		int albumSize = UserHomeController.currentAlbum.getPhotos().size();
		currentIndex = UserHomeController.currentAlbum.getPhotos().indexOf(AlbumHomeController.currentPhoto);

		if (e.getSource().equals(prevPhotoBtn)) {
			// move back one photo
			currentIndex--;

			if (currentIndex < 0) {
				currentIndex = albumSize - 1;
			}

			AlbumHomeController.currentPhoto = UserHomeController.currentAlbum.getPhotos().get(currentIndex);

		} else {

			currentIndex++;

			if (currentIndex > albumSize - 1) {
				currentIndex = 0;
			}

			AlbumHomeController.currentPhoto = UserHomeController.currentAlbum.getPhotos().get(currentIndex);

		}

		updateFields();
	}

	/**
	 * Allows the user to go back to album home.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void goBack() throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("/view/AlbumHome.fxml"));
		Scene nextScene = new Scene(root, 800, 450);
		Photos.changeScene(nextScene);

	}

	/**
	 * Updates fields the fields of date and caption.
	 */
	public void updateFields() {
		displayedPhoto.setImage(AlbumHomeController.currentPhoto.getImage());
		photoName.setText("Photo Caption: " + AlbumHomeController.currentPhoto.toString());
		photoDate.setText("Date Taken: " + AlbumHomeController.currentPhoto.lastModified.toString());
		tags.setItems(AlbumHomeController.currentPhoto.getTags());
	}

//	public void tagList() {
//		ArrayList<Tag> tagList = photos.getSelectionModel().getSelectedItem().getTags();
//		
//			
//	}
	
/**
 * closes the application.
 */
public void quit() {
		System.exit(0);
	}

}
