package cs213.photoAlbum.simpleView;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import cs213.photoAlbum.control.ControlInterface;
import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;

/**
 * This class represents the thumbnail for each of the photos in an album. It shows the name of the file and contains the deletion button which is
 * shown when edit mode is activated.
 * @author Roberto Ronderos Botero
 */
public class PhotoInfo extends JPanel implements MouseListener{

	private static final long serialVersionUID = 1L;
	PhotosScreen photosScreen;
	public String photoName;
	
	protected ImagePanel picture;
	protected ImageButton deleteBtn;
	
	protected JLabel photoNameLbl;
	protected JPanel deletePanel;
	protected BoxLayout bl = new BoxLayout(this,BoxLayout.Y_AXIS);
	protected GridBagConstraints c = new GridBagConstraints();
	private final Dimension photoInfoSize = new Dimension(160,178);
	protected final String thumbnailsPath = "thumbnails/";
	protected final String picturesPath = "pictures/";
	private boolean mEntered = false;
	private boolean mClicked = false;
	private boolean btnVisible = false;
	protected OptionsListener ol;
	protected ImagePanel cover;	
	ControlInterface control;
	Album album;
	PhotoDisplay photoDisplay;
	Photo photo;
	
	/**
	 * Constructor for the class PhotoInfo
	 * @param album
	 * @param photo
	 * @param control
	 * @param photosScreen
	 * @throws Exception
	 */
	public PhotoInfo(Album album,Photo photo, ControlInterface control, PhotosScreen photosScreen) throws Exception{
		
		this.photo=photo;
		this.album= album;
		this.control = control;
		ol = new OptionsListener();
		this.photosScreen = photosScreen;
		setLayout(bl);
		
		photoName = photo.getFilename();
		photoNameLbl = new JLabel(photoName);
		
		String file = photoName;
		String[] fileInfo = new String[2];
		fileInfo=file.split("\\.");
		String fileName = fileInfo[0];
		String fileExtension = fileInfo[1];
		
		
		transform(picturesPath+fileName+"."+fileExtension,thumbnailsPath+fileName+"thumb."+fileExtension,140,120,10);
		
		cover = new ImagePanel(new ImageIcon(thumbnailsPath+fileName+"thumb."+fileExtension).getImage());
		cover.setAlignmentY(Component.CENTER_ALIGNMENT);
		cover.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		this.setOpaque(false);
		photoNameLbl.setForeground(Color.WHITE);
		photoNameLbl.setOpaque(false);
		photoNameLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		deleteBtn = new ImageButton("resources/deleteBtnUP.png");
		deleteBtn.setPreferredSize(new Dimension(20,20));
		deleteBtn.addActionListener(ol);
		deleteBtn.setPressedIcon(new ImageIcon("resources/deleteBtnDN.png"));
		deleteBtn.setOpaque(false);
		deleteBtn.setContentAreaFilled(false);
		deleteBtn.setBorderPainted(false);
		deleteBtn.setActionCommand("deleteAlbum");
		
		deletePanel = new JPanel();
		deletePanel.setOpaque(false);
		deletePanel.setLayout(new BoxLayout(deletePanel, BoxLayout.LINE_AXIS));
		deletePanel.add(Box.createHorizontalGlue());
		deletePanel.add(deleteBtn);
		deletePanel.setVisible(false);
		
		add(deletePanel);
		add(cover);
		add(photoNameLbl);
		this.setPreferredSize(photoInfoSize);
		this.setAlignmentY(Component.CENTER_ALIGNMENT);
		this.setBorder(new EmptyBorder(10, 10, 10, 10));		
		
		addMouseListener(this);
	}	
	
	/**
	 * Second constructor for the class PhotoInfo
	 * @param photo
	 * @param control
	 * @param photosScreen
	 * @throws Exception
	 */
	public PhotoInfo(Photo photo, ControlInterface control,PhotosScreen photosScreen) throws Exception {
		this.album= null;
		this.control = control;
		this.photo = photo;
		this.control = control;
		ol = new OptionsListener();
		this.photosScreen = photosScreen;
		setLayout(bl);
		
		photoName = photo.getFilename();
		photoNameLbl = new JLabel(photoName);
		
		String file = photoName;
		String[] fileInfo = new String[2];
		fileInfo=file.split("\\.");
		String fileName = fileInfo[0];
		String fileExtension = fileInfo[1];
		
		
		transform(picturesPath+fileName+"."+fileExtension,thumbnailsPath+fileName+"thumb."+fileExtension,140,120,10);
		
		cover = new ImagePanel(new ImageIcon(thumbnailsPath+fileName+"thumb."+fileExtension).getImage());
		cover.setAlignmentY(Component.CENTER_ALIGNMENT);
		cover.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		this.setOpaque(false);
		photoNameLbl.setForeground(Color.WHITE);
		photoNameLbl.setOpaque(false);
		photoNameLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		deleteBtn = new ImageButton("resources/deleteBtnUP.png");
		deleteBtn.setPreferredSize(new Dimension(20,20));
		deleteBtn.addActionListener(ol);
		deleteBtn.setPressedIcon(new ImageIcon("resources/deleteBtnDN.png"));
		deleteBtn.setOpaque(false);
		deleteBtn.setContentAreaFilled(false);
		deleteBtn.setBorderPainted(false);
		deleteBtn.setActionCommand("deleteAlbum");
		
		deletePanel = new JPanel();
		deletePanel.setOpaque(false);
		deletePanel.setLayout(new BoxLayout(deletePanel, BoxLayout.LINE_AXIS));
		deletePanel.add(Box.createHorizontalGlue());
		deletePanel.add(deleteBtn);
		deletePanel.setVisible(false);
		
		add(deletePanel);
		add(cover);
		add(photoNameLbl);
		this.setPreferredSize(photoInfoSize);
		this.setAlignmentY(Component.CENTER_ALIGNMENT);
		this.setBorder(new EmptyBorder(10, 10, 10, 10));		
		
		this.addMouseListener(new MouseAdapter() { 
			 @Override
				public void mouseEntered(MouseEvent arg0) {
					mEntered = true;
					mClicked = false;
					PhotoInfo.this.paintComponent(PhotoInfo.super.getGraphics());
					PhotoInfo.this.repaint();
					
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
					mEntered = false;
					mClicked = false;
					PhotoInfo.this.paintComponent(PhotoInfo.super.getGraphics());		
					PhotoInfo.this.repaint();
					
				}

				@Override
				public void mousePressed(MouseEvent arg0) {		
					mEntered = false;
					mClicked = true;
					PhotoInfo.this.paintComponent(PhotoInfo.super.getGraphics());
					PhotoInfo.this.repaint();
					
				}

				@Override
				public void mouseReleased(MouseEvent arg0) {
					mEntered = true;
					mClicked = false;
					PhotoInfo.this.paintComponent(PhotoInfo.super.getGraphics());
					PhotoInfo.this.repaint();
					
				}
				@Override
				public void mouseClicked(MouseEvent arg0) {
					photoDisplay = new PhotoDisplay(PhotoInfo.this.photo, PhotoInfo.this.photosScreen,PhotoInfo.this.control);
					PhotoInfo.this.photoDisplay.setVisible(true);
					PhotoInfo.this.photosScreen.setVisible(false);
					
				}
	          }); 
	}

	/**
	 * Shows the delete button of a picture when the edit mode is activated.
	 */
	public void showHideDeleteBtn(){
		if(!btnVisible){
			deletePanel.setVisible(true);
			this.repaint();
			btnVisible=true;
		}
		else{
			deletePanel.setVisible(false);
			this.repaint();
			btnVisible=false;
		}
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
	
	  public void transform(String originalFile, String thumbnailFile, int thumbWidth, int thumbHeight, int quality) throws Exception 
	  {
		  try{
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
		  catch(Exception e){
			System.out.println("Image not found");  
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
		@Override
		public void mouseClicked(MouseEvent arg0) {
			photoDisplay = new PhotoDisplay(PhotoInfo.this.photo, PhotoInfo.this.photosScreen,PhotoInfo.this.control, PhotoInfo.this.album.getAlbumName());
			PhotoInfo.this.photoDisplay.setVisible(true);
			PhotoInfo.this.photosScreen.setVisible(false);
			
		}
	
	protected class OptionsListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			try {
				PhotoInfo.this.photosScreen.deletePhoto(PhotoInfo.this.photoName);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	
}
