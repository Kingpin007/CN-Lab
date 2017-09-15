import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		String sentence = null;
		String modifiedSentence = null;
		int playerNumber;
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		Socket clientSocket = new Socket("localhost", 6789);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		System.out.println("Enter Player number");
		playerNumber = Integer.parseInt(inFromUser.readLine());
		if(playerNumber == 1) {
			while(true) {
				System.out.print("Player "+playerNumber+": ");
				sentence = inFromUser.readLine();
				outToServer.writeBytes(sentence + '\n');
				modifiedSentence = inFromServer.readLine();
				displayBoard(modifiedSentence);
				if(modifiedSentence.contains("won") || modifiedSentence.contains("draw"))
					break;
				modifiedSentence = inFromServer.readLine();
				displayBoard(modifiedSentence);
				if(modifiedSentence.contains("won") || modifiedSentence.contains("draw"))
					break;
			}
		}
		else if(playerNumber == 2) {
			while(true) {
				modifiedSentence = inFromServer.readLine();
				displayBoard(modifiedSentence);
				if(modifiedSentence.contains("Player") || modifiedSentence.contains("Draw"))
					break;
				System.out.print("Player "+playerNumber+": ");
				sentence = inFromUser.readLine();
				outToServer.writeBytes(sentence + '\n');
				modifiedSentence = inFromServer.readLine();
				displayBoard(modifiedSentence);
				if(modifiedSentence.contains("Player") || modifiedSentence.contains("Draw"))
					break;
			}
		}
	}
	private static void displayBoard(String msg) {
		String p1 = null,p2 = null;
		for(int i=0;i<msg.length();i++) {
			if(i%2==0)
				p1 += msg.charAt(i);
			else
				p2 += msg.charAt(i);
		}
		String[] board = {"1"," | ","2"," | ","3","\n-----------\n","4"," | ","5"," | ","6","\n-----------\n","7"," | ","8"," | ","9","\n-----------\n"};
		if(p1!=null) {
			for(int i=0;i<board.length;i++) {
			if(p1.contains(board[i]))
				board[i] = "X";
			}
		}
		if(p2 != null) {
			for(int i=0;i<board.length;i++) {
				if(p2.contains(board[i]))
					board[i] = "O";
			}
		}
		System.out.println("\nCurrent Board State: "+msg);
		for(int i=0;i<board.length;i++) {
			System.out.print(board[i]);
		}
	}
}
