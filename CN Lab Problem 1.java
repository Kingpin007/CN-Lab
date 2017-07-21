import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author Kingpin007
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		InetAddress remotehost = null,localhost = null,remoteIPAddressObject = null;
		try {
			remotehost = InetAddress.getByName(new URL("https://"+input.next()).getHost());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			localhost = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String remoteIP = remotehost.getHostAddress();
		byte[] remoteIPAddress = remotehost.getAddress();
		String localIP = localhost.getHostAddress();
		String localhostName = localhost.getHostName();
		try {
			remoteIPAddressObject = InetAddress.getByAddress(remoteIPAddress);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String remotehostName = remoteIPAddressObject.getCanonicalHostName();
		System.out.println("Remote Host IP: "+remoteIP+"\nLocal IP: "+localIP+"\nLocal hostname: "+localhostName+"\nRemote hostname: "+remotehostName);
		input.close();		
	}

}
