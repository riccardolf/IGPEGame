package it.unical.igpe.entity;

import java.awt.Rectangle;

import com.badlogic.gdx.math.Vector2;

public class Wall extends AbstractStaticObject{
	public Wall(Vector2 _pos) {
		this.pos = _pos;
		this.boundingBox = new Rectangle((int) pos.x,(int) pos.y, 64, 64);
	}
}
