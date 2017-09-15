import java.net.*;
import java.io.*;
import java.util.*;

class Server {
	public static void main(String argv[]) throws Exception {
		
		ServerSocket welcomeSocket = new ServerSocket(6789);
		Socket cs = welcomeSocket.accept();
		Socket cs2 = welcomeSocket.accept();
		BufferedReader ip1 = new BufferedReader(new InputStreamReader(cs.getInputStream()));
		DataOutputStream op1 = new DataOutputStream(cs.getOutputStream());
		BufferedReader ip2 = new BufferedReader(new InputStreamReader(cs2.getInputStream()));
		DataOutputStream op2 = new DataOutputStream(cs2.getOutputStream());
		String currentState = "";
		while (true) {
			String p1 = ip1.readLine();
			System.out.println(p1);
			if(currentState.contains(p1))
				throw new Exception("Wrong input from user!");
			currentState += p1;
			if(whoWon(currentState) == 0) {
				op1.writeBytes(currentState +'\n');
				op2.writeBytes(currentState +'\n');
			}
			else if(whoWon(currentState) == 1) {
				op1.writeBytes(currentState + "Player 1 Won!\n");
				op2.writeBytes(currentState + "Player 1 Won!\n");
				break;
			}
			else if(whoWon(currentState) == 2) {
				op1.writeBytes(currentState + "Player 2 Won!\n");
				op2.writeBytes(currentState + "Player 2 Won!\n");
				break;
			}
			else {
				op1.writeBytes(currentState + "Draw\n");
				op2.writeBytes(currentState + "Draw\n");
				break;
			}
			p1 = ip2.readLine();
			System.out.println(p1);
			if(currentState.contains(p1))
				throw new Exception("Wrong input from user!");
			currentState += p1;
			if(whoWon(currentState) == 0) {
				op1.writeBytes(currentState +'\n');
				op2.writeBytes(currentState +'\n');
			}
			else if(whoWon(currentState) == 1) {
				op1.writeBytes(currentState + "Player 1 Won!\n");
				op2.writeBytes(currentState + "Player 1 Won!\n");
				break;
			}
			else if(whoWon(currentState) == 2) {
				op1.writeBytes(currentState + "Player 2 Won!\n");
				op2.writeBytes(currentState + "Player 2 Won!\n");
				break;
			}
			else {
				op1.writeBytes(currentState + "Draw\n");
				op2.writeBytes(currentState + "Draw\n");
				break;
			}
		}
	}
	private static int whoWon(String msg) {
		// TODO Auto-generated method stub
		if(msg.length() <= 4)
			return 0;
		String[] winners = {"159","357","123","456","789","147","258","369"};
		String p1 = null,p2 = null;
		for(int i=0;i<msg.length();i++) {
			if(i%2==0)
				p1 += msg.charAt(i);
			else
				p2 += msg.charAt(i);
		}
		for(int i=0;i<winners.length;i++) {
			if(p1.contains(winners[i]))
				return 1;
			else if(p2.contains(winners[i]))
				return 2;
		}
		if(msg.length() == 9)
			return 3;
		else
			return 0;
	}
}

	
