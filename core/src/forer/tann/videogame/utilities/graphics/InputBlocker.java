package forer.tann.videogame.utilities.graphics;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import forer.tann.videogame.Main;

public class InputBlocker extends Actor{

	private static InputBlocker self;
	public static InputBlocker get(){
		if(self==null) self = new InputBlocker();
		return self;
	}

	private InputBlocker() {
		setSize(Main.width, Main.height);
		addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				boolean popped = Main.self.pop();
				if(!popped){
					Main.self.currentScreen.pop();
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(0,0,0,.7f);
		Draw.fillActor(batch, this);
		super.draw(batch, parentAlpha);
	}

}
