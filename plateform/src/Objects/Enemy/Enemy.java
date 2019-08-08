package Objects.Enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import Objects.Player;
import gamecore.GameObject;
import gamecore.Handler;
import gamecore.ID;


public class Enemy extends GameObject{
	private final float GRAVITY = 0.5f; 
	private final float MAX_SPEED = 10f; 
	private float lifePoints =10; 
	//private float attackPoints = 10; 
	private Handler handler; 
	public Enemy(float x, float y, ID id) {
		super(x, y, id);
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle((int)0, (int)0, (int)32, (int)32);
	}
	
	private void collision() {

		for (int i = 0; i < handler.Objects.size(); i++) {
			GameObject tempObject = handler.Objects.get(i);
			if (tempObject.getId() == ID.Player) { 
				Player tempDamage = (Player) tempObject;
				
				if (getBounds().intersects(tempObject.getBounds())) {
					lifePoints -= tempDamage.getDamage(); 
					
					handler.removeObject(tempObject);
					
					if (lifePoints == 0) {
						handler.removeObject(this);
					}

				}
			}
		}
	}
	@Override
	public void tick() {
		Y += volY; 
		X += volX; 
		if (falling || jumping) { 
			volY += GRAVITY; 
			
			if (volY > MAX_SPEED)
				volY = MAX_SPEED; 
		}
	}
	

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.RED);
		g.fillRect((int)X, (int)Y, (int)32, (int)32);
	}

}
