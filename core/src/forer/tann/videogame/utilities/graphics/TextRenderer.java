package forer.tann.videogame.utilities.graphics;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import forer.tann.videogame.Main;
import forer.tann.videogame.utilities.graphics.font.TannFont;



public class TextRenderer extends Actor{
	private static Color defaultColour=Colours.LIGHT;
	private static TannFont defaultFont=TannFont.font;
	Color textColour = defaultColour;
	Color pictureColour = Colours.zWHITE;
	TannFont font = defaultFont;
	int wrapWidth;
	String text;
	int fontHeight;
	int align = Align.center;
	private FrameBuffer buffer;

	public TextRenderer(String text){
		setup(text, defaultFont, (int)font.getWidth(text), Align.center);
	}

	public TextRenderer(String text, int boxWidth) {
		setup(text, defaultFont, boxWidth, Align.center);
	}

	public TextRenderer(String text, TannFont font, int boxWidth) {
		setup(text, font, boxWidth, Align.center);
	}

	public TextRenderer(String text, TannFont font, int boxWidth, int align) {
		setup(text, font, boxWidth, align, defaultColour);
	}

	public TextRenderer(String text, TannFont font, int boxWidth, int align, Color colour) {
		setup(text, font, boxWidth, align, colour);
	}

	private void setup(String text, TannFont font, int boxWidth, int align){
		setup(text, font, boxWidth, align, defaultColour);
	}

	private void setup(String text, TannFont font, int boxWidth, int align, Color colour){
		this.align=align;
		this.font=font;
		this.text=text;
		this.wrapWidth=boxWidth;
		fontHeight=(int) (getHeight());		
		textColour=colour;
		setupLines(text);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		//		font.draw(batch, "TOIJEOIJ OIDSJA OISDJ OIASJ DOIJSA DOI", 50, 50);
		Draw.draw(batch, buffer.getColorBufferTexture(), (int)getX(), (int)getY()+getHeight(), 1, -1);
		super.draw(batch, parentAlpha);
	}

	Array<Line> lines = new Array<Line>();
	SpriteBatch batch = new SpriteBatch();
	OrthographicCamera bufferCam;
	private void setupLines(String entireText){

		int baseLineHeight = font.getLineHeight(); 
		int currentX=0;
		int currentY=font.getHeight();
		int lineHeight = baseLineHeight;
		int spaceWidth = font.getSpaceWidth();
		char[] charArray = entireText.toCharArray();
		int previousIndex=0;
		lines.clear();
		Line currentLine= new Line();
		boolean specialMode=false;
		for(int index = 0; index<=charArray.length; index++){
			boolean finishedWord=false;
			char c=0;
			if(index==charArray.length) finishedWord=true;
			else{
				c = charArray[index];
				if(c==' '||c=='['||c==']')finishedWord=true;
			}
			if(finishedWord){
				String word = entireText.substring(previousIndex, index);
				TextureRegion tr=null;
				int length=0;
				previousIndex=index+1;
				if(specialMode){
					boolean specialNewLine=false;
					boolean cancelSpecial=false;
					boolean formatting=false;
					boolean space=false;
					int specialLineHeight=0;
					if(word.startsWith("tc")){
						char colour = word.charAt(2);
						textColour = colourFromChar(colour);
						formatting=true;
						cancelSpecial=true;
						space=true;
					}
					else if(word.startsWith("pc")){
						char colour = word.charAt(2);
						pictureColour = colourFromChar(colour);
						formatting=true;
						cancelSpecial=true;
						space=true;
					}
					else if(word.equalsIgnoreCase("n")){
						specialNewLine=true;
						specialLineHeight=lineHeight;
						cancelSpecial=true;
						space=true;
					}
					else if(word.equals("nh")){
						specialNewLine=true;
						cancelSpecial=true;
						specialLineHeight=lineHeight/2;
						space=true;
					}
					else if(word.equals("nq")){
						specialNewLine=true;
						cancelSpecial=true;
						specialLineHeight=lineHeight/4;
						space=true;
					}
					if(specialNewLine){
						currentLine.setWidth(currentX-(specialMode?0:spaceWidth));
						currentLine.setY(currentY);
						lines.add(currentLine);
						currentLine = new Line();
						currentY+=specialLineHeight;
						lineHeight=baseLineHeight;
						currentX=0;
						specialMode=false;
					}
					if(cancelSpecial||formatting){
						specialMode=false;
					}


					if(word.equals("h")){
						space=true;
						currentX+=spaceWidth/2;
					}
					if(word.equals("q")){
						space=true;
						currentX+=spaceWidth/4;
					}
					if(space){
						specialMode=false;
						if(formatting){
							currentX+=font.getKerning();
						}
						continue;
					}
					if(!space && !formatting && specialMode){
						//find texture
						tr = textureMap.get(word);
						if(tr==null){
							System.out.println("couldn't find image id: "+word);
						}
						length = tr.getRegionWidth();
					}


				}
				else{
					length = font.getWidth(word);
				}
				if(currentX+length>wrapWidth){
					//too far, needs new line
					currentLine.setWidth(currentX-(specialMode?0:spaceWidth));
					currentLine.setY(currentY);
					lines.add(currentLine);
					currentLine = new Line();
					currentY+=lineHeight;
					lineHeight=baseLineHeight;
					currentX=0;
				}
				if(specialMode){
					//adjust line height based on texture height
					int diff = tr.getRegionHeight()-lineHeight;
					int newDiff = diff-(lineHeight-baseLineHeight);
					if(diff>0){
						lineHeight+=newDiff;
						currentY+=newDiff;
					}
					currentLine.addTextPosition(new TextPosition(tr, currentX, 0, pictureColour));
				}
				else currentLine.addTextPosition(new TextPosition(word, currentX, 0, textColour));

				currentX+=length;

			}
			if(c=='['){
				specialMode=true;
			}
			if(c==']')specialMode=false;
			if(c==' ')currentX+=spaceWidth;

		}
		if(!lines.contains(currentLine, true)){
			//finish final word
			currentLine.setWidth(currentX);
			currentLine.setY(currentY);
			lines.add(currentLine);
			currentY+=lineHeight;
		}
		currentY-=font.getLineHeight();
		//now setup buffer and draw to it
		batch.setColor(defaultColour);
		int bufferWidth=(int)(wrapWidth);
		int bufferHeight=(int) (currentY);

		if(bufferWidth%2!=0)bufferWidth++;
		if(bufferHeight%2!=0){
			bufferHeight++;
			bonusBonusY=-1;
			setSize(bufferWidth, bufferHeight-1);
		}
		else{
			bonusBonusY=-1;
			setSize(bufferWidth, bufferHeight);

		}


		buffer = new FrameBuffer(Format.RGBA8888, bufferWidth, bufferHeight, false);
		bufferCam = new OrthographicCamera(buffer.getWidth(), buffer.getHeight());
		bufferCam.translate((int)(buffer.getWidth()/2), (int)(buffer.getHeight()/2));
		bufferCam.update();

		buffer.bind();
		buffer.begin();
		batch.setProjectionMatrix(bufferCam.combined);
		batch.begin();
		batch.setColor(getColor());


		for(Line l: lines){
			l.render(batch, align);
		}
		batch.end();
		buffer.end();
		FrameBuffer.unbind();
		buffer.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
	}

	static HashMap<Character, Color> colourMap = new HashMap<>();

	private Color colourFromChar(char colour) {
		return colourMap.get(colour);
	}

	private static HashMap<String, TextureRegion> textureMap = new HashMap<String, TextureRegion>();

	public static void staticSetup(){
		setupTextures();
		setupColours();
	}

	private static void setupColours() {
		colourMap.put('o', Colours.ORANGE);
		colourMap.put('b', Colours.BROWN);
		colourMap.put('d', Colours.DARK);
		colourMap.put('l', Colours.LIGHT);
		colourMap.put('w', Colours.zWHITE);
	}

	public static void setupTextures(){
		setImage("->", Main.atlas.findRegion("right"));
		setImage("v", Main.atlas.findRegion("down"));
		setImage("glasses", Main.atlas.findRegion("glasses"));
	}

	public static void setImage(String id, TextureRegion texture){
		textureMap.put(id, texture);
	}



	private class Line{
		int width;
		int y;
		Array<TextPosition> textPositions = new Array<TextPosition>();
		public Line() {
		}
		public void setWidth(int width) {
			this.width=width;
		}
		public void setY(int y) {
			this.y=y;
		}
		public void addTextPosition(TextPosition tp){
			textPositions.add(tp);
		}
		public void render(Batch batch, int align){
			int bonusX=0;
			if(align == Align.center){
				bonusX=(wrapWidth-width)/2;
			}
			for(TextPosition tp: textPositions) tp.render(batch, bonusX, y);
		}
	}

	static int bonusBonusY;

	private class TextPosition{
		String text; 
		TextureRegion tr;
		int x, y;
		Color colour;
		public TextPosition(String text, int x, int y, Color colour) {
			this.text=text; 
			this.x=x; this.y=y;
			this.colour=colour;
		}
		public TextPosition(TextureRegion tr, int x, int y, Color colour) {
			this.tr=tr; 
			this.x=x; this.y=y;
			this.colour=colour;
		}

		public void render(Batch batch, int bonusX, int bonusY){
			if(tr!=null) {
				Color old = batch.getColor();
				batch.setColor(colour);
				batch.draw(tr, (int)(x+bonusX), (int)(buffer.getHeight()-y+font.getLineHeight()/2f-tr.getRegionHeight()/2f-bonusY+bonusBonusY));
				batch.setColor(old);
			}
			else {
				batch.setColor(colour);
				font.draw(batch, text, (int)(x+bonusX), (int)(buffer.getHeight()-y-bonusY+bonusBonusY+1));
			}
		}
	}
}