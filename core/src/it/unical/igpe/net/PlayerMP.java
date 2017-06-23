package it.unical.igpe.net;

import java.net.InetAddress;

import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.logic.Player;

public class PlayerMP extends Player {
	
	public MultiplayerWorld world;
	public InetAddress ipAddress;
	public int port;

	public PlayerMP(Vector2 _pos, MultiplayerWorld _world, String username, InetAddress ipAddress, int port) {
		super(_pos, null, username);
		this.world = _world;
		this.activeWeapon = pistol;
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
}
