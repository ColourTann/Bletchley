package forer.tann.videogame.utilities.graphics;

import com.badlogic.gdx.graphics.Color;

public class Colours {

	public static final Color LIGHT = make(0xefce98);
	public static final Color ORANGE = make(0xb97928);
	public static final Color BROWN = make(0x963b04);
	public static final Color DARK = make(0x260b00);
//	public static final Color OTHER = make(0x061400);
//	public static final Color RED = make(0xC4, 0x56, 0x56);
//	public static final Color GREEN = make(0x19, 0x72, 0x25);
	
	public static final Color[] ALL_COLOUR = new Color[]{LIGHT, ORANGE, BROWN, DARK};
	
//	public static final Color DARK = random();
//	public static final Color LIGHT = random();
//	public static final Color GREEN = random();
	public static final Color zWHITE = Color.WHITE;
	
	public static Color make(int rgb){
		int r = (rgb>>16)&0xff;
		int g = (rgb>>8)&0xff;
		int b = (rgb>>0)&0xff;
		return make(r,g,b);
	}
	
	public static Color make(int r, int g, int b){
		return new Color(r/255f, g/255f, b/255f, 1);
	}
	
	public static Color random(){
		return make((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
	}
	
}
