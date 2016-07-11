package forer.tann.videogame.utilities.graphics.font;

import com.badlogic.gdx.graphics.g2d.Batch;

import forer.tann.videogame.Main;

public abstract class TannFont {

	public static TannPixelFont font = new TannPixelFont(Main.atlas.findRegion("fonts/font"), 1);
	public static TannFont bigFont = new TannPixelFont(Main.atlas.findRegion("fonts/font"), 2);
	public static TannFont biggerFont = new TannPixelFont(Main.atlas.findRegion("fonts/font"), 3);
	public static TannFont biggestFont = new TannPixelFont(Main.atlas.findRegion("fonts/font"), 4);
	
	public abstract void draw(Batch batch, CharSequence text, float x, float y);
	public abstract void draw(Batch batch, CharSequence text, float x, float y, int align);
	public abstract void unscaledDraw(Batch batch, CharSequence text, float x, float y);
	
	public abstract int getWidth(CharSequence text);
	public abstract int getHeight();
	public abstract int getLineHeight(); 
	public abstract int getSpaceWidth();
}
