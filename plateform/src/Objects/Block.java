package Objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import gamecore.GameObject;
import gamecore.ID;

public class Block extends GameObject{

	public Block(float x, float y, ID id) {
		super(x, y, id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle((int)0, (int)0, (int)64, (int)64);
	}

	@Override
	public void tick() {
		
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.drawRect((int)X, (int)Y, 32, 32);
		
	}

}
