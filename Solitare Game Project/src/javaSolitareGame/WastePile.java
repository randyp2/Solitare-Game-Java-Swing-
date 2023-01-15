package javaSolitareGame;

import java.awt.Color;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/*
 * Class that stores the UI removable cards
 */
public class WastePile extends JLayeredPane{
	
	// Values to remove the UI Card
	private String storedRank;
	private String storedSuit;
	private int storedValue;

	WastePile() {
		
		this.setBounds((LeftPanel.WIDTH / 2) - (Deck.WIDTH / 2), (LeftPanel.HEIGHT / 2), Deck.WIDTH, Deck.HEIGHT);
		this.setVisible(true);
	}
	
	// Remove what is in the current pile
	public void removeCurrPile() {
		this.removeAll();
	}
	
	// Remove the card that is clicked on
	public void removeSpecificCard(UICard card) {
		
		
		
		// Store values to find the UICard
		storedRank = card.getCard().getRank();
		storedSuit = card.getCard().getSuit();
		storedValue = card.getCard().getValue();
		
		int index = 0; // Keep track of the index the card is being removed at
		
		for(int i = 0; i < LeftPanel.wastePileArr.length; i++) {
			
			CardLabel currCard = LeftPanel.wastePileArr[i];
					
			System.out.println("Curr index: " + index);
			if(currCard != null) {
				if(currCard.getCard().getRank().equals(storedRank) && currCard.getCard().getSuit().equals(storedSuit) && currCard.getCard().getValue() == storedValue) {
					index = i;
					break;
				}
			}
		}
		
		/*
		 * Print out info
		 */
		System.out.println();
		System.out.println("Pile before removal: ");
		for(CardLabel cards: LeftPanel.wastePileArr) {
			if(cards != null) {
				System.out.print(cards.getCard() + ", ");
			}else {
				System.out.print("null, ");
			}
			
		}
		System.out.println();
		System.out.println("The index of " + card.getCard() + " is: " + index);
		System.out.println("We removed: " + LeftPanel.wastePileArr[index].getCard());
		System.out.println();
		
		LeftPanel.wastePileArr[index] = null;//Remove it from pile
		
		
		/*
		 * Print out after removal info
		 */
		System.out.println("Pile after removal: ");
		for(CardLabel cards: LeftPanel.wastePileArr) {
			if(cards != null) {
				System.out.print(cards.getCard() + ", ");
			}else {
				System.out.print("null, ");
			}
		}
		System.out.println();
		
		
		
		
		card.setVisible(false);
		this.remove(card);
		
		// Create the drag card to be placed in the midpanel
		createDragCard(card);
		
	}
		
	public void createDragCard(UICard card) {
		DragCard dc = new DragCard((LeftPanel.WIDTH / 2) - (Deck.WIDTH / 2) , MidPanel.HEIGHT - (Deck.HEIGHT / 2) - 20);
		dc.setCard(card.getCard());
		dc.setVisible(false);
		
		MyFrame.midPanel.createDragCard(dc);
	}
	
	
	
}
