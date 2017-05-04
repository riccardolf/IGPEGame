package it.unical.igpe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	public static Texture textureBack;
	public static Texture textureSet;
	public static TextureRegion Water;
	public static TextureRegion Sand;
	public static TextureRegion Grass;
	public static TextureRegion Wood;
	
	public static Animation<TextureRegion> walkAnimation;
	public static Animation<TextureRegion> shootAnimation;
	public static Texture walkSheet;
	
	
	public static void load() {
		// load texture for blocks
		textureSet = new Texture(Gdx.files.internal("tileset.png"));
		TextureRegion[][] tmp = TextureRegion.split(textureSet, 32, 32);
		Water = tmp[0][0];
		Sand = tmp[0][1];
		Grass = tmp[0][2];
		Wood = tmp[0][3];

		// load character's texture
		walkSheet = new Texture(Gdx.files.internal("player.png"));
		tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / 2, walkSheet.getHeight() / 4);
		TextureRegion[] walkFrames = new TextureRegion[2 * 3];
		TextureRegion[] shootFrames = new TextureRegion[2 * 1];
		int index = 0;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 2; j++)
				walkFrames[index++] = tmp[i][j];
		
		index = 0;
		for(int i = 3; i < 4; i++)
			for(int j = 0; j < 2; j++)
				shootFrames[index++] = tmp[i][j];
		
		walkAnimation = new Animation<TextureRegion>(0.1f, walkFrames);
		shootAnimation = new Animation<TextureRegion>(0.3f, shootFrames);
	}
}
