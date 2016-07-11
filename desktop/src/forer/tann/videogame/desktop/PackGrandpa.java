package forer.tann.videogame.desktop;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class PackGrandpa {
	public static void main(String[] args){
		Settings settings = new Settings();
		settings.combineSubdirectories = true;
		settings.filterMag=TextureFilter.Nearest;
		settings.filterMin=TextureFilter.Nearest;
		TexturePacker.process(settings, "../images", "../core/assets", "atlas_image");
	}
}
