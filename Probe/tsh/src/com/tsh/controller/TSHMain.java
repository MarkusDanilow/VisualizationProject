package com.tsh.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;
import com.tsh.common.MsgTransformer;
import com.tsh.gpio.ADXL345;
import com.tsh.gpio.Buzzer;
import com.tsh.gpio.Ultrasonic;
import com.tsh.view.GUI;
import com.tsh.view.RPIGUI;

public class TSHMain {
	public static Ultrasonic us;
	private static MsgTransformer msg;
	private static MsgTransformer msg2;
	private static ADXL345 gyro;
	private static int[] vector = new int[3];
	private static Buzzer buzz;

	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws UnsupportedBusNumberException
	 * @throws IOException
	 */
	public static void main(String[] args) throws InterruptedException, IOException, UnsupportedBusNumberException {
		us = new Ultrasonic();
		gyro = new ADXL345();
		msg2 = new MsgTransformer();
		buzz = new Buzzer();
		String api = "http://www.liquidsolution.de/api.php?post=true";
		LocalDateTime lt = LocalDateTime.now();

		String t = lt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		// String t = lt.toString();
		String deviceID = "Rpi1";
		String appID = "tshV0.1";
		int apiKey = 4711;

		System.out.println(lt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

		RPIGUI gui = new RPIGUI("The Seeing Hand");
		while (true) {

			msg = us.getDistance();
			msg2.setVector(gyro.getVector());
			String xPos = String.valueOf(gyro.getVector()[0]);
			String yPos = String.valueOf(gyro.getVector()[1]);
			String zPos = String.valueOf(gyro.getVector()[2]);
			String distance = "65555";
			if (msg.getValue() > 0) {
				gui.setValueDisplay(msg);
				gui.setDebugMsg(msg2.getVector());

				String con = api + "&xPos=" + xPos + "&yPos=" + yPos + "&zPos=" + zPos + "&distance=" + (int)msg.getValue() + "&deviceID=" + deviceID + "&appID=" + appID + "&api=" + apiKey;
				gui.setApi(con);
				System.out.println(con);
				System.out.println(con.length());

				URL url2 = new URL(con);
				BufferedReader in = new BufferedReader(new InputStreamReader(url2.openStream()));

				String inputLine;
				while ((inputLine = in.readLine()) != null)
					System.out.println(inputLine);
				in.close();

				if (msg.getValue() < 15d) {
					if (msg.getValue() < 7d) {
						if (msg.getValue() < 6d) {
							buzz.getFeedback(20);
						} else {
							buzz.getFeedback(50);
						}
					} else {
						buzz.getFeedback(100);

					}
				}
				System.out.println(msg);
			}

		}

	}

}
