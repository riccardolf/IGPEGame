package it.unical.igpe.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import it.unical.igpe.utils.GameConfig;

public class SoundManager {
	public String footStep;
	public String pistolFire;
	public String rifleFire;
	public String shotgunFire;
	public String pistolReload;
	public String shotgunReload;
	public String trapClosing;
	public String healthRestored;
	public String gameMusic;
	public String menuMusic;
	
	// SFX
	public Music FootStep;
	public Sound PistolFire;
	public Sound RifleFire;
	public Sound ShotgunFire;
	public Sound PistolReload;
	public Sound ShotgunReload;
	public Sound TrapClosing;
	public Sound HealthRestored;

	// MUSIC
	public Music GameMusic;
	public Music MenuMusic;
	
	public SoundManager() {
		// SFX
		FootStep = Gdx.audio.newMusic(Gdx.files.internal("Audio/footstep.ogg"));
		PistolFire = Gdx.audio.newSound(Gdx.files.internal("Audio/pistol_fire.ogg"));
		RifleFire = Gdx.audio.newSound(Gdx.files.internal("Audio/rifle_fire.ogg"));
		ShotgunFire = Gdx.audio.newSound(Gdx.files.internal("Audio/shotgun_fire.ogg"));
		PistolReload = Gdx.audio.newSound(Gdx.files.internal("Audio/pistol_reload.ogg"));
		ShotgunReload = Gdx.audio.newSound(Gdx.files.internal("Audio/shotgun_reload.ogg"));
		TrapClosing = Gdx.audio.newSound(Gdx.files.internal("Audio/trap.ogg"));
		HealthRestored = Gdx.audio.newSound(Gdx.files.internal("Audio/health.ogg"));

		// MUSIC
		GameMusic = Gdx.audio.newMusic(Gdx.files.internal("Audio/gamemusic.ogg"));
		MenuMusic = Gdx.audio.newMusic(Gdx.files.internal("Audio/menumusic.mp3"));
		
		FootStep.setLooping(true);
		GameMusic.setLooping(true);
		MenuMusic.setLooping(true);
	}
	
	public void play(String stuff) {
		switch (stuff) {
		case "footStep":
			FootStep.setVolume(GameConfig.MUSIC_VOLUME);
			FootStep.play();
			break;
		case "pistolFire":
					
					break;
		case "rifleFire":
			
			break;
		case "shotgunFire":
			
			break;
		case "pistolReload":
			
			break;
		case "shotgunReload":
			
			break;
		case "trapClosing":
			
			break;
		case "healthRestored":
			
			break;
		case "gameMusic":
			GameMusic.setVolume(GameConfig.MUSIC_VOLUME);
			GameMusic.play();		
			break;
		case "menuMusic":
			MenuMusic.setVolume(GameConfig.MUSIC_VOLUME);
			MenuMusic.play();
			break;
		default:
			break;
		}
	}

}