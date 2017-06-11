package it.unical.igpe.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;

import it.unical.igpe.game.IGPEGame;
import it.unical.igpe.logic.Player;
import it.unical.igpe.tools.Assets;

public class HUD implements Disposable {
	private Stage stage;
	private SpriteBatch batch;
	private ProgressBar health;
	private Table table;
	private Texture pistol;
	private Texture shotgun;
	private Texture rifle;
	private BitmapFont font;

	public HUD() {
		// Loading textures
		pistol = new Texture(Gdx.files.internal("pistol.png"));
		rifle = new Texture(Gdx.files.internal("rifle.png"));
		shotgun = new Texture(Gdx.files.internal("shotgun.png"));
		font = new BitmapFont();
		font.setColor(Color.BLACK);

		// Creating batch
		batch = new SpriteBatch();
		batch.getProjectionMatrix().setToOrtho2D(0, 0, 800, 800);

		// Setting up stage and table
		stage = new Stage();
		table = new Table();

		// Setting up tables' positions
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		float partialYpos = height - (height * 95f) / 100f;
		table.setBounds(0f, 0f, width, partialYpos);

		health = new ProgressBar(0, 100, 1, false, IGPEGame.skinui);
		table.add(health);

		stage.addActor(table);
	}

	public void render(Player player) {
		health.setValue(player.getHP());
		batch.begin();
		if (player.activeWeapon.ID == "pistol")
			batch.draw(pistol, 10, 40, 64, 64);
		else if (player.activeWeapon.ID == "rifle")
			batch.draw(rifle, 10, 40, 64, 64);
		else if (player.activeWeapon.ID == "shotgun")
			batch.draw(shotgun, 10, 40, 64, 64);
		if (player.isReloading())
			font.draw(batch, "RELOADING", 5, 15);
		else
			for (int i = 0; i < player.activeWeapon.actClip; i++)
				batch.draw(Assets.Bullet, i * 10, 5, 8, 8, 32, 32, 1f, 1f, 0);
		
		for(int i = 0; i < player.keys; i++)
			batch.draw(Assets.manager.get(Assets.Key, Texture.class), 650 + i * 32 , 5, 32, 32);
		batch.end();
		stage.draw();
	}

	@Override
	public void dispose() {
		stage.dispose();
		batch.dispose();
	}

}
