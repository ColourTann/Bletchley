package forer.tann.videogame.puzzles.picross;

import forer.tann.videogame.Main;
import forer.tann.videogame.puzzles.Puzzle;

/**
 * Created by Forer on 7/11/2016.
 */
public class Picross extends Puzzle {
    int width = 5;
    int height = 5;
    int tileSize = PicrossTile.getSize();
    PicrossTile[][] tiles = new PicrossTile[width][height];
    public Picross() {
        //Draw Labels, Don't draw the 0,0 label, it's blank
        //Draw the horizontal labels
        for (int x=0; x<width;x++) {
            PicrossLabel label = new PicrossLabel(x,-1);
            addActor(label);

            //Gotta put the picross in the right place
            int x1 = (Main.width/2);
            int y1 = (Main.height/2);
            //Move the picross down and left because we're looking at the bottom left of the picross
            x1 -= tileSize * width * .5f;
            y1 -= tileSize * height * .5f;
            setPosition(x1, y1);
        }
        //Draw the vertical labels
        for (int y=0; y<height;y++) {
            PicrossLabel label = new PicrossLabel(-1,y);
            addActor(label);

            //Gotta put the picross in the right place
            int x1 = (Main.width/2);
            int y1 = (Main.height/2);
            //Move the picross down and left because we're looking at the bottom left of the picross
            x1 -= tileSize * width * .5f;
            y1 -= tileSize * height * .5f;
            setPosition(x1, y1);
        }

        //Draw Tiles
        for(int x=0;x<width;x++){
            for(int y=0;y<height;y++){
                PicrossTile tile = new PicrossTile(x,y);
                tiles[x][y] = tile;
                addActor(tile);


                //Gotta put the picross in the right place
                int x1 = (Main.width/2);
                int y1 = (Main.height/2);
                //Move the picross down and left because we're looking at the bottom left of the picross
                x1 -= tileSize * x * .5f;
                y1 -= tileSize * y * .5f;
                setPosition(x1, y1);
            }
        }

    }
}
