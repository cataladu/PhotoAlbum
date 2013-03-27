package cs213.photoAlbum.model;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;

/**
 * Interface implemented by Back end to establish communication between Control and Model for storage and retrieval of information purposes. 
 */
public interface BackendInterface extends Serializable {
	
	Backend readUser() throws FileNotFoundException, IOException, ClassNotFoundException;
	void writeUser(BackendInterface backend) throws IOException; 
	boolean addUser(String id,String fullName);
	boolean deleteUser(String id);
	Hashtable<String,User> getUsers();	
	User getUser(String fullName);
	
}
