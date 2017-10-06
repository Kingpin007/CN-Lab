import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Proxy {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ServerSocket welcomeSocket = new ServerSocket(1234);
		Socket clientSocket = new Socket("localhost", 6789);
		int clientNumber = 0;
		while(true) {
			Socket serverSocket = welcomeSocket.accept();
			proxyThread pt = new proxyThread(serverSocket,clientSocket,++clientNumber);
			pt.start();
		}
	}
}
