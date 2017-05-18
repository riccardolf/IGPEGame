package it.unical.igpe.tools;

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
	
	public static Animation<TextureRegion> runningPistolAnimation;
	public static Animation<TextureRegion> idlePistolAnimation;
	public static Animation<TextureRegion> reloadingPistolAnimation;
	public static TextureAtlas atlasRunHandgun;
	public static TextureAtlas atlasIdleHandgun;
	public static TextureAtlas atlasReloadHandgun;
	
	public static void load() {
		// load texture for blocks
		textureSet = new Texture(Gdx.files.internal("tileset.png"));		
		Ground = new Texture(Gdx.files.internal("ground.png"));
		Wall = new Texture(Gdx.files.internal("wall.png"));

		// load character's texture
		atlasRunHandgun = new TextureAtlas(Gdx.files.internal("handgun_move.atlas"));
		runningPistolAnimation = new Animation<TextureRegion>(0.03f, atlasRunHandgun.findRegions("handgun_move"));
		atlasIdleHandgun = new TextureAtlas(Gdx.files.internal("handgun_idle.atlas"));
		idlePistolAnimation = new Animation<TextureRegion>(0.08f, atlasIdleHandgun.findRegions("handgun_idle"));
		atlasReloadHandgun = new TextureAtlas(Gdx.files.internal("handgun_reload.atlas"));
		reloadingPistolAnimation = new Animation<TextureRegion>(0.08f, atlasReloadHandgun.findRegions("handgun_reload"));
	}
}
