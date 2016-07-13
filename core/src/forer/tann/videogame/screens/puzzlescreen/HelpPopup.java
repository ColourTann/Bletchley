package forer.tann.videogame.screens.puzzlescreen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import forer.tann.videogame.Main;
import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;
import forer.tann.videogame.utilities.graphics.TextRenderer;

public class HelpPopup extends Group{
	static final int WIDTH = 100;
	static final int GAP = 6;
	public HelpPopup(String text) {
		TextRenderer tr = new TextRenderer(text, WIDTH-GAP*2);
		setSize(WIDTH, tr.getHeight()+GAP*2);
		tr.setPosition(GAP, GAP);
		addActor(tr);
		addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Main.self.currentScreen.pop();
				return false;
			}
		});
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.DARK);
		Draw.fillActor(batch, this);
		batch.setColor(Colours.BROWN);
		Draw.drawRectangle(batch, getX(), getY(), getWidth(), getHeight(), 1);
		batch.setColor(Colours.zWHITE);
		super.draw(batch, parentAlpha);
	}
}
