package forer.tann.videogame.puzzles.picross;

import com.badlogic.gdx.graphics.g2d.Batch;

import forer.tann.videogame.screens.puzzlescreen.PuzzleScreen;
import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;

public class PicrossScreen extends PuzzleScreen {
	Picross picross;
	
	public PicrossScreen() {
		super("it's picross");
		addActor(picross = new Picross());
	}
	@Override
	public void draw (Batch batch, float parentAlpha) {
		batch.setColor(Colours.DARK);
		Draw.fillActor(batch, this);
		super.draw(batch, parentAlpha);
	}
}
