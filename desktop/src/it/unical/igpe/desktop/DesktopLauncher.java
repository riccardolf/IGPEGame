package it.unical.igpe.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import it.unical.igpe.tools.GameConfig;
import it.unical.igpe.game.IGPEGame;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		
		cfg.title = GameConfig.GAMENAME;
		cfg.width = GameConfig.WIDTH;
		cfg.height = GameConfig.HEIGHT;
		cfg.resizable = false;
		
		new LwjglApplication(new IGPEGame(), cfg);
	}
}
