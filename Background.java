package TRON; 

import acm.graphics.GCompound;
import acm.graphics.GImage;

public class Background extends GCompound{
	
	GImage image;
	
	public Background() {
		image = new GImage("Tron1.png");
		this.setLocation(0,0);
		this.image.setSize(1000, 700);
		this.add(image);
	}
}
