package it.unical.igpe.game;

import com.badlogic.gdx.Game;

import it.unical.igpe.screens.MainMenuScreen;
import it.unical.igpe.tools.Assets;

public class IGPEGame extends Game {
	
	@Override
	public void create() {
		//this.setScreen(new GameScreen(this, new World()));
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

}
