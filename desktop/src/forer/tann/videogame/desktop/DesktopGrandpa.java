package forer.tann.videogame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import forer.tann.videogame.Main;

public class DesktopGrandpa {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width= 1;
		config.height = 1;
		new LwjglApplication(new Main(), config);
	}
}
