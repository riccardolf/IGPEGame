package it.unical.igpe.logic;

import java.awt.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractGameObject {
	public float angle;
	public String username;
	protected Rectangle boundingBox;
	protected String ID;
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

	public Rectangle getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(Rectangle boundingBox) {
		this.boundingBox = boundingBox;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
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
