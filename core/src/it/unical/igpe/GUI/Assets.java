package it.unical.igpe.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	public static AssetManager manager = new AssetManager();
	public static String Ground = "ground.png";
	public static String Wall = "wall.png";
	public static String Stair = "stairO.png";
	public static String StairClosed = "stairC.png";
	public static String Light = "enemy/light.png";
	public static String HealthPack = "hp.png";
	public static String TrapOpen = "trapO.png";
	public static String TrapClosed = "trapC.png";
	public static String KeyY = "keyY.png";
	public static String KeyR = "keyR.png";
	public static String KeyG = "keyG.png";
	public static String KeyB = "keyB.png";
	public static String Key = "key.png";
	public static String AmmoBox = "ammobox.png";
	public static String Box = "box.png";
	public static String Cactus = "cactus.png";
	public static String Logs = "logs.png";
	public static String Barrel = "barrel.png";
	public static String Plant = "plant.png";

	public static TextureAtlas atlas;
	public static TextureRegion Skull;
	
	//Player
	public static Animation<TextureRegion> runningPistolAnimation;
	public static Animation<TextureRegion> idlePistolAnimation;
	public static Animation<TextureRegion> reloadingPistolAnimation;
	public static Animation<TextureRegion> shootingPistolAnimation;
	public static Animation<TextureRegion> runningShotgunAnimation;
	public static Animation<TextureRegion> idleShotgunAnimation;
	public static Animation<TextureRegion> reloadingShotgunAnimation;
	public static Animation<TextureRegion> shootingShotgunAnimation;
	public static Animation<TextureRegion> runningRifleAnimation;
	public static Animation<TextureRegion> idleRifleAnimation;
	public static Animation<TextureRegion> reloadingRifleAnimation;
	public static Animation<TextureRegion> shootingRifleAnimation;
	
	//Enemy
	public static Animation<TextureRegion> eRunningPistolAnimation;
	public static Animation<TextureRegion> eIdlePistolAnimation;
	public static Animation<TextureRegion> eReloadingPistolAnimation;
	public static Animation<TextureRegion> eShootingPistolAnimation;
	public static Animation<TextureRegion> eRunningShotgunAnimation;
	public static Animation<TextureRegion> eIdleShotgunAnimation;
	public static Animation<TextureRegion> eReloadingShotgunAnimation;
	public static Animation<TextureRegion> eShootingShotgunAnimation;
	public static Animation<TextureRegion> eRunningRifleAnimation;
	public static Animation<TextureRegion> eIdleRifleAnimation;
	public static Animation<TextureRegion> eReloadingRifleAnimation;
	public static Animation<TextureRegion> eShootingRifleAnimation;

	/**
	 * Load assets for the game
	 */
	public static void load() {

		// load texture for blocks
		manager.load(Ground, Texture.class);
		manager.load(Wall, Texture.class);
		manager.load(Stair, Texture.class);
		manager.load(StairClosed, Texture.class);
		manager.load(Light, Texture.class);
		manager.load(HealthPack, Texture.class);
		manager.load(TrapOpen, Texture.class);
		manager.load(TrapClosed, Texture.class);
		manager.load(KeyY, Texture.class);
		manager.load(KeyR, Texture.class);
		manager.load(KeyG, Texture.class);
		manager.load(KeyB, Texture.class);
		manager.load(Key, Texture.class);
		manager.load(AmmoBox, Texture.class);
		manager.load(Box, Texture.class);
		manager.load(Cactus, Texture.class);
		manager.load(Logs, Texture.class);
		manager.load(Barrel, Texture.class);
		manager.load(Plant, Texture.class);

		// load character's animations
		Skull = new TextureRegion(new Texture(Gdx.files.internal("enemy/skull.png")));
		
		// Player
		atlas = new TextureAtlas(Gdx.files.internal("player/handgun_move.atlas"));
		runningPistolAnimation = new Animation<TextureRegion>(0.03f, atlas.findRegions("handgun_move"));
		atlas = new TextureAtlas(Gdx.files.internal("player/handgun_idle.atlas"));
		idlePistolAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("handgun_idle"));
		atlas = new TextureAtlas(Gdx.files.internal("player/handgun_reload.atlas"));
		reloadingPistolAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("handgun_reload"));
		atlas = new TextureAtlas(Gdx.files.internal("player/handgun_shoot.atlas"));
		shootingPistolAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("handgun_shoot"));

		atlas = new TextureAtlas(Gdx.files.internal("player/rifle_move.atlas"));
		runningRifleAnimation = new Animation<TextureRegion>(0.03f, atlas.findRegions("rifle_move"));
		atlas = new TextureAtlas(Gdx.files.internal("player/rifle_idle.atlas"));
		idleRifleAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("rifle_idle"));
		atlas = new TextureAtlas(Gdx.files.internal("player/rifle_reload.atlas"));
		reloadingRifleAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("rifle_reload"));
		atlas = new TextureAtlas(Gdx.files.internal("player/rifle_shoot.atlas"));
		shootingRifleAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("rifle_shoot"));

		atlas = new TextureAtlas(Gdx.files.internal("player/shotgun_move.atlas"));
		runningShotgunAnimation = new Animation<TextureRegion>(0.03f, atlas.findRegions("shotgun_move"));
		atlas = new TextureAtlas(Gdx.files.internal("player/shotgun_idle.atlas"));
		idleShotgunAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("shotgun_idle"));
		atlas = new TextureAtlas(Gdx.files.internal("player/shotgun_reload.atlas"));
		reloadingShotgunAnimation = new Animation<TextureRegion>(0.04f, atlas.findRegions("shotgun_reload"));
		atlas = new TextureAtlas(Gdx.files.internal("player/shotgun_shoot.atlas"));
		shootingShotgunAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("shotgun_shoot"));
		
		// Enemy
		
		atlas = new TextureAtlas(Gdx.files.internal("enemy/handgun_move.atlas"));
		eRunningPistolAnimation = new Animation<TextureRegion>(0.03f, atlas.findRegions("handgun_move"));
		atlas = new TextureAtlas(Gdx.files.internal("enemy/handgun_idle.atlas"));
		eIdlePistolAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("handgun_idle"));
		atlas = new TextureAtlas(Gdx.files.internal("enemy/handgun_reload.atlas"));
		eReloadingPistolAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("handgun_reload"));
		atlas = new TextureAtlas(Gdx.files.internal("enemy/handgun_shoot.atlas"));
		eShootingPistolAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("handgun_shoot"));

		atlas = new TextureAtlas(Gdx.files.internal("enemy/rifle_move.atlas"));
		eRunningRifleAnimation = new Animation<TextureRegion>(0.03f, atlas.findRegions("rifle_move"));
		atlas = new TextureAtlas(Gdx.files.internal("enemy/rifle_idle.atlas"));
		eIdleRifleAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("rifle_idle"));
		atlas = new TextureAtlas(Gdx.files.internal("enemy/rifle_reload.atlas"));
		eReloadingRifleAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("rifle_reload"));
		atlas = new TextureAtlas(Gdx.files.internal("enemy/rifle_shoot.atlas"));
		eShootingRifleAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("rifle_shoot"));

		atlas = new TextureAtlas(Gdx.files.internal("enemy/shotgun_move.atlas"));
		eRunningShotgunAnimation = new Animation<TextureRegion>(0.03f, atlas.findRegions("shotgun_move"));
		atlas = new TextureAtlas(Gdx.files.internal("enemy/shotgun_idle.atlas"));
		eIdleShotgunAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("shotgun_idle"));
		atlas = new TextureAtlas(Gdx.files.internal("enemy/shotgun_reload.atlas"));
		eReloadingShotgunAnimation = new Animation<TextureRegion>(0.04f, atlas.findRegions("shotgun_reload"));
		atlas = new TextureAtlas(Gdx.files.internal("enemy/shotgun_shoot.atlas"));
		eShootingShotgunAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("shotgun_shoot"));

	}
}
