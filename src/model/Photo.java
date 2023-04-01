package model;

import java.io.File;
import java.net.MalformedURLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

// TODO: Auto-generated Javadoc
/**
 * The Class Photo which constructs the photo.
 * 
 * @author Osama Syed
 * @author Arbaz Pathan
 * 
 */
public class Photo {

	/** The caption of the photo. */
	String caption;
	
	/** The tags on the photo. */
	public ObservableList<Tag> tags;

	/** The last modified date of the photo. */
	public LocalDate lastModified;

	/** The image. */
	private Image image;
	

	/**
	 * Instantiates a new photo.
	 *
	 * @param file 
	 * @param caption 
	 * @throws MalformedURLException 
	 */
	public Photo(File file, String caption) throws MalformedURLException {

		// lastModified = new Date(file.lastModified());

		lastModified = Instant.ofEpochMilli(file.lastModified()).atZone(ZoneId.systemDefault()).toLocalDate();

		String filePath = file.toURI().toURL().toString();
		this.image = new Image(filePath);
		this.caption = caption;
		this.tags = FXCollections.observableArrayList();

	}

	/**
	 * Adds the tag to the photo.
	 *
	 * @param key 
	 * @param value 
	 */
	public void addTag(String key, String value) {
		
		this.tags.add(new Tag(key, value));
	}
	
	/**
	 * Gets the tags attached to the photo.
	 *
	 * @return tags
	 */
	public ObservableList<Tag> getTags() {
		return this.tags;
	}

	/**
	 * To string.
	 *
	 * @return string
	 */
	public String toString() {
		return this.caption;
	}

	/**
	 * Gets the image.
	 *
	 * @return image
	 */
	public Image getImage() {
		return this.image;
	}

	/**
	 * Sets the caption of a photo.
	 *
	 * @param caption 
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
	

}
