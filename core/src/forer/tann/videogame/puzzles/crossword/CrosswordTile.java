package forer.tann.videogame.puzzles.crossword;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;

import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;
import forer.tann.videogame.utilities.graphics.font.TannFont;

public class CrosswordTile extends Actor{
	static final int SIZE = 9;
	int gridX, gridY;
	public int number;
	public CrosswordTile(int x, int y) {
		setup(x,y,-1);
	}
	
	public CrosswordTile(int x, int y, int number) {
		setup(x,y,number);
	}
	
	private void setup(int x, int y, int number){
		this.gridX=x;
		this.gridY=y;
		setSize(SIZE, SIZE);
		this.number=number;
		setPosition(Crossword.GAP+x*(SIZE+Crossword.GAP), Crossword.GAP+y*(SIZE+Crossword.GAP));
		addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return false;
			}
		});
		setColor(Colours.LIGHT);
	}
	
	
	
	String letter="";
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(getColor());
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		batch.setColor(Colours.DARK);
		TannFont.font.draw(batch, letter, getX()+getWidth()/2, (int)(getY()+getHeight()/2), Align.center);
		super.draw(batch, parentAlpha);
	}

	public void setLetter(char c) {
		letter = String.valueOf(c);
	}

}
