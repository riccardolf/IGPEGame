package it.unical.igpe.net.packet;

import it.unical.igpe.net.GameClient;
import it.unical.igpe.net.GameServer;

public class Packet04Loot extends Packet {
	private int x;
	private int y;

	public Packet04Loot(byte[] data) {
		super(04);
		String[] dataArray = readData(data).split(",");
		this.x = Integer.parseInt(dataArray[0]);
		this.y = Integer.parseInt(dataArray[1]);
	}

	public Packet04Loot(int x, int y) {
		super(04);
		this.x = x;
		this.y = y;
	}

	@Override
	public void writeData(GameClient client) {
		client.sendData(getData());
	}

	@Override
	public void writeData(GameServer server) {
		server.sendDataToAllClients(getData());
	}

	@Override
	public byte[] getData() {
		return ("04" + this.x + "," + this.y).getBytes();
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

}
