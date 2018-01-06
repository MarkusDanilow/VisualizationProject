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
import com.tsh.serial.GPSReader;
import com.tsh.view.GUI;
import com.tsh.view.RPIGUI;

public class TSHMain {
	public static Ultrasonic us;
	private static MsgTransformer msg;
	private static MsgTransformer msg2;
	private static ADXL345 gyro;
	private static int[] vector = new int[3];
	private static Buzzer buzz;
	private static GPSReader gps;
	private static String gpsData;

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
		gps = new GPSReader();
		gps.setComm();
		String api = "http://www.liquidsolution.de/api.php?post=true";
		LocalDateTime lt = LocalDateTime.now();

		String t = lt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		// String t = lt.toString();
		String deviceID = "Rpi1";
		String appID = "tshV0.2";
		int apiKey = 4711;

		System.out.println("Polling-Start:" + lt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

		GUI gui = new GUI("The Seeing Hand");
		while (true) {
			System.err.println("Retrieved from GPS-Module: " + gps.getMsg());
			msg = us.getDistance();
			msg2.setVector(gyro.getVector());
			msg.setGPS(gps.getMsg());
			gpsData = msg.getPGS();
			// System.out.println(gpsData);
			String xPos = String.valueOf(gyro.getVector()[0]);
			String yPos = String.valueOf(gyro.getVector()[1]);
			String zPos = String.valueOf(gyro.getVector()[2]);
			
			if (msg.getValue() > 0) {
				gui.setValueDisplay(msg);
				gui.setGps(gpsData);

				gui.setDebugMsg(msg2.getVector());

				String con = api + "&xPos=" + xPos + "&yPos=" + yPos + "&zPos=" + zPos + "&distance=" + (int) msg.getValue() + gpsData + "&deviceID=" + deviceID + "&appID=" + appID + "&api=" + apiKey;
				gui.setApi(con);
				System.out.println("URL: "+con);
				System.out.println("URL-Length: "+con.length());

				URL url2 = new URL(con);
				BufferedReader in = new BufferedReader(new InputStreamReader(url2.openStream()));

				String inputLine;
				while ((inputLine = in.readLine()) != null)
					System.out.println("Retrieved from Server: " + inputLine);
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
				System.out.println("--------------------------------------------------------------------------");
				Thread.sleep(500);
			}

		}

	}

}
