package Objects;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import gamecore.*;
public class Player extends GameObject{

	public Player(float x, float y, ID id) {
		super(x, y, id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle((int)0, (int)0, (int)32, (int)32); 
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.BLUE);
		g.fillRect((int)X, (int)Y, (int)32, (int)32);
	}

	
	
}
