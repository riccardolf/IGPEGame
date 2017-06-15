package it.unical.igpe.multiplayer;

public class ServerMessage {
	// The different types of message sent by the Client
	// WHOISIN to receive the list of the users connected
	// MESSAGE an ordinary message
	// LOGOUT to disconnect from the Server
	static final int MESSAGE = 0, LOGOUT = 1;
	private int type;
	private String message;

	// constructor
	ServerMessage(int type, String message) {
		this.type = type;
		this.message = message;
	}

	int getType() {
		return type;
	}

	String getMessage() {
		return message;
	}

}
