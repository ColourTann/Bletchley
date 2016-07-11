package forer.tann.videogame.puzzles.crossword;

import com.badlogic.gdx.graphics.g2d.Batch;

import forer.tann.videogame.puzzles.Puzzle;
import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;

public class Crossword extends Puzzle{

	int width = 10;
	int height = 8;
	CrosswordTile[][] tiles = new CrosswordTile[width][height];
	public Crossword() {

		for(int x=0;x<width;x++){
			for(int y=0;y<height;y++){
				CrosswordTile tile = new CrosswordTile(x,y);
				tiles[x][y] = tile;
				addActor(tile);
			}
		}

	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

}
