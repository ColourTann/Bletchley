package forer.tann.videogame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;

public abstract class Screen extends Group{
	
	public Screen() {
		setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	public abstract void keyPressed(int keycode);
	public abstract void keyReleased(int keycode);
	public abstract void gamepadButtonPressed(int buttoncode);

	public void setActive(boolean b) {
	}

}
