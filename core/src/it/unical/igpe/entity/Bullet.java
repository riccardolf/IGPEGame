package it.unical.igpe.entity;

import java.awt.Rectangle;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.GameConfig;

public class Bullet extends AbstractGameObject{
	
	public Bullet(Vector2 _pos, float _angle) {
		this.angle = _angle;
		// FIXME: Bullet's starting position
//		pos.x = (float) (_pos.x * Math.cos(angle) - _pos.y * Math.sin(angle));
//		pos.y =  (float) (_pos.x * Math.sin(angle) + _pos.y * Math.cos(angle));
//		System.out.println("Player X: " + _pos.x + " Y: " + _pos.y);
//		System.out.println("Bullet X: " + pos.x + " Y: " + pos.y);
		this.boundingBox = new Rectangle((int)_pos.x,(int) _pos.y, 8, 8);
		this.ID = "bullet";
		this.alive = true;
		this.HP = 1f;
		this.speed = GameConfig.BULLETSPEED;
	}
	
	public void update() {
		this.toString();
		boundingBox.x += Math.cos(angle)*speed;
		boundingBox.y += Math.sin(angle)*speed;
	}
	@Override
	public String toString() {
		return "X: " + boundingBox.x + " Y:" + boundingBox.y;
	}
}
