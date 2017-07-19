package it.unical.igpe.logic;

import java.awt.Rectangle;

import com.badlogic.gdx.math.Vector2;

public abstract class AbstractStaticObject {
	protected Rectangle boundingBox;
	protected String ID;
	
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
	
	public int getX() {
		return boundingBox.x;
	}
	
	public int getY() {
		return boundingBox.y;
	}
	
	public void setX(int x) {
		this.boundingBox.x = x;
	}
	
	public void setY(int y) {
		this.boundingBox.y = y;
	}
	
	public Vector2 getPos() {
		return new Vector2(boundingBox.x, boundingBox.y);
	}

	public void setPos(Vector2 pos) {
		this.boundingBox.x = (int) pos.x * 64;
		this.boundingBox.y = (int) pos.y * 64;
	}
}
