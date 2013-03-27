package cs213.photoAlbum.model;

import java.io.Serializable;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;

/**
 * This class represents a photo and all its corresponding information. 
 * @author Roberto Ronderos Botero
 */
public class Photo implements Serializable, Comparable<Photo> {
	
	/**
	 * SUID
	 */
	private static final long serialVersionUID = 8866790839491002809L;

	/**
	 * Name of the photo
	 */
	private String filename=null;
	/**
	 * Date that the photo was taken.
	 */
	private Calendar date; 
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy-H:m:s");
	private String dateString;
	/**
	 * Description of the photo.
	 */
	private String caption;
	/**
	 * List of tags the photo contains.
	 */
	private Hashtable<String,Tag> tags;
	/**
	 * Hash table of all albums where the photo belongs to.
	*/
	private Hashtable<String,String> albums;
	/**
	 * Hash table of all tags values the photo has for searching and efficiency purposes.
	*/
	private Hashtable<String,String> tagValues;
	
	/**
	 * Constructor for the class Photo without tags.
	 * @throws ParseException 
	 */
	protected Photo(String fileName, String caption) {
		this.filename = fileName;
		this.caption = caption;		
		setDate(this.date);
		this.dateString = sdf.format(date.getTime());				
		this.tags = new Hashtable<String,Tag>();
		this.albums = new Hashtable<String,String>();
		this.tagValues = new Hashtable<String,String>();
	}
	
	/**
	 * Constructor for the class Photo with tags.
	 */
	protected Photo(String fileName, String caption, Hashtable<String,Tag> tags){
		this.filename = fileName;
		this.tags = tags;
		this.caption = caption;
		setDate(this.date);
		this.tags = new Hashtable<String,Tag>();
		this.albums = new Hashtable<String,String>();
	}
	
	/**
	 * It returns to how many albums a photo belongs to
	 * @return Number of albums
	 */
	public int numberOfAlbumsBelongingTo(){
		return this.albums.size();
	}
	
	/**
	 * Returns hashtable of TagValues
	 * @return tagValu\es
	 */
	public Hashtable<String,String> getTagValues(){
		return this.tagValues;
	}
	
	/**
	 * Gets the name of the photo
	 * @return Name of photo
	 */
	public String getFilename() {
		return filename;
	}
	
	/**
	 * Sets the name of the photo to a new value.
	 * @param filename New name of photo.
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	/**
	 * Gets the date the photo was taken.
	 * @return  String Date of the photo.
	 */
	public String getStringDate() {
		return this.dateString;
	}
	/**
	 * Gets the date the photo was taken.
	 * @return Calendar object Date of the photo.
	 */
	public Calendar getCalendarDate() {
		return this.date;
	}
	
	/**
	 * Sets the date of the photo to a new value.
	 * @param date New date of photo.
	 */
	private void setDate(Calendar date) {
		this.date = Calendar.getInstance();
	}
	
	/**
	 * Gets the caption of the photo.
	 * @return Caption of the photo.
	 */
	public String getCaption() {
		return caption;
	}
	
	/**
	 * Sets the caption of the photo to a new value.
	 * @param caption New caption of photo.
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * Gets the Array List of tags the photo contains. 
	 * @return ArrayList<Tag> Array List of tags contained in photo.
	 */
	public Hashtable<String,Tag> getTags() {
		return this.tags;
	}
	
	public void setTags(Hashtable<String,Tag> tags) {
		this.tags=tags;
	}
	
	/**
	 * Adds a new tag to a photo.
	 * @param tagName Name of the tag (Location, Person...)
	 * @param tagValue Value of the specific tag (City, Name...)
	 * @return Boolean value to check whether the tag was successfully added or not.
	 */
	public boolean addTag(String tagName, String tagValue){
		
	    tagName = tagName.toUpperCase();
	    tagValue = tagValue.toLowerCase();
	    
		String key = tagName+tagValue; //Key used to index a tag
		if(this.tags.get(key)!=null){
			return false;
		}
		Tag tag = new Tag(tagName,tagValue);
		tags.put(key,tag);
		tagValues.put(tagValue,tagValue);
		return true;
	}
	/**
	 * Removes tag to a photo.
	 * @param tagName Name of the tag (Location, Person...)
	 * @param tagValue Value of the specific tag (City, Name...)
	 * @return Boolean value to check whether the tag was successfully removed or not.
	 */
	public boolean removeTag(String tagName, String tagValue){
		
		tagName = tagName.toUpperCase();
	    tagValue = tagValue.toLowerCase();
		
		String key = tagName+tagValue; //Key used to index a tag
		if(this.tags.get(key)==null){
			return false;
		}
	
		tags.remove(key);
		tagValues.remove(tagValue);
		return true;
	}	
	
	/**
	 * Implements Comparable for comparisons of objects of type Photo (Used for date comparisons ONLY)
	 * @param photo
	 * @return compared value
	 */
	public int compareTo(Photo photo){
		
		if(this.date.before(photo.date))
			return -1;
		
		else if(this.date.after(photo.date))
			return 1;
			
		return 0;
	}
	
	/**
	 *  Returns all name of albums where this photo is stored	  
	 * @return Hashtable<String,String> albums
	 */
	public Hashtable<String,String> getAlbums() {
		return albums;
	}
	/**
	 * Adds an album to a Hashtable of albums (strings).
	 * @param albumName Name of the album to be added
	 */
	public void addAlbums(String albumName) {
		this.albums.put(albumName,albumName);
	}

}
