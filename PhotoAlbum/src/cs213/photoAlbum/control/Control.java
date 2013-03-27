package cs213.photoAlbum.control;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


import cs213.photoAlbum.model.*;
/**
 * This class implements the algorithmic logic of the program, decision making and data manipulation processes.
 * It communicates with Model through BackendInterface to retrieve a specific user and manipulate it as needed. It respects the MVC architecture by the use
 * of interfaces.
 * @author Roberto Ronderos Botero (First 12 Methods)
 * @author Catalina Laverde Duarte (Last 9 Methods)
 */

public class Control implements ControlInterface {
	
	/**
	 * Interface to communicate with Model.
	 */
	BackendInterface backend;
	/**
	 * User in Control. Control can only manage one user at a time.
	 */
	public User user;
	/**
	 * Default constructor for Control, it's invoked by CmdView.
	 * It initializes an object of type BackendInterface.
	 */
	public Control(BackendInterface backend){
		this.backend = backend;
		
	}
	/**
	 * Returns backend.
	 * @return backend
	 */
	public BackendInterface getBackend() {
		return backend;
	}

	/**
	 * Sets backend to a new value.
	 * @param backend
	 */
	public void setBackend(BackendInterface backend) {
		this.backend = backend;
	}

	/**
	 * Returns user to view.
	 * @return User
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * Sets user to a new value.
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Creates a new album for a specific user.
	 * @param albumName The album name to be created for a specific user.
	 * @return boolean To check whether the album was successfully created or not.	
	 */
	public boolean createAlbum(String albumName){
		return this.user.addAlbum(albumName);
	}

	/**
	 * Deletes an album from a specific user.
	 * @param albumName The album name to be deleted from a specific user.
	 * @return boolean To check whether the album was successfully deleted or not.	
	 */
	public boolean deleteAlbum(String albumName){
		return this.user.deleteAlbum(albumName);
	}
	
	/**
	 * Returns all existing albums in a specific user.	 
	 * @return Hashtable<String,Album> Hashtable of all albums stored in a user.	
	 */
	public Hashtable<String,Album> listAlbums(){
		return user.getAlbums();
	}
	
	/**
	 * Returns all existing photos within a specific album.	 
	 * @return Hashtable<String,Photo> Hashtable of all photos stored within a specific album.	
	 */
	public Hashtable<String,Photo> listPhotos(String albumName){

		Hashtable<String,Photo> photosInAlbum= new Hashtable<String,Photo>();
		
		if(user.getAlbum(albumName)!=null){
			
			photosInAlbum = user.getAlbum(albumName).getPhotosInAlbum(user);
			return photosInAlbum;
		}		
		
		return null;
	}
	
	/**
	 * Returns all existing photos stored in user.	 
	 * @return Hashtable<String,Photo> Hashtable of all photos stored within a specific album.	
	 */
	public Hashtable<String,Photo> listPhotos(){
		return user.getPhotos();
	}
	
	/**
	 * Adds a photo to a specific album in a user.	
	 * @param fileName The name of the photo as it is stored in disk.
	 * @param caption The caption for the photo to be added.
	 * @param albumName The name of the album where the photo is going to be added. 
	 * @return boolean To check whether the photo was successfully added or not.
	 * 
	 */
	public Photo addPhoto(String fileName, String caption, String albumName) {

		if(user.getAlbum(albumName).findPhotoInAlbum(fileName)==false){ //if does not exist in the album add it
			Album album = user.getAlbum(albumName);
			album.addPhoto(fileName, caption,user);
			return user.getPhoto(fileName);
		}
		
		return null;
	}
	
	/**
	 * Moves a photo from a specific album to another in a user.	
	 * @param fileName The name of the photo to be moved as it is stored in disk. 
	 * @param oldAlbum The name of the album the photo is currently contained.
	 * @param newAlbum The name of the album where the photo is going to be moved. 
	 * @return boolean To check whether the photo was successfully moved or not.	
	 */
	public int movePhoto(String fileName, String oldAlbum, String newAlbum){

		if(user.getAlbum(newAlbum).findPhotoInAlbum(fileName)==true){ //If file is already in new album
			return -1;
		}
		if(user.getAlbum(oldAlbum).findPhotoInAlbum(fileName)==false){ //If you didn't find the picture in the album
			return 0;
		}
		
		
		//If non of the previous, proceed to move the picture.
		//Delete 'pointer' from oldAlbum, add 'pointer' to new album
		user.getAlbum(oldAlbum).removePhotoInAlbum(fileName);
		user.getAlbum(newAlbum).addPhotoInAlbum(fileName);
		
		return (int) 1;
	}
	
	/**
	 * Removes a photo from a specific album in a user.	
	 * @param fileName The name of the photo to be removed as it is stored in disk. 
	 * @param albumName The name of the album the photo is going to be removed from.
	 * @return boolean To check whether the photo was successfully removed or not.	
	 */
	public boolean removePhoto(String fileName, String albumName){
		/*If the photo to be deleted only exists in one album, then delete it's instance also, else just delete it's pointer in the album */
		
		if(user.getAlbum(albumName).getPhotoInAlbum(fileName,user)!=null){
			
			if(user.getPhoto(fileName).numberOfAlbumsBelongingTo()>1){ //belongs to more than one album, just delete its pointer at album.
				user.getAlbum(albumName).removePhotoInAlbum(fileName);
				return true;
			}
			else{
				user.getAlbum(albumName).removePhotoInAlbum(fileName); //Remove pointer in album.
				user.removePhoto(fileName); //Remove photo instance permanently.
				return true;
			}
		}		
		return false;
	}
	
	/**
	 * Adds a new tag to a specific photo within an album in a user.
	 * @param fileName The name of the photo the tag is going to be added to. 
	 * @param tagType The type of tag to be added to a specific photo.
	 * @param tagValue The value of tag to be added to a specific photo.
	 * @return boolean To check whether the photo was successfully tagged or not.
	 */
	public boolean addTag(String fileName, String tagType , String tagValue){
		
		if(user.getPhoto(fileName)!=null){
			return user.getPhoto(fileName).addTag(tagType, tagValue);
		}
		return false;
	}
	
	/**
	 * Deletes a new tag from a specific photo within an album in a user.
	 * @param fileName The name of the photo the tag is going to be deleted from. 
	 * @param tagType The type of tag to be deleted from a specific photo.
	 * @param tagValue The value of tag to be deleted from a specific photo.
	 * @return boolean To check whether the tag was successfully deleted or not.	
	 */
	public boolean deleteTag(String fileName, String tagType , String tagValue){
	
		if(user.getPhoto(fileName)!=null){
			return user.getPhoto(fileName).removeTag(tagType, tagValue);
			
		}
		return false;
	}
	
	/**
	 * Returns photo with all its information.
	 * @param fileName The name of the file containing the information.
	 * @return photo Returns the photo.
	 */
	public Photo listPhotoInfo(String fileName){
		return user.getPhoto(fileName);		
	}
	/**
	 * Returns all photos from within the same date range.
	 * @param start The starting date delimiter to retrieve all photos.
	 * @param end The ending date delimiter to retrieve all photos.
	 * @return ArrayList<Photo> Array List of photos within the same date range.
	 * @throws java.text.ParseException 
	 * 
	 */
	public ArrayList<Photo> getPhotosByDate(String start, String end) throws java.text.ParseException {
		
		ArrayList<Photo> photos = new ArrayList<Photo>();
				
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy-H:m:s");
		Date date = (Date) formatter.parse(start);
		
		Calendar startDate = Calendar.getInstance();
		startDate.set(Calendar.MILLISECOND,0); 
		startDate.setTime(date);
		
		
		date = (Date) formatter.parse(end);
		Calendar endDate = Calendar.getInstance();
		endDate.set(Calendar.MILLISECOND,0);
		endDate.setTime(date);
		
		
		if(user.getPhotos()==null)
			return null;
		
		for (Photo photo : user.getPhotos().values() ) {
			Calendar photoDate = photo.getCalendarDate();
			
			if(photoDate.after(startDate) && photoDate.before(endDate))
				photos.add(photo);
				
			else if(photo.getCalendarDate().equals(startDate))
				photos.add(photo);
				
			else if(photo.getCalendarDate().equals(endDate))
				photos.add(photo);
		}
		
		Collections.sort(photos);
		
		return photos;
	}
	/**
	 * Returns all photos with the same given tag.
	 * @param tags ArrayList of Strings with all the tags information to be searched in photos.
	 * @return ArrayList<Photo> Array List containing all photos with the same tag.
	 */
	public ArrayList<Photo> getPhotosByTag(ArrayList<String> tags){
		
		ArrayList<Photo> photos = new ArrayList<Photo>();
		String splitted[];
		
		for(int i=0; i<tags.size(); i++){
			splitted = tags.get(i).split(":");
			
			if(splitted.length==2){ //Complete Tag Information
				String tagType = splitted[0].toUpperCase(); 
				String tagValue= splitted[1].replaceAll("\"", "");
				tagValue = tagValue.toLowerCase(); 
				for(Photo photo: user.getPhotos().values()){					
					if(photo.getTags().get(tagType+tagValue)!=null)
						photos.add(photo);					
				}
			}
			
			else if(splitted.length==1){ //Partial information (Only tag value)
				String tagValue = splitted[0].toLowerCase();
				for(Photo photo: user.getPhotos().values()){					
					if(photo.getTagValues().get(tagValue)!=null)
						photos.add(photo);					
				}				
			}
			
		}
		
		Collections.sort(photos);
		
		return photos;
	}
	
	/**
	 * It logs in a User with the given username.
	 * @return Boolean value to check whether the user was logged in or not.
	 */
	public boolean logIn(String username){
		
		if(backend.getUser(username)!=null){
			User user = backend.getUser(username);
			this.user = user;
			return true;
		}
		return false;
	}
	
	/**
	 * Adds a new user to Photo Album through Backend
	 * @param username Username of the new User to be added.
	 * @param fullName fullName of the new User to be added.
	 * @return Boolean value to check whether the new user was successfully added or not.
	 */
	public boolean addUser(String username, String fullName){
		return backend.addUser(username, fullName);
	}
	
	/**
	 * Deletes a user from Photo Album through Backend
	 * @param username Username of the user to be deleted.
	 * @return Boolean value to check whether the new user was successfully deleted or not.
	 */
	public boolean deleteUser(String username){
		return backend.deleteUser(username);
	}
	
	/**
	 * Returns all existing users in Photo Album.
	 * @return Hashtable<String,User> 
	 */
	public Hashtable<String,User> getUsers(){
		return backend.getUsers();
	}
	
	
}
