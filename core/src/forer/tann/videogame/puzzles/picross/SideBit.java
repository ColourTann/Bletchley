package forer.tann.videogame.puzzles.picross;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;
import forer.tann.videogame.utilities.graphics.font.TannFont;
import forer.tann.videogame.utilities.graphics.font.TannPixelFont;

public class SideBit extends Group{
	static final int SIZE = 28;
	int[] digits;
	int horizontal;
	public SideBit(int[] digits, int position, int horizontal, float height) {
		this.digits=digits;
		this.digits= new int[]{1,3,2};
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
		batch.setColor(Colours.BROWN);
		Draw.fillActor(batch, this);
		
		batch.setColor(Colours.DARK);
		
		int letterDist = 8;
		int letterGap = 6;
		if(horizontal==1){
			for(int i=0;i<digits.length;i++){
				TannPixelFont.font.draw(batch, ""+digits[digits.length-i-1], getX()+getWidth()-letterDist*(i)-letterGap, getY()+getHeight()/2, Align.center);
			}
		}
		else{
			for(int i=0;i<digits.length;i++){
				TannPixelFont.font.draw(batch, ""+digits[i], getX()+getWidth()/2, getY()+getHeight()-letterDist*(i)-letterGap, Align.center);
			}
		}
		
		super.draw(batch, parentAlpha);
	}

}
