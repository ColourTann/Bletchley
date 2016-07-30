package forer.tann.videogame.screens.puzzlescreen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import forer.tann.videogame.Main;
import forer.tann.videogame.utilities.graphics.Button;
import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;
import forer.tann.videogame.utilities.graphics.TextRenderer;

public class SkipPopup extends Group{
	static final int GAP = 4;
	public SkipPopup() {
		TextRenderer tr = new TextRenderer("Skip this puzzle?", 100);
		setWidth(tr.getWidth()+GAP*2);
		addActor(tr);

		Button yes = new Button("Yes");
		yes.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				PuzzleScreen ps =(PuzzleScreen) (Main.self.currentScreen);
				ps.activateSkip();
				Main.self.currentScreen.pop();
				return false;
			}
		});
		Button cancel = new Button("Cancel");
		cancel.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Main.self.currentScreen.pop();
				return false;
			}
		});
		addActor(yes);
		addActor(cancel);
		int totalButtonWidth = (int) (yes.getWidth()+cancel.getWidth());
		int gap = (int)(getWidth()-totalButtonWidth)/3;
		cancel.setPosition(gap, GAP);
		yes.setPosition(gap*2+cancel.getWidth(), GAP);
		tr.setPosition(GAP, GAP*2+yes.getHeight());

		setSize(tr.getWidth()+GAP*2, tr.getHeight()+yes.getHeight()+GAP*3);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.DARK);
		Draw.fillActor(batch, this);
		batch.setColor(Colours.BROWN);
		Draw.drawRectangle(batch, getX(), getY(), getWidth(), getHeight(), 1);
		batch.setColor(Colours.zWHITE);
		super.draw(batch, parentAlpha);
	}
}

