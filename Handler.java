package gamecore;

import java.awt.Graphics;
import java.util.ArrayList;


public class Handler {

	public ArrayList<GameObject> Objects = new ArrayList<GameObject>(); // this the list of all the object in the Whole Game
	/**
	 * this Method will go through each object in he whole game and run it tick method 
	 */
	public void tick() {
		for (int j = 0; j < Objects.size(); j++) {
			GameObject tempObj = Objects.get(j);
			
			tempObj.tick(); 
		}
	}
	
	/**
	 * this method will go though each object in he whole game and run its render method 
	 * @param g
	 */
	public void render(Graphics g) {
		for (int j = 0; j < Objects.size(); j++) {
			GameObject tempObj = Objects.get(j);
			
			tempObj.render(g); 
		}
	}
	/**
	 * this will add a new object to the Objects list it requires a object to be able to add 
	 * @param skeleton
	 */
	public void addObject(GameObject object) {
		this.Objects.add(object); 
	}
	/**
	 * this will add a remove a object to the Objects list it requires a object to be able to remove
	 * @param object
	 */
	public void removeObject(GameObject object) {
		this.Objects.remove(object); 
	}
}
