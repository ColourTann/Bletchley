package forer.tann.videogame.puzzles.picross;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;


public class PicrossTile extends Group{
	public static final int SIZE = 13;
	public static final int GAP = 1;
	PicrossTileState correctState;
	PicrossTileState state = PicrossTileState.Off;
	boolean mouseOn;

	public enum PicrossTileState{
		On, Off, Cross, DeCross
	}

	public PicrossTile(int x, int y, PicrossTileState correct) {
		setSize(SIZE, SIZE);
		setPosition(GAP+x*(SIZE+GAP), GAP+y*(SIZE+GAP));
		correctState = correct;
		addListener(new InputListener(){

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
				if(pointer!=0) return;
				setValue();
			};

			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				switch(button){
				//left click
				case 0: 
					switch(state){
					case Cross:
						break;
					case Off:
						PicrossScreen.getCurrentScreen().picross.setupDrag(PicrossTileState.On); 
						break;
					case On:
						PicrossScreen.getCurrentScreen().picross.setupDrag(PicrossTileState.Off); 
						break;
					default:
						break;
					}
					break;

				//right click
				case 1:	
					switch(state){
					case Cross:
						PicrossScreen.getCurrentScreen().picross.setupDrag(PicrossTileState.DeCross); 
						break;
					case Off:
						PicrossScreen.getCurrentScreen().picross.setupDrag(PicrossTileState.Cross); 
						break;
					case On:
						break;
					default:
						break;

					}
					break;
				}
				setValue();
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				PicrossScreen.getCurrentScreen().picross.setupDrag(null);
			}
		});
	}

	void setValue(){
		PicrossTileState target =PicrossScreen.getCurrentScreen().picross.getValueToSet(); 
		if(target==PicrossTileState.DeCross){
			if(state == PicrossTileState.On) return;
			target = PicrossTileState.Off;
		}
		if(target==null) return;
		if(target==PicrossTileState.On && state == PicrossTileState.Cross) return;
		if(target==PicrossTileState.Cross && state == PicrossTileState.On) return;
		setState(target);
	}
	
	void setState(PicrossTileState state){
		this.state=state;
		PicrossScreen.getCurrentScreen().checkComplete();
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.LIGHT);
		Draw.fillActor(batch, this);
		
		switch(state){
		case Cross:
			batch.setColor(Colours.DARK);
			Draw.drawLine(batch, getX(), getY(), getX()+getWidth(), getY()+getHeight(), 1);
			Draw.drawLine(batch, getX()+getWidth(), getY(), getX(), getY()+getHeight(), 1);
			break;
		case Off:
			break;
		case On:
			batch.setColor(Colours.DARK);
			Draw.fillActor(batch, this);
			break;
		default:
			break;
		
		}
		super.draw(batch, parentAlpha);
	}

	public boolean isCorrect() {
		if(correctState==PicrossTileState.On){
			return state==PicrossTileState.On;
		}
		if(correctState==PicrossTileState.Off){
			return state!=PicrossTileState.On;
		}
		System.out.println("big ol error");
		return false;
	}

}
