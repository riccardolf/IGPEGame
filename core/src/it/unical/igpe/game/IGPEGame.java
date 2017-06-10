package it.unical.igpe.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import it.unical.igpe.screens.ScreenManager;
import it.unical.igpe.tools.GameConfig;

public class IGPEGame extends Game {
	
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
		
		music = Gdx.audio.newMusic(Gdx.files.internal("Audio/music.mp3"));
		music.play();
		music.setLooping(true);
		
		new ScreenManager(this);
		this.setScreen(ScreenManager.MMS);
	}

	@Override
	public void render() {
		music.setVolume(GameConfig.MUSIC_VOLUME);
		super.render();
	}

}
