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
import it.unical.igpe.net.packet.Packet01Disconnect;
import it.unical.igpe.net.packet.Packet02Move;
import it.unical.igpe.net.packet.Packet03Fire;

public class GameServer extends Thread {
	private DatagramSocket socket;
	private List<PlayerMP> connectedPlayers = new ArrayList<PlayerMP>();

	public GameServer(int port) {
		try {
			this.socket = new DatagramSocket(port);
			System.out.println("Creating Server...");
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
					+ ((Packet00Login) packet).getUsername() + " has connected");
			PlayerMP player = new PlayerMP(new Vector2(), null, ((Packet00Login) packet).getUsername(), address, port);
			this.addConnection(player, (Packet00Login) packet);
			break;
		case DISCONNECT:
			packet = new Packet01Disconnect(data);
			System.out.println("[" + address.getHostAddress() + ":" + port + "] "
					+ ((Packet01Disconnect) packet).getUsername() + " has left...");
			this.removeConnection((Packet01Disconnect) packet);
			break;
		case MOVE:
			packet = new Packet02Move(data);
			this.handleMove((Packet02Move) packet);
			break;
		case FIRE:
			packet = new Packet03Fire(data);
			handleFire((Packet03Fire) packet);
			break;
		}
	}

	private void handleFire(Packet03Fire packet) {
		if (getPlayerMP(packet.getUsername()) != null) {
			int index = getPlayerMPIndex(packet.getUsername());
			this.connectedPlayers.get(index).getBoundingBox().x = packet.getX();
			this.connectedPlayers.get(index).getBoundingBox().y = packet.getY();
			this.connectedPlayers.get(index).angle = packet.getAngle();
			packet.writeData(this, packet.getUsername());
		}
	}

	private void removeConnection(Packet01Disconnect packet) {
		this.connectedPlayers.remove(getPlayerMPIndex(packet.getUsername()));
		packet.writeData(this, packet.getUsername());
	}

	private void handleMove(Packet02Move packet) {
		if (getPlayerMP(packet.getUsername()) != null) {
			int index = getPlayerMPIndex(packet.getUsername());
			this.connectedPlayers.get(index).getBoundingBox().x = packet.getX();
			this.connectedPlayers.get(index).getBoundingBox().y = packet.getY();
			this.connectedPlayers.get(index).angle = packet.getAngle();
			this.connectedPlayers.get(index).state = packet.getState();
			this.connectedPlayers.get(index).activeWeapon.ID = packet.getActWeapon();
			packet.writeData(this, packet.getUsername());
		}
	}

	public void addConnection(PlayerMP player, Packet00Login packet) {
		boolean alreadyConnected = false;
		for (PlayerMP p : this.connectedPlayers) {
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
			this.connectedPlayers.add(player);
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

	public synchronized void sendDataToAllClients(byte[] data, String username) {
		for (PlayerMP p : connectedPlayers) {
//			if(username == p.getUsername())
//				continue;
//			System.out.println("sent " + p.getUsername() + " to " + username);
			sendData(data, p.ipAddress, p.port);
		}
	}

	public PlayerMP getPlayerMP(String username) {
		for (PlayerMP player : this.connectedPlayers) {
			if (player.getUsername().equals(username)) {
				return player;
			}
		}
		return null;
	}

	public int getPlayerMPIndex(String username) {
		int index = 0;
		for (PlayerMP player : this.connectedPlayers) {
			if (player.getUsername().equals(username)) {
				break;
			}
			index++;
		}
		return index;
	}
}
