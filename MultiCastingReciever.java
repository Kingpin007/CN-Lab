import java.net.*;
import java.io.*;
import java.util.*;

public class MultiCastingReciever {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		InetAddress group = InetAddress.getByName("228.5.6.56");
		Scanner input = new Scanner(System.in);
		MulticastSocket s = new MulticastSocket(6789);
		s.joinGroup(group);
		while(true) {
			byte[] buf = new byte[1000];
			DatagramPacket recv = new DatagramPacket(buf, buf.length);
			s.receive(recv);
			String rs = new String(recv.getData());
			System.out.println("Message Recieved: "+rs);
		}
	}

}
