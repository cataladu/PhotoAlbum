package cs213.photoAlbum.simpleView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import cs213.photoAlbum.control.*;
import cs213.photoAlbum.model.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;

/**
 * This class is the Albums Screen. It the first screen the user is going to see once logged in. It displays all albums, allows searching
 * and editing of information as well as creation of new albums. 
 * @author Catalina Laverde Duarte *
 */
public class AlbumsScreen extends JFrame implements MouseListener {
	
	private static final long serialVersionUID = 1L;

	protected JLabel welcome;
	protected JLabel albumsLbl;
	protected JTextField searchT;
	
	protected ImageButton edit;
	protected ImageButton search;
	protected ImageButton advancedSearch;
	protected ImageButton logout;
	protected ImageButton addAlbum;
	protected ImageButton back;
	
	protected JScrollPane scrollAlbums;
	protected JPanel albumContainer;
	protected ImagePanel bg;
	protected JPanel mainPanel; //This panel will contain everything else
	protected AlbumCreationPopUp creationScreen;
	protected ASearchPopUp aSearchPopUp;
	
	AlbumInfo albumPanel;
	ArrayList<AlbumInfo> albumpanels;
	ArrayList<AlbumInfo> albumpanelsSearchResult;
	protected ControlInterface control;
	
	protected JFrame error = new JFrame("Message"); //Frame for errors
		
	protected GridBagLayout gl = new GridBagLayout();
	protected GridBagConstraints c = new GridBagConstraints();
	
	protected OptionsListener ol;
	
	protected BackendInterface backend;
	private loginScreen loginScreen; 
	
	boolean editing = false;
	
	/**
	 * Constructor for the class AlbumsScreen
	 * @param user
	 * @param control
	 * @param backend
	 * @param loginScreen
	 * @throws Exception
	 */
	public AlbumsScreen(String user, ControlInterface control, BackendInterface backend, loginScreen loginScreen) throws Exception{
		
		this.control = control;
		this.backend = backend;
		this.loginScreen = loginScreen;
		this.albumpanelsSearchResult = new ArrayList<AlbumInfo>();
		
		ol = new OptionsListener();
		
		albumpanels = new ArrayList<AlbumInfo>();
		this.setBackground(Color.BLACK);
		this.setSize(800,608);
		this.setResizable(false);
				
		welcome = new JLabel("Welcome "+user);
		welcome.setFont(Themes.titleFont);
		welcome.setForeground( Themes.themeColor);
		
		searchT = new JTextField(20);
		searchT.setUI(new HintTextFieldUI("Search Albums", true));
		searchT.setEditable(true);
		
		albumsLbl = new JLabel("Albums");
		albumsLbl.setFont(Themes.titleFont);
		albumsLbl.setForeground(Themes.themeColor);
		
		edit = new ImageButton("resources/editUP.jpg");
		edit.setActionCommand("Edit");
		edit.setPreferredSize(new Dimension(100,30));
		edit.setPressedIcon(new ImageIcon("resources/editDN.jpg"));
		edit.addActionListener(ol);
		
		search = new ImageButton("resources/searchUP.jpg");
		search.setActionCommand("Search");
		search.setPressedIcon(new ImageIcon("resources/searchDN.jpg"));
		search.setPreferredSize(new Dimension(100,30));
		search.addActionListener(ol);
		
		advancedSearch = new ImageButton("resources/advancedSearchUP.jpg");
		advancedSearch.setActionCommand("Advanced Search");
		advancedSearch.setPressedIcon(new ImageIcon("resources/advancedSearchDN.jpg"));
		advancedSearch.setPreferredSize(new Dimension(160,30));
		advancedSearch.addActionListener(ol);
		advancedSearch.setActionCommand("Advanced Search");
		
		back = new ImageButton("resources/backUP.jpg");
		back.setActionCommand("back");		
		back.setPreferredSize(new Dimension(100,30));
		back.setPressedIcon(new ImageIcon("resources/backDN.jpg"));
		back.addActionListener(ol);
		back.setVisible(false);
		
		
		logout = new ImageButton("resources/logoutUP.jpg");
		logout.setActionCommand("Log out");
		logout.setPreferredSize(new Dimension(100,30));
		logout.setPressedIcon(new ImageIcon("resources/logoutDN.jpg"));
		logout.addMouseListener(this);
				
		
		
		albumContainer = new JPanel();
		albumContainer.setBackground(Color.black);
		albumContainer.setLayout(new WrapLayout(FlowLayout.LEFT));
		albumContainer.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);	
		
		scrollAlbums = new JScrollPane(albumContainer);
		scrollAlbums.getViewport().add(albumContainer);  
		scrollAlbums.setOpaque(false);
		scrollAlbums.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollAlbums.setMinimumSize(new Dimension(700,380));
		scrollAlbums.setPreferredSize( new Dimension(700,380));
		scrollAlbums.setBorder(new javax.swing.border.MatteBorder(new ImageIcon("resources/border.jpg")));
		
		mainPanel = new JPanel( new GridBagLayout() );
		mainPanel.setBackground(Color.BLACK);
				
		this.getContentPane().add(mainPanel, BorderLayout.NORTH);	
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(20,30,5,5);
		mainPanel.add(welcome,c);
		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(20,50,0,5);
		mainPanel.add(searchT, c);
		c.gridx = 2;
		c.gridy = 0;
		c.insets = new Insets(20,0,0,0);
		mainPanel.add(search, c);
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(20,30,8,5);
		mainPanel.add(albumsLbl,c);
		c.gridx = 2;
		c.gridy = 1;
		c.insets = new Insets(10,0,20,0);
		mainPanel.add(advancedSearch,c);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		mainPanel.add(scrollAlbums,c);
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		mainPanel.add(back,c);
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 2;
		mainPanel.add(logout,c);
		c.gridx = 2;
		c.gridy = 3;
		mainPanel.add(edit,c);
				
		mainPanel.requestFocusInWindow(); 
		drawAlbumThumbnails(control);
				
		//Add listener to save the state of the user
		addWindowListener(new WindowAdapter(){
			
			public void windowClosing(WindowEvent we){
				AlbumsScreen.this.saveState();
				System.exit(0);
			 }
		});
		
		addWindowFocusListener(new WindowAdapter() {
		    public void windowGainedFocus(WindowEvent e) {
		    	mainPanel.requestFocusInWindow(); 
		    }
		});
		
		
	}
	/**
	 * Method that draws all available album thumb nails;
	 * @param control
	 * @throws Exception
	 */
	public void drawAlbumThumbnails(ControlInterface control) throws Exception{
		
		Hashtable<String,Album> albums = new Hashtable<String,Album>();
		albums = control.listAlbums();
		
		if(albums.size()>0){
			
		this.albumpanels.clear();
			
			for(Album album: albums.values()){	
				albumPanel = new AlbumInfo(album,control,this);
				this.albumpanels.add(albumPanel);
				albumContainer.add(albumPanel);				
			}
					
		}
		
		addAlbum = new ImageButton("resources/NewAlbumUP.png");
		addAlbum.setActionCommand("Add album");
		addAlbum.setPreferredSize(new Dimension(160,178));
		addAlbum.setPressedIcon(new ImageIcon("resources/NewAlbumDN.png"));
		addAlbum.addActionListener(ol);
		addAlbum.setOpaque(false);
		addAlbum.setContentAreaFilled(false);
		addAlbum.setBorderPainted(false);		
		albumContainer.add(addAlbum);
		
		
	}
	/**
	 * Draws album container with search result thumb nails
	 */
	public void drawSearchResults(){
		if(this.albumpanelsSearchResult.size()>0){
			albumContainer.removeAll();
			albumContainer.repaint();
			albumContainer.revalidate();
			for(AlbumInfo album: this.albumpanelsSearchResult){	//for each album thumb in results
				albumContainer.add(album);		
			}			
			scrollAlbums.repaint();
			scrollAlbums.revalidate();
			mainPanel.repaint();
			mainPanel.revalidate();
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			this.albumpanelsSearchResult.clear();
		}
		else{ //no results
			final JFrame error = new JFrame("Important Message!");
			JOptionPane.showMessageDialog(error, "No Results for given input.");
		}		
	}
	/**
	 * Method that creates an album when the add album button is clicked
	 * @param control
	 * @param albumName
	 * @throws Exception 
	 */
	public void addAlbum(ControlInterface control , String albumName) throws Exception{
		boolean exist = control.createAlbum(albumName);
		creationScreen.setVisible(false);
		if(!exist){
        	final JFrame error = new JFrame("Important Message!");
        	JOptionPane.showMessageDialog(error, "Album already exists!");
		}
		
		else{
			reDrawAlbums(control);
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		
	}
	/**
	 * Method that deletes an album when user clicks the 'x' button
	 * @throws Exception 
	 */
	
	public void deleteAlbum(ControlInterface control,AlbumInfo album) throws Exception{
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		ArrayList<AlbumInfo> copy = new ArrayList<AlbumInfo>();
		deleteAlbumPhotos(album);
		control.deleteAlbum(album.getAlbumName());
		
		if(albumpanels.size()>0){
			for(AlbumInfo panel: this.albumpanels){
				if(!panel.getAlbumName().equals(album.getAlbumName())){
					copy.add(panel);
				}
			}
			this.albumpanels.clear();
			for(AlbumInfo panel: copy){				
					this.albumpanels.add(panel);				
			}
			
			reDrawAlbums(control);
		}
	}
	/**
	 * Method that deletes all photos in an album. Once an album is deleted all files related to it must be deleted as well.
	 * @param albumthumb
	 */
	public void deleteAlbumPhotos(AlbumInfo albumthumb){
		
		String PathResources = "pictures";
		String PathThumnails = "thumbnails";
		Hashtable<String,Album> albums = new Hashtable<String,Album>();
		albums = control.listAlbums();
		
		Album album = albums.get(albumthumb.getAlbumName());
		
		for(Photo photo:album.getPhotosInAlbum(control.getUser()).values()){
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
	 * Re Draws all albums after one of them is deleted.
	 * @param control
	 * @throws Exception
	 */
	public void reDrawAlbums(ControlInterface control) throws Exception{
		albumContainer.removeAll();
		drawAlbumThumbnails(control);
		albumContainer.repaint();
		albumContainer.revalidate();
		scrollAlbums.repaint();
		scrollAlbums.revalidate();
		mainPanel.repaint();
		mainPanel.revalidate();
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
	/**
	 * Method called when log out btn is clicked.
	 */
	public void mouseClicked(MouseEvent arg0) {
		saveState();
		this.setVisible(false);
		this.loginScreen.setVisible(true);
		this.loginScreen.toFront();
		this.loginScreen.repaint();
	}
	
	/**
	 * Saves the state of the program through Backend.
	 */
	public void saveState(){
		try {
			this.backend.writeUser(this.backend);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Listener for all buttons in AlbumsScreen. Editing albums, Adding a new album, Deleting an album, performing a regular and an advanced search
	 * and logging out. 
	 */
	protected class OptionsListener implements ActionListener {
				
		public void actionPerformed(ActionEvent e,AlbumInfo album) throws Exception{
			String action = e.getActionCommand();
			if(action.equals("deleteAlbum")){
				System.out.print(album.albumN);
				deleteAlbum(AlbumsScreen.this.control,album);
			}
		}
		
		public void actionPerformed(ActionEvent e){
			String action = e.getActionCommand();
			
			if(action.equals("Edit")){
				
				if(!editing){
					editing = true;
					edit.setIcon(new ImageIcon("resources/saveUP.jpg"));		
					edit.setPressedIcon(new ImageIcon("resources/saveDN.jpg"));
					for(AlbumInfo panel:AlbumsScreen.this.albumpanels){
						panel.showHideDeleteBtn();
						panel.allowNameEditing();
					}
				}
				else{
					editing = false;
					edit.setIcon(new ImageIcon("resources/editUP.jpg"));		
					edit.setPressedIcon(new ImageIcon("resources/editDN.jpg"));
					for(AlbumInfo panel:AlbumsScreen.this.albumpanels){
						panel.changeName();
						panel.showHideDeleteBtn();
						panel.allowNameEditing();
					}	
				}
			}
			
			else if(action.equals("Search")){
				if(editing){
					editing = false;
					edit.setIcon(new ImageIcon("resources/editUP.jpg"));		
					edit.setPressedIcon(new ImageIcon("resources/editDN.jpg"));
					for(AlbumInfo panel:AlbumsScreen.this.albumpanels){
						panel.changeName();
						panel.showHideDeleteBtn();
						panel.allowNameEditing();
					}	
				}
				
				AlbumsScreen.this.albumpanelsSearchResult.clear();
				if(!searchT.getText().equals("")){
					for(AlbumInfo album: AlbumsScreen.this.albumpanels){
						if(album.albumN.toLowerCase().contains(searchT.getText().toLowerCase())){
							AlbumsScreen.this.albumpanelsSearchResult.add(album);
						}
					}
					AlbumsScreen.this.back.setVisible(true);
					drawSearchResults();
				}
				else{
					try {
						reDrawAlbums(AlbumsScreen.this.control);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			
			else if(action.equals("Advanced Search")){
				if(editing){
					editing = false;
					edit.setIcon(new ImageIcon("resources/editUP.jpg"));		
					edit.setPressedIcon(new ImageIcon("resources/editDN.jpg"));
					for(AlbumInfo panel:AlbumsScreen.this.albumpanels){
						panel.changeName();
						panel.showHideDeleteBtn();
						panel.allowNameEditing();
					}	
				}
				AlbumsScreen.this.aSearchPopUp = new ASearchPopUp(AlbumsScreen.this.control, AlbumsScreen.this);
				AlbumsScreen.this.aSearchPopUp.setVisible(true);
				
			}
			
			else if(action.equals("Add album")){
				if(editing){
					editing = false;
					edit.setIcon(new ImageIcon("resources/editUP.jpg"));		
					edit.setPressedIcon(new ImageIcon("resources/editDN.jpg"));
					for(AlbumInfo panel:AlbumsScreen.this.albumpanels){
						panel.changeName();
						panel.showHideDeleteBtn();
						panel.allowNameEditing();
					}	
				}
				AlbumsScreen.this.creationScreen = new AlbumCreationPopUp(AlbumsScreen.this,AlbumsScreen.this.control);
				AlbumsScreen.this.creationScreen.setVisible(true);
				
			}
			else if(action.equals("back")){
				if(editing){
					editing = false;
					edit.setIcon(new ImageIcon("resources/editUP.jpg"));		
					edit.setPressedIcon(new ImageIcon("resources/editDN.jpg"));
					for(AlbumInfo panel:AlbumsScreen.this.albumpanels){
						panel.changeName();
						panel.showHideDeleteBtn();
						panel.allowNameEditing();
					}	
				}
				try {
					reDrawAlbums(AlbumsScreen.this.control);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				AlbumsScreen.this.back.setVisible(false);
			}
				
	}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {	}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
	public class HintTextFieldUI extends BasicTextFieldUI implements FocusListener {

	    private String hint;
	    private boolean hideOnFocus;
	    private Color color;

	    public Color getColor() {
	        return color;
	    }

	    public void setColor(Color color) {
	        this.color = color;
	        repaint();
	    }

	    private void repaint() {
	        if(getComponent() != null) {
	            getComponent().repaint();           
	        }
	    }

	    public boolean isHideOnFocus() {
	        return hideOnFocus;
	    }

	    public void setHideOnFocus(boolean hideOnFocus) {
	        this.hideOnFocus = hideOnFocus;
	        repaint();
	    }

	    public String getHint() {
	        return hint;
	    }

	    public void setHint(String hint) {
	        this.hint = hint;
	        repaint();
	    }
	    public HintTextFieldUI(String hint) {
	        this(hint,false);
	    }

	    public HintTextFieldUI(String hint, boolean hideOnFocus) {
	        this(hint,hideOnFocus, null);
	    }

	    public HintTextFieldUI(String hint, boolean hideOnFocus, Color color) {
	        this.hint = hint;
	        this.hideOnFocus = hideOnFocus;
	        this.color = color;
	    }

	    @Override
	    protected void paintSafely(Graphics g) {
	        super.paintSafely(g);
	        JTextComponent comp = getComponent();
	        if(hint!=null && comp.getText().length() == 0 && (!(hideOnFocus && comp.hasFocus()))){
	            if(color != null) {
	                g.setColor(color);
	            } else {
	                g.setColor(comp.getForeground().brighter().brighter().brighter());              
	            }
	            int padding = (comp.getHeight() - comp.getFont().getSize())/2;
	            g.drawString(hint, 2, comp.getHeight()-padding-1);          
	        }
	    }

	    @Override
	    public void focusGained(FocusEvent e) {
	        if(hideOnFocus) repaint();

	    }

	    @Override
	    public void focusLost(FocusEvent e) {
	        if(hideOnFocus) repaint();
	    }
	    @Override
	    protected void installListeners() {
	        super.installListeners();
	        getComponent().addFocusListener(this);
	    }
	    @Override
	    protected void uninstallListeners() {
	        super.uninstallListeners();
	        getComponent().removeFocusListener(this);
	    }
	}
	
		
	
}
