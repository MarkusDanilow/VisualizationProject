package com.tsh.gpio;


import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.tsh.common.StringAndDouble;

public class Ultrasonic {
	private static GpioPinDigitalOutput sensorTriggerPin;
	private static GpioPinDigitalInput sensorEchoPin;
	private static double tol; // add tolerance to measurement in fact the
	private static StringAndDouble msg;

	// sensor isn't high-quality
	private static GpioController gpio = GpioFactory.getInstance();

	public Ultrasonic() {
		sensorTriggerPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_25);
		sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_24, PinPullResistance.PULL_DOWN);
		tol = 8.3;
		gpio = GpioFactory.getInstance();
	}

	public StringAndDouble getDistance() throws InterruptedException {
		double distance = 0;

		msg = new StringAndDouble(null, 0);
		try {
			Thread.sleep(500);
			sensorTriggerPin.high(); // Make trigger pin HIGH
			Thread.sleep((long) 0.01);// Delay for 10 microseconds
			sensorTriggerPin.low(); // Make trigger pin LOW

			while (sensorEchoPin.isLow()) {
				// Wait until the ECHO pin gets HIGH

			}
			long startTime = System.nanoTime();
			// Store the surrent time to calculate ECHO pin HIGH time

			while (sensorEchoPin.isHigh()) {
				// Wait until the ECHO pin gets LOW
			}
			long endTime = System.nanoTime();
			msg.setValue((endTime - startTime) / 1e3 / 2 / 29.1);

			Thread.sleep(500);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (distance < 100) {
			msg.setText("DEBUG: Distance: ");
			return msg;
		} else {
			msg.setText("DEBUG: Out of range: ");
			return msg;
		}

	}

	public static double getTol() {
		return tol;
	}

	public static void setTol(double tol) {
		Ultrasonic.tol = tol;
	}

}
