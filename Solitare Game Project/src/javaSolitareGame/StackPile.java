package javaSolitareGame;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class StackPile extends JLayeredPane{
	
	int layeredIndex; // Keep track of the number of layers in the pile
	String suitPile;
	
	StackPile() {
		this.layeredIndex = 0;
		
	}
	
	// Initialize bounds of the layered panel
	public void initializeBound(int x, int y, int width, int height) {
		this.setBounds(x, y, width, height);
		this.setVisible(true);
			
	}
		
	// Set what type of stack pile this is
	public void setSuitPile(String suit) {
		this.suitPile = suit;
		
		switch(suit) {
		case "H":
			
			System.out.println("Making heart stack pile!");
			
			JLabel heartDefaultLayer = new JLabel();
			heartDefaultLayer.setBounds(0, 0, Deck.WIDTH, Deck.HEIGHT);
			heartDefaultLayer.setIcon(new ImageIcon("Ace Card Pile (Heart).png"));
			heartDefaultLayer.setVisible(true);
			
			this.addLayer(heartDefaultLayer);
			
			break;
			
		case "D":
			
			System.out.println("Making diamond stack pile!");
			
			JLabel diamondDefaultLayer = new JLabel();
			diamondDefaultLayer.setBounds(0, 0, Deck.WIDTH, Deck.HEIGHT);
			diamondDefaultLayer.setIcon(new ImageIcon("Ace Card Pile (Diamonds).png"));
			diamondDefaultLayer.setVisible(true);
			
			this.addLayer(diamondDefaultLayer);
			
			break;
		
		case "C":
			
			System.out.println("Making club stack pile!");
			
			JLabel clubDefaultLayer = new JLabel();
			clubDefaultLayer.setBounds(0, 0, Deck.WIDTH, Deck.HEIGHT);
			clubDefaultLayer.setIcon(new ImageIcon("Ace Card Pile (Club).png"));
			clubDefaultLayer.setVisible(true);
			
			this.addLayer(clubDefaultLayer);
			
			break;
		
		case "S":
			
			System.out.println("Making spades stack pile!");
			
			JLabel spadeDefaultLayer = new JLabel();
			spadeDefaultLayer.setBounds(0, 0, Deck.WIDTH, Deck.HEIGHT);
			spadeDefaultLayer.setIcon(new ImageIcon("Ace Card Pile (Spades).png"));
			spadeDefaultLayer.setVisible(true);
			
			this.addLayer(spadeDefaultLayer);
			
			break;
			
		default: 
			System.out.println("ERROR MAKING STACK PILE!");
			
		}
	}
	
	// Return the suit type
	public String getSuitPile() {
		return this.suitPile;
	}
	
	// Add new layers
	public void addLayer(JLabel card) {
		this.add(card, Integer.valueOf(layeredIndex));
		layeredIndex += 1;
	}

}
