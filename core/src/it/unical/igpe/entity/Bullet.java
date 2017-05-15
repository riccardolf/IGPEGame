package it.unical.igpe.entity;

import java.awt.Rectangle;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.GameConfig;

public class Bullet extends AbstractGameObject{
	
	public Bullet(Vector2 _pos, float _angle) {
		this.angle = _angle;
		this.pos = new Vector2(_pos);
		this.pos.x += 32;
		this.pos.y += 32;
		this.boundingBox = new Rectangle((int)pos.x, (int)pos.y, 8, 8);
		this.ID = "bullet";
		this.alive = true;
		this.HP = 1f;
		this.speed = GameConfig.BULLETSPEED;
	}
	
	public void update() {
		pos.x += Math.cos(angle)*speed;
	    pos.y += Math.sin(angle)*speed;
	}
	@Override
	public String toString() {
		return "X: " + pos.x + " Y:" + pos.y;
	}
}
