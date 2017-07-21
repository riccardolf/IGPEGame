package it.unical.igpe.net.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import it.unical.igpe.GUI.Assets;
import it.unical.igpe.GUI.SoundManager;
import it.unical.igpe.GUI.screens.ScreenManager;
import it.unical.igpe.game.IGPEGame;
import it.unical.igpe.utils.GameConfig;

public class MultiplayerOverScreen implements Screen {
	private float time;
	public static String winner;
	public static int kills;
	
	private SpriteBatch batch;
	public Stage stage;
	private Table table;
	private Label title;
	private Label winnerText;
	private Label killsText;
	
	public MultiplayerOverScreen() {
		batch = new SpriteBatch();
		batch.getProjectionMatrix().setToOrtho2D(0, 0, GameConfig.BACKGROUNDWIDTH, GameConfig.BACKGROUNDHEIGHT);

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		title = new Label("THE GAME IS OVER", IGPEGame.skinsoldier);
		
		table.add(title);
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		SoundManager.manager.get(SoundManager.GameMusic, Music.class).stop();
		SoundManager.manager.get(SoundManager.MenuMusic, Music.class).play();
		SoundManager.manager.get(SoundManager.MenuMusic, Music.class).setVolume(GameConfig.MUSIC_VOLUME);
		SoundManager.manager.get(SoundManager.MenuMusic, Music.class).setLooping(true);
		
		winnerText = new Label("THE WINNER IS " + winner, IGPEGame.skinsoldier);
		killsText = new Label("WITH " + kills + " KILLS", IGPEGame.skinsoldier);
		
		table.row();
		table.add(winnerText);
		table.row();
		table.add(killsText);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(IGPEGame.background,0,0);
		batch.end();
		
		time += delta;
		if (time > 1) {
			if (Gdx.input.isKeyPressed(Keys.ANY_KEY) || Gdx.input.justTouched()) {
				time = 0;
				Assets.manager.clear();
				if(IGPEGame.game.socketServer != null)
					IGPEGame.game.socketServer.close();
				table.removeActor(winnerText);
				table.removeActor(killsText);
				IGPEGame.game.setScreen(ScreenManager.MMS);
			}
		}
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}

	@Override
	public void dispose() {
		
	}
	
	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

}
