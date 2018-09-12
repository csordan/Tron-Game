package TRON; 

public class PowerUp {
	private int locationX;
	private int locationY;
	private int powerTimer;
	//also include type for different types of PowerUps
	private double type;

	public PowerUp(int locationX, int locationY, double type) {
		this.locationX = locationX;
		this.locationY = locationY;
		this.type = type;
	}
	
	public double getType() {
		return type;
	}
	public void setType(double type) {
		this.type = type;
	}
	//getters/setters
	public int getPowerTimer() {
		return powerTimer;
	}
	public void setPowerTimer(int powerTimer) {
		this.powerTimer = powerTimer;
	}
	public int getLocationX() {
		return locationX;
	}
	public void setLocationX(int locationX) {
		this.locationX = locationX;
	}
	public int getLocationY() {
		return locationY;
	}
	public void setLocationY(int locationY) {
		this.locationY = locationY;
	}
	
	public void relocate(int field[][], int WIDTH, int HEIGHT) {
		this.setLocationX((int)(Math.random()*(WIDTH-1) + 1));
		this.setLocationY((int)(Math.random()*(HEIGHT-1) + 1));
		
		while (field[this.getLocationY()][this.getLocationX()] != 0) {
			this.setLocationX((int)(Math.random()*(WIDTH-1) + 1));
			this.setLocationY((int)(Math.random()*(HEIGHT-1) + 1));
		}
		this.setType(Math.random() * 3);
	}
	
	
}
