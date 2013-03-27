package cs213.photoAlbum.simpleView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.*;

import cs213.photoAlbum.control.ControlInterface;
import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.BackendInterface;

/**
 * This class represents the screen the user is going to see when an album is opened. It displays all photos in the album, allows editing, searching, creation of
 * new photos and slideShow.
 * @author Roberto Ronderos Botero 
 */
public class PhotosScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private ControlInterface control;
	private BackendInterface backend;
	private AlbumInfo albumInfo;
	
	public File currentDirectory;
	
	protected JFrame error = new JFrame("Message"); //Frame for errors
	
	protected JLabel nameAlbumLbl;
	
	protected ImageButton slideShowBtn;
	protected ImageButton editBtn;
	protected ImageButton backBtn;
	protected ImageButton addPhoto;
	protected ImageButton makeAlbumBtn;
	
	protected JScrollPane scrollPhotos;
	protected JPanel mainPanel;
	protected JPanel photosContainer;
			
	protected GridBagLayout gl = new GridBagLayout();
	protected GridBagConstraints c = new GridBagConstraints();
	protected OptionsListener ol;
	
	protected PhotoInfo photoInfo;
	protected ArrayList<PhotoInfo> photopanels;
	protected ArrayList<PhotoInfo> copy;
	protected ArrayList<Photo> photosSearchResult;
	
	Album album;
	newPhotoPopUp popup;
	AlbumsScreen albumsScreen;
	SlideShow slideShow;
	makeAlbumPopUp makeAlbumPopUp;
	private boolean search=false;;
	
	/**
	 * Constructor for the class PhotosScreen
	 * @param album
	 * @param control
	 * @param backend
	 * @param albumInfo
	 * @param albumsScreen
	 * @throws Exception
	 */
	public PhotosScreen(Album album, ControlInterface control, BackendInterface backend, AlbumInfo albumInfo, AlbumsScreen albumsScreen) throws Exception {
		
		this.albumsScreen = albumsScreen;
		this.album = album;
		this.control = control;
		this.backend = backend;
		this.albumInfo = albumInfo;
		
		ol = new OptionsListener();
		
		super.setBackground(Color.BLACK);
		this.getRootPane().setBackground(Color.BLACK);
		this.getLayeredPane().setBackground(Color.BLACK);
		this.getContentPane().setBackground(Color.BLACK);
		this.setBackground(Color.BLACK);
		this.setSize(800,608);
		this.setResizable(false);
		
		nameAlbumLbl = new JLabel(album.getAlbumName());
		nameAlbumLbl.setFont(Themes.titleFont);
		nameAlbumLbl.setForeground(Themes.themeColor);
		
		slideShowBtn = new ImageButton("resources/slideShowUP.jpg");
		slideShowBtn.setPressedIcon(new ImageIcon("resources/slideShowDN.jpg"));
		slideShowBtn.setActionCommand("Slide Show");
		slideShowBtn.setPreferredSize(new Dimension(100,30));
		slideShowBtn.addActionListener(ol);
		
		editBtn = new ImageButton("resources/editUP.jpg");
		editBtn.setPressedIcon(new ImageIcon("resources/editDN.jpg"));
		editBtn.setActionCommand("Edit");
		editBtn.setPreferredSize(new Dimension(100,30));
		editBtn.addActionListener(ol);
		
		backBtn = new ImageButton("resources/backUP.jpg");
		backBtn.setPressedIcon(new ImageIcon("resources/backDN.jpg"));
		backBtn.setActionCommand("Back");
		backBtn.setPreferredSize(new Dimension(100,30));
		backBtn.addActionListener(ol);
		
		photopanels = new ArrayList<PhotoInfo>();
		
		photosContainer = new JPanel();
		photosContainer.setLayout(new WrapLayout(FlowLayout.LEFT));
		photosContainer.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		photosContainer.setBackground(Color.BLACK);
		
		scrollPhotos = new JScrollPane(photosContainer);
		scrollPhotos.getViewport().add(photosContainer);  
		scrollPhotos.setBackground(Color.BLACK);  
		scrollPhotos.setMinimumSize(new Dimension(700,380));
		scrollPhotos.setPreferredSize( new Dimension(700,380));
		scrollPhotos.setBorder(new javax.swing.border.MatteBorder(new ImageIcon("resources/border.jpg")));
		mainPanel = new JPanel( new GridBagLayout() );
		mainPanel.setBackground(Color.BLACK);
		
		this.getContentPane().add(mainPanel,BorderLayout.NORTH);
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(20,30,5,50);
		mainPanel.add(nameAlbumLbl,c);
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(20,220,5,0);
		mainPanel.add(slideShowBtn, c);
		c.gridx = 2;
		c.gridy = 1;
		c.insets = new Insets(20,0,5,0);
		mainPanel.add(editBtn, c);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		mainPanel.add(scrollPhotos,c);
		c.gridx = 2;
		c.gridy = 3;
		mainPanel.add(backBtn,c);
		
		drawPhotoThumbnails();
		
		//Add listener to save the state of the user
		addWindowListener(new WindowAdapter(){
			
			public void windowClosing(WindowEvent we){
				PhotosScreen.this.saveState();
				System.exit(0);
			 }
		});
		
	}
	
	
	/**
	 * Constructor for photos advanced search result
	 * @param searchResult
	 * @throws Exception 
	 */
	public PhotosScreen(ArrayList<Photo> searchResult, ControlInterface control, AlbumsScreen albumsScreen) throws Exception {
		
		this.search= true;
		this.control = control;
		this.photosSearchResult = searchResult;
		this.albumsScreen = albumsScreen;
		
		ol = new OptionsListener();
		
		super.setBackground(Color.BLACK);
		this.getRootPane().setBackground(Color.BLACK);
		this.getLayeredPane().setBackground(Color.BLACK);
		this.getContentPane().setBackground(Color.BLACK);
		this.setBackground(Color.BLACK);
		this.setSize(800,608);
		this.setResizable(false);
		
		nameAlbumLbl = new JLabel("Search Results");
		nameAlbumLbl.setFont(Themes.titleFont);
		nameAlbumLbl.setForeground(Themes.themeColor);
		
		slideShowBtn = new ImageButton("resources/slideShowUP.jpg");
		slideShowBtn.setPressedIcon(new ImageIcon("resources/slideShowDN.jpg"));
		slideShowBtn.setActionCommand("Slide Show");
		slideShowBtn.setPreferredSize(new Dimension(100,30));
		slideShowBtn.addActionListener(ol);
		
		makeAlbumBtn = new ImageButton("resources/makeAlbumUP.jpg");
		makeAlbumBtn.setPressedIcon(new ImageIcon("resources/makeAlbumDN.jpg"));
		makeAlbumBtn.setActionCommand("make album");
		makeAlbumBtn.setPreferredSize(new Dimension(110,30));
		makeAlbumBtn.addActionListener(ol);
		
		backBtn = new ImageButton("resources/backUP.jpg");
		backBtn.setPressedIcon(new ImageIcon("resources/backDN.jpg"));
		backBtn.setActionCommand("Back");
		backBtn.setPreferredSize(new Dimension(100,30));
		backBtn.addActionListener(ol);
		
		photopanels = new ArrayList<PhotoInfo>();
		
		photosContainer = new JPanel();
		photosContainer.setBackground(Color.black);
		photosContainer.setLayout(new WrapLayout(FlowLayout.LEFT));
		photosContainer.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);	
		
		scrollPhotos = new JScrollPane(photosContainer);
		scrollPhotos.setOpaque(false);
		scrollPhotos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPhotos.getViewport().setView(photosContainer);  
		scrollPhotos.setMinimumSize(new Dimension(700,380));
		scrollPhotos.setPreferredSize( new Dimension(700,380));
		scrollPhotos.setBorder(new javax.swing.border.MatteBorder(new ImageIcon("resources/border.jpg")));
		mainPanel = new JPanel( new GridBagLayout() );
		mainPanel.setBackground(Color.BLACK);
		
		this.getContentPane().add(mainPanel,BorderLayout.NORTH);
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(20,30,5,50);
		mainPanel.add(nameAlbumLbl,c);
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(20,220,5,0);
		mainPanel.add(slideShowBtn, c);
		c.gridx = 2;
		c.gridy = 1;
		c.insets = new Insets(20,0,5,0);
		mainPanel.add(makeAlbumBtn, c);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		mainPanel.add(scrollPhotos,c);
		c.gridx = 2;
		c.gridy = 3;
		mainPanel.add(backBtn,c);
		
		drawPhotoThumbnailsSearchResults();
		
		//set frame location
		this.setLocationRelativeTo(null);
		
		//Add listener to save the state of the user
		addWindowListener(new WindowAdapter(){
			
			public void windowClosing(WindowEvent we){
				System.exit(0);
			 }
		});
	}

	/**
	 * Saves the state of the program
	 */
	public void saveState(){
		try {
			this.backend.writeUser(this.backend);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Draws the thumbnails after a search is performed.
	 * @throws Exception
	 */
	private void drawPhotoThumbnailsSearchResults() throws Exception {
		
		
		if(this.photosSearchResult.size()>0){
			
			for(Photo photo: photosSearchResult){
				photoInfo = new PhotoInfo(photo,control,this);
				this.photopanels.add(photoInfo);
				photosContainer.add(photoInfo);			
			}
		
		
		}
		
	}
	
	/**
	 * Draws regular thumbnails of the photos in the album.
	 * @throws Exception
	 */
	public void drawPhotoThumbnails() throws Exception{
		
		Hashtable<String,Photo> photos = new Hashtable<String,Photo>();
		photos = album.getPhotosInAlbum(control.getUser());
		
		if(photos.size()>0){
			
			this.photopanels.clear();
			
			for(Photo photo: photos.values()){
				photoInfo = new PhotoInfo(this.album,photo,control,this);
				this.photopanels.add(photoInfo);
				photosContainer.add(photoInfo);			
			}
		
		
		}
		
		addPhoto = new ImageButton("resources/NewAlbumUP.png");
		addPhoto.setActionCommand("Add photo");
		addPhoto.setPreferredSize(new Dimension(160,178));
		addPhoto.setPressedIcon(new ImageIcon("resources/NewAlbumDN.png"));
		addPhoto.addActionListener(ol);
		addPhoto.setOpaque(false);
		addPhoto.setContentAreaFilled(false);
		addPhoto.setBorderPainted(false);
		photosContainer.add(addPhoto);
		
	}
	
	public void reDrawPhotos() throws Exception{
		photosContainer.removeAll();
		drawPhotoThumbnails();
		photosContainer.repaint();
		photosContainer.revalidate();
		scrollPhotos.repaint();
		scrollPhotos.revalidate();
		mainPanel.repaint();
		mainPanel.revalidate();
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));		
	}
	
	/**
	 * Method to add a new picture to the album
	 * @param filename
	 * @param caption
	 * @throws Exception
	 */
	public void addPhoto(String filename, String caption) throws Exception{
		//add Photo to user in control		
		album.addPhoto(filename, caption, this.control.getUser());
		//add photo to photos container
		Photo photo = album.getPhotoInAlbum(filename, this.control.getUser());
		PhotoInfo photoThumb = new PhotoInfo(this.album,photo,this.control,this);
		this.photopanels.add(photoThumb);		
	}
	
	/**
	 * Listener for all buttons in PhotosScreen
	 * @author Roberto Ronderos Botero
	 */
	protected class OptionsListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			String action = e.getActionCommand();
			if(action.equals("Add photo")){
				popup = new newPhotoPopUp(PhotosScreen.this.control,PhotosScreen.this,PhotosScreen.this.album);
				popup.setVisible(true);
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			
			if(action.equals("Edit")){
				for(PhotoInfo panel:PhotosScreen.this.photopanels){
					panel.showHideDeleteBtn();
				}
			}
			
			if(action.equals("Back")){
				try {
					albumsScreen.reDrawAlbums(PhotosScreen.this.control);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(albumInfo==null){
					PhotosScreen.this.albumsScreen.setVisible(true);
					PhotosScreen.this.setVisible(false);
				}
				else{
				PhotosScreen.this.albumInfo.showAlbumsScreen();
				PhotosScreen.this.setVisible(false);
				}
			}
			
			if(action.equals("Slide Show")){
				if(!PhotosScreen.this.search){
					PhotosScreen.this.slideShow = new SlideShow(PhotosScreen.this.control,PhotosScreen.this, PhotosScreen.this.photopanels);
					PhotosScreen.this.slideShow.setVisible(true);
					PhotosScreen.this.setVisible(false);
				}
				else{
					PhotosScreen.this.slideShow = new SlideShow(PhotosScreen.this.control,PhotosScreen.this.photosSearchResult,PhotosScreen.this);
					PhotosScreen.this.slideShow.setVisible(true);
					PhotosScreen.this.setVisible(false);
				}
			}
			
			if(action.equals("make album")){
				PhotosScreen.this.makeAlbumPopUp = new makeAlbumPopUp(PhotosScreen.this,PhotosScreen.this.control);
				PhotosScreen.this.makeAlbumPopUp.setVisible(true);
			}

			
		}
	}
	
	/**
	 * Methods to make a new album out of the pictures being displayed
	 * @param albumName
	 * @throws Exception
	 */
	public void makeAlbum(String albumName) throws Exception{
		boolean exist = control.createAlbum(albumName);
		makeAlbumPopUp.setVisible(false);
		if(!exist){
        	final JFrame error = new JFrame("Important Message!");
        	JOptionPane.showMessageDialog(error, "Album already exists!");
		}		
		else{			
			for(Photo photo:PhotosScreen.this.photosSearchResult){
				control.addPhoto(photo.getFilename(), photo.getCaption(), albumName);
			}			
			final JFrame error = new JFrame("Important Message!");
        	JOptionPane.showMessageDialog(error, "Album created!");
			albumsScreen.reDrawAlbums(control);
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}		
		
	}

	/**
	 * Method to delete a picture of an album
	 * @param photoName
	 * @throws Exception
	 */
	public void deletePhoto(String photoName) throws Exception {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		this.copy = new ArrayList<PhotoInfo>();
		deletePhotoFile(photoName);
		this.album.removePhotoInAlbum(photoName);
		
		if(this.photopanels.size()>0){
			for(PhotoInfo thumb: this.photopanels){
				if(!thumb.photoName.equals(photoName)){
					this.copy.add(thumb);
				}
			}
			this.photopanels.clear();
			for(PhotoInfo thumb: this.copy){				
					this.photopanels.add(thumb);				
			}
			
			reDrawPhotos();
		}
		
		
 	}

	/**
	 * Method to delete the actual file of the picture
	 * @param photoName
	 */
	private void deletePhotoFile(String photoName) {
		String PathResources = "pictures";
		String PathThumnails = "thumbnails";
		
		
		
		Photo photo = this.album.getPhotoInAlbum(photoName, this.control.getUser());
		
			int numAlbums = photo.numberOfAlbumsBelongingTo();
			if(numAlbums==1){
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