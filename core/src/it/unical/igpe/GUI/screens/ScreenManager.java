package it.unical.igpe.GUI.screens;

import it.unical.igpe.MapUtils.World;
import it.unical.igpe.game.IGPEGame;
import it.unical.igpe.net.screens.MultiplayerGameScreen;
import it.unical.igpe.net.screens.MultiplayerOverScreen;
import it.unical.igpe.net.screens.MultiplayerPauseScreen;

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
	public static MultiplayerPauseScreen MPS;
	public static MultiplayerOverScreen MOS;
	
	public ScreenManager(IGPEGame game) {
		MMS = new MainMenuScreen(game);
		LCS = new LevelChooseScreen(game);
		LCompletedS = new LevelCompletedScreen(game);
		OS = new OptionScreen(game);
		PS = new PauseScreen(game);
		LS = new LoadingScreen();
		MS = new MultiScreen(game);
		MOS = new MultiplayerOverScreen(game);
		IGPEGame.game.setScreen(MMS);
	}
	
	public static void CreateGS(String path, IGPEGame game) {
		GS = new GameScreen(new World(path, game));
	}
	
	public static void CreateMGS(IGPEGame game) {
		MGS = new MultiplayerGameScreen(game);
		MPS = new MultiplayerPauseScreen(game);
	}
}
