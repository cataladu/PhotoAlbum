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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import cs213.photoAlbum.control.ControlInterface;

/**
 * This class is called when a new album is created using the resulting photos of a search. It takes all displayed photos and creates a new album with them.
 * @author Roberto Ronderos Botero *
 */

public class makeAlbumPopUp extends JFrame {

	private static final long serialVersionUID = 1L;
	protected JTextField albumNameTF;
	protected JLabel albumName;
	protected ImageButton create;
	protected JPanel right;
	protected JPanel container;
	PhotosScreen photosScreen;
	ControlInterface control;
	protected FlowLayout fl = new FlowLayout();
	protected creationListener cl;
	
	/**
	 * Constructor for the class makeAlbumPopUp
	 * @param photosScreen
	 * @param control
	 */
	public makeAlbumPopUp(PhotosScreen photosScreen, ControlInterface control){
		
		cl = new creationListener();
		
		container = new JPanel();
		container.setLayout(fl);
		
		this.photosScreen = photosScreen;
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
	 * Listener for the button that indicates the creation of the album
	 * @author Catalina Laverde Duarte
	 *
	 */
	protected class creationListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e){
			
			String albumName = albumNameTF.getText();
			try {
				makeAlbumPopUp.this.photosScreen.makeAlbum(albumName);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
						
		}
		
	}
	
}
