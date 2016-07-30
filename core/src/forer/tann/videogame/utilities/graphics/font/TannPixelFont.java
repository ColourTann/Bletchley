package forer.tann.videogame.utilities.graphics.font;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.CharArray;

import forer.tann.videogame.Main;
import forer.tann.videogame.utilities.graphics.Draw;

public class TannPixelFont extends TannFont{


	HashMap<Character, TextureRegion> glyphs = new HashMap<Character, TextureRegion>();
	int[] heights= new int[]{-1,-1,-1}; 
	private int scale=1;
	public TannPixelFont(TextureRegion font, int scale) {
		this.scale=scale;
		Pixmap p = Draw.getPixmap(font);
		int heightIndex=0;
		int height =0;
		for(int y=0;y<font.getRegionHeight();y++){
			boolean foundPixel=false;
			for(int x=0;x<font.getRegionWidth();x++){
				int col = p.getPixel(font.getRegionX()+x, font.getRegionY()+y);
				Color c = new Color(col);

				if(c.a>0){
					foundPixel=true;
					break;
				}
			}
			if(y==font.getRegionHeight()-1||!foundPixel){
				if(y==font.getRegionHeight()-1) y++;
				heights[heightIndex++]=y-height;
				height=y+1;
			}
		}
		//all the characters split by rows
		String[] chars =  new String[]{
				"0123456789.,!?:;()\"+-/_%='*",
				"ABCDEFGHIJKLMNOPQRSTUVWXYZ",
				"abcdefghijklmnopqrstuvwxyz"
		};
		int x=0;
		int y=0;
		for(int row=0;row<chars.length;row++){
			for(char c:chars[row].toCharArray()){
				for(int dx=0;true;dx++){
					//check to see if the column contains no set pixels
					//this will only work if no characters in the font have vertical gaps
					boolean empty=true;
					for(int dy=0;dy<=heights[row];dy++){
						int col = p.getPixel(font.getRegionX()+x+dx, font.getRegionY()+y+dy);
						if(col==-1){
							empty=false;
							break;
						}
					}
					if(!empty)continue;
					TextureRegion region = new TextureRegion(font, x, y, dx, heights[row]);
					if (c >= 'A' && c <= 'Z') {
						// Put the lower case glyph in too
						glyphs.put((char) (c - 'A' + 'a'), region);
					}
					glyphs.put(c, region);
					x+=dx+1;
					break;
				}
			}
			x=0;
			y+=heights[row]+1;
		}
	}

	public void draw(Batch batch, CharSequence text, float x, float y){
		//will probably want to cache this
		drawInternal(batch, text, x, y, Align.left, true);
	}

	public void draw(Batch batch, CharSequence text, float x, float y, int align) {
		if(align==Align.center){
			drawInternal(batch, text, x-getWidth(text)/2, y-getHeight()/2, Align.left, true);
		}
	}

	public void unscaledDraw(Batch batch, CharSequence text, float x, float y){
		drawInternal(batch, text, x, y, Align.left, false);
	}

	private void drawInternal(Batch batch, CharSequence text, float fx, float fy, int align, boolean scaled){
		int x = (int)fx;
		int y = (int)fy;
		if(align==Align.center){
			x-=getWidth(text)/2;
			y-=getHeight()/2;
		}
		for (int i = 0; i < text.length(); i++){
			char c = text.charAt(i);
			if(c==' '){
				x+=getSpaceWidth();
				continue;
			}
			TextureRegion t= glyphs.get(c);
			if(t==null){
				System.out.println("character not in font: "+c);
				continue;
			}
			int bonusY=0;
			if(c>='a' && c<='z'){
				if(align!=Align.center)bonusY=-2*getScale();
			}
			Draw.draw(batch, t, x, y+bonusY, scaled?getScale():scale, scaled?getScale():scale);
			x+=t.getRegionWidth()*getScale()+getKerning();
		}
	}


	static CharArray badChars = new CharArray();	
	public int getWidth(CharSequence text){
		//need to take into account spaces
		int total=0;
		for(int i=0;i<text.length();i++){
			char c = text.charAt(i);
			if(c==' ') total+=getSpaceWidth();
			else {
				TextureRegion g = glyphs.get(c);
				if(g==null){
					if(!badChars.contains(c)){
						badChars.add(c);
						System.out.println("can't find glyph "+c+" in the font");
					}
					continue;
				}
				total+=g.getRegionWidth()*getScale();
				if(i<text.length()-1)total+=getKerning();
			}
		}
		return total;
	}

	public int getHeight(){
		return heights[0]*getScale();
	}
	public int getLineHeight(){
		return (heights[0]+3)*getScale();
	}
	public int getSpaceWidth(){
		return 4*getScale();
	}
	public int getKerning(){
		return 1*getScale();
	}

	int getScale(){
		return scale;
	}
}
