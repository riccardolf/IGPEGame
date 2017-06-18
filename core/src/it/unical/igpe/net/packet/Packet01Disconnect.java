package it.unical.igpe.net.packet;

import it.unical.igpe.net.GameClient;
import it.unical.igpe.net.GameServer;

public class Packet01Disconnect extends Packet{

	public Packet01Disconnect(int packetID) {
		super(packetID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void writeData(GameClient client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeData(GameServer server) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getData() {
		// TODO Auto-generated method stub
		return null;
	}

}
