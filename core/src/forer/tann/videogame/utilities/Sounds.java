package forer.tann.videogame.utilities;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import forer.tann.videogame.Main;
import forer.tann.videogame.utilities.ui.Slider;

public class Sounds {
	public enum SoundType{Good, Bad, CrosswordLetter, On, Off}
	private static Sound good = Gdx.audio.newSound(Gdx.files.internal("sfx/good.wav"));
	private static Sound on = Gdx.audio.newSound(Gdx.files.internal("sfx/on.wav"));
	private static Sound off = Gdx.audio.newSound(Gdx.files.internal("sfx/off.wav"));
	private static Sound bad = Gdx.audio.newSound(Gdx.files.internal("sfx/bad.wav"));
	private static Sound type = Gdx.audio.newSound(Gdx.files.internal("sfx/type.wav"));
	private static void playSound(Sound sound){
		float variance = .12f;
		float add = (float) (Math.random()*variance*2-variance);
		sound.play(1, 1+add, 0);
	}
	
	static Music current;
	public static void playMusic(String music){
		if(current!=null){
			fadeOut(current);
		}
		current = Gdx.audio.newMusic(Gdx.files.internal("music/"+music+".mp3"));
		fadeIn(current);
		current.play();
		current.setLooping(true);
	}
	
	static void fadeOut(Music m){
		fadeOuts.add(m);
	}
	
	static void fadeIn(Music m){
		fadeIns.add(m);
		m.setVolume(0);;
	}
	
	static ArrayList<Music> fadeIns = new ArrayList<Music>();
	static ArrayList<Music> fadeOuts = new ArrayList<Music>();
	
	public static void tick(float delta){
		for(int i=fadeOuts.size()-1;i>=0;i--){
			Music m = fadeOuts.get(i);
			if(m.getVolume()<=0){
				fadeOuts.remove(m);
				m.stop();
				m.dispose();
			}
			else{
				m.setVolume(Math.max(0, m.getVolume()-delta));
			}
		}
		
		for(int i=fadeIns.size()-1;i>=0;i--){
			Music m = fadeIns.get(i);
			if(m.getVolume()<Slider.music.getValue()){
				m.setVolume(Math.min(Slider.music.getValue(), m.getVolume()+delta));
			}
			else{
				fadeIns.remove(i);
			}
		}
	}
	
	public static void playSound(SoundType sType){
		switch(sType){
		case CrosswordLetter:
			playSound(type);
			break;
		case Good:
			playSound(good);
			break;
		case Bad:
			playSound(bad);
			break;
		case Off:
			playSound(on);
			break;
		case On:
			playSound(off);
			break;
		default:
			break;
			
		}
	}

	public static void updateMusicVolume() {
		if(current!=null){
			current.setVolume(Slider.music.getValue());
		}
	}

}
