package javaSolitareGame;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class DraggableLayeredPane extends JLayeredPane{
	

	int layeredIndex; // Index of layered pane
	
	MoveListener ml; //Create mouse listener and motion listener
	DraggableLayeredPane() {
		ml = new MoveListener(this); 
		layeredIndex = 0;
		
		this.addMouseListener(ml);
		this.addMouseMotionListener(ml);
	}
	
	// Initialize bounds of the layered panel
	public void initializeBound(int x, int y, int width, int height) {
		this.setBounds(x, y, width, height);
		this.setVisible(true);
		
	}
	
	
	
	// Add a layer to the layered pane
	public void addLayer(JLabel card) {
		
		this.add(card, Integer.valueOf(layeredIndex));
		layeredIndex += 1;
	}
}
