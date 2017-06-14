package it.unical.igpe.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import it.unical.igpe.game.IGPEGame;

public class MultiScreen implements Screen {
	private IGPEGame game;

	private SpriteBatch batch;
	private Stage stage;
	private Table tableChoose;
	private Table tableServer;
	private Table tableClient;
	private TextButton returnButton;
	private TextButton chosenReturnClientButton;
	private TextButton chosenReturnServerButton;
	private TextButton Client;
	private TextButton Server;
	private TextButton connectClient;
	private TextButton createServer;
	private TextButton selectMap;
	private Label clientLabel;
	private Label serverLabel;
	private Label IPClientLabel;
	private Label IPServerLabel;
	private Label nameLabel;
	private TextField nameText;
	private TextField IPClientText;
	private TextField IPServerText;
	
	public MultiScreen(IGPEGame _game) {
		this.game = _game;
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		batch.getProjectionMatrix().setToOrtho2D(0, 0, 900, 506);

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		tableChoose = new Table();
		tableServer = new Table();
		tableClient = new Table();
		tableChoose.setFillParent(true);
		tableServer.setFillParent(true);
		tableClient.setFillParent(true);
		stage.addActor(tableChoose);
		stage.addActor(tableServer);
		stage.addActor(tableClient);
		tableChoose.setDebug(false);
		tableServer.setDebug(false);
		tableClient.setDebug(false);
		
		tableChoose.setVisible(true);
		tableClient.setVisible(false);
		tableServer.setVisible(false);

		returnButton = new TextButton("Return", IGPEGame.skinsoldier);
		returnButton.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(ScreenManager.MMS);
			}
		});
		
		chosenReturnClientButton = new TextButton("Return", IGPEGame.skinsoldier);
		chosenReturnClientButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				tableChoose.setVisible(true);
				tableClient.setVisible(false);
				tableServer.setVisible(false);
			}
		});
		
		chosenReturnServerButton = new TextButton("Return", IGPEGame.skinsoldier);
		chosenReturnServerButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				tableChoose.setVisible(true);
				tableClient.setVisible(false);
				tableServer.setVisible(false);
			}
		});
		
		
		Client = new TextButton("Connect to a Server", IGPEGame.skinsoldier);
		Client.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				tableChoose.setVisible(false);
				tableClient.setVisible(true);
				tableServer.setVisible(false);
			}
		});
		
		Server = new TextButton("Create Server", IGPEGame.skinsoldier);
		Server.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				tableChoose.setVisible(false);
				tableClient.setVisible(false);
				tableServer.setVisible(true);
			}
		});
		
		connectClient = new TextButton("Connect to the server", IGPEGame.skinsoldier);
		connectClient.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO: Connection to the server
			}
		});
		
		createServer = new TextButton("Create", IGPEGame.skinsoldier);
		createServer.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO: Creation server
			}
		});
		
		selectMap = new TextButton("Select Map", IGPEGame.skinsoldier);
		selectMap.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO: Selection Map
			}
		});
		
		
		clientLabel = new Label("Client", IGPEGame.skinsoldier);
		serverLabel = new Label("Server", IGPEGame.skinsoldier);
		IPClientLabel = new Label("IP", IGPEGame.skinsoldier);
		IPServerLabel = new Label("IP", IGPEGame.skinsoldier);
		nameLabel = new Label("Name", IGPEGame.skinsoldier);
		nameText = new TextField("Pasticciopoli", IGPEGame.skinsoldier);
		IPClientText = new TextField("127.0.0.1", IGPEGame.skinsoldier);
		IPServerText = new TextField("127.0.0.1", IGPEGame.skinsoldier);
		
		tableChoose.add(Client);
		tableChoose.row();
		tableChoose.add(Server);
		tableChoose.row();
		tableChoose.add(returnButton);
		
		tableClient.add(clientLabel);
		tableClient.row();
		tableClient.add(nameLabel);
		tableClient.add(nameText).width(200);
		tableClient.row();
		tableClient.add(IPClientLabel);
		tableClient.add(IPClientText).width(200);
		tableClient.row();
		tableClient.add(connectClient);
		tableClient.row();
		tableClient.add(chosenReturnClientButton);
		
		tableServer.add(serverLabel);
		tableServer.row();
		tableServer.add(IPServerLabel);
		tableServer.add(IPServerText).width(200);
		tableServer.row();
		tableServer.add(selectMap);
		tableServer.row();
		tableServer.add(createServer);
		tableServer.row();
		tableServer.add(chosenReturnServerButton);
		
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
		// TODO Auto-generated method stubs
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