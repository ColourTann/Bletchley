package forer.tann.videogame.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import forer.tann.videogame.Main;

public class DesktopGrandpa {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable=false;
		config.title="Battle of Wits";
		config.width= 1;
		config.height = 1;
		config.addIcon("help.png", FileType.Internal);
		new LwjglApplication(new Main(), config);
	}
}
