package it.unical.igpe.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player extends AbstractGameObject {
	public Player(Vector2 _pos) {
		pos = _pos;
		boundingBox = new Rectangle(pos.x, pos.y, 64, 64);
		ID = "player";
		alive = true;
		HP = 100f;
	}
}
