import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author Kingpin007 Question : Using java 1) Print Local Host name. 2) Print
 *         local IP Address. 3) Print remote Host Name by giving its IP Address.
 *         4) Print remote IP Address by giving its Host Name. 5) print the MAC
 *         Address of the Local Host.
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		InetAddress remotehost = null, localhost = null, remoteIPAddressObject = null;
		NetworkInterface networkInterfaces = null;
		try {
			remotehost = InetAddress.getByName(new URL("https://" + input.next()).getHost());
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
			networkInterfaces = NetworkInterface.getByInetAddress(localhost);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		NetworkInterface ni = networkInterfaces;
		System.out.println("Interface Name: " + ni.getName());
		System.out.println("Display Name: " + ni.getDisplayName());
		byte[] macAddress = null;
		try {
			macAddress = ni.getHardwareAddress();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			StringBuilder sb = new StringBuilder();
			for (byte b : macAddress) {
				sb.append(String.format("%02X", b));
				sb.append(":");
			}
			sb.deleteCharAt(sb.length() - 1);
			String mac = new String(sb);
			System.out.println("MAC Address: " + mac);
		} catch (Exception e) {
		}
		try {
			remoteIPAddressObject = InetAddress.getByAddress(remoteIPAddress);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String remotehostName = remoteIPAddressObject.getCanonicalHostName();
		System.out.println("Remote Host IP: " + remoteIP + "\nLocal IP: " + localIP + "\nLocal hostname: "
				+ localhostName + "\nRemote hostname: " + remotehostName);
		String ipaddress = input.next();
		String[] ips = ipaddress.split("\\.");

		Integer[] ipvalue = new Integer[4];
		int c = 0;
		for (String s : ips) {
			ipvalue[c++] = Integer.valueOf(s);
		}
		if (ipvalue[0] <= 126)
			System.out.println("Class A");
		else if (ipvalue[0] >= 128 && ipvalue[0] < 192)
			System.out.println("Class B");
		else if (ipvalue[0] >= 192 && ipvalue[0] < 224)
			System.out.println("Class C");
		else if (ipvalue[0] >= 224 && ipvalue[0] < 240)
			System.out.println("Class D");
		else if (ipvalue[0] >= 240 && ipvalue[0] < 255)
			System.out.println("Class E");
		input.close();
	}

}
