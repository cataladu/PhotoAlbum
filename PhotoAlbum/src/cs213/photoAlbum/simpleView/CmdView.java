package cs213.photoAlbum.simpleView;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import cs213.photoAlbum.control.*;
import cs213.photoAlbum.model.*;

/**
 * This class is the mean of communication between the User and the program.
 * @author Roberto Ronderos Botero
 */

public class CmdView {

	public static void main(String[] args) throws Exception{
		 
		BackendInterface backend= new Backend(); //Variable to communicate with Model merely for modification and retrieval of information.
	    backend= backend.readUser(); //Loading program
		mainMenu(backend,args); //Calling Main Menu
	}
	
	
	private static enum commandsNM {
		 listusers, adduser, deleteuser, login, exit;
	}
	
	/**
	 * It performs all the command line mode for handling of users in the program
	 * @param backend
	 * @param args
	 * @throws IOException
	 */
	private static void mainMenu(BackendInterface backend,String[] args) throws IOException{
		
		ControlInterface control = new Control(backend);; //Variable to communicate with Control and perform all logic and data processes.
		String username,fullName; 			
		Boolean done=false;
		
		Hashtable<String,User> users = new Hashtable<String,User>();
		commandsNM command= commandsNM.login ;
	
				
		try {
			command = commandsNM.valueOf(args[0]);
		}
		
		catch(Exception e){
			System.out.println("Not a valid command, try again...");
			System.exit(1);
		}
				
		//Switch for command
		
		switch(command){
		
			case listusers: 
				
				System.out.println("Listing Existing users: ");
				users= control.getUsers();
				
				if(users.size()>0){
					for (User user : users.values() ) {
						System.out.println(user.getUsername());
					}					
				}
				
				else {
					System.out.println("No users exist");
				}
				break;
				
			case adduser:
									
					try{ 
						
						username= args[1];
						try{
						
							fullName = args[2];
							done=control.addUser(username, fullName);					
							
							if(!done){
								users=control.getUsers();
							
								for (User user : users.values() ) {
								
									if(user.getUsername()==username)
										fullName=user.getFullName();
								}
								System.out.println("User "+username+" already exists with name "+fullName);
							}
							else{
								System.out.println("Created "+username+" with name "+fullName);
							}			
						}
						catch(Exception e){
							System.out.println("Please input the name, try again...");
						}
					}
					catch(Exception e){
						System.out.println("Please input the username, try again...");
					}
								
				
											
				break;
				
			case deleteuser:
				
					try{	
						username= args[1];
						done = control.deleteUser(username);
						
						if(!done){
							System.out.println("user "+username+" does not exist");
						}
						
						else{
							System.out.println("Deleted "+username);
						}	
					}
					catch(Exception e){
						System.out.println("Please input the username, try again...");
					}				
				break;
				
			case login: 	
				
				try{	
					
					username = args[1];
					
					done = control.logIn(username);
					
					if(!done){
						System.out.println("user "+username+" does not exist");
						
					}
					else{						
						InteractiveMode(control, backend);
					}
				}
				catch(Exception e){
					System.out.println("Please input the username, try again...");
					
				}
				break;
				
			case exit:
				
				System.exit(0);
		}
		
		backend.writeUser(backend);
	}
	
	private static enum commandsIM {
		createAlbum , deleteAlbum , listAlbums , listPhotos , addPhoto , movePhoto , removePhoto , addTag , deleteTag , listPhotoInfo ,
		getPhotosByDate , getPhotosByTag , logout ;
	}
	
	/**
	 * It performs all the operations for the interactive mode of the menu
	 * @param control Control Interface to have communication with the Control class to manipulate information
	 * @param backend To retrieve information
	 * @throws IOException 
	 * @throws ParseException
	 */
	private static void InteractiveMode (ControlInterface control, BackendInterface backend) throws IOException, ParseException{

		System.out.println("Input the command:");
		String input;
		String tokens[];
		input = askForInput();
		String regex = "";
		
		if(input.indexOf("\"")!=-1&&input.indexOf(":")==-1){
		    regex = "\"";
		    tokens = input.split(regex);
		}
		
		else if(input.indexOf(":")!=-1){
			regex = "\\s"; 
			tokens = input.split(regex,2);
		}
		
		else{
			regex = "\\s"; 
			tokens = input.split(regex,2);
		}
		
		String albumName;
		String albumName2;
		String filename;
		String caption;
		String tagType;
		String tagValue;
		String startDate;
		String endDate;
		
		int iDone=0;
		boolean done=false;
		
		tokens[0]=tokens[0].trim();
		commandsIM command= commandsIM.valueOf("logout");
		
		try{			
			command = commandsIM.valueOf(tokens[0]);			
		}
		catch(IllegalArgumentException e){
			System.out.println("Invalid input, try again: ");
			InteractiveMode(control,backend);
		}
		
		switch(command){
		
			case createAlbum: 
				
				try{
					albumName = tokens[1];
					done = control.createAlbum(albumName);
					
					if(done){
						System.out.println("Created album for user "+control.getUser().getUsername()+":" );
						System.out.println(albumName); 						
					}
					
					else{
						System.out.println("Album exists for user "+control.getUser().getUsername()+":" );
						System.out.println(albumName);
					}
				}
				catch(ArrayIndexOutOfBoundsException e){
					System.out.println("Invalid input please input Album Name, try again...");
					InteractiveMode(control,backend);
				}
				break;
				
			case deleteAlbum:
				
				try{
					albumName = tokens[1];
					done= control.deleteAlbum(albumName);
					
					if(done){
						System.out.println("Deleted album from user "+control.getUser().getUsername()+":" );
						System.out.println(albumName);
					}
					
					else{
						System.out.println("Album does not exist for user "+control.getUser().getUsername()+":" );
						System.out.println(albumName);
					}
				}
				catch(ArrayIndexOutOfBoundsException e){
					System.out.println("Invalid input please input Album Name, try again...");
					InteractiveMode(control,backend);
				}
				break;
				
			case listAlbums:	
				
				try{
					Hashtable<String,Album> albums = new Hashtable<String,Album>();
					albums = control.listAlbums();
					
					if(albums.size()>0){
						System.out.println("Albums for user: "+control.getUser().getUsername());
						for(Album album: albums.values()){	
							System.out.println(album.getAlbumName()+" number of photos: "+album.numberOfPhotosBelongingTo()+" ,"+album.minDate(control.getUser())+" - "+album.maxDate(control.getUser()));
						}
					}
					else{
						System.out.println("No albums exist for user "+control.getUser().getUsername());
					}
				}
				catch(NullPointerException e){
					System.out.println("Error:" +e);
					InteractiveMode(control,backend);
				}
				break;
				
			case listPhotos:
				
				Hashtable<String,Photo> photos = new Hashtable<String,Photo>();
				
				try{
					albumName = tokens[1];
					photos = control.listPhotos(albumName);
					System.out.println("Photos for album "+albumName+":"); 
					if(photos.size()>0){
						for(Photo photo: photos.values()){
							System.out.println(photo.getFilename()+" - "+photo.getStringDate());
						}
					}
					else{
						System.out.println("No photos in album "+albumName);
					}
				}
				catch(NullPointerException e){
					System.out.println("No such Album, please try again...");
					InteractiveMode(control,backend);
				}
				catch(ArrayIndexOutOfBoundsException e){
					System.out.println("Please input album name, try again...");
					InteractiveMode(control,backend);
				}
				break;
				
			case addPhoto:		
				
				try{
					filename = tokens[1];
					
					try{						
						caption = null;
						
						try{
						    Photo photo = control.listPhotos().get(filename);
							
						    if(photo.getFilename()!=null){
						    	caption = tokens[3];
						    }
						    else{
							caption = null;
						    }
						 }
						catch(NullPointerException e){
							caption = tokens[3];
						}
						 
						try{
							if(caption==null){	   
								albumName = tokens[3];	
							}
							else{
								albumName = tokens[5];
							}
							if(control.listAlbums().get(albumName).findPhotoInAlbum(filename)==true){
								System.out.println("Photo "+filename+" already exists in album "+albumName); 
								InteractiveMode(control,backend);	
							}
							
									Photo photo = control.addPhoto(filename, caption, albumName);
									if(photo!=null){
										System.out.println("Added photo "+filename+":"); 
										System.out.println(photo.getCaption()+" - Album: "+albumName); 
									}
									else{
										System.out.println("Photo "+filename+" already exists in album "+albumName); 
									}
						}
						catch(NullPointerException e){
							System.out.println("Album does not exist, please try again:");
							InteractiveMode(control,backend);
						}
					catch(ArrayIndexOutOfBoundsException e){
							System.out.println("Please input album name, try again:");
							InteractiveMode(control,backend);
					}
						
					}
			   catch(ArrayIndexOutOfBoundsException e){
				  System.out.println("Please input caption, try again:");
				  InteractiveMode(control,backend);
			   }
					
				}
				catch(ArrayIndexOutOfBoundsException e){
					System.out.println("Please input file name, try again:");
					InteractiveMode(control,backend);
				}
				break;
				
			case removePhoto:
				
				try{
					filename = tokens[1];
					try{
						if(control.listPhotos().get(filename).equals(""));
					}
					catch(NullPointerException e){
						System.out.println(filename+" does not exit");
						InteractiveMode(control,backend);							
					}
						try{
							albumName = tokens[3];
							try{
								if(control.listAlbums().get(albumName).equals(""));
							}
							catch(NullPointerException e){
								System.out.println(albumName+" does not exit");
								InteractiveMode(control,backend);							
							}							
							done = control.removePhoto(filename, albumName)	;
							if(done){
								System.out.println("Removed photo");
								System.out.println(filename+" - From album "+albumName); 
							}
							else{
								System.out.println("Photo "+filename+" is not in album "+albumName); 
							}							
					}
					catch(ArrayIndexOutOfBoundsException e){
							System.out.println("Please input album name, try again:");
							InteractiveMode(control,backend);
						}
					
				}
				catch(ArrayIndexOutOfBoundsException e){
					System.out.println("Please input file name, try again:");
					InteractiveMode(control,backend);
				}
				break;	
				
			case movePhoto:
				
				try{
					filename = tokens[1];
					try{
						albumName = tokens[3];
						try{
							if(control.listAlbums().get(albumName).equals(""));
						}
						catch(NullPointerException e){
							System.out.println(albumName+" does not exit");
							InteractiveMode(control,backend);							
						}
							
							try{
								albumName2 = tokens[5];
								try{
									if(control.listAlbums().get(albumName2).equals(""));
								}
								catch(NullPointerException e){
									System.out.println(albumName2+" does not exits");
									InteractiveMode(control,backend);							
								}
								
								iDone = control.movePhoto(filename, albumName, albumName2); 
								
								if(iDone==1){
									System.out.println("Moved photo "+filename+":"); 
									System.out.println(filename+" - From album: "+albumName+" to album "+albumName2); 
								}
								
								else if(iDone==0){
									System.out.println("Photo "+filename+" does not exist in "+albumName); 
								}
							}
							catch(ArrayIndexOutOfBoundsException e){
								System.out.println("Please input new album name, try again: ");
								InteractiveMode(control,backend);
							}
					}
					catch(ArrayIndexOutOfBoundsException e){
						System.out.println("Please input the old album name, try again: ");
						InteractiveMode(control,backend);
					}
					
				}
				catch(ArrayIndexOutOfBoundsException e){
					System.out.println("Please input the file name, try again: ");
					InteractiveMode(control,backend);
				}
				break;
				
			case addTag:
				
				try{
					String regex2 = "\\s";
					String splits[] = tokens[1].split(regex2);
					filename = splits[0];
					filename = filename.substring(1,filename.length()-1);
					try{
						if(control.listPhotos().get(filename).equals(""));
					}
					catch(NullPointerException e){
						System.out.println(filename+" does not exit");
						InteractiveMode(control,backend);							
					}
					try{
						tagType = splits[1];
						
						if(tagType.indexOf(":")==-1){
							System.out.println("Not a valid argument.\n Use: addTag \"<fileName>\" <tagType>:<tagValue>");
							break;
						}
						
						tagType = tagType.trim();
						int colonPos= tagType.indexOf(":");
						
						if(tagType.indexOf("\"")!=-1){
						tagValue= tagType.substring(colonPos+2,tagType.length()-1);
						}
						
						else{
							System.out.println("Not a valid argument.\n Use: addTag \"<fileName>\" <tagType>:<tagValue>");
							break;
						}
						
						tagType = tagType.substring(0, colonPos);
							
						if(tagValue!=""){
								done = control.addTag(filename, tagType, tagValue);
						
								if(done){
									System.out.println("Added Tag:"); 
									System.out.println(filename+" "+tagType+":"+tagValue); 
								}
								else{
									System.out.println("Tag already exists for "+filename+" "+tagType+":"+tagValue); 
								}
							}
							else{
								System.out.println("Please input tag value, try again:");
								InteractiveMode(control,backend);
							}
					}
					catch(ArrayIndexOutOfBoundsException e){
						System.out.println("Please input tag type, try again:");
						InteractiveMode(control,backend);
					}
					
				}
				catch(StringIndexOutOfBoundsException e){
					System.out.println("Wrong format, use \"<filename>\" <tagType>:\"<tagValue\"> , try again:");
					InteractiveMode(control,backend);
				}
				catch(ArrayIndexOutOfBoundsException e){
					System.out.println("Please input filename, try again:");
					InteractiveMode(control,backend);
				}	
				break;
				
			case deleteTag:
				
				try{
					String regex2 = "\\s";
					String splits[] = tokens[1].split(regex2);
					filename = splits[0];
					filename = filename.substring(1,filename.length()-1);
					try{
						if(control.listPhotos().get(filename).equals(""));
					}
					catch(NullPointerException e){
						System.out.println("Not a valid argument.\n Use: addTag \"<fileName>\" <tagType>:<tagValue>");
						InteractiveMode(control,backend);							
					}
					try{
						tagType = splits[1];
						
						if(tagType.indexOf(":")==-1){
							System.out.println("Not a valid argument.\n Use: addTag \"<fileName>\" <tagType>:<tagValue>");
							break;
						}
						
						tagType = tagType.trim();
						int colonPos= tagType.indexOf(":");
						
						if(tagType.indexOf("\"")!=-1){
						tagValue= tagType.substring(colonPos+2,tagType.length()-1);
						}
						
						else{
							System.out.println("Not a valid argument.\n Use: addTag \"<fileName>\" <tagType>:<tagValue>");
							break;
						}
						
						tagType = tagType.substring(0, colonPos);
							
						if(tagValue!=""){
								done = control.deleteTag(filename, tagType, tagValue);
						
								if(done){
									System.out.println("Deleted Tag:"); 
									System.out.println(filename+" "+tagType+":"+tagValue); 
								}
								else{
									System.out.println("Tag does not exist for "+filename+" "+tagType+":"+tagValue); 
								}
							}
							else{
								System.out.println("Please input tag value, try again:");
								InteractiveMode(control,backend);
							}
					}
					catch(ArrayIndexOutOfBoundsException e){
						System.out.println("Please input tag type, try again:");
						InteractiveMode(control,backend);
					}
					
				}
				catch(StringIndexOutOfBoundsException e){
					System.out.println("Wrong format, use \"<filename>\" <tagType>:\"<tagValue\"> , try again:");
					InteractiveMode(control,backend);
				}
				catch(ArrayIndexOutOfBoundsException e){
					System.out.println("Please input filename, try again:");
					InteractiveMode(control,backend);
				}	
				break;
				
			case listPhotoInfo:
				
				try{
					filename = tokens[1];
					Photo photo= control.listPhotoInfo(filename);
				
					if(photo!=null){
					
						System.out.println("Photo file name: "+photo.getFilename());
						Hashtable<String,String> albums = photo.getAlbums();
						System.out.print("Album: "); 
						int size = albums.size();
						int i=1;
						
						for(String album: albums.values()){
							
							if(i!=size){
								System.out.print(album+" , ");
							}
							else{
								System.out.println(album);
							}
							i++;
						}
						
						System.out.println("Date: "+photo.getStringDate());
						System.out.println("Tags:");
						Hashtable<String,Tag> tags = photo.getTags();
						
						for(Tag tag:tags.values()){ 
							System.out.println(tag.getTagType()+":"+tag.getTagValue());
						}						
					}
					else{
						System.out.println("Photo "+filename+" does not exist");
					}
				}
				
				catch(ArrayIndexOutOfBoundsException e){
					System.out.println("Please input filename, try again: ");
					InteractiveMode(control,backend);
				}	
				break;
				
			case getPhotosByDate:
				
				try{
					String regexSpace = "\\s";
					String split3[] = tokens[1].split(regexSpace);
					startDate = split3[0];
					
						try{
							endDate = split3[1];
							ArrayList<Photo> aLphotos = control.getPhotosByDate(startDate, endDate);
							
							if(aLphotos.size()>0){
								
								for(Photo Photo: aLphotos){
									
									System.out.print(Photo.getCaption()+" - ");
									Hashtable<String,String> albums = Photo.getAlbums();
									System.out.print("Album: "); 
									int size = albums.size();
									int i=1;
									
									for(String album: albums.values()){
										if(i!=size){
											System.out.print(album+" , ");
										}
										
										else{
											System.out.print(album);
										}
										i++;
									}
									
									System.out.println(" - Date: "+Photo.getStringDate());
								}
							}
							else{
								System.out.println("No photos within range "+startDate+" "+endDate); 
							}
						}					
					catch(ArrayIndexOutOfBoundsException e){
							System.out.println("Please input the end date (dd/mm/yyyy) , try again:");
							InteractiveMode(control,backend);
					}	
				}
				
				catch(ArrayIndexOutOfBoundsException e){
					System.out.println("Please input the start date (dd/mm/yyyy) , try again:");
					InteractiveMode(control,backend);
				}	
				break;
				
			case getPhotosByTag:
				
				try{
					
					tagType = tokens[1];
					ArrayList<String> tagsArray = new ArrayList<String>();
					ArrayList<Photo> aLphotos = new ArrayList<Photo>();
					
					if(tagType.indexOf(",")!=-1){ //has commas
							
							String regexCommas = ",";
							String tags[] = tagType.split(regexCommas);
							for(int i=0;i<tags.length;i++){
								if(tags[i].trim().indexOf("\"")==-1){
									  System.out.println("Wrong format, use : <tagType>:\"<tagValue>\"");
									  InteractiveMode(control,backend);
								  }
								tagsArray.add(tags[i].trim());
							}
							aLphotos = control.getPhotosByTag(tagsArray);
						}
					else{
						  if(tagType.trim().indexOf("\"")==-1){
							  System.out.println("Wrong format, use : <tagType>:\"<tagValue>\"");
							  InteractiveMode(control,backend);
						  }
						  tagsArray.add(tagType.trim());
						  aLphotos = control.getPhotosByTag(tagsArray);
						}
					  	
							if(aLphotos.size()>0){
								
								for(Photo Photo: aLphotos){
									
									System.out.print(Photo.getCaption()+" - ");
									Hashtable<String,String> albums = Photo.getAlbums();
									System.out.print("Album: "); 
									int size = albums.size();
									int i=1;
									
									for(String album: albums.values()){
										if(i!=size){
											System.out.print(album+" , ");
										}
										else{
											System.out.print(album);
										}
										i++;
									}
									System.out.println(" - Date: "+Photo.getStringDate());
								}
							}
							else{
								System.out.print("No photos with tag value(s) ");
								int sizeArray= tagsArray.size();
								int i=1;
								
								for(String tagvalues: tagsArray){
									
									if(i!=sizeArray){
										System.out.print(tagvalues+" , ");
									}
									else{
										System.out.println(tagvalues);	
									}
								}
							}
						}					
				catch(ArrayIndexOutOfBoundsException e){
						System.out.println("Please input tags , try again:");
						InteractiveMode(control,backend);
				}					
				break;
				
			default:
				System.out.println("Good Bye "+control.getUser().getUsername()+".");
				System.exit(0);
				
		}
		
		backend.writeUser(backend);
		InteractiveMode(control,backend);
	}
	
	private static String askForInput(){
		
		String result;
		Scanner in = new Scanner(System.in);
		
		do{				
			result= in.nextLine();
			if(result.isEmpty())
				System.out.println("Please input something. Try Again: ");
		}
		while(result.isEmpty());
		
		return result;
	}	
}
