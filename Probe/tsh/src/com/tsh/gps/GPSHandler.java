package com.tsh.gps;

public class GPSHandler {

	double lol;
	double lob;
	double rol;
	double rob;
	double rul;
	double rub;
	double lul;
	double lub;
	double[] matrix;
	double laengeWaagerecht;
	double LaengeSenkrecht;
	double pixel;
	double ratioWaagerecht;
	double ratioSenkrecht;
	double laengengrad;
	double breitengrad;

	// float x = 100;
	// float y = 100;

	public GPSHandler() {
		pixel = 1873;
		lol = 9.183356612920761;
		lob = 48.48547494510185;
		rol = 9.19342964887619;
		rob = 48.48547494510185;
		rul = 9.19342964887619;
		rub = 48.47885489462709;
		lul = 9.183356612920761;
		lub = 48.47885489462709;
		matrix = new double[8];
		matrix[0] = lol;
		matrix[1] = lob;
		matrix[2] = rol;
		matrix[3] = rob;
		matrix[4] = rul;
		matrix[5] = rub;
		matrix[6] = lul;
		matrix[7] = lub;
		laengeWaagerecht = rol - lol;
		LaengeSenkrecht = rob - rub;
		pixel = 1873;
	}

	public void setMapRatio(double pixel) {
		this.pixel = pixel;
	}

	public String getPixelCoord(int x, int y) {
		ratioWaagerecht = laengeWaagerecht / pixel;
		ratioSenkrecht = LaengeSenkrecht / pixel;
		laengengrad = ratioWaagerecht * x + lol;
		breitengrad = ratioSenkrecht * y + lob;
		String ret = "Der Pixelpunkt bei x=" + x + " und y=" + y + " ergibt folgende Werte:\n";
		ret += "Längengrad: " + laengengrad + "\n";
		ret += "Breitengrad: " + breitengrad;
		return ret;
	}
	public static void main(String[]args){
		GPSHandler gpsh = new GPSHandler();
		String[] ddm = new String[4];
		ddm[0]="4831.80171";
		ddm[1]="N";
		ddm[2]="00903.81690";
		ddm[3]="E";
		
		float[] data = gpsh.getGPSCoord(ddm);
		System.out.println(data[0]);
		System.out.println(data[1]);
	
		
	}
	public float[] getGPSCoord(String[] ddm) {
		// convert from degree-decimal-minutes into decimal-degree
		// ddm[0]=latitudePos
		// ddm[1]=latitudeDirection
		// ddm[2]=longitudePos
		// ddm[3]=longitudeDirection
		float[] ret = new float[2];
		// latitude
		ret[0] = convertLat(ddm[0], ddm[1]);
		// longitude
		ret[1] = convertLon(ddm[2], ddm[3]);

		// System.out.println("DEBUG: latitude: "+lat_val);
		// System.out.println("DEBUG: longitude: "+lon_val);

		return ret;
	}

	public float convertLat(String raw_latitude, String lat_direction) {
		// https://stefan.bloggt.es/2010/07/gps-und-java-nmea-daten-auswerten-1/
		// 01.01.2018 16:08:19
		System.out.println(raw_latitude);
		System.out.println(lat_direction);
		String lat_deg = raw_latitude.substring(0, 2);
		String lat_min1 = raw_latitude.substring(2, 4);
		String lat_min2 = raw_latitude.substring(5);
		String lat_min3 = "0." + lat_min1 + lat_min2;
		float lat_dec = Float.parseFloat(lat_min3) / .6f;
		float lat_val = Float.parseFloat(lat_deg) + lat_dec;
		// Direction of latitude. North is positive, south negative
		if (lat_direction.equals("N")) {
			// no correction needed
		} else {
			lat_val = lat_val * -1;
		}
		return lat_val;
	}

	public float convertLon(String raw_longitude, String lon_direction) {
		// https://stefan.bloggt.es/2010/07/gps-und-java-nmea-daten-auswerten-1/
		// 01.01.2018 16:08:19
		// Conversion of longitude to floating point values
		String lon_deg = raw_longitude.substring(0, 3);
		String lon_min1 = raw_longitude.substring(3, 5);
		String lon_min2 = raw_longitude.substring(6);
		String lon_min3 = "0." + lon_min1 + lon_min2;
		float lon_dec = Float.parseFloat(lon_min3) / .6f;
		float lon_val = Float.parseFloat(lon_deg) + lon_dec;
		// direction of longitude, east is positive
		if (lon_direction.equals("E")) {
			// No correction needed
		} else {
			lon_val = lon_val * -1;
		}
		return lon_val;
	}
}
