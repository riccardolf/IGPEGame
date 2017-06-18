package it.unical.igpe.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.net.packet.Packet;
import it.unical.igpe.net.packet.Packet.PacketTypes;
import it.unical.igpe.net.packet.Packet00Login;
import it.unical.igpe.net.packet.Packet02Move;

public class GameServer extends Thread {
	private DatagramSocket socket;
	private List<PlayerMP> connectedPlayer = new ArrayList<PlayerMP>();

	public GameServer() {
		try {
			this.socket = new DatagramSocket(1234);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());

			String message = new String(packet.getData());
			System.out.println("CLIENT >" + message);
			if (message.trim().equalsIgnoreCase("ping")) {
				sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
			}
		}
	}

	private void parsePacket(byte[] data, InetAddress address, int port) {
		String message = new String(data).trim();
		PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
		Packet packet = null;
		switch (type) {
		default:
		case INVALID:
			break;
		case LOGIN:
			packet = new Packet00Login(data);
			System.out.println("[" + address.getHostAddress() + ":" + port + "]"
					+ ((Packet00Login) packet).getUsername() + "has connected");
			PlayerMP player = new PlayerMP(new Vector2(), null, ((Packet00Login) packet).getUsername(), address, port);
			this.addConnection(player, (Packet00Login) packet);
			break;
		case DISCONNECT:
			break;
		case MOVE:
			packet = new Packet02Move(data);
			this.handleMove((Packet02Move) packet);
			break;
		}
	}

	private void handleMove(Packet02Move packet) {
		if(getPlayerMP(packet.getUsername()) != null) {
			int index = getPlayerMPIndex(packet.getUsername());
			this.connectedPlayer.get(index).getBoundingBox().x = packet.getX();
			this.connectedPlayer.get(index).getBoundingBox().y = packet.getY();
			packet.writeData(this);
		}
	}

	private int getPlayerMPIndex(String username) {
		// TODO Auto-generated method stub
		return 0;
	}

	private Object getPlayerMP(String username) {
		return null;
	}

	public void addConnection(PlayerMP player, Packet00Login packet) {
		boolean alreadyConnected = false;
		for (PlayerMP p : this.connectedPlayer) {
			if (player.getUsername().equalsIgnoreCase(p.getUsername())) {
				if (p.ipAddress == null)
					p.ipAddress = player.ipAddress;
				if (p.port == -1)
					p.port = player.port;
				alreadyConnected = true;
			} else {
				sendData(packet.getData(), p.ipAddress, p.port);
				packet = new Packet00Login(p.getUsername(), p.getBoundingBox().x, p.getBoundingBox().y);
				sendData(packet.getData(), player.ipAddress, player.port);
			}
		}
		if (!alreadyConnected) {
			this.connectedPlayer.add(player);
		}

	}

	public void sendData(byte[] data, InetAddress ipAddress, int port) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendDataToAllClients(byte[] data) {
		for (PlayerMP p : connectedPlayer) {
			sendData(data, p.ipAddress, p.port);
		}
	}
}
