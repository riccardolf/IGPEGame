package it.unical.igpe.multiplayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import it.unical.igpe.game.World;

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
	Client(String server, int port, String username) {
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
			display("Error connectiong to server:" + ec);
			return false;
		}
		String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
		display(msg);
		/* Creating both Data Stream */
		try {
			sInput = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException eIO) {
			display("Exception creating new Input/output Streams: " + eIO);
			return false;
		}
		// creates the Thread to listen from the server
		new ListenFromServer().start();
		// Send our username to the server this is the only message that we
		// will send as a String. All other messages will be ChatMessage objects
		try {
			sOutput.writeObject(username);
		} catch (IOException eIO) {
			display("Exception doing login : " + eIO);
			disconnect();
			return false;
		}
		// success we inform the caller that it worked
		return true;
	}

	private void display(String msg) {
		System.out.println(msg); // println in console mode
	}

	void sendMessage(World world) {
		try {
			sOutput.writeObject(world);
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

	/*
	 * To start the Client in console mode use one of the following command >
	 * java Client > java Client username > java Client username portNumber >
	 * java Client username portNumber serverAddress at the console prompt If
	 * the portNumber is not specified 1500 is used If the serverAddress is not
	 * specified "localHost" is used If the username is not specified
	 * "Anonymous" is used > java Client is equivalent to > java Client
	 * Anonymous 1500 localhost are eqquivalent
	 *
	 * In console mode, if an error occurs the program simply stops when a GUI
	 * id used, the GUI is informed of the disconnection
	 */
	public static void main(String[] args) {
		// create the Client object
		Client client = new Client("localhost", 1234, "ciaone");
	}

	/*
	 * a class that waits for the message from the server and simply 
	 * System.out.println() it in console mode
	 */
	class ListenFromServer extends Thread {

		public void run() {
			while (true) {
				try {
					String msg = (String) sInput.readObject();
					// if console mode print the message and add back the prompt
					System.out.println(msg);
					System.out.print("> ");
				} catch (IOException e) {
					display("Server has close the connection: " + e);
					break;
				}
				// can't happen with a String object but need the catch anyhow
				catch (ClassNotFoundException e2) {
				}
			}
		}
	}
}
