package javaSolitareGame;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;

public class MyFrame extends JFrame{
	
	/*
	 * Height and width of the whole game panel
	 */
	public static final int WIDTH = 1300;
	public static final int HEIGHT = 800; // og = 600
	public static Deck deck; // Create a deck to use for all panels
	public static Audio audio; // Create a audio class for move listener to use
	public static int score; // Score for each move in the game: Card added to pile = +5, From pile to stack pile = +20, From wastepile to stack pile: +10
	
	
	/*
	 * Divide the game up into three different panels where each component of a game
	 * will take place.
	 * 
	 * LeftPanel: Be able to draw cards and click on the cards to be placed down
	 * MidPanel: Be able to drag the cards onto the selective piles
	 * RigthPanel: Be abel to place card into ace stack pile
	 */
	public static LeftPanel leftPanel;
	public static MidPanel midPanel;
	public static RightPanel rightPanel;
	
	MyFrame() throws IOException {
		
		// Initialize and shuffle main game deck
		deck = new Deck();
		deck.shuffle();
		this.audio = new Audio();
		this.score = 0; 
		
		
		// Initialize the game frame and the game panels
		initializeFrame();
		initializePanels();
		
		
		
	}
	
	/*
	 * Set the components of the JFrame
	 */
	public void initializeFrame() {
		
		this.setTitle("Solitare Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		
	}
	
	/*
	 * Initialize the left, middle, and right JPanels into the JFrame.
	 * Each with their separate classes.
	 */
	public void initializePanels() throws IOException {
		
		leftPanel = new LeftPanel();
		midPanel = new MidPanel();
		rightPanel = new RightPanel();
		
		// Add the panels into our JFrame
		this.add(midPanel, BorderLayout.CENTER);
		this.add(leftPanel, BorderLayout.WEST);
		this.add(rightPanel, BorderLayout.EAST);
		
		frameSetVisible();
	
		
	}
	
	public void frameSetVisible() {
		this.setVisible(true);
	}
}
