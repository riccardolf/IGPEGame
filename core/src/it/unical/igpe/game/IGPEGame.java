package it.unical.igpe.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import it.unical.igpe.screens.ScreenManager;
import it.unical.igpe.tools.GameConfig;

public class IGPEGame extends Game {
	
	public static Music music;
	
	@Override
	public void create() {
		new ScreenManager(this);
		music = Gdx.audio.newMusic(Gdx.files.internal("Audio/music.mp3"));
		music.play();
		music.setLooping(true);
		this.setScreen(ScreenManager.MMS);
	}

	@Override
	public void render() {
		music.setVolume(GameConfig.MUSIC_VOLUME);
		super.render();
	}

}
