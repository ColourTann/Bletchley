package forer.tann.videogame.puzzles.picross;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;
import forer.tann.videogame.utilities.graphics.font.TannFont;

/**
 * Created by Forer on 7/11/2016.
 */
public class PicrossLabel extends Actor {
    static final int BORDER = 1;
    static final int SIZE = PicrossTile.SIZE;
    static final int MULTIPLIER = 4;
    int gridX, gridY;
    boolean isX;
    public PicrossLabel(boolean isX, int x, int y) {
        this.gridX=x;
        this.gridY=y;
        this.isX=isX;

        if (isX) {
            setSize(SIZE, SIZE * MULTIPLIER);
            setPosition(x * SIZE, y * SIZE);
        } else {
            setSize(SIZE * MULTIPLIER, SIZE);
            setPosition(-MULTIPLIER * SIZE, y * SIZE);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.DARK);
        Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());

        batch.setColor(Color.GOLD);
        Draw.fillRectangle(batch, getX() + BORDER, getY() + BORDER, getWidth() - BORDER * 2, getHeight() - BORDER * 2);

        batch.setColor(Color.BLACK);

        if(!isX) {
            TannFont.font.draw(batch, "Words", getX()+(SIZE/4), getY()+(SIZE/4));
        } else {
            TannFont.font.draw(batch,"Words",getX()+(SIZE/4), getY()+(SIZE/4));
        }

        super.draw(batch, parentAlpha);

    }
}
