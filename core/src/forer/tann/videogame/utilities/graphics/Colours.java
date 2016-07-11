package forer.tann.videogame.utilities.graphics;

import com.badlogic.gdx.graphics.Color;

public class Colours {

	public static final Color DARK = make(22, 20, 19);
	public static final Color LIGHT = make(210, 199, 172);
	public static final Color RED = make(134, 53, 39);
	public static final Color GREEN = make(42, 74, 13);
	public static final Color BLUE = make(0, 36, 97);
	public static final Color zWHITE = make(255,255,255);
	
	public static Color make(int r, int g, int b){
		return new Color(r/255f, g/255f, b/255f, 1);
	}
	
	public static Color random(){
		return make((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
	}
	
}
