
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

public class Server {
	static ArrayList<ServerThread> clients = new ArrayList<ServerThread>(); // LIST OF ONLINE USERS
	static ServerGUI sgui;
	static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	static int port;
	boolean keepGoing;
	ServerSocket serverSocket;
	static Socket socket;

	public Server(int p, ServerGUI sg) { // CONTRUCTOR
		sgui = sg;
		port = p;		
	}
	
	public void start() {
		keepGoing = true;
		try 
		{
			serverSocket = new ServerSocket(port); // CREATES SERVER SOCKET
			display("Server waiting for Clients on port " + port);
			
			while(keepGoing) 
			{				
				socket = serverSocket.accept(); // LISTENS FOR USER JOINING TO SERVER
				
				if(!keepGoing) {
					break;
				}
				
				ServerThread client = new ServerThread(socket); // CREATES SERVERTHREAD  
				clients.add(client); // ADDS CLIENT TO THE ARRAY						
				client.start(); // STARTS THE THREAD
			}

			try {
				serverSocket.close();
				for(int i = 0; i < clients.size(); ++i) {
					ServerThread tc = clients.get(i);
					try {
						tc.in.close();
						tc.out.close();
						tc.socket.close();
					}
					catch(IOException e) {
						e.printStackTrace();
					}
					finally {
						try {
							socket.close();
						} catch(IOException e) {
							e.printStackTrace();
						}
						try {
							serverSocket.close();
						} catch(IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			catch(Exception e) {
				display("Exception closing the server and clients: " + e);
				e.printStackTrace();
			}
		}
		
		catch (IOException e) {
			display(sdf.format(new Date()) + " Exception on new ServerSocket: " + e + "\n");
			e.printStackTrace();
		}
	}		

	public static void display(String msg) { // DISPLAY METHOD TO UPDATE EVENT LOG ON SERVER GUI
		String eventUpdate = sdf.format(new Date()) + " - " + msg + "\n";
		sgui.appendEvent(eventUpdate);
	}

	public synchronized static void broadcast(String message) { // BROADCAST TO SEND MESSAGE TO ALL CLIENT CONNECTED
		String time = sdf.format(new Date());
		String messageLf = "\n" + message + "\n" + time + "\n";
		sgui.appendRoom(messageLf); 

		for(int i = clients.size(); --i >= 0;) {
			ServerThread ct = clients.get(i);
			if(!ct.writeMsg(messageLf)) {
				clients.remove(i);
				display("Disconnected Client. User was removed from list");
			}
		}
	}
	
	public static String getHost() { // GET HOST METHOD
		String host = "";
		try {
			host = InetAddress.getLocalHost().getHostName(); // USES INET TO GET HOSTNAME
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return host; // RETURNS VARIABLE HOST
	}
	
	public static int getPort() { // GET PORT
		try {
			Properties prop = new Properties(); // PROP FILE OBJECT CREATED
			FileInputStream in = new FileInputStream("Server.prop"); // GETS 'SERVER' PROP FILE FROM SYSTEM
			prop.load(in);
			in.close();
			
			port = Integer.parseInt(prop.getProperty("Port")); // GETS PORT FROM THE PROP FILE
		} catch (IOException e) {
			System.out.println(e);
		}
		return port; // RETURNS VARIABLE PORT
	}
	
	public static boolean writeMsg(String msg) { 
	    if(!socket.isConnected()) {
	        try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    return false;
	}
	
	public synchronized static void whosonline(){
		for(int i = 0; i < clients.size(); ++i) {
			ServerThread ct = clients.get(i);
	        writeMsg((i+1) + ") " + ct.username + " since " + ct.date);
	    }
	}
}


