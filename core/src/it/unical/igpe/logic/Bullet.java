package it.unical.igpe.logic;

import java.awt.Rectangle;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.utils.GameConfig;
import it.unical.igpe.utils.Updatable;

public class Bullet extends AbstractDynamicObject implements Updatable {

	public Bullet(Vector2 _pos, float _angle, String _ID, float dmg) {
		this.angle = _angle;
		this.boundingBox = new Rectangle((int) _pos.x, (int) _pos.y, 8, 8);
		this.ID = _ID;
		this.alive = true;
		this.HP = dmg;
	}

	@Override
	public void update(float delta) {
		boundingBox.x += Math.cos(angle) * GameConfig.BULLETSPEED * delta;
		boundingBox.y += Math.sin(angle) * GameConfig.BULLETSPEED * delta;
	}

	@Override
	public String toString() {
		return "X: " + this.boundingBox.x + " Y:" + this.boundingBox.y + " " + this.ID;
	}
}
