package forer.tann.videogame.screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import forer.tann.videogame.puzzles.picross.Picross;

public class PuzzleTestScreen extends Screen{

	
	public PuzzleTestScreen() {
		addActor(new Picross());
	}
	
	@Override
	public void keyPressed(int keycode) {
	}

	@Override
	public void keyReleased(int keycode) {
	}

	@Override
	public void gamepadButtonPressed(int buttoncode) {
	}

	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
}
