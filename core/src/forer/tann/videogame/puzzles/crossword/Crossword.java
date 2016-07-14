package forer.tann.videogame.puzzles.crossword;

import java.util.ArrayList;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import forer.tann.videogame.Main;
import forer.tann.videogame.puzzles.Puzzle;
import forer.tann.videogame.screens.puzzlescreen.PuzzleScreen;
import forer.tann.videogame.utilities.Sounds;
import forer.tann.videogame.utilities.Sounds.SoundType;
import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;

public class Crossword extends Puzzle{

	CrosswordTile startTile;
	CrosswordTile typingAt;
	int typingRight;
	
	int width = 15;
	int height = 15;
	public static final int GAP = 1;
	CrosswordTile[][] tiles = new CrosswordTile[width][height];
	
	private static Crossword self;
	public static Crossword get(){
		if(self==null) self = new Crossword();
		return self;
	}
	
	private Crossword() {
		setSize(width*(CrosswordTile.SIZE+GAP)+GAP, height*(CrosswordTile.SIZE+GAP)+GAP);
		addAnswers();
		setupColouredTiles();
	}

	private void addAnswers() {
		addAnswer("troupe", 0, 14, 1, false);
		addAnswer("shortcut", 7, 14, 1, false);
		addAnswer("privet", 0, 12, 1, false);
		addAnswer("aromatic", 7, 12, 1, false);
		addAnswer("trend", 0, 10, 1, false);
		addAnswer("greatdeal", 5, 10, 1, false);
		addAnswer("owe", 4, 9, 1, false);
		addAnswer("feign", 0, 8, 1, true);
		addAnswer("newark", 6, 8, 1, false);
		addAnswer("impale", 3, 6, 1, true);
		addAnswer("guise", 10, 6, 1, false);
		addAnswer("ash", 8, 5, 1, false);
		addAnswer("centrebit", 0, 4, 1, false);
		addAnswer("token", 10, 4, 1, false);
		addAnswer("lamedogs", 0, 2, 1, false);
		addAnswer("racing", 9, 2, 1, false);
		addAnswer("silencer", 0, 0, 1, false);
		addAnswer("alight", 9, 0, 1, false);
		
		addAnswer("tipstaff", 0, 14, 0, false);
		addAnswer("oliveoil", 2, 14, 0, false);
		addAnswer("pseudonym", 4, 14, 0, true);
		addAnswer("horde", 8, 14, 0, false);
		addAnswer("remit", 10, 14, 0, false);
		addAnswer("cutter", 12, 14, 0, false);
		addAnswer("tackle", 14, 14, 0, false);
		addAnswer("agenda", 6, 11, 0, false);
		addAnswer("ada", 9, 10, 0, false);
		addAnswer("wreath", 8, 8, 0, true);
		addAnswer("rightnail", 10,8, 0, false);
		addAnswer("tinkling", 12, 7, 0, false);
		addAnswer("sennight", 14, 7, 0, false);
		addAnswer("pie", 5, 6, 0, false);
		addAnswer("scales", 0, 5, 0, false);
		addAnswer("enamel", 2, 5, 0, false);
		addAnswer("rodin", 4, 4, 0, false);
		addAnswer("bogie", 6, 4, 0, false);
	}
	
	private void setupColouredTiles() {
		getTile(4, 14).setClue(0);
		getTile(0, 8).setClue(1);
		getTile(3, 6).setClue(1);
		getTile(8, 8).setClue(0);
	}


	private CrosswordTile makeTile(int x, int y){
		if(getTile(x, y)!=null) return getTile(x, y);
		CrosswordTile tile = new CrosswordTile(x,y);
		tiles[x][y] = tile;
		addActor(tile);
		return tile;
	}
	
	private void addAnswer(String answer, int startX, int startY, int right, boolean hidden){
		int dx=right;
		int dy=-1+right;
		int lettersUsed=0;
		CrosswordTile start =makeTile(startX, startY);
		for(int x=start.gridX,y=start.gridY;lettersUsed<answer.length();x+=dx,y+=dy){
			CrosswordTile t = makeTile(x, y);
			t.setLetter(answer.charAt(lettersUsed), hidden);
			lettersUsed++;
		}
	}
	
	public CrosswordTile getTile(int x, int y){
		if(x<0||x>=width || y<0||y>=height) return null;
		return tiles[x][y];
	}
	
	public void keyPressed(int keycode) {
		if(typingAt==null) return;
		if(keycode==Input.Keys.BACKSPACE){
			if(typingAt!=null){
				CrosswordTile t = getTile(typingAt.gridX+typingRight*-1, typingAt.gridY+(-1+typingRight)*-1);
				if(t!=null)setTypingTile(t);
			}
			Sounds.playSound(SoundType.CrosswordLetter);
		}
		int start = Input.Keys.A;
		if(keycode<start || keycode > start + 26){
			return;
		}
		Sounds.playSound(SoundType.CrosswordLetter);
		char key = (char) ('a' + (keycode-start));
		if(typingAt!=null){
			typingAt.type(key);
			setTypingTile(getTile(typingAt.gridX+typingRight, typingAt.gridY+(-1+typingRight)));
		}
		if(typingAt==null){
			boolean good = checkAnswer(startTile);
			if(!good){
				Sounds.playSound(SoundType.Bad);
				resetLetters(startTile);
			}
			if(good){
				Sounds.playSound(SoundType.Good);
			}
		}
	}

	private boolean checkAnswer(CrosswordTile start) {
		boolean ok = true;
		for(CrosswordTile t:getTilesInClue(start)){
			if(!t.isCorrect()) ok=false;
		}
		if(ok){
			for(CrosswordTile t:getTilesInClue(start)){
				t.correct=true;
			}
			start.clue.complete();
			((PuzzleScreen)Main.self.currentScreen).checkComplete();
		}
		return ok;
	}
	
	public ArrayList<CrosswordTile> getTilesInClue(CrosswordTile start){
		ArrayList<CrosswordTile> result = new ArrayList<>();
		for(int dx=0,dy=0;true;dx+=typingRight,dy+=(-1+typingRight)){
			CrosswordTile t = getTile(start.gridX+dx, start.gridY+dy);
			if(t==null) break;
			result.add(t);
		}
		return result;
	}

	public void startTyping(CrosswordTile tile) {
		if(tile.clue.complete)return;
		startTile=tile;
		setTypingTile(tile);
		this.typingRight=tile.direction;
		resetLetters(tile);
	}
	
	public void resetLetters(CrosswordTile start){
		for(CrosswordTile t:getTilesInClue(start)){
			t.type(' ');
		}
	}
	
	public void setTypingTile(CrosswordTile tile){
		if(typingAt!=null){
			typingAt.setHighlight(false);
		}
		this.typingAt=tile;
		if(tile!=null){
			
		tile.setHighlight(true);
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.DARK);
		Draw.fillActor(batch, this);
		super.draw(batch, parentAlpha);
	}
}
