package it.unical.igpe.logic;

import java.awt.Rectangle;

import com.badlogic.gdx.math.Vector2;

public class Wall extends AbstractStaticObject{
	public Wall(Vector2 _pos) {
		this.boundingBox = new Rectangle((int) _pos.x,(int) _pos.y, 64, 64);
	}
}
