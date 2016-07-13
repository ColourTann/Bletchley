package forer.tann.videogame.screens.dialogue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import forer.tann.videogame.Main;
import forer.tann.videogame.screens.Screen;
import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;
import forer.tann.videogame.utilities.graphics.TextRenderer;
import forer.tann.videogame.utilities.graphics.font.TannFont;

public class DialogueScreen extends Screen{
	public static final int GAP = 35;
	public static final int HORIZONTAL_GAP = 3;
	TextureRegion image;
	TextRenderer text;

	public interface Procedure{
		void action();
	}

	public DialogueScreen(String message, String picture) {
		setup(message,picture,null);
	}

	public DialogueScreen(String message, String picture, Runnable r) {
		setup(message,picture,r);
	}

	public void setup(String message, String picture, final Runnable r) {
		image = Main.atlas.findRegion("pixelimages/"+picture);
		text = new TextRenderer(message, TannFont.bigFont, Main.width-HORIZONTAL_GAP*2);
		text.setPosition(HORIZONTAL_GAP, (int)(GAP/2-text.getHeight()/2));
		addActor(text);
		addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					Main.self.nextScreen();
				return false;
			}
		});

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Draw.setBatchColour(batch, Colours.DARK, getColor().a);
		Draw.fillActor(batch, this);
		Draw.setBatchColour(batch, Colours.zWHITE, getColor().a);
		batch.draw(image, (int)getX(), GAP);
		super.draw(batch, parentAlpha);
	}

	@Override
	public void keyPressed(int keycode) {
	}

	@Override
	public void keyReleased(int keycode) {
	}


}
