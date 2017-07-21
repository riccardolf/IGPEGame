package it.unical.igpe.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

import it.unical.igpe.GUI.SoundManager;
import it.unical.igpe.GUI.screens.ScreenManager;
import it.unical.igpe.net.GameClient;
import it.unical.igpe.net.GameServer;
import it.unical.igpe.net.MultiplayerWorld;
import it.unical.igpe.net.packet.Packet01Disconnect;
import it.unical.igpe.utils.GameConfig;

public class IGPEGame extends Game implements Disposable {
	private static final String PREFS_NAME = "my_game";
	private static final String MUSIC_VOLUME = "musicvolume";
	private static final String SOUND_VOLUME = "soundvolume";
	private static final String FULLSCREEN = "fullscreen";
	public static IGPEGame game;
	public static Texture background;
	public static Skin skinsoldier;
	public static Skin skinComic;
	public static Skin skinUi;

	private Preferences prefs;
	public MultiplayerWorld worldMP;
	public GameClient socketClient;
	public GameServer socketServer;
	
	public SoundManager soundManager;

	@Override
	public void create() {
		game = this;
		soundManager = new SoundManager();
		
		background = new Texture(Gdx.files.internal("background.jpg"));

		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("skin/starsoldier/star-soldier-ui.atlas"));
		skinsoldier = new Skin(Gdx.files.internal("skin/starsoldier/star-soldier-ui.json"), atlas);

		atlas = new TextureAtlas(Gdx.files.internal("skin/comic/comic-ui.atlas"));
		skinComic = new Skin(Gdx.files.internal("skin/comic/comic-ui.json"));
		
		atlas = new TextureAtlas(Gdx.files.internal("skin/ui/uiskin.atlas"));
		skinUi = new Skin(Gdx.files.internal("skin/ui/uiskin.json"));
		
		GameConfig.MUSIC_VOLUME = this.getMusicVolume();
		GameConfig.SOUND_VOLUME = this.getSoundVolume();
		GameConfig.isFullscreen = this.getFullScreen();

		if (GameConfig.isFullscreen)
			Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

		new ScreenManager(this);
		this.setScreen(ScreenManager.MMS);
	}

	@Override
	public void render() {
		super.render();
	}

	public void dispose() {
		if (this.socketServer != null) {
			Packet01Disconnect packetDisconnect = new Packet01Disconnect(IGPEGame.game.worldMP.player.getUsername());
			packetDisconnect.writeData(IGPEGame.game.socketClient);
			IGPEGame.game.socketServer.close();
		}
	}

	public void setVolume() {
		prefs.putFloat(MUSIC_VOLUME, GameConfig.MUSIC_VOLUME);
		prefs.putFloat(SOUND_VOLUME, GameConfig.SOUND_VOLUME);
		prefs.flush();
	}

	public void setFullScreen() {
		prefs.putBoolean(FULLSCREEN, GameConfig.isFullscreen);
		prefs.flush();
	}

	public float getMusicVolume() {
		if (prefs == null) {
			prefs = Gdx.app.getPreferences(PREFS_NAME);
		}
		return prefs.getFloat(MUSIC_VOLUME, 1.0f);
	}

	public float getSoundVolume() {
		if (prefs == null)
			prefs = Gdx.app.getPreferences(PREFS_NAME);
		return prefs.getFloat(SOUND_VOLUME, 1.0f);
	}

	public boolean getFullScreen() {
		if (prefs == null)
			prefs = Gdx.app.getPreferences(PREFS_NAME);
		return prefs.getBoolean(FULLSCREEN, false);
	}

}
