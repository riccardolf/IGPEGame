package it.unical.igpe.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
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
	public static String Light = "light.png";
	public static String HealthPack = "hp.png";
	public static String TrapOpen = "trapO.png";
	public static String TrapClosed = "trapC.png";
	public static String KeyY = "keyY.png";
	public static String KeyR = "keyR.png";
	public static String KeyG = "keyG.png";
	public static String KeyB = "keyB.png";
	public static String Skull = "skull.png";
	public static String Key = "key.png";
	public static String Crosshair = "crosshair2.png";

	public static String FootStep = "Audio/footstep.ogg";
	public static String PistolFire = "Audio/pistol_fire.ogg";
	public static String RifleFire = "Audio/rifle_fire.wav";
	public static String ShotgunFire = "Audio/shotgun_fire.wav";
	public static String PistolReload = "Audio/pistol_reload.ogg";
	public static String ShotgunReload = "Audio/shotgun_reload.ogg";
	
	public static String TrapClosing = "Audio/trap.ogg";
	public static String HealthRestored = "Audio/health.ogg";
	
	public static String GameMusic = "Audio/gamemusic.ogg";

	public static TextureAtlas atlas;
	public static TextureRegion Enemy;
	public static TextureRegion Bullet;
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
		manager.load(Key, Texture.class);
		manager.load(Crosshair, Pixmap.class);

		// load character's animations
		Bullet = new TextureRegion(new Texture(Gdx.files.internal("bullet.png")));
		Enemy = new TextureRegion(new Texture(Gdx.files.internal("idle.png")));
		atlas = new TextureAtlas(Gdx.files.internal("handgun_move.atlas"));
		runningPistolAnimation = new Animation<TextureRegion>(0.03f, atlas.findRegions("handgun_move"));
		atlas = new TextureAtlas(Gdx.files.internal("handgun_idle.atlas"));
		idlePistolAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("handgun_idle"));
		atlas = new TextureAtlas(Gdx.files.internal("handgun_reload.atlas"));
		reloadingPistolAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("handgun_reload"));
		atlas = new TextureAtlas(Gdx.files.internal("handgun_shoot.atlas"));
		shootingPistolAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("handgun_shoot"));

		atlas = new TextureAtlas(Gdx.files.internal("rifle_move.atlas"));
		runningRifleAnimation = new Animation<TextureRegion>(0.03f, atlas.findRegions("rifle_move"));
		atlas = new TextureAtlas(Gdx.files.internal("rifle_idle.atlas"));
		idleRifleAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("rifle_idle"));
		atlas = new TextureAtlas(Gdx.files.internal("rifle_reload.atlas"));
		reloadingRifleAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("rifle_reload"));
		atlas = new TextureAtlas(Gdx.files.internal("rifle_shoot.atlas"));
		shootingRifleAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("rifle_shoot"));

		atlas = new TextureAtlas(Gdx.files.internal("shotgun_move.atlas"));
		runningShotgunAnimation = new Animation<TextureRegion>(0.03f, atlas.findRegions("shotgun_move"));
		atlas = new TextureAtlas(Gdx.files.internal("shotgun_idle.atlas"));
		idleShotgunAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("shotgun_idle"));
		atlas = new TextureAtlas(Gdx.files.internal("shotgun_reload.atlas"));
		reloadingShotgunAnimation = new Animation<TextureRegion>(0.04f, atlas.findRegions("shotgun_reload"));
		atlas = new TextureAtlas(Gdx.files.internal("shotgun_shoot.atlas"));
		shootingShotgunAnimation = new Animation<TextureRegion>(0.08f, atlas.findRegions("shotgun_shoot"));

		// load sound effects
		manager.load(FootStep, Music.class);
		manager.load(PistolFire, Sound.class);
		manager.load(RifleFire, Sound.class);
		manager.load(ShotgunFire, Sound.class);
		manager.load(GameMusic, Music.class);
		manager.load(ShotgunReload, Sound.class);
		manager.load(PistolReload, Sound.class);
		manager.load(TrapClosing, Sound.class);
		manager.load(HealthRestored, Sound.class);
	}
}
