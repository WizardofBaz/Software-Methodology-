package model;

import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// TODO: Auto-generated Javadoc
/**
 * The Class Album. Constructs Album.
 * 
 * @author Osama Syed
 * @author Arbaz Pathan
 * 
 */
public class Album {

	/** The name of album. */
	private String name;
	
	/** The photos. */
	private ObservableList<Photo> photos = FXCollections.observableArrayList();

	/** The most recent photo. */
	private LocalDate earliest;
	
	/** The least recent photo. */
	private LocalDate latest;

	/**
	 * Instantiates a new album.
	 *
	 * @param name 
	 */
	public Album(String name) {
		this.name = name;
	}

	/**
	 * Instantiates a new album.
	 *
	 * @param name 
	 * @param photos 
	 */
	public Album(String name, ObservableList<Photo> photos) {
		this.name = name;
		this.photos = photos;
		updateDates();
	}

	/**
	 * Gets the photos in the list.
	 *
	 * @return photos
	 */
	public ObservableList<Photo> getPhotos() {
		return photos;
	}

	/**
	 * Adds the photo.
	 *
	 * @param photo
	 */
	public void addPhoto(Photo photo) {

		this.photos.add(photo);
		updateDates();

	}

	/**
	 * Gets the earliest photo in the list.
	 *
	 * @return earliest
	 */
	public LocalDate getEarliest() {
		return this.earliest;
	}

	/**
	 * Gets the latest photo in the list.
	 *
	 * @return latest
	 */
	public LocalDate getLatest() {
		return this.latest;
	}

	/**
	 * Gets the name of album.
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * To string.
	 *
	 * @return string
	 */
	public String toString() {

		return this.name;

	}

	/**
	 * Removes the photo from the album.
	 *
	 * @param photo 
	 */
	public void removePhoto(Photo photo) {

		photos.remove(photo);
		updateDates();

	}

	/**
	 * Update the dates of album based on current photos in the album.
	 */
	public void updateDates() {
		if (photos.size() != 0) {

			earliest = photos.get(0).lastModified;
			latest = photos.get(0).lastModified;

			for (Photo p : photos) {

				if (earliest.compareTo(p.lastModified) > 0) {
					earliest = p.lastModified;
				}
			}

			for (Photo q : photos) {

				if (latest.compareTo(q.lastModified) < 0) {
					latest = q.lastModified;
				}
			}
		} else {
			earliest = null;
			latest = null;
		}

	}

	/**
	 * Sets the name of the album.
	 *
	 * @param name 
	 */
	public void setName(String name) {
		this.name = name;
	}

}
