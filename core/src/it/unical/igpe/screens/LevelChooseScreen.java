package it.unical.igpe.screens;

import java.io.File;

import javax.swing.JFileChooser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import it.unical.igpe.game.IGPEGame;
import it.unical.igpe.game.World;
import it.unical.igpe.tools.Assets;

public class LevelChooseScreen implements Screen {
	private IGPEGame game;

	private Skin skin;
	private TextureAtlas atlas;
	private Texture mainMenu;
	private SpriteBatch batch;
	private Stage stage;
	private Table table;
	private Label title;
	private TextButton defaultLevel;
	private TextButton chooseLevel;
	private TextButton returnButton;
	private Screen prevScreen;
	public String world;

	public LevelChooseScreen(IGPEGame _game, Screen _prevScreen) {
		this.game = _game;
		this.prevScreen = _prevScreen;
	}

	@Override
	public void show() {
		mainMenu = new Texture(Gdx.files.internal("MainMenu.jpg"));
		atlas = new TextureAtlas(Gdx.files.internal("skin/starsoldier/star-soldier-ui.atlas"));
		skin = new Skin(Gdx.files.internal("skin/starsoldier/star-soldier-ui.json"), atlas);

		batch = new SpriteBatch();
		batch.getProjectionMatrix().setToOrtho2D(0, 0, 900, 506);

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		title = new Label("Choose Level", skin);

		defaultLevel = new TextButton("Default Level", skin);
		defaultLevel.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Assets.load();
				game.setScreen(new GameScreen(game, new World("map.txt")));
			}
		});

		chooseLevel = new TextButton("Choose Level", skin);
		chooseLevel.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("."));
				fileChooser.setAcceptAllFileFilterUsed(false);
				fileChooser.showOpenDialog(fileChooser);
				File file = fileChooser.getSelectedFile();
				if (file != null) {
					Assets.load();
					game.setScreen(new GameScreen(game, new World(file.getPath())));
				}
			}
		});

		returnButton = new TextButton("Return", skin);
		returnButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(prevScreen);
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
		batch.draw(mainMenu, 0, 0);
		batch.end();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();
		mainMenu.dispose();
		skin.dispose();
		atlas.dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
