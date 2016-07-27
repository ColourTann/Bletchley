package forer.tann.videogame.puzzles.picross;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import forer.tann.videogame.puzzles.Puzzle;
import forer.tann.videogame.puzzles.picross.PicrossTile.PicrossTileState;
import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;

public class Picross extends Puzzle{

	int tilesAcross = 10;
	int tilesDown = 10;
	PicrossTile[][] tiles;

	public Picross(String path) {
		Texture tex = new Texture(Gdx.files.internal("picross/"+path+".png"));
		tex.getTextureData().prepare();
		Pixmap pixMap = tex.getTextureData().consumePixmap();
		tilesAcross = tex.getWidth();
		tilesDown = tex.getHeight();
		tiles = new PicrossTile[tilesAcross][tilesDown];

		setSize((PicrossTile.SIZE+PicrossTile.GAP)*tilesAcross + PicrossTile.GAP, (PicrossTile.SIZE+PicrossTile.GAP)*tilesDown+ PicrossTile.GAP);
		for(int x=0;x<tilesAcross;x++){
			for(int y=0;y<tilesDown;y++){
				int col = pixMap.getPixel(x, tilesDown-1-y);
				PicrossTile tile =new PicrossTile(x, y, col==255?PicrossTileState.On:PicrossTileState.Off); 
				tiles[x][y] = tile;
				addActor(tile);
			}
		}
		for(int x=0;x<tilesAcross;x++){
			SideBit s = new SideBit(x, 0, getHeight());
			addActor(s);
			ArrayList<Integer> digits = new ArrayList<Integer>();
			digits.add(0);
			for(int ty=0;ty<tilesDown;ty++){
				PicrossTile t =tiles[x][ty];
				int index = digits.size()-1;
				if(t.correctState==PicrossTileState.On){
					digits.set(index, digits.get(index)+1);
				}
				else{
					if(digits.get(index)!=0){
						digits.add(0);
					}
				}
			}
			if(digits.get(digits.size()-1)==0){
				digits.remove(digits.size()-1);
			}
			s.digits = digits;
		}
		for(int y=0;y<tilesDown;y++){
			SideBit s = new SideBit(y, 1, getHeight());
			addActor(s);
			ArrayList<Integer> digits = new ArrayList<Integer>();
			digits.add(0);
			for(int tx=0;tx<tilesAcross;tx++){
				PicrossTile t =tiles[tx][y];
				int index = digits.size()-1;
				if(t.correctState==PicrossTileState.On){
					digits.set(index, digits.get(index)+1);
				}
				else{
					if(digits.get(index)!=0){
						digits.add(0);
					}
				}
			}
			if(digits.get(digits.size()-1)==0){
				digits.remove(digits.size()-1);
			}
			s.digits = digits;
		}

	}


	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.BROWN);
		Draw.fillActor(batch, this);
//		batch.setColor(Colours.ORANGE);
//		Draw.fillRectangle(batch, (int)(getX()+getWidth()/2), getY(), 1, getHeight());
//		Draw.fillRectangle(batch, getX(), (int)(getY()+getHeight()/2), getWidth(), 1);
		super.draw(batch, parentAlpha);
	};

	PicrossTileState valueToSet;
	public void setupDrag(PicrossTileState state) {
		valueToSet=state;
	}

	public PicrossTileState getValueToSet(){
		return valueToSet;
	}


	public boolean checkComplete() {
		for(int x=0;x<tilesAcross;x++){
			for(int y=0;y<tilesDown;y++){
				if(!tiles[x][y].isCorrect()){
					return false;
				}
			}
		}
		return true;
	}


}
