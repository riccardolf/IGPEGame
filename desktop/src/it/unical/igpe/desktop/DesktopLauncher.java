package it.unical.igpe.desktop;

import javax.swing.JFrame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import it.unical.igpe.tools.GameConfig;
import it.unical.igpe.game.IGPEGame;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		
//		JFrame initialWindow = new JFrame(cfg.title = GameConfig.GAMENAME);
//		initialWindow.add(new InitialPanel());
//		initialWindow.setResizable(false);
//		initialWindow.pack();
//		
//		initialWindow.setLocation(null);
//		initialWindow.setVisible(true);
//		initialWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		cfg.title = GameConfig.GAMENAME;
		cfg.width = GameConfig.WIDTH;
		cfg.height = GameConfig.HEIGHT;
		cfg.resizable = false;
		
		new LwjglApplication(new IGPEGame(), cfg);
	}
}
