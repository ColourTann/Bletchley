package forer.tann.videogame.screens.puzzlescreen;

import forer.tann.videogame.Main;
import forer.tann.videogame.screens.Screen;

public class PuzzleScreen extends Screen{
	HelpPopup help;
	HintPopup hint;
	public PuzzleScreen(String helpText) {
		help = new HelpPopup(helpText);
		help.setPosition((int)(Main.width/2-help.getWidth()/2), (int)(Main.height/2-help.getHeight()/2));
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
		push(help);
		addBlocker();
	}
	
	public void hint() {
		push(hint);
		addBlocker();
	}

	public void skip() {
	}

}
