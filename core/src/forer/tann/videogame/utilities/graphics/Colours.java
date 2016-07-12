package forer.tann.videogame.utilities.graphics;

import com.badlogic.gdx.graphics.Color;

public class Colours {

	public static final Color DARK = make(0x05, 0x0F, 0x21);
	public static final Color LIGHT = make(0xF4, 0xF0, 0xE1);
	public static final Color RED = make(0xC4, 0x56, 0x56);
	public static final Color GREEN = make(0x19, 0x72, 0x25);
	public static final Color zWHITE = make(255,255,255);
	
	public static Color make(int r, int g, int b){
		return new Color(r/255f, g/255f, b/255f, 1);
	}
	
	public static Color random(){
		return make((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
	}
	
}
