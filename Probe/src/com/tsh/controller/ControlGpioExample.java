package com.tsh.controller;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class ControlGpioExample {

	private static GpioPinDigitalOutput sensorTriggerPin;
	private static GpioPinDigitalInput sensorEchoPin;
	final static double tol=8.3; //add tolerance to measurement in fact the sensor isnt high-quality
	final static GpioController gpio = GpioFactory.getInstance();

	public static void main(String[] args) throws InterruptedException {
		new ControlGpioExample().run();
	}

	public void run() throws InterruptedException {
		sensorTriggerPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_25); // Trigger
																				// pin
																				// as
																				// OUTPUT
		sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_24,	PinPullResistance.PULL_DOWN); // Echo pin as INPUT

		while (true) {
			try {
				Thread.sleep(2000);
				sensorTriggerPin.high(); // Make trigger pin HIGH
				Thread.sleep((long) 0.01);// Delay for 10 microseconds
				sensorTriggerPin.low(); // Make trigger pin LOW

				while (sensorEchoPin.isLow()) { // Wait until the ECHO pin gets
												// HIGH

				}
				long startTime = System.nanoTime(); // Store the surrent time to
													// calculate ECHO pin HIGH
													// time.
				
				while (sensorEchoPin.isHigh()) { // Wait until the ECHO pin gets
													// LOW

				}
				long endTime = System.nanoTime(); // Store the echo pin HIGH end
													// time to calculate ECHO
													// pin HIGH time.

				//System.out.println("Distance :"+ ((((endTime - startTime) / 1e3) / 2) / 29.1) + " cm"); // Printing
																					// out
																					// the
																					// distance
																					// in
																					// cm
				System.out.println("Distance: " + (endTime - startTime)/1000/2/(34.32-tol));
				
				Thread.sleep(1000);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}