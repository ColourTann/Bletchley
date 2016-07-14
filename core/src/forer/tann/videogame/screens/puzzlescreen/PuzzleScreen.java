package forer.tann.videogame.screens.puzzlescreen;

import com.badlogic.gdx.graphics.g2d.Batch;

import forer.tann.videogame.Main;
import forer.tann.videogame.screens.Screen;
import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;

public abstract class PuzzleScreen extends Screen{
	HelpPopup help;
	HintPopup hint;
	SkipPopup skip;
	public PuzzleScreen(String helpText) {
		help = new HelpPopup(helpText);
		help.setPosition((int)(Main.width/2-help.getWidth()/2), (int)(Main.height/2-help.getHeight()/2));
		skip = new SkipPopup();
		skip.setPosition((int)(Main.width/2-skip.getWidth()/2), (int)(Main.height/2-skip.getHeight()/2));
		hint= new HintPopup();
		hint.setPosition((int)(Main.width/2-hint.getWidth()/2), (int)(Main.height/2-hint.getHeight()/2));
		
		Options o = new Options();
		addActor(o);
		o.setPosition(Main.width-o.getWidth(), Main.height-o.getHeight());
	}
	
	
	@Override
	public void keyPressed(int keycode) {
	}

	@Override
	public void keyReleased(int keycode) {
	}

	public void showHelp(){
		addBlocker();
		push(help);
	}
	
	public void showHint() {
		addBlocker();
		push(hint);
	}

	public void showSkip() {
		addBlocker();
		push(skip);
	}
	
	public void activateSkip() {
		System.out.println("skip");
	}

	public void activateHint() {
		System.out.println("hint");
	}
	
	public void complete(){
		Main.self.nextScreen();
	}
	
	public abstract void checkComplete();

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		Draw.setBatchColour(batch, Colours.DARK, 1-getColor().a);
		Draw.fillActor(batch, this);
	}
}
