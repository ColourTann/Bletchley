package forer.tann.videogame.puzzles.picross;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;
import forer.tann.videogame.utilities.graphics.font.TannFont;
import forer.tann.videogame.utilities.graphics.font.TannPixelFont;

public class SideBit extends Group{
	static final int SIZE = 36;
	ArrayList<Integer> digits;
	int horizontal;
	public SideBit(int position, int horizontal, float height) {
		this.horizontal=horizontal;
		if(horizontal==0){
			
			setSize(PicrossTile.SIZE, SIZE);
			setPosition(PicrossTile.GAP + position * (PicrossTile.GAP+PicrossTile.SIZE), height);
		}
		else{
			setSize(SIZE, PicrossTile.SIZE);
			
			setPosition(-SIZE, PicrossTile.GAP + position * (PicrossTile.GAP+PicrossTile.SIZE));
		}
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.ORANGE);
		Draw.fillActor(batch, this);
		
		batch.setColor(Colours.DARK);
		
		int letterDist = 8;
		int letterGap = 6;
		if(horizontal==1){
			for(int i=0;i<digits.size();i++){
				TannPixelFont.font.draw(batch, ""+digits.get(digits.size()-i-1), getX()+getWidth()-letterDist*(i)-letterGap, getY()+getHeight()/2, Align.center);
			}
		}
		else{
			for(int i=0;i<digits.size();i++){
				TannPixelFont.font.draw(batch, ""+digits.get(i), getX()+getWidth()/2, getY()+letterDist*(i)+letterGap, Align.center);
			}
		}
		
		super.draw(batch, parentAlpha);
	}

}
