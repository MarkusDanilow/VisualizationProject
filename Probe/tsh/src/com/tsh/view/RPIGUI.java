package com.tsh.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.Toolkit;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.BevelBorder;

import com.tsh.common.MsgTransformer;

public class RPIGUI {
	private static JFrame frame;
	private static JLayeredPane paneContrainer;
	private static SpringLayout springLayout;
	private static JLayeredPane paneTop;
	private static JLabel txtPWInput;
	private static JLabel txtPWInput2;

	public static void main(String[] args) {
		// set Frame
		frame = new JFrame("hello");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("logo.png"));
		frame.setBounds(0, 0, 800, 600);
		frame.setLocation(0, 0);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// set PaneContainer
		paneContrainer = new JLayeredPane();
		frame.getContentPane().add(paneContrainer);
		frame.getContentPane().setVisible(true);

		// set SpringLayout
		springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		frame.getContentPane().setForeground(Color.GRAY);
		frame.getContentPane().setBackground(Color.BLACK);
		springLayout.putConstraint(SpringLayout.NORTH, paneContrainer, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, paneContrainer, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, paneContrainer, 550, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, paneContrainer, -10, SpringLayout.EAST, frame.getContentPane());

		// set Pane
		paneTop = new JLayeredPane();
		paneTop.setBorder(new BevelBorder(BevelBorder.RAISED, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN));
		paneTop.setBounds(10, 10, 150, 50);
		paneTop.setVisible(true);
		paneContrainer.add(paneTop);

		// set Label
		txtPWInput = new JLabel("65535");
		txtPWInput.setForeground(Color.GREEN);
		txtPWInput.setBounds(5, 5, 140, 40);
		txtPWInput.setFont(new Font("Arial", Font.BOLD, 32));
		paneTop.add(txtPWInput);
		
		txtPWInput2 = new JLabel("x");
		txtPWInput2.setForeground(Color.WHITE);
		txtPWInput2.setBounds(1, 1, 10, 10);
		txtPWInput2.setFont(new Font("Arial", Font.BOLD, 10));
		paneTop.add(txtPWInput2);
		
		
	}
}
