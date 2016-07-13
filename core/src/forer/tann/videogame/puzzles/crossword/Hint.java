package forer.tann.videogame.puzzles.crossword;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import forer.tann.videogame.utilities.graphics.Draw;

public class Hint extends Actor{
	
	public static final int SIZE = 18;
	
	public Hint() {
		setSize(SIZE,SIZE);
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		Draw.fillActor(batch, this);
	}
	
}
