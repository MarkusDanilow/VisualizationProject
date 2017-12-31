package com.tsh.serial;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class Portreader implements SerialPortEventListener {
	private static SerialPort serialPort;
	private static int counter = 0;
	private static String receivedData = "";
	private static int posStart = 0;
	private static int posEnd = 0;
	private static String msg = "";

	
	public Portreader(SerialPort sp){
		this.serialPort = sp;
	}
	public void serialEvent(SerialPortEvent event) {

		if (event.isRXCHAR() && event.getEventValue() > 0) {
			try {
				if (counter < 5) {
					receivedData += serialPort.readString(event.getEventValue());
					counter++;
				} else {
					receivedData = receivedData.replace("\r", "").replace("\n", "");
					receivedData = receivedData.replace("$", "\n$");
					if (receivedData.contains("$GPRMC")) {
						posStart = receivedData.indexOf("$GPRMC");
						posEnd = receivedData.indexOf("$", receivedData.indexOf("$GPRMC") + 1);
						if (posEnd > posStart) {
							msg = receivedData.substring(posStart, posEnd - 1);
							//System.out.println(msg);
							receivedData = "";
						}
					}
					counter = 0;
				}
				;
			} catch (SerialPortException ex) {
				System.out.println("Error in receiving response from port: " + ex);
			}
		}
	}
	public String getMsg(){
		return msg;
	}
}
