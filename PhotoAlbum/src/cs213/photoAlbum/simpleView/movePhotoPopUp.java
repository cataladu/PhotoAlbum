package cs213.photoAlbum.simpleView;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * This class is the screen shown when a photo needs to be moved from one album to another.
 * @author Roberto Ronderos Botero
 *
 */
public class movePhotoPopUp extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[] albums ;
	PhotoDisplay photoDisplay;
	JComboBox albumsCB;	
	protected ImageButton moveBtn;
	protected JLabel title;
	
	
	/**
	 * Constructor for the class movePhotoPopUp
	 * @param albums
	 * @param photoDisplay
	 */
	public movePhotoPopUp(String[] albums, PhotoDisplay photoDisplay){
		this.photoDisplay = photoDisplay;
		this.albums = albums;		
		
		setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		super.setBackground(Color.BLACK);
		this.getRootPane().setBackground(Color.BLACK);
		this.getLayeredPane().setBackground(Color.BLACK);
		this.getContentPane().setBackground(Color.BLACK);
		this.setBackground(Color.BLACK);
		this.setPreferredSize(new Dimension(300,150));
		
		title = new JLabel("Select album: ");
		title.setFont(Themes.titleFont);
		title.setForeground(Themes.themeColor);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setBackground(Color.black);
		
		albumsCB = new JComboBox(albums);
		albumsCB.setSelectedIndex(0);
		albumsCB.addActionListener(this);
		albumsCB.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		moveBtn = new ImageButton(new ImageIcon("resources/moveUP.jpg"));
		moveBtn.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				movePhotoPopUp.this.photoDisplay.movePhoto();				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}			
			
		});
		
		add(new Box.Filler(new Dimension(0,10),new Dimension(0,10),new Dimension(0,10)));
		add(title);
		add(new Box.Filler(new Dimension(0,10),new Dimension(0,10),new Dimension(0,10)));
		add(albumsCB);
		add(new Box.Filler(new Dimension(0,10),new Dimension(0,10),new Dimension(0,10)));
		add(moveBtn);
		setTitle("Move Photo to another album");
		pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		addWindowListener(new WindowAdapter(){
			
			public void windowClosing(WindowEvent we){					
				movePhotoPopUp.this.setVisible(false);				
			 }
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {		
        String newAlbumName = (String)this.albumsCB.getSelectedItem();
        this.photoDisplay.newAlbumName = newAlbumName;		
	}
		
}
