package it.unical.igpe.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends AbstractGameObject {
	public Enemy(Vector2 pos) {
		pos = new Vector2(pos);
		boundingBox = new Rectangle(pos.x, pos.y, 64, 64);
		ID = "enemy";
		alive = true;
		HP = 100f;
	}
}
