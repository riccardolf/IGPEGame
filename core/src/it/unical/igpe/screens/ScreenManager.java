package it.unical.igpe.screens;

import it.unical.igpe.game.IGPEGame;
import it.unical.igpe.game.World;
import it.unical.igpe.net.MultiplayerGameScreen;
import it.unical.igpe.net.MultiplayerPauseScreen;

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
	
	public ScreenManager() {
		MMS = new MainMenuScreen();
		LCS = new LevelChooseScreen();
		LCompletedS = new LevelCompletedScreen();
		OS = new OptionScreen();
		PS = new PauseScreen();
		LS = new LoadingScreen();
		MS = new MultiScreen();
		IGPEGame.game.setScreen(MMS);
	}
	
	public static void CreateGS(String path) {
		GS = new GameScreen(new World(path));
	}
	
	public static void CreateMGS() {
		MGS = new MultiplayerGameScreen();
		MPS = new MultiplayerPauseScreen();
	}
}
