package com.tsh.connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TreeMap;

import javax.swing.text.DateFormatter;

public class RemoteAPI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			/*
			 * URL url = new URL("http://www.liquidsolution.de"); URLConnection con =
			 * url.openConnection(); // activate the output con.setDoOutput(true);
			 * PrintStream ps = new PrintStream(con.getOutputStream()); // send your
			 * parameters to your site ps.print("data=true"); //
			 * ps.print("&secondKey=secondValue");
			 * 
			 * // we have to get the input stream in order to actually send the request
			 * con.getInputStream();
			 * 
			 * // close the print stream ps.close(); ps.flush();
			 */

			String[] tm = new String[5];

			String api = "http://www.liquidsolution.de/api.php?post=true";
			LocalDateTime lt = LocalDateTime.now();
			String xPos = "65555";
			String yPos = "65555";
			String zPos = "65555";
			String distance = "65555";
			String t=lt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			//String t = lt.toString();
			String deviceID = "Rpi1";
			String appID = "tshV0.1";
			int apiKey = 4711;

			System.out.println(lt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			String con = api + "&xPos=" + xPos + "&yPos=" + yPos + "&zPos=" + zPos + "&distance=" + distance + "&t=" + t
					+ "&deviceID=" + deviceID + "&appID=" + appID+ "&api=" + apiKey;
			System.out.println(con);
			System.out.println(con.length());

			URL url2 = new URL(con);
			BufferedReader in = new BufferedReader(new InputStreamReader(url2.openStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null)
				System.out.println(inputLine);
			in.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
