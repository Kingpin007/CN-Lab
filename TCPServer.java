import java.net.*;
import java.io.*;
import java.util.*;

class TCPServer {
	public static void main(String argv[]) throws Exception {
		String clientSentence;
		String capitalizedSentence;
		ServerSocket welcomeSocket = new ServerSocket(6789);

		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			try {
				clientSentence = inFromClient.readLine();
			}
			catch(Exception e) {
				System.out.println("Exception Caught: " + e.getMessage() + "program terminated.");
				break;
			}
			if(clientSentence.equals("date of server?"))
				capitalizedSentence = "date of server: " + (new Date()).toString() + '\n';
			else
				capitalizedSentence = "Server sends: " + clientSentence.toUpperCase() + '\n';
			System.out.println("Server: client 1 sends " + clientSentence);
			System.out.println("Server: " + capitalizedSentence);
			outToClient.writeBytes(capitalizedSentence);
		}
		welcomeSocket.close();
	}
}

/*
Server: client 1 sends: msg1
Server: msg2
Server: date: ------
*/