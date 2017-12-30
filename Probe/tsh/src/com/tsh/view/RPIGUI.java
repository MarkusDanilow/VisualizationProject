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

	private JLayeredPane m00;
	private JLayeredPane m01;
	private JLayeredPane m02;
	private JLayeredPane m03;
	private JLayeredPane m10;
	private JLayeredPane m11;
	private JLayeredPane m12;
	private JLayeredPane m13;
	private JLayeredPane m20;
	private JLayeredPane m21;
	private JLayeredPane m22;
	private JLayeredPane m23;
	private JLayeredPane m30;
	private JLayeredPane m31;
	private JLayeredPane m32;
	private JLayeredPane m33;
	private JLayeredPane m40;
	private JLayeredPane m41;
	private JLayeredPane m42;
	private JLayeredPane m43;
	private JLayeredPane m50;
	private JLayeredPane m51;
	private JLayeredPane m52;
	private JLayeredPane m53;
	private JLayeredPane m60;
	private JLayeredPane m61;
	private JLayeredPane m62;
	private JLayeredPane m63;
	private JLayeredPane m70;
	private JLayeredPane m71;
	private JLayeredPane m72;

	private JLabel l00;
	private JLabel l01;
	private JLabel l02;
	private JLabel l03;
	private JLabel l10;
	private JLabel l11;
	private JLabel l12;
	private JLabel l13;
	private JLabel l20;
	private JLabel l21;
	private JLabel l22;
	private JLabel l23;
	private JLabel l30;
	private JLabel l31;
	private JLabel l32;
	private JLabel l33;
	private JLabel l40;
	private JLabel l41;
	private JLabel l42;
	private JLabel l43;
	private JLabel l50;
	private JLabel l51;
	private JLabel l52;
	private JLabel l53;
	private JLabel l60;
	private JLabel l61;
	private JLabel l62;
	private JLabel l63;
	private JLabel l70;
	private JLabel l71;
	private JLabel l72;

	public RPIGUI(String frameTitle) {
		this.setFrame(frameTitle);
		this.setContainer();
		this.setSpringLayout();
		this.setMatrix();
		this.setContent();
		this.setEntityLabels();
	}

	private void setFrame(String frameTitle) {
		frame = new JFrame(frameTitle);
		frame.setForeground(Color.WHITE);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("logo.png"));
		frame.setBounds(0, 0, 1000, 700);
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
		m00 = new JLayeredPane();
		m01 = new JLayeredPane();
		m02 = new JLayeredPane();
		m03 = new JLayeredPane();
		m10 = new JLayeredPane();
		m11 = new JLayeredPane();
		m12 = new JLayeredPane();
		m13 = new JLayeredPane();
		m20 = new JLayeredPane();
		m21 = new JLayeredPane();
		m22 = new JLayeredPane();
		m23 = new JLayeredPane();
		m30 = new JLayeredPane();
		m31 = new JLayeredPane();
		m32 = new JLayeredPane();
		m33 = new JLayeredPane();
		m40 = new JLayeredPane();
		m41 = new JLayeredPane();
		m42 = new JLayeredPane();
		m43 = new JLayeredPane();
		m50 = new JLayeredPane();
		m51 = new JLayeredPane();
		m52 = new JLayeredPane();
		m53 = new JLayeredPane();
		m60 = new JLayeredPane();
		m61 = new JLayeredPane();
		m62 = new JLayeredPane();
		m63 = new JLayeredPane();
		m70 = new JLayeredPane();
		m71 = new JLayeredPane();
		m72 = new JLayeredPane();

		m00.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m01.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m02.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m03.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m10.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m11.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m12.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m13.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m20.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m21.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m22.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m23.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m30.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m31.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m32.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m33.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m40.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m41.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m42.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m43.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m50.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m51.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m52.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m53.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m60.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m61.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m62.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m63.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m70.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m71.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		m72.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		m00.setBounds(20, 10, 130, 40);
		m01.setBounds(150, 10, 200, 40);
		m02.setBounds(370, 10, 200, 40);
		m03.setBounds(590, 10, 200, 40);
		m10.setBounds(20, 80, 130, 40);
		m11.setBounds(150, 80, 200, 40);
		m12.setBounds(370, 80, 200, 40);
		m13.setBounds(590, 80, 200, 40);
		m20.setBounds(20, 150, 130, 40);
		m21.setBounds(150, 150, 200, 40);
		m22.setBounds(370, 150, 200, 40);
		m23.setBounds(590, 150, 200, 40);
		m30.setBounds(20, 220, 130, 40);
		m31.setBounds(150, 220, 200, 40);
		m32.setBounds(370, 220, 200, 40);
		m33.setBounds(590, 220, 200, 40);
		m40.setBounds(20, 290, 130, 40);
		m41.setBounds(150, 290, 200, 40);
		m42.setBounds(370, 290, 200, 40);
		m43.setBounds(590, 290, 200, 40);
		m50.setBounds(20, 360, 130, 40);
		m51.setBounds(150, 360, 200, 40);
		m52.setBounds(370, 360, 200, 40);
		m53.setBounds(590, 360, 200, 40);
		m60.setBounds(20, 430, 130, 40);
		m61.setBounds(150, 430, 200, 40);
		m62.setBounds(370, 430, 200, 40);
		m63.setBounds(590, 430, 200, 40);
		m70.setBounds(20, 480, 130, 40);
		m71.setBounds(150, 480, 200, 40);
		m72.setBounds(370, 480, 420, 40);

		m00.setVisible(true);
		m01.setVisible(true);
		m02.setVisible(true);
		m03.setVisible(true);
		m10.setVisible(true);
		m11.setVisible(true);
		m12.setVisible(true);
		m13.setVisible(true);
		m20.setVisible(true);
		m21.setVisible(true);
		m22.setVisible(true);
		m23.setVisible(true);
		m30.setVisible(true);
		m31.setVisible(true);
		m32.setVisible(true);
		m33.setVisible(true);
		m40.setVisible(true);
		m41.setVisible(true);
		m42.setVisible(true);
		m43.setVisible(true);
		m50.setVisible(true);
		m51.setVisible(true);
		m52.setVisible(true);
		m53.setVisible(true);
		m60.setVisible(true);
		m61.setVisible(true);
		m62.setVisible(true);
		m63.setVisible(true);
		m70.setVisible(true);
		m71.setVisible(true);
		m72.setVisible(true);

		this.paneContrainer.add(m00);
		this.paneContrainer.add(m01);
		this.paneContrainer.add(m02);
		this.paneContrainer.add(m03);
		this.paneContrainer.add(m10);
		this.paneContrainer.add(m11);
		this.paneContrainer.add(m12);
		this.paneContrainer.add(m13);
		this.paneContrainer.add(m20);
		this.paneContrainer.add(m21);
		this.paneContrainer.add(m22);
		this.paneContrainer.add(m23);
		this.paneContrainer.add(m30);
		this.paneContrainer.add(m31);
		this.paneContrainer.add(m32);
		this.paneContrainer.add(m33);
		this.paneContrainer.add(m40);
		this.paneContrainer.add(m41);
		this.paneContrainer.add(m42);
		this.paneContrainer.add(m43);
		this.paneContrainer.add(m50);
		this.paneContrainer.add(m51);
		this.paneContrainer.add(m52);
		this.paneContrainer.add(m53);
		this.paneContrainer.add(m60);
		this.paneContrainer.add(m61);
		this.paneContrainer.add(m62);
		this.paneContrainer.add(m63);
		this.paneContrainer.add(m70);
		this.paneContrainer.add(m71);
		this.paneContrainer.add(m72);

	}

	private void setContent() {
		l00 = new JLabel();
		l01 = new JLabel();
		l02 = new JLabel();
		l03 = new JLabel();
		l10 = new JLabel();
		l11 = new JLabel();
		l12 = new JLabel();
		l13 = new JLabel();
		l20 = new JLabel();
		l21 = new JLabel();
		l22 = new JLabel();
		l23 = new JLabel();
		l30 = new JLabel();
		l31 = new JLabel();
		l32 = new JLabel();
		l33 = new JLabel();
		l40 = new JLabel();
		l41 = new JLabel();
		l42 = new JLabel();
		l43 = new JLabel();
		l50 = new JLabel();
		l51 = new JLabel();
		l52 = new JLabel();
		l53 = new JLabel();
		l60 = new JLabel();
		l61 = new JLabel();
		l62 = new JLabel();
		l63 = new JLabel();
		l70 = new JLabel();
		l71 = new JLabel();
		l72 = new JLabel();

		/*
		 * l00.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null,
		 * null)); l01.setBorder(new BevelBorder(BevelBorder.RAISED, Color.GRAY,
		 * Color.WHITE, Color.DARK_GRAY, Color.black)); l02.setBorder(new
		 * BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		 * l03.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null,
		 * null)); l10.setBorder(new BevelBorder(BevelBorder.LOWERED, null,
		 * null, null, null)); l11.setBorder(new
		 * BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		 * l12.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null,
		 * null)); l13.setBorder(new BevelBorder(BevelBorder.LOWERED, null,
		 * null, null, null)); l20.setBorder(new
		 * BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		 * l21.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null,
		 * null)); l22.setBorder(new BevelBorder(BevelBorder.LOWERED, null,
		 * null, null, null)); l23.setBorder(new
		 * BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		 * l30.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null,
		 * null)); l31.setBorder(new BevelBorder(BevelBorder.LOWERED, null,
		 * null, null, null)); l32.setBorder(new
		 * BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		 * l33.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null,
		 * null)); l40.setBorder(new BevelBorder(BevelBorder.LOWERED, null,
		 * null, null, null)); l41.setBorder(new
		 * BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		 * l42.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null,
		 * null)); l43.setBorder(new BevelBorder(BevelBorder.LOWERED, null,
		 * null, null, null)); l50.setBorder(new
		 * BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		 * l51.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null,
		 * null)); l52.setBorder(new BevelBorder(BevelBorder.LOWERED, null,
		 * null, null, null)); l53.setBorder(new
		 * BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		 * l60.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null,
		 * null)); l61.setBorder(new BevelBorder(BevelBorder.LOWERED, null,
		 * null, null, null)); l62.setBorder(new
		 * BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		 * l63.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null,
		 * null)); l70.setBorder(new BevelBorder(BevelBorder.LOWERED, null,
		 * null, null, null)); l71.setBorder(new
		 * BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		 * l72.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null,
		 * null)); l73.setBorder(new BevelBorder(BevelBorder.LOWERED, null,
		 * null, null, null)); l80.setBorder(new
		 * BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		 * l81.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null,
		 * null)); l82.setBorder(new BevelBorder(BevelBorder.LOWERED, null,
		 * null, null, null)); //l83.setBorder(new
		 * BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		 * l90.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null,
		 * null)); l91.setBorder(new BevelBorder(BevelBorder.LOWERED, null,
		 * null, null, null)); l92.setBorder(new
		 * BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		 * //l93.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
		 * null, null));
		 */
		l00.setBounds(5, 5, 120, 30);
		l01.setBounds(5, 5, 190, 30);
		l02.setBounds(5, 5, 190, 30);
		l03.setBounds(5, 5, 190, 30);
		l10.setBounds(5, 5, 120, 30);
		l11.setBounds(5, 5, 190, 30);
		l12.setBounds(5, 5, 190, 30);
		l13.setBounds(5, 5, 190, 30);
		l20.setBounds(5, 5, 120, 30);
		l21.setBounds(5, 5, 190, 30);
		l22.setBounds(5, 5, 190, 30);
		l23.setBounds(5, 5, 190, 30);
		l30.setBounds(5, 5, 120, 30);
		l31.setBounds(5, 5, 190, 30);
		l32.setBounds(5, 5, 190, 30);
		l33.setBounds(5, 5, 190, 30);
		l40.setBounds(5, 5, 120, 30);
		l41.setBounds(5, 5, 190, 30);
		l42.setBounds(5, 5, 190, 30);
		l43.setBounds(5, 5, 190, 30);
		l50.setBounds(5, 5, 120, 30);
		l51.setBounds(5, 5, 190, 30);
		l52.setBounds(5, 5, 190, 30);
		l53.setBounds(5, 5, 190, 30);
		l60.setBounds(5, 5, 120, 30);
		l61.setBounds(5, 5, 190, 30);
		l62.setBounds(5, 5, 190, 30);
		l63.setBounds(5, 5, 190, 30);
		l70.setBounds(5, 5, 120, 30);
		l71.setBounds(5, 5, 190, 30);
		l72.setBounds(5, 5, 410, 30);
		l72.setFont(new Font("Arial",10,10));

		l00.setVisible(true);
		l01.setVisible(true);
		l02.setVisible(true);
		l03.setVisible(true);
		l10.setVisible(true);
		l11.setVisible(true);
		l12.setVisible(true);
		l13.setVisible(true);
		l20.setVisible(true);
		l21.setVisible(true);
		l22.setVisible(true);
		l23.setVisible(true);
		l30.setVisible(true);
		l31.setVisible(true);
		l32.setVisible(true);
		l33.setVisible(true);
		l40.setVisible(true);
		l41.setVisible(true);
		l42.setVisible(true);
		l43.setVisible(true);
		l50.setVisible(true);
		l51.setVisible(true);
		l52.setVisible(true);
		l53.setVisible(true);
		l60.setVisible(true);
		l61.setVisible(true);
		l62.setVisible(true);
		l63.setVisible(true);
		l70.setVisible(true);
		l71.setVisible(true);
		l72.setVisible(true);

		this.m00.add(l00);
		this.m01.add(l01);
		this.m02.add(l02);
		this.m03.add(l03);
		this.m10.add(l10);
		this.m11.add(l11);
		this.m12.add(l12);
		this.m13.add(l13);
		this.m20.add(l20);
		this.m21.add(l21);
		this.m22.add(l22);
		this.m23.add(l23);
		this.m30.add(l30);
		this.m31.add(l31);
		this.m32.add(l32);
		this.m33.add(l33);
		this.m40.add(l40);
		this.m41.add(l41);
		this.m42.add(l42);
		this.m43.add(l43);
		this.m50.add(l50);
		this.m51.add(l51);
		this.m52.add(l52);
		this.m53.add(l53);
		this.m60.add(l60);
		this.m61.add(l61);
		this.m62.add(l62);
		this.m63.add(l63);
		this.m70.add(l70);
		this.m71.add(l71);
		this.m72.add(l72);

	}

	public void setEntityLabels(){
		l00.setText("Gyro");
		l10.setText("US");
		l20.setText("GPS");
		l30.setText("Gyro");
		l40.setText("Gyro");
		l50.setText("Gyro");
		l60.setText("Gyro");
		l70.setText("API");
	}
	public void setDebugMsg(MsgTransformer msg) {
		this.l01.setText("x,y,z: " + msg.toString());
	}

	public void setDebugMsg(String msg) {
		this.l01.setText("x,y,z: " + msg);
	}

	public void setValueDisplay(MsgTransformer msg) {
		this.l11.setText(String.valueOf(msg.getValue()));
	}

	public void setApi(String msg) {
		this.l72.setText("<html>"+msg.substring(0)+"<br>"+msg.substring(50)+"<b/html>");
	}

}
