package it.unical.igpe.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;


public class Bullet extends AbstractGameObject {
	public Bullet(Vector2 pos) {
		pos = new Vector2(pos);
		boundingBox = new Rectangle(pos.x, pos.y, 8, 8);
		ID = "bullet";
		alive = true;
		HP = 1f;
	}
	
	public void fire(Vector3 dir, float angle) {
		
	}
	
}
