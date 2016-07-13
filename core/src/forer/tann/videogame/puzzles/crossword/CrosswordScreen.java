package forer.tann.videogame.puzzles.crossword;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;

import forer.tann.videogame.Main;
import forer.tann.videogame.screens.PuzzleScreen;
import forer.tann.videogame.screens.Screen;
import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;

public class CrosswordScreen extends PuzzleScreen{
	Crossword crossword;
	int gap;
	public CrosswordScreen() {
		addActor(crossword = new Crossword());
		gap = (int) ((Main.width-crossword.getWidth()-CrosswordClue.WIDTH)/3);
		crossword.setPosition(gap*2 + CrosswordClue.WIDTH, (int)(Main.height/2-crossword.getHeight()/2));
		setupClues();
		addActor(new Hint());
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.DARK);
		Draw.fillActor(batch, this);
		super.draw(batch, parentAlpha);
	}
	
	public void setupClues(){
		addClue(new CrosswordClue("[pcg][v] \"The war\" (anag.) (6)"));
		addClue(new CrosswordClue("[pcg][->] The little fellow has some beer; it makes me lose colour, I say (6)"));
		addClue(new CrosswordClue("[pcr][v] Kind of alias (9)"));
		addClue(new CrosswordClue("[pcr][->] Pretend (5)"));
		layoutClues();
	}
	
	static final int CLUE_GAP=2;
	ArrayList<CrosswordClue> clues = new ArrayList<>();
	
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
}
