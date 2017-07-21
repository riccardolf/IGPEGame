package it.unical.igpe.net.packet;

import it.unical.igpe.net.GameClient;
import it.unical.igpe.net.GameServer;

public class Packet05GameOver extends Packet {
	private String usernameWinner;
	private int killsWinner;

	public Packet05GameOver(byte[] data) {
		super(05);
		String[] dataArray = readData(data).split(",");
		this.usernameWinner = dataArray[0];
		this.killsWinner = Integer.parseInt(dataArray[1]);
	}
	
	public Packet05GameOver(String usernameWinner, int killsWinner) {
		super(05);
		this.usernameWinner = usernameWinner;
		this.killsWinner = killsWinner;
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
		return ("05" + this.usernameWinner + "," + this.killsWinner).getBytes();
	}

	public String getUsernameWinner() {
		return usernameWinner;
	}
	
	public int getKillsWinner() {
		return killsWinner;
	}

}
