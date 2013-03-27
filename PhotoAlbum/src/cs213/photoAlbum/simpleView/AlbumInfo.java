package cs213.photoAlbum.simpleView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Image;
import java.util.Enumeration;
import java.util.Hashtable;
import cs213.photoAlbum.control.*;
import cs213.photoAlbum.model.*;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * This class represents each album in the user's sesion. Thumbnail and information of the album such as name, number of photos and dates. 
 * @author Catalina Laverde Duarte
 */
public class AlbumInfo extends JPanel implements MouseListener{

	AlbumsScreen albumsScreen;
	private static final long serialVersionUID = 1L;
	public String albumN;
	private String numPhotos;
	private String dates;
	protected ImagePanel cover;
	protected ImageButton deleteBtn;
	protected JTextField albumName;
	protected JLabel numberPhotos;
	protected JLabel rangeDates;
	JPanel deletePane;
	protected BoxLayout bl = new BoxLayout(this,BoxLayout.Y_AXIS);
	protected GridBagConstraints c = new GridBagConstraints();
	private final Dimension albumInfoSize = new Dimension(160,178);
	protected final String thumbnailsPath = "thumbnails/";
	protected final String picturesPath = "pictures/";
	private boolean mEntered = false;
	private boolean mClicked = false;
	private boolean btnVisible = false;
	protected OptionsListener ol1;
	protected PhotosScreen photoScreen;
	protected Album album;
	ControlInterface control;
	
	/**
	 * Constructor for the class AlbumInfo	
	 * @param album
	 * @param control
	 * @param albumsScreen
	 * @throws Exception
	 */
	public AlbumInfo(Album album, ControlInterface control, AlbumsScreen albumsScreen) throws Exception{
		
		ol1 = new OptionsListener();
		this.albumsScreen = albumsScreen;
		this.album = album;
		this.control = control;
	
		setLayout(bl);
		albumN = album.getAlbumName();
		numPhotos = album.numberOfPhotosBelongingTo()+"";
		dates = album.minDate(control.getUser())+" - "+album.maxDate(control.getUser());
		
		albumName = new JTextField(20);
		albumName.setText(albumN);
		albumName.setEditable(false);
		albumName.setBorder(null);
		albumName.setBackground(null);
		albumName.setFont(Themes.componentsFont);		
		albumName.setHorizontalAlignment(JTextField.CENTER);
		albumName.setForeground(Color.WHITE);
		albumName.setOpaque(false);
		albumName.setAlignmentX(Component.CENTER_ALIGNMENT);
		albumName.setActionCommand("Edit name");
		albumName.addActionListener(ol1);
				
		numberPhotos = new JLabel(numPhotos);
		numberPhotos.setOpaque(false);
		numberPhotos.setForeground(Color.WHITE);
		numberPhotos.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		rangeDates = new JLabel(dates);
		rangeDates.setOpaque(false);
		rangeDates.setForeground(Color.WHITE);
		rangeDates.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		String file = getCover(album,control);
		String[] fileInfo = new String[2];
		fileInfo=file.split("\\.");
		String fileName = fileInfo[0];
		String fileExtension = fileInfo[1];
				
		transform(picturesPath+fileName+"."+fileExtension,thumbnailsPath+fileName+"thumb."+fileExtension,140,120,10);
		
		cover = new ImagePanel(new ImageIcon(thumbnailsPath+fileName+"thumb."+fileExtension).getImage());
		cover.setAlignmentY(Component.CENTER_ALIGNMENT);
		cover.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		deleteBtn = new ImageButton("resources/deleteBtnUP.png");
		deleteBtn.setPreferredSize(new Dimension(20,20));
		deleteBtn.addActionListener(ol1);
		deleteBtn.setPressedIcon(new ImageIcon("resources/deleteBtnDN.png"));
		deleteBtn.setOpaque(false);
		deleteBtn.setContentAreaFilled(false);
		deleteBtn.setBorderPainted(false);
		deleteBtn.setActionCommand("deleteAlbum");
				
		deletePane = new JPanel();
		deletePane.setOpaque(false);
		deletePane.setLayout(new BoxLayout(deletePane, BoxLayout.LINE_AXIS));
		deletePane.add(Box.createHorizontalGlue());
		deletePane.add(deleteBtn);
		deletePane.setVisible(false);
			
		add(deletePane);
		add(cover);
		add(albumName);
		add(numberPhotos);
		add(rangeDates);
		
		this.setPreferredSize(albumInfoSize);
		this.setAlignmentY(Component.CENTER_ALIGNMENT);
		this.setOpaque(false);
		this.setBorder(new EmptyBorder(10, 10, 10, 10));		
		
		addMouseListener(this);
		
	}
	
	/**
	 * Returns the name of the album 
	 * @return String
	 */
	public String getAlbumName(){
		return this.albumN;
	}
	
	/**
	 * Allows editing the name of the album for renaming. 
	 */
	public void allowNameEditing(){
		if(btnVisible){
			this.albumName.setForeground(Color.BLACK);
			this.albumName.setOpaque(true);
			this.albumName.setEditable(true);
		}
		else{
			this.albumName.setForeground(Color.WHITE);
			this.albumName.setOpaque(false);
			this.albumName.setEditable(false);
		}
	}
	
	/**
	 * Shows delete button when an album is going to be deleted in edit view of Albums Screen.
	 */
	public void showHideDeleteBtn(){
		if(!btnVisible){
			deletePane.setVisible(true);
			this.repaint();
			btnVisible=true;
		}
		else{
			deletePane.setVisible(false);
			this.repaint();
			btnVisible=false;
		}
	}
	
	@Override
	public void paintComponent(Graphics g)
    {
		super.paintComponent(g);
       
		if(this.mEntered){
        	super.paintComponent(g);
	        Color ppColor = new Color(147, 159, 166, 70); //r,g,b,alpha
	        g.setColor(ppColor);
	        g.fillRect(0,0,160,178); //x,y,width,height
        }
		else if(this.mClicked){
        	super.paintComponent(g);
	        Color ppColor = new Color(44, 90, 115, 70); //r,g,b,alpha
	        g.setColor(ppColor);
	        g.fillRect(0,0,160,178); //x,y,width,height
		}
    }    
	
	/**
	 * Gets the first picture of an album to create the thumbnail 
	 * @param album
	 * @param control
	 * @return
	 */
	private String getCover(Album album, ControlInterface control){
		
		Hashtable<String,Photo> picName = album.getPhotosInAlbum(control.getUser());
		Enumeration<String> keys = picName.keys();
		String value = null;
		
		while(keys.hasMoreElements()){
			Object key = keys.nextElement();
			value = picName.get(key).getFilename();
			break;
		}
		
		if(value!=null)
			return value;
		else
			value = "defaultCover.jpg";
		
		return value;
	}
	
	/*
	 *
	 * Part of the InfoGlue Content Management Platform (www.infoglue.org)
	 *
	 * 
	 *
	 *  Copyright (C)
	 * 
	 * This program is free software; you can redistribute it and/or modify it under
	 * the terms of the GNU General Public License version 2, as published by the
	 * Free Software Foundation. See the file LICENSE.html for more information.
	 * 
	 * This program is distributed in the hope that it will be useful, but WITHOUT
	 * ANY WARRANTY, including the implied warranty of MERCHANTABILITY or FITNESS
	 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
	 * 
	 * You should have received a copy of the GNU General Public License along with
	 * this program; if not, write to the Free Software Foundation, Inc. / 59 Temple
	 * Place, Suite 330 / Boston, MA 02111-1307 / USA.
	 *
	 * 
	 */
	
	/**
	 * This method takes a picture and transforms it to any size needed. 
	 */
	  public void transform(String originalFile, String thumbnailFile, int thumbWidth, int thumbHeight, int quality) throws Exception 
	  {
	    Image image = javax.imageio.ImageIO.read(new File(originalFile));
	      
	      double thumbRatio = (double)thumbWidth / (double)thumbHeight;
	      int imageWidth    = image.getWidth(null);
	      int imageHeight   = image.getHeight(null);
	      double imageRatio = (double)imageWidth / (double)imageHeight;
	      if (thumbRatio < imageRatio) 
	      {
	        thumbHeight = (int)(thumbWidth / imageRatio);
	      } 
	      else 
	      {
	          thumbWidth = (int)(thumbHeight * imageRatio);
	      }
	      
	    if(imageWidth < thumbWidth && imageHeight < thumbHeight)
	    {
	      thumbWidth = imageWidth;
	      thumbHeight = imageHeight;
	    }
	    else if(imageWidth < thumbWidth)
	      thumbWidth = imageWidth;
	    else if(imageHeight < thumbHeight)
	      thumbHeight = imageHeight;

	      BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
	      Graphics2D graphics2D = thumbImage.createGraphics();
	      graphics2D.setBackground(Color.WHITE);
	      graphics2D.setPaint(Color.WHITE); 
	      graphics2D.fillRect(0, 0, thumbWidth, thumbHeight);
	      graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	      graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);
	      
	    javax.imageio.ImageIO.write(thumbImage, "JPG", new File(thumbnailFile));
	  }

	  /**
	   * Returns to the Album's screen 
	   */
	public void showAlbumsScreen(){
		this.albumsScreen.setVisible(true);
	}  
	  
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		if(albumsScreen.editing){
			albumsScreen.editing = false;
			albumsScreen.edit.setIcon(new ImageIcon("resources/editUP.jpg"));		
			albumsScreen.edit.setPressedIcon(new ImageIcon("resources/editDN.jpg"));
			for(AlbumInfo panel:albumsScreen.albumpanels){
				panel.changeName();
				panel.showHideDeleteBtn();
				panel.allowNameEditing();
			}	
		}
		try {
			photoScreen = new PhotosScreen(this.album,this.albumsScreen.control,this.albumsScreen.backend,this,this.albumsScreen);
			photoScreen.setVisible(true);
			photoScreen.setLocationRelativeTo(null);
			photoScreen.setResizable(false);
			this.albumsScreen.setVisible(false);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		mEntered = true;
		mClicked = false;
		this.paintComponent(super.getGraphics());
		this.repaint();
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		mEntered = false;
		mClicked = false;
		this.paintComponent(super.getGraphics());		
		this.repaint();
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {		
		mEntered = false;
		mClicked = true;
		this.paintComponent(super.getGraphics());
		this.repaint();
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		mEntered = true;
		mClicked = false;
		this.paintComponent(super.getGraphics());
		this.repaint();
		
	}
	
	/**
	 * Method to rename an album
	 */
	public void changeName(){
		String TFAlbum = AlbumInfo.this.albumName.getText();
			if(!AlbumInfo.this.albumN.equals(TFAlbum)){
				User user = AlbumInfo.this.control.getUser();
				Hashtable<String,Album> userAlbums= user.getAlbums();
				String newAlbumName = AlbumInfo.this.albumName.getText();
				if(userAlbums.get(newAlbumName)==null){
					//getOld album and the copy that album to a new one and add it to a user	
					Album oldAlbum = AlbumInfo.this.album;
					String oldAlbumName = oldAlbum.getAlbumName();
					Album newAlbum = oldAlbum;				
					newAlbum.setAlbumName(newAlbumName);					
					
					userAlbums.remove(oldAlbumName);
					userAlbums.put(newAlbumName, newAlbum);
					//Change text fields and attributes.
					
					AlbumInfo.this.album.setAlbumName(newAlbumName);	
					AlbumInfo.this.albumN = newAlbumName;
					AlbumInfo.this.albumName.setForeground(Color.WHITE);
					AlbumInfo.this.albumName.setOpaque(false);
					AlbumInfo.this.albumName.setEditable(false);
				}
				else{
					AlbumInfo.this.albumName.setText(albumN);
					final JFrame error = new JFrame("Important Message!");
		        	JOptionPane.showMessageDialog(error, "There's an album with the same name!");
		        	
				}
			}
		
	}
	
	/**
	 * Listener when an album is going to be deleted.	 * 
	 */
	protected class OptionsListener implements ActionListener {
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			String action = e.getActionCommand();
			if(action.equals("deleteAlbum")){				
							
					try {
						albumsScreen.ol.actionPerformed(e, AlbumInfo.this);						
						AlbumInfo.this.albumsScreen.edit.setIcon(new ImageIcon("resources/editUP.jpg"));		
						AlbumInfo.this.albumsScreen.edit.setPressedIcon(new ImageIcon("resources/editDN.jpg"));
						AlbumInfo.this.albumsScreen.editing=false;
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
			}
			if(action.equals("Edit name")){
				AlbumInfo.this.changeName();				
			}
		
		}
	}

	
	  
}

