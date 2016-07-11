package forer.tann.videogame.utilities.graphics.font;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class TannRegularFont extends TannFont{

	BitmapFont font;
	
	public TannRegularFont(BitmapFont font) {
	}
	
	@Override
	public void draw(Batch batch, CharSequence text, float x, float y) {
		font.draw(batch, text, x, y);
	}

	@Override
	public void draw(Batch batch, CharSequence text, float x, float y, int align) {
		switch(align){
		
		}
		font.draw(batch, text, x, y);
	}

	@Override
	public void unscaledDraw(Batch batch, CharSequence text, float x, float y) {
		draw(batch,text,x,y);
	}

	@Override
	public int getWidth(CharSequence text) {
		return 0;
	}

	@Override
	public int getHeight() {
		return 0;
	}

	@Override
	public int getLineHeight() {
		return 0;
	}

	@Override
	public int getSpaceWidth() {
		return 0;
	}

}
