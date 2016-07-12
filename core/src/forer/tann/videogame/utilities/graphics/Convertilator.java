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
import forer.tann.videogame.screens.dialogue.DialogueScreen;

public class Convertilator {

	static final int RESULT_WIDTH = Main.width;
	static final int RESULT_HEIGHT = Main.height-DialogueScreen.GAP;
	//	static Color[] colours = new Color[]{Colours.BLUE, Colours.DARK, Colours.RED, Colours.LIGHT, Colours.GREEN, Colours.zWHITE};
	static Color[] colours = new Color[]{Colours.DARK, Colours.LIGHT, Colours.RED, Colours.GREEN};
	static int sourceWidth;
	static int sourceHeight;
	static float[][][] bonuses;
	static Color[][] sourceColours;
	public static void convertilate(String filename){
		//setting up all the data//
		Texture t = new Texture(Gdx.files.internal(filename));
		sourceWidth = t.getWidth();
		sourceHeight = t.getHeight();
		bonuses = new float[RESULT_WIDTH][RESULT_HEIGHT][3];
		TextureData data = t.getTextureData();
		data.prepare();
		Pixmap sourceMap = data.consumePixmap();
		Pixmap resultMap = new Pixmap(RESULT_WIDTH, RESULT_HEIGHT, Format.RGBA8888);
		float widthRatio = sourceMap.getWidth()/(float)resultMap.getWidth();
		float heightRatio = sourceMap.getWidth()/(float)resultMap.getWidth();


		//load the data into a byte array to avoid io every check//
		ByteBuffer allSourcePixels = sourceMap.getPixels();
		byte[] allColours = new byte[t.getWidth()*3*t.getHeight()];
		allSourcePixels.get(allColours);
		//find the number of pixels to average from the source to get a single pixel in result//
		//eg 2x2 area//
		int searchWidth=Math.max(1, (int) widthRatio);
		int searchHeight=Math.max(1, (int) heightRatio);
		//		searchWidth=1;
		//		searchHeight=1;
		sourceColours = new Color[sourceWidth][sourceHeight];
		for(int x=0;x<sourceWidth;x++){
			for(int y=0;y<sourceHeight;y++){
				sourceColours[x][y] = getColour(x, y, allColours);
			}
		}


//		chooseBestColours(4);

		//targetX and targetY refer to the final target pixels on the result
		for(int targetY =0;targetY<RESULT_HEIGHT;targetY++){
			for(int targetX =0;targetX<RESULT_WIDTH;targetX++){
				//startX/Y refer to where to start looking for pixels on the source image
				int startX = (int) ((targetX/(float)RESULT_WIDTH)*sourceMap.getWidth());
				int startY = (int) ((targetY/(float)RESULT_HEIGHT)*sourceMap.getHeight());
				for(int sourceX = startX; sourceX<startX+searchWidth;sourceX++){
					for(int sourceY = startY; sourceY<startY+searchHeight;sourceY++){
						//iterate around the area and log the colours
						logColour(sourceColours[sourceX][sourceY]);
					}
				}
				//get the best colour and draw a single pixel!
				resultMap.setColor(getBestFit(targetX, targetY));
				resultMap.drawPixel(targetX, targetY);
			}
		}
		//output the result in the desktop folder
		PixmapIO.writePNG(Gdx.files.local("converted/"+filename.split("\\.")[0]+".png"), resultMap);
	}

	private static void chooseBestColours(int num) {
		float[][][] heatMap = new float[256][256][256];
		for(int x=0;x<sourceWidth;x++){
			for(int y=0;y<sourceHeight;y++){
				Color c = sourceColours[x][y];
				heatMap[(int)(c.r*255)][(int)(c.g*255)][(int)(c.b*255)]++;
			}
		}

		Color[] bestColours = new Color[num];
		for(int colourNum=0;colourNum<num;colourNum++){
			float bestValue=0;
			int bestR=0, bestG=0, bestB=0;
			for(int r=0;r<255;r++){
				for(int g=0;g<255;g++){
					for(int b=0;b<255;b++){
						if(heatMap[r][g][b]>bestValue){
							bestValue=heatMap[r][g][b];
							bestR=r;
							bestG=g;
							bestB=b;

						}
					}
				}
			}

			Color bestCol =new Color(bestR/255f, bestG/255f, bestB/255f,1);
			bestColours[colourNum] = bestCol;
			int radius=50;
			for(int x=-radius;x<=radius;x++){
				for(int y=-radius;y<=radius;y++){
					for(int z=-radius;z<=radius;z++){
						int newR=bestR+x;
						int newG=bestG+y;
						int newB=bestB+z;
						if(newR<0||newR>255||newG<0||newG>255||newB<0||newB>255) continue;
						float dist = (float) Math.sqrt(x*x+y*y+z*z);
						heatMap[newR][newG][newB]*=dist/(radius*radius*radius);
					}
				}
			}
			
			float bestHue = getHue((int)(bestCol.r*255), (int)(bestCol.g*255), (int)(bestCol.b*255));
			float bestSat = getSaturation((int)(bestCol.r*255), (int)(bestCol.g*255), (int)(bestCol.b*255));
			float bestVal = getValue((int)(bestCol.r*255), (int)(bestCol.g*255), (int)(bestCol.b*255));
			System.out.println(bestHue+":"+bestVal+":"+bestSat);
			
			float hueRange = 40;
			// 0-1 for the next two
			float hueStrength = 1;
			float falloff = .6f;
			if(bestVal<88 && bestSat>13 && hueStrength>0){
				for(int r=0;r<255;r++){
					for(int g=0;g<255;g++){
						for(int b=0;b<255;b++){
							float hue = getHue(r,g,b);
							float hueDiff = Math.abs(hue-bestHue);
							if(hueDiff<hueRange){
								float multiplier = (1-falloff)+(hueDiff/hueRange)*falloff;
								float takeOff = heatMap[r][g][b] * multiplier * hueStrength; 
								
								heatMap[r][g][b]-=takeOff;
							}
						}
					}
				}
			}


		}


		colours=bestColours;

	}

	public static float getSaturation(int r, int g, int b){
		float fRed = r/255f;
		float fGreen = g/255f;
		float fBlue = b/255f;
		float max = Math.max(fRed, Math.max(fGreen, fBlue));
		return max * 100;
	}

	public static float getValue(int r, int g, int b){
		float fRed = r/255f;
		float fGreen = g/255f;
		float fBlue = b/255f;
		float max = Math.max(fRed, Math.max(fGreen, fBlue));
		float min = Math.min(fRed, Math.min(fGreen, fBlue));
		if(max==0) return 0;
		return ((max-min)/max) * 100;
	}

	public static float getHue(int r, int g, int b){
		float fRed = r/255f;
		float fGreen = g/255f;
		float fBlue = b/255f;
		float hue =0;
		float max = Math.max(fRed, Math.max(fGreen, fBlue));
		float min = Math.min(fRed, Math.min(fGreen, fBlue));
		float delta = max-min;
		if(delta==0) return 0;
		if(fRed==max){
			hue = (fGreen-fBlue)/(delta);
		}
		else if(fGreen==max){
			hue = 2 + (fBlue-fRed)/(delta);
		}
		else{
			hue = 4 + (fRed-fGreen)/(delta);
		}
		hue *= 60;
		if(hue<0) hue += 360;
		return hue;
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
		int range = 2;
		for(int bx=-range;bx<=range;bx++){
			for(int by=-range;by<=range;by++){
				int newX = x+bx;
				int newY = y+by;
				if(newX<0||newY<0||newX>=RESULT_WIDTH||newY>=RESULT_HEIGHT) continue;
				float dist = bx*bx+by*by;
				//ratio is the general bonus multiplier
				float ratio = .5f;
				//range multiplier is how the distance affects it
				float rangeMultiplier=dist;
				rangeMultiplier=.6f/rangeMultiplier;
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
