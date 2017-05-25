package it.unical.igpe.logic;

import java.util.LinkedList;

import java.awt.Rectangle;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.tools.GameConfig;

public class Player extends AbstractGameObject {
	private LinkedList<Bullet> b;
	private boolean reloading;
	private Weapon activeWeapon;
	private Weapon pistol;
	private Weapon rifle;
	private Weapon shotgun;

	public Player(Vector2 _pos) {
		this.boundingBox = new Rectangle((int) _pos.x, (int) _pos.y, 48, 48);
		this.reloading = false;
		this.ID = "player";
		this.alive = true;
		this.HP = 100f;
		this.speed = GameConfig.MOVESPEED;
		this.angle = 0f;
		this.b = new LinkedList<Bullet>();
		this.pistol = new Weapon();
		this.rifle = new Weapon();
		this.shotgun = new Weapon();
		this.pistol.createPistol();
		this.rifle.createRifle();
		this.shotgun.createShotgun();
		this.activeWeapon = pistol;
	}

	// TODO: FireRate per single Weapon
	public void fire(float x, float y, float angle) {
		if (!reloading) {
			b.add(new Bullet(new Vector2(x, y), (float) Math.toRadians(angle)));
			this.activeWeapon.actClip--;
		}
	}

	public void reload() {
		if (!(activeWeapon.actClip == activeWeapon.sizeClip)) {
			activeWeapon.reloadAct = 0;
			reloading = true;
		}
	}

	public void hit(float dmg) {
		this.HP -= dmg;
	}

	public boolean isReloading(float delta) {
		if (activeWeapon.reloadAct > activeWeapon.reloadTime) {
			reloading = false;
		} else if (activeWeapon.reloadAct < activeWeapon.reloadTime) {
			activeWeapon.reloadAct += delta;
			reloading = true;
			activeWeapon.actClip = activeWeapon.sizeClip;
		}
		return reloading;
	}

	public void checkAmmo() {
		if (activeWeapon.actClip == 0)
			this.reload();
	}

	public String getActWeapon() {
		return activeWeapon.ID;
	}

	public void setActWeapon(String ID) {
		System.out.println(ID);
		if (ID == "pistol")
			activeWeapon = pistol;
		else if (ID == "shotgun")
			activeWeapon = shotgun;
		else if (ID == "rifle")
			activeWeapon = rifle;
	}

	public boolean getReloading() {
		return this.reloading;
	}

	public void setReloading(boolean bool) {
		this.reloading = bool;
	}

	public LinkedList<Bullet> getBullets() {
		return b;
	}

	public void setBullets(LinkedList<Bullet> _b) {
		this.b = _b;
	}


}
