package it.unical.igpe.entity;

import java.util.LinkedList;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.GameConfig;

public class Player extends AbstractGameObject {
	private LinkedList<Bullet> b;
	
	public Player(Vector2 _pos) {
		this.pos = _pos;
		this.boundingBox = new Rectangle(pos.x, pos.y, 64, 64);
		this.ID = "player";
		this.alive = true;
		this.HP = 100f;
		this.speed = GameConfig.MOVESPEED;
		this.angle = 0f;
		this.b = new LinkedList<Bullet>();
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
			if(bullet.check())
				b.remove(bullet);
		}
	}
	
	public LinkedList<Bullet> getBullets() {
		return b;
	}
}
