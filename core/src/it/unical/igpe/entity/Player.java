package it.unical.igpe.entity;

import java.util.LinkedList;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.GameConfig;

public class Player extends AbstractGameObject implements Runnable {
	private Thread thread;
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
		this.thread = new Thread();
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
	
	public void updateBoundingBox() {
		this.boundingBox.x = pos.x;
		this.boundingBox.y = pos.y;				
	}
	
	public LinkedList<Bullet> getBullets() {
		return b;
	}

	@Override
	public void run() {
		thread.start();
	}
}
