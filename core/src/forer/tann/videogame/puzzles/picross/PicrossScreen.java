package forer.tann.videogame.puzzles.picross;

import forer.tann.videogame.Main;
import forer.tann.videogame.screens.puzzlescreen.PuzzleScreen;

public class PicrossScreen extends PuzzleScreen{

	private static PicrossScreen self;
	public static PicrossScreen getCurrentScreen(){
		return self;
	}
	
	public Picross picross;
	
	public PicrossScreen(String string) {
		super("nonogram[n][n]"
				+ "By each row and column there are numbers. They tell you how many black squares there are in that column or row.[n][n]"
				+ "A '3' means there are 3 black squares in a row somehwere in that line.[n][n]"
				+ "A '1 2' means there is one black square, then a gap of at least one white square, followed by two black squares in a row.[n][n]"
				+ "Left-click to toggle tile colour, right-click to mark a tile as empty.",
				"Remove all mistakes and solve 3 random squares?");
		self=this;
		picross = new Picross(string);
		picross.setPosition((int)(Main.width/2-picross.getWidth()/2)+SideBit.SIZE/2, (int)(Main.height/2-picross.getHeight()/2)-SideBit.SIZE/2);
		addActor(picross);
	}

	@Override
	public void setActive(boolean active) {
		self=this;
		super.setActive(active);
	}
	
	@Override
	public void activateHint() {
		picross.hint();
	}

	@Override
	public void checkComplete() {
		if(picross.checkComplete()){
			complete();
		}
	}

}
