package it.unical.igpe.screens;

import it.unical.igpe.game.IGPEGame;
import it.unical.igpe.game.World;

public class ScreenManager {
	public static MainMenuScreen MMS;
	public static LevelChooseScreen LCS;
	public static GameScreen GS;
	public static LevelCompletedScreen LCompletedS;
	public static OptionScreen OS;
	public static PauseScreen PS;
	public static LoadingScreen LS;
	
	private static IGPEGame game;
	
	public ScreenManager(IGPEGame _game) {
		game = _game;
		MMS = new MainMenuScreen(game);
		LCS = new LevelChooseScreen(game, MMS);
		LCompletedS = new LevelCompletedScreen(game);
		OS = new OptionScreen(game, MMS);
		PS = new PauseScreen(game, GS);
		LS = new LoadingScreen(game);
		game.setScreen(MMS);
	}
	
	public static void CreateGameScreen(String path) {
		GS = new GameScreen(game, new World(path));
	}
}
