package com.tsh.serial;

import java.io.IOException;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

import com.tsh.serial.Portreader;

public class GPSReader {
	private String msg = "";
	private Portreader pr;

	/**
	 * @param args
	 * @return
	 */

	public void setComm() {

		String[] portNames = SerialPortList.getPortNames();

		if (portNames.length == 0) {
			System.err.println("No ports available. Press Enter to exit...");
			try {
				System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
			

		}
		String portName = "";
		System.err.println("Available comm-ports:");
		for (int i = 0; i < portNames.length; i++) {
			portName = portNames[i];
			System.err.println(portNames[i]);
		}
		System.err.println("-----------------------------------------------------");
		SerialPort serialPort = new SerialPort("/dev/ttyUSB0");
		pr = new Portreader(serialPort);
		try {

			serialPort.openPort();
			serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
			serialPort.addEventListener(pr, SerialPort.MASK_RXCHAR);
		} catch (SerialPortException ex) {
			System.out.println("Error in writing data to port: " + ex);
		}

		
		
	}
	
	public void setLoop(){
		while (true) {
			msg = pr.getMsg();
			System.out.println(msg);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public String getMsg() {
		return pr.getMsg();
		//return msg;
	}
	/*
	 * public static void main(String[] args) {
	 * 
	 * String[] portNames = SerialPortList.getPortNames();
	 * 
	 * if (portNames.length == 0) {
	 * System.err.println("No ports available. Press Enter to exit..."); try {
	 * System.in.read(); } catch (IOException e) { e.printStackTrace(); }
	 * return; } String portName = "";
	 * System.err.println("Available comm-ports:"); for (int i = 0; i <
	 * portNames.length; i++) { portName = portNames[i];
	 * System.err.println(portNames[i]); }
	 * System.err.println("-----------------------------------------------------"
	 * ); SerialPort serialPort = new SerialPort("/dev/ttyUSB0"); pr = new
	 * Portreader(serialPort); try {
	 * 
	 * 
	 * serialPort.openPort(); serialPort.setParams(SerialPort.BAUDRATE_9600,
	 * SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
	 * serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN |
	 * SerialPort.FLOWCONTROL_RTSCTS_OUT); serialPort.addEventListener(pr,
	 * SerialPort.MASK_RXCHAR); } catch (SerialPortException ex) {
	 * System.out.println("Error in writing data to port: " + ex); }
	 * while(true){ System.out.println(pr.getMsg()); try { Thread.sleep(1000); }
	 * catch (InterruptedException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 * 
	 * }
	 */

}
