package com.tsh.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TSHServer {

	private static SimpleDateFormat time = new SimpleDateFormat("'Current time: 'HH:mm:ss");
	static SimpleDateFormat date = new SimpleDateFormat("'Current date: 'yyyy-MM-dd");
	private Socket s;
	private BufferedReader incomingMsg;
	private PrintWriter outgoingMsg;

	private static String hostname = "www.liquidsolution.de";
	private static int port = 2222;

	private static ServerSocket server;
	private static Socket socket;

	public TSHServer() {
		System.out.println("Starting Server");
		try {
			server = new ServerSocket(port);
			socket = server.accept();
			// s = new Socket();
			incomingMsg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outgoingMsg = new PrintWriter(socket.getOutputStream(), true);
		} catch (ArrayIndexOutOfBoundsException ae) {
			System.out.println("Please use: java TSHServer <port>");
			ae.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO-Error");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TSHServer tsh = new TSHServer();
			tsh.transact();
	}

	public void transact() {
		//System.out.println("Starting Protocol");
		try {
			outgoingMsg.println("Enter CMD: {DATE, TIME}");
			String expectDate = incomingMsg.readLine();
			Date currentDate = new Date();
			if (expectDate.equalsIgnoreCase("date")) {
				outgoingMsg.println(date.format(currentDate));
			} else if (expectDate.equalsIgnoreCase("time")) {
				outgoingMsg.println(time.format(currentDate));
			} else {
				outgoingMsg.println(expectDate + " wrong command!");
			}
		} catch (IOException e) {
			System.out.println("IO-Error");
			e.printStackTrace();
		}
		//System.out.println("Finished Protocol");
		transact();
	}

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
}