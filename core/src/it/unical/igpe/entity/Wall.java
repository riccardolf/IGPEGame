package it.unical.igpe.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Wall extends AbstractStaticObject{
	public Wall(Vector2 _pos) {
		this.pos = _pos;
		this.boundingBox = new Rectangle(pos.x, pos.y, 64, 64);
	}
}
