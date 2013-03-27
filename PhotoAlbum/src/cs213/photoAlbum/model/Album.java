package cs213.photoAlbum.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;

/**
 * This class represents an album and all its corresponding information. 
 * @author Catalina Laverde Duarte
 */
public class Album implements Serializable {
	/**
	 * SUID 
	 */
	private static final long serialVersionUID = -3314782389344816179L;
	
	/**
	 * Name of the Album
	 */
	private String albumName;
	/**
	 * Hashtable of all photos names stored within an album for search and efficiency purposes.
	*/		
	Hashtable<String,Photo> photos ;
	/**
	 * Hasttable of all photos stored within an album.
	*/
	private Hashtable<String,String> photosInAlbum;
	/**
	 * Constructor for the class Album	
	 * @param albumName
	 */
	public Album(String albumName){
		this.albumName = albumName;
		this.photosInAlbum = new Hashtable<String,String>();
	}
	
	/**
	 * Returns the name of the Album
	 * @return String albumName
	 */
	public String getAlbumName() {
		return albumName;
	}

	/**
	 * Sets the name of the Album
	 * @param albumName 
	 */
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	/**
	 * It returns the number of photos in an album
	 * @return int Number of photos belonging to the specific album.
	 */
	public int numberOfPhotosBelongingTo(){
		return this.photosInAlbum.size();
	}
	/**
	 * Adds a pointer to a photo in the album
	 * @param filename .
	 */
	public void addPhotoInAlbum(String filename) {
		this.photosInAlbum.put(filename,filename);
	}
	/**
	 * Deletes pointer to photo
	 * @param filename
	 */
	public void removePhotoInAlbum(String filename) { //deletes pointer to picture
		this.photosInAlbum.remove(filename);
	}
	/**
	 * Adds a new photo to the album. This Photo is a new instance, meaning that the object didn't exits and will be created.
	 * @param fileName Name of the photo to be added.
	 * @param caption Caption of the photo to be added.
	 * @return Boolean value to check whether the photo was successfully added or not.
	 * @throws ParseException 
	 */
	public boolean addPhoto(String fileName, String caption, User user) {
	 
		//If the photo is not in the album and its not in the user then we need to create a new photo!
		if(photosInAlbum.get(fileName)==null && user.getPhotos().get(fileName)==null){
			
			photosInAlbum.put(fileName,fileName); //Add photo to list of string 
			Photo photo = new Photo(fileName,caption); //Creates actual photo
			photo.addAlbums(this.albumName); //Adds the name of the album to the array of albums ( strings )
			photos = user.getPhotos();
			photos.put(fileName, photo); //Add actual photo to array of photo objects
			return true;
		}
	  
	  
	  else{ //The photo is not in the album but it is in the user!! then we dont need to create a new photo!
		 if(photosInAlbum.get(fileName)==null && user.getPhotos().get(fileName)!=null){
				
				photosInAlbum.put(fileName,fileName); //Add photo to list of string 
				photos = user.getPhotos();
				Photo photo = photos.get(fileName); //Returns photo				
				photo.addAlbums(this.albumName); //Adds the name of the album to the array of albums ( strings )
				return true;
		}
	  }
		
		return false;
	}
	
	/**
	 * Used to know if a photo is in an album
	 * @param fileName
	 * @return true if there photo is in the album, false else.
	 */
	public boolean findPhotoInAlbum(String fileName){
		
		if(photosInAlbum.get(fileName)!=null){
			return true;
		}
		return false;
	}
	
	/**
	 * returns a specific photo in album
	 * @param fileName
	 * @return photo
	 */
	public  Photo getPhotoInAlbum(String fileName, User user){
		
		if(photosInAlbum.get(fileName)!=null){
			
			photos = user.getPhotos();
			
			if(photos.get(fileName)!=null){		
				return photos.get(fileName);
			}
			
			return null;
		}
		return null;
	}
	/**
	 * Returns all photos in a specific album of a specific user 		
	 * @param user
	 * @return Hashtable<String,Photo>
	 */
	public Hashtable<String,Photo> getPhotosInAlbum(User user){
		
		Hashtable<String,Photo> photos = new Hashtable<String,Photo>();
	
		for (String fileName : photosInAlbum.values() ) {
			Photo photo = getPhotoInAlbum(fileName, user);
			photos.put(photo.getFilename(), photo);
		}
				
		return photos;
	}
	
	/**
	 * Deletes a specific photo from an album.
	 * @param fileName Name of the photo to be deleted.
	 * @return Boolean value to check whether the photo was successfully deleted or not.
	 */
	public boolean deletePhoto(String fileName, User user){

		if(photosInAlbum.get(fileName)!=null){
			photos = user.getPhotos();
			photos.remove(fileName); //Remove actual photo
			photosInAlbum.remove(fileName); //remove string filename from album
			return true;
		}
		
		return false;
	}
	
	/**
	 * Edits the attributes in a specific photo.
	 * @param fileName Name of the photo.
	 * @param caption Caption of the photo.
	 * @param tags Hashtable of tags of the photo.
	 * @return Boolean value to check whether the photo was successfully deleted or not.
	 */
	public boolean editPhoto(String fileName, String caption, Hashtable<String,Tag> tags, User user){
		
		if(photosInAlbum.get(fileName)!=null){
			photos = user.getPhotos();
			photos.get(fileName).setCaption(caption); //Editing photo object - caption
			photos.get(fileName).setTags(tags); //Editing photo object - tags
			return true;
		}		
		
		return false;
	}
	
	/**
	 * Recaptions an existing photo in an album.
	 * @param fileName Name of the photo to be recaptioned. 
	 * @param caption New caption to add to the photo. 
	 * @return Boolean value to check whether the photo was successfully recaptioned or not.
	 */
	public boolean recaptionPhoto(String fileName, String caption, User user){

		if(photosInAlbum.get(fileName)!=null){
			photos = user.getPhotos();
			photos.get(fileName).setCaption(caption); 
			return true;
		}	
		
		return false;
	}
	
	/**
	 * It returns the earliest date of the photos in the album
	 * @param user
	 * @return String Earliest date
	 * @throws ParseException
	 */
	public String minDate(User user) throws ParseException{
				
		Hashtable<String,Photo> photos = new Hashtable<String,Photo>();
		photos = getPhotosInAlbum(user);
		
		int i=0;

		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("dd/MM/yyyy");
		Calendar date = Calendar.getInstance();
		date.set(Calendar.MILLISECOND,0);
		String currentDate = sdf.format(date.getTime());
				
		if(photos.size()>0){
			
			for(Photo photo: photos.values()){
				
				if(i==0){
					date = photo.getCalendarDate();
					currentDate = sdf.format(date.getTime());
				}
				
				if(photo.getCalendarDate().compareTo(date)<0){
					date = photo.getCalendarDate();
					currentDate = sdf.format(date.getTime());
				}
				i++;
			}
		}
		
		return currentDate;		
	}
	
	/**
	 * Returns the latest date of the photos in an album
	 * @param user
	 * @return Latest date
	 * @throws ParseException
	 */
	public String maxDate(User user) throws ParseException{

		Hashtable<String,Photo> photos = new Hashtable<String,Photo>();
		photos = getPhotosInAlbum(user);
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("dd/MM/yyyy");
		Calendar date = Calendar.getInstance();
		date.set(Calendar.MILLISECOND,0);
		String currentDate = sdf.format(date.getTime());
		
		int i=0;
		
		if(photos.size()>0){
			for(Photo photo: photos.values()){
				if(i==0){
					date = photo.getCalendarDate();
					currentDate = sdf.format(date.getTime());
				}
				if(photo.getCalendarDate().compareTo(date)>0){
					date = photo.getCalendarDate();
					currentDate = sdf.format(date.getTime());
				}
				
				i++;
			}
		}
		
		return currentDate;
	}
}
