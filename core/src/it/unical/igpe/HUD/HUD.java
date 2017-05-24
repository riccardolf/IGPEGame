package it.unical.igpe.HUD;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;

public class HUD implements Disposable {
	
	public Stage stage;
	private Label bulletsCount;
	
	public HUD() {
		stage = new Stage();
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		
		bulletsCount = new Label("Ammo: ", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
		table.add(bulletsCount);
		
		stage.addActor(table);
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
