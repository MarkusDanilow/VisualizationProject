package com.tsh.connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class RemoteAPI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			URL url = new URL("http://www.liquidsolution.de");
			URLConnection con = url.openConnection();
			// activate the output
			con.setDoOutput(true);
			PrintStream ps = new PrintStream(con.getOutputStream());
			// send your parameters to your site
			ps.print("data=true");
			// ps.print("&secondKey=secondValue");

			// we have to get the input stream in order to actually send the request
			con.getInputStream();

			// close the print stream
			ps.close();
			ps.flush();
			URL url2 = new URL("http://www.liquidsolution.de/api.php?data=true");
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
