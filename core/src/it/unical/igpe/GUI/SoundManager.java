package it.unical.igpe.GUI;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
	public static AssetManager manager = new AssetManager();

	// SFX
	public static String PistolFire = "Audio/pistol_fire.ogg";
	public static String RifleFire = "Audio/rifle_fire.ogg";
	public static String ShotgunFire = "Audio/shotgun_fire.ogg";
	public static String PistolReload = "Audio/pistol_reload.ogg";
	public static String ShotgunReload = "Audio/shotgun_reload.ogg";
	public static String TrapClosing = "Audio/trap.ogg";
	public static String HealthRestored = "Audio/health.ogg";
	public static String Step = "Audio/step.ogg";

	// MUSIC
	public static String GameMusic = "Audio/gamemusic.ogg";
	public static String MenuMusic = "Audio/menumusic.mp3";

	public static void load() {
		// load sound effects
		manager.load(GameMusic, Music.class);
		manager.load(MenuMusic, Music.class);
		manager.load(PistolFire, Sound.class);
		manager.load(RifleFire, Sound.class);
		manager.load(ShotgunFire, Sound.class);
		manager.load(ShotgunReload, Sound.class);
		manager.load(PistolReload, Sound.class);
		manager.load(TrapClosing, Sound.class);
		manager.load(HealthRestored, Sound.class);
		manager.load(Step, Sound.class);
	}
}
