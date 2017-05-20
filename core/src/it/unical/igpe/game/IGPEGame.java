package it.unical.igpe.game;

import com.badlogic.gdx.Game;

import it.unical.igpe.screens.GameScreen;
import it.unical.igpe.screens.MainScreen;
import it.unical.igpe.tools.Assets;

public class IGPEGame extends Game {
	
	@Override
	public void create() {
		Assets.load();
		//this.setScreen(new GameScreen(this, new World()));
		this.setScreen(new MainScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

}
