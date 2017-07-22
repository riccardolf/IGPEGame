package it.unical.igpe.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import it.unical.igpe.game.IGPEGame;
import it.unical.igpe.utils.GameConfig;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();

		cfg.title = GameConfig.GAMENAME;
		cfg.width = GameConfig.WIDTH;
		cfg.height = GameConfig.HEIGHT;
		cfg.addIcon("icon.png", Files.FileType.Internal);
		new LwjglApplication(new IGPEGame(), cfg);
	}
}
