package it.unical.igpe.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;

import it.unical.igpe.HUD.HUD;
import it.unical.igpe.game.IGPEGame;
import it.unical.igpe.game.World;
import it.unical.igpe.tools.Assets;
import it.unical.igpe.tools.GameConfig;
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
		IGPEGame.music.pause();
		Assets.manager.get(Assets.GameMusic, Music.class).setLooping(true);
		Assets.manager.get(Assets.GameMusic, Music.class).play();
	}

	@Override
	public void render(float delta) {
		delta = 0.01f;
		world.update(delta);
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.render(delta);
		hud.render(world.getPlayer());
		
		Assets.manager.get(Assets.GameMusic, Music.class).setVolume(GameConfig.MUSIC_VOLUME);
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))
			game.setScreen(ScreenManager.PS);
		if(world.isLevelFinished() || world.isGameOver())
			game.setScreen(ScreenManager.LCompletedS);
	}

	@Override
	public void resize(int width, int height) {
		renderer.viewport.update(width, height, true);
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
