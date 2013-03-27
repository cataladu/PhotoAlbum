package cs213.photoAlbum.simpleView;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import cs213.photoAlbum.control.ControlInterface;

/**
 * Class for the screen that the user is going to see when an album is going to be created. It asks for the name of the album and makes sure it
 * does not exist already in the user's sesion. 
 * @author Catalina Laverde Duarte
 **/
public class AlbumCreationPopUp extends JFrame {

	private static final long serialVersionUID = 1L;
	protected JTextField albumNameTF;
	protected JLabel albumName;
	protected ImageButton create;
	protected JPanel right;
	protected JPanel container;
	AlbumsScreen albumScreen;
	ControlInterface control;
	protected FlowLayout fl = new FlowLayout();
	protected creationListener1 cl;
	
	/**
	 * Constructor for the class AlbumCreationPopUp
	 * @param albumsScreen
	 * @param control
	 */
	public AlbumCreationPopUp(AlbumsScreen albumsScreen, ControlInterface control){
		
		cl = new creationListener1();
		
		container = new JPanel();
		container.setLayout(fl);
		
		this.albumScreen = albumsScreen;
		this.control = control;
		
		albumNameTF = new JTextField(20);
		albumNameTF.setEditable(true);
		
		albumName = new JLabel("Name of new album: ");
		albumName.setFont(Themes.componentsFont);
		albumName.setForeground(Themes.themeColor);
		
		create = new ImageButton("resources/createUP.jpg");
		create.setPressedIcon(new ImageIcon("resources/createDN.jpg"));
		create.setPreferredSize(new Dimension(100,30));
		create.addActionListener(cl);
		
		container.add(albumName);
		container.add(albumNameTF);
		container.add(create);
		this.add(container);
		
		container.setBackground(Color.BLACK);
		container.setAlignmentY(Component.CENTER_ALIGNMENT);
		container.setBorder(new EmptyBorder(10, 10, 10, 10));
		setSize(260,145);
		setResizable(false);	
		setLocationRelativeTo(null);
		
		//Add listener to save the state of the user
				addWindowListener(new WindowAdapter(){
					
					public void windowClosing(WindowEvent we){
						setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					 }
				});
		
	}
	
	/**
	 * Listener for the button of creation.
	 * @author Catalina
	 *
	 */
	protected class creationListener1 implements ActionListener {
		
		public void actionPerformed(ActionEvent e){
			
			String albumName = albumNameTF.getText();
			
			if(albumName.equals("")){
            	final JFrame error = new JFrame("Important Message!");
				JOptionPane.showMessageDialog(error, "Please enter a name for the new Album");
			}
			
			else{
			
				try {
					AlbumCreationPopUp.this.albumScreen.addAlbum(AlbumCreationPopUp.this.control, albumName);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
						
		}
		
	}
	
}
