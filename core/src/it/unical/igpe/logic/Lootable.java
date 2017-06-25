package it.unical.igpe.logic;

import java.awt.Rectangle;

import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.utils.LootableType;

public class Lootable extends AbstractStaticObject {
	private LootableType type;
	private float HP;
	public boolean closed;

	public Lootable(Vector2 v, LootableType t) {
		this.boundingBox = new Rectangle((int) v.x, (int) v.y, 32, 32);
		this.type = t;
		this.closed = false;
	}

	@Override
	public String toString() {
		return type + " " + this.boundingBox.toString();
	}

	public Vector2 getPos() {
		return new Vector2(this.boundingBox.x, this.boundingBox.y);
	}

	public void setPos(Vector2 _pos) {
		this.boundingBox.x = (int) _pos.x;
		this.boundingBox.y = (int) _pos.y;
	}

	public LootableType getType() {
		return this.type;
	}

	public float getHP() {
		return HP;
	}

	public void setHP(float hP) {
		HP = hP;
	}

}
