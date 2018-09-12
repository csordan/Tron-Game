package TRON;

import java.awt.event.KeyListener;

public class Player {
	private int direction;
	private int locationX;
	private int locationY;
	private boolean crashed;
	
	public Player(int startingX, int startingY, int direction) {
		locationX = startingX;
		locationY = startingY;
		this.direction = direction;
	}
	
	//getters
	
	public int getLocationY() {
		return locationY;
	}
	
	public int getLocationX() {
		return locationX;
	}
	
	public int getDirection() {
		return direction;
	}
	
	//setters
	public void setLocationY(int locationY) {
		this.locationY = locationY;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void setLocationX(int locationX) {
		this.locationX = locationX;
	}
	
	//die & crash
	public boolean hasCrashed() {
		return crashed;
	}

	//hasCrashed determines that the vehicle has crashed
	public void setCrashed(boolean crashed) {
		this.crashed = crashed;
	}
	
	//move method
	public void move() {
		
		//left
		if (this.getDirection() == 1) {
			locationX--;
		}
		//up
		if (this.getDirection() == 2) {
			locationY--;
		}
		//right
		if (this.getDirection() == 3) {
			locationX++;
		}
		//down
		if (this.getDirection() == 4) {
			locationY++;
		}
	}
	
}
