package forer.tann.videogame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import forer.tann.videogame.Main;
import forer.tann.videogame.utilities.graphics.ConvertImagesMain;

public class ConvertImages {
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width= 1;
		config.height = 1;
		new LwjglApplication(new ConvertImagesMain(), config);
	}
}
