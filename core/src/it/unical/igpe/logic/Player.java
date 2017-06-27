package it.unical.igpe.logic;

import java.awt.Rectangle;

import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.MapUtils.World;
import it.unical.igpe.utils.GameConfig;

public class Player extends AbstractDynamicObject {
	public static final int STATE_IDLE = 0;
	public static final int STATE_RUNNING = 1;
	public static final int STATE_RELOADING = 2;
	public static final int STATE_SHOOTING = 3;
	public int state;
	protected boolean reloading;
	protected boolean shooting;
	public String username;
	public Weapon activeWeapon;
	public Weapon pistol;
	public Weapon rifle;
	public Weapon shotgun;
	public World world;
	public float slowMeter;
	public boolean slowActive;

	public Player(Vector2 _pos, World _world, String username) {
		this.world = _world;
		this.boundingBox = new Rectangle((int) _pos.x, (int) _pos.y, 48, 48);
		this.reloading = false;
		this.ID = "player";
		this.alive = true;
		this.HP = 100f;
		this.speed = GameConfig.MOVESPEED;
		this.pistol = new Weapon();
		this.rifle = new Weapon();
		this.shotgun = new Weapon();
		this.pistol.createPistol();
		this.rifle.createRifle();
		this.shotgun.createShotgun();
		this.activeWeapon = pistol;
		this.username = username;
	}

	public void fire() {
		if (!reloading) {
			world.addBullet(new Bullet(this.shotPosition(), (float) Math.toRadians(this.angle + 90f), "player",
					activeWeapon.damage));
			this.activeWeapon.lastFired = 0f;
			this.activeWeapon.actClip--;
		}
		if (!reloading && activeWeapon.ID == "shotgun") {
			world.addBullet(new Bullet(this.shotPosition(), (float) Math.toRadians(this.angle + 100f), "player",
					activeWeapon.damage));
			world.addBullet(new Bullet(this.shotPosition(), (float) Math.toRadians(this.angle + 80f), "player",
					activeWeapon.damage));
		}
	}

	public void reload() {
		if (!(activeWeapon.actClip == activeWeapon.sizeClip)) {
			activeWeapon.reloadAct = 0;
			reloading = true;
		}
	}

	public boolean isReloading(float delta) {
		if (activeWeapon.reloadAct >= activeWeapon.reloadTime) {
			reloading = false;
		} else if (activeWeapon.reloadAct < activeWeapon.reloadTime) {
			activeWeapon.reloadAct += delta;
			reloading = true;
			activeWeapon.reload();
		}
		return reloading;
	}

	public boolean checkAmmo() {
		return activeWeapon.actClip == 0;
	}

	public void setActWeapon(String ID) {
		if (ID == "pistol")
			activeWeapon = pistol;
		else if (ID == "shotgun")
			activeWeapon = shotgun;
		else if (ID == "rifle")
			activeWeapon = rifle;
	}

	public boolean canReload() {
		if (this.activeWeapon.actClip < this.activeWeapon.sizeClip && this.activeWeapon.actAmmo > 0)
			return true;
		return false;
	}

	public boolean canShoot() {
		if (this.activeWeapon.lastFired >= this.activeWeapon.fireRate && this.activeWeapon.actClip > 0 && !this.isReloading())
			return true;
		return false;
	}

	public boolean isShooting(float delta) {
		activeWeapon.lastFired += delta;
		if (activeWeapon.lastFired >= activeWeapon.fireRate / 2) {
			shooting = false;
		}
		else
			shooting = true;
		return shooting;
	}
	
	public boolean isSlowMo(float delta) {
		if(slowActive && slowMeter > 0f)
			slowMeter -= delta;
		else if(!slowActive && slowMeter <= 1f)
			slowMeter += delta / 4;
		else if(slowMeter <= 0f)
			slowActive = false;
		return slowActive;
	}

	public Vector2 shotPosition() {
		float x2 = (float) (16 * Math.cos(Math.toRadians(this.angle)) - 16 * Math.sin(Math.toRadians(this.angle)));
		float y2 = (float) (16 * Math.sin(Math.toRadians(this.angle)) + 16 * Math.cos(Math.toRadians(this.angle)));
		return new Vector2(this.getPos().x + 32 + x2, this.getPos().y + 32 + y2);
	}
	
	public float getSkillCharge() {
		return 100;
	}

	public boolean isReloading() {
		return reloading;
	}
	
	public boolean isShooting() {
		return shooting;
	}

	public String getActWeapon() {
		return activeWeapon.ID;
	}

	public void setReloading(boolean bool) {
		this.reloading = bool;
	}

	public String getUsername() {
		return this.username;
	}

	public void hit(float dmg) {
		this.HP -= dmg;
	}

	public boolean hasAmmo() {
		if(activeWeapon.actAmmo == 0 && activeWeapon.actClip == 0)
			return false;
		return true;
	}

}
