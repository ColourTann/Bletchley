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
	boolean complete;
	public CrosswordClue clue;
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
				if(direction==-1) return false;
				Crossword.get().setStart(CrosswordTile.this);
				return false;
			}
		});
		setColor(Colours.LIGHT);
	}

	boolean ordained;
	String correctLetter;
	String letter="";
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(highlight?Colours.DARK:getColor());
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		batch.setColor(highlight?Colours.LIGHT:Colours.DARK);

		TannFont.font.draw(batch, letter, getX()+getWidth()/2, (int)(getY()+getHeight()/2), Align.center);
		if(highlight){
			batch.setColor(Colours.LIGHT);
			Draw.drawRectangle(batch, getX()-1, getY()-1, getWidth()+2, getHeight()+2, 1);
		}
		super.draw(batch, parentAlpha);
	}

	public void setLetter(char c, boolean hidden) {
		if(hidden){
			correctLetter= String.valueOf(c);
		}
		else{
			letter= String.valueOf(c);
			ordained=true;
		}
	}

	public void type(char key) {
		if(!ordained) letter=String.valueOf(key);
	}

	boolean highlight;
	public void setHighlight(boolean highlight) {
		this.highlight=highlight;
	}

	int direction=-1;
	public void setClue(int dir) {
		this.direction=dir;
		setColor(dir==1?Colours.ORANGE:Colours.BROWN);
	}

	public boolean isCorrect() {
		return letter.equals(correctLetter);
	}

}
