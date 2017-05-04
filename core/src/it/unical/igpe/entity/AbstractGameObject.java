package it.unical.igpe.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.GameConfig;

public abstract class AbstractGameObject extends Thread {
	protected Vector2 pos;
	protected Rectangle boundingBox;
	protected String ID;
	protected boolean alive;
	protected float HP;
	
	public void MoveUp() {
		this.pos.y += GameConfig.MOVESPEED;
	}
	public void MoveDown() {
		this.pos.y -= GameConfig.MOVESPEED;
	}
	public void MoveRight() {
		this.pos.x += GameConfig.MOVESPEED;
	}
	public void MoveLeft() {
		this.pos.x -= GameConfig.MOVESPEED;
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
