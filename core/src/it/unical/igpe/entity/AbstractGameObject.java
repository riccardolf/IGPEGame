package it.unical.igpe.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.Collidable;

public abstract class AbstractGameObject implements Collidable {
	protected float angle;
	protected Vector2 pos;
	protected Rectangle boundingBox;
	protected String ID;
	protected boolean alive;
	protected float HP;
	protected float speed;
	
	public void MoveUp() {
		this.pos.y += speed;
	}
	public void MoveDown() {
		this.pos.y -= speed;
	}
	public void MoveRight() {
		this.pos.x += speed;
	}
	public void MoveLeft() {
		this.pos.x -= speed;
	}
	public void MoveUpLeft() {
		this.pos.y += speed * 0.7;
		this.pos.x -= speed * 0.7;
	}
	public void MoveUpRight() {
		this.pos.y += speed * 0.7;
		this.pos.x += speed * 0.7;
	}
	public void MoveDownLeft() {
		this.pos.y -= speed * 0.7;
		this.pos.x -= speed * 0.7;
	}
	public void MoveDownRight() {
		this.pos.y -= speed * 0.7;
		this.pos.x += speed * 0.7;
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
