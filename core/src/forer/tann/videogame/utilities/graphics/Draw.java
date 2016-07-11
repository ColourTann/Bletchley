package forer.tann.videogame.utilities.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import forer.tann.videogame.Main;

public class Draw {


	public static void draw(Batch batch, Texture t, float x, float y, float xScale, float yScale){
		batch.draw(t, x, y, t.getWidth()*xScale, t.getHeight()*yScale);
	}

	public static void draw(Batch batch, TextureRegion t, float x, float y, float xScale, float yScale){
		batch.draw(t, x, y, t.getRegionWidth()*xScale, t.getRegionHeight()*yScale);
	}

	public static void drawCentered(Batch batch, Texture t, float x, float y, float xScale, float yScale, float radianRotation){
		batch.draw(t, (int) (x - t.getWidth() / 2), (int) (y - t.getHeight() / 2),
				(int) (t.getWidth() / 2f), (int) (t.getHeight() / 2f), t.getWidth(),
				t.getHeight(), xScale, yScale, rad2deg(radianRotation), 0, 0,
				t.getWidth(), t.getHeight(), false, false);
	}

	public static void drawCentered(Batch batch, TextureRegion t, float x, float y, float xScale, float yScale, float radianRotation){
		batch.draw(t, (int) (x - t.getRegionWidth() / 2f),
				(int) (y - t.getRegionHeight() / 2f), (int) (t.getRegionWidth() / 2f),
				(int) (t.getRegionHeight() / 2f), t.getRegionWidth(),
				t.getRegionHeight(), xScale, yScale, rad2deg(radianRotation));
	}

	public static void drawRectangle(Batch batch, float x, float y, float width, float height, float lineWidth) {
		draw(batch, getSq(), x, y, width, lineWidth);
		draw(batch, getSq(), x, y + height - lineWidth, width, lineWidth);
		draw(batch, getSq(), x, y + lineWidth, lineWidth, height - lineWidth * 2);
		draw(batch, getSq(), x + width - lineWidth, y + lineWidth, lineWidth, height - lineWidth * 2);
	}

	public static void fillRectangle(Batch batch, float x, float y, float width, float height) {
		Draw.draw(batch, Draw.getSq(), x, y, width, height);
	}
	
	public static void fillActor(Batch batch, Actor a) {
		Draw.draw(batch, Draw.getSq(), a.getX(), a.getY(), a.getWidth(), a.getHeight());
	}

	static TextureRegion circle256 = Main.atlas.findRegion("circle256");
	public static void fillEllipse(Batch batch, float x, float y, float width, float height){
		Draw.draw(batch, circle256, x-width/2f, y-height/2f, width/256f, height/256f);
	}

	public static void drawLine(Batch batch, float x, float y, float tX, float tY, float width) {
		float dist = (float) Math.sqrt((tX - x) * (tX - x) + (tY - y) * (tY - y));
		float radians = (float) Math.atan2(tY - y, tX - x);
		drawAngledLine(batch, x, y, radians, dist, width);
	}

	private static void drawAngledLine(Batch batch, float x, float y, float radians, float distance, float width) {
		batch.draw(getSq(), (int) x, (int) y, 0f, .5f, getSq().getRegionWidth(), getSq().getRegionHeight(), distance, width, rad2deg(radians));
	}

	// Blending Junk
	public enum BlendType {
		Normal, Additive, Set
	}

	public static void setBlend(Batch batch, BlendType type) {
		switch (type) {
		case Additive:
			batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
			break;
		case Normal:
			Gdx.gl20.glBlendEquation(GL20.GL_FUNC_ADD);
			batch.setBlendFunction(GL20.GL_SRC_ALPHA,
					GL20.GL_ONE_MINUS_SRC_ALPHA);
			break;
		case Set:
			batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ZERO);
		}
	}

	public static float rad2deg(float rad) {
		return (float) (rad * 180f / Math.PI);
	}

	private static AtlasRegion wSq;

	public static AtlasRegion getSq() {
		if (wSq == null) {
			wSq = Main.atlas.findRegion("pixel");
		}
		return wSq;
	}

	public static AtlasRegion getFrame(Array<AtlasRegion> textures, float animationSpeed) {
		return textures.get((int)((Main.ticks*animationSpeed)%textures.size));
	}

	public static Pixmap getPixmap(Texture t){
		t.getTextureData().prepare();
		return t.getTextureData().consumePixmap();
	}

	public static Pixmap getPixmap(TextureRegion t){
		return getPixmap(t.getTexture());
	}

	public static void setBatchColour(Batch batch, Color col, float a) {
		batch.setColor(col.r, col.g, col.b, a);
	}
}
