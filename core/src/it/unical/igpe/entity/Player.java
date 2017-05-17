package it.unical.igpe.entity;

import java.util.LinkedList;

import java.awt.Rectangle;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.GameConfig;

public class Player extends AbstractGameObject {
	private LinkedList<Bullet> b;
	private boolean reloading;
	private float reloadTime;
	private float reloadAct;

	public Player(Vector2 _pos) {
		this.boundingBox = new Rectangle((int) _pos.x, (int) _pos.y, 64, 64);
		this.ID = "player";
		this.alive = true;
		this.HP = 100f;
		this.speed = GameConfig.MOVESPEED;
		this.angle = 0f;
		this.b = new LinkedList<Bullet>();
		this.reloading = false;
		this.reloadTime = 1;
		this.reloadAct = 0;
	}

	public void fire(float angle) {
		if (!reloading)
			b.add(new Bullet(new Vector2(boundingBox.x, boundingBox.y), (float) Math.toRadians(angle)));
	}

	public void reload() {
		reloadAct = 0;
	}

	public void hit(float dmg) {
		this.HP -= dmg;
	}

	public void tick() {
		for (Bullet bullet : b) {
			bullet.update();
		}
	}

	public LinkedList<Bullet> getBullets() {
		return b;
	}

	public void setBullets(LinkedList<Bullet> _b) {
		this.b = _b;
	}

	public boolean isReloading(float delta) {
		if (reloadAct > reloadTime) {
			reloading = false;
		} else if (reloadAct < reloadTime) {
			reloadAct += delta;
			reloading = true;
		}

		return reloading;
	}
}
