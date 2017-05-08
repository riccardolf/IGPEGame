package it.unical.igpe;

import com.badlogic.gdx.Game;

import it.unical.igpe.screens.GameScreen;

public class IGPEGame extends Game {
	
	@Override
	public void create() {
		Assets.load();
		this.setScreen(new GameScreen(this, new World()));
	}

	@Override
	public void render() {
		super.render();
	}

}
