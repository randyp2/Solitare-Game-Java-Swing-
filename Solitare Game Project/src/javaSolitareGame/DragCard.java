package javaSolitareGame;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class DragCard extends JLabel{
	
	Card card;
	ImageIcon faceUpImg;
	ImageIcon currImg;
	int xPos;
	int yPos;
	boolean faceUp;
	
	MoveListener ml;
	

	DragCard(int xPos, int yPos){
		this.xPos = xPos;
		this.yPos = yPos;
		this.setBounds(this.xPos, this.yPos, Deck.WIDTH, Deck.HEIGHT);
		
		this.setVisible(true);
		
	}
	
	public void createMotionListener() {
		MoveListener ml = new MoveListener(this);
		this.addMouseListener(ml);
		this.addMouseMotionListener(ml);
	}
	
	public void setCard(Card card) {
		this.card = card;
		this.setIcon(new ImageIcon(card.getImg()));
		this.faceUpImg = new ImageIcon(card.getImg());
	}
	
	public void setFaceUp(boolean faceUp)  {
		this.faceUp = faceUp;
		
	}
	
	public void setImg(ImageIcon img) {
		this.currImg = img;
		this.setIcon(img);
	}
	
	public Card getCard() {
		return this.card;
	}
	
	public int getXPos() {
		return this.xPos;
	}
	
	public int getYPos() {
		return this.yPos;
	}
	
	public boolean getFaceUp() {
		return this.faceUp;
	}
	
	public ImageIcon getFaceUpImg() {
		return faceUpImg;
	}
}
