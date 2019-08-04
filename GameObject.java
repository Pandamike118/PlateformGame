package gamecore;

import java.awt.Graphics;
import java.awt.Rectangle;
/**
 * this class will apply to all other game object in so that when they 
 * are created they have a location and a ID in the list 
 * @author michaelantonacci
 *
 */
public abstract class GameObject {
	protected float X; 
	protected float Y; 
	protected ID id; 
	protected float volX, volY; 
	
	public float getX() {
		return X;
	}
	public float getY() {
		return Y;
	}
	public ID getId() {
		return id;
	}
	public void setX(float x) {
		X = x;
	}
	public void setY(float y) {
		Y = y;
	}
	public void setId(ID id) {
		this.id = id;
	}
	public float getVolX() {
		return volX;
	}
	public float getVolY() {
		return volY;
	}
	public void setVolX(float volX) {
		this.volX = volX;
	}
	public void setVolY(float volY) {
		this.volY = volY;
	}
	
	public GameObject(float x, float y, ID id) {
		super();
		X = x;
		Y = y;
		this.id = id;
	
	}
	
	public abstract Rectangle getBounds(); 
	public abstract void tick(); 
	public abstract void render(Graphics g); 
	
	
}
