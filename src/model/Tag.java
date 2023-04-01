package model;

// TODO: Auto-generated Javadoc
/**
 * The Class Tag.
 */
public class Tag {

	/** The key. */
	String key;
	
	/** The value. */
	String value;

	/**
	 * Instantiates a new tag.
	 *
	 * @param key 
	 * @param value 
	 */
	public Tag(String key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * To string.
	 *
	 * @return string
	 */
	public String toString() {
		return "Tag Name: " + this.key + " | " + "Tag Value: " + this.value;
	}

	/**
	 * Gets the value part of the tag.
	 *
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Gets the name or key of the tag.
	 *
	 * @return key
	 */
	public String getName() {
		return key;
	}

	/**
	 * Checks if two tags are the same.
	 *
	 * @param other 
	 * @return true, if successful
	 */
	public boolean equals(Tag other) {
		return key.equals(other.key) && value.equals(other.value);
	}

	/**
	 * Gets the key.
	 *
	 * @return key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Sets the key.
	 *
	 * @param key 
	 */
	public void setKey(String key) {
		this.key = key;
	}

}
