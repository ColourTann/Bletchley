package forer.tann.videogame.puzzles.wordsearch;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;

import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;
import forer.tann.videogame.utilities.graphics.font.TannFont;

public class WordSearchTile extends Group{
	public static final int SIZE = 16;
	public static final int GAP = 1;
	public int x, y;
	public WordSearchTile(int x, int y) {
		this.x=x;
		this.y=y;
		setSize(SIZE, SIZE);
		setPosition(GAP + x*(SIZE+GAP), GAP + y*(SIZE+GAP));
		addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				((WordSearch)getParent()).startDrag(WordSearchTile.this);
				return true;
			}
			
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				if(pointer==0) return;
				((WordSearch)getParent()).hover(WordSearchTile.this);
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				((WordSearch)getParent()).endDrag(WordSearchTile.this);
			}
		});
	}

	char content;
	public void setChar(char c) {
		this.content=c;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(highlight?Colours.LIGHT:Colours.ORANGE);
		Draw.fillActor(batch, this);
		batch.setColor(Colours.DARK);
		TannFont.bigFont.draw(batch, content+"", getX()+getWidth()/2, getY()+getHeight()/2, Align.center);
		super.draw(batch, parentAlpha);
	}
	
	
	boolean highlight;
	public void highlight(boolean on) {
		this.highlight=on;
	}

	
	
}
