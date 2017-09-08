import java.net.*;
import java.util.Scanner;
import java.io.*;

class Client {
	public static void main(String argv[]) throws Exception {
		System.out.println("Welcome to the Quiz\n");
		String answer;
		String question;
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		Socket clientSocket = new Socket("localhost", 6789);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		Integer score = null;
		while(true) {
			question = inFromServer.readLine();
			if(question.equals("-1"))
				break;
			System.out.print(question+"?\n");
			answer = inFromUser.readLine();
			outToServer.writeBytes(answer + '\n');
		}
		InetAddress group = InetAddress.getByName("228.5.6.56");
		Scanner input = new Scanner(System.in);
		MulticastSocket s = new MulticastSocket(6789);
		s.joinGroup(group);
		byte[] buf = new byte[1000];
		DatagramPacket recv = new DatagramPacket(buf, buf.length);
		s.receive(recv);
		String rs = new String(recv.getData());
		System.out.println(rs);
	}
}
