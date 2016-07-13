package forer.tann.videogame;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
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
import com.badlogic.gdx.utils.viewport.FitViewport;

import forer.tann.videogame.puzzles.crossword.Crossword;
import forer.tann.videogame.puzzles.crossword.CrosswordScreen;
import forer.tann.videogame.puzzles.picross.PicrossScreen;
import forer.tann.videogame.screens.PuzzleTestScreen;
import forer.tann.videogame.screens.Screen;
import forer.tann.videogame.screens.dialogue.DialogueScreen;
import forer.tann.videogame.screens.titleScreen.TitleScreen;
import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Convertilator;
import forer.tann.videogame.utilities.graphics.Draw;
import forer.tann.videogame.utilities.graphics.TextRenderer;
import forer.tann.videogame.utilities.graphics.font.TannFont;

public class Main extends ApplicationAdapter {
	public static int width=300,height=200;
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
	private static ArrayList<Screen> screens = new ArrayList<>();
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
					toggleMenu();
					return false;
				}
				currentScreen.keyPressed(keycode);
				return true;
			}


		});

		setScale(scale);
		
		
		
		screens.add(new DialogueScreen("All was quiet at 62 Farwell Road (click to continue)", "quiet"));
		screens.add(new DialogueScreen("John was in his favourite chair", "sitting"));
		screens.add(new DialogueScreen("Clank- thud. \"must be the paper\", thought John", "post"));
		screens.add(new DialogueScreen("\"Sounds like it's getting bad out there\"", "headline"));
		screens.add(new DialogueScreen("\"While I'm safe here, out in the countryside\"", "hands"));
		screens.add(new DialogueScreen("John always did the crossword", "crossword"));
		screens.add(new CrosswordScreen());
		setScreen(screens.get(6));

//		setScreen(screens.get(0));

	}

	public void nextScreen(){
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
//			stage.addActor(InputBlocker.get());
//			stage.addActor(PauseScreen.get());
			setState(MainState.Paused);
		}
		else {
//			InputBlocker.get().remove();
//			PauseScreen.get().remove();
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
		setScreen(screen);
		screen.setActive(false);
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

	public void setScreen(Screen screen){
		if(previousScreen!=null){
			previousScreen.clearActions();
			previousScreen.remove();
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
		if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
			delta/=10f;
		}
		ticks+=delta;
		stage.act(delta);
	}
}
