package gamecore;

import java.awt.Canvas;
import java.awt.Color;

import java.awt.Graphics;

import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamecore.Emenies.Skeleton;
import gamecore.Towers.Archer.Archer;

public class Boardsimple extends Canvas implements Runnable{
	private static final long serialVersionUID = 3485185195587775746L;
	private Thread animator; // stating a Single thread
	public static final int WIDITH = 1280; // Setting the size of the window
	public static final int HEIGHT = 832; // Setting the size of the window
	private boolean running = false;// stating that running is equal to false
	private Handler handler; // stating a handler class that will take care of all the objects in the game
	private HUD hud; // stating the Health Up Display
	private Generate generate; // stating the wave generator
	private Menu menu; // stating the menu screen
	private Shop shop;
	public static BufferedImage spread_Sheet; 
	protected BufferedImage Image;
	
	

	public static int[][] newMap = { 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 } };
	// this is the game map path

	public static PathGrid Path = new PathGrid(newMap);// we are creating the game map and converting the 1 and 0's to
														// actually 64 x 64 tile with ID's

	// the main method which will call upon our board method which has everything
	// inside
	public static void main(String args[]) {
		new Boardsimple();

	}

	// this enum tell us the state if the game if it is either on the Menu "page" or
	// the Game "page" etc.
	public enum STATE {
		Menu, Game;
	}

	public STATE gameState = STATE.Menu;// stating the game state as Menu (the start of the game)
	public static Tile startTile = gamecore.PathGrid.Map[0][3]; // stating the start tile for all enemies
	
	/**
	 * Our Most important method this will load everything that the game will need
	 * to run and create all the enemies and other thing in side the game
	 */
	public Boardsimple() {
		handler = new Handler(); // init. a new handler

		menu = new Menu(this, handler); // init. the menu
		
		this.addMouseListener(menu);

		new Window(WIDITH, HEIGHT, "Tower Defense Game", this); // initizies the window
		
		BufferedImageLoader loader = new BufferedImageLoader(); 
		
		spread_Sheet = loader.getImage("/64SpreadSheet.png"); // loads the the spread sheet for the graphics
		Image = spread_Sheet.getSubimage(1, 1, 64, 64); // getting specific single 64x64 frame 
		hud = new HUD(); // init. the Health Up Display

		shop = new Shop(this, handler);
		this.addMouseListener(shop);
		generate = new Generate(handler, hud);
		
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
		// if we change the game state to game then it will run the tick method that
		// hold all thing the "game" needs
		if (gameState == STATE.Game) {
			handler.tick(); // run the tick method in the handler class
			hud.tick();// run the tick method in the Health Up Display class
			shop.tick();
			generate.tick(); //generate.tick(); // run the tick method in the Wave Generate

		} else {
			menu.tick(); // run the tick method in the menu class
		}
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

		g.setColor(Color.black);
		g.fillRect(0, 0, WIDITH, HEIGHT);// this will create the background to black

		if (gameState == STATE.Game) {
			for (int i = 0; i < gamecore.PathGrid.Map.length; i++) {
				for (int j = 0; j < gamecore.PathGrid.Map[i].length; j++) {
					Tile temp = gamecore.PathGrid.Map[i][j];
					// this will check if the the map grid is either 1 2 or 0 and will change the
					// color which then
					// will create the map
					if (temp.getID() == tileID.Grass) {
						g.drawImage(Image, (int)temp.getX(),  (int)temp.getY(), null);
					} else if (temp.getID() == tileID.Path) {
						
					} else if (temp.getID() == tileID.Water) {
						//Image = spread_Sheet.getSubimage(3,1, 64, 64);
					}
					
				}

				handler.render(g);// this will render all the objects in the game (if they are Game objects)
				shop.render(g);
				hud.render(g);// this will render the Health Up Display

			}
		} else {
			menu.render(g); // this will render the menu display
		}

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
