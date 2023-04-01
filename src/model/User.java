package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// TODO: Auto-generated Javadoc
/**
 * The Class User. 
 * 
 * @author Osama Syed
 * @author Arbaz Pathan
 * 
 * 
 */
public class User {

	/** The username. */
	private String username;
	
	/** The albums. */
	private ObservableList<Album> albums;

	/**
	 * Instantiates a new user.
	 *
	 * @param username
	 */
	public User(String username) {
		this.username = username;
		albums = FXCollections.observableArrayList();
	}

	/**
	 * Adds the album.
	 *
	 * @param album
	 */
	public void addAlbum(Album album) {
		albums.add(album);

	}

	/**
	 * Gets the username.
	 *
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the albums.
	 *
	 * @return albums
	 */
	public ObservableList<Album> getAlbums() {
		return albums;
	}

	/**
	 * To string.
	 *
	 * @return string
	 */
	public String toString() {
		return this.username;
	}

	/**
	 * Delete album.
	 *
	 * @param album 
	 */
	public void deleteAlbum(Album album) {
		albums.remove(album);
	}

}
