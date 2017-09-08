import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

class Server {
	public static void main(String argv[]) throws Exception {
		Scanner input = new Scanner(System.in);
		ServerSocket welcomeSocket = new ServerSocket(6789);
		Integer[] scores = new Integer[1000];
		System.out.print("Enter the number of clients: ");
		int n = input.nextInt();
		AtomicInteger s = new AtomicInteger(0);
		for(int i=0;i<n;i++) {
			Socket connectionSocket = welcomeSocket.accept();
			ServerThread st = new ServerThread(connectionSocket,i,s);
			st.run();
			scores[i] = s.get();
		}
		String msg = "";
		for(int i=0;i<n;i++)
			msg += ("Client " + (i+1) +" score: "+ scores[i].toString()+'\n');
		InetAddress group = InetAddress.getByName("228.5.6.56");
		MulticastSocket s1 = new MulticastSocket(6789);
		s1.joinGroup(group);
		while(true) {
			DatagramPacket hi = new DatagramPacket(msg.getBytes(), msg.length(), group, 6789);
			s1.send(hi);
		}
	}
}