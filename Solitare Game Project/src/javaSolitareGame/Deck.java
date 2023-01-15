package javaSolitareGame;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Deck {
	public static final int WIDTH = 112; //1574px  width of the deck of cards image
	public static final int HEIGHT = 158; // 658px height of the deck of cards image
	
	// Store ranks and suits of each card
	private String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
	private String[] suits = {"D", "H", "S", "C"};
	
	/*
	 * It will contain:
	 * Array list of card objects
	 * Shuffle method - Rearranges the order of the deck
	 * Deal method - Removes the and returns the first card in the deck or the 0th index
	 */
	
	ArrayList<Card> deckOfCards; // List for all cards to be stored in 
	
	Deck() throws IOException {
		
		deckOfCards = new ArrayList<>(); 
		String color = "";
		
		// Info to extract each card from the deck of cards image
		// Extracting the images
		BufferedImage bigImage = ImageIO.read(new File("deck img(2).png"));
		BufferedImage tempImage;		
		
		for(int suit = 0; suit < suits.length; suit++) {
			
			for(int rank = 0; rank < ranks.length; rank++) {
				
				// Set either red or black color
				if(suit < 2) {
					color = "red";
				}else {
					color = "black";
				}
				//Extract the subimage 
				tempImage = bigImage.getSubimage(
						(rank*WIDTH) + ((rank*9) + 4),  // Starting x coord
						suit * HEIGHT + (suit * 8),  // Starting y coord
						WIDTH,  // Width of image
						HEIGHT); // Height of image
						
				// Create each individual card
				deckOfCards.add(new Card(
						suits[suit], // Calls the suit array to get the name of the suit
						ranks[rank], // Calls the rank array to get the name of the rank
						rank + 1, // Sets the value of the card
						color, // Sets the color of the card
						tempImage, // Sets the image of the card
						ImageIO.read(new File("back of playing card.png")))); // Set the image of the back of the card
			}
			
		}
	}
	
	
	/*
	 * Display the deck in the console to ensure it has been added
	 */
	public void displayDeck() {
		for(Card card : deckOfCards) {
			System.out.println(card);
		}
	}
	
	/*
	 * Get the top of the card to add it to the mid panel
	 * Not used in displaying the top card
	 */
	public Card getTopCard() {
		return deckOfCards.remove(0);
	}
	
	/*
	 * Get the first card in the deck, and then remove it from the deck
	 * Create a CardLabel instance of the card then physically add it to the leftpanel
	 */
	public CardLabel drawCard() {

		CardLabel newCard = new CardLabel();
		newCard.setCard(deckOfCards.get(0));
		newCard.setImg(deckOfCards.remove(0).getImg());
		
		MyFrame.leftPanel.add(newCard);
		
		return newCard;
	}
	
	// Shuffle the deck
	public void shuffle() {
		for(int i = 0; i < deckOfCards.size(); i++) {
			int ranNum = (int)((Math.random() * (deckOfCards.size() - 1)));
			Card x = deckOfCards.get(i);
			Card y = deckOfCards.get(ranNum);
			
			// Swap the cards
			deckOfCards.set(i, y);
			deckOfCards.set(ranNum, x);
			
		}
	}
	
	// Return the deck of cards
	public ArrayList<Card> getDeckOfCards() {
		return deckOfCards;
	}
}
