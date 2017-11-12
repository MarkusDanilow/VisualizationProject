package com.tsh.controller;


import com.tsh.common.StringAndDouble;
import com.tsh.gpio.Ultrasonic;
import com.tsh.view.GUI;

public class TSHMain {
	public static Ultrasonic us;
	private static StringAndDouble msg;
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		us = new Ultrasonic();

		GUI gui = new GUI("The Seeing Hand");
		while(true){
			msg = us.getDistance();
			if(msg.getValue()>0){
				gui.setValueDisplay(msg);
				gui.setDebugMsg(msg);
				System.out.println(msg);
			}
			
			
		}

	}

}
