package forer.tann.videogame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;

import forer.tann.videogame.Main;
import forer.tann.videogame.Main.TransitionType;
import forer.tann.videogame.utilities.graphics.Button;
import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;
import forer.tann.videogame.utilities.graphics.InputBlocker;
import forer.tann.videogame.utilities.graphics.TextRenderer;
import forer.tann.videogame.utilities.graphics.font.TannFont;
import forer.tann.videogame.utilities.ui.Slider;

public class PauseScreen extends Group{
	private static int w=120,h=70;
	private static PauseScreen self;
	public Group musicAttribution;
	public static PauseScreen get(){
		if(self==null)self=new PauseScreen();
		return self;
	}
	
	private PauseScreen(){
		setSize(w,h);
		setPosition(Main.width/2-w/2, Main.height/2-h/2);
		
		addAttribution("Made by tann", "https://twitter.com/colourtann", (int)(getWidth()/2), getY(.77f), 100);
		
		String music = "Music licensed under Creative Commons Attribution[n][n]"
				+ "The Encouragement Stick by Doctor Turtle[n][n]"
				+ "April and May by Kai Engel[n][n]"
				+ "Other music in the public domain";
		TextRenderer tr = new TextRenderer(music, Main.width/5*4);
		musicAttribution = new Group(){
			@Override
			public void draw(Batch batch, float parentAlpha) {
				batch.setColor(Colours.DARK);
				Draw.fillActor(batch, this);
				batch.setColor(Colours.BROWN);
				Draw.drawRectangle(batch, getX(), getY(), getWidth(), getHeight(), 1);
				batch.setColor(Colours.zWHITE);
				super.draw(batch, parentAlpha);
			}
		};
		musicAttribution.addActor(tr);
		tr.setPosition(2, 10);
		musicAttribution.setSize(tr.getWidth()+4, tr.getHeight()+20);
		musicAttribution.setPosition(Main.width/2-musicAttribution.getWidth()/2-getX(), Main.height/2-musicAttribution.getHeight()/2-getY());
		
		Actor a = new Actor(){
			@Override
			public void draw(Batch batch, float parentAlpha) {
				batch.setColor(Colours.DARK);
				Draw.fillActor(batch, this);
				batch.setColor(Colours.ORANGE);
				Draw.drawRectangle(batch, getX(), getY(), getWidth(), getHeight(), 1);
				batch.setColor(Colours.LIGHT);
				TannFont.font.draw(batch, "music attributions", getX()+getWidth()/2, getY()+getHeight()/2, Align.center);
			}
		};
		a.setSize(100, 11);
		addActor(a);
		a.setPosition(getWidth()/2-a.getWidth()/2, getY(.53f));
		a.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				addActor(musicAttribution);
				return false;
			}
		});
		
		
		Slider.music.setPosition(w/2-Slider.music.getWidth()/2, getY(.32f));
		addActor(Slider.music);
		
		
		int numScales=5;
		int increment = w/(numScales+1);
		for(int i=0;i<numScales;i++) addScaleButton(i+1, increment*(i+1), getY(.15f), increment-2);
	}
	
	private void addScaleButton(final int scale, int x, int y, int width){
		Button t = new Button("X"+scale, width);
		t.setClickAction(new Runnable() {
			@Override
			public void run() {
				Main.self.setScale(scale);
			}
		});
		t.setPosition((int)(x-t.getWidth()/2), (int)(y-t.getHeight()/2));
		addActor(t);
	}


	private void addAttribution(String text, final String url, int x, int y, int width){
		Button b = new Button(text, width);
		b.setPosition(x-b.getWidth()/2, y);
		b.setClickAction(new Runnable() {
			@Override
			public void run() {
				Gdx.net.openURI(url);
			}
		});
		addActor(b);		
	}
	
	private int getY(float ratio){
		return (int) (getHeight()*ratio);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.LIGHT);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		
		batch.setColor(Colours.ORANGE);
		Draw.drawRectangle(batch, getX(), getY(), getWidth(), getHeight(), 1);
		super.draw(batch, parentAlpha);
	}

	public boolean pop() {
		if(musicAttribution.hasParent()){
			musicAttribution.remove();
			return true;
		}
		else if(getParent()!=null){
			Main.self.toggleMenu();
			return true;
		}
		return false;
	}
}