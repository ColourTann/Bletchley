package forer.tann.videogame.puzzles.picross;

import com.badlogic.gdx.graphics.g2d.Batch;

import forer.tann.videogame.puzzles.Puzzle;
import forer.tann.videogame.puzzles.picross.PicrossTile.PicrossTileState;
import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;

public class Picross extends Puzzle{

	int tilesAcross = 10;
	int tilesDown = 10;
	PicrossTile[][] tiles = new PicrossTile[tilesAcross][tilesDown];
	
	public Picross() {
		setSize((PicrossTile.SIZE+PicrossTile.GAP)*tilesAcross + PicrossTile.GAP, (PicrossTile.SIZE+PicrossTile.GAP)*tilesDown+ PicrossTile.GAP);
		for(int x=0;x<tilesAcross;x++){
			for(int y=0;y<tilesDown;y++){
				PicrossTile p =new PicrossTile(x, y);; 
				tiles[x][y] = p;
				addActor(p);
			}
		}
		for(int x=0;x<tilesAcross;x++){
			SideBit s = new SideBit(null, x, 0, getHeight());
			addActor(s);
			SideBit s1 = new SideBit(null, x, 1, getHeight());
			addActor(s1);
		}

	}

	
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.BROWN);
		Draw.fillActor(batch, this);
		super.draw(batch, parentAlpha);
	};

	PicrossTileState valueToSet;
	public void setupDrag(PicrossTileState state) {
		valueToSet=state;
	}

	public PicrossTileState getValueToSet(){
		return valueToSet;
	}


}
