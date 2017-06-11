package it.unical.igpe.logic;

import it.unical.igpe.tools.GameConfig;

public class Weapon {
	public float lastFired;
	public float fireRate;
	protected int sizeClip;
	public int actClip;
	public int actAmmo;
	protected int maxAmmo;
	protected int damage;
	protected int bulletSpeed;
	public float reloadAct;
	public float reloadTime;
	public String ID;
	
	public void reload() {
		while(actClip <= sizeClip) {
			actClip++;
			actAmmo--;
		}
	}
	
	public void createPistol() {
		fireRate = 0.1f;
		sizeClip = 10;
		actClip = 10;
		actAmmo = 100;
		maxAmmo = 100;
		reloadTime = 1;
		reloadAct = reloadTime;
		damage = 15;
		bulletSpeed = GameConfig.BULLETSPEED;
		ID = "pistol";
	}
	
	public void createRifle() {
		fireRate = 0.4f;
		sizeClip = 1;
		actClip = 1;
		actAmmo = 20;
		maxAmmo = 50;
		reloadTime = 1;
		reloadAct = reloadTime;
		damage = 50;
		bulletSpeed = GameConfig.BULLETSPEED;
		ID = "rifle";
	}
	public void createShotgun() {
		fireRate = 0.5f;
		sizeClip = 3;
		actClip = 3;
		actAmmo = 25;
		maxAmmo = 50;
		reloadTime = 0.6f;
		reloadAct = reloadTime;
		damage = 34;
		bulletSpeed = GameConfig.BULLETSPEED;
		ID = "shotgun";
	}	
}
