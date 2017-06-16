package it.unical.igpe.multiplayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	public static int uniqueID;
	private int port;
	private boolean keepGoing;
	private static ArrayList<ClientThread> al;

	public Server(int port) {
		this.port = port;
		al = new ArrayList<ClientThread>();
	}

	public void start() {
		keepGoing = true;
		try {
			ServerSocket serverSocket = new ServerSocket(port);

			while (keepGoing) {
				Socket socket = serverSocket.accept();
				if (!keepGoing)
					break;
				ClientThread t = new ClientThread(socket);
				al.add(t);
				t.start();
			}

			serverSocket.close();
			for (int i = 0; i < al.size(); i++) {
				ClientThread tc = al.get(i);
				tc.sInput.close();
				tc.sOutput.close();
				tc.socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private synchronized void broadcast(ServerMessage message) {
		System.out.println("broadcast sent");
		// we loop in reverse order in case we would have to remove a Client
		// because it has disconnected
		for (int i = al.size(); --i >= 0;) {
			ClientThread ct = al.get(i);
			// try to write to the Client if it fails remove it from the list
			if (!ct.writeMsg(message)) {
				al.remove(i);
				System.out.println("Disconnected Client " + ct.username + " removed from list.");
			}
		}
	}

	synchronized void remove(int id) {
		// scan the array list until we found the Id
		for (int i = 0; i < al.size(); ++i) {
			ClientThread ct = al.get(i);
			if (ct.id == id) {
				al.remove(i);
				return;
			}
		}
	}

	class ClientThread extends Thread {
		Socket socket;
		ObjectInputStream sInput;
		ObjectOutputStream sOutput;
		int id;
		String username;
		ServerMessage sm;

		public ClientThread(Socket socket) {
			id = ++Server.uniqueID;
			this.socket = socket;

			try {
				sOutput = new ObjectOutputStream(socket.getOutputStream());
				sInput = new ObjectInputStream(socket.getInputStream());
				username = (String) sInput.readObject();
				System.out.println(username + " just connected.");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			while (true) {
				try {
					sm = (ServerMessage) sInput.readObject();
				} catch (ClassNotFoundException e) {
					break;
				} catch (IOException e) {
					break;
				}
				
				broadcast(sm);
			}
			remove(id);
			close();
		}

		private void close() {
			try {
				if (sOutput != null)
					sOutput.close();
			} catch (IOException e) {
			}

			try {
				if (sInput != null)
					sInput.close();
			} catch (Exception e) {
			}

			try {
				if (socket != null)
					socket.close();
			} catch (Exception e) {
			}
		}

		private boolean writeMsg(ServerMessage message) {
			// if Client is still connected send the message to it
			if (!socket.isConnected()) {
				System.out.println("socket not connected");
				close();
				return false;
			}
			// write the message to the stream
			try {
				sOutput.writeObject(message);
				System.out.println("message wrote to the stream");
			}
			// if an error occurs, do not abort just inform the user
			catch (IOException e) {
				System.out.println("Error sending message to " + username);
				System.out.println(e.toString());
			}
			return true;
		}

	}
}
