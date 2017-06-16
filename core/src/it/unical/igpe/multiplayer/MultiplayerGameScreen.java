package it.unical.igpe.multiplayer;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;

import it.unical.igpe.HUD.HUD;
import it.unical.igpe.game.IGPEGame;

public class MultiplayerGameScreen implements Screen{
	// message to server player.pos, player.angle, player.state, player.activeWeapon.ID, initial pos player.fire()
	// message from server other players, enemies
	// draw this things on multiplayer
	MultiplayerWorld world;
	IGPEGame game;
	HUD hud;
	MultiplayerWorldRenderer renderer;
	public int port;

	public MultiplayerGameScreen(IGPEGame _game) {
		this.game = _game;
	}

	@Override
	public void show() {
		this.renderer = new MultiplayerWorldRenderer(world);
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void render(float delta) {
		delta = 0.01f;
		renderer.render(delta);
		if(Gdx.input.isKeyJustPressed(Keys.ANY_KEY)) {
			System.out.println("trying to send message");
			game.client.sendMessage(new ServerMessage(0));
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

}
