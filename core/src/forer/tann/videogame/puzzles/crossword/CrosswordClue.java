package forer.tann.videogame.puzzles.crossword;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;

import forer.tann.videogame.Main;
import forer.tann.videogame.utilities.Sounds;
import forer.tann.videogame.utilities.Sounds.SoundType;
import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;
import forer.tann.videogame.utilities.graphics.TextRenderer;
import forer.tann.videogame.utilities.graphics.font.TannFont;

public class CrosswordClue extends Group{
	static final int WIDTH= 85;
	static final int GAP= 2;
	boolean complete;
	CrosswordTile tile;
	public CrosswordClue(String clue, final CrosswordTile tile, int colour) {
		this.tile=tile;
		tile.setClue(tile.direction, colour);
		tile.clue=this;
		TextRenderer renderer = new TextRenderer(clue, TannFont.font, WIDTH-GAP*2, Align.left, Colours.DARK);
		setSize(WIDTH, renderer.getHeight()+GAP*2);
		renderer.setPosition(GAP, GAP);
		addActor(renderer);
		setColor(Colours.LIGHT);
		addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Crossword.get().startTyping(tile);
				return false;
			}
		});
	}
	
	TextureRegion tick = Main.atlas.findRegion("tick");
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(getColor());
		Draw.fillActor(batch, this);
		if(complete){
//			batch.setColor(Colours.BROWN);
//			Draw.drawLine(batch, getX(), getY(), getX()+getWidth(), getY()+getHeight()-1, 1);
//			Draw.drawRectangle(batch, getX(), getY(), getWidth(), getHeight(), 1);
			batch.setColor(Colours.BROWN);
			batch.draw(tick, getX()+getWidth()-10, getY()+1);
		}
		batch.setColor(Colours.zWHITE);
		
		super.draw(batch, parentAlpha);
	}
	
	public void complete(){
		Sounds.playSound(SoundType.Good);
		complete=true;
		CrosswordScreen.get().checkComplete();
	}
}
