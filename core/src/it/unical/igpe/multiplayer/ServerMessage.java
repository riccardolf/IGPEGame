package it.unical.igpe.multiplayer;

import java.io.Serializable;

import it.unical.igpe.logic.Bullet;
import it.unical.igpe.logic.Enemy;
import it.unical.igpe.logic.Player;

public class ServerMessage implements Serializable{
	private static final long serialVersionUID = -1964720133756399344L;
	
	public static final int LOGOUT = 0, PLAYER_MOVED = 1, PLAYER_DEATH = 2, BULLET_FIRED = 3, ENEMY_MOVED = 4, ENEMY_DEATH = 5;
	public int type;
	public Player playerdata;
	public Bullet bulletFired;
	public Enemy enemydata;

	ServerMessage(int type) {
		this.type = type;
	}

}
