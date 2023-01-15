package javaSolitareGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class LeftPanel extends JPanel implements MouseListener{
	
	// Width and height of leftpanel
	public static final int WIDTH = (int)(MyFrame.WIDTH / 7);
	public static final int HEIGHT = MyFrame.HEIGHT;
	private final int OFFSET = 50; // Not used
	
	public static int indexToSkip = -1; // Not used
	
	
	
	public static Card currCard; // Get the current card
	private ArrayList<CardLabel> pile; // List of all viewable card labels
	public static CardLabel[] wastePileArr; // List of all cards that is moved to the wastepile
	private int wastePileIdx; // Track the index of the wastepile
	public static int indexLayer = 0; // Index layer for j layered pane
	
	private JPanel blank; // Blank waste pile card
	
	boolean isEmpty = false; // Check if waste pile is empty
	
	
	public static WastePile wastePile; // Instantiate a waste pile j layered pane
	public static CardLabel currLabel; // Current viewable card
	DeckLabel deckLabel; // J Label for the waste pile
	
	/*
	 * Initialize properties for left panel:
	 * It will hold a button which represents the deck of cards where users can draw near cards each time
	 * the deck is pressed.
	 */
	LeftPanel() throws IOException {
		
		pile = new ArrayList<>();
		this.wastePileArr = new CardLabel[30];
		this.wastePileIdx = 0;
		
		
		// Set properties on left panel
		this.setBackground(new Color(0x228B22));
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setLayout(null);
		this.setVisible(true);
		
		// Initialize the waste pile
		wastePile = new WastePile();
		
		// Initialize the blank rectangle and add it to wastepile
		// This will be the default layer
		blank = new JPanel();
		blank.setBounds(0, 0, Deck.WIDTH, Deck.HEIGHT);
		blank.setBackground(Color.LIGHT_GRAY);
		
		wastePile.add(blank, Integer.valueOf(indexLayer));
		indexLayer+=1;
		this.add(wastePile);
		
		// Creates a new JLabel for the card
		currLabel = new CardLabel();
		this.add(currLabel); // Adds the panel to the panel
		
		// Create the click-able deck button to draw cards
		createDeckButton();
		
		this.setVisible(true);

	}
	
	// Creates the back of playing card button
	public void createDeckButton() throws IOException {
		deckLabel = new DeckLabel(); // Initialize deck label class
		
		// Add the label to the frame
		this.add(deckLabel);
		
		// Add mouse listener to the deck label
		deckLabel.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		// If the deck is empty then reset the deck of cards not used
		if(isEmpty) {
			
			System.out.println("Playing sound...");
			
			try {
				MyFrame.audio.playShuffleSound();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} // Play shuffle sound effect
			
			// Remove and set all current cardlabels in waste pile to invisible
			System.out.println("Waste pile array length: " + wastePileArr.length);
			for(int i = 0; i < this.wastePileArr.length; i++) {
				
				if(wastePileArr[i] == null) {
					continue;
				}else {
					wastePileArr[i].setVisible(false);
					this.remove(wastePileArr[i]);
				}
			}
			
			// Add back the unused card labels in the waste pile to the og deck
			for(int i = 0; i < wastePileArr.length; i++) {
				if(wastePileArr[i] != null) {
					MyFrame.deck.getDeckOfCards().add(wastePileArr[i].getCard());
					wastePileArr[i] = null;
				}
			}
			
			/*
			for(int i = 0; i < wastePileArr.length; i++) {
				if(wastePileArr[i] != null) {
					wastePileArr[i] = null;
				}
			}*/
			
			System.out.println("The deck was empty, it is now replenished");
			
			// Reset the deck label to the back of playing card image
			try {
				deckLabel.setImg(ImageIO.read(new File("back of playing card.png")));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// Remove what is in the current waste pile and add the empty space again
			wastePile.removeCurrPile();
			wastePile.add(blank, Integer.valueOf(indexLayer));
			
			// Reset the index layer and the is empty value
			indexLayer += 1;
			isEmpty = false;
			
			// Reset the waste pile index
			this.wastePileIdx = 0;
			
		}else {
		
			System.out.println("\nYou drew a card!!");
			System.out.println("Playing sound...");
			
			try {
				MyFrame.audio.playCardSound();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} // Play draw card sound effect
			
			// Create a new card label
			CardLabel currCard = MyFrame.deck.drawCard();
			
			//Set the current label to the current card
			currLabel = currCard;
			System.out.println("You drew a: " + currLabel.getCard());
			System.out.println("Current cards left in deck of cards: " + MyFrame.deck.getDeckOfCards().size());
			
			// Add it to the waste pile array to keep track of cards in waste pile
			this.wastePileArr[wastePileIdx] = currLabel;
			this.wastePileIdx++;
			
			// Display the card on the left panel and set it to visible
			currLabel.setVisible();
			
			// Check for empty deck
			try {
				checkForEmptyDeck();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	// Set visibility to true
	public void setVisible() {
		this.setVisible(true);
	}
	
	// Check if there is no more cards to cycle through
	public void checkForEmptyDeck() throws IOException {
		
		// If the deck of cards is 0 then set the back of card img to an undo deck img
		if(MyFrame.deck.getDeckOfCards().size() == 0) {
			BufferedImage img = ImageIO.read(new File("undo dec.png"));
			deckLabel.setImg(img);
			isEmpty = true; // Set to true
		}
	}
	
	
	// Return pile array list
	public ArrayList<CardLabel> getPile() {
		return this.pile;
	}
	
	public void removeCardFromPile(int idx) {
		this.pile.remove(idx);
		
		
	}
	
	
	
}
