import java.net.*;
import java.util.*;

public class UDPClient {
	public static void main(String args[]) throws Exception {
		System.out.print("Client: ");
		Scanner input = new Scanner(System.in);
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName("localhost");
		byte[] sendData = new byte[1024];
		byte[] recieveData = new byte[1024];
		String sentence = input.nextLine();
		sendData = sentence.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
		clientSocket.send(sendPacket);
		DatagramPacket recievepacket = new DatagramPacket(recieveData,recieveData.length);
		clientSocket.receive(recievepacket);
		String recieved = new String(recievepacket.getData());
		System.out.println("Client: "+ recieved);
		System.out.println("Client: date of server?");
		clientSocket.receive(recievepacket);
		String recieved2 = new String(recievepacket.getData());
		System.out.println("Client: date of server is: " + recieved2);
		clientSocket.close();
	}
}