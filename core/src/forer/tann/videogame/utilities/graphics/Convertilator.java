package forer.tann.videogame.utilities.graphics;

import java.nio.ByteBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;

import forer.tann.videogame.Main;

public class Convertilator {

	static final int RESULT_WIDTH = Main.width;
	static final int RESULT_HEIGHT = Main.height;
	//	static Color[] colours = new Color[]{Colours.BLUE, Colours.DARK, Colours.RED, Colours.LIGHT, Colours.GREEN, Colours.zWHITE};
	static Color[] colours = new Color[]{Color.RED, Color.WHITE, Color.BLACK};
	static int sourceWidth;
	static int sourceHeight;
	static float[][][] bonuses;
	public static void convertilate(String filename){
		//setting up all the data//
		Texture t = new Texture(Gdx.files.internal(filename));
		sourceWidth = t.getWidth();
		sourceHeight = t.getHeight();
		bonuses = new float[sourceWidth][sourceHeight][3];
		TextureData data = t.getTextureData();
		data.prepare();
		Pixmap sourceMap = data.consumePixmap();
		Pixmap resultMap = new Pixmap(RESULT_WIDTH, RESULT_HEIGHT, Format.RGBA8888);
		float widthRatio = sourceMap.getWidth()/(float)resultMap.getWidth();
		float heightRatio = sourceMap.getWidth()/(float)resultMap.getWidth();

		//find the number of pixels to average from the source to get a single pixel in result//
		//eg 2x2 area//
		int searchWidth = (int) (RESULT_WIDTH*widthRatio);
		int searchHeight = (int) (RESULT_HEIGHT*heightRatio);

		//load the data into a byte array to avoid io every check//
		ByteBuffer allSourcePixels = sourceMap.getPixels();
		byte[] allColours = new byte[t.getWidth()*3*t.getHeight()];
		allSourcePixels.get(allColours);
		searchWidth=(int) widthRatio;
		searchHeight=(int) heightRatio;
		//		searchWidth=1;
		//		searchHeight=1;

		//targetX and targetY refer to the final target pixels on the result
		for(int targetY =0;targetY<RESULT_HEIGHT;targetY++){
			for(int targetX =0;targetX<RESULT_WIDTH;targetX++){
				//startX/Y refer to where to start looking for pixels on the source image
				int startX = (int) ((targetX/(float)RESULT_WIDTH)*sourceMap.getWidth());
				int startY = (int) ((targetY/(float)RESULT_HEIGHT)*sourceMap.getHeight());
				for(int sourceX = startX; sourceX<startX+searchWidth;sourceX++){
					for(int sourceY = startY; sourceY<startY+searchHeight;sourceY++){
						//iterate around the area and log the colours
						logColour(getColour(sourceX, sourceY, allColours));
					}
				}
				//get the best colour and draw a single pixel!
				resultMap.setColor(getBestFit(targetX, targetY));
				resultMap.drawPixel(targetX, targetY);
			}
		}
		//output the result in the desktop folder
		PixmapIO.writePNG(Gdx.files.local("converted_"+filename), resultMap);
	}

	static float r, g, b;
	static int count;
	static float bonusR, bonusG, bonusB;

	private static void logColour(Color colour){
		r+=colour.r; g+=colour.g; b+=colour.b;
		count++;		
	}

	public static Color getBestFit(int x, int y){
		//r,g,b store the total amount of those colours so must be divided by the number of pixels sampled
		r/=count; g/=count; b/=count;
		
		//look through to find the best colour
		Color best=null;
		float bestDiff=9999;
		for(Color c:colours){
			//add the bonuses from nearby pixels!
			float diff = Math.abs(c.r-(r+bonuses[x][y][0])) + Math.abs(c.g-(g+bonuses[x][y][1])) + Math.abs(c.b-(b+bonuses[x][y][2]));
			if(diff<bestDiff){
				bestDiff=diff;
				best=c;
			}
		}
		//range of how far to set the bonuses
		int range = 3;
		for(int bx=-range;bx<=range;bx++){
			for(int by=-range;by<=range;by++){
				int newX = x+bx;
				int newY = y+by;
				if(newX<0||newY<0||newX>sourceWidth||newY>sourceHeight) continue;
				float dist = bx*bx+by*by;
				//ratio is the general bonus multiplier
				float ratio = .5f;
				//range multiplier is how the distance affects it
				float rangeMultiplier=dist;
				rangeMultiplier=1.5f/rangeMultiplier;
				//add the r/g/b difference to the bonuses array to keep track of how the colours are wrong
				bonuses[newX][newY][0]+=(r-best.r)*(ratio*rangeMultiplier);
				bonuses[newX][newY][1]+=(g-best.g)*(ratio*rangeMultiplier);
				bonuses[newX][newY][2]+=(b-best.b)*(ratio*rangeMultiplier);
			}
		}
		count=0;
		r=0;
		g=0;
		b=0;
		return best;
	}

	private static Color getColour(int x, int y, byte[] bytes){
		//create a Color object from a byte array! I can probably be more efficient but it only takes like 5 seconds so ¯\_(ツ)_/¯ 
		int start = y*sourceWidth*3 + x*3;
		int r = (int)bytes[start] & 0xff;
		int g = (int)bytes[start+1] & 0xff;
		int b = (int)bytes[start+2] & 0xff;
		return new Color(r/255f,g/255f,b/255f,1);
	}

}
