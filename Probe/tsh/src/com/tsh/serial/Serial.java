package com.tsh.serial;

import java.io.IOException;
import jssc.*;

/**
 * 
 * inpired by @author Emiliarge
 */
public class Serial {

	private static SerialPort serialPort;
	private static int counter = 0;
	private static String receivedData = "";
	private static int posStart = 0;
	private static int posEnd = 0;

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		String[] portNames = SerialPortList.getPortNames();

		if (portNames.length == 0) {
			System.out.println(
					"There are no serial-ports :( You can use an emulator, such ad VSPE, to create a virtual serial port.");
			System.out.println("Press Enter to exit...");
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		String portName = "";

		System.err.println("Available com-ports:");
		for (int i = 0; i < portNames.length; i++) {
			portName = portNames[i];
			System.err.println(portNames[i]);
		}
		System.err.println("-----------------------------------------------------");
		serialPort = new SerialPort(portName);
		try {
			// opening port
			serialPort.openPort();

			serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);

			serialPort.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);
			// writing string to port
			serialPort.writeString("UBX-CFG-RST");

		} catch (SerialPortException ex) {
			System.out.println("Error in writing data to port: " + ex);
		}
	}

	// receiving response from port
	private static class PortReader implements SerialPortEventListener {

		@Override
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
							//Debug
							// System.out.println(receivedData.indexOf("$GPRMC"));
							// System.out.println(receivedData.length());
							posStart = receivedData.indexOf("$GPRMC");
							posEnd = receivedData.indexOf("$", receivedData.indexOf("$GPRMC") + 1);
							//Debug
							// System.out.println(receivedData.indexOf("$", posEnd + 1));
							if (posEnd > posStart) {
								System.out.println(receivedData.substring(posStart, posEnd - 1));
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
	}
}
