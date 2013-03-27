package cs213.photoAlbum.simpleView;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import cs213.photoAlbum.control.ControlInterface;
import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.Tag;

/**
 * This class is the screen the user is going to see when a picture is clicked. It displays the picture with all its information and allows editing such as 
 * adding and deleting tags.
 * @author Roberto Ronderos Botero
 *
 */
public class PhotoDisplay extends JFrame {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;
		//GUI Components
		//*********************************
		//JPanels
		protected ImagePanel mainPanel;
		protected JPanel leftPanel;
		protected JPanel rightPanel;
		protected JPanel buttonsPanel;
		protected JPanel photoPanel;
		protected JPanel infoPanel;
		protected JPanel namePanel;
		protected JPanel captionPanel;
		protected JPanel datePanel;
		protected JPanel tagsPanel;
		protected JPanel mainInfo;
		protected JPanel buttons;
		
		//JButtons with images
		protected ImageButton movePhotoBtn;
		protected ImageButton backBtn;
		protected ImageButton editBtn;
		protected ImageButton addTagBtn;
		protected ImageButton removeTagBtn;
		
		//JLabels
		protected JLabel titleLbl;
		protected JLabel nameLbl;
		protected JLabel captionLbl;
		protected JLabel dateLbl;
		protected JLabel tagsLbl;
		
		//Reflected Image 
		protected Reflect photoRf;
		
		//JTextFields
		protected JTextField photoNameTxtF;
		protected JTextField captionTxtF;
		protected JTextField dateTxtF;
		
		//JList
		protected JList tagList;
		
		
		
		//JScrollPane
		protected JScrollPane tagListSP;
		protected DefaultListModel tagsModel = new DefaultListModel();
		//********************************
		
		//Class Attributes
		//********************************
		Photo photo;
		PhotosScreen photosScreen;
		OptionsListener ol;
		ControlInterface control;
		String album;
		String newAlbumName;
		movePhotoPopUp movePopUp;
		addTagsPopUp tagsPopUp;
		
		/**
		 * Constructor for the class PhotoDisplay
		 * @param photo
		 * @param photosScreen
		 * @param control
		 * @param album
		 */
		public PhotoDisplay(Photo photo, PhotosScreen photosScreen, ControlInterface control, String album){
			
			this.album = album;
			this.control = control;
			this.ol = new OptionsListener();
			this.photo = photo;
			this.photosScreen = photosScreen;
			
			mainPanel = new ImagePanel("resources/bgPhoto.jpg");
			mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.X_AXIS));
			mainPanel.setPreferredSize(new Dimension(1024,768));
			
			
			leftPanel = new JPanel();
			leftPanel.setPreferredSize(new Dimension(646,768));
			leftPanel.setOpaque(false);
			leftPanel.add(Box.createHorizontalGlue());
			
			
			photoRf = new Reflect("pictures/"+photo.getFilename());
			photoRf.setPreferredSize(new Dimension(505,768));
			photoRf.setOpaque(false);
			
			leftPanel.add(photoRf);
			
			rightPanel = new JPanel();
			rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
			rightPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
			rightPanel.setPreferredSize(new Dimension(350,768));
			rightPanel.setOpaque(false);
			
			mainInfo = new JPanel();
			mainInfo.setLayout(new BoxLayout(mainInfo,BoxLayout.Y_AXIS));
			mainInfo.setAlignmentY(Component.LEFT_ALIGNMENT);
			mainInfo.setPreferredSize(new Dimension(350,350));
			mainInfo.setOpaque(false);
			
			titleLbl = new JLabel("Photo Information");
			titleLbl.setFont(Themes.titleFont);
			titleLbl.setForeground(Themes.themeColor);
			titleLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
			titleLbl.setOpaque(false);
			
			
			namePanel = new JPanel();
			namePanel.setLayout(new FlowLayout());
			namePanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			namePanel.setOpaque(false);
			
			
			nameLbl = new JLabel("Name:");
			nameLbl.setFont(Themes.componentsFont);
			nameLbl.setForeground(Themes.themeColor);
			nameLbl.setOpaque(false);
			photoNameTxtF = new JTextField(20);
			photoNameTxtF.setFont(Themes.componentsFont);
			photoNameTxtF.setForeground(Color.white);
			photoNameTxtF.setBorder(null);
			photoNameTxtF.setEditable(false);
			photoNameTxtF.setOpaque(false);
			photoNameTxtF.setText(photo.getFilename());
			
			namePanel.setAlignmentY(Component.TOP_ALIGNMENT);
			namePanel.add(nameLbl);
			namePanel.add(photoNameTxtF);
			
			captionPanel = new JPanel();
			captionPanel.setLayout(new FlowLayout());
			captionPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			captionPanel.setOpaque(false);
			
			captionLbl = new JLabel("Caption:");
			captionLbl.setFont(Themes.componentsFont);
			captionLbl.setForeground(Themes.themeColor);
			captionLbl.setOpaque(false);
			captionTxtF = new JTextField(18);
			captionTxtF.setFont(Themes.componentsFont);
			captionTxtF.setForeground(Color.white);
			captionTxtF.setBorder(null);
			captionTxtF.setEditable(false);
			captionTxtF.setOpaque(false);
			captionTxtF.setText(photo.getCaption());
			
			captionPanel.setAlignmentY(Component.TOP_ALIGNMENT);
			captionPanel.add(captionLbl);
			captionPanel.add(captionTxtF);
			
			datePanel = new JPanel();
			datePanel.setLayout(new FlowLayout());
			datePanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			datePanel.setOpaque(false);
			
			dateLbl = new JLabel("Date:");
			dateLbl.setFont(Themes.componentsFont);
			dateLbl.setForeground(Themes.themeColor);
			dateLbl.setOpaque(false);
			dateTxtF = new JTextField(20);
			dateTxtF.setFont(Themes.componentsFont);
			dateTxtF.setForeground(Color.white);
			dateTxtF.setBorder(null);
			dateTxtF.setEditable(false);
			dateTxtF.setOpaque(false);
			dateTxtF.setText(photo.getStringDate());
			
			datePanel.setAlignmentY(Component.TOP_ALIGNMENT);
			datePanel.add(dateLbl);
			datePanel.add(dateTxtF);

			mainInfo.add(namePanel);
			mainInfo.add(captionPanel);
			mainInfo.add(datePanel);
			
			tagsPanel = new JPanel();
			tagsPanel.setLayout(new BoxLayout(tagsPanel,BoxLayout.Y_AXIS));
			tagsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
			tagsPanel.setOpaque(false);
			tagsPanel.setPreferredSize(new Dimension(200,200));
			tagsPanel.setBorder(new EmptyBorder(20,20,20,20));
			
			tagsLbl = new JLabel("Tags: ");
			tagsLbl.setFont(Themes.componentsFont);
			tagsLbl.setForeground(Themes.themeColor);
			tagsLbl.setOpaque(false);
			tagsLbl.setAlignmentX(Component.CENTER_ALIGNMENT);			
			filltagsModel();
			tagList = new JList(tagsModel);
			tagList.setOpaque(true);
			tagList.setAlignmentX(Component.CENTER_ALIGNMENT);
			tagList.setPreferredSize(new Dimension(200,200));
			tagListSP = new JScrollPane(tagList);
			tagListSP.setOpaque(true);
			tagListSP.setAlignmentX(Component.CENTER_ALIGNMENT);
			tagListSP.setPreferredSize(new Dimension(200,200));
			
					
			tagsPanel.add(tagsLbl);
			tagsPanel.add(tagListSP);
			
			buttonsPanel = new JPanel();
			buttonsPanel.setLayout((new BoxLayout(buttonsPanel,BoxLayout.X_AXIS)));
			buttonsPanel.setOpaque(false);
			buttonsPanel.setBorder(new EmptyBorder(20,0,20,0));
			
			movePhotoBtn = new ImageButton(new ImageIcon("resources/moveUP.jpg"));
			movePhotoBtn.setPressedIcon(new ImageIcon("resources/moveDN.jpg"));
			movePhotoBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
			movePhotoBtn.setActionCommand("move photo");
			movePhotoBtn.addActionListener(ol);
			backBtn = new ImageButton(new ImageIcon("resources/backUP.jpg"));
			backBtn.setPressedIcon(new ImageIcon("resources/backDN.jpg"));
			backBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
			backBtn.setActionCommand("back");
			backBtn.addActionListener(ol);
			editBtn = new ImageButton(new ImageIcon("resources/editUP.jpg"));
			editBtn.setPressedIcon(new ImageIcon("resources/editDN.jpg"));
			editBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
			editBtn.setActionCommand("edit");
			editBtn.addActionListener(ol);
			
			
			
			
			buttonsPanel.add(movePhotoBtn);
			buttonsPanel.add(new Box.Filler(new Dimension(20,0), new Dimension(20,0), new Dimension(20,0)));
			buttonsPanel.add(backBtn);
			buttonsPanel.add(editBtn);
			
			
			rightPanel.add(new Box.Filler(new Dimension(0,50), new Dimension(0,50), new Dimension(0,50)));
			rightPanel.add(titleLbl);
			rightPanel.add(new Box.Filler(new Dimension(0,50), new Dimension(0,50), new Dimension(0,50)));
			rightPanel.add(mainInfo);
			rightPanel.add(new Box.Filler(new Dimension(0,50), new Dimension(0,50), new Dimension(0,50)));
			rightPanel.add(tagsPanel);
			rightPanel.add(new Box.Filler(new Dimension(0,50), new Dimension(0,50), new Dimension(0,50)));
			rightPanel.add(buttonsPanel);
			
			mainPanel.add(new Box.Filler(new Dimension(100,0), new Dimension(100,0), new Dimension(100,0)));
			mainPanel.add(leftPanel);
			mainPanel.add(new Box.Filler(new Dimension(110,0), new Dimension(110,0), new Dimension(110,0)));
			mainPanel.add(rightPanel);
			
			
			
			
			setTitle("Photo Display");
			add(mainPanel);
			Toolkit tk = Toolkit.getDefaultToolkit();
		    Dimension screenSize = tk.getScreenSize();
		    int screenHeight = screenSize.height;
		    int screenWidth = screenSize.width;
		    setSize(1024,700);
		    setLocation(screenWidth / 4, screenHeight / 6);
		    this.setResizable(false);
		    addWindowListener(new WindowAdapter(){
				
				public void windowClosing(WindowEvent we){
					PhotoDisplay.this.photosScreen.saveState();
					System.exit(0);
					
				 }
			});
			
			
		}

		/**
		 * Second constructor for the class PhotoDisplay
		 * @param photo
		 * @param photosScreen
		 * @param control
		 */
		public PhotoDisplay(Photo photo, PhotosScreen photosScreen,ControlInterface control) {
			this.control = control;
			this.ol = new OptionsListener();
			this.photo = photo;
			this.photosScreen = photosScreen;
			
			mainPanel = new ImagePanel("resources/bgPhoto.jpg");
			mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.X_AXIS));
			mainPanel.setPreferredSize(new Dimension(1024,768));
			
			
			leftPanel = new JPanel();
			leftPanel.setPreferredSize(new Dimension(646,768));
			leftPanel.setOpaque(false);
			leftPanel.add(Box.createHorizontalGlue());
			
			
			photoRf = new Reflect("pictures/"+photo.getFilename());
			photoRf.setPreferredSize(new Dimension(505,768));
			photoRf.setOpaque(false);
			
			leftPanel.add(photoRf);
			
			rightPanel = new JPanel();
			rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
			rightPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
			rightPanel.setPreferredSize(new Dimension(350,768));
			rightPanel.setOpaque(false);
			
			mainInfo = new JPanel();
			mainInfo.setLayout(new BoxLayout(mainInfo,BoxLayout.Y_AXIS));
			mainInfo.setAlignmentY(Component.LEFT_ALIGNMENT);
			mainInfo.setPreferredSize(new Dimension(350,350));
			mainInfo.setOpaque(false);
			
			titleLbl = new JLabel("Photo Information");
			titleLbl.setFont(Themes.titleFont);
			titleLbl.setForeground(Themes.themeColor);
			titleLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
			titleLbl.setOpaque(false);
			
			
			namePanel = new JPanel();
			namePanel.setLayout(new FlowLayout());
			namePanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			namePanel.setOpaque(false);
			
			
			nameLbl = new JLabel("Name:");
			nameLbl.setFont(Themes.componentsFont);
			nameLbl.setForeground(Themes.themeColor);
			nameLbl.setOpaque(false);
			photoNameTxtF = new JTextField(20);
			photoNameTxtF.setFont(Themes.componentsFont);
			photoNameTxtF.setForeground(Color.white);
			photoNameTxtF.setBorder(null);
			photoNameTxtF.setEditable(false);
			photoNameTxtF.setOpaque(false);
			photoNameTxtF.setText(photo.getFilename());
			
			namePanel.setAlignmentY(Component.TOP_ALIGNMENT);
			namePanel.add(nameLbl);
			namePanel.add(photoNameTxtF);
			
			captionPanel = new JPanel();
			captionPanel.setLayout(new FlowLayout());
			captionPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			captionPanel.setOpaque(false);
			
			captionLbl = new JLabel("Caption:");
			captionLbl.setFont(Themes.componentsFont);
			captionLbl.setForeground(Themes.themeColor);
			captionLbl.setOpaque(false);
			captionTxtF = new JTextField(18);
			captionTxtF.setFont(Themes.componentsFont);
			captionTxtF.setForeground(Color.white);
			captionTxtF.setBorder(null);
			captionTxtF.setEditable(false);
			captionTxtF.setOpaque(false);
			captionTxtF.setText(photo.getCaption());
			
			captionPanel.setAlignmentY(Component.TOP_ALIGNMENT);
			captionPanel.add(captionLbl);
			captionPanel.add(captionTxtF);
			
			datePanel = new JPanel();
			datePanel.setLayout(new FlowLayout());
			datePanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			datePanel.setOpaque(false);
			
			dateLbl = new JLabel("Date:");
			dateLbl.setFont(Themes.componentsFont);
			dateLbl.setForeground(Themes.themeColor);
			dateLbl.setOpaque(false);
			dateTxtF = new JTextField(20);
			dateTxtF.setFont(Themes.componentsFont);
			dateTxtF.setForeground(Color.white);
			dateTxtF.setBorder(null);
			dateTxtF.setEditable(false);
			dateTxtF.setOpaque(false);
			dateTxtF.setText(photo.getStringDate());
			
			datePanel.setAlignmentY(Component.TOP_ALIGNMENT);
			datePanel.add(dateLbl);
			datePanel.add(dateTxtF);

			mainInfo.add(namePanel);
			mainInfo.add(captionPanel);
			mainInfo.add(datePanel);
			
			tagsPanel = new JPanel();
			tagsPanel.setLayout(new BoxLayout(tagsPanel,BoxLayout.Y_AXIS));
			tagsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
			tagsPanel.setOpaque(false);
			tagsPanel.setPreferredSize(new Dimension(200,200));
			tagsPanel.setBorder(new EmptyBorder(20,20,20,20));
			
			tagsLbl = new JLabel("Tags: ");
			tagsLbl.setFont(Themes.componentsFont);
			tagsLbl.setForeground(Themes.themeColor);
			tagsLbl.setOpaque(false);
			tagsLbl.setAlignmentX(Component.CENTER_ALIGNMENT);			
			filltagsModel();
			tagList = new JList(tagsModel);
			tagList.setOpaque(true);
			tagList.setAlignmentX(Component.CENTER_ALIGNMENT);
			tagList.setPreferredSize(new Dimension(200,200));
			tagListSP = new JScrollPane(tagList);
			tagListSP.setOpaque(true);
			tagListSP.setAlignmentX(Component.CENTER_ALIGNMENT);
			tagListSP.setPreferredSize(new Dimension(200,200));
			
					
			tagsPanel.add(tagsLbl);
			tagsPanel.add(tagListSP);
			
			buttonsPanel = new JPanel();
			buttonsPanel.setLayout((new BoxLayout(buttonsPanel,BoxLayout.X_AXIS)));
			buttonsPanel.setOpaque(false);
			buttonsPanel.setBorder(new EmptyBorder(20,0,20,0));
			
			
			backBtn = new ImageButton(new ImageIcon("resources/backUP.jpg"));
			backBtn.setPressedIcon(new ImageIcon("resources/backDN.jpg"));
			backBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
			backBtn.setActionCommand("back");
			backBtn.addActionListener(ol);		
			
			
			
			
			
			buttonsPanel.add(new Box.Filler(new Dimension(20,0), new Dimension(20,0), new Dimension(20,0)));
			buttonsPanel.add(backBtn);
			
			
			rightPanel.add(new Box.Filler(new Dimension(0,50), new Dimension(0,50), new Dimension(0,50)));
			rightPanel.add(titleLbl);
			rightPanel.add(new Box.Filler(new Dimension(0,50), new Dimension(0,50), new Dimension(0,50)));
			rightPanel.add(mainInfo);
			rightPanel.add(new Box.Filler(new Dimension(0,50), new Dimension(0,50), new Dimension(0,50)));
			rightPanel.add(tagsPanel);
			rightPanel.add(new Box.Filler(new Dimension(0,50), new Dimension(0,50), new Dimension(0,50)));
			rightPanel.add(buttonsPanel);
			
			mainPanel.add(new Box.Filler(new Dimension(100,0), new Dimension(100,0), new Dimension(100,0)));
			mainPanel.add(leftPanel);
			mainPanel.add(new Box.Filler(new Dimension(110,0), new Dimension(110,0), new Dimension(110,0)));
			mainPanel.add(rightPanel);
			
			
			
			
			setTitle("Photo Display");
			add(mainPanel);
			Toolkit tk = Toolkit.getDefaultToolkit();
		    Dimension screenSize = tk.getScreenSize();
		    int screenHeight = screenSize.height;
		    int screenWidth = screenSize.width;
		    setSize(1024,700);
		    setLocation(screenWidth / 4, screenHeight / 6);
		    this.setResizable(false);
		    addWindowListener(new WindowAdapter(){
				
				public void windowClosing(WindowEvent we){
					PhotoDisplay.this.photosScreen.saveState();
					System.exit(0);
					
				 }
			});
		}

		/**
		 * Method that fills up the model with all the tags the picture has.
		 */
		private void filltagsModel() {
			Hashtable<String,Tag> tags = new Hashtable<String,Tag>();
			tags = this.photo.getTags();
			this.tagsModel.clear();
			for(Tag tag:tags.values()){
				this.tagsModel.addElement(tag.getTagType()+":\""+tag.getTagValue()+"\"");
			}
			
		}
		
		/**
		 * Method to move one picture from one album to another
		 */
		public void movePhoto(){
			if(newAlbumName!=null&&!newAlbumName.equals("")){					
				int code = this.control.movePhoto(this.photo.getFilename(), this.album, this.newAlbumName);
				if(code==-1){
					final JFrame error = new JFrame("Important Message!");
					JOptionPane.showMessageDialog(error, "Photo Already in that album.");
				}
				else if(code == 1){
					final JFrame error = new JFrame("Important Message!");
					JOptionPane.showMessageDialog(error, "Photo Correctly Moved.");
					this.movePopUp.setVisible(false);	
					//Photo correctly moved
					try {
						this.photosScreen.reDrawPhotos();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
			else{
				final JFrame error = new JFrame("Important Message!");
				JOptionPane.showMessageDialog(error, "No Album Selected.");
			}
		}
		
		/**
		 * Saves changes made to the picture
		 */
		public void save(){
			editBtn.setIcon(new ImageIcon("resources/editUP.jpg"));
			editBtn.setPressedIcon(new ImageIcon("resources/editDN.jpg"));
			editBtn.setActionCommand("edit");
			
			String newCaption = captionTxtF.getText();
			PhotoDisplay.this.photo.setCaption(newCaption);
			captionTxtF.setText(newCaption);
			captionTxtF.setBorder(null);
			captionTxtF.setEditable(false);
			captionTxtF.setOpaque(false);
			captionTxtF.setForeground(Color.white);
			
			
			
			tagsPanel.remove(buttons);
			
			
			filltagsModel();
			tagList.revalidate();
			tagList.repaint();
			tagListSP.revalidate();
			tagListSP.repaint();
			tagsPanel.revalidate();
			tagsPanel.repaint();
			buttonsPanel.revalidate();
			buttonsPanel.repaint();
		}
		
		/**
		 * Listener for the different buttons in PhotoDisplay screen.
		 * @author Roberto Ronderos Botero
		 *
		 */
		protected class OptionsListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				
				String option = e.getActionCommand();
				
				if(option.equals("back")){
					PhotoDisplay.this.setVisible(false);
					PhotoDisplay.this.photosScreen.setVisible(true);
				}
				else if(option.equals("move photo")){
					Hashtable<String,Album> albumsHT = control.listAlbums();
					String[] albums = new String[albumsHT.size()];
					int i=0;
					for(Album album:albumsHT.values()){
						albums[i] = album.getAlbumName();
						i++;
					}
					PhotoDisplay.this.movePopUp = new movePhotoPopUp(albums, PhotoDisplay.this);
					PhotoDisplay.this.movePopUp.setVisible(true);					
					
				}
				else if(option.equals("edit")){
					editBtn.setIcon(new ImageIcon("resources/saveUP.jpg"));
					editBtn.setPressedIcon(new ImageIcon("resources/saveDN.jpg"));
					editBtn.setActionCommand("save");
					
					captionTxtF.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.red));
					captionTxtF.setEditable(true);
					captionTxtF.setOpaque(true);
					captionTxtF.setForeground(Color.black);
					
					buttons = new JPanel();
					buttons.setOpaque(false);
					buttons.setLayout(new FlowLayout());
					
					addTagBtn = new ImageButton(new ImageIcon("resources/addTagsUP.jpg"));
					addTagBtn.setPressedIcon(new ImageIcon("resources/addTagsDN.jpg"));
					addTagBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
					addTagBtn.setActionCommand("add Tags");
					addTagBtn.addActionListener(ol);
					
					removeTagBtn = new ImageButton(new ImageIcon("resources/removeTagUP.jpg"));
					removeTagBtn.setPressedIcon(new ImageIcon("resources/removeTagDN.jpg"));
					removeTagBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
					removeTagBtn.setActionCommand("remove Tag");
					removeTagBtn.addActionListener(ol);
					
					buttons.add(removeTagBtn);
					buttons.add(addTagBtn);
					
					
					tagsPanel.add(buttons);
					tagsPanel.revalidate();
					tagsPanel.repaint();
					buttonsPanel.revalidate();
					buttonsPanel.repaint();				
				}
				else if(option.equals("save")){
					save();
				}
				else if(option.equals("add Tags")){
					PhotoDisplay.this.tagsPopUp = new addTagsPopUp(PhotoDisplay.this.control, PhotoDisplay.this, PhotoDisplay.this.photo.getFilename());
					PhotoDisplay.this.tagsPopUp.setVisible(true);
				}
				else if(option.equals("remove Tag")){
					String selected = (String) tagList.getSelectedValue();
					if(selected!=null&&!selected.equals("")){
						String[] split1= new String[2];
						split1= selected.split(":");
						String type = split1[0];
						String value = split1[1];
						value = value.substring(1);
						value = value.substring(0, value.length()-1);
						control.deleteTag(PhotoDisplay.this.photo.getFilename(), type, value);
						tagsModel.removeElement(selected);
						filltagsModel();
						tagList.revalidate();
						tagList.repaint();
						tagListSP.revalidate();
						tagListSP.repaint();
						tagsPanel.revalidate();
						tagsPanel.repaint();
						buttonsPanel.revalidate();
						buttonsPanel.repaint();
						
					}
					else{
						final JFrame error = new JFrame("Important Message!");
						JOptionPane.showMessageDialog(error, "No Tag Selected.");
					}
				}
				
				
			}
		}
		
		
		
		
		
}
