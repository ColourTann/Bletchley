package forer.tann.videogame.puzzles.crossword;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;

public class CrosswordTile extends Actor{
	static final int BORDER = 1;
	static final int SIZE = 10;
	int gridX, gridY;
	public CrosswordTile(int x, int y) {
		this.gridX=x;
		this.gridY=y;
		setSize(SIZE, SIZE);
		setPosition(x*SIZE, y*SIZE);
		addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("you tapped on "+gridX+":"+gridY);
				return super.touchDown(event, x, y, pointer, button);
			}
		});
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		
		batch.setColor(Colours.DARK);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		
		batch.setColor(Colours.LIGHT);
		Draw.fillRectangle(batch, getX()+BORDER, getY()+BORDER, getWidth()-BORDER*2, getHeight()-BORDER*2);
		super.draw(batch, parentAlpha);
	}

}
