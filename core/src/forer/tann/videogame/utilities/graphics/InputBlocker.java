package forer.tann.videogame.utilities.graphics;

import com.badlogic.gdx.Input;
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
				Main.self.currentScreen.pop();
				remove();
				return super.touchDown(event, x, y, pointer, button);
			}
		});
	}

}
