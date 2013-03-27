package cs213.photoAlbum.model;

import java.io.Serializable;

/**
 * This class represents a tag and its information. 
 * @author Catalina Laverde Duarte
 */
public class Tag implements Serializable ,Comparable<Tag>{

	/**
	 * SUID
	 */
	private static final long serialVersionUID = -9054235967448665716L;
	/**
	 * Type of the tag. Example: Location or Name.
	 */
	private String tagType;
	/**
	 * Value of a specific tag. Example: Name of a City or Name of a person.
	 */
	private String tagValue;
	
	/**
	 * Gets the type of a tag.
	 * @return Tag type
	 */
	public String getTagType() {
		return tagType;
	}
	
	/**
	 * Constructor for the class Tag
	 * @param tagType 
	 * @param tagValue
	 */
	public Tag(String tagType, String tagValue){
		this.tagType = tagType;
		this.tagValue = tagValue;
	}
	
	/**
	 * Sets the type of tag to a new value.
	 * @param tagType New type of tag to be set.
	 */
	public void setTagType(String tagType) {
		this.tagType = tagType;
	}
	
	/**
	 * Gets the value of a tag.
	 * @return Tag value.
	 */
	public String getTagValue() {
		return tagValue;
	}
	
	/**
	 * Sets the value of tag to a new value.
	 * @param tagValue New value of tag to be set.
	 */
	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}
	
	/**
	 * Implementing compareTo for comparisons of Tag objects
	 */
	public int compareTo(Tag tag){
		
		if( (tag.tagType.equalsIgnoreCase(this.tagType)) && (tag.tagValue.equalsIgnoreCase(tag.tagValue)))
			return 0;
		
		return -1;		
	}

}
