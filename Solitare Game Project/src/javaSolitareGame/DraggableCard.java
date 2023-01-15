package javaSolitareGame;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class DraggableCard extends JLabel{
	
	/*
	 * Class to drag the card in the midpanel
	 */
	Card card; // Create card values for draggable card
	ImageIcon img = new ImageIcon(); // Img for draggable card
	int xPos; // X pos
	int yPos; // Y pos
	boolean faceUp; // Whether the card is face up or not
	
	MoveListener ml; //Create mouse listener and motion listener
	 
	DraggableCard(int xPos, int yPos) {
		
		this.xPos = xPos; //Set x pos
		this.yPos = yPos; // Set y pos
		this.setBounds(xPos, yPos, Deck.WIDTH, Deck.HEIGHT); // Set the bounds, width, and height
		this.setVisible(true);
		
				
	}
	
	
	
	
	
	// Create a motion listener for this draggable card 
	
	public void createMotionListener() {
		ml = new MoveListener(this); 
		
		
		this.addMouseListener(ml);
		this.addMouseMotionListener(ml);
	}
	
	// Return the current card value it contains
	public Card getCard() {
		return card;
	}
	
	// Return the x coord 
	public int getX() {
		return xPos;
	}
	
	// Return the y coord
	public int getY() {
		return yPos;
	}
	
	// Set the card value to its parameter
	public void setCard(Card card) {
		System.out.println("Card is: " + card);
		this.card = card;
		this.setIcon(new ImageIcon(card.getImg()));
		
	}
	
	// Set the img to the card img or back img
	public void setImg(ImageIcon img) {
		this.img = img;
		this.setIcon(img);
	}
	
	// Set whether the card is face up
	public void setFaceUp(boolean faceUp) {
		this.faceUp = faceUp;
	}
	
	// Return whether the card is facing up
	public boolean getFaceUp() {
		return this.faceUp;
	}
	
	public void setVisible() {
		this.setVisible(true); 
	}
	// Set the bounds of the card again
	/*
	public void initializeBounds(int offset) {
		yPos += offset;
		this.setBounds(xPos, yPos, Deck.WIDTH, Deck.HEIGHT);
	}*/
	
	
}
