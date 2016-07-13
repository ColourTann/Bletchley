package forer.tann.videogame.utilities.graphics;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class ConvertImagesMain extends ApplicationAdapter {

	@Override
	public void create() {
		FileHandle handle = Gdx.files.internal("toconvert");
		for(FileHandle f: handle.list()){
			Convertilator.convertilate(f);
		}
		System.exit(0);;
	}
	
}
