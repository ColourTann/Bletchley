package forer.tann.videogame.screens.puzzlescreen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;
import forer.tann.videogame.utilities.graphics.TextRenderer;

public class HintPopup extends Group{
	static final int GAP = 0;
	public HintPopup() {
		TextRenderer tr = new TextRenderer("Use grandad's intuition? [glasses][n][glasses][glasses]", 100);
		addActor(tr);
		tr.setPosition(GAP, GAP);
		setSize(tr.getWidth()+GAP*2, tr.getHeight()+GAP*2);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.DARK);
		Draw.fillActor(batch, this);
		batch.setColor(Colours.BROWN);
		Draw.drawRectangle(batch, getX(), getY(), getWidth(), getHeight(), 1);
		batch.setColor(Colours.zWHITE);
		super.draw(batch, parentAlpha);
	}
}
