package it.unical.igpe.multiplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class MultiplayerWorldRenderer {
	private MultiplayerWorld world;
	public MultiplayerWorldRenderer(MultiplayerWorld world) {
		this.world = world;
	}
	
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}
