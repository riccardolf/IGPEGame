package it.unical.igpe.net.packet;

import it.unical.igpe.net.GameClient;
import it.unical.igpe.net.GameServer;

public class Packet05GameOver extends Packet {
	private String usernameWinner;

	public Packet05GameOver(byte[] data) {
		super(05);
		String[] dataArray = readData(data).split(",");
		this.usernameWinner = dataArray[0];
	}
	
	public Packet05GameOver(String usernameWinner) {
		super(05);
		this.usernameWinner = usernameWinner;
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
		return ("05" + this.usernameWinner).getBytes();
	}

	public String getUsernameWinner() {
		return usernameWinner;
	}

}
