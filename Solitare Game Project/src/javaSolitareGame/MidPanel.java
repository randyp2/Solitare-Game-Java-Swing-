 package javaSolitareGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.Arrays;

public class MidPanel extends JPanel{
	/*
	 * Initialize properties for the middle panel
	 * It will contain:
	 * Piles of cards where the user drags cards into the middle panel to create a 
	 * stack of cards.
	 */
	
	//public static MoveListener ml; // Initailizes a move listener to drag that cards
	public static DragCard[][] pileArray; // Initialize 2d Array for table piles
	public static int HEIGHT = MyFrame.HEIGHT; // Initialize height const
	public static int WIDTH2; // Initialize width const
	public int width; // Initialize width const
	
	private int lpWidth; // Layered pane width
	private int lpHeight; // Layered pane height
	private int x; // X Pos of the layered pane
	private int y; // Y Pos of the layered pane
	private int offsetX; // X coord offset for layered panes
	
	private int xPos; // X pos of card in the layered pane
	private int yPos; // Y pos of card in the layered pane
	private int offsetY; // Y coord offset for each additional card in a layered pane
	
	
	public static int[][] jlpCoords; // Coordinates of the j layerd pane: [xmin, xmax, ymin, ymax]
	public static int[] newPileCoords; // Coordinate for when a new pile shows up: [xmin, xmax, ymin, ymax]
	
	public static DraggableLayeredPane[] jlpArr; // Array consisting of all j layered panes
	public static boolean draggableCardPressed = false;
	
	public static DragCard currDragCard; // Keep track of the current drag card on the midpanel
	public static DraggableLayeredPane currDragPile; // Keep track of the current drag pile on the midpanel
	public static DragCard[] dragPileContents; // Keep track of whats in the drag pile
	
	public static JLabel score; // JLabel of score
	/*
	 * JLP coords will be in form of:
	 * {x min, x max, y min, y max}
	 */
	
	
	MidPanel() throws IOException {
		
		//ml = new MoveListener();
		this.pileArray = new DragCard[20][7]; 
		this.jlpArr = new DraggableLayeredPane[pileArray[0].length]; // Used to be pileArray.length
		this.width = this.getWidth();
		this.WIDTH2 = width;
		
		// Variables for the layerd panes
		this.lpWidth = Deck.WIDTH;
		this.lpHeight = Deck.HEIGHT;
		this.x = (LeftPanel.WIDTH / 2) - (Deck.WIDTH / 2);
		this.y = LeftPanel.HEIGHT / 10;
		this.offsetX = Deck.WIDTH + 10;
		
		// Variables for the drag cards in the layered panes
		this.xPos = 0;
		this.yPos = 0;
		this.offsetY = 25;
		
		this.jlpCoords = new int[7][4];
		this.dragPileContents = new DragCard[13]; 
		
		this.setBackground(new Color(0x7FFF00));
		this.setLayout(null);
		this.setOpaque(true);
		setVisible();
		
		displayPiles();
		displayScore();
	}
	
	
	
	/*
	 * Populates the pile array and displays the card labels onto the panel
	 */
	public void displayPiles() {
				
		// Initialize 7 J layered panels across the game board
		for(int lP = 0; lP < pileArray[0].length; lP++) {
			DraggableLayeredPane dlp = new DraggableLayeredPane();
			dlp.initializeBound(x, y, lpWidth, lpHeight);
			this.add(dlp);
			jlpArr[lP] = dlp; // Populate the jlp array
			
			jlpCoords[lP] = new int[] {x, x + Deck.WIDTH, y, y + lpHeight}; // Pouplate the jlp coords
			
			x += offsetX; // Increment the x pos of each layered pane
			lpHeight += 25; // Increment the height of the draggable layered pane
			
			
		}
		
		/*
		 * Print out the drag pile array coords
		 */
		System.out.println("\nARRAY COORDS");
		for(int[] arr : jlpCoords) {
			System.out.println(Arrays.toString(arr));
		}
		System.out.println();
		
		
		int numberOfCards = 1;
		
		for(int col = 0; col < pileArray[0].length; col++) {
			this.yPos = 0;
			for(int row = 0; row < numberOfCards; row++) {
				
				
				//If col is not first card in pile, then increment the Y value
				if(row != 0) {
					
					yPos += offsetY; // Increment the y pos of card
					
					DragCard currCard = new DragCard(xPos, yPos); // Create a draggable card
					currCard.setCard(MyFrame.deck.getTopCard()); // Set card values to the draggable card
					currCard.setFaceUp(true);
										
					// If the card is not the last card in the pile
					if(row != numberOfCards - 1) {
						currCard.setImg(new ImageIcon(currCard.getCard().getBackImg())); // Set to back img if not the 
						currCard.setFaceUp(false);
					}
					pileArray[row][col] = currCard; // Populate the pile array card
					jlpArr[col].addLayer(pileArray[row][col]); // Add it to the pile
					
				}else {
					
					DragCard currCard = new DragCard(xPos, yPos); // Create a draggable card label
					currCard.setCard(MyFrame.deck.getTopCard()); // Get the card on the top of the deck
					currCard.setFaceUp(true);
					
					System.out.println("Adding card: " + currCard.getCard());
					
					if(row != numberOfCards - 1) {
						currCard.setImg(new ImageIcon(currCard.getCard().getBackImg())); // If its not the last card in the pile then the img is back
						currCard.setFaceUp(false);
					}
					pileArray[row][col] = currCard; // Populate the pile array with draggable card labels
					jlpArr[col].addLayer(pileArray[row][col]); // Add card to jlp to make it visisble
				}
				
			}
			
			numberOfCards++; // Increment number of cards in the pile
		}
			
		
		/*
		 * Print out the pile array
		 */
		System.out.println("\nPILE ARRAY: ");
		for(int col = 0; col < pileArray[0].length; col++) {
			System.out.print("[");
			for(int row = 0; row < pileArray.length; row++) {
				DragCard card = pileArray[row][col];
				if(card != null) {
					System.out.print(card.getCard() + ", ");
				}else {
					System.out.print("null, ");
				}
			}
			System.out.println("]");
		}
		
		
	}
	
	/*
	 * Display the score of the user at the top right corner of MidPanel
	 */
	public void displayScore() {
		
		// Add score to top right of mid panel "Score: 5"
		score = new JLabel();
		score.setText("Score: " + MyFrame.score);
		score.setBounds(this.getWidth() + 10, 0, 1000, 100);
		score.setForeground(Color.gray);
		score.setFont(new Font("MV Boli", Font.PLAIN, 20)); // Font family, font style, font size
		
		this.add(score);
	}
	
	public static void updateScore() {
		
		// Set the score text to the new score
		score.setText("Score: " + MyFrame.score);
	}
	
	
	/*
	 * Checks for conditions of bottom cards of the pile:
	 * 	Not the same color
	 * 	Value must be one higher
	 * 	Card must be faced up
	 */
	public static int checkValidity(Card card) {
		int colIdx = 0;
		
		
		int validPile = -1;
		
		// Iterate from column major order
		outerloop:
		for(int col = 0; col < pileArray[0].length; col++) {
			
			for(int row = 0; row < pileArray.length; row++) {
				DragCard firstDragCard = pileArray[0][col]; // Made specifically to check for empty piles
				DragCard lastDragCard = pileArray[row][col];
				DragCard nextDragCard = null;
				
				if(row != 19) { // If the row is not the last row
					nextDragCard = pileArray[row + 1][col];
				}else if(row == 19 && pileArray[row][col] != null) { // If the last row is populated theres no more space to put a card
					break outerloop;
				}
				
				// If there is an empty pile then a card of value 13, or a King, can be placed down
				if(card.getValue() == 13 && firstDragCard == null) {
					validPile = col;
					break outerloop;
				}else if(card.getValue() == 13 && firstDragCard != null) {
					continue outerloop;
				}
								
				if(nextDragCard == null) { // If next drag card is null, then the current drag card is the last card in the pile, skip over piles with 13 cards already
					
					if(lastDragCard == null) continue outerloop; // This means that this is an empty pile
					if(lastDragCard.getCard().getValue() == 1) continue outerloop; // This means that the last card is an Ace which no more cards can be placed under it
					
					System.out.println("Comparing current drag card: " + card + " With last drag card: " + lastDragCard.getCard());
					if(!lastDragCard.getFaceUp() || (lastDragCard.getCard().getValue() - 1) != card.getValue() // Check validitiy of last card
							|| lastDragCard.getCard().getColor().equals(card.getColor())) {
						System.out.println("No matches in pile: " + col);
						validPile = -1;
						continue outerloop;
						
					}else { // If valid set the valid pile and valid variable
						validPile = col;
						break outerloop;
					}
				}
				
				
			}
		}
		
		System.out.println("\nVALID PILE: " + validPile);
		return validPile;
	
	}
	
	/*
	 * Check if the suggested pile is a valid pile to place the draggable card
	 */
	public static boolean validPlacement(DragCard currCard, DragCard enteredCard) {
		
		if(!currCard.getFaceUp() || (currCard.getCard().getValue() - 1) != enteredCard.getCard().getValue()
				|| currCard.getCard().getColor().equals(enteredCard.getCard().getColor())) {
			return false;
		}
		
		
		return true;
		
		
	}
	
	
	// Display drag card on the mid panel
	public void createDragCard(DragCard card) {
		System.out.println("Card: " + card.getCard());
		this.currDragCard = card;
		this.currDragCard.setFaceUp(true);
		
		card.createMotionListener();
		this.add(card);
		card.setVisible(true);
	}
	
	
	// Set visibility to true
	public void setVisible() {
		this.setVisible(true);
		
	}
	
	
	// Determine where the user wants to put the drag card from movelistener
	public static int determinePlacement(int x, int y) {
		
		/*
		 * Iterate thorugh the jlpCoords to determine where the x and y coords
		 * fall under
		 */
		int jlp = 0;
		for(int[] arr :  jlpCoords) {
			int xMin = arr[0], xMax = arr[1], yMin = arr[2], yMax = arr[3];
			
			if((x >= xMin && x <= xMax) && (y >= yMin && y <= yMax)) {
				return jlp;
			}else {
				jlp++;
			}
		}
		
		return -1;
	}
	
	// Check if the suggested placement is a valid placement
	public static boolean checkPlacement(int jlpIndex) {
		
		// Find the position of the last card in the suggested jlp pile
		DragCard firstCard = pileArray[0][jlpIndex]; // Made specifically for checking for empty pile
		DragCard lastCard = null;
		int lastCardRow = 0;
		
		// Check if the pile is empty
		// First check the curr drag card
		// Then check the curr drag pile
		// Check if the card is a king bieng placed on an emtpy pile
		if(currDragCard != null && currDragPile == null) {
			if(firstCard == null && currDragCard.getCard().getValue() == 13) {
				return true;
			}
		}else if(currDragCard == null && currDragPile != null) {
			if(firstCard == null && dragPileContents[0].getCard().getValue() == 13) {
				return true;
			}
		}
		
		// Find the last card on the pileArray
		for(int row = 0; row < pileArray.length; row++) {
			DragCard card = pileArray[row][jlpIndex];
			
			if(card == null) {
				lastCardRow = row - 1;
				lastCard = pileArray[lastCardRow][jlpIndex];
				break;
			}else {
				lastCard = pileArray[pileArray.length - 1][jlpIndex];
			}
		}
		
		
		// Determine if that card is a valid placement
		if(lastCard == null) {
			return false;
		}
		
		/*
		 * If the curr drag card is not null
		 */
		if(currDragCard != null && dragPileContents[0] == null) {
			if(!lastCard.getFaceUp() || (lastCard.getCard().getValue() - 1) != currDragCard.getCard().getValue()
					|| lastCard.getCard().getColor().equals(currDragCard.getCard().getColor())) {
				return false;
			}
		}else if(currDragCard == null && dragPileContents[0] != null){ // If the curr drag pile is not null
			if(!lastCard.getFaceUp() || (lastCard.getCard().getValue() - 1) != dragPileContents[0].getCard().getValue()
					|| lastCard.getCard().getColor().equals(dragPileContents[0].getCard().getColor())) {
				return false;
			}
			
		}
		
		return true;
	}
	
	
	//Add the drag card to the specified pile
	public void addToPile(int index) {
		
		/*DragCard test = new DragCard(0, 50);
		test.setCard(MyFrame.deck.getTopCard());
		test.setVisible(true);
		jlpArr[1].initializeBound(((LeftPanel.WIDTH / 2) - (Deck.WIDTH / 2)) + (Deck.WIDTH + 10), LeftPanel.HEIGHT / 10, lpWidth, lpHeight + 50);
		jlpArr[1].addLayer(test);*/
		
		
		this.yPos = 0; // Reset the Y position of a card in a jlp
		this.x = (LeftPanel.WIDTH / 2) - (Deck.WIDTH / 2); // Reset the X position of the jlp
		int numCards = 0; // Number of cards in the suggested layered pane
		int rowPos = -1; // Store row position of where the new card will be placed
		this.lpHeight = Deck.HEIGHT; // Reset the lp height
		
		/*
		 * Iterate through the column of the specified index to find how many non-null values there are
		 */
		for(int row = 0; row < pileArray.length; row++) {
			
			if(pileArray[row][index] != null) {
				yPos += offsetY;
				numCards++;
			}else {
				//pileArray[row][index] = this.currDragCard;
				rowPos = row;
				break;
			}
		}
		
		/*
		 * Create a the new card to be placed in the pile
		 */
		DragCard newCard = new DragCard(0, yPos);
		newCard.setCard(this.currDragCard.getCard());
		newCard.setFaceUp(true);
		newCard.setVisible(true);
		pileArray[rowPos][index] = newCard;
		
		/*
		 * Print out the pile array
		 */
		System.out.println("\nPILE ARRAY WHEN ADDING A NEW CARD: ");
		for(int col = 0; col < pileArray[0].length; col++) {
			System.out.print("[");
			for(int row = 0; row < pileArray.length; row++) {
				DragCard card = pileArray[row][col];
				if(card != null) {
					System.out.print(card.getCard() + ", ");
				}else {
					System.out.print("null, ");
				}
			}
			System.out.println("]");
		}
		
		
		jlpArr[index].initializeBound(x + (offsetX * index), y, lpWidth, (y + lpHeight + (numCards * offsetY))); // Resize the jlp
		jlpArr[index].addLayer(newCard); // Add the new drag card
		jlpCoords[index][jlpCoords[0].length - 1] = y + lpHeight + (numCards * offsetY); // Update the jlp coords array
		
		
		
		/*
		 * Set curr drag card invisible and remove it from the panel
		 * Set curr drag card to null value
		 */
		this.currDragCard.setVisible(false);
		this.remove(this.currDragCard);
		this.currDragCard = null;
		
	}
	
	/*
	 * Add the new drag pile to a existing pile
	 */
	public void addPileToPile(int index) {
		this.yPos = 0; // Reset the Y position of a card in a jlp
		this.x = (LeftPanel.WIDTH / 2) - (Deck.WIDTH / 2); // Reset the X position of the jlp
		int numCards = 0; // Number of cards in the suggested layered pane
		int rowPos = -1; // Store row position of where the new card will be placed
		this.lpHeight = Deck.HEIGHT; // Reset the lp height
		
		for(int row = 0; row < pileArray.length; row++) {
			
			if(pileArray[row][index] != null) {
				yPos += offsetY;
				numCards++;
			}else {
				//pileArray[row][index] = this.currDragCard;
				rowPos = row;
				break;
			}
		}
		
		for(int i = 0; i < dragPileContents.length; i++) {
			
			if(dragPileContents[i] == null) {
				break;
			}
			/*
			 * Create a new card and update the pile array
			 */
			DragCard newCard = new DragCard(0, yPos);
			newCard.setCard(dragPileContents[i].getCard());
			newCard.setFaceUp(true);
			newCard.setVisible(true);
			pileArray[rowPos][index] = newCard;
			rowPos++;
			
			/*
			 * Update the jlp array components
			 */
			jlpArr[index].initializeBound(x + (offsetX * index), y, lpWidth, (y + lpHeight + (numCards * offsetY))); // Resize the jlp
			jlpArr[index].addLayer(newCard); // Add the new drag card
			jlpCoords[index][jlpCoords[0].length - 1] = y + lpHeight + (numCards * offsetY); // Update the jlp coords array
			
			// Update variables for next iteration
			yPos += 25;
			numCards++;
			
		}
		

		// Set null values for current drag pile
		for(int i = 0; i < dragPileContents.length; i++) {
			dragPileContents[i] = null;
		}
		
		// Remove the current drag pile from the midpanel
		this.currDragPile.setVisible(false);
		this.remove(currDragPile);
		this.currDragPile = null;
		
	}
	
	/*
	 * Find if the drag card pressed is the last card in the pile
	 */
	public static boolean isLastCard(DragCard card) {
		
		outerloop:
		for(int col = 0; col < pileArray[0].length; col++) {
			
			for(int row = 0; row < pileArray.length; row++) {
				
				DragCard currDragCard = pileArray[row][col];
				DragCard nextDragCard = null;
				
				if(row != 19) nextDragCard = pileArray[row + 1][col];
				if(currDragCard == null) continue;
				
				/*System.out.println("\nIS LAST CARD CONDITIONS FOR ROW: " + row + " COL: " + col);
				System.out.println("currDragCard: " + currDragCard.getCard());
				System.out.println("card: " + card.getCard());
				System.out.println("nextDragCard: " + nextDragCard);
				System.out.println();*/
				
				if(currDragCard != null && currDragCard == card && nextDragCard == null) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	// Find where the drag card is in the pile and gather info on it
	public void getNewPileInfo(DragCard dragCard, int xPos, int yPos) {
		int rowPos, colPos;
		rowPos = colPos = 0; // Keep track of the x and y position of the dragCard in the pile array
		int suggestedPile = -1; // Keep track of which pile the user wants to create a new one in
		
		// Iterate through the pile array 
		for(int col = 0; col < pileArray[0].length; col++) {
			
			for(int row = 0; row < pileArray.length; row++) {
				if(pileArray[row][col] == dragCard) {
					suggestedPile = col;
					rowPos = row;
					colPos = col;
				}
			}
		}
		
		if(pileArray[rowPos][colPos].getFaceUp()) {
			createNewPile(rowPos, colPos, suggestedPile, xPos, yPos);
			
		}else {
			System.out.println("\nCannot make new pile because the card is faced down!");
			System.out.println();
		}
		
	}
	
	// Create a new draggable layered pane for new pile to drag
	public void createNewPile(int rowPos, int colPos, int suggestedPile, int xPos, int yPos) {
		
		/*
		 * Remove the selected card and the cards below it, store it in an array list
		 */
		ArrayList<DragCard> removedCards = new ArrayList<DragCard>();
		this.lpHeight = Deck.HEIGHT; // Resize the lp height
		int numCardsRemoved = 0;
		
		/*
		 * Until the pile array has reached its last card in the pile, set each card to invisible and remove it from the current pile
		 */
		for(int row = rowPos; row < pileArray.length; row++) {
			
			if(pileArray[row][colPos] == null) {
				break;
			}
			
			pileArray[row][colPos].setVisible(false); // Set the cards below the card pressed to invisible
			jlpArr[suggestedPile].remove(pileArray[row][colPos]); // Remove the cards in the pile
			removedCards.add(pileArray[row][colPos]);
			pileArray[row][colPos] = null; // Update the pile array
			numCardsRemoved++;
			
		}
		
		
		/*
		 * Print out the pile array
		 */
		System.out.println("\nPILE ARRAY WHEN REMOVING A PILE: ");
		for(int col = 0; col < pileArray[0].length; col++) {
			System.out.print("[");
			for(int row = 0; row < pileArray.length; row++) {
				DragCard card = pileArray[row][col];
				if(card != null) {
					System.out.print(card.getCard() + ", ");
				}else {
					System.out.print("null, ");
				}
			}
			System.out.println("]");
		}
		
		
		/*
		 * Flip the last card to an up position
		 */
		
		if(rowPos != 0) {
			pileArray[rowPos - 1][colPos].setImg(pileArray[rowPos - 1][colPos].getFaceUpImg());
			pileArray[rowPos - 1][colPos].setFaceUp(true);
		}
		
		/*
		 * Update the size of jlp and the jlp coords
		 */
		if(rowPos == 0) rowPos = 1;
		jlpArr[suggestedPile].initializeBound(jlpArr[suggestedPile].getX(), jlpArr[suggestedPile].getY(), lpWidth, y + lpHeight + ((rowPos - 1) * offsetY)); // Resize the jlp
		jlpCoords[suggestedPile][jlpCoords[0].length - 1] = y + lpHeight + ((rowPos - 1) * offsetY); // Update the jlp coords of y max value
		System.out.println("X: " + jlpArr[suggestedPile].getX() + " Y: " + jlpArr[suggestedPile].getY());
		
		/*
		System.out.println();
		System.out.println("Y: " + y);
		System.out.println("lpHeight: " + lpHeight);
		System.out.println("RowPos: " + rowPos);
		System.out.println("SuggestedPile: " + suggestedPile);
		System.out.println("Resizing to: " + (y + lpHeight + ((rowPos - 1) * offsetY)));*/
		
		/*
		 * Print out the new jlp coords
		 */
		System.out.println();
		for(int[] arr: jlpCoords) {
			System.out.println(Arrays.toString(arr));
		}
		
		
		
		
		/*
		 * Create the new draggable layered pane where the user clicks 
		 */
		
		DraggableLayeredPane dragPile = new DraggableLayeredPane();
		dragPile.initializeBound(this.getWidth() - ((LeftPanel.WIDTH / 2) - (Deck.WIDTH / 2) + Deck.WIDTH), MidPanel.HEIGHT - (Deck.HEIGHT / 2) - 20, lpWidth, lpHeight + (numCardsRemoved * offsetY)); // Display it on opposite side of drag card
		dragPile.setVisible(false);
		
		/*
		 * Set each card in the pile into the new pile
		 */
		int currYPos = 0;
		for(DragCard card : removedCards) {
			
			DragCard newCard = new DragCard(0, currYPos);
			newCard.setCard(card.getCard());
			newCard.setFaceUp(true);
			dragPile.addLayer(newCard);
			
			
			currYPos += 25;
			
		}
		
		/*
		 * For each card that was removed update the dragPileContent array
		 */
		int idx = 0;
		for(DragCard card : removedCards) {
			dragPileContents[idx] = card;
			idx++;
		}
		System.out.println("\nCONTENTS OF DRAG PILE ARRAY: ");
		System.out.println(Arrays.toString(this.dragPileContents));
		System.out.println();
		
		this.currDragPile = dragPile;
		displayNewPile(dragPile);
	}
	
	/*
	 * Display the new drag pile onto the midpanel
	 */
	public void displayNewPile(DraggableLayeredPane pile) {
		
		
		this.add(pile); // Add it to the midpanel
		pile.setVisible(true); // Set the visibility to true
		
		
		
	}
	
	// Function to remove either drag card or drag pile when putting into stack pile
	public void removeCard(String type) {
		
		if(type.equals("card")) {
			this.currDragCard.setVisible(false);
			this.remove(this.currDragCard);
			this.currDragCard = null;
			
		}else if(type.equals("pile")) {
			this.currDragPile.setVisible(false);
			this.remove(this.currDragPile);
			this.currDragPile = null;
			
			// Set null values to drag pile contents
			for(int i = 0; i < dragPileContents.length; i++) {
				dragPileContents[i] = null;
			}
		}
		
		
	}
	
	public void didWin() {
		
		boolean didWin = true;
		
		// If there is no more cards in the pile array that means all cards have been placed in the stack pile meaning the user won the game!
		for(int col = 0; col < pileArray[0].length; col++) {
			for(int row = 0; row < pileArray.length; row++) {
				if(pileArray[row][col] != null) didWin = false;
			}
		}
		
		
		
		if(didWin) {
			System.out.println("\nCONGRATS YOU WON!");
			
			try {
				displayCongratulations();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void displayCongratulations() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		
		
		
		
		
		// Display the image onto the JPanel
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon("Winner.png"));
		label.setBounds(10, 150, 850, 450);
		label.setVisible(true);
		
		this.add(label);
		
		// Play congrats sound effect
		System.out.println("\nPlaying sound...");
		
		MyFrame.audio.playCongratulations();
		
		
	}
	
	// Return a specific draggable layered pane from jlparr
	/*public DraggableLayeredPane getDlp(int idx) {
		return this.jlpArr[idx];
	}*/
	
	
	
	
}
