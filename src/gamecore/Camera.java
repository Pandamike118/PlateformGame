package gamecore;

public class Camera {
	private float x,y;

	Camera(float x , float y){ 
		this.setX(x); 
		this.setY(y); 
		
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void tick(GameObject player) {
		x = -player.getX() - Boardsimple.WIDITH/2; 		
	}
	

}
