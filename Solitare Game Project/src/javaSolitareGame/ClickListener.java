package javaSolitareGame;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JLabel;

public class ClickListener implements MouseListener{
	
	UICard card;
	//CardLabel displayCard;
	
	DraggableCard currCard;
	
	
	
	ClickListener(UICard card) {
		this.card = card;
		//this.displayCard = displayCard;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Component pressedComponent = e.getComponent().getComponentAt(e.getPoint());
		
		// Click listener for UICard inside waste pile
		if(pressedComponent instanceof UICard) {
			
			System.out.println();
			System.out.println("You have pressed on the UICard");
			
			// If the UICard can be placed in either a pile or a stack pile, and that there's not already a drag pile being placed			
			if((MidPanel.checkValidity(card.getCard()) != - 1 || RightPanel.checkValidity(card.getCard()) != -1) && MidPanel.dragPileContents[0] == null) {
				
				LeftPanel.wastePile.removeSpecificCard(card); // Remove the card and add it to the wastepile
				// Play sound effect
				try {
					MyFrame.audio.playCardSound();
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}else if(MidPanel.dragPileContents[0] != null){
				System.out.println("\nDRAG CARD CANNOT BE PLACED WHEN A DRAG PILE IS PRESENT");
				System.out.println();
			}else {
				System.out.println("\nINVALID CARD PLACEMENT: PLEASE TRY AGAIN");
				System.out.println();
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
		//if(releasedComponent instanceof DraggableLayeredPane)
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
