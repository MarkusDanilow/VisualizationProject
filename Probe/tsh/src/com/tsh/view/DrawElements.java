package com.tsh.view;

import java.awt.*;

import javax.swing.*;

public class DrawElements extends JFrame {

	Container c;
	GraphicElements z;

	public DrawElements() {
		c = getContentPane();
		z = new GraphicElements();
		c.add(z);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DrawElements d = new DrawElements();
		d.setTitle("test");
		d.setSize(800, 800);
		d.setVisible(true);
		d.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		for(int i =10; i<500;i++) {
			d.z.drawLine(i,i+200);
			i*=2;
		}
		

	}

}
