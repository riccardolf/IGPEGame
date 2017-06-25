package it.unical.igpe.GUI;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
	public static AssetManager manager = new AssetManager();

	// SFX
	public static String FootStep = "Audio/footstep.ogg";
	public static String PistolFire = "Audio/pistol_fire.ogg";
	public static String RifleFire = "Audio/rifle_fire.wav";
	public static String ShotgunFire = "Audio/shotgun_fire.wav";
	public static String PistolReload = "Audio/pistol_reload.ogg";
	public static String ShotgunReload = "Audio/shotgun_reload.ogg";
	public static String TrapClosing = "Audio/trap.ogg";
	public static String HealthRestored = "Audio/health.ogg";

	// MUSIC
	public static String GameMusic = "Audio/gamemusic.ogg";

	public static void load() {
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
