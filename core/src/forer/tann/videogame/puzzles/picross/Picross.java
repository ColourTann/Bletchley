package forer.tann.videogame.puzzles.picross;

import forer.tann.videogame.puzzles.Puzzle;

/**
 * Created by Forer on 7/11/2016.
 */
public class Picross extends Puzzle {
    int width = 5;
    int height = 5;
    PicrossTile[][] tiles = new PicrossTile[width][height];
    public Picross() {
        for(int x=0;x<width;x++){
            for(int y=0;y<height;y++){
                PicrossTile tile = new PicrossTile(x,y);
                tiles[x][y] = tile;
                addActor(tile);
            }
        }
    }
}
