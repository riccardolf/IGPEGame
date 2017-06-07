package it.unical.igpe.logic;

import java.util.Iterator;
import java.util.LinkedList;

import java.awt.Rectangle;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.game.World;
import it.unical.igpe.tools.GameConfig;

public class Player extends AbstractGameObject {
	private LinkedList<Bullet> b;
	private boolean reloading;
	private Weapon activeWeapon;
	private Weapon pistol;
	private Weapon rifle;
	private Weapon shotgun;
	private World world;
	public int keys;

	public Player(Vector2 _pos, World _world) {
		this.world = _world;
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
		this.keys = 0;
	}

	// TODO: FireRate per single Weapon
	public void fire() {
		if (!reloading) {
			world.addBullet(new Bullet(new Vector2(this.getPos().x + 32, this.getPos().y + 32), (float) Math.toRadians(this.angle + 90f), "player", activeWeapon.damage));
			this.activeWeapon.actClip--;
		}
		if (!reloading && activeWeapon.ID == "shotgun") {
			world.addBullet(new Bullet(new Vector2(this.getPos().x + 32, this.getPos().y + 32), (float) Math.toRadians(this.angle + 100f), "player",activeWeapon.damage));
			world.addBullet(new Bullet(new Vector2(this.getPos().x + 32, this.getPos().y + 32), (float) Math.toRadians(this.angle + 80f), "player",activeWeapon.damage));
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
		if(b.isEmpty())
			return null;
		LinkedList<Bullet> bls = new LinkedList<Bullet>();
		Iterator<Bullet> iter = b.iterator();
		while(iter.hasNext()) {
			bls.add(iter.next());
		}
		b.clear();
		return bls;
	}

	public void setBullets(LinkedList<Bullet> _b) {
		this.b = _b;
	}
}
