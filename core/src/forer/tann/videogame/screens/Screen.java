package forer.tann.videogame.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import forer.tann.videogame.utilities.graphics.InputBlocker;

public abstract class Screen extends Group{
	private boolean active = true;
	public Screen() {
		setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	public abstract void keyPressed(int keycode);
	public abstract void keyReleased(int keycode);

	public void setActive(boolean active) {
		setTouchable(active?Touchable.enabled:Touchable.disabled);
		this.active=active;
	}

	public ArrayList<Actor> stack = new ArrayList<Actor>();
	
	public void addBlocker(){
		addActor(InputBlocker.get());
	}
	
	public void push(Actor a){
		stack.add(a);
		addActor(a);
	}
	
	public void pop() {
		if(stack.size()>0){
			removeActor(stack.remove(0));	
			
		}
		removeActor(InputBlocker.get());
	}
	

}
