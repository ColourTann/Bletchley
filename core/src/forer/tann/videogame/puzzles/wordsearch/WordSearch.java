package forer.tann.videogame.puzzles.wordsearch;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;

import forer.tann.videogame.puzzles.Puzzle;
import forer.tann.videogame.puzzles.crossword.CrosswordTile;
import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;

public class WordSearch extends Puzzle {
	static int TILES_ACROSS ;
	static int TILES_DOWN;
	WordSearchTile[][] tiles;
	public WordSearch() {
		FileHandle file = Gdx.files.internal("wordsearch/letters");
		String s = file.readString();
		String[] letters = s.split("\n");
		TILES_ACROSS=letters[0].length()-1;
		TILES_DOWN=letters.length;
		setSize(TILES_ACROSS*(WordSearchTile.SIZE+WordSearchTile.GAP)+WordSearchTile.GAP, 
				TILES_DOWN*(WordSearchTile.SIZE+WordSearchTile.GAP)+WordSearchTile.GAP);
		tiles = new WordSearchTile[TILES_ACROSS][TILES_DOWN];
		for(int x=0;x<TILES_ACROSS;x++){
			for(int y=0;y<TILES_DOWN;y++){
				WordSearchTile t = new WordSearchTile(x, y);
				tiles[x][y]=t;
				addActor(t);
			}
		}
		
		for(int y=0;y<TILES_DOWN;y++){
			char[] chars = letters[y].toCharArray();
			for(int x=0;x<TILES_ACROSS;x++){
				tiles[x][TILES_DOWN-y-1].setChar(chars[x]);
			}
		}
	}

	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.BROWN);
		Draw.fillActor(batch, this);
		super.draw(batch, parentAlpha);
	}

	WordSearchTile origin;
	
	public void startDrag(WordSearchTile t) {
		t.highlight(true);
		origin=t;
		currentWord.add(t);
	}


	public void endDrag(WordSearchTile wordSearchTile) {
		if(((WordSearchScreen)getParent()).confirmWord()){
			lockWord();
		}
		
		clearWord();
		origin=null;
	}

	private void lockWord() {
		for(WordSearchTile wst:currentWord){
			wst.lock();
		}
	}

	ArrayList<WordSearchTile> currentWord = new ArrayList<WordSearchTile>();
	public void hover(WordSearchTile t) {
		if(origin==null) return;
		int dx = t.x-origin.x;
		int dy = t.y-origin.y;
		int ax = Math.abs(dx);
		int ay = Math.abs(dy);
		if(ax==0||ay==0||ax==ay){
			clearWord();
			int x = origin.x;
			int y = origin.y;
			while(x!=t.x||y!=t.y){
				currentWord.add(tiles[x][y]);
				x+=Math.signum(dx);
				y+=Math.signum(dy);
			}
			currentWord.add(tiles[x][y]);
			highlightWords();
		}
	}
	
	void highlightWords(){
		((WordSearchScreen)getParent()).setWord(currentWord);
		for(WordSearchTile t:currentWord){
			t.highlight(true);
		}
	}
	
	public void clearWord(){
		for(WordSearchTile t:currentWord){
			t.highlight(false);
		}
		currentWord.clear();;
	}
	
}
