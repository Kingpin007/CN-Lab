import java.net.*;
import java.io.*;
import java.util.*;

class TCPServer {
	public static void main(String argv[]) throws Exception {
		
		ServerSocket welcomeSocket = new ServerSocket(6789);
		int i = 0;
		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			serverThread st = new serverThread(connectionSocket,i++);
			st.run();
		}
	}
}

/*
Server: client 1 sends: msg1
Server: msg2
Server: date: ------
*/