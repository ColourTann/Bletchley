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
    int[] numbers = new int[]{1,2,2};
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
            int offset=0;
            for (int n : numbers){
                TannFont.font.draw(batch, n+"", getX() + (SIZE / 4) + (offset * 5), getY() + (SIZE / 4));
                offset++;
            }
        } else {
            int offset=numbers.length;
            for (int n : numbers){
                TannFont.font.draw(batch, n+"", getX() + (SIZE / 4), getY() + (SIZE / 4) + (offset * 7));
                offset--;
            }
        }

        super.draw(batch, parentAlpha);

    }
}
