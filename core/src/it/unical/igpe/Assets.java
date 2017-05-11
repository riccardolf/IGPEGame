package it.unical.igpe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	public static Texture textureBack;
	public static Texture textureSet;
	public static TextureRegion Water;
	public static TextureRegion Sand;
	public static TextureRegion Grass;
	public static TextureRegion Wood;
	
	public static Animation<TextureRegion> runningAnimation;
	public static TextureAtlas atlas;
	
	
	public static void load() {
		// load texture for blocks
		textureSet = new Texture(Gdx.files.internal("tileset.png"));
		TextureRegion[][] tmp = TextureRegion.split(textureSet, 32, 32);
		Water = tmp[0][0];
		Sand = tmp[0][1];
		Grass = tmp[0][2];
		Wood = tmp[0][3];
		

		// load character's texture
		atlas = new TextureAtlas(Gdx.files.internal("rifle_reload.atlas"));
		runningAnimation = new Animation<TextureRegion>(0.03f, atlas.findRegions("rifle_reload"));
	}
}
