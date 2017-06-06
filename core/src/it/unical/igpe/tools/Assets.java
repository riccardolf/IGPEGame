package it.unical.igpe.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	public static Texture textureBack;
	public static Texture Ground;
	public static Texture Wall;
	public static Texture Stair;
	public static Texture Light;
	
	public static TextureRegion Enemy;
	
	public static TextureAtlas atlas;
	public static Animation<TextureRegion> runningPistolAnimation;
	public static Animation<TextureRegion> idlePistolAnimation;
	public static Animation<TextureRegion> reloadingPistolAnimation;
	public static Animation<TextureRegion> runningShotgunAnimation;
	public static Animation<TextureRegion> idleShotgunAnimation;
	public static Animation<TextureRegion> reloadingShotgunAnimation;
	public static Animation<TextureRegion> runningRifleAnimation;
	public static Animation<TextureRegion> idleRifleAnimation;
	public static Animation<TextureRegion> reloadingRifleAnimation;
	
	public static void load() {
		
		// load texture for blocks	
		Ground = new Texture(Gdx.files.internal("ground.png"));
		Wall = new Texture(Gdx.files.internal("wall.png"));
		Stair = new Texture(Gdx.files.internal("stair.png"));
		Enemy = new TextureRegion(new Texture(Gdx.files.internal("idle.png")));
		Light = new Texture(Gdx.files.internal("light.png"));
		
		// load character's texture
		atlas = new TextureAtlas(Gdx.files.internal("handgun_move.atlas"));
		runningPistolAnimation = new Animation<TextureRegion>(0.03f, atlas.findRegions("handgun_move"));
		atlas = new TextureAtlas(Gdx.files.internal("handgun_idle.atlas"));
		idlePistolAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("handgun_idle"));
		atlas = new TextureAtlas(Gdx.files.internal("handgun_reload.atlas"));
		reloadingPistolAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("handgun_reload"));
		
		atlas = new TextureAtlas(Gdx.files.internal("rifle_move.atlas"));
		runningRifleAnimation = new Animation<TextureRegion>(0.03f, atlas.findRegions("rifle_move"));
		atlas = new TextureAtlas(Gdx.files.internal("rifle_idle.atlas"));
		idleRifleAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("rifle_idle"));
		atlas = new TextureAtlas(Gdx.files.internal("rifle_reload.atlas"));
		reloadingRifleAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("rifle_reload"));
		
		atlas = new TextureAtlas(Gdx.files.internal("shotgun_move.atlas"));
		runningShotgunAnimation = new Animation<TextureRegion>(0.03f, atlas.findRegions("shotgun_move"));
		atlas = new TextureAtlas(Gdx.files.internal("shotgun_idle.atlas"));
		idleShotgunAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("shotgun_idle"));
		atlas = new TextureAtlas(Gdx.files.internal("shotgun_reload.atlas"));
		reloadingShotgunAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("shotgun_reload"));
		
	}
}
