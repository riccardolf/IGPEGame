package it.unical.igpe.screens;

import java.io.File;

import javax.swing.JFileChooser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import it.unical.igpe.game.IGPEGame;

public class LevelChooseScreen implements Screen {
	private SpriteBatch batch;
	private Stage stage;
	private Table table;
	private Label title;
	private TextButton defaultLevel;
	private TextButton chooseLevel;
	private TextButton returnButton;
	public String world;

	@Override
	public void show() {
		batch = new SpriteBatch();
		batch.getProjectionMatrix().setToOrtho2D(0, 0, 900, 506);

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		title = new Label("Choose Level", IGPEGame.skinsoldier);

		defaultLevel = new TextButton("Default Level", IGPEGame.skinsoldier);
		defaultLevel.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				ScreenManager.CreateGS("map.txt");
				IGPEGame.game.setScreen(ScreenManager.LS);
			}
		});

		chooseLevel = new TextButton("Choose Level", IGPEGame.skinsoldier);
		chooseLevel.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("."));
				fileChooser.setAcceptAllFileFilterUsed(false);
				fileChooser.showOpenDialog(fileChooser);
				File file = fileChooser.getSelectedFile();
				if (file != null) {
					ScreenManager.CreateGS(file.getPath());
					IGPEGame.game.setScreen(ScreenManager.LS);
				}
			}
		});

		returnButton = new TextButton("Return", IGPEGame.skinsoldier);
		returnButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				IGPEGame.game.setScreen(ScreenManager.MMS);
			}
		});
		table.add(title);
		table.row();
		table.add(defaultLevel);
		table.row();
		table.add(chooseLevel);
		table.row();
		table.add(returnButton);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(IGPEGame.background, 0, 0);
		batch.end();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();
		batch.dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
