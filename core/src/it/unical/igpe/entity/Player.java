package it.unical.igpe.entity;

import java.util.LinkedList;

import java.awt.Rectangle;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.GameConfig;

public class Player extends AbstractGameObject {
	private LinkedList<Bullet> b;
	public boolean isReloading;
	
	public Player(Vector2 _pos) {
		this.pos = _pos;
		this.boundingBox = new Rectangle((int)pos.x, (int)pos.y, 64, 64);
		this.ID = "player";
		this.alive = true;
		this.HP = 100f;
		this.speed = GameConfig.MOVESPEED;
		this.angle = 0f;
		this.b = new LinkedList<Bullet>();
		this.isReloading = false;
	}

	public void fire(float angle) {
		Bullet bullet = new Bullet(pos, (float)Math.toRadians(angle));
		b.add(bullet);
	}
	
	public void hit(float dmg) {
		this.HP -= dmg;
	}
	
	public void tick() {
		for (Bullet bullet : b) {
			bullet.update();
		}
	}
	
	public void updateBoundingBox() {
		this.boundingBox.x = (int) pos.x;
		this.boundingBox.y = (int) pos.y;				
	}
	
	public LinkedList<Bullet> getBullets() {
		return b;
	}
	
	public void setBullets(LinkedList<Bullet> _b) {
		this.b = _b;
	}
}
