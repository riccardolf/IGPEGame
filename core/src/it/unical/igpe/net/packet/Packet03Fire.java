package it.unical.igpe.net.packet;

import it.unical.igpe.net.GameClient;
import it.unical.igpe.net.GameServer;

public class Packet03Fire extends Packet{
	
	private String username;
	private int x, y;
	private float angle;

	public Packet03Fire(byte[] data) {
		super(03);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		this.x = Integer.parseInt(dataArray[1]);
		this.y = Integer.parseInt(dataArray[2]);
		this.angle = Float.parseFloat(dataArray[3]);
	}
	
	public Packet03Fire(String username, int x, int y, float angle) {
		super(03);
		this.username = username;
		this.x = x;
		this.y = y;
		this.angle = angle;
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
		return ("03" + this.username + "," + this.x + "," + this.y + "," + this.angle).getBytes();
	}
	
	public String getUsername() {
		return username;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public float getAngle() {
		return this.angle;
	}

}
