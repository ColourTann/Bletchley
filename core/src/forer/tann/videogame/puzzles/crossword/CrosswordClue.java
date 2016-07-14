package forer.tann.videogame.puzzles.crossword;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;

import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;
import forer.tann.videogame.utilities.graphics.TextRenderer;
import forer.tann.videogame.utilities.graphics.font.TannFont;

public class CrosswordClue extends Group{
	static final int WIDTH= 100;
	static final int GAP= 2;
	boolean complete;
	public CrosswordClue(String clue, final CrosswordTile tile) {
		tile.clue=this;
		TextRenderer renderer = new TextRenderer(clue, TannFont.font, WIDTH-GAP*2, Align.left, Colours.DARK);
		setSize(WIDTH, renderer.getHeight()+GAP*2);
		renderer.setPosition(GAP, GAP);
		addActor(renderer);
		setColor(Colours.LIGHT);
		addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Crossword.get().startTyping(tile);
				return false;
			}
		});
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(getColor());
		Draw.fillActor(batch, this);
		if(complete){
			batch.setColor(Colours.BROWN);
			Draw.drawLine(batch, getX(), getY(), getX()+getWidth(), getY()+getHeight()-1, 1);
		}
		batch.setColor(Colours.zWHITE);
		
		super.draw(batch, parentAlpha);
	}
	
	public void complete(){
		complete=true;
	}
}
