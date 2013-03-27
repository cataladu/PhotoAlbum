package cs213.photoAlbum.simpleView;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import cs213.photoAlbum.control.ControlInterface;

/**
 * This class is called when a new tag needs to be added to a photo.
 * @author Roberto Ronderos Botero
 */
public class addTagsPopUp extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	protected JPanel labelsPanel;
	protected JPanel tagsPanel;
	protected JPanel buttonsPanel;
	
	protected JLabel titleLbl;
	protected JLabel typeLbl;
	protected JLabel valueLbl;
	
	protected JTextField typeTxtF;
	protected JTextField valueTxtF;
	
	protected ImageButton addBtn;
	protected ImageButton addAnotherbtn;
	protected ImageButton doneBtn;
	
	protected JScrollPane scrollPane;
	protected GridBagConstraints c  = new GridBagConstraints();
	
	
	
	OptionsListener ol;
	ControlInterface control;
	PhotoDisplay photoDisplay;
	String filename;
	protected Insets tagInsets= new Insets(20,10,20,10);
	
	/**
	 * Constructor for the class addTagsPopUp
	 * @param control
	 * @param photoDisplay
	 * @param filename
	 */
	
	public addTagsPopUp(ControlInterface control, PhotoDisplay photoDisplay, String filename){
		
		ol = new OptionsListener();
		this.control = control;
		this.photoDisplay = photoDisplay;
		this.filename = filename;
		
		setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		super.setBackground(Color.BLACK);
		this.getRootPane().setBackground(Color.BLACK);
		this.getLayeredPane().setBackground(Color.BLACK);
		this.getContentPane().setBackground(Color.BLACK);
		this.setBackground(Color.BLACK);
		this.setPreferredSize(new Dimension(410,230));
		this.setLocationRelativeTo(null);
		this.setTitle("Add Tags");
		
		labelsPanel = new JPanel();
		labelsPanel.setOpaque(false);
		labelsPanel.setLayout(new BoxLayout(labelsPanel,BoxLayout.X_AXIS));
		
		titleLbl = new JLabel("Add Tags");
		titleLbl.setOpaque(false);
		titleLbl.setFont(Themes.titleFont);
		titleLbl.setForeground(Themes.themeColor);
		labelsPanel.add(titleLbl);
		
		tagsPanel = new JPanel(new GridLayout(0,2,2,10));
		tagsPanel.setOpaque(true);
		tagsPanel.setBackground(Color.black);
		tagsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		typeLbl = new JLabel("Tag Type");
		typeLbl.setOpaque(false);
		typeLbl.setFont(Themes.componentsFont);
		typeLbl.setForeground(Themes.themeColor);
		
		tagsPanel.add(typeLbl);
		
		valueLbl = new JLabel("Tag Value");
		valueLbl.setOpaque(false);
		valueLbl.setFont(Themes.componentsFont);
		valueLbl.setForeground(Themes.themeColor);
		
		tagsPanel.add(valueLbl);
		
		typeTxtF = new JTextField(10);
		typeTxtF.setFont(Themes.componentsFont);
		typeTxtF.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		tagsPanel.add(typeTxtF);
		
		valueTxtF = new JTextField(10);
		valueTxtF.setFont(Themes.componentsFont);
		valueTxtF.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		tagsPanel.add(valueTxtF);			

		scrollPane = new JScrollPane(tagsPanel);
		scrollPane.setOpaque(false);
		scrollPane.setBackground(Color.black);
		scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);		
		scrollPane.setPreferredSize(new Dimension(250,100));
		
		
		
		buttonsPanel = new JPanel();
		buttonsPanel.setOpaque(false);
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel,BoxLayout.X_AXIS));
		
		addAnotherbtn = new ImageButton(new ImageIcon("resources/addAnotherUP.jpg"));
		addAnotherbtn.setPressedIcon(new ImageIcon("resources/addAnotherDN.jpg"));
		addAnotherbtn.setActionCommand("add another");
		addAnotherbtn.addActionListener(ol);
		
		doneBtn = new ImageButton(new ImageIcon("resources/doneUP.jpg"));
		doneBtn.setPressedIcon(new ImageIcon("resources/doneDN.jpg"));
		doneBtn.setActionCommand("done");
		doneBtn.addActionListener(ol);
		
		buttonsPanel.add(new Box.Filler(new Dimension(20,0), new Dimension(20,0), new Dimension(20,0)));
		buttonsPanel.add(addAnotherbtn);
		buttonsPanel.add(new Box.Filler(new Dimension(20,0), new Dimension(20,0), new Dimension(20,0)));
		buttonsPanel.add(doneBtn);
		labelsPanel.add(new Box.Filler(new Dimension(20,0), new Dimension(20,0), new Dimension(20,0)));
		
		add(new Box.Filler(new Dimension(0,20), new Dimension(0,20), new Dimension(0,20)));
		add(labelsPanel);
		add(new Box.Filler(new Dimension(0,20), new Dimension(0,20), new Dimension(0,20)));
		add(scrollPane);
		add(new Box.Filler(new Dimension(0,20), new Dimension(0,20), new Dimension(0,20)));
		add(buttonsPanel);
		add(new Box.Filler(new Dimension(0,20), new Dimension(0,20), new Dimension(0,20)));
		
		pack();
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		
		addWindowListener(new WindowAdapter(){
			
			public void windowClosing(WindowEvent we){					
				addTagsPopUp.this.setVisible(false);				
			 }
		});
		
	}
	
	/**
	 * Options listener is the method for all buttons in this class. Explains what to do once the buttons have been clicked.	 
	 */
	protected class OptionsListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			String option = e.getActionCommand();
			
			if(option.equals("add another")){
				JTextField TxtF = new JTextField(10);
				TxtF.setFont(Themes.componentsFont);
				TxtF.setAlignmentX(Component.CENTER_ALIGNMENT);
				tagsPanel.add(TxtF);
				JTextField TxtF2 = new JTextField(10);
				TxtF2.setFont(Themes.componentsFont);
				TxtF2.setAlignmentX(Component.CENTER_ALIGNMENT);
				tagsPanel.add(TxtF2);
				tagsPanel.revalidate();
				int height = (int)tagsPanel.getPreferredSize().getHeight();
				scrollPane.getVerticalScrollBar().setValue(height+20);
			}
			else if(option.equals("done")){
				ArrayList<String> types = new ArrayList<String>();
				ArrayList<String> vals = new ArrayList<String>();
				boolean typeDone=false;
				for (Component c : tagsPanel.getComponents()) {
				    if (c instanceof JTextField) { 
				       if(!typeDone){
				    	   types.add(((JTextField) c).getText());
				    	   typeDone=true;
				       }
				       else{
				    	   vals.add(((JTextField) c).getText());
				    	   typeDone=false;
				       }
				    	   
				    }
				}
				
				for(int i=0;i<types.size();i++){
					if(!types.get(i).equals("")&&!vals.get(i).equals("")){
					boolean done = control.addTag(filename, types.get(i), vals.get(i));
					
					if(!done){						
						final JFrame error = new JFrame("Important Message!");
						JOptionPane.showMessageDialog(error, "Tag already exists for "+filename+" "+types.get(i)+":\""+vals.get(i)+"\"");
					}
					else{
						final JFrame error = new JFrame("Important Message!");
						JOptionPane.showMessageDialog(error, "Tag added:  "+types.get(i)+":\""+vals.get(i)+"\"");
					}
					}
				}
				addTagsPopUp.this.photoDisplay.save();
				addTagsPopUp.this.setVisible(false);
				
			}
		}
	}
	
}
