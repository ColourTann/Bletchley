package forer.tann.videogame.utilities.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;

import forer.tann.videogame.utilities.graphics.font.TannFont;

public class Button extends Actor{
	TextureRegion tr;
	String text;
	public Button(TextureRegion tr) {
		this.tr=tr;
		setSize(tr.getRegionWidth(), tr.getRegionHeight());
	}
	
	public static final int TEXT_GAP=3;
	
	public Button(String text) {
		this.text=text;
		setSize(TannFont.font.getWidth(text)+TEXT_GAP*2, TannFont.font.getHeight()+TEXT_GAP*2);
	}
	
	public Button(String text, int width) {
		this.text=text;
		setSize(width, TannFont.font.getHeight()+TEXT_GAP*2);
	}
	
	public void setClickAction(final Runnable r){
		addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				r.run();
				return false;
			}
		});
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if(tr!=null){
			batch.setColor(Colours.zWHITE);
			batch.draw(tr, getX(), getY());	
		}
		else{
			batch.setColor(Colours.DARK);
			Draw.fillActor(batch, this);
			batch.setColor(Colours.ORANGE);
			Draw.drawRectangle(batch, getX(), getY(), getWidth(), getHeight(), 1);
			batch.setColor(Colours.LIGHT);
			TannFont.font.draw(batch, text, getX()+getWidth()/2, getY()+getHeight()/2, Align.center);
		}
		
		super.draw(batch, parentAlpha);
	}
}
