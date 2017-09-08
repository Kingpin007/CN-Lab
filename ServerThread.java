import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerThread extends Thread {
	protected Socket connectionSocket;
	protected String clientSentence;
	protected String capitalizedSentence;
	protected int clientnumber;
	AtomicInteger s;

	public ServerThread(Socket clientSocket, int x,AtomicInteger sc) {
		this.connectionSocket = clientSocket;
		this.clientnumber = x;
		s = sc;
	}

	public void run() {
		String[] questions = {"question 1 Options are: 1,2,3,4,5","question 2 Options are: 1,2,3,4,5","question 3 Options are: 1,2,3,4,5","question 4 Options are: 1,2,3,4,5","question 5 Options are: 1,2,3,4,5"};
		String[] answers = {"1","2","3","4","5"};
		int score = 0;
		try {
			for(int i=0;i<5;i++) {
				BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
				outToClient.writeBytes(questions[i] + '\n');
				clientSentence = inFromClient.readLine();
				if (clientSentence.equals(answers[i]))
					score += 1;
				if(i == 4)
					outToClient.writeBytes("-1\n");
			}
			s.set(score);
			
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
}
