package cs213.photoAlbum.simpleView;

import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * This class is used to be able to personalize the buttons of the program.
 * @author Roberto Ronderos Botero
 *
 */

class ImageButton extends JButton {

	private static final long serialVersionUID = 1L;

	public ImageButton(String img) {
	    this(new ImageIcon(img));
	  }

	  public ImageButton(ImageIcon icon) {
	    setIcon(icon);
	    setMargin(new Insets(0, 0, 0, 0));
	    setIconTextGap(0);
	    setBorderPainted(false);
	    setBorder(null);
	    setText(null);
	    setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
	  }

	}