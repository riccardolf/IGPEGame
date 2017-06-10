package it.unical.igpe.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	public static AssetManager manager = new AssetManager();
	static String Ground = "ground.png";
	static String Wall = "wall.png";
	static String Stair = "stairO.png";
	static String StairClosed = "stairC.png";
	static String Light = "light.png";
	static String HealthPack = "hp.png";
	static String TrapOpen = "trapO.png";
	static String TrapClosed = "trapC.png";
	static String KeyY = "keyY.png";
	static String KeyR = "keyR.png";
	static String KeyG = "keyG.png";
	static String KeyB = "keyB.png";
	static String Skull = "skull.png";

	public static String FootStep = "Audio/footstep.ogg";
	public static String PistolFire = "Audio/pistol_fire.wav";
	public static String RifleFire = "Audio/rifle_fire.wav";
	public static String ShotgunFire = "Audio/shotgun_fire.wav";

	public static TextureAtlas atlas;
	public static TextureRegion Enemy;
	public static Animation<TextureRegion> runningPistolAnimation;
	public static Animation<TextureRegion> idlePistolAnimation;
	public static Animation<TextureRegion> reloadingPistolAnimation;
	public static Animation<TextureRegion> runningShotgunAnimation;
	public static Animation<TextureRegion> idleShotgunAnimation;
	public static Animation<TextureRegion> reloadingShotgunAnimation;
	public static Animation<TextureRegion> runningRifleAnimation;
	public static Animation<TextureRegion> idleRifleAnimation;
	public static Animation<TextureRegion> reloadingRifleAnimation;

	public static Sound footstep;

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
		manager.load(Skull, Texture.class);

		// load character's animations
		Enemy = new TextureRegion(new Texture(Gdx.files.internal("idle.png")));
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

		// load sound effects
		manager.load(FootStep, Sound.class);
		manager.load(PistolFire, Sound.class);
		manager.load(RifleFire, Sound.class);
		manager.load(ShotgunFire, Sound.class);
	}
}
