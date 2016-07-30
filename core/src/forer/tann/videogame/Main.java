package forer.tann.videogame;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import forer.tann.videogame.puzzles.crossword.Crossword;
import forer.tann.videogame.puzzles.crossword.CrosswordScreen;
import forer.tann.videogame.puzzles.picross.PicrossScreen;
import forer.tann.videogame.puzzles.wordsearch.WordSearchScreen;
import forer.tann.videogame.screens.PauseScreen;
import forer.tann.videogame.screens.Screen;
import forer.tann.videogame.screens.dialogue.DialogueScreen;
import forer.tann.videogame.screens.titleScreen.TitleScreen;
import forer.tann.videogame.utilities.Sounds;
import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Convertilator;
import forer.tann.videogame.utilities.graphics.Draw;
import forer.tann.videogame.utilities.graphics.InputBlocker;
import forer.tann.videogame.utilities.graphics.TextRenderer;
import forer.tann.videogame.utilities.graphics.font.TannFont;

public class Main extends ApplicationAdapter {
	public static int width=300,height=210;
	SpriteBatch batch;
	Stage stage;
	OrthographicCamera cam;
	public static TextureAtlas atlas;
	public static Main self;
	public static int scale=3;
	public static boolean debug = true;
	public Screen currentScreen;
	Screen previousScreen;
	FrameBuffer buffer;
	public static float ticks;
	public static int coloursUnlocked=2;
	public enum MainState{Normal, Paused}
	public static final int version = 0;
	private static ArrayList<Screen> screens = new ArrayList<Screen>();
	@Override
	public void create () {
		self=this;
		buffer = new FrameBuffer(Format.RGBA8888, Main.width, Main.height, false);
		atlas= new TextureAtlas(Gdx.files.internal("atlas_image.atlas"));
		TextRenderer.staticSetup();
		stage = new Stage(new FitViewport(Main.width, Main.height));
		cam =(OrthographicCamera) stage.getCamera();
		batch = (SpriteBatch) stage.getBatch();
		Gdx.input.setInputProcessor(stage);
		stage.addListener(new InputListener(){
			public boolean keyDown (InputEvent event, int keycode) {

				switch(keycode){
				case Keys.ESCAPE:
					if(currentScreen.pop()){
						return false;
					}
					if(state==MainState.Paused){
						pop();
					}
					else{
						toggleMenu();
					}
					return false;
				}
				currentScreen.keyPressed(keycode);
				return true;
			}


		});

		setScale(scale);
		
		
		screens.add(new DialogueScreen("All was quiet at 62 Wren Road (click to continue)", "quiet", new Runnable() {public void run() { Sounds.playMusic("Kai_Engel_-_07_-_May");}}));
		screens.add(new DialogueScreen("John was in his favourite chair", "sitting"));
		screens.add(new DialogueScreen("Clank- thud. [tco]\"Must be the paper\"[tcl], thought John", "post"));
		screens.add(new DialogueScreen("[tco]\"Sounds like it's getting bad out there\"", "headline"));
		screens.add(new DialogueScreen("[tco]\"While I'm safe here, out in the countryside\"", "hands"));
		screens.add(new DialogueScreen("John always did the crossword", "crossword3"));
		screens.add(CrosswordScreen.get());
		screens.add(new DialogueScreen("'If you can solve this, please call this number'", "crossword_completed", new Runnable() {public void run() { Sounds.playMusic("King_Olivers_Creole_Jazz_Band_-_Snake_Rag");}}));
		screens.add(new DialogueScreen("'A great opportunity awaits'", "phone"));
		screens.add(new DialogueScreen("[tcb]\"And you say you got 'ash' for 13 across?\"", "churchill_phone"));
		screens.add(new DialogueScreen("*TAP-A-TAP-A-TAP*", "agent"));
		screens.add(new DialogueScreen("[tcb]\"You're just the man we've been looking for!\"", "churchill_sepia"));
		screens.add(new DialogueScreen("[tcb]\"We're completely stumped on these nazi codes\"", "car"));
		screens.add(new DialogueScreen("[tcb]\"We need you to join our team at\"", "car2"));
		screens.add(new DialogueScreen("[tcb]\"Bletchley Park!\"", "bletch"));
		screens.add(new DialogueScreen("[tco]\"But I've never cracked a code before!\"", "grandpa_path"));
		screens.add(new DialogueScreen("[tcb]\"We found these on a german scientist\"", "papers"));
		screens.add(new DialogueScreen("[tcb]\"But nobody can work out what it means.\"", "meeting"));
		screens.add(new DialogueScreen("[tcb]\"Let's just see what you can do!\"", "hand_envelope2"));
		screens.add(new PicrossScreen("gun"));
		screens.add(new DialogueScreen("[tcb]\"A gun eh? Let's see what the second one is.\"", "hand_envelope"));
		screens.add(new PicrossScreen("skull"));
		screens.add(new DialogueScreen("[tcb]\"Hmm, poison and a gun... [tcl]POISON BULLETS!\"", "churchill_think", new Runnable() {public void run() { Sounds.playMusic("Eddie_Elkins_Orchestra_-_April_Showers");}}));
		screens.add(new DialogueScreen("[tcb]\"We must invent antidote grenades immediately!\"", "writing"));
		screens.add(new DialogueScreen("[tcb]\"But this is really amazing work, John!\"", "churchill_happy"));
		screens.add(new DialogueScreen("[tcl]\"n...e...s...a...b...\"", "telephone_workers"));
		screens.add(new DialogueScreen("[tcb]\"We just intercepted a top secret radio message\"", "secret"));
		screens.add(new DialogueScreen("[tcb]\"What do you think?\"", "letters"));
		screens.add(new DialogueScreen("[tco]\"Hmm... I think if we arrange it in a grid...\"", "grid"));
		screens.add(new WordSearchScreen());
		setScreen(screens.get(0), true);
	}
	
	public enum Conspiracy{MoonBase, NukeBase, MoonNuke};
	
	public void addScreens(Conspiracy consp){
		Sounds.playMusic("Victor_Herbert_Orchestra_-_01_-_1909_-_It_Happened_in_Nordland");
		switch(consp){
		case MoonBase:
			screens.add(new DialogueScreen("[tco]\"Moon...Base?\"", "churchill_conspiracy"));
			screens.add(new DialogueScreen("[tco]\"How did hitler get a base on the moon?\"", "moonbase"));
			screens.add(new DialogueScreen("[tco]\"We must get there!\"", "rocket"));
			break;
		case MoonNuke:
			screens.add(new DialogueScreen("[tco]\"Moon...Nuke?\"", "churchill_conspiracy"));
			screens.add(new DialogueScreen("[tco]\"They're going to blow a chuck off the moon\"", "moon_explosion"));
			screens.add(new DialogueScreen("[tco]\"and send it towards london!!\"", "moon_earth"));
			break;
		case NukeBase:
			screens.add(new DialogueScreen("[tco]\"Nuke...Base?\"", "churchill_conspiracy"));
			screens.add(new DialogueScreen("[tco]\"They must have stolen a nuke from Einstein\"", "nuke"));
			screens.add(new DialogueScreen("[tco]\"We must get it back before they use it!\"", "nuke_explosion"));
			break;
		default:
			break;
		
		}
		addFinalScreens();
	}
	
	public void addFinalScreens(){
		screens.add(new DialogueScreen("[tco]\"We must stop them!\"", "churchill_think"));
		screens.add(new DialogueScreen("The next day", "time"));
		screens.add(new DialogueScreen("The war was won!", "peace", new Runnable() {public void run() { Sounds.playMusic("Victor_Herbert_Orchestra_-_21_-_1912_-_The_Ameer");}}));
		screens.add(new DialogueScreen("[tco]We can't let them know", "victory"));
		screens.add(new DialogueScreen("[tco]That we're this good at solving puzzles", "victory2"));
		screens.add(new DialogueScreen("[tco]We'll just say we used computers", "computers"));
		screens.add(new DialogueScreen("", "end"));
		//final boss crossword!
	}

	public void nextScreen(){
		if(screens.indexOf(currentScreen)==screens.size()-1) return;
		setScreen(screens.get((screens.indexOf(currentScreen)+1)%screens.size()), TransitionType.FADE, Interpolation.linear, 1.4f);
	}

	static Runnable makeRunnable (final Screen target,final TransitionType type, final Interpolation interp, final float time){
		return new Runnable() {
			public void run() {
				Main.self.setScreen(target, type, interp, time);
			}
		};
	}
	
	public void setScale(int scale){
		Main.scale=scale;
		int newWidth = width*scale;
		int newHeight= height*scale;
		Gdx.graphics.setWindowedMode(newWidth, newHeight);
		stage.getViewport().update(newWidth, newHeight);
	}

	public void toggleMenu() {
		if(state!=MainState.Paused){
			stage.addActor(InputBlocker.get());
			stage.addActor(PauseScreen.get());
			setState(MainState.Paused);
		}
		else {
			InputBlocker.get().remove();
			PauseScreen.get().remove();
			setState(MainState.Normal);
		}

	}



	private MainState state=MainState.Normal;
	public MainState getState(){
		return state;
	}
	public void setState(MainState state){
		this.state=state;
	}
	public enum TransitionType{LEFT, RIGHT, FADE};
	public void setScreen(final Screen screen, TransitionType type, Interpolation interp, float speed){
		if(screen==currentScreen)return;
		setScreen(screen, false);
		RunnableAction ra = Actions.run(new Runnable() {
			public void run() {
				screen.setActive(true);
			}
		});
		switch(type){
		case LEFT:
			screen.setPosition(Main.width, 0);
			screen.addAction(Actions.sequence(Actions.moveTo(0, 0, speed, interp), ra));
			previousScreen.addAction(Actions.moveTo(-Main.width, 0, speed, interp));
			break;
		case RIGHT:
			screen.setPosition(-Main.width, 0);
			screen.addAction(Actions.sequence(Actions.moveTo(0, 0, speed, interp), ra));
			previousScreen.addAction(Actions.moveTo(Main.width, 0, speed, interp));
			break;
		case FADE:
			screen.setColor(1, 1, 1, 0);
			screen.setPosition(0, 0);
			previousScreen.addAction(Actions.fadeOut(speed/2, interp));
			screen.addAction(Actions.delay(speed/3, Actions.fadeIn(speed/2, interp)));
			screen.addAction(Actions.after(ra));
			
		}
		previousScreen.addAction(Actions.after(Actions.removeActor()));
	}

	public void setScreen(Screen screen, boolean instant){
		screen.setActive(instant);
		if(previousScreen!=null){
			previousScreen.clearActions();
			previousScreen.remove();
			currentScreen.setActive(false);
		}
		if(currentScreen!=null){
			currentScreen.clearActions();
			previousScreen=currentScreen;
			currentScreen.setActive(false);
		}
		currentScreen=screen;
		stage.addActor(screen);
		if(previousScreen!=null) stage.addActor(previousScreen);

	}
	@Override
	public void render () {

		update(Gdx.graphics.getDeltaTime());

		buffer.bind();
		buffer.begin();
		batch.begin();
		batch.setColor(Colours.DARK);
		batch.setProjectionMatrix(cam.combined);
		Draw.fillRectangle(batch, 0, 0, Main.width, Main.height);
		batch.end();
		batch.setColor(Colours.zWHITE);
		stage.draw();
		batch.begin();

		if(Main.debug)drawFPS(batch);
		batch.end();
		buffer.end();

		batch.begin();
		batch.setColor(1,1,1,1);
		buffer.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		Draw.draw(batch, buffer.getColorBufferTexture(), 0, height, 1, -1);
		batch.end();
	}

	public void drawFPS(Batch batch){
		batch.setColor(Colours.LIGHT);
		TannFont.font.draw(batch, "FPS: "+Gdx.graphics.getFramesPerSecond(), 0, Main.height);
	}


	public void update(float delta){
		ticks+=delta;
		Sounds.tick(delta);
		stage.act(delta);
	}

	public boolean pop() {
		if(PauseScreen.get().pop()){
			return true;
		}
		return false;
	}
}
