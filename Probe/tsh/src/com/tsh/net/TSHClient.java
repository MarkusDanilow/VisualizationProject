package com.tsh.net;

import java.io.*;
import java.net.*;

public class TSHClient {

	/**
	 * @param args
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws UnknownHostException {
		// TODO Auto-generated method stub
		String hostname = "localhost";
		int port = 2222;
		Socket c;
		try {
			c = new Socket(hostname, port);
			BufferedReader incomingMsg = new BufferedReader(new InputStreamReader(c.getInputStream()));
			PrintWriter outgoingMsg = new PrintWriter(c.getOutputStream(), true);
			BufferedReader keyInput = new BufferedReader(new InputStreamReader(System.in));

			// Startung Protocol
			System.out.println(hostname + " : " + port);
			String text = incomingMsg.readLine();
			System.out.println(text);
			while (true) {
				text = keyInput.readLine();
				outgoingMsg.println(text);
				text = incomingMsg.readLine();
				System.out.println(text);
			}

		} catch (ArrayIndexOutOfBoundsException ae) {
			System.out.println("Please use: java TSHClient <hostname> <port>");
		} catch (UnknownHostException ue) {
			System.out.println("Unknown DNS-Entry: " + hostname);
		} catch (SocketException se) {
			System.out.println("Socket-Error");
		} catch (IOException e) {
			System.out.println("IO-Error");
		
		}


	}

}
