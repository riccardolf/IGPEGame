package it.unical.igpe.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.GameConfig;

public class Bullet extends AbstractGameObject{
	
	public Bullet(Vector2 _pos, float _angle) {
		this.angle = _angle;
		this.pos = new Vector2(_pos);
		this.boundingBox = new Rectangle(pos.x, pos.y, 8, 8);
		this.ID = "bullet";
		this.alive = true;
		this.HP = 1f;
		this.speed = GameConfig.BULLETSPEED;
	}
	
	public void update() {
		pos.x += Math.cos(angle)*speed;
	    pos.y += Math.sin(angle)*speed;
	}
	
	public boolean check() {
		if(this.pos.x > 800 || this.pos.y > 600 || this.pos.x < 0 || this.pos.y < 0)
			return true;
		return false;
	}
}
