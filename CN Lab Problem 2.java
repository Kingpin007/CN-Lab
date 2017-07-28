import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.*;

/*
 * @author Anirudh Kanabar
 * CN Lab problem 2
 * 1) Using URL class print different components of URL
 * 2) Using Socket class check which ports on the localhost are listening for connection requests
 */
public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		URL url = new URL("https://google.com");
		String host = url.getHost();
		String protocol = url.getProtocol();
		int defaultPort = url.getDefaultPort();
		System.out.println("Host: " + host + "\nProtocol: " + protocol + "\nDefault Port: " + defaultPort);
		ArrayList<Integer> a = new ArrayList<Integer>();
		Socket socket = null,socket2 = null;
		for(int i=134;i<65536;i++) {
			try {
				socket = new Socket(InetAddress.getLocalHost(),i);
				socket.close();
				a.add(i);
				System.out.println("Added port : "+i);
			}catch(Exception e1) {
				System.out.println("failed to Add port : "+i);
			}
		}
		System.out.println("Total number of ports open: "+a.size()+"\n The Open ports are:\n");
		for(int i=0;i<a.size();i++) {
			System.out.println(a.get(i));
		}
		socket.close();
	}

}
