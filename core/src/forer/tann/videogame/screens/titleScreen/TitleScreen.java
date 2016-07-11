package forer.tann.videogame.screens.titleScreen;

import com.badlogic.gdx.graphics.g2d.Batch;

import forer.tann.videogame.Main;
import forer.tann.videogame.screens.Screen;
import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;
import forer.tann.videogame.utilities.graphics.TextRenderer;
import forer.tann.videogame.utilities.graphics.font.TannFont;

public class TitleScreen extends Screen{
NewsPaper paper;
	public TitleScreen() {
		paper = new NewsPaper(Main.width-20, Main.height-20);
		paper.setPosition(10, 10);
		addActor(paper);
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
		batch.setColor(Colours.DARK);
		Draw.fillActor(batch, this);
		super.draw(batch, parentAlpha);
	}

}
