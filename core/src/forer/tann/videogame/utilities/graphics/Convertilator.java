package forer.tann.videogame.utilities.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;

import forer.tann.videogame.Main;

public class Convertilator {

	static final int TARGET_WIDTH = Main.width;
	static final int TARGET_HEIGHT = Main.height;
	static Color[] colours = new Color[]{Colours.BLUE, Colours.DARK, Colours.RED, Colours.LIGHT, Colours.GREEN};
	public static void convertilate(String filename){
		Texture t = new Texture(Gdx.files.internal(filename));
		TextureData data = t.getTextureData();
		data.prepare();
		Pixmap p = data.consumePixmap();

		Pixmap targetMap = new Pixmap(TARGET_WIDTH, TARGET_HEIGHT, Format.RGBA8888);
		
		float widthRatio = p.getWidth()/(float)targetMap.getWidth();
		float heightRatio = p.getWidth()/(float)targetMap.getWidth();
		int searchWidth = (int) (TARGET_WIDTH*widthRatio);
		int searchHeight = (int) (TARGET_HEIGHT*heightRatio);
		for(int targetX =0;targetX<TARGET_WIDTH;targetX++){
			for(int targetY =0;targetY<TARGET_HEIGHT;targetY++){
				int startX = (int) (targetX/(float)TARGET_WIDTH);
				int startY = (int) (targetY/(float)TARGET_HEIGHT);
				double[] stdDeviations = new double[colours.length];
				for(int sourceX = startX; sourceX<startX+searchWidth;sourceX++){
					for(int sourceY = startY; sourceY<startY+searchHeight;sourceY++){
						for(int colourIndex=0;colourIndex<colours.length;colourIndex++){
							stdDeviations[colourIndex] += checkDeviation(colours[colourIndex], p.getPixel(sourceX, sourceY));
						}
					}
				}
				//now add
				
				
				targetMap.drawPixel(targetX, targetY);
				
			}
		}

	}
	private static double checkDeviation(Color a, int pixel) {
		Color b = new Color(pixel);
		double rDiff = Math.pow(a.r-b.r,2);
		double gDiff = Math.pow(a.r-b.r,2);
		double bDiff = Math.pow(a.r-b.r,2);
		return rDiff+gDiff+bDiff;
	}

}
