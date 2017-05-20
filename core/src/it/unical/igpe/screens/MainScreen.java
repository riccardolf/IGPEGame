package it.unical.igpe.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import it.unical.igpe.game.IGPEGame;
import it.unical.igpe.game.World;

public class MainScreen implements Screen {
	
	private Stage stage;
	private Table table;
	private TextButtonStyle TBS;
	private BitmapFont font;
	private TextButton startButton;
	
	private IGPEGame game;
	
	public MainScreen(IGPEGame _game) {
		game = _game;
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		font = new BitmapFont();
		TBS = new TextButtonStyle();
		TBS.font = font;
		TBS.fontColor = Color.BLACK;
		
		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		startButton = new TextButton("Start", TBS);
		startButton.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(new GameScreen(game, new World()));				
			}
		});
		table.add(startButton);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
	}

	@Override
	public void pause() {}
	
	@Override
	public void resume() {}
}
