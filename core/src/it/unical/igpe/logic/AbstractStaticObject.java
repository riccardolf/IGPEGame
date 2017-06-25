package it.unical.igpe.logic;

import java.awt.Rectangle;

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
}
