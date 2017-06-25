package it.unical.igpe.logic;

import com.badlogic.gdx.math.Vector2;

public abstract class AbstractDynamicObject extends AbstractStaticObject{
	public float angle;
	public String username;
	protected boolean alive;
	protected float HP;
	protected float speed;

	@Override
	public String toString() {
		return "Pos: " + boundingBox.x + " " + boundingBox.y + " " + boundingBox.width + " " + boundingBox.height;
	}

	public Vector2 getPos() {
		return new Vector2(boundingBox.x, boundingBox.y);
	}

	public void setPos(Vector2 pos) {
		this.boundingBox.x = (int) pos.x;
		this.boundingBox.y = (int) pos.y;
	}

	public boolean Alive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public float getHP() {
		return HP;
	}

	public void setHP(float hP) {
		HP = hP;
	}
}
