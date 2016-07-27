package forer.tann.videogame.puzzles.wordsearch;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;
import forer.tann.videogame.utilities.graphics.font.TannFont;


public class WordSearchInput extends Group{

	static final int HEIGHT = 16, WIDTH=65;
	public String word="";
	
	public WordSearchInput() {
		setSize(WIDTH, HEIGHT);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.BROWN);
		Draw.fillActor(batch, this);
		batch.setColor(Colours.LIGHT);
		TannFont.bigFont.draw(batch, word, getX()+2, getY()+getHeight()/2-TannFont.bigFont.getHeight()/2);
		super.draw(batch, parentAlpha);
	}

}
