import java.net.*;
import java.io.*;
import java.util.*;

public class MultiCasting {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// join a Multicast group and send the group salutations
		Scanner input = new Scanner(System.in);
		String msg;
		System.out.print("Please Enter Client ID: ");
		String clientID = input.nextLine();
		InetAddress group = InetAddress.getByName("228.5.6.56");
		MulticastSocket s = new MulticastSocket(6789);
		s.joinGroup(group);
		while(true) {
			System.out.print("Enter Message to be Sent: ");
			msg = input.nextLine();
			msg = clientID+": " + msg;
			if(msg.equals("-1"))
				break;
			DatagramPacket hi = new DatagramPacket(msg.getBytes(), msg.length(), group, 6789);
			s.send(hi);
		}
	}

}
