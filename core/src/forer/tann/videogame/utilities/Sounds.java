package forer.tann.videogame.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import forer.tann.videogame.Main;

public class Sounds {
	public enum SoundType{Good, Bad}
	private static Sound good = Gdx.audio.newSound(Gdx.files.internal("sfx/good.wav"));
	private static Sound bad = Gdx.audio.newSound(Gdx.files.internal("sfx/bad.wav"));
	private static void playSound(Sound sound){
		float variance = .12f;
		float add = (float) (Math.random()*variance*2-variance);
		sound.play(1, 1+add, 0);
	}
	
	public static void playSound(SoundType type){
		switch(type){
		case Good:
			playSound(good);
			break;
		case Bad:
			playSound(bad);
			break;
		default:
			break;
			
		}
	}

}
