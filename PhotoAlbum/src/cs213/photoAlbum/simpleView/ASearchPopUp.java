package cs213.photoAlbum.simpleView;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;

import cs213.photoAlbum.control.ControlInterface;
import cs213.photoAlbum.model.Photo;

/**
 * Screen for advanced search, it gets all information needed to search though the albums. 
 * @author Catalina Laverde Duarte
 */
public class ASearchPopUp extends JFrame {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;
	
	//GUI Components
	//******************************
	//JFrames
	protected JPanel mainPanel;
	protected JPanel titlePanel;
	protected JPanel componentsPanel;
	protected JPanel firstRow;
	protected JPanel secondRow;
	protected JPanel thirdRow;
	protected JPanel fourthRow;
	protected JPanel buttonsPanel;
	//JLabels
	protected JLabel titleLbl;
	protected JLabel searchByDateLbl;
	protected JLabel fromLbl;
	protected JLabel toLbl;
	protected JLabel searchByTagsLbl;
	protected JLabel tagsLbl;
	//JRadioButton
	protected JRadioButton dateRB;
	protected JRadioButton tagsRB;
	//JTextFields
	protected JTextField fromTxtF;
	protected JTextField toTxtF;
	//JTextAreas
	protected JTextArea tagsTxtA;
	//JButtons with images
	protected ImageButton searchBtn;
	protected ImageButton cancelBtn;
	//JScroller
	protected JScrollPane  tagsScroller;
	
	//******************************
	//Class Attributes
	//******************************
	boolean searchDates = false;
	boolean searchTags = false;
	ControlInterface control;
	protected OptionsListener ol;
	AlbumsScreen albumsScreen;
	
	/**
	 * Constructor for the class ASearchPopUp
	 * @param control
	 * @param albumsScreen
	 */
	public ASearchPopUp(ControlInterface control, AlbumsScreen albumsScreen){
		
		//initializing attributes
		this.control = control;
		this.albumsScreen = albumsScreen;
		ol = new OptionsListener();
		
		//Initializing Panels
		mainPanel = new JPanel();
		mainPanel.setOpaque(false);
		titlePanel = new JPanel();
		titlePanel.setOpaque(false);
		componentsPanel = new JPanel();
		componentsPanel.setOpaque(false);
		firstRow = new JPanel();
		firstRow.setOpaque(false);
		secondRow = new JPanel();
		secondRow.setOpaque(false);
		thirdRow = new JPanel();
		thirdRow.setOpaque(false);
		fourthRow = new JPanel();
		fourthRow.setOpaque(false);
		buttonsPanel = new JPanel();
		buttonsPanel.setOpaque(false);		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));		
		
		
		//Adding components to panels:
		//Title Panel
		titleLbl = new JLabel("Advanced Search");
		titleLbl.setFont(Themes.titleFont);
		titleLbl.setForeground(Themes.themeColor);
		titlePanel.add(titleLbl);
		
		//Components Panel	
		componentsPanel.setLayout(new BoxLayout(componentsPanel, BoxLayout.Y_AXIS));
		componentsPanel.setPreferredSize(new Dimension(280,200));
		
		firstRow.setLayout(new WrapLayout(FlowLayout.LEFT));
		firstRow.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);		
		searchByDateLbl = new JLabel("Search By Date");
		searchByDateLbl.setFont(Themes.componentsFont);
		searchByDateLbl.setForeground(Themes.themeColor);		
		firstRow.add(searchByDateLbl);		
		dateRB = new JRadioButton();
		dateRB.setBackground(Color.black);		
		firstRow.add(dateRB);			
		componentsPanel.add(firstRow);
		
		secondRow.setLayout(new WrapLayout(FlowLayout.CENTER));
		secondRow.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);			
		fromLbl = new JLabel("From:");
		fromLbl.setFont(Themes.componentsFont);
		fromLbl.setForeground(Themes.themeColor);		
		secondRow.add(fromLbl);		
		fromTxtF = new JTextField(10);
		fromTxtF.setUI(new HintTextFieldUI("dd/MM/yyyy-H:m:s", true));
		fromTxtF.setEditable(false);
		secondRow.add(fromTxtF);		
		toLbl = new JLabel("To:");
		toLbl.setFont(Themes.componentsFont);
		toLbl.setForeground(Themes.themeColor);		
		secondRow.add(toLbl);				
		toTxtF = new JTextField(10);
		toTxtF.setUI(new HintTextFieldUI("dd/MM/yyyy-H:m:s", true));
		toTxtF.setEditable(false);
		secondRow.add(toTxtF);		
		componentsPanel.add(secondRow);
		
		thirdRow.setLayout(new WrapLayout(FlowLayout.LEFT));
		thirdRow.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);		
		searchByTagsLbl = new JLabel("Search By Tags:");
		searchByTagsLbl.setFont(Themes.componentsFont);
		searchByTagsLbl.setForeground(Themes.themeColor);		
		thirdRow.add(searchByTagsLbl);		
		tagsRB = new JRadioButton();
		tagsRB.setBackground(Color.black);		
		thirdRow.add(tagsRB);		
		componentsPanel.add(thirdRow);
		
		FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
		fl.setHgap(20);
		fourthRow.setLayout(fl);
		fourthRow.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);	
		
		tagsLbl = new JLabel("Tags:");
		tagsLbl.setFont(Themes.componentsFont);
		tagsLbl.setForeground(Themes.themeColor);		
		fourthRow.add(tagsLbl);		
		tagsTxtA = new JTextArea("[<tagType>:]<tagValue>,\n[<tagType>:]<tagValue>\neg.person:\"roberto\"\neg 2.person:\"roberto\",game:\"cod\"",5,20);
		tagsTxtA.setLineWrap(true);	
		tagsTxtA.setEditable(false); 
		tagsScroller = new JScrollPane(tagsTxtA);
		tagsScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		fourthRow.add(tagsScroller);
		
		 // Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
	    group.add(dateRB);
	    group.add(tagsRB);
	    RadioListener myListener = new RadioListener();
	    dateRB.addActionListener(myListener);
	    tagsRB.addActionListener(myListener);
	    dateRB.setActionCommand("dates");
	    tagsRB.setActionCommand("tags");
		componentsPanel.add(fourthRow);
		
		
		//Buttons Panel
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel,BoxLayout.LINE_AXIS));
		searchBtn = new ImageButton(new ImageIcon("resources/searchUp.jpg"));
		searchBtn.setPressedIcon(new ImageIcon("resources/searchDN.jpg"));
		searchBtn.setActionCommand("search");
		searchBtn.addActionListener(ol);
		buttonsPanel.add(searchBtn);
		
		cancelBtn = new ImageButton(new ImageIcon("resources/cancelUp.jpg"));
		cancelBtn.setPressedIcon(new ImageIcon("resources/cancelDN.jpg"));
		cancelBtn.setActionCommand("cancel");
		cancelBtn.addActionListener(ol);
		buttonsPanel.add(cancelBtn);
		
		//Adding panels to main panel
		mainPanel.add(titlePanel);
		mainPanel.add(componentsPanel);
		mainPanel.add(buttonsPanel);	
		
		
		//Setting up  background color
		mainPanel.setBackground(Color.black);
		this.getContentPane().setBackground(Color.BLACK);
		
		add(mainPanel);
		//Frame not re sizable
		this.setResizable(false);
		//set frame location
		this.setLocationRelativeTo(albumsScreen);
		//set window size 
		this.setPreferredSize(new Dimension(350,300));
		this.pack();
		
	}
	
	/**
	 * Radio listener to make sure the user only performs one kind of search at the same time.
	 * @author Catalina Laverde Duarte	 *
	 */
	class RadioListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			if (e.getActionCommand() == "dates") {
					tagsTxtA.setEditable(false);
					tagsTxtA.setText("[<tagType>:]<tagValue>,\n[<tagType>:]<tagValue>\neg.person:\"roberto\"\neg 2.person:\"roberto\",game:\"cod\"");
					searchTags = false;
			    	fromTxtF.setEditable(true);
					toTxtF.setEditable(true);
					searchDates = true;			    
	        } 
			else if(e.getActionCommand() == "tags") {
				tagsTxtA.setEditable(true);
				searchTags = true;
		    	fromTxtF.setEditable(false);
		    	fromTxtF.setText("");
		    	fromTxtF.setUI(new HintTextFieldUI("dd/MM/yyyy-H:m:s", true));
				toTxtF.setEditable(false);
				toTxtF.setText("");
				toTxtF.setUI(new HintTextFieldUI("dd/MM/yyyy-H:m:s", true));
				searchDates = false;
	        }			
			
		}
	}	
	
	/**
	 * Listener for the class ASearchPopUp, It also does all error handling depending on the user's inputs.
	 * @author Catalina Laverde Duarte
	 *
	 */
	protected class OptionsListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			String source = e.getActionCommand();
			
			if(source.equals("search")){
				
				if(searchDates&&!searchTags){
					String from = fromTxtF.getText();
					String to = toTxtF.getText();					
									
					if(!from.equals("")&&!to.equals("")){
						
						 	boolean fromCheck=checkFormatDate(from);
						 	boolean toCheck = checkFormatDate(to);
						 	
						 	if(fromCheck&&toCheck){
						 	
								ArrayList<Photo> searchResult=null;
								try {
									searchResult = control.getPhotosByDate(from, to);
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								if(searchResult!=null){
									PhotosScreen photoScreen=null;
									try {
										photoScreen = new PhotosScreen(searchResult, ASearchPopUp.this.control,ASearchPopUp.this.albumsScreen);
									} catch (Exception e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									photoScreen.setVisible(true);
									ASearchPopUp.this.albumsScreen.setVisible(false);
									ASearchPopUp.this.setVisible(false);
								}
								else{
									final JFrame error = new JFrame("Important Message!");
									JOptionPane.showMessageDialog(error, "No results for that search.");
								}
						 	}
						 	else{
						 		final JFrame error = new JFrame("Important Message!");
								JOptionPane.showMessageDialog(error, "Wrong Format, try: dd/MM/yyyy-H:m:s");
						 	}
						
						
					}
					else{
						final JFrame error = new JFrame("Important Message!");
						JOptionPane.showMessageDialog(error, "Please input \"from\" a \"to\" date fields");
					}
				}				
				else if(!searchDates&&searchTags){
					ArrayList<String> tags = tags(tagsTxtA.getText());				
									
					if(tags!=null){
						 							 	
								ArrayList<Photo> searchResult=null;								
								searchResult = control.getPhotosByTag(tags);
								
								if(searchResult!=null){
									PhotosScreen photoScreen=null;
									try {
										photoScreen = new PhotosScreen(searchResult, ASearchPopUp.this.control,ASearchPopUp.this.albumsScreen);
									} catch (Exception e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									photoScreen.setVisible(true);
									ASearchPopUp.this.albumsScreen.setVisible(false);
									ASearchPopUp.this.setVisible(false);
								}
								else{
									final JFrame error = new JFrame("Important Message!");
									JOptionPane.showMessageDialog(error, "No results for that search.");
								}
					}
					
				}
			}
			else if(source.equals("cancel")){
				ASearchPopUp.this.setVisible(false);
			}
		}
		
		public ArrayList<String> tags(String tags){
			
			ArrayList<String> tagsArray = new ArrayList<String>();
			if(tags.indexOf(",")!=-1){ //has commas					
					String regexCommas = ",";
					String tagsSplitted[] = tags.split(regexCommas);
					for(int i=0;i<tagsSplitted.length;i++){
						if(tagsSplitted[i].trim().indexOf("\"")==-1){
							final JFrame error = new JFrame("Important Message!");
							JOptionPane.showMessageDialog(error, "Wrong format, use : <tagType>:\"<tagValue>\"");
							return null;
						  }
						tagsArray.add(tagsSplitted[i].trim());
						
					}
				}
			else{
				  if(tags.trim().indexOf("\"")==-1){
					  final JFrame error = new JFrame("Important Message!");
						JOptionPane.showMessageDialog(error, "Wrong format, use : <tagType>:\"<tagValue>");
						return null;
				  }
				  tagsArray.add(tags.trim());
				 
				}
			return tagsArray;
		}
	}
	
	public boolean checkFormatDate(String datein) {		
		try{
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy-H:m:s");
			formatter.parse(datein);
			return true;
		}
		catch(ParseException e){
			return false;
		}
	}
	
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
