package gamecore;

import java.awt.Canvas;
import java.awt.Color;

import java.awt.Graphics;
//import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import Objects.Block;
import Objects.Player;
import Objects.Enemy.Enemy;


public class Boardsimple extends Canvas implements Runnable{
	private static final long serialVersionUID = 3485185195587775746L;
	private Thread animator; // stating a Single thread
	public static final int WIDITH = 1280; // Setting the size of the window
	public static final int HEIGHT = 832; // Setting the size of the window
	private boolean running = false;// stating that running is equal to false
	private Handler handler; // stating a handler class that will take care of all the objects in the game
	private BufferedImage level = null; 
	
	

	
	// the main method which will call upon our board method which has everything
	// inside
	public static void main(String args[]) {
		new Boardsimple();

	}
	
	private void loadImageLevel(BufferedImage image){ 
		int widith = image.getWidth(); 
		int height = image.getHeight(); 
		
		System.out.println("width and height of level" +widith + " "+ height);
		
		for (int xx=0; xx<height; xx++) { 
			for(int yy= 0; yy< widith; yy++ ) { 
				int pixel = image.getRGB(xx, yy); 
				int red = (pixel >> 16) & 0xff; 
				int green = (pixel >> 8) & 0xff; 
				int blue = (pixel) & 0xff; 
				
				if (red == 255 && green == 255 && blue == 255) { 
					handler.addObject(new Block(xx*32, yy*32 , ID.Floor));
				}
			}
		} 
		}

	
	/**
	 * Our Most important method this will load everything that the game will need
	 * to run and create all objects inside of the game. the only reason we do not have this in the main class is 
	 * because of system loading and so that it not called multiple times 
	 */
	public Boardsimple() {
		
		BufferedImageLoader loader = new BufferedImageLoader(); 
		level = loader.loadImage("/level1.png"); 
		handler = new Handler(); // init. a new handler
		new Window(WIDITH, HEIGHT, "plateformer prototype", this); //init. the window 
		
		loadImageLevel(level); 
		handler.addObject(new Player(100, 100,ID.Player));
		handler.addObject(new Enemy(400, 100, ID.Enemy));
		
		
	}

	/**
	 * this method Start will start the thread so we are able to have smooth
	 * animation
	 */
	public synchronized void start() {
		animator = new Thread(this);// initizes a new thread
		animator.start(); // starts the thread and runs the mirco methods for each game object 
		running = true; // tells us if the thread is running or not
	}

	/*
	 * this method Stop will stop the thread so we are able to stop the game/repaint
	 */
	public synchronized void stop() {
		// this try catch is just there to make sure if we do stop it doesn't give us
		// any error and if it does it will tell us and not break the game
		try {
			animator.join();// stops the thread
			running = false; // tells us if the thread is running or not
		} catch (Exception e) {
			e.printStackTrace();// just trace the error and prints it in the console
		}

	}

	/**
	 * this method is the thread running method which will update any graphic on are
	 * screen to a specific refresh rate which is 60 frames per sec.
	 */
	@Override
	public void run() {
		long lasttime = System.nanoTime(), // the time before refreshing
				timer = System.currentTimeMillis(); // the current time of the refresh
		double amountOfTicks = 60.0, // how many time it will refresh
				ns = 1000000000 / amountOfTicks, // ns is just the amount of refresh ticks in nano seconds
				delta = 0; // delta is it difference between frame updates
		int frame = 0; // this will be the frame counter
		// while it is running the thread we are going to update the delta and the
		// graphic else it will stop the thread
		while (running) {
			long current = System.nanoTime();
			delta += (current - lasttime) / ns;// delta is the change in time from each frame
			lasttime = current;
			while (delta >= 1) {
				tick();
				delta--;
			}
			// so we update the graphic of the game and if it is false we will not render
			// the new frame
			if (running) {
				render(); // will render the graphics
			}
			// add the frame counter so we can keep track of the load of the game 
			frame++;

			// so basically this tests if the game lags out so it
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS " + frame);
				// reset the frame count
				frame = 0;
			}
		}
		stop(); // this will stop the thread
	}

	/**
	 * this method will run the tick method in the handler class which then run all
	 * the tick methods in the objects that in the game
	 */
	private void tick() {

		
			handler.tick(); // run the tick method in the handler class
			
	}

	/*
	 * This Method will render the graphic of the game and also buffer the graphic
	 * so they don't update to quickly since we be playing with nano seconds and the render rate would be to high for changes
	 */
	private void render() {
		BufferStrategy Buffer = this.getBufferStrategy(); // this buffer strategy will be set to null
		if (Buffer == null) { // this if buffer is set to null then were are going to make the 3 buffers so
								// we don't overload everything trying to be add at the same time when first
								// loading the game
			this.createBufferStrategy(3);
			return;// this will repeat the method
		}

		Graphics g = Buffer.getDrawGraphics(); // graphics
		//Graphics2D g2d = (Graphics2D)g; 

		
		/////////////////////////////////////////////////////////////////////
		//Draw Here
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDITH, HEIGHT);// this will create the background to black
		
		
				handler.render(g);// this will render all the objects in the game (if they are Game objects)
		/////////////////////////////////////////////////////////////////////		
		
		
		g.dispose(); // we will dispose of the old graphics from previous frame
		Buffer.show(); // will show the new updated graphics
	}

	/**
	 * This Method to clamp a a variable from going out of the range given
	 * 
	 * @param var - being the quaint changing
	 * @param min - being the minimum value that the variable can go
	 * @param max - being the maximum value that the variable can go
	 * @return - it will return the maximum or minimum based on what the variable is
	 */
	public static float clamp(float var, float min, float max) {
		if (var >= max)
			return var = max;
		else if (var <= min)
			return var = min;
		else
			return var;

	}
/** 
 * This method test for hovering 
 * @param mx - The mouse current X coord.
 * @param my -The mouse current Y coord.
 * @param x - The objects X (start) coord. 
 * @param y - The objects Y (start) coord.
 * @param Widith - The objects widith
 * @param Height - the objects height 
 * @return
 */
	public static boolean mouseOver(int mx, int my, int x, int y, int Widith, int Height) {
		if (mx > x && mx < x + Widith) {
			if (my > y && my < y + Height) {
				return true;
			} else
				return false;
		} else
			return false;
	}
}
