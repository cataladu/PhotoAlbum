package cs213.photoAlbum.model;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * This class represents an user and all its corresponding information. 
 * @author Catalina Laverde Duarte
 */
public class User implements Serializable {
	
	/**
	 * SUID
	 */
	private static final long serialVersionUID = -3181581315631605580L;
	/**
	 * Username or ID of the user.
	 */
	private String username;
	/**
	 * Full name of the user.
	 */
	private String fullName;
	/**
	 * Array List of Albums the user stores.
	 */
	private Hashtable<String,Album> albums;
	/**
	 * Array List of all photos the user has.
	 */
	private Hashtable<String,Photo> photos;
	
	/**
	 * Constructor for the class User
	 * @param username
	 * @param fullname
	 */
	public User(String username,String fullname){
		this.username = username;
		this.fullName = fullname;
		albums = new Hashtable<String,Album>();
		photos = new Hashtable<String,Photo>();
	}
	
	/**
	 * Returns a specific album given its unique name
	 * @param albumName
	 * @return Album
	 */
	public Album getAlbum(String albumName){
		if(this.albums.get(albumName)!=null){
			return this.albums.get(albumName);
		}
		return null;
	}
		
	/**
	 * Gets the UserName of the User.
	 * @return Username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username of the user to a new value.
	 * @param username New username to be set.
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Gets the full name of the user.
	 * @return Full name of the user.
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Sets the full name of the user to a new value.
	 * @param fullName New full name to be set.
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	/**
	 * Gets all existing albums within a user.
	 * @return All existing albums in a user. 
	 */
	public Hashtable<String,Album> getAlbums() {
		return albums;
	}
	/**
	 * Gets all existing photos within a user.
	 * @return All existing albums in a user. 
	 */
	public Hashtable<String,Photo> getPhotos() {
		if(photos==null){
			return null;
		}		
		return photos;
	}

	/**
	 * Sets all existing albums in a user to a new Hashtable value.
	 * @param albums New Hashtable of albums to be set.
	 */
	public void setAlbums(Hashtable<String,Album> albums) {
		this.albums = albums; 
	}
	/**
	 * Check whether a photo exists in an user or not.
	 * @param fileName
	 * @return boolean
	 */
	public boolean findPhotoInUser(String fileName){
		if(photos.get(fileName)!=null){
			return true;
		}
		return false;
	}
	
	/**
	 * Returns a specific photo in a user given its unique name.
	 * @param fileName
	 * @return photo
	 */
	public Photo getPhoto(String fileName){
		if(photos.get(fileName)!=null){
			return photos.get(fileName);
		}
		return null;
	}
	
	/**
	 * Removes a photo from a user given its unique name.
	 * @param fileName
	 * @return Boolean value to check whether the photo was successfully removed or not. 
	 */
	public boolean removePhoto(String fileName){
		if(photos.get(fileName)!=null){
			photos.remove(fileName);
			return true;
		}
		return false;
	}

	/**
	 * Adds a new album to the Hashtable of albums of the user.
	 * @param albumName Name of the album to be added.
	 * @return Boolean value to check whether the album was successfully added or not.
	 */
	public boolean addAlbum(String albumName){
		
		if(this.albums.get(albumName) != null){
			return false;
		}
		
		Album newAlbum = new Album(albumName);		
		albums.put(albumName,newAlbum);
		return true;
	 }
	 
	/**
	 * Deletes an album from the Hashtable of albums in the user.
	 * @param albumName Name of the album to be deleted.
	 * @return Boolean value to check whether the album was successfully deleted or not.
	 */
	 public boolean deleteAlbum(String albumName){
		 
		if(albums.get(albumName)!=null){
			albums.remove(albumName);
			return true;
		}
		
		return false;
	 }
	 
	 /**
	  * Renames an existing album in the user.
	  * @param newName New name of the album to be renamed.
	  * @param oldName Old name of the album to be renamed.
	  * @return Boolean value to check whether the album was successfully renamed or not. 
	  */
	 public boolean renameAlbum(String newName, String oldName){

		 	if(albums.get(oldName)!=null){
				albums.get(oldName).setAlbumName(newName);
				return true;
			}		 	
			
			return false;
	 }
}
