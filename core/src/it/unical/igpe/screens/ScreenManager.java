package it.unical.igpe.screens;

import it.unical.igpe.game.IGPEGame;
import it.unical.igpe.game.World;
import it.unical.igpe.multiplayer.MultiplayerGameScreen;

public class ScreenManager {
	public static MainMenuScreen MMS;
	public static LevelChooseScreen LCS;
	public static GameScreen GS;
	public static LevelCompletedScreen LCompletedS;
	public static OptionScreen OS;
	public static PauseScreen PS;
	public static LoadingScreen LS;
	public static MultiScreen MS;
	public static MultiplayerGameScreen MGS;
	
	
	private static IGPEGame game;
	
	public ScreenManager(IGPEGame _game) {
		game = _game;
		MMS = new MainMenuScreen(game);
		LCS = new LevelChooseScreen(game);
		LCompletedS = new LevelCompletedScreen(game);
		OS = new OptionScreen(game);
		PS = new PauseScreen(game);
		LS = new LoadingScreen(game);
		MS = new MultiScreen(game);
		game.setScreen(MMS);
	}
	
	public static void CreateGameScreen(String path) {
		GS = new GameScreen(game, new World(path));
	}
}
