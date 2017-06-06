package it.unical.igpe.logic;

import com.badlogic.gdx.math.Rectangle;

public abstract class AbstractStaticObject {
	protected Rectangle boundingBox;
	protected String ID;
	protected float HP;
	
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
	public float getHP() {
		return HP;
	}
	public void setHP(float hP) {
		HP = hP;
	}
}
