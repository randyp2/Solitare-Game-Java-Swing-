package javaSolitareGame;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class Audio {

	Audio() {
		System.out.println("\nCreating Audio class");
	}
	
	// Sound effect for either drawing a card or placing a card down
	public void playCardSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		
		File file = new File("Draw Card Sound Effect.wav");
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		Clip clip = AudioSystem.getClip();
		clip.open(audioStream);
		
		clip.start();
		
	}
	
	// Sound effect for shuffling the deck of cards
	public void playShuffleSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		
		File file = new File("Shuffle Card Sound Effect.wav");
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		Clip clip = AudioSystem.getClip();
		clip.open(audioStream);
		
		clip.start();
		
	}
	
	// Sound effect for winning
	public void playCongratulations() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		
		File file = new File("Winner Sound Effect.wav");
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		Clip clip = AudioSystem.getClip();
		clip.open(audioStream);
		
		clip.start();
		
	}
}
