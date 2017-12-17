package com.tsh.net;

import java.io.IOException;
import java.net.*;

public class TSHServer {

	public InetAddress getHost(String hostname) {
		System.out.println("Host-Check started...");
		InetAddress ip = null;
		try {
			ip = InetAddress.getByName(hostname);
			System.out.println("Hostname:\t " + ip.getHostName());
			System.out.println("Host-IP:\t " + ip.getHostAddress());
		} catch (UnknownHostException uhx) {
			System.err.println("Error:\tUnknown host");
		} finally {
			System.out.println("Host-Check finished...");
		}
		return ip;
	}

	public static void setServer(int port) {
		ServerSocket server;
		try {
			server = new ServerSocket(port);
			Socket socket = server.accept();
			new TSHClock(socket).transact();
		} catch (ArrayIndexOutOfBoundsException ae) {
			System.out.println("Please use: java TSHServer <port>");
			ae.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TSHServer tsh = new TSHServer();
		tsh.getHost("www.liquidsolution.de");

		setServer(2222);

	}

}
