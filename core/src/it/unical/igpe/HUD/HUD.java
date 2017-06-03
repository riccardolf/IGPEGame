package it.unical.igpe.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;

import it.unical.igpe.logic.Player;

public class HUD implements Disposable {

	private Stage stage;
	private SpriteBatch batch;
	private ProgressBar health;
	private Skin skin;
	private TextureAtlas atlas;
	private Table upperTable;
	private Table bottomTable;

	public HUD() {
		// Loading textures
		atlas = new TextureAtlas(Gdx.files.internal("skin/ui/uiskin.atlas"));
		skin = new Skin(Gdx.files.internal("skin/ui/uiskin.json"), atlas);
		
		// Creating batch
		batch = new SpriteBatch();
		batch.getProjectionMatrix().setToOrtho2D(0, 0, 1191, 670);
		
		// Setting up stage and table
		stage = new Stage();
		upperTable = new Table();
		bottomTable = new Table();
		
		// Setting up tables' positions
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		float partialYpos = height - (height * 95f) / 100f;
		bottomTable.setBounds(0f, 0f, width, partialYpos); 

		health = new ProgressBar(0, 100, 1, false, skin);
		bottomTable.add(health);

		stage.addActor(upperTable);
		stage.addActor(bottomTable);
	}

	public void render(Player player) {
		health.setValue(player.getHP());
		stage.draw();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
