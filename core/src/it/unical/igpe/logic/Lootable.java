package it.unical.igpe.logic;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.tools.LootableType;

public class Lootable {
	private Rectangle boundingBox;
	private LootableType type;
	private float HP;
	private boolean looted;
	
	public Lootable(Vector2 v, LootableType t) {
		this.boundingBox = new Rectangle((int) v.x, (int) v.y, 32, 32);
		this.type = t;
		this.looted = false;
	}

	@Override
	public String toString() {
		return type + " " +this.boundingBox.toString();
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

	public LootableType getType() {
		return this.type;
	}

	public float getHP() {
		return HP;
	}

	public void setHP(float hP) {
		HP = hP;
	}
	
	public boolean wasLooted() {
		return looted;
	}
}
