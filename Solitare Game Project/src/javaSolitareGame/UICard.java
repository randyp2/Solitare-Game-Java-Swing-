package javaSolitareGame;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/*
 * Class that is able to be removed from the deck to be dragged
 */
public class UICard extends JLabel{
	
	// Store values of its current card
	// Store cardLabel of its current card
	Card card;
	CardLabel displayCard;
	
	// Instantiate properties
	ImageIcon image;
	int WIDTH;
	int HEIGHT;
	Point imgCorner;
	Point startPt;
	ClickListener cl;
	
	// Initialize UI Card with its set properties
	UICard() {
		
		this.setBounds(0, 0, Deck.WIDTH, Deck.HEIGHT);
		this.setVisible(true); 
		
		imgCorner = new Point(0, 0);
		
		cl = new ClickListener(this);
		this.addMouseListener(cl);
	}
	
	// Set the card to the UI Card
	public void setCard(Card card) {
		this.card = card;
	}
	
	// Return value of the card
	public Card getCard() {
		return card;
	}
	
	// Return display card
	public CardLabel getDisplayCard() {
		return this.displayCard;
	}
	
	// Set image to the UI Card
	public void setImg(ImageIcon img) {
		
		this.image = img;
		this.setIcon(img);
		
		WIDTH = image.getIconWidth();
		HEIGHT = image.getIconHeight();
	}

	
	
	
}
