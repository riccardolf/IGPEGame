package it.unical.igpe.logic;

import java.awt.Rectangle;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.tools.GameConfig;
import it.unical.igpe.tools.Updatable;

public class Bullet extends AbstractGameObject implements Updatable {

	public Bullet(Vector2 _pos, float _angle, String _ID, float dmg) {
		this.angle = _angle;
		this.boundingBox = new Rectangle((int) _pos.x, (int) _pos.y, 8, 8);
		this.ID = _ID;
		this.alive = true;
		this.HP = dmg;
		this.speed = GameConfig.BULLETSPEED;
	}

	@Override
	public void update(float delta) {
		boundingBox.x += Math.cos(angle) * speed;
		boundingBox.y += Math.sin(angle) * speed;
	}

	@Override
	public String toString() {
		return "X: " + this.boundingBox.x + " Y:" + this.boundingBox.y + " " + this.ID;
	}
}
