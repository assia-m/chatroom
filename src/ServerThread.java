
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

public class ServerThread extends Thread {
	Socket socket;
	ObjectInputStream in; // OBJECT INPUT
	ObjectOutputStream out; // OBJECT OUTPUT
	String username;
	MessageHandler mh; // OBJECT LINKED TO CLASS 'MESSAGEHANDLER'
	String date = new Date().toString() + "\n";
	SimpleDateFormat time = Server.sdf;
	static ArrayList<ServerThread> onlineClients = Server.clients; // LIST OF ONLINE CLIENTS

	ServerThread(Socket s) { // CONSTUCTOR
		socket = s;
		System.out.println("Thread trying to create Object Input/Output Streams");
		try
		{
			out = new ObjectOutputStream(socket.getOutputStream()); // CREATES OBJECT OUTPUT
			in  = new ObjectInputStream(socket.getInputStream()); // CREATES INPUT INPUT
			username = (String) in.readObject();
			Server.display("User just connected"); // DISPLAYS ON SERVER USER HAS CONNECTED
		}
		catch (IOException e) {
			Server.display("Exception creating new Input/output Streams: " + e);
			return;
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		boolean keepGoing = true;
		while(keepGoing) {
			try {
				mh = (MessageHandler) in.readObject(); // READS OBJECT
			}
			catch (IOException e) {
				e.printStackTrace();
				break;				
			}
			catch(ClassNotFoundException e) {
				e.printStackTrace();
				break;
			}
			
			String message = mh.getMessage();  // GETS MESSAGE FROM CLASS 'MESSAGEHANDLER' THROUGH OBJECT 'mh'

			switch(mh.getType()) {

			case MessageHandler.MESSAGE:
				Server.broadcast(username + message); // BROADCAST METHOD CALLED
				break;
			case MessageHandler.WHOISIN: // DISPLAYS LIST OF CLIENTS ONLINE WITH WHAT TIME THEY JOINED
				writeMsg("\nList of the users connected at " + time.format(new Date()) + "\n");
				for(int i = 0; i < onlineClients.size(); ++i) {
					ServerThread ct = onlineClients.get(i);
					writeMsg((i+1) + ") " + ct.username + " since " + ct.date);
				}
				break;
			}
		}
	}

	public boolean writeMsg(String msg) { // METHOD TO SEND MESSAGE TO CLIENT SIDE
		try {
			out.writeObject(msg); // WRITES MESSAGE TO CLIENTS
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return true;
	}
}
