package forer.tann.videogame.puzzles.wordsearch;

import java.util.ArrayList;

import forer.tann.videogame.Main;
import forer.tann.videogame.Main.Conspiracy;
import forer.tann.videogame.screens.puzzlescreen.PuzzleScreen;

public class WordSearchScreen extends PuzzleScreen{

	WordSearchInput[] inputs = new WordSearchInput[2];
	
	public WordSearchScreen() {
		super("WordSearch[n][n]"
				+ "Find hidden words in the grid. Click and drag to make a word. You can make words backwards!",
				null);
		WordSearch w = new WordSearch();
		w.setPosition((int)(getHeight()/2-w.getHeight()/2), (int)(getHeight()/2-w.getHeight()/2));
		addActor(w);
		for(int i=0;i<inputs.length;i++){
			WordSearchInput wsi = new WordSearchInput();
			inputs[inputs.length-i-1]=wsi;
			addActor(wsi);
			wsi.setPosition((int)(Main.width-wsi.getWidth()-20), (int)(Main.height/2-wsi.getHeight()/2-20 + 40*i));
		}
	}

	@Override
	public void activateHint() {
	}

	@Override
	public void checkComplete() {
	}

	public void setWord(ArrayList<WordSearchTile> currentWord) {
		String s ="";
		for(WordSearchTile wst:currentWord){
			s+=wst.content;
		}
		
			inputs[index].word = s;	
		
	}
	int index=0;
	public void confirmWord() {
		String s = inputs[index].word; 
		if(s.equalsIgnoreCase("MOON")||s.equalsIgnoreCase("BASE")||s.equalsIgnoreCase("NUKE")){
			index++;
			if(index==2){
				complete();
			}
		}
		else{
			inputs[index].word="";
		}
	}

	@Override
	public void complete() {
		long total = inputs[0].word.hashCode()+inputs[1].word.hashCode();
		long moon = "moon".hashCode();
		long base = "base".hashCode();
		long nuke = "nuke".hashCode();
		if(total==moon+base){
			Main.self.addScreens(Conspiracy.MoonBase);
		}
		else if(total==moon+nuke){
			Main.self.addScreens(Conspiracy.MoonNuke);	
		}
		else{
			Main.self.addScreens(Conspiracy.NukeBase);
		}
		super.complete();
	}
}
