package javaSolitareGame;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Card {
	
	// Properties for each card
	private String suit;
	private String rank;
	private int value;
	private String color;
	private BufferedImage img;
	private BufferedImage backImg;
	
	

	/*
	 * Initialize the properties of a card: value, suite, rank, img
	 */
	Card(String suit, String rank, int value, String color, BufferedImage img, BufferedImage backImg) {
		this.suit = suit;
		this.rank = rank;
		this.value = value;
		this.color = color;
		this.img = img;
		this.backImg = backImg;
	}
	
	// Return the suit
	public String getSuit() {
		return suit;
	}
	
	// Return the rank
	public String getRank() {
		return rank;
	}
	
	// Return the value of the card
	public int getValue() {
		return value;
	}
	
	// Return the color of the card
	public String getColor() {
		return color;
	}
	
	// Return the img of the card
	public BufferedImage getImg() {
		return img;
	}
	
	// Return the back of the card img
	public BufferedImage getBackImg() {
		return backImg;
	}
	
	// Return string value of each card
	public String toString() {
		return "Rank: " + rank + " Suit: " + suit + " Value: " + value + " Color: " + color;
	}
}
