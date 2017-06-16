package it.unical.igpe.multiplayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
	private ObjectInputStream sInput; // to read from the socket
	private ObjectOutputStream sOutput; // to write on the socket
	private Socket socket;

	private String server, username;
	private int port;

	public Client(String server, int port, String username) {
		this.server = server;
		this.port = port;
		this.username = username;
	}

	public boolean start() {
		try {
			socket = new Socket(server, port);
		}
		catch (Exception ec) {
			System.out.println("Error connection to server:" + ec);
			return false;
		}
		System.out.println("Connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
		try {
			sInput = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException eIO) {
			System.out.println("Exception creating new Input/output Streams: " + eIO);
			return false;
		}
		new ListenFromServer().start();
		try {
			sOutput.writeObject(username);
		} catch (IOException eIO) {
			System.out.println("Exception doing login : " + eIO);
			disconnect();
			return false;
		}
		return true;
	}

	void sendMessage(ServerMessage msg) {
		try {
			sOutput.writeObject(msg);
			sOutput.flush();
			System.out.println("message sent");

		} catch (IOException e) {
			System.out.println("Exception writing to server: " + e);
		}
	}

	private void disconnect() {
		try {
			if (sInput != null)
				sInput.close();
		} catch (Exception e) {
		} 
		try {
			if (sOutput != null)
				sOutput.close();
		} catch (Exception e) {
		}
		try {
			if (socket != null)
				socket.close();
		} catch (Exception e) {
		}
	}

	class ListenFromServer extends Thread {
		public void run() {
			while (true) {
				try {
					ServerMessage msg = (ServerMessage) sInput.readObject();
					System.out.println(msg.type);
				} catch (IOException e) {
					System.out.println("Server has close the connection: " + e);
					break;
				}
				catch (ClassNotFoundException e2) {
					System.out.println("Class not found: " + e2);
				}
			}
		}
	}
}
