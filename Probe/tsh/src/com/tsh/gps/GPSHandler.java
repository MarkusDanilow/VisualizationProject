package com.tsh.gps;

import com.tsh.serial.ReadGPS;

public class GPSHandler {

	public static void main(String[] args) {
		double lol = 9.183356612920761;
		double lob = 48.48547494510185;
		double rol = 9.19342964887619;
		double rob = 48.48547494510185;
		double rul = 9.19342964887619;
		double rub = 48.47885489462709;
		double lul = 9.183356612920761;
		double lub = 48.47885489462709;
		double laengeWaagerecht = rol - lol;
		double LaengeSenkrecht = rob - rub;
		double pixel = 1873;
		double ratioWaagerecht = laengeWaagerecht / pixel;
		double ratioSenkrecht = LaengeSenkrecht / pixel;
		float x = 100;
		float y = 100;
		System.out.println("Der Pixelpunkt bei x=" + x + " und y=" + y + " ergibt folgende Werte:\n");
		System.out.println("Längengrad: " + (ratioWaagerecht * x + lol));
		System.out.println("Breitengrad: " + (ratioSenkrecht * y + lob));
		ReadGPS gps = new ReadGPS();
		System.out.println(gps.getGPS());
		
	}

}
