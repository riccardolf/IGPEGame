package it.unical.igpe.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import it.unical.igpe.screens.ScreenManager;
import it.unical.igpe.tools.GameConfig;

public class IGPEGame extends Game {
	private static final String PREFS_NAME = "my_game";
	private static final String MUSIC_VOLUME = "musicvolume";
	private static final String SOUND_VOLUME = "soundvolume";
	private Preferences prefs;
	
	public static Music music;
	public static Texture background;
	public static Skin skinsoldier;
	public static Skin skinui;
	
	@Override
	public void create() {
		background = new Texture(Gdx.files.internal("background.jpg"));
		
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("skin/starsoldier/star-soldier-ui.atlas"));
		skinsoldier = new Skin(Gdx.files.internal("skin/starsoldier/star-soldier-ui.json"), atlas);
		
		atlas = new TextureAtlas(Gdx.files.internal("skin/ui/uiskin.atlas"));
		skinui = new Skin(Gdx.files.internal("skin/ui/uiskin.json"), atlas);
		
		music = Gdx.audio.newMusic(Gdx.files.internal("Audio/menumusic.mp3"));
		music.play();
		music.setLooping(true);
		
		GameConfig.MUSIC_VOLUME = this.getMusicVolume();
		GameConfig.SOUND_VOLUME = this.getSoundVolume();
		
		new ScreenManager(this);
		this.setScreen(ScreenManager.MMS);
	}

	@Override
	public void render() {
		music.setVolume(GameConfig.MUSIC_VOLUME);
		super.render();
	}
	
	public void setVolume() {
		prefs.putFloat(MUSIC_VOLUME, GameConfig.MUSIC_VOLUME);
		prefs.putFloat(SOUND_VOLUME, GameConfig.SOUND_VOLUME);
		prefs.flush();
	}
	
	public float getMusicVolume() {
		if(prefs==null){
			prefs = Gdx.app.getPreferences(PREFS_NAME);
	    }
		return prefs.getFloat(MUSIC_VOLUME, 1.0f);
	}
	
	public float getSoundVolume() {
		if(prefs==null)
			prefs = Gdx.app.getPreferences(PREFS_NAME);
		return prefs.getFloat(SOUND_VOLUME, 1.0f);
	}

}
