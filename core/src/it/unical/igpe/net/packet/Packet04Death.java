package it.unical.igpe.net.packet;

import it.unical.igpe.net.GameClient;
import it.unical.igpe.net.GameServer;

public class Packet04Death extends Packet{

	private String usernameKiller;
	private String usernameKilled;

	public Packet04Death(byte[] data) {
		super(04);
		String[] dataArray = readData(data).split(",");
		this.usernameKiller = dataArray[0];
		this.usernameKilled = dataArray[1];
	}
	
	public Packet04Death(String usernameKiller, String usernameKilled) {
		super(04);
		this.usernameKiller = usernameKiller;
		this.usernameKilled = usernameKilled;
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
		return ("04" + this.usernameKiller + "," + this.usernameKilled).getBytes();
	}

	public String getUsernameKiller() {
		return usernameKiller;
	}

	public String getUsernameKilled() {
		return usernameKilled;
	}

}
