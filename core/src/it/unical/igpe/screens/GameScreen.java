package it.unical.igpe.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import it.unical.igpe.HUD.HUD;
import it.unical.igpe.game.IGPEGame;
import it.unical.igpe.game.World;
import it.unical.igpe.tools.MapRenderer;

public class GameScreen implements Screen {
	World world;
	IGPEGame game;
	HUD hud;
	MapRenderer renderer;

	public GameScreen(IGPEGame _game, World _world) {
		this.game = _game;
		this.world = _world;
	}

	@Override
	public void show() {
		this.renderer = new MapRenderer(world);
		this.hud = new HUD();
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void render(float delta) {
		delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());
		world.updateWorld(delta);
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.render(delta);
		hud.render(world.getPlayer());
		
		if(world.isGameOver())
			game.setScreen(new GameOverScreen(game));
		if(world.isLevelFinished())
			game.setScreen(new LevelCompletedScreen(game));
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void dispose() {
		hud.dispose();
		renderer.dispose();
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
