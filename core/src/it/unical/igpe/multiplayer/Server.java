package it.unical.igpe.multiplayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import it.unical.igpe.game.World;

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
		World world;

		public ClientThread(Socket socket) {
			id = ++Server.uniqueID;
			this.socket = socket;

			try {
				sOutput = new ObjectOutputStream(socket.getOutputStream());
				sInput = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			boolean keepGoing = true;
			while (keepGoing) {
				try {
					world = (World) sInput.readObject();
				} catch (ClassNotFoundException e) {
					break;
				} catch (IOException e) {
					break;
				}
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

		private boolean sendWorld(World world) {
			if (!socket.isConnected()) {
				close();
				return false;
			}

			try {
				sOutput.writeObject(world);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
	}
}
