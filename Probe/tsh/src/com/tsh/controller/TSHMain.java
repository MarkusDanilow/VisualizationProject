package com.tsh.controller;


import java.io.IOException;

import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;
import com.tsh.common.MsgTransformer;
import com.tsh.gpio.ADXL345;
import com.tsh.gpio.Ultrasonic;
import com.tsh.view.GUI;

public class TSHMain {
	public static Ultrasonic us;
	private static MsgTransformer msg;
	private static MsgTransformer msg2;
	private static ADXL345 gyro;
	private static int[] vector = new int[3];
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
		
		GUI gui = new GUI("The Seeing Hand");
		while(true){

			msg = us.getDistance();
			msg2.setVector(gyro.getVector());
			if(msg.getValue()>0){
				gui.setValueDisplay(msg);
				gui.setDebugMsg(msg2.getVector());
				System.out.println(msg);
			}
			
			
		}

	}

}