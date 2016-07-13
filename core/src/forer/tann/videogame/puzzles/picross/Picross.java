package forer.tann.videogame.puzzles.picross;

import java.util.ArrayList;
import java.util.List;

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
    List<int[]> labelList;
    public Picross() {

    	
    	//Generate Labels to use for temporary stuff
    	generateLabels(true);
        //Draw Labels, Don't draw the 0,0 label, it's blank
        //Draw the horizontal labels
        for (int x=0; x<width;x++) {
            PicrossLabel label = new PicrossLabel(true, x,height, labelList.get(x));
            addActor(label);

            //Gotta put the picross in the right place
            int x1 = (Main.width/2);
            int y1 = (Main.height/2);
            //Move the picross down and left because we're looking at the bottom left of the picross
            x1 -= tileSize * width * .5f;
            y1 -= tileSize * height * .5f;
            setPosition(x1, y1);
        }
        
        //I know there's a better way to do this part but I can't think of the BEST way and this is good enough for now
        generateLabels(false);
        //Draw the vertical labels
        for (int y=0; y<height;y++) {
            PicrossLabel label = new PicrossLabel(false, -1,y, labelList.get(y));
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
    
    void generateLabels(boolean isHorizontal) {
    	labelList = new ArrayList<int[]>();
    	for (int x = 0; x<5;x++) {
    		labelList.add(new int[]{1,1});
    	}
    }
}
