package it.unical.igpe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	public static Texture textureBack;
	public static Texture textureSet;
	public static Texture Ground;
	public static Texture Wall;
	
	public static Animation<TextureRegion> runningAnimation;
	public static TextureAtlas atlas;
	
	
	public static void load() {
		// load texture for blocks
		textureSet = new Texture(Gdx.files.internal("tileset.png"));		
		Ground = new Texture(Gdx.files.internal("ground.png"));
		Wall = new Texture(Gdx.files.internal("wall.png"));

		// load character's texture
		atlas = new TextureAtlas(Gdx.files.internal("handgun_idle.atlas"));
		runningAnimation = new Animation<TextureRegion>(0.03f, atlas.findRegions("handgun_idle"));
	}
}
