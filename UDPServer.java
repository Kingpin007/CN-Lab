import java.io.*;
import java.net.*;
import java.util.*;

public class UDPServer {
	public static void main(String args[]) throws Exception {
		DatagramSocket serverSocket = new DatagramSocket(9876);
		byte[] receiveData = new byte[1024];
		while (true) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			String sentence = new String(receivePacket.getData());
			System.out.println("server: client 1 sends - " + sentence);
			System.out.println("Server: msg2");
			byte[] sendData = new byte[1024];
			Date dateObject = new Date();
			System.out.println("Server: date:"+ dateObject.toString());
			byte[] sendData1 = "server sends: msg2".getBytes();
			byte[] sendData2 = (dateObject.toString()).getBytes();
			DatagramPacket sendPacket1 = new DatagramPacket(sendData1, sendData1.length, receivePacket.getAddress(), receivePacket.getPort());
			DatagramPacket sendPacket2 = new DatagramPacket(sendData2, sendData2.length, receivePacket.getAddress(), receivePacket.getPort());
			serverSocket.send(sendPacket1);
			serverSocket.send(sendPacket2);
		}
	}
}