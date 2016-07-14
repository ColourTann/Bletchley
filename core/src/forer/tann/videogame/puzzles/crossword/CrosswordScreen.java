package forer.tann.videogame.puzzles.crossword;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;

import forer.tann.videogame.Main;
import forer.tann.videogame.screens.Screen;
import forer.tann.videogame.screens.puzzlescreen.Options;
import forer.tann.videogame.screens.puzzlescreen.PuzzleScreen;
import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;

public class CrosswordScreen extends PuzzleScreen{
	Crossword crossword;
	int gap;
	
	private static CrosswordScreen self;
	public static CrosswordScreen get(){
		if(self==null) self = new CrosswordScreen();
		return self;
	}
	
	private CrosswordScreen() {
		super("[tco]Crossword[n][nh][tcl]Try to figure out the clues. Click a coloured tile or a clue to start typing your answer.");
		addActor(crossword = Crossword.get());
		gap = (int) ((Main.width-crossword.getWidth()-CrosswordClue.WIDTH)/3);
		crossword.setPosition(gap*2 + CrosswordClue.WIDTH, (int)((Main.height-Options.HEIGHT)/2-crossword.getHeight()/2));
		setupClues();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.DARK);
		Draw.fillActor(batch, this);
		super.draw(batch, parentAlpha);
	}
	
	public void setupClues(){
		addClue(new CrosswordClue("[pcb][v] \"The war\" (anag.) (6)", Crossword.get().getTile(8, 8)));
		addClue(new CrosswordClue("[pcb][v] Kind of alias (9)", Crossword.get().getTile(4, 14)));
		addClue(new CrosswordClue("[pco][->] The little fellow has some beer; it makes me lose colour, I say (6)", Crossword.get().getTile(3, 6)));
		addClue(new CrosswordClue("[pco][->] Pretend (5)", Crossword.get().getTile(0, 8)));
		layoutClues();
	}
	
	static final int CLUE_GAP=2;
	ArrayList<CrosswordClue> clues = new ArrayList<CrosswordClue>();
	
	private void addClue(CrosswordClue clue){
		clues.add(clue);
	}
	
	private void layoutClues(){
		int totalHeight=0;
		for(CrosswordClue c:clues){
			totalHeight+=c.getHeight();
		}
		totalHeight+=clues.size()*CLUE_GAP;
		int clueY=Main.height/2-totalHeight/2;
		for(int i=0;i<clues.size();i++){
			CrosswordClue c= clues.get(i);
			addActor(c);
			c.setPosition(gap, clueY);
			clueY+=c.getHeight()+CLUE_GAP;
		}
	}
	
	@Override
	public void keyPressed(int keycode) {
		crossword.keyPressed(keycode);
		super.keyPressed(keycode);
	}
	
	public void checkComplete(){
		boolean ok = true;
		for(CrosswordClue clue:clues){
			if(!clue.complete){
				ok = false;
			}
		}
		if(ok){
			complete();
		}
	}

	@Override
	public void activateHint() {
		for(CrosswordClue clue:clues){
			if(!clue.complete){
				for(CrosswordTile t:crossword.getTilesInClue(clue.tile)){
					t.forceCorrect();
				}
				clue.complete();
				break;
			}
		}
	}
}
