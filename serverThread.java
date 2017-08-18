import java.net.*;
import java.io.*;
import java.util.*;

public class serverThread extends Thread {
	protected Socket connectionSocket;
	protected String clientSentence;
	protected String capitalizedSentence;
	protected int clientnumber;

	public serverThread(Socket clientSocket, int x) {
		this.connectionSocket = clientSocket;
		this.clientnumber = x;
	}

	public void run() {
		try {
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			clientSentence = inFromClient.readLine();
			if (clientSentence.equals("date of server?"))
				capitalizedSentence = "date of server: " + (new Date()).toString() + '\n';
			else
				capitalizedSentence = "Server sends: " + clientSentence.toUpperCase() + '\n';
			System.out.println("Server: client " + clientnumber + " sends " + clientSentence);
			System.out.println("Server: " + capitalizedSentence);
			outToClient.writeBytes(capitalizedSentence);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
}
