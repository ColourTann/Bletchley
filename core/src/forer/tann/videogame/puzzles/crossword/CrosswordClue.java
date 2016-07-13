package forer.tann.videogame.puzzles.crossword;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;
import forer.tann.videogame.utilities.graphics.TextRenderer;
import forer.tann.videogame.utilities.graphics.font.TannFont;

public class CrosswordClue extends Group{
	static final int WIDTH= 100;
	static final int GAP= 2;
	public CrosswordClue(String clue) {
		TextRenderer renderer = new TextRenderer(clue, TannFont.font, WIDTH-GAP*2, Align.left, Colours.DARK);
		setSize(WIDTH, renderer.getHeight()+GAP*2);
		renderer.setPosition(GAP, GAP);
		addActor(renderer);
		setColor(Colours.LIGHT);
//		setColor(colour);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(getColor());
		Draw.fillActor(batch, this);
		batch.setColor(Colours.zWHITE);
		super.draw(batch, parentAlpha);
	}
}
