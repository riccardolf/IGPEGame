package it.unical.igpe.logic;

import java.awt.Rectangle;

import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.tools.TileType;

public class Tile {
	private Rectangle boundingBox;
	private TileType type;

	public Tile(Vector2 v, TileType t) {
		this.boundingBox = new Rectangle((int) v.x, (int) v.y, 64, 64);
		this.type = t;
	}

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

	public TileType getType() {
		return this.type;
	}
}
