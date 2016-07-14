package forer.tann.videogame.puzzles.crossword;

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
		setupTiles();
		addAnswers();
		setupColouredTiles();
	}

	private void setupTiles() {
		tile(0,0,34); 
		line(1,7,0);
		tile(9,0,35);
		line(10,14,0);
		
		polka(0, 6,1);
		polka(10, 14,1);
		
		tile(0,2,32);
		line(1,7,2);
		tile(9,2,33);
		line(10,14,2);
		
		polka(0,14,3);
		
		tile(0,4,28);
		line(1,3,4);
		tile(4,4,29);
		tile(5,4);
		tile(6,4,30);
		line(7,8,4);
		tile(10,4,31);
		line(11,14,4);
		
		tile(0,5,25);
		tile(2,5,26);
		tile(5,5);
		tile(8,5,27);
		tile(9,5);
		polka(10,14,5);
		
		tile(3,6,22);
		tile(4,6);
		tile(5,6,23);
		line(6,8,6);
		tile(10,6,24);
		line(11,14,6);
		
		polka(0,11,7);
		tile(12,7,20);
		tile(14,7,21);
		
		tile(0,8,16);
		line(1,4,8);
		tile(6,8,17);
		tile(7,8);
		tile(8,8,18);
		tile(9,8);
		tile(10,8,19);
		tile(11,8);
		
		polka(0,2,9);
		tile(4,9,15);
		line(5,6,9);
		tile(9,9);
		polka(12,14,9);
		
		tile(0,10,12);
		line(1,4,10);
		tile(6,10,13);
		line(7,8,10);
		tile(9,10,14);
		line(10,14,10);
		
		polka(0,5,11);
		tile(6,11,11);
		polka(8,14,11);
		
		tile(0,12,9);
		line(1,5,12);
		tile(7,12,10);
		line(8,14,12);
		
		polka(0,5,13);
		polka(8,14,13);
		
		tile(0,14,1);
		tile(1,14);
		tile(2,14,2);
		tile(3,14);
		tile(4,14,3);
		tile(5,14);
		tile(7,14,4);
		tile(8,14,5);
		tile(9,14);
		tile(10,14,6);
		tile(11,14);
		tile(12,14,7);
		tile(13,14);
		tile(14,14,8);
	}
	
	private void addAnswers() {
		addAnswer("troupe", 1, 1, false);
		addAnswer("shortcut", 4, 1, false);
		addAnswer("privet", 9, 1, false);
		addAnswer("aromatic", 10, 1, false);
		addAnswer("trend", 12, 1, false);
		addAnswer("greatdeal", 13, 1, false);
		addAnswer("owe", 15, 1, false);
		addAnswer("feign", 16, 1, true);
		addAnswer("newark", 17, 1, false);
		addAnswer("impale", 22, 1, true);
		addAnswer("guise", 24, 1, false);
		addAnswer("ash", 27, 1, false);
		addAnswer("centrebit", 28, 1, false);
		addAnswer("token", 31, 1, false);
		addAnswer("lamedogs", 32, 1, false);
		addAnswer("racing", 33, 1, false);
		addAnswer("silencer", 34, 1, false);
		addAnswer("alight", 35, 1, false);
		
		addAnswer("tipstaff", 1, 0, false);
		addAnswer("oliveoil", 2, 0, false);
		addAnswer("pseudonym", 3, 0, true);
		addAnswer("horde", 5, 0, false);
		addAnswer("remit", 6, 0, false);
		addAnswer("cutter", 7, 0, false);
		addAnswer("tackle", 8, 0, false);
		addAnswer("agenda", 11, 0, false);
		addAnswer("ada", 14, 0, false);
		addAnswer("wreath", 18, 0, true);
		addAnswer("rightnail", 19, 0, false);
		addAnswer("tinkling", 20, 0, false);
		addAnswer("sennight", 21, 0, false);
		addAnswer("pie", 23, 0, false);
		addAnswer("scales", 25, 0, false);
		addAnswer("enamel", 26, 0, false);
		addAnswer("rodin", 29, 0, false);
		addAnswer("bogie", 30, 0, false);
	}
	
	private void setupColouredTiles() {
		getTile(4, 14).setClue(0);
		getTile(0, 8).setClue(1);
		getTile(3, 6).setClue(1);
		getTile(8, 8).setClue(0);
	}


	private void polka(int sx, int ex, int y){
		for(int x=sx;x<=ex;x+=2){
			tile(x,y);
		}
	}
	
	private void line(int sx, int ex, int y){
		for(int x=sx;x<=ex;x++){
			tile(x,y);
		}
	}
	
	private void tile(int x, int y){
		tile(x,y,-1);
	}
	
	private void tile(int x, int y, int number){
		CrosswordTile tile = new CrosswordTile(x,y,number);
		tiles[x][y] = tile;
		addActor(tile);
	}
	
	private void addAnswer(String answer, int number, int right, boolean hidden){
		int dx=right;
		int dy=-1+right;
		CrosswordTile start = getTileFromNumber(number);
		int lettersUsed=0;
		for(int x=start.gridX,y=start.gridY;true;x+=dx,y+=dy){
			CrosswordTile t = getTile(x, y);
			if(t==null || answer.length()==lettersUsed)break;
			t.setLetter(answer.charAt(lettersUsed), hidden);
			lettersUsed++;
		}
	}
	
	
	
	private CrosswordTile getTileFromNumber(int number) {
		for(int x=0;x<width;x++){
			for(int y=0;y<height;y++){
				CrosswordTile tile = getTile(x, y);
				if(tile==null) continue;
				if(tile.number==number) return tile;
			}
		}
		return null;
	}

	public CrosswordTile getTile(int x, int y){
		if(x<0||x>=width || y<0||y>=height) return null;
		return tiles[x][y];
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.DARK);
		Draw.fillActor(batch, this);
		super.draw(batch, parentAlpha);
	}

	public void keyPressed(int keycode) {
		if(typingAt==null) return;
		if(keycode==Input.Keys.BACKSPACE){
			if(typingAt!=null){
				CrosswordTile t = getTile(typingAt.gridX+typingRight*-1, typingAt.gridY+(-1+typingRight)*-1);
				if(t!=null)setTile(t);
			}
		}
		int start = Input.Keys.A;
		if(keycode<start || keycode > start + 26){
			return;
		}
		char key = (char) ('a' + (keycode-start));
		if(typingAt!=null){
			typingAt.type(key);
			setTile(getTile(typingAt.gridX+typingRight, typingAt.gridY+(-1+typingRight)));
		}
		if(typingAt==null){
			boolean good = checkAnswer(startTile);
			if(!good){
				Sounds.playSound(SoundType.Bad);
				clearClue(startTile);
			}
			if(good){
				Sounds.playSound(SoundType.Good);
			}
		}
	}

	private boolean checkAnswer(CrosswordTile start) {
		boolean ok = true;
		for(int dx=0,dy=0;true;dx+=typingRight,dy+=(-1+typingRight)){
			CrosswordTile t = getTile(start.gridX+dx, start.gridY+dy);
			if(t==null) break;
			if(!t.isCorrect()) ok=false;
		}
		if(ok){
			for(int dx=0,dy=0;true;dx+=typingRight,dy+=(-1+typingRight)){
				CrosswordTile t = getTile(start.gridX+dx, start.gridY+dy);
				if(t==null) break;
				t.ordained=true;
				
			}
			//make ding//
			start.complete=true;
			start.clue.complete();
			((PuzzleScreen)Main.self.currentScreen).checkComplete();
		}
		return ok;
	}

	public void setStart(CrosswordTile tile) {
		if(tile.complete)return;
		startTile=tile;
		setTile(tile);
		this.typingRight=tile.direction;
		clearClue(tile);
	}
	
	public void clearClue(CrosswordTile tile){
		for(int dx=0,dy=0;true;dx+=typingRight,dy+=(-1+typingRight)){
			CrosswordTile t = getTile(tile.gridX+dx, tile.gridY+dy);
			if(t==null) break;
			t.type(' ');
		}
	}
	
	public void setTile(CrosswordTile tile){
		if(typingAt!=null){
			typingAt.setHighlight(false);
		}
		this.typingAt=tile;
		if(tile!=null){
			
		tile.setHighlight(true);
		}
	}
	
}
