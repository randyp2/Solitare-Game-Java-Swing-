package javaSolitareGame;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class RightPanel extends JPanel{
	public static final int WIDTH = (int)(MyFrame.WIDTH / 7);
	public static final int HEIGHT = MyFrame.HEIGHT;
	
	
	private int offsetY; // Offset of y coord between piles
	public static StackPile[] stackPiles;
	/*
	 * Arrange stack pile contents in this order
	 * {
	 * 	Heart | Diamond | Clubs | Spade
	 *    {}      {}      {}      {},
	 *    
	 * }
	 */
	public static DragCard[][] stackPileContents; 
	public static int[][] stackPileCoords; // Coordinates for stack piles [xmin, xmax, ymin, ymax]
	
	
	/*
	 * Initialize properties for the right panel
	 * It will contains:
	 * The foundation piles were users can stack cards onto of the foundation according to the suite
	 * and in ascending order.
	 */
	RightPanel() {
		this.offsetY = 10;
		this.stackPiles = new StackPile[4];
		this.stackPileContents = new DragCard[13][4];
		this.stackPileCoords = new int[4][4];
		
		this.setBackground(new Color(0x228B22));
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setLayout(null);
		this.setVisible(true);
		
		
		displayStackPiles();
		initializeStackPileCoords();
	}
	
	public void displayStackPiles() {
		/*
		 * Make stack piles for each suit
		 */
		int xPos = (this.WIDTH / 2) - (Deck.WIDTH / 2);
		int yPos = this.HEIGHT / 90;
		
		System.out.println("\nHeight of right panel: " + this.HEIGHT);
		StackPile heartPile = new StackPile();
		heartPile.initializeBound(xPos, yPos, Deck.WIDTH, Deck.HEIGHT);
		heartPile.setSuitPile("H");
		this.stackPiles[0] = heartPile;
		yPos += (Deck.HEIGHT + offsetY);
		
		
		StackPile diamondPile = new StackPile();
		diamondPile.initializeBound(xPos, yPos, Deck.WIDTH, Deck.HEIGHT);
		diamondPile.setSuitPile("D");
		this.stackPiles[1] = diamondPile;
		yPos += (Deck.HEIGHT + offsetY);
		
		
		StackPile clubPile = new StackPile();
		clubPile.initializeBound(xPos, yPos, Deck.WIDTH, Deck.HEIGHT);
		clubPile.setSuitPile("C");
		this.stackPiles[2] = clubPile;
		yPos += (Deck.HEIGHT + offsetY);
		
		
		StackPile spadePile = new StackPile();
		spadePile.initializeBound(xPos, yPos, Deck.WIDTH, Deck.HEIGHT);
		spadePile.setSuitPile("S");
		this.stackPiles[3] = spadePile;
		yPos += (Deck.HEIGHT + offsetY);
		
		
		
		
		this.add(heartPile);
		this.add(diamondPile);
		this.add(clubPile);
		this.add(spadePile);
		
	}
	
	/*
	 * Initialize the stack pile coords
	 */
	public void initializeStackPileCoords() {
		int xMin = 962;
		int xMax = 1059;
		int yMin = this.HEIGHT / 90;
		int yMax = (this.HEIGHT / 90) + Deck.HEIGHT;
		
		for(int row = 0; row < stackPileCoords.length; row++) {
			
			stackPileCoords[row][0] = xMin; // Initialize the min X coord
			stackPileCoords[row][1] = xMax; // Initialize the max X coord
			stackPileCoords[row][2] = yMin; // Initialize the min Y coord
			stackPileCoords[row][3] = yMax; // Initialize the max Y coord
			
			yMin += (Deck.HEIGHT + offsetY);
			yMax += (Deck.HEIGHT + (offsetY / 2));
		}
		
		/*
		 * Print out the stack pile coords
		 */
		System.out.println("\nSTACK PILE COORDS:");
		for(int[] array: stackPileCoords) {
			System.out.println(Arrays.toString(array));
		}
		System.out.println();
	}
	
	/*
	 * Check if there are any valid stack piles for the drag card or drag pile to be placed under
	 */
	public static int checkValidity(Card card) {
		
		int validPile = -1;
		boolean valid = true;
		
			outerloop:
			for(int col = 0; col < stackPileContents[0].length; col++) {
				
				for(int row = 0; row < stackPileContents.length; row++) {
					DragCard firstDragCard = stackPileContents[0][col]; // Made specifically to check for empty piles
					DragCard lastDragCard = stackPileContents[row][col];
					DragCard nextDragCard = null;
					
					if(row != 12) { // If the row is not the last row
						nextDragCard = stackPileContents[row + 1][col];
					}else if(row == 12 && stackPileContents[row][col] != null) {
						break outerloop;
					}
					
					// If there is an empty pile then a card of value 13, or a King, can be placed down
					System.out.println();
					System.out.println("First DragCard: " + firstDragCard);
					System.out.println("Card value: " + card.getValue());
					System.out.println("Card suit: " + card.getSuit());
					System.out.println("Stack pile rank: " + stackPiles[col].getSuitPile());
					System.out.println();
					
					// Check if the card is a king and there is one last slot open in the stack pile
					if(row == 11 && nextDragCard == null && card.getValue() == 13 && card.getSuit().equals(stackPiles[col].getSuitPile())) {
						validPile = col;
						break outerloop;
						
					}else if(row == 11 && nextDragCard == null && card.getValue() == 13 && !card.getSuit().equals(stackPiles[col].getSuitPile())) {
						continue outerloop;
					}
					
					// Check if the card is an ace and there is an empty pile
					if(card.getValue() == 1 && firstDragCard == null && card.getSuit().equals(stackPiles[col].getSuitPile())) {
						validPile = col;
						break outerloop;
					}else if(card.getValue() == 1 && firstDragCard == null && !card.getSuit().equals(stackPiles[col].getSuitPile())) {
						System.out.println("\nTHE CARD WAS AN ACE BUT THIS PILE DOESNT HAVE THE SAME SUIT");
						continue outerloop;
					}else if(card.getValue() == 1 && firstDragCard != null && card.getSuit().equals(stackPiles[col].getSuitPile())) {
						System.out.println("\nTHE CARD WAS AN ACE BUT THERE IS ALREADY A CARD PRESENT IN THE STACK PILE");
						break outerloop;
					}
					
					// If the current drag card is a null value and the card is not an ace break the loop
					if(lastDragCard == null && card.getValue() != 1) break;
					
					// Compare card in waste pile or current piles to stack piles
					if(nextDragCard == null && row != 12) { // If next drag card is null, then the current drag card is the last card in the pile, skip over piles with 13 cards already
						
						System.out.println("Comparing current drag card: " + card + " With last drag card: " + lastDragCard.getCard());
						if(!lastDragCard.getCard().getSuit().equals(card.getSuit()) || (lastDragCard.getCard().getValue() + 1) != card.getValue()) { // Check validitiy of last card
								
							valid = false;
							
							System.out.println("No matches in pile: " + col);
							continue outerloop;
						}else { // If valid set the valid pile and valid variable
							valid = true;
							validPile = col;
							break outerloop;
						}
					}
					
					
				}
			}
		
		System.out.println("\nVALID STACK PILE: " + validPile);
		return validPile;
	}
	
	/*
	 * Check if the released point of mouse is a valid stack pile
	 */
	public static int validStackPile(int x, int y) {
		int stackPile = 0;
		for(int[] arr :  stackPileCoords) {
			int xMin = arr[0], xMax = arr[1], yMin = arr[2], yMax = arr[3];
			
			if((x >= xMin && x <= xMax) && (y >= yMin && y <= yMax)) {
				return stackPile;
			}else {
				stackPile++;
			}
		}
		
		return -1;
	}
	
	/*
	 * Check if the suggested stack pile placement is valid
	 */
	public static boolean checkPlacement(int suggestedPile) {
		
		DragCard firstCard = stackPileContents[0][suggestedPile]; // Made specifically for checking for empty pile
		DragCard lastCard = null;
		int lastCardRow = 0;
		
		// Check if the pile is empty
		// Checking for drag card not drag pile
		// Then checking for drag pile not drag card
		if(MidPanel.currDragCard != null && MidPanel.dragPileContents[0] == null) {
			if(firstCard == null && MidPanel.currDragCard.getCard().getValue() == 1 && MidPanel.currDragCard.getCard().getSuit().equals(stackPiles[suggestedPile].getSuitPile())) {
				return true;
			}
			
		}else if(MidPanel.currDragCard == null && MidPanel.dragPileContents[0] != null) {
			
			if(firstCard == null && MidPanel.dragPileContents[0].getCard().getValue() == 1 && MidPanel.dragPileContents[0].getCard().getSuit().equals(stackPiles[suggestedPile].getSuitPile())) {
				return true;
			}
		}
		
		// Look for the last cards position and value in the suggested pile
		for(int row = 0; row < stackPileContents.length; row++) {
			DragCard card = stackPileContents[row][suggestedPile];
			
			
			if(row != 0 && card == null) {
				lastCardRow = row - 1;
				lastCard = stackPileContents[lastCardRow][suggestedPile];
				break;
			}else if(row == 12 && card != null) { // The stack pile is full
				return false;
			}
		}
		
		if(lastCard == null)return false;
		
		// Checking for drag card not drag pile first
		if(MidPanel.currDragCard != null && MidPanel.dragPileContents[0] == null) {
			
			if(!lastCard.getCard().getSuit().equals(MidPanel.currDragCard.getCard().getSuit()) && lastCard.getCard().getValue() + 1 != MidPanel.currDragCard.getCard().getValue()) {
				return false;
			}
		} if(MidPanel.currDragCard == null && MidPanel.dragPileContents[0] != null) {
			
			if(!lastCard.getCard().getSuit().equals(MidPanel.dragPileContents[0].getCard().getSuit()) && lastCard.getCard().getValue() + 1 != MidPanel.dragPileContents[0].getCard().getValue()) {
				return false;
			}
		}else if(MidPanel.currDragCard == null && MidPanel.dragPileContents[0] == null) {
			System.out.println("\nBOTH DRAG CARD AND DRAG PILE CANNOT BE MOVED AT THE SAME TIME!!!");
		}
		
		
		
		return true;
	}
	
	public void addToPile(int pileIdx) {
		// X and Y position of the drag card
		int xPos = 0;
		int yPos = 0;
		int rowPos = 0; // Index of row of where the last card is
		
		DragCard card = null; // Card to add to stack pile
		
		// Find the last card in the stack pile
		for(int row = 0; row < stackPileContents.length; row++) {
			
			if(stackPileContents[row][pileIdx] == null) {
				rowPos = row;
				break;
			}
		}
		
		// Create a DragCard to be placed
		// Check if its a drag card being placed or a drag pile being placed
		if(MidPanel.currDragCard != null && MidPanel.currDragPile == null) {
			card = new DragCard(xPos, yPos);
			card.setCard(MidPanel.currDragCard.getCard());
			card.setFaceUp(true);
			card.setVisible(true);
			stackPileContents[rowPos][pileIdx] = card;
			
			stackPiles[pileIdx].addLayer(card); // Add to the stack pile
			
			// Remove the drag card from the midpanel
			MyFrame.midPanel.removeCard("card");
			
			
		}else if(MidPanel.currDragCard == null && MidPanel.currDragPile != null) {
			card = new DragCard(xPos, yPos);
			card.setCard(MidPanel.dragPileContents[0].getCard());
			card.setFaceUp(true);
			card.setVisible(true);
			stackPileContents[rowPos][pileIdx] = card;
			
			stackPiles[pileIdx].addLayer(card); // Add to the stack pile
			
			// Remove the drag pile from the midpanel
			MyFrame.midPanel.removeCard("pile");
		}
		
		
		
		
		
		
	}
}
