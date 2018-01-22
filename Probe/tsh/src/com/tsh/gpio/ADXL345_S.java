package com.tsh.gpio;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;

//Root source: http://www.instructables.com/id/Raspberry-Pi-ADXL345-3-Axis-Accelerometer-Java-Tut/
public class ADXL345_S {
	private static I2CBus Bus;
	private static I2CDevice device;
	private static byte[] data;
	private static final int X_OFFSET = 0;
	private static final int Y_OFFSET = 0;
	private static final int Z_OFFSET = 0;
	private static int[] vector = new int[3];

	public ADXL345_S() throws Exception {
		// Create I2C bus
		Bus = I2CFactory.getInstance(I2CBus.BUS_1);
		// Get I2C device, device I2C address is 0x53(83)
		device = Bus.getDevice(0x53);
		// Select Bandwidth rate register
		// Normal mode, Output data rate = 100 Hz
		device.write(0x2C, (byte) 0x0A);
		// Select Power control register
		// Auto-sleep disable
		device.write(0x2D, (byte) 0x08);
		// Select Data format register
		// Self test disabled, 4-wire interface, Full resolution, range = +/-2g
		device.write(0x31, (byte) 0x08);
		data = new byte[6];
		Thread.sleep(500);
		// Read 6 bytes of data
		// xAccl lsb, xAccl msb, yAccl lsb, yAccl msb, zAccl lsb, zAccl msb
	}

	public int[] getVector() throws IOException {
		int[] payload = new int[3];
		data[0] = (byte) device.read(0x32);
		data[1] = (byte) device.read(0x33);
		data[2] = (byte) device.read(0x34);
		data[3] = (byte) device.read(0x35);
		data[4] = (byte) device.read(0x36);
		data[5] = (byte) device.read(0x37);

		System.out.println("-------------------------------------\nFetching ADXL345-Data");
		// Convert the data to 10-bits
		// xPos
		payload[0] = get16((data[1] & 0x03) * 256 + (data[0] & 0xFF) + X_OFFSET);

		// yPos
		payload[1] = get16((data[3] & 0x03) * 256 + (data[2] & 0xFF) + Y_OFFSET);

		// zPos
		payload[2] = get16((data[5] & 0x03) * 256 + (data[4] & 0xFF) + Z_OFFSET);

		return payload;
	}

	public int get16(int valueOf10Bit) {
		// transforming 10bit-Value into 16bit-Value (linear)
		if (valueOf10Bit > 511) {
			valueOf10Bit -= 1023;
		} 
		valueOf10Bit += 255;
		System.out.print(valueOf10Bit + " ");
		return valueOf10Bit * 128;
	}

	public static void main(String args[]) throws Exception {
		ADXL345_S adxl = new ADXL345_S();
		int[] payload = new int[3];
		while (true) {
			payload = adxl.getVector();
			System.out.println("\n" + payload[0] + " " + payload[1] + " " + payload[2]);
			System.out.println("AXDL done\n-------------------------------------");
			Thread.sleep(1500);

		}
	}

}
