package com.tsh.view;

import javax.swing.*;
import java.awt.*;

public class GraphicElements extends JPanel {
	private int[] x1 = new int[5000];
	private int[] x2 = new int[5000];
	private int[] x3 = new int[5000];
	private int[] y1 = new int[5000];
	private int[] y2 = new int[5000];
	private int[] y3 = new int[5000];
	private boolean state = false;

	private int n = 0;

	public void paintComponent(Graphics g) {
		x1[0] = 50;
		
		y1[0] = 10;
		x2[0] = 250;
		
		y2[0] = 10;
		

		// draw
		g.drawLine(50, 10, 50, 500);
		g.drawLine(250, 10, 250, 500);
		g.drawLine(450, 10, 450, 500);

		g.setColor(Color.RED);
		g.drawPolyline(x1, y1, n);
		g.drawPolyline(x2, y1, n);
		

		// int [] x = {30,40,60,70};
		// int [] y = {5,5,30,5};
		// g.drawPolygon(x, y, 4);

	}

	public void drawLine(int y1, int y2) {
		int dim = 0;

		this.y1[n++] = y1;
		this.y1[n] = y2;
		if(this.state == true) {
			this.x1[n] = 250;
			this.x2[n] = 450;
			this.state = false;
		}else {
			this.x1[n] = 50;
			this.x2[n] = 250;
			this.state = true;
		}
		
		
		repaint();
		
		
		
	}

}
