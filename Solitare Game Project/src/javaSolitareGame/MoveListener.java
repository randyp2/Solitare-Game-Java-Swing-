package javaSolitareGame;

import java.awt.Color;
import java.awt.Component;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;




public class MoveListener extends javax.swing.JFrame implements MouseListener, MouseMotionListener{
	
	ImageIcon image = new ImageIcon("back of playing card.png");
	//int WIDTH = image.getIconWidth();
	//int HEIGHT = image.getIconHeight();
	Point imgCorner;
	Point startPt;
	Point currPt2;
	
	boolean isPile; // Check if a component is a part of a pile or not
	
	
	Component label;
	DragCard currCard;
	
	// Movelistener for drag pile
	MoveListener(JLayeredPane pane) {
		imgCorner = new Point(0, 0);
		this.label = pane;
	}
	
	// Move listener for a drag card
	MoveListener(JLabel label) {
		imgCorner = new Point(0,0);
		this.label = label;
	}
	
	
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		Component pressedComponent = e.getComponent().getComponentAt(e.getPoint()); // Store the component that was pressed
		startPt = SwingUtilities.convertPoint(label, e.getPoint(), label.getParent()); // Track where the mouse is when it is pressed
		
		System.out.println("Starting point: " + startPt);
		Point pressedPt = SwingUtilities.convertPoint(label, e.getPoint(), label.getParent()); // Keep track of mouse position when pressed
		isPile = MidPanel.determinePlacement(pressedPt.x, pressedPt.y) != -1 ? true : false; // Check if the pressed card is in a pile
		
		
		if(pressedComponent instanceof DragCard && !isPile) { // Check if the pressed component is a drag card and is not in the pile
			System.out.println("You pressed on the drag card: " + ((DragCard)pressedComponent).getCard());
			
		}else if(pressedComponent instanceof DragCard && isPile) { // Check if the pressed component is a drag cards thats in the pile
			System.out.println("\nYou pressed on the drag card inside the pile: " + ((DragCard)pressedComponent).getCard());
			
			/*System.out.println("\n Conditional Statements: ");
			System.out.println(MidPanel.checkValidity(((DragCard)pressedComponent).getCard()) != -1);

			System.out.println(RightPanel.checkValidity(((DragCard)pressedComponent).getCard()) != -1);
			System.out.println(MidPanel.isLastCard((DragCard)pressedComponent));
			System.out.println(MidPanel.currDragCard == null);
			System.out.println(((DragCard)pressedComponent).getFaceUp());
			System.out.println();*/
			
			if((MidPanel.checkValidity(((DragCard)pressedComponent).getCard()) != -1 || (RightPanel.checkValidity(((DragCard)pressedComponent).getCard()) != -1
					&& MidPanel.isLastCard((DragCard)pressedComponent))) && MidPanel.currDragCard == null && ((DragCard)pressedComponent).getFaceUp()) {
				
				/*System.out.println("\n Conditional Statements: ");
				System.out.println(MidPanel.checkValidity(((DragCard)pressedComponent).getCard()) != -1);
	
				System.out.println((RightPanel.checkValidity(((DragCard)pressedComponent).getCard()) != -1
						&& MidPanel.isLastCard((DragCard)pressedComponent)));
				System.out.println(MidPanel.currDragCard == null);
				System.out.println(((DragCard)pressedComponent).getFaceUp());
				System.out.println();*/
				
				MyFrame.midPanel.getNewPileInfo((DragCard)pressedComponent, pressedPt.x, pressedPt.y);
				
				// Play sound effect
				try {
					MyFrame.audio.playCardSound();
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}else if(MidPanel.currDragCard != null){
				System.out.println("\nYOU CANNOT PLACE A PILE WHILE A DRAG CARD IS PRESENT!!!!");
				System.out.println();
				
			}else {	
				System.out.println("\nTHERE IS NO WHERE TO PLACE THIS PILE!!!!!");
				System.out.println();
			}
			
			
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		startPt = null;
		
		Component releasedComponent = e.getComponent().getComponentAt(e.getPoint()); // Component the mouse releases on
		Point releasedPt = SwingUtilities.convertPoint(label, e.getPoint(), label.getParent()); // Keep track of released position of the mouse
		
		
		
		
		if(releasedComponent instanceof DragCard && !isPile) {// Check if the released component is a drag card and is not in the pile
			
			int suggestedPile = MidPanel.determinePlacement(releasedPt.x, releasedPt.y); // Check which pile the user wants to place the card in
			
			if(suggestedPile == -1) { // Check for a null placement
				System.out.println("\nINVALID PLACEMENT!");
				
				/*
				 * Check if there is no drag pile on the panel
				 */
				if(MidPanel.currDragCard != null && MidPanel.dragPileContents[0] == null) {
					MidPanel.currDragCard.setLocation((LeftPanel.WIDTH / 2) - (Deck.WIDTH / 2) , MidPanel.HEIGHT - (Deck.HEIGHT / 2) - 20); // Return to og position
					
				}else if(MidPanel.currDragCard == null && MidPanel.dragPileContents[0] != null){ // Check if there is no drag card on the panel
					MidPanel.currDragPile.setLocation(MyFrame.midPanel.getWidth() - ((LeftPanel.WIDTH / 2) - (Deck.WIDTH / 2) + Deck.WIDTH), MidPanel.HEIGHT - (Deck.HEIGHT / 2) - 20); // Return to og position
					
				}else if(MidPanel.currDragCard != null && MidPanel.dragPileContents != null) {
					System.out.println("\nBOTH A DRAGCARD AND A DRAGPILE CANNOT BE PLACED AT THE SAME TIME, PLEASE DO )ONE AT A TIME");
				}
				
			}else {
				
				if(MidPanel.currDragCard != null && MidPanel.dragPileContents[0] == null){
					if(MidPanel.checkPlacement(suggestedPile)) { // Check if the suggested pile is a valid placement else return to og position
						
						MyFrame.midPanel.addToPile(suggestedPile);
						
						// Play sound effect
						try {
							MyFrame.audio.playCardSound();
						} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						MyFrame.score += 5; // Update score
						MidPanel.updateScore(); // Update the display score
						System.out.println("\nSCORE: " + MyFrame.score);
						System.out.println();
					}else {
						System.out.println("\nINVALID PILE PLACEMENT!");
						MidPanel.currDragCard.setLocation((LeftPanel.WIDTH / 2) - (Deck.WIDTH / 2) , MidPanel.HEIGHT - (Deck.HEIGHT / 2) - 20);
					}
				}else if(MidPanel.currDragCard == null && MidPanel.dragPileContents[0] != null) {
					
					if(MidPanel.checkPlacement(suggestedPile)) {
						
						MyFrame.midPanel.addPileToPile(suggestedPile);
						
						// Play sound effect
						try {
							MyFrame.audio.playCardSound();
						} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						MyFrame.score += 5; // Update score
						MidPanel.updateScore(); // Update the display score
						System.out.println("\nSCORE: " + MyFrame.score);
						System.out.println();
					}else {
						System.out.println("THE PILE CANNOT BE PLACED HERE");
						MidPanel.currDragPile.setLocation(MyFrame.midPanel.getWidth() - ((LeftPanel.WIDTH / 2) - (Deck.WIDTH / 2) + Deck.WIDTH), MidPanel.HEIGHT - (Deck.HEIGHT / 2) - 20); // Return to og position
					}
				}
				
			}
			
			
		}else if (releasedComponent instanceof DragCard && isPile) { // Check if the released component is a drag cards thats in the pile
			
			
		}else if(releasedComponent == null && MidPanel.currDragCard != null && MidPanel.dragPileContents[0] == null) { // If the released component is null then the user attempts to drag it to pile 0
			
			int checkForPile = RightPanel.validStackPile(releasedPt.x, releasedPt.y);
			if(checkForPile != -1) {
				
				// If the drag card can be placed in a stack pile then add it to stack pile
				if(RightPanel.checkPlacement(checkForPile)) {
					
					System.out.println("\nTHIS IS A VALID PLACEMENT");
					MyFrame.rightPanel.addToPile(checkForPile);
					
					// Play sound effect
					try {
						MyFrame.audio.playCardSound();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					MyFrame.score += 10; // Update score
					MidPanel.updateScore(); // Update the display score
					System.out.println("\nSCORE: " + MyFrame.score);
					System.out.println();
					
					// Check for a win condition
					MyFrame.midPanel.didWin();
					
				}else {
					System.out.println("\n INVALID STACK PILE PLACEMENT!");
					MidPanel.currDragCard.setLocation((LeftPanel.WIDTH / 2) - (Deck.WIDTH / 2) , MidPanel.HEIGHT - (Deck.HEIGHT / 2) - 20);
				}
			}else if(MidPanel.checkPlacement(0)) { // Add it to the very first pile
				
				MyFrame.midPanel.addToPile(0);
				
				// Play sound effect
				try {
					MyFrame.audio.playCardSound();
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				MyFrame.score += 5; // Update score
				MidPanel.updateScore(); // Update the display score
				System.out.println("\nSCORE: " + MyFrame.score);
				System.out.println();
			}else {
				System.out.println("\nINVALID PILE PLACEMENT!");
				MidPanel.currDragCard.setLocation((LeftPanel.WIDTH / 2) - (Deck.WIDTH / 2) , MidPanel.HEIGHT - (Deck.HEIGHT / 2) - 20);
			}
			
		}else if(releasedComponent == null && MidPanel.currDragCard == null && MidPanel.dragPileContents[0] != null) {
			
			int checkForPileTwo = RightPanel.validStackPile(releasedPt.x, releasedPt.y);
			if(checkForPileTwo != -1) {
				
				// If the drag pile can be placed in a stack pile then add it to stack pile
				if(RightPanel.checkPlacement(checkForPileTwo)) {
					
					System.out.println("\nTHIS IS A VALID PLACEMENT");
					MyFrame.rightPanel.addToPile(checkForPileTwo);
					
					// Play sound effect
					try {
						MyFrame.audio.playCardSound();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					MyFrame.score += 20; // Update score
					MidPanel.updateScore(); // Update the display score
					System.out.println("\nSCORE: " + MyFrame.score);
					System.out.println();
					
					// Check for a win condition
					MyFrame.midPanel.didWin();
					
				}else {
					System.out.println("\n INVALID STACK PILE PLACEMENT!");
					MidPanel.currDragPile.setLocation(MyFrame.midPanel.getWidth() - ((LeftPanel.WIDTH / 2) - (Deck.WIDTH / 2) + Deck.WIDTH), MidPanel.HEIGHT - (Deck.HEIGHT / 2) - 20); // Return to og position
				}
				
			}else if(MidPanel.checkPlacement(0)) { // Add it to the very first pile
				
				MyFrame.midPanel.addPileToPile(0);
				
				// Play sound effect
				try {
					MyFrame.audio.playCardSound();
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				MyFrame.score += 5; // Update score
				MidPanel.updateScore(); // Update the display score
				System.out.println("\nSCORE: " + MyFrame.score);
				System.out.println();
			}else {
				System.out.println("\nINVALID PILE PLACEMENT!");
				MidPanel.currDragPile.setLocation(MyFrame.midPanel.getWidth() - ((LeftPanel.WIDTH / 2) - (Deck.WIDTH / 2) + Deck.WIDTH), MidPanel.HEIGHT - (Deck.HEIGHT / 2) - 20); // Return to og position
			}
		}
		
		/*
		 * Check if mouse was released on a stack pile
		 */
		/*
		int validStackPile = RightPanel.validStackPile(releasedPt.x, releasedPt.y);
		
		if(validStackPile != -1) {
			
			if(RightPanel.checkPlacement(validStackPile)) {
				System.out.println("\nTHIS IS A VALID PLACEMENT");
				MyFrame.rightPanel.addToPile(validStackPile);
			}else {
				System.out.println("\n INVALID STACK PILE PLACEMENT!");
				
				if(MidPanel.currDragCard != null && MidPanel.currDragPile == null) {
					MidPanel.currDragCard.setLocation((LeftPanel.WIDTH / 2) - (Deck.WIDTH / 2) , MidPanel.HEIGHT - (Deck.HEIGHT / 2) - 20);
					
				}else if(MidPanel.currDragCard == null && MidPanel.currDragPile != null) {
					MidPanel.currDragPile.setLocation(MyFrame.midPanel.getWidth() - ((LeftPanel.WIDTH / 2) - (Deck.WIDTH / 2) + Deck.WIDTH), MidPanel.HEIGHT - (Deck.HEIGHT / 2) - 20); // Return to og position
					
				}
			}
		}else {
			System.out.println("\nTHIS IS NOT A VALID PILE PALCEMENT");
		}*/
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	/*
	 * Function used to drag a card or a pile
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
		Point currPt = SwingUtilities.convertPoint(label, e.getPoint(), label.getParent());
		currPt2 = currPt;
		
		
		// If the component you are trying to drag is not part of a pile then you can drag it
		if(!isPile) {
			if(label.getParent().getBounds().contains(currPt)) {
				Point newPt = label.getLocation();
				newPt.translate((currPt.x - startPt.x), (currPt.y - startPt.y));
				newPt.x = Math.max(newPt.x, 0);
				newPt.y = Math.max(newPt.y, 0);
				newPt.x = Math.min(newPt.x, label.getParent().getWidth() - label.getWidth());
				newPt.y = Math.min(newPt.y, label.getParent().getHeight() - label.getHeight());
				label.setLocation(newPt);
				startPt = currPt;
			}
		}
		
	}
	
	



	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
