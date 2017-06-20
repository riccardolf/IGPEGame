package it.unical.igpe.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.game.IGPEGame;
import it.unical.igpe.net.packet.Packet;
import it.unical.igpe.net.packet.Packet00Login;
import it.unical.igpe.net.packet.Packet01Disconnect;
import it.unical.igpe.net.packet.Packet02Move;
import it.unical.igpe.net.packet.Packet03Fire;
import it.unical.igpe.net.packet.Packet.PacketTypes;

public class GameClient extends Thread {
	private InetAddress ipAddress;
	private DatagramSocket socket;

	public GameClient(String ipAddress) {
		try {
			this.socket = new DatagramSocket();
			this.ipAddress = InetAddress.getByName(ipAddress);
			System.out.println("Connected to server " + ipAddress);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
	}
	
	public void run() {
		while(true) {
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
	
	public void sendData(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1234);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
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
			handleLogin((Packet00Login) packet, address, port);
			break;
		case DISCONNECT:
			packet = new Packet01Disconnect(data);
            System.out.println("[" + address.getHostAddress() + ":" + port + "] "
                    + ((Packet01Disconnect) packet).getUsername() + " has left the world...");
            IGPEGame.game.worldMP.removePlayerMP(((Packet01Disconnect) packet).getUsername());
			break;
		case MOVE:
			packet = new Packet02Move(data);
			handleMove((Packet02Move) packet);
			break;
		case FIRE:
			packet = new Packet03Fire(data);
			handleFire((Packet03Fire) packet);
			break;
		}
	}

	private void handleFire(Packet03Fire packet) {
//		System.out.println("Player " + packet.getUsername() + " fired");
		IGPEGame.game.worldMP.fireBullet(packet.getUsername(), packet.getX(), packet.getY(), packet.getAngle());
	}

	private void handleLogin(Packet00Login packet, InetAddress address, int port) {
		System.out.println("[" + address.getHostAddress() + ":" + port + "]"
				+ packet.getUsername() + " has joined the game");
		PlayerMP player = new PlayerMP(new Vector2(packet.getX(), packet.getY()), IGPEGame.game.worldMP, packet.getUsername(), address, port);
		IGPEGame.game.worldMP.addEntity(player);
	}
	
	private void handleMove(Packet02Move packet) {
//		System.out.println("Player " + packet.getUsername() + " moved");
		IGPEGame.game.worldMP.movePlayer(packet.getUsername(), packet.getX(), packet.getY(), packet.getAngle(), packet.getState(), packet.getActWeapon());
	}
}
