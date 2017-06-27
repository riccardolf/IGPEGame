package it.unical.igpe.logic;

public abstract class AbstractDynamicObject extends AbstractStaticObject{
	public float angle;
	public String username;
	protected boolean alive;
	protected float HP;
	protected float speed;

	@Override
	public String toString() {
		return "Pos: " + this.getX() + " " + this.getY() + " " + boundingBox.width + " " + boundingBox.height;
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
