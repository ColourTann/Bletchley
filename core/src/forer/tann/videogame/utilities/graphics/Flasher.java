package forer.tann.videogame.utilities.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Flasher extends Actor {
	Color col;
	boolean box;
	int lineWidth;

	public Flasher(Actor a, Color col, float fadeTime){
		this(0, 0, a.getWidth(), a.getHeight(), col, fadeTime); 
	}
	
	public Flasher(float x, float y, float width, float height) {
		this(x, y, width, height, Colours.BROWN, .8f);
	}

	public Flasher(float x, float y, float width, float height, Color col) {
		this(x, y, width, height, col, .8f);
	}

	public Flasher(float x, float y, float width, float height, Color col,
			float fadeTime) {
		this(x, y, width, height, col, fadeTime, -1);
	}

	public Flasher(float x, float y, float width, float height, Color col,
			float fadeTime, int lineWidth) {
		box = lineWidth > 0;
		this.lineWidth = lineWidth;
		setTouchable(Touchable.disabled);
		setBounds(x, y, width, height);
		addAction(Actions.fadeOut(fadeTime, Interpolation.pow2));
		this.col = col;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Draw.setBatchColour(batch, col, getColor().a);
		
		if (box) Draw.drawRectangle(batch, getX(), getY(), getWidth(), getHeight(), lineWidth);
		else Draw.draw(batch, Draw.getSq(), getX(), getY(), getWidth(), getHeight());

		if (parentAlpha <= .001f) getParent().removeActor(this);
	}

}
