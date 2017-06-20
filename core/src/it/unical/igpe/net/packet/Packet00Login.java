package it.unical.igpe.net.packet;

import it.unical.igpe.net.GameClient;
import it.unical.igpe.net.GameServer;

public class Packet00Login extends Packet{
	
	private String username;
	private int x, y;

	public Packet00Login(byte[] data) {
		super(00);
		String[] dataArray = readData(data).split(","); 
		this.username = dataArray[0];
		this.x = Integer.parseInt(dataArray[1]);
		this.y = Integer.parseInt(dataArray[2]);
	}
	
	public Packet00Login(String username, int x, int y) {
		super(00);
		this.username = username;
		this.x = x;
		this.y = y;
	}

	@Override
	public void writeData(GameClient client) {
		client.sendData(getData());
	}

	@Override
	public void writeData(GameServer server, String username) {
		server.sendDataToAllClients(getData(), username);
	}

	@Override
	public byte[] getData() {
		return ("00" + this.username + "," + this.x + "," + this.y).getBytes();
	}

	public String getUsername() {
		return username;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

}
