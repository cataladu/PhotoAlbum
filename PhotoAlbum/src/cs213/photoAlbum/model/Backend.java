package cs213.photoAlbum.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;

/**
 * This class refers to functionality that will allow for the storage and retrieval from the directories data and photos.
 * It respects the MVC architecture by the use of interfaces.
 * @author Catalina Laverde Duarte 
 */
public class Backend implements Serializable, BackendInterface {
	
	/**
	 * SUID
	 */	
	private static final long serialVersionUID = 1555081347938717291L;
	private static final String dirName = "data";
	private static final String fileName = "users.dat"; 
		
	/**
	 * Hashtable of existing users in the program.
	 */
	private Hashtable<String,User> users;

	
	public Backend(){
		users = new Hashtable<String,User>();
	}
	
	/**
	 * Loads users from memory.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public Backend readUser() throws FileNotFoundException, IOException, ClassNotFoundException{
		try{
		ObjectInputStream ois = 
			new ObjectInputStream(new FileInputStream(dirName + File.separator + fileName));
		return (Backend)ois.readObject();
		}
		catch(Exception e){
			Backend backend = new Backend();
			return backend;
		}
	}

	/**
	 * Saves users into memory.
	 * @param backend An instance of Backend Interface.
	 */
	public void writeUser(BackendInterface backend) 
	throws IOException {
		
		ObjectOutputStream oos = 
			new ObjectOutputStream(new FileOutputStream(dirName + File.separator + fileName));
		oos.writeObject(backend);
		
	}
	
	/**
	 * Adds a new user to the Hashtable of users.
	 * @param id ID of the user to be added.
	 * @param fullName Full name of the user to be added.
	 * @return Boolean value to check whether the user was successfully added or not.
	 */
	public boolean addUser(String id, String fullName){
		if(this.users.get(id)==null){
			User user = new User(id,fullName);
			this.users.put(id,user);
			return true;
		}
		return false;
	}
	
	/**
	 * Deletes a user from the Hashtable of Users.
	 * @param username Username of the user to be deleted.
	 * @return Boolean value to check whether the user was successfully deleted or not.
	 */
	public boolean deleteUser(String username){
		
		if(this.users.get(username)!=null){
			this.users.remove(username);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Returns the current list of users in the program.
	 * @return Hashtable of Users.
	 */
	public Hashtable<String,User> getUsers(){		
		return this.users;		
	}
	
	/**
	 * It returns a specific User to Control.
	 * @param username Username of the user to be retrieved.
	 * @return User The user
	 */
	public User getUser(String username){
		
		if(this.users.get(username)!=null){
			return this.users.get(username);
		}
		
		return null;		
	}	
}
