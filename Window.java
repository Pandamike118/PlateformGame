package gamecore;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6733699228443403847L;

	public Window(int WIDITH, int LENGTH, String TITLE, Board game) {
		JFrame frame = new JFrame("Tower Defense Game"); // crate the frame of our window and add a title 
		
		frame.setPreferredSize(new Dimension(WIDITH, LENGTH));// setting the size of the window
		frame.setMaximumSize(new Dimension(WIDITH, LENGTH));// setting the max size of the window
		frame.setMinimumSize(new Dimension(WIDITH, LENGTH));// setting the min size of the window
		
	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // make sure the game will end when you exit out
		frame.setLocationRelativeTo(null); // set it in the center of the screen
		frame.setResizable(false); // will not allow the user to change the size the window
		frame.add(game);// created the board
		frame.setVisible(true);// this will make sure that the frame is visible 
		game.start();
	}
}