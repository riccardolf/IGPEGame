package it.unical.igpe.GUI.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;

import it.unical.igpe.GUI.Assets;
import it.unical.igpe.MapUtils.World;
import it.unical.igpe.game.IGPEGame;
import it.unical.igpe.logic.Player;
import it.unical.igpe.net.PlayerMP;

public class HUD implements Disposable {
	private Stage stage;
	private SpriteBatch batch;
	private ProgressBar health;
	private ProgressBar skill;
	private Table table;
	private Texture pistol;
	private Texture shotgun;
	private Texture rifle;
	private BitmapFont font;

	/**
	 * @param isMP true if the game is multiplayer
	 */
	public HUD(boolean isMP) {
		// Loading textures
		pistol = new Texture(Gdx.files.internal("pistol.png"));
		rifle = new Texture(Gdx.files.internal("rifle.png"));
		shotgun = new Texture(Gdx.files.internal("shotgun.png"));
		font = IGPEGame.skinsoldier.getFont("text");
		font.setColor(IGPEGame.skinsoldier.getColor("sky-blue"));
		font.getData().setScale(0.8f);

		// Creating batch
		batch = new SpriteBatch();
		batch.getProjectionMatrix().setToOrtho2D(0, 0, 800, 800);

		// Setting up stage and table
		stage = new Stage();
		table = new Table();
		table.setDebug(false);

		// Setting up tables' positions
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		float partialYpos = height - (height * 95f) / 100f;
		table.setBounds(0f, 0f, width, partialYpos);

		health = new ProgressBar(0, 100, 1, false, IGPEGame.skinui);
		if (!isMP) {
			skill = new ProgressBar(0.0f, 1.0f, 0.1f, false, IGPEGame.skinui);
			table.add(skill).width(100);
			table.row().height(50);
		}
		table.add(health).width(200);

		stage.addActor(table);
	}

	public void render(Player player) {
		health.setValue(player.getHP());
		skill.setValue(player.slowMeter);
		batch.begin();
		if (player.activeWeapon.ID == "pistol")
			batch.draw(pistol, 10, 40, 64, 64);
		else if (player.activeWeapon.ID == "rifle")
			batch.draw(rifle, 10, 40, 64, 64);
		else if (player.activeWeapon.ID == "shotgun")
			batch.draw(shotgun, 10, 40, 64, 64);

		if (player.isReloading() && player.hasAmmo())
			font.draw(batch, "RELOADING", 15, 25);
		else if (!player.hasAmmo())
			font.draw(batch, "NO AMMO", 15, 25);
		else
			font.draw(batch, player.activeWeapon.actClip + " / " + player.activeWeapon.actAmmo, 15, 25);

		for (int i = 0; i < World.keyCollected; i++)
			batch.draw(Assets.manager.get(Assets.Key, Texture.class), 650 + i * 32, 5, 32, 32);
				
		batch.end();
		stage.draw();
	}

	@Override
	public void dispose() {
		stage.dispose();
		batch.dispose();
	}

	public void render(PlayerMP player) {
		health.setValue(player.getHP());
		batch.begin();
		if (player.activeWeapon.ID == "pistol")
			batch.draw(pistol, 10, 40, 64, 64);
		else if (player.activeWeapon.ID == "rifle")
			batch.draw(rifle, 10, 40, 64, 64);
		else if (player.activeWeapon.ID == "shotgun")
			batch.draw(shotgun, 10, 40, 64, 64);

		if (player.isReloading() && player.hasAmmo())
			font.draw(batch, "RELOADING", 15, 20);
		else if (!player.hasAmmo())
			font.draw(batch, "NO AMMO", 15, 20);
		else
			font.draw(batch, player.activeWeapon.actClip + " / " + player.activeWeapon.actAmmo, 15, 25);
		
		font.draw(batch, player.kills + " / " + player.deaths, 600, 600);

		batch.end();
		stage.draw();
	}

}
