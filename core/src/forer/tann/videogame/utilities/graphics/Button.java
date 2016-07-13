package forer.tann.videogame.utilities.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Button extends Actor{
	TextureRegion tr;
	public Button(TextureRegion tr) {
		this.tr=tr;
		setSize(tr.getRegionWidth(), tr.getRegionHeight());
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
		batch.setColor(Colours.zWHITE);
		batch.draw(tr, getX(), getY());
		super.draw(batch, parentAlpha);
	}
}
