package forer.tann.videogame.screens.puzzlescreen;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import forer.tann.videogame.Main;
import forer.tann.videogame.utilities.graphics.Button;

public class Options extends Group{
	static final int BUTTON_SIZE = 17;
	static final int BUTTON_GAP = 2;
	public static final int HEIGHT = BUTTON_SIZE+BUTTON_GAP*2;
	public Options(boolean clue) {
		setSize(BUTTON_SIZE*3 + BUTTON_GAP*4, HEIGHT);
		Button skip = new Button(Main.atlas.findRegion("skip"));
		Button help = new Button(Main.atlas.findRegion("help"));
		Button hint = new Button(Main.atlas.findRegion("clue"));
		help.setPosition(BUTTON_GAP, BUTTON_GAP);
		hint.setPosition(BUTTON_GAP*2 + BUTTON_SIZE, BUTTON_GAP);
		skip.setPosition(BUTTON_GAP*3 + BUTTON_SIZE*2, BUTTON_GAP);
		addActor(help); 
		if(clue){
			addActor(skip); 
			addActor(hint);
		}
		
		help.setClickAction(new Runnable() {
			public void run() {
				PuzzleScreen ps =(PuzzleScreen) (Main.self.currentScreen);
				ps.showHelp();
			}
		});
		
		hint.setClickAction(new Runnable() {
			public void run() {
				PuzzleScreen ps =(PuzzleScreen) (Main.self.currentScreen);
				ps.showHint();
			}
		});
		
		skip.setClickAction(new Runnable() {
			public void run() {
				PuzzleScreen ps =(PuzzleScreen) (Main.self.currentScreen);
				ps.showSkip();
			}
		});
	}

}
