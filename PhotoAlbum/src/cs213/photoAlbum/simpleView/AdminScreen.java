package cs213.photoAlbum.simpleView;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import cs213.photoAlbum.control.Control;
import cs213.photoAlbum.control.ControlInterface;
import cs213.photoAlbum.model.*;

/**
 * This class represents the screen the administrator is going to see. Creation and deletion of users is done in this class.
 * @author Roberto Ronderos Botero *
 */
public class AdminScreen extends JFrame{

	private static final long serialVersionUID = 1L;
	
	protected JLabel title;
	protected ImageButton addUserBtn;
	protected ImageButton deleteUserBtn;
	protected ImageButton logOutBtn;
	protected JPanel buttonsPanel;
	protected JList users;
	protected DefaultListModel usersModel = new DefaultListModel();
	protected JScrollPane scrollPane;
	protected JPanel mainPanel;
	protected UserCreationPopUp createUser;
	
	protected OptionsListener ol;
	protected BackendInterface backend;
	protected Hashtable<String,User> listUsers = new Hashtable<String,User>();
	protected loginScreen login;
	protected ControlInterface control;
	
	/**
	 * Constructor for the class AdminScreen
	 * @param backend Establishes communication with the users of the program
	 * @param login Login is used here to control the visibility of the screen.
	 */
	public AdminScreen(BackendInterface backend, final loginScreen login){
		
		this.control = new Control(backend);
		this.login = login;
		this.setBackground(Color.BLACK);
		listUsers = backend.getUsers();
		fillListUsers();
		
		ol = new OptionsListener();
		this.backend = backend;
		
		title = new JLabel("Administrative Mode");
		title.setFont(Themes.titleFont);
		title.setForeground(Themes.themeColor);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setBorder(new EmptyBorder(0,5,10,5));
		
		addUserBtn = new ImageButton("resources/addUserUP.jpg");
		addUserBtn.setActionCommand("Add user");
		addUserBtn.setPreferredSize(new Dimension(100,30));
		addUserBtn.setPressedIcon(new ImageIcon("resources/addUserDN.jpg"));
		addUserBtn.addActionListener(ol);
		addUserBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		deleteUserBtn = new ImageButton("resources/deleteUserUP.jpg");
		deleteUserBtn.setActionCommand("Delete User");
		deleteUserBtn.setPreferredSize(new Dimension(100,30));
		deleteUserBtn.setPressedIcon(new ImageIcon("resources/deleteUserDN.jpg"));
		deleteUserBtn.addActionListener(ol);
		deleteUserBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		logOutBtn = new ImageButton("resources/logoutUP.jpg");
		logOutBtn.setActionCommand("Log Out");
		logOutBtn.setPreferredSize(new Dimension(100,30));
		logOutBtn.setPressedIcon(new ImageIcon("resources/logoutDN.jpg"));
		logOutBtn.addActionListener(ol);
		logOutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel,BoxLayout.Y_AXIS));
		buttonsPanel.add(addUserBtn);
		buttonsPanel.add(deleteUserBtn);
		buttonsPanel.add(logOutBtn);
		buttonsPanel.setBackground(Color.BLACK);
		buttonsPanel.setBorder(new EmptyBorder(20,40,20,20));
		
		users = new JList(usersModel);
		users.setBorder(new EmptyBorder(20,10,40,20));
		scrollPane = new JScrollPane(users);
				
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.BLACK);
		mainPanel.setLayout( new BorderLayout());
		
		this.add(mainPanel);
		mainPanel.add(title,BorderLayout.PAGE_START);
		mainPanel.add(buttonsPanel,BorderLayout.LINE_START);
		mainPanel.add(scrollPane,BorderLayout.LINE_END);
		mainPanel.setBorder(new EmptyBorder(20,20,20,20));
		this.setPreferredSize(new Dimension(430,250));
		this.pack();
		
		addWindowListener(new WindowAdapter(){
			
			public void windowClosing(WindowEvent we){
				AdminScreen.this.saveState();
				System.exit(0);
				
			 }
		});
	}
	
	/**
	 * Adds all users to the list reading from backend.
	 */
	public void fillListUsers(){

		for(User user:listUsers.values()){
			usersModel.addElement(user.getUsername());
		}
		
	}
	
	/**
	 * Saves all changes made to the state of the program.
	 */
	public void saveState(){
		try {
			this.backend.writeUser(this.backend);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * It deletes all pictures in the user's account once the user is deleted.
	 * @param user
	 */
	public void deleteUserPhotos(String user){
		
		String PathResources = "pictures";
		String PathThumnails = "thumbnails";
		
		control.logIn(user);
		
		Hashtable<String,Photo> photos = new Hashtable<String,Photo>();
		photos = control.listPhotos();
		
		for(Photo photo:photos.values()){
			if(photo.numberOfAlbumsBelongingTo()==1){
				String file =photo.getFilename();
				String[] fileInfo = new String[2];
				fileInfo=file.split("\\.");
				String fileName = fileInfo[0];
				String fileExtension = fileInfo[1];
				
				File photoF = new File(PathResources+File.separator+fileName+"."+fileExtension);
				File photoThumbF = new File(PathThumnails+File.separator+fileName+"thumb."+fileExtension);
				
				photoF.delete();
				photoThumbF.delete();
			}			
		}						
		
		
	}
	
	/**
	 * OptionsListener for all actions performed when a button of this screen is clicked.  
	 */
	protected class OptionsListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {

			String action = e.getActionCommand();
			
			if(action.equals("Delete User")){
				String username = (String) users.getSelectedValue();
				if(username!=null&&!username.equals("")){
				int index = users.getSelectedIndex();
				deleteUserPhotos(username);
				AdminScreen.this.backend.deleteUser(username);
				usersModel.remove(index);
				}
			}
			
			if(action.equals("Add user")){
				createUser = new UserCreationPopUp(AdminScreen.this);
				createUser.setVisible(true);
			}
			
			if(action.endsWith("Log Out")){
				AdminScreen.this.saveState();
				AdminScreen.this.setVisible(false);
				AdminScreen.this.login.setVisible(true);
			}
			
		}
		
		
	}
	
}


















