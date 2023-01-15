package javaSolitareGame;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class CardLabel extends JLabel implements ActionListener{
	ImageIcon image; // The image of the card label
	
	private int xVelocity; // Amount of pixels the x position will move
	private int yVelocity; // Amount of pixels the y position will move
	private int x; // Current x position
	private int y; // Current y position
	private int yEndPt; // Y end point
	Timer timer; // Initialize a new timer
	
	Card card; // Instantiate a card to the card label
	
	CardLabel() {
		
		// Initialize variables
		this.xVelocity = 1;
		this.yVelocity = 1;
		this.x = (LeftPanel.WIDTH / 2) - (Deck.WIDTH / 2);
		this.y = LeftPanel.HEIGHT / 10;
		this.yEndPt = (LeftPanel.HEIGHT / 2);
		
		this.setBounds(x, y, Deck.WIDTH, Deck.HEIGHT); // Set initial position the same as deck label position
		this.setVisible(false); // Set it automatically to invisible
		
		// Initialize timer
		// Calls action performed method every 5ms
		timer = new Timer(5, this); 
	}
	
	// Set an image for the card label
	public void setImg(BufferedImage img) {
		
		// Set the icon image of the card
		this.setIcon(new ImageIcon(img));
		image = new ImageIcon(img);
	}
	
	// Set visibility to true and begin timer
	public void setVisible() {
		
		this.setVisible(true);
		timer.start();
	}
	
	
	// Initialize a card to the card label
	public void setCard(Card card) {
		this.card = card;
	}
	
	// Return the card that is initialized to this card label
	public Card getCard() {
		return card;
	}
	
	// Return the image of this card
	public ImageIcon getImg() {
		return this.image;
	}
	
	// Animate the cardLabel to move down to wastePile
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// Stop timer once y reaches the waste pile
		if(y == yEndPt) {
			
			timer.stop(); // Stop the timer 
			y = LeftPanel.HEIGHT / 10; // Reset the y coordinate
			
			// Create an instance of a UI card
			UICard uiCard = new UICard();
			uiCard.setCard(card);
			uiCard.setImg(image);
			
			// Add the card to the Jlayered pane
			LeftPanel.wastePile.add(uiCard, Integer.valueOf(LeftPanel.indexLayer));
			LeftPanel.indexLayer+=1;
			return;
		}
		
		// Increment y by 10 each time
		y += 10;
		
		// Update the current location of the label
		this.setLocation(x, y);
		
	}
}
