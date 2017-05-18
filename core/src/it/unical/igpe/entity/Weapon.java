package it.unical.igpe.entity;

import it.unical.igpe.GameConfig;

public class Weapon {
	protected int sizeClip;
	protected int actClip;
	protected int actAmmo;
	protected int maxAmmo;
	protected int damage;
	protected int bulletSpeed;
	protected float reloadAct;
	protected float reloadTime;
	protected String ID;
	
	public void reload() {
		while(actClip <= sizeClip) {
			actClip++;
			actAmmo--;
		}
	}
	
	public void createPistol() {
		sizeClip = 10;
		actClip = 10;
		actAmmo = 100;
		maxAmmo = 100;
		reloadAct = 0;
		reloadTime = 1;
		damage = 15;
		bulletSpeed = GameConfig.BULLETSPEED;
		ID = "pistol";
	}
	
	public void createRifle() {
		sizeClip = 5;
		actClip = 5;
		actAmmo = 20;
		maxAmmo = 50;
		reloadAct = 0;
		reloadTime = 1;
		damage = 55;
		bulletSpeed = GameConfig.BULLETSPEED;
		ID = "rifle";
	}
	public void createShotgun() {
		sizeClip = 10;
		actClip = 10;
		actAmmo = 25;
		maxAmmo = 50;
		reloadAct = 0;
		reloadTime = 1;
		damage = 34;
		bulletSpeed = GameConfig.BULLETSPEED;
		ID = "shotgun";
	}	
}
