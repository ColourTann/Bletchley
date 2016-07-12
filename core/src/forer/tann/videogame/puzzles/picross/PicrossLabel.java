package forer.tann.videogame.puzzles.picross;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;

/**
 * Created by Forer on 7/11/2016.
 */
public class PicrossLabel extends Actor {
    static final int BORDER = 1;
    static final int SIZE = 10;
    int gridX, gridY;
    public PicrossLabel(int x, int y) {
        this.gridX=x;
        this.gridY=y;
        setSize(SIZE, SIZE);
        setPosition(x * SIZE, y * SIZE);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {


        batch.setColor(Colours.DARK);
        Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());

        batch.setColor(Color.GOLD);
        Draw.fillRectangle(batch, getX()+BORDER, getY()+BORDER, getWidth()-BORDER*2, getHeight()-BORDER*2);
        super.draw(batch, parentAlpha);
    }
}
