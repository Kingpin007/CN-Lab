import java.net.*;
import java.io.*;
import java.util.*;

class ServerForProxyTest {
	public static void main(String argv[]) throws Exception {
		
		ServerSocket welcomeSocket = new ServerSocket(6789);
		Socket cs = welcomeSocket.accept();
		BufferedReader ip1 = new BufferedReader(new InputStreamReader(cs.getInputStream()));
		DataOutputStream op1 = new DataOutputStream(cs.getOutputStream());
		while (true) {
			System.out.println("Waiting for input! ");
			String p1 = ip1.readLine();
			System.out.println("Server recieved: " + p1);
			op1.writeBytes(p1.toUpperCase() + '\n');
			System.out.println("Server sent: "+p1.toUpperCase()+"\n\n");
		}
	}
}