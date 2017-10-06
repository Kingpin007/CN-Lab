import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class proxyThread extends Thread {
	private Socket serverSocket;
	private Socket clientSocket;
	private int clients;
	proxyThread(Socket ss, Socket s,int clients){
		serverSocket = ss;
		clientSocket = s;
		this.clients = clients;
	}
	public void run() {
		try {
			System.out.println("New Client Connected! Current number of clients connected : " + clients);
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(serverSocket.getOutputStream());
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String stringClient=null,stringServer=null;
			while(true) {
				stringClient = inFromClient.readLine();
				System.out.println("Read from client: " + stringClient);
				outToServer.writeBytes(stringClient+'\n');
				System.out.println("Out To Server: " + stringClient+"\n\n");
				stringServer = inFromServer.readLine();
				outToClient.writeBytes(stringServer+'\n');
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
