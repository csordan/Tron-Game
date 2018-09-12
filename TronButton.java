package TRON;

import acm.graphics.GCompound;
import acm.graphics.GDimension;
import acm.graphics.GImage;

public class TronButton extends GCompound{
	
	GImage button;
	GImage button1;
	
	public TronButton() {
		button = new GImage("Tron5.png");
		this.setLocation(165,130);
		this.button.setSize(615,449);
		this.add(button); 

	}
}
