package cs213.photoAlbum.simpleView;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * This class is the user creation pop up. It asks for the full name and username of the new user.
 * @author Roberto Ronderos Botero
 */
public class UserCreationPopUp extends JFrame {

	private static final long serialVersionUID = 1L;
	
	protected JLabel fullNameLbl;
	protected JLabel usernameLbl;
	protected JTextField fullNameTF;
	protected JTextField usernameTF;
	protected ImageButton createBtn;
	protected JPanel mainPanel;
	
	protected FlowLayout fl = new FlowLayout();
	protected creationListener cl;
	
	AdminScreen adminScreen;
	
	/**
	 * Constructor for the class UserCreationPopUp
	 * @param adminScreen
	 */
	public UserCreationPopUp(AdminScreen adminScreen){
		this.adminScreen = adminScreen;
		
		cl = new creationListener();
		this.setPreferredSize(new Dimension (200,200));
		mainPanel = new JPanel();
		mainPanel.setLayout(fl);
		mainPanel.setPreferredSize(new Dimension (200,200));
		
		
		fullNameLbl = new JLabel("Full name: ");
		fullNameLbl.setFont(Themes.componentsFont);
		fullNameLbl.setForeground(Themes.themeColor);
		
		usernameLbl = new JLabel("Username: ");
		usernameLbl.setFont(Themes.componentsFont);
		usernameLbl.setForeground(Themes.themeColor);
		
		fullNameTF = new JTextField(15);
		fullNameTF.setEditable(true);
		usernameTF = new JTextField(15);
		usernameTF.setEditable(true);
		
		createBtn = new ImageButton("resources/createUP.jpg");
		createBtn.setPressedIcon(new ImageIcon("resources/createDN.jpg"));
		createBtn.setPreferredSize(new Dimension(100,30));
		createBtn.addActionListener(cl);
		
		this.setBackground(Color.BLACK);
		mainPanel.add(fullNameLbl);
		mainPanel.add(fullNameTF);
		mainPanel.add(usernameLbl);
		mainPanel.add(usernameTF);
		mainPanel.add(createBtn);
				
		mainPanel.setBackground(Color.BLACK);
		mainPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		
		this.add(mainPanel);
		this.pack();
		setLocationRelativeTo(null);

	}
	
	/**
	 * Listener for the button in UserCreationPopUp. It takes all information and creates the new user.
	 * @author Roberto Ronderos Botero
	 */
	protected class creationListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
			adminScreen.backend.addUser(UserCreationPopUp.this.usernameTF.getText(), UserCreationPopUp.this.fullNameTF.getText());
			adminScreen.usersModel.addElement(UserCreationPopUp.this.usernameTF.getText());
			UserCreationPopUp.this.setVisible(false);
		}
		
	}
}
