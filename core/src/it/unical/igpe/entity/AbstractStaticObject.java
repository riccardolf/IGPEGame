package it.unical.igpe.entity;

import java.awt.Rectangle;

import com.badlogic.gdx.math.Vector2;

public abstract class AbstractStaticObject {
	protected Vector2 pos;
	protected Rectangle boundingBox;

	@Override
	public String toString() {
		return this.boundingBox.toString();
	}

	public Vector2 getPos() {
		return pos;
	}

	public void setPos(Vector2 pos) {
		this.pos = pos;
	}

	public Rectangle getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(Rectangle boundingBox) {
		this.boundingBox = boundingBox;
	}
}
