package com.tsh.net;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

public class TSHClock {
	static SimpleDateFormat time = new SimpleDateFormat("'Current time: 'HH:mm:ss");
	static SimpleDateFormat date = new SimpleDateFormat("'Current date: 'yyyy-MM-dd");
	Socket s;
	BufferedReader incomingMsg;
	PrintWriter outgoingMsg;

	public TSHClock(Socket s) {
		try {
			this.s = s;
			incomingMsg = new BufferedReader(new InputStreamReader(s.getInputStream()));
			outgoingMsg = new PrintWriter(s.getOutputStream(), true);
		} catch (IOException e) {
			System.out.println("IO-Error");
			e.printStackTrace();
		}
	}

	public void transact() {
		System.out.println("Starting Protocol");

		try {
			outgoingMsg.println("Enter DATE and TIME");
			String expectDate = incomingMsg.readLine();
			Date currentDate = new Date();
			if (expectDate.equalsIgnoreCase("date")) {
				outgoingMsg.println(date.format(currentDate));
			} else if (expectDate.equalsIgnoreCase("time")) {
				outgoingMsg.println(time.format(currentDate));
			} else {
				outgoingMsg.println(expectDate + " wrong input!");
			}

		} catch (IOException e) {
			System.out.println("IO-Error");
			e.printStackTrace();
		}
		System.out.println("Finished Protocol");

	}
}
