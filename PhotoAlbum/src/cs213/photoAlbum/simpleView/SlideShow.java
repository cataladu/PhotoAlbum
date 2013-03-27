package cs213.photoAlbum.simpleView;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import cs213.photoAlbum.control.*;
import cs213.photoAlbum.model.Photo;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * This class is the screen with the SlideShow of the pictures. It shows name, caption and date of the picture and allows the user to go back and forward through
 * the pictures displayed at that moment in PhotosScreen. 
 * @author Roberto Ronderos Botero
 */
public class SlideShow extends JFrame {

	private static final long serialVersionUID = 1L;

	int counter;
	int numberPhotos;
	
	protected ImagePanel mainPanel;
	protected JPanel slideArea;
	protected JPanel infoArea;

	protected ImageButton backBtn;
	protected ImageButton nextBtn;
	protected ImageButton previousBtn;
	
	protected JLabel photoNameLbl;
	protected JLabel name;
	protected JLabel captionLbl;
	protected JLabel caption;
	protected JLabel dateLbl;
	protected JLabel date;
	
	protected Reflect picture;
	
	protected ArrayList<PhotoInfo> photos;
	protected ArrayList<Reflect> photosEffect;
	protected ArrayList<PhotoInfo> photosSlide;
	protected PhotosScreen photosScreen;
	
	int first=0;
	boolean done = false;
	protected OptionsListener ol;

	private ArrayList<Photo> photosSearchResult;

	private ArrayList<Photo> photosSlideSearchResult;
	
	/**
	 * Constructor for the class SlideShow
	 * @param control
	 * @param photosScreen
	 * @param photos
	 */
	public SlideShow(ControlInterface control, PhotosScreen photosScreen, ArrayList<PhotoInfo> photos){
		
		counter = 0;
		ol = new OptionsListener();
	
		this.photos = photos;	
		numberPhotos = photos.size();
		this.photosScreen = photosScreen;
		
		photosSlide = new ArrayList<PhotoInfo>();
		photosEffect = new ArrayList<Reflect>();
		
		createReflections();
	
		picture = photosEffect.get(0);
		picture.setPreferredSize(new Dimension(505,768));
	
		name = new JLabel("Filename: ");
		name.setFont(Themes.titleFont);
		name.setForeground(Themes.themeColor);
		name.setAlignmentX(Component.CENTER_ALIGNMENT);
				
		photoNameLbl = new JLabel(photos.get(first).photo.getFilename());
		photoNameLbl.setFont(Themes.titleFont);
		photoNameLbl.setForeground( Color.WHITE);
		photoNameLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
				
		caption = new JLabel("Caption: ");
		caption.setFont(Themes.titleFont);
		caption.setForeground(Themes.themeColor);
		caption.setAlignmentX(Component.CENTER_ALIGNMENT);
	
		captionLbl = new JLabel(photos.get(first).photo.getCaption());
		captionLbl.setFont(Themes.titleFont);
		captionLbl.setForeground( Color.WHITE);
		captionLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
			
		date = new JLabel("Date");
		date.setFont(Themes.titleFont);
		date.setForeground( Themes.themeColor);
		date.setAlignmentX(Component.CENTER_ALIGNMENT);
	
		dateLbl = new JLabel(photos.get(first).photo.getStringDate());
		dateLbl.setFont(Themes.titleFont);
		dateLbl.setForeground( Color.WHITE);
		dateLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		previousBtn = new ImageButton("resources/prevUP.png");
		previousBtn.setPressedIcon(new ImageIcon("resources/prevDN.png"));
		previousBtn.setActionCommand("Previous");
		previousBtn.setContentAreaFilled(false);
		previousBtn.setBorderPainted(false);
		previousBtn.setOpaque(false);
		previousBtn.setPreferredSize(new Dimension(56,57));
		previousBtn.addActionListener(ol);

		nextBtn = new ImageButton("resources/nextUP.png");
		nextBtn.setPressedIcon(new ImageIcon("resources/nextDN.png"));
		nextBtn.setActionCommand("Next");
		nextBtn.setContentAreaFilled(false);
		nextBtn.setBorderPainted(false);
		nextBtn.setOpaque(false);
		nextBtn.setPreferredSize(new Dimension(56,57));
		nextBtn.addActionListener(ol);
		
		backBtn = new ImageButton("resources/backUP.jpg");
		backBtn.setActionCommand("Back");		
		backBtn.setPreferredSize(new Dimension(100,30));
		backBtn.setPressedIcon(new ImageIcon("resources/backDN.jpg"));
		backBtn.addActionListener(ol);
		backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		slideArea = new JPanel();
		slideArea.setPreferredSize(new Dimension(646,768));
		slideArea.setLayout( new FlowLayout());
		slideArea.setBackground(Color.BLACK);
		slideArea.setBorder(new EmptyBorder(20,0,0,0));
		slideArea.add(previousBtn);
		slideArea.add(picture);
		slideArea.add(nextBtn);
		slideArea.setOpaque(false);
				
		infoArea = new JPanel();
		infoArea.setLayout( new BoxLayout(infoArea, BoxLayout.Y_AXIS));
		infoArea.setBackground(Color.BLACK);
		infoArea.setPreferredSize(new Dimension(350,768));
		infoArea.add(name);
		infoArea.add(photoNameLbl);
		infoArea.add(new Box.Filler(new Dimension(20,20), new Dimension(20,20), new Dimension(20,20)));
		infoArea.add(caption);
		infoArea.add(captionLbl);
		infoArea.add(new Box.Filler(new Dimension(20,20), new Dimension(20,20), new Dimension(20,20)));
		infoArea.add(date);
		infoArea.add(dateLbl);
		infoArea.add(new Box.Filler(new Dimension(100,300), new Dimension(100,300), new Dimension(100,300)));
		infoArea.add(backBtn);
		infoArea.setOpaque(false);
		
		mainPanel = new ImagePanel("resources/bgPhoto.jpg");
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		mainPanel.setPreferredSize(new Dimension(1040,700));
		mainPanel.add(slideArea);
		mainPanel.add(infoArea);
		
		add(mainPanel);
		
		this.setBackground(Color.BLACK);
		this.setSize(1040,700);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Slide Show");
	}
	
	/**
	 * Second constructor for the SlideShow
	 * @param control
	 * @param photosSearchResult
	 * @param photosScreen
	 */
	public SlideShow(ControlInterface control,ArrayList<Photo> photosSearchResult, PhotosScreen photosScreen) {
		counter = 0;
		ol = new OptionsListener();
		
		this.photosSearchResult = photosSearchResult;	
		numberPhotos = photosSearchResult.size();
		this.photosScreen = photosScreen;
		
		
		photosSlideSearchResult = new ArrayList<Photo>();
		photosEffect = new ArrayList<Reflect>();
		
		createReflections();
	
		picture = photosEffect.get(0);
		picture.setPreferredSize(new Dimension(505,768));
	
		name = new JLabel("Filename: ");
		name.setFont(Themes.titleFont);
		name.setForeground(Themes.themeColor);
		name.setAlignmentX(Component.CENTER_ALIGNMENT);
				
		photoNameLbl = new JLabel(photosSearchResult.get(first).getFilename());
		photoNameLbl.setFont(Themes.titleFont);
		photoNameLbl.setForeground( Color.WHITE);
		photoNameLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
				
		caption = new JLabel("Caption: ");
		caption.setFont(Themes.titleFont);
		caption.setForeground(Themes.themeColor);
		caption.setAlignmentX(Component.CENTER_ALIGNMENT);
	
		captionLbl = new JLabel(photosSearchResult.get(first).getCaption());
		captionLbl.setFont(Themes.titleFont);
		captionLbl.setForeground( Color.WHITE);
		captionLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
			
		date = new JLabel("Date");
		date.setFont(Themes.titleFont);
		date.setForeground( Themes.themeColor);
		date.setAlignmentX(Component.CENTER_ALIGNMENT);
	
		dateLbl = new JLabel(photosSearchResult.get(first).getStringDate());
		dateLbl.setFont(Themes.titleFont);
		dateLbl.setForeground( Color.WHITE);
		dateLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		previousBtn = new ImageButton("resources/prevUP.png");
		previousBtn.setPressedIcon(new ImageIcon("resources/prevDN.png"));
		previousBtn.setActionCommand("Previous");
		previousBtn.setContentAreaFilled(false);
		previousBtn.setBorderPainted(false);
		previousBtn.setOpaque(false);
		previousBtn.setPreferredSize(new Dimension(56,57));
		previousBtn.addActionListener(ol);

		nextBtn = new ImageButton("resources/nextUP.png");
		nextBtn.setPressedIcon(new ImageIcon("resources/nextDN.png"));
		nextBtn.setActionCommand("Next");
		nextBtn.setContentAreaFilled(false);
		nextBtn.setBorderPainted(false);
		nextBtn.setOpaque(false);
		nextBtn.setPreferredSize(new Dimension(56,57));
		nextBtn.addActionListener(ol);
		
		backBtn = new ImageButton("resources/backUP.jpg");
		backBtn.setActionCommand("Back");		
		backBtn.setPreferredSize(new Dimension(100,30));
		backBtn.setPressedIcon(new ImageIcon("resources/backDN.jpg"));
		backBtn.addActionListener(ol);
		backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		slideArea = new JPanel();
		slideArea.setPreferredSize(new Dimension(646,768));
		slideArea.setLayout( new FlowLayout());
		slideArea.setBackground(Color.BLACK);
		slideArea.setBorder(new EmptyBorder(20,0,0,0));
		slideArea.add(previousBtn);
		slideArea.add(picture);
		slideArea.add(nextBtn);
		slideArea.setOpaque(false);
				
		infoArea = new JPanel();
		infoArea.setLayout( new BoxLayout(infoArea, BoxLayout.Y_AXIS));
		infoArea.setBackground(Color.BLACK);
		infoArea.setPreferredSize(new Dimension(350,768));
		infoArea.add(name);
		infoArea.add(photoNameLbl);
		infoArea.add(new Box.Filler(new Dimension(20,20), new Dimension(20,20), new Dimension(20,20)));
		infoArea.add(caption);
		infoArea.add(captionLbl);
		infoArea.add(new Box.Filler(new Dimension(20,20), new Dimension(20,20), new Dimension(20,20)));
		infoArea.add(date);
		infoArea.add(dateLbl);
		infoArea.add(new Box.Filler(new Dimension(100,300), new Dimension(100,300), new Dimension(100,300)));
		infoArea.add(backBtn);
		infoArea.setOpaque(false);
		
		mainPanel = new ImagePanel("resources/bgPhoto.jpg");
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		mainPanel.setPreferredSize(new Dimension(1040,700));
		mainPanel.add(slideArea);
		mainPanel.add(infoArea);
		
		add(mainPanel);
		
		this.setBackground(Color.BLACK);
		this.setSize(1040,700);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Slide Show");
	}

	/**
	 * This method creates the reflection objects which are the effect applied to the pictures.
	 */
	private void createReflections(){
		
		if(this.photos!=null){
			for(PhotoInfo photoThumb:this.photos){			
				photosSlide.add(photoThumb);
				Reflect photoReflect = new Reflect("pictures/"+photoThumb.photo.getFilename());
				photosEffect.add(photoReflect);
			}
		}
		else{
			for(Photo photoThumb:this.photosSearchResult){			
				photosSlideSearchResult.add(photoThumb);
				Reflect photoReflect = new Reflect("pictures/"+photoThumb.getFilename());
				photosEffect.add(photoReflect);
			}
		}
		
	}
	
	/**
	 * Listener for the buttons in SlideShow. Next to go to the next picture, Previous to go the previous picture and Back to go back to the
	 * Photo Screen
	 * @author Roberto Ronderos Botero
	 */
	protected class OptionsListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
			String action = e.getActionCommand();
			
			if(action.equals("Next")){
								
				SlideShow.this.counter++;
				
				if(SlideShow.this.counter==SlideShow.this.numberPhotos)
					SlideShow.this.counter=0;
				
				SlideShow.this.slideArea.remove(picture);
				SlideShow.this.slideArea.remove(SlideShow.this.nextBtn);
				SlideShow.this.picture = SlideShow.this.photosEffect.get(SlideShow.this.counter);
				SlideShow.this.slideArea.add(picture);
				SlideShow.this.slideArea.add(SlideShow.this.nextBtn);
				if(SlideShow.this.photos!=null){
				SlideShow.this.photoNameLbl.setText(SlideShow.this.photosSlide.get(SlideShow.this.counter).photo.getFilename());
				SlideShow.this.captionLbl.setText(SlideShow.this.photosSlide.get(SlideShow.this.counter).photo.getCaption());
				SlideShow.this.dateLbl.setText(SlideShow.this.photosSlide.get(SlideShow.this.counter).photo.getStringDate());
				}
				else{
				SlideShow.this.photoNameLbl.setText(SlideShow.this.photosSlideSearchResult.get(SlideShow.this.counter).getFilename());
				SlideShow.this.captionLbl.setText(SlideShow.this.photosSlideSearchResult.get(SlideShow.this.counter).getCaption());
				SlideShow.this.dateLbl.setText(SlideShow.this.photosSlideSearchResult.get(SlideShow.this.counter).getStringDate());	
				}
				SlideShow.this.repaint();

				
			}
			
			if(action.equals("Previous")){
				
				SlideShow.this.counter--;	
				
				if(SlideShow.this.counter==-1)
					SlideShow.this.counter=SlideShow.this.numberPhotos-1;

				SlideShow.this.slideArea.remove(picture);
				SlideShow.this.slideArea.remove(SlideShow.this.nextBtn);
				SlideShow.this.picture = SlideShow.this.photosEffect.get(SlideShow.this.counter);
				SlideShow.this.slideArea.add(picture);
				SlideShow.this.slideArea.add(SlideShow.this.nextBtn);
				if(SlideShow.this.photos!=null){
					SlideShow.this.photoNameLbl.setText(SlideShow.this.photosSlide.get(SlideShow.this.counter).photo.getFilename());
					SlideShow.this.captionLbl.setText(SlideShow.this.photosSlide.get(SlideShow.this.counter).photo.getCaption());
					SlideShow.this.dateLbl.setText(SlideShow.this.photosSlide.get(SlideShow.this.counter).photo.getStringDate());
				}
				else{
					SlideShow.this.photoNameLbl.setText(SlideShow.this.photosSlideSearchResult.get(SlideShow.this.counter).getFilename());
					SlideShow.this.captionLbl.setText(SlideShow.this.photosSlideSearchResult.get(SlideShow.this.counter).getCaption());
					SlideShow.this.dateLbl.setText(SlideShow.this.photosSlideSearchResult.get(SlideShow.this.counter).getStringDate());	
				}
				SlideShow.this.slideArea.repaint();
				SlideShow.this.slideArea.revalidate();
		
				
			}
			
			if(action.equals("Back")){
				SlideShow.this.photosScreen.setVisible(true);
				SlideShow.this.setVisible(false);
			}
			
		}
		
		
		
	}
}
