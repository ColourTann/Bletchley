package forer.tann.videogame.puzzles.picross;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;

/**
 * Created by Forer on 7/11/2016.
 */
public class PicrossTile extends Actor {
    static final int BORDER = 1;
    static final int SIZE = 9;
    int gridX, gridY;
    boolean selected = false;
    public PicrossTile(int x, int y) {
        this.gridX=x;
        this.gridY=y;
        setSize(SIZE, SIZE);
        setPosition(x*SIZE, y*SIZE);
        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("you tapped on "+gridX+":"+gridY);
                selected = !selected;
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {


        batch.setColor(Colours.DARK);
        Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());

        Color selectionColor = Colours.LIGHT;
        if (selected) selectionColor = Colours.random();
        batch.setColor(selectionColor);
        Draw.fillRectangle(batch, getX()+BORDER, getY()+BORDER, getWidth()-BORDER*2, getHeight()-BORDER*2);
        super.draw(batch, parentAlpha);
    }

    static public int getSize() {
        return SIZE;
    }

}
