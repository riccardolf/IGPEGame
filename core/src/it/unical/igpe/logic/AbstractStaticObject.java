package it.unical.igpe.logic;

import java.awt.Rectangle;

import com.badlogic.gdx.math.Vector2;

public abstract class AbstractStaticObject {
	protected Rectangle boundingBox;

	@Override
	public String toString() {
		return this.boundingBox.toString();
	}

	public Vector2 getPos() {
		return new Vector2(this.boundingBox.x, this.boundingBox.y);
	}

	public void setPos(Vector2 _pos) {
		this.boundingBox.x = (int) _pos.x;
		this.boundingBox.y = (int) _pos.y;
	}

	public Rectangle getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(Rectangle boundingBox) {
		this.boundingBox = boundingBox;
	}
}
