package it.unical.igpe.multiplayer;

import java.util.LinkedList;

import it.unical.igpe.logic.Bullet;
import it.unical.igpe.logic.Enemy;
import it.unical.igpe.logic.Player;

public class ServerMessage {
	// The different types of message sent by the Client to the server
	// MESSAGE an ordinary message
	// LOGOUT to disconnect from the Server
	static final int MESSAGE = 0, LOGOUT = 1;
	private int type;
	private LinkedList<Player> pls;
	private Bullet bulletFired;
	private LinkedList<Enemy> ens;
	private String msg;

	ServerMessage(int type, Bullet bulletFired, LinkedList<Player> pls, LinkedList<Enemy> ens) {
		this.type = type;
		this.pls = pls;
		this.bulletFired = bulletFired;
		this.ens = ens;
	}
	
	ServerMessage(int type, String msg) {
		this.msg = msg;
	}

	int getType() {
		return type;
	}
	
	String getMsg() {
		return msg;
	}

	LinkedList<Player> getPlayers() {
		return pls;
	}
	
	LinkedList<Enemy> getEnemies() {
		return ens;
	}
	
	Bullet getBullet() {
		return bulletFired;
	}

}
