package it.unical.igpe.multiplayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
	private ObjectInputStream sInput; // to read from the socket
	private ObjectOutputStream sOutput; // to write on the socket
	private Socket socket;

	// the server, the port and the username
	private String server, username;
	private int port;

	/*
	 * Constructor called by console mode server: the server address port: the
	 * port number username: the username
	 */
	public Client(String server, int port, String username) {
		this.server = server;
		this.port = port;
		this.username = username;
	}

	/*
	 * To start the dialog
	 */
	public boolean start() {
		// try to connect to the server
		try {
			socket = new Socket(server, port);
		}
		// if it failed not much I can so
		catch (Exception ec) {
			System.out.println("Error connectiong to server:" + ec);
			return false;
		}
		System.out.println("Connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
		/* Creating both Data Stream */
		try {
			sInput = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException eIO) {
			System.out.println("Exception creating new Input/output Streams: " + eIO);
			return false;
		}
		// creates the Thread to listen from the server
		new ListenFromServer().start();
		// Send our username to the server this is the only message that we
		// will send as a String. All other messages will be ChatMessage objects
		try {
			sOutput.writeObject(username);
		} catch (IOException eIO) {
			System.out.println("Exception doing login : " + eIO);
			disconnect();
			return false;
		}
		// success we inform the caller that it worked
		return true;
	}

	void sendMessage(ServerMessage msg) {
		try {
			sOutput.writeObject(msg);
			System.out.println("message sent");

		} catch (IOException e) {
		}
	}

	private void disconnect() {
		try {
			if (sInput != null)
				sInput.close();
		} catch (Exception e) {
		} // not much else I can do
		try {
			if (sOutput != null)
				sOutput.close();
		} catch (Exception e) {
		} // not much else I can do
		try {
			if (socket != null)
				socket.close();
		} catch (Exception e) {
		} // not much else I can do
	}

	class ListenFromServer extends Thread {
		public void run() {
			while (true) {
				try {
					ServerMessage msg = (ServerMessage) sInput.readObject();
					System.out.println("msg received");
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
