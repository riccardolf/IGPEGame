package it.unical.igpe.logic;

import java.awt.Rectangle;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.tools.GameConfig;
import it.unical.igpe.tools.Updatable;

public class Bullet extends AbstractGameObject implements Updatable {

	public Bullet(Vector2 _pos, float _angle) {
		this.angle = _angle;
		this.boundingBox = new Rectangle((int) _pos.x, (int) _pos.y, 8, 8);
		this.ID = "bullet";
		this.alive = true;
		this.HP = 1f;
		this.speed = GameConfig.BULLETSPEED;
	}

	@Override
	public boolean update() {
		boundingBox.x += Math.cos(angle) * speed;
		boundingBox.y += Math.sin(angle) * speed;
		return true;
	}

	@Override
	public String toString() {
		return "X: " + boundingBox.x + " Y:" + boundingBox.y;
	}
}
