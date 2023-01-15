package javaSolitareGame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class DeckLabel extends JLabel {
	BufferedImage img;
	
	DeckLabel() throws IOException {
		
		// Image for back of playing cards
		img = ImageIO.read(new File("back of playing card.png"));
		
		
		// Set the properties
		this.setIcon(new ImageIcon(img));
		this.setBounds((LeftPanel.WIDTH / 2) - (Deck.WIDTH / 2), LeftPanel.HEIGHT / 10, Deck.WIDTH, Deck.HEIGHT);
		
		
	}
	
	public void setImg(BufferedImage img) {
		this.setIcon(new ImageIcon(img));
	}
}
