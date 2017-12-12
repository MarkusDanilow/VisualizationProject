package com.tsh.gpio;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.tsh.common.MsgTransformer;

public class Buzzer {

	private static GpioPinDigitalOutput buzzerPin;
	private static GpioPinDigitalOutput vibroPin;
	private static GpioController gpio = GpioFactory.getInstance();

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public Buzzer(){
		buzzerPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_15);
		vibroPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_16);
		gpio = GpioFactory.getInstance();
	}
	
	public void getFeedback(int duration) throws InterruptedException {
		// TODO Auto-generated method stub
		
		try {
			buzzerPin.high(); // Make trigger pin HIGH
			vibroPin.high();
			Thread.sleep((long) duration);// Delay for 10 microseconds
			buzzerPin.low();
			vibroPin.low();
			System.out.println("feedback!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
