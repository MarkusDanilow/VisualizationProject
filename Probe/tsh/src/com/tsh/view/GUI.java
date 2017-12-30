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

public class GUI {

	private JFrame frame;
	private SpringLayout springLayout;
	private JLayeredPane paneContrainer;
	private JLayeredPane paneTop;
	private JLayeredPane paneLeftMiddle;
	private JLayeredPane paneFooterLeft;
	private JLayeredPane paneFooterMiddle;
	private JLayeredPane paneFooterRight;
	private JLayeredPane paneLeftDown;
	private JLayeredPane paneRightDown;
	private JLayeredPane paneRightUp;
	private JLabel disLabel;
	private JTextField txtStateHeader;
	private JLabel txtPWInput;
	private JLabel api;

	public GUI(String frameTitle) {
		this.setFrame(frameTitle);
		this.setContainer();
		this.setSpringLayout();
		this.setMatrix();
		this.setContent();
	}

	private void setFrame(String frameTitle) {
		frame = new JFrame(frameTitle);
		frame.setForeground(Color.WHITE);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("logo.png"));
		frame.setBounds(100, 100, 1000, 700);
		frame.setLocation(0, 0);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	private void setContainer() {
		paneContrainer = new JLayeredPane();
		this.frame.getContentPane().add(paneContrainer);
	}

	private void setSpringLayout() {
		springLayout = new SpringLayout();
		this.frame.getContentPane().setLayout(springLayout);
		springLayout.putConstraint(SpringLayout.NORTH, this.paneContrainer, 10, SpringLayout.NORTH, this.frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, this.paneContrainer, 10, SpringLayout.WEST, this.frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, this.paneContrainer, 550, SpringLayout.NORTH, this.frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, this.paneContrainer, -10, SpringLayout.EAST, this.frame.getContentPane());
	}

	private void setMatrix() {
		// *********************************LAYOUT***************************************************************
		paneTop = new JLayeredPane();
		paneTop.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		paneTop.setBounds(10, 11, 546, 26);
		paneTop.setVisible(true);
		this.paneContrainer.add(paneTop);
		// *********************************LAYOUT***************************************************************
		paneLeftMiddle = new JLayeredPane();
		paneLeftMiddle.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		paneLeftMiddle.setBounds(10, 51, 546, 308);
		paneLeftMiddle.setVisible(true);
		this.paneContrainer.add(paneLeftMiddle);
		// *********************************LAYOUT***************************************************************
		paneFooterLeft = new JLayeredPane();
		paneFooterLeft.setBounds(10, 509, 734, 20);
		paneFooterLeft.setVisible(true);
		this.paneContrainer.add(paneFooterLeft);
		// *********************************LAYOUT***************************************************************
		paneFooterMiddle = new JLayeredPane();
		paneFooterMiddle.setBounds(754, 509, 120, 20);
		paneFooterMiddle.setVisible(true);
		this.paneContrainer.add(paneFooterMiddle);
		// *********************************LAYOUT***************************************************************
		paneFooterRight = new JLayeredPane();
		paneFooterRight.setBounds(884, 509, 80, 20);
		paneFooterRight.setVisible(true);
		this.paneContrainer.add(paneFooterRight);
		// *********************************LAYOUT***************************************************************
		paneLeftDown = new JLayeredPane();
		paneLeftDown.setBounds(10, 371, 452, 127);
		paneLeftDown.setLayout(new BorderLayout(0, 0));
		paneLeftDown.setVisible(true);
		this.paneContrainer.add(paneLeftDown);
		// *********************************LAYOUT***************************************************************
		paneRightDown = new JLayeredPane();
		paneRightDown.setBounds(472, 371, 492, 127);
		paneRightDown.setVisible(true);
		this.paneContrainer.add(paneRightDown);
		// *********************************LAYOUT***************************************************************
		paneRightUp = new JLayeredPane();
		paneRightUp.setBounds(940, 11, 24, 26);
		paneRightUp.setVisible(true);
		this.paneContrainer.add(paneRightUp);
	}

	private void setContent() {
		txtPWInput = new JLabel("DEBUG-MSG");
		txtPWInput.setBounds(5, 5, 500, 20);
		this.paneTop.add(txtPWInput);

		disLabel = new JLabel();
		disLabel.setText("Initilize tsh...");
		disLabel.setBounds(5, 5, 200, 20);
		this.paneLeftMiddle.add(disLabel);

		txtStateHeader = new JTextField();
		txtStateHeader.setEditable(false);
		txtStateHeader.setText("State: fine");
		txtStateHeader.setBounds(0, 0, 100, 20);
		txtStateHeader.setColumns(10);
		this.paneFooterRight.add(txtStateHeader);

		api = new JLabel("api-msg");
		api.setBounds(5, 5, 700, 20);
		api.setFont(new Font("Arial", Font.BOLD, 10));
		this.paneFooterLeft.add(api);

	}

	public void setDebugMsg(MsgTransformer msg) {
		this.txtPWInput.setText("x,y,z: " + msg.toString());
	}

	public void setDebugMsg(String msg) {
		this.txtPWInput.setText("x,y,z: " + msg);
	}

	public void setValueDisplay(MsgTransformer msg) {
		this.disLabel.setText(String.valueOf(msg.getValue()));
	}

	public void setApi(String msg) {
		this.api.setText(msg);
	}

}
