package vis.frame;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.lwjgl.LWJGLException;

import com.base.engine.Settings;
import com.toedter.calendar.JDateChooser;

import vis.events.LoadDataEvent;
import vis.events.LoadFromAPIEvent;
import vis.events.OpenHelpEvent;
import vis.events.QuitApplicationEvent;
import vis.events.TimelineChangeEvent;
import vis.events.TimelineNextEvent;
import vis.events.TimelinePlayEvent;
import vis.events.TimelinePreviousEvent;
import vis.interfaces.AppInterface;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	public static final int CANVAS_COUNT = 1;
	public static final int leftSidebarWidth = 200, rightSidebarWidth = 200, footerHeight = 100;

	public final AppInterface app;

	private Canvas[] canvases = new Canvas[CANVAS_COUNT];

	private JButton back, forth, play;
	private JSlider timeline;

	private AtomicBoolean timelineRunning = new AtomicBoolean(false);

	public static Dimension getScreenSize() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}

	public MainWindow(AppInterface app, String title, int width, int height) throws LWJGLException {

		// set reference to the app
		this.app = app;

		// shut down entire application if the main window is being closed
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// maximize window
		this.setSize(getScreenSize());

		// disable resizing
		this.setResizable(false);

		// title and position
		this.setTitle(title);
		this.setLocationRelativeTo(null);

		// add all components to the window
		this.init();

		// display the window
		this.setVisible(true);
	}

	/**
	 * @throws LWJGLException
	 */
	/**
	 * @throws LWJGLException
	 */
	/**
	 * @throws LWJGLException
	 */
	/**
	 * @throws LWJGLException
	 */
	/**
	 * @throws LWJGLException
	 */
	/**
	 * @throws LWJGLException
	 */
	private void init() throws LWJGLException {

		this.setLayout(new BorderLayout());

		Font captionFont = new Font("Arial", Font.BOLD + Font.PLAIN, 14);

		/* ------------ create the sidebar to the left ------------ */
		JPanel leftSidebarPanel = new JPanel();
		leftSidebarPanel.setPreferredSize(new Dimension(leftSidebarWidth, this.getHeight()));

		leftSidebarPanel.setBackground(new Color(3, 16, 69));
		leftSidebarPanel.setLayout(null);

		// TODO: Generisches String-Array view[] ersetzen
		// String view[] = { "3D", "2D", "1D", "ScatterPlot", "Spline",
		// "Bar-Chart", "Aimed Target" };

		// Visualisierungstechniken
		JLabel lblTechnik = new JLabel("Visualisierungstechnik");
		lblTechnik.setForeground(Color.WHITE);
		lblTechnik.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblTechnik.setBounds(35, 11, 130, 14);
		leftSidebarPanel.add(lblTechnik);

		JComboBox cmbTechnik1 = new JComboBox();
		cmbTechnik1.setFont(new Font("SansSerif", Font.PLAIN, 11));
		cmbTechnik1.setModel(new DefaultComboBoxModel(
				new String[] { "3D", "2D", "1D", "Scatter plot", "Spline", "Bar-Chart", "Aimed Target" }));
		cmbTechnik1.setBounds(5, 36, 90, 21);
		cmbTechnik1.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
		leftSidebarPanel.add(cmbTechnik1);

		JComboBox cmbTechnik2 = new JComboBox();
		cmbTechnik2.setFont(new Font("SansSerif", Font.PLAIN, 11));
		cmbTechnik2.setModel(new DefaultComboBoxModel(
				new String[] { "Bar-Chart", "3D", "2D", "1D", "Scatter plot", "Spline", "Aimed Target" }));
		cmbTechnik2.setBounds(105, 36, 90, 21);
		cmbTechnik2.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
		leftSidebarPanel.add(cmbTechnik2);

		JComboBox cmbTechnik3 = new JComboBox();
		cmbTechnik3.setFont(new Font("SansSerif", Font.PLAIN, 11));
		cmbTechnik3.setModel(new DefaultComboBoxModel(
				new String[] { "Spline", "3D", "2D", "1D", "Scatter plot", "Bar-Chart", "Aimed Target" }));
		cmbTechnik3.setBounds(5, 65, 90, 21);
		cmbTechnik3.setBorder(BorderFactory.createLineBorder(new Color(128, 0, 0), 2));
		leftSidebarPanel.add(cmbTechnik3);

		JComboBox cmbTechnik4 = new JComboBox();
		cmbTechnik4.setFont(new Font("SansSerif", Font.PLAIN, 11));
		cmbTechnik4.setModel(new DefaultComboBoxModel(
				new String[] { "Scatter plot", "Aimed Target", "3D", "2D", "1D", "Spline", "Bar-Chart" }));
		cmbTechnik4.setBounds(105, 65, 90, 21);
		cmbTechnik4.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
		leftSidebarPanel.add(cmbTechnik4);

		// TODO: Als eigene Panels definieren und dynamisch verschieben
		// Parameterfilter
		JLabel lblParameterfilter = new JLabel("Parameter-Filter (global)");
		lblParameterfilter.setForeground(Color.WHITE);
		lblParameterfilter.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblParameterfilter.setBounds(31, 127, 138, 14);
		leftSidebarPanel.add(lblParameterfilter);

		JCheckBox chckbxXposition = new JCheckBox("x-Position");
		chckbxXposition.setFont(new Font("SansSerif", Font.PLAIN, 11));
		chckbxXposition.setBounds(5, 148, 90, 21);
		leftSidebarPanel.add(chckbxXposition);

		ColorChooserButton xChooser = new ColorChooserButton(Color.red);
		xChooser.setBounds(105, 148, 90, 21);
		leftSidebarPanel.add(xChooser);

		JCheckBox chckbxYposition = new JCheckBox("y-Position");
		chckbxYposition.setFont(new Font("SansSerif", Font.PLAIN, 11));
		chckbxYposition.setBounds(5, 174, 90, 21);
		leftSidebarPanel.add(chckbxYposition);

		ColorChooserButton yChooser = new ColorChooserButton(Color.blue);
		yChooser.setBounds(105, 174, 90, 21);
		leftSidebarPanel.add(yChooser);

		JCheckBox chckbxZposition = new JCheckBox("z-Position");
		chckbxZposition.setFont(new Font("SansSerif", Font.PLAIN, 11));
		chckbxZposition.setBounds(5, 200, 90, 21);
		leftSidebarPanel.add(chckbxZposition);

		ColorChooserButton zChooser = new ColorChooserButton(Color.green);
		zChooser.setBounds(105, 200, 90, 21);
		leftSidebarPanel.add(zChooser);

		JCheckBox chckbxTargetObjekt = new JCheckBox("Target Objekt");
		chckbxTargetObjekt.setFont(new Font("SansSerif", Font.PLAIN, 11));
		chckbxTargetObjekt.setBounds(5, 226, 90, 20);
		leftSidebarPanel.add(chckbxTargetObjekt);

		ColorChooserButton tChooser = new ColorChooserButton(Color.yellow);
		tChooser.setBounds(105, 226, 90, 21);
		leftSidebarPanel.add(tChooser);

		// TODO: Als eigene Panels definieren und dynamisch verschieben
		// Datumsauswahl
		JLabel lblDatumsauswahl = new JLabel("Datumsauswahl");
		lblDatumsauswahl.setForeground(Color.WHITE);
		lblDatumsauswahl.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblDatumsauswahl.setBounds(54, 288, 92, 14);
		leftSidebarPanel.add(lblDatumsauswahl);

		JLabel lblVon = new JLabel("Von:");
		lblVon.setForeground(Color.WHITE);
		lblVon.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblVon.setBounds(5, 313, 25, 14);
		leftSidebarPanel.add(lblVon);

		JLabel lblBis = new JLabel("Bis:");
		lblBis.setForeground(Color.WHITE);
		lblBis.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblBis.setBounds(5, 343, 25, 14);
		leftSidebarPanel.add(lblBis);

		JDateChooser dateChooserFrom = new JDateChooser();
		dateChooserFrom.setBounds(105, 313, 90, 21);
		leftSidebarPanel.add(dateChooserFrom);

		JDateChooser dateChooserTo = new JDateChooser();
		dateChooserTo.setBounds(105, 343, 90, 21);
		leftSidebarPanel.add(dateChooserTo);

		this.add(leftSidebarPanel, BorderLayout.WEST);

		/*
		 * ------------ create the main panel in the center and all of the
		 * canvases that will be placed inside of it ------------
		 */
		JPanel canvasPanel = new JPanel(new GridLayout((int) Math.sqrt(CANVAS_COUNT), (int) Math.sqrt(CANVAS_COUNT)));

		for (byte i = 0; i < CANVAS_COUNT; i++) {
			JPanel cPanel = new JPanel();
			cPanel.setLayout(new GridLayout(1, 1));

			cPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

			// TODO: Border umsetzen nach Farbschema
			// switch (i) {
			// case 0:
			// cPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
			// break;
			//
			// case 1:
			// cPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE,
			// 2));
			// break;
			//
			// case 2:
			// cPanel.setBorder(BorderFactory.createLineBorder(new
			// Color(128,0,0), 2));
			// break;
			//
			// case 3:
			// cPanel.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
			// break;
			//
			// default:
			// break;
			// }
			//
			// JPanel innerCanvas = new JPanel();
			// innerCanvas.setBorder(BorderFactory.createLineBorder(new
			// Color(128,0,0), 2));

			Canvas c = new Canvas();

			canvasPanel.add(cPanel);
			// innerCanvas.add(cPanel);
			cPanel.add(c);

			c.setIgnoreRepaint(true);
			c.setBackground(Settings.getClearColor());
			canvases[i] = c;
		}

		this.add(canvasPanel, BorderLayout.CENTER);

		/* ------------ create the sidebar to the right ------------ */
		JPanel rightSidebarPanel = new JPanel();
		rightSidebarPanel.setLayout(new GridLayout(0, 1, 0, 0));
		rightSidebarPanel.setPreferredSize(new Dimension(rightSidebarWidth, this.getHeight()));
		rightSidebarPanel.setBackground(new Color(3, 16, 69));
		rightSidebarPanel.setLayout(null);
		
		JLabel common = new JLabel("Common");
		common.setForeground(Color.WHITE);
		common.setFont(new Font("SansSerif", Font.BOLD, 12));
		common.setBounds(35, 11, 130, 14);
		rightSidebarPanel.add(common);
		
		JCheckBox live = new JCheckBox("live-view");
		live.setFont(new Font("SansSerif", Font.PLAIN, 11));
		live.setBounds(5, 36, 90, 21);
		rightSidebarPanel.add(live);
		
		JCheckBox freeze = new JCheckBox("freeze");
		freeze.setFont(new Font("SansSerif", Font.PLAIN, 11));
		freeze.setBounds(5, 65, 90, 21);
		rightSidebarPanel.add(freeze);
		
		//TODO: 3D muss durch eine Variable ersetzt werden
		JLabel paneA = new JLabel("Pane A: 3D");
		paneA.setForeground(Color.WHITE);
		paneA.setFont(new Font("SansSerif", Font.BOLD, 12));
		paneA.setBounds(31, 127, 138, 14);
		rightSidebarPanel.add(paneA);
		
		JCheckBox singledot = new JCheckBox("single dot / vector");
		singledot.setFont(new Font("SansSerif", Font.PLAIN, 11));
		singledot.setBounds(5, 152, 90, 21);
		rightSidebarPanel.add(singledot);
		
		JCheckBox plotall = new JCheckBox("plot all");
		plotall.setFont(new Font("SansSerif", Font.PLAIN, 11));
		plotall.setBounds(5, 181, 90, 21);
		rightSidebarPanel.add(plotall);
		
		JTextField wert1 = new JTextField();
		JTextField wert2 = new JTextField();
		
		JCheckBox plotbet = new JCheckBox("plot between" + wert1 + "to" + wert2);
		plotbet.setFont(new Font("SansSerif", Font.PLAIN, 8));
		plotbet.setBounds(5, 210, 90, 21);
		rightSidebarPanel.add(plotbet);
		
		JLabel rotate = new JLabel("rotate");
		rotate.setForeground(Color.WHITE);
		rotate.setFont(new Font("SansSerif", Font.BOLD, 10));
		rotate.setBounds(31, 239, 138, 14);
		rightSidebarPanel.add(rotate);
		
		JCheckBox gradlinks = new JCheckBox("90° links");
		gradlinks.setFont(new Font("SansSerif", Font.PLAIN, 11));
		gradlinks.setBounds(5, 264, 90, 21);
		rightSidebarPanel.add(gradlinks);
		
		JCheckBox gradrechts = new JCheckBox("90° rechts");
		gradrechts.setFont(new Font("SansSerif", Font.PLAIN, 11));
		gradrechts.setBounds(90, 264, 90, 21);
		rightSidebarPanel.add(gradrechts);
		
		//TODO: Bar-Char durch Variable ersetzen
		JLabel paneB = new JLabel("Pane B: Bar-Char");
		paneB.setForeground(Color.WHITE);
		paneB.setFont(new Font("SansSerif", Font.BOLD, 12));
		paneB.setBounds(31, 326, 138, 14);
		rightSidebarPanel.add(paneB);
		
		String[] show = { "show x", "show y", "show z"};
		
		JComboBox cmbshow1 = new JComboBox();
		cmbshow1.setFont(new Font("SansSerif", Font.PLAIN, 11));
		cmbshow1.setModel(new DefaultComboBoxModel(show));
		cmbshow1.setBounds(5, 355, 90, 21);
		//cmbshow1.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
		rightSidebarPanel.add(cmbshow1);
		
		JTextField posbar = new JTextField();
		
		JLabel calcbar = new JLabel("calculate mean for last " + posbar + " positions");
		calcbar.setForeground(Color.WHITE);
		calcbar.setFont(new Font("SansSerif", Font.BOLD, 8));
		calcbar.setBounds(5, 384, 138, 21);
		rightSidebarPanel.add(calcbar);
		
		JLabel paneC = new JLabel("Pane C: Spline");
		paneC.setForeground(Color.WHITE);
		paneC.setFont(new Font("SansSerif", Font.BOLD, 12));
		paneC.setBounds(31, 446, 138, 14);
		rightSidebarPanel.add(paneC);
		
		JComboBox cmbshow2 = new JComboBox();
		cmbshow2.setFont(new Font("SansSerif", Font.PLAIN, 11));
		cmbshow2.setModel(new DefaultComboBoxModel(show));
		cmbshow2.setBounds(5, 471, 90, 21);
		//cmbshow2.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
		rightSidebarPanel.add(cmbshow2);
		
		JTextField posline = new JTextField();
		
		JLabel calcline = new JLabel("calculate mean for last " + posline + " positions");
		calcline.setForeground(Color.WHITE);
		calcline.setFont(new Font("SansSerif", Font.BOLD, 8));
		calcline.setBounds(5, 500, 138, 21);
		rightSidebarPanel.add(calcline);
		
		JLabel paneD = new JLabel("Pane D: Scatterplot");
		paneD.setForeground(Color.WHITE);
		paneD.setFont(new Font("SansSerif", Font.BOLD, 12));
		paneD.setBounds(31, 562, 138, 14);
		rightSidebarPanel.add(paneD);
		
		JTextField deviation = new JTextField();
		deviation.setEditable(false);
		
		JLabel calcscat = new JLabel("calculate standard deviation\n" + deviation);
		calcscat.setForeground(Color.WHITE);
		calcscat.setFont(new Font("SansSerif", Font.BOLD, 8));
		calcscat.setBounds(5, 591, 138, 21);
		rightSidebarPanel.add(calcscat);
		
		
	//	rightSidebarPanel.add(new JLabel("Common"));

	//	JTextArea common = new JTextArea("- live-view \n - freeze");
	//	common.setEditable(false);
	//		rightSidebarPanel.add(common);

		// TODO: Pane A durch Variable ersetzen
	//	rightSidebarPanel.add(new JLabel("Pane A"));

	//		JTextArea paneA = new JTextArea("- sigle dot / vector \n - plot all");
	//	paneA.setEditable(false);
	//		rightSidebarPanel.add(paneA);

		// TODO: Pane B durch Variable ersetzen
	//		rightSidebarPanel.add(new JLabel("Pane B"));

	//	JTextArea paneB = new JTextArea("- plot between *Button*");
	//	paneB.setEditable(false);
	//	rightSidebarPanel.add(paneB);

		// TODO: Pane C durch Variable ersetzen
	//	rightSidebarPanel.add(new JLabel("Pane C"));

	//	JTextArea paneC = new JTextArea("rotate \n - 90� left \n - 90� right");
	//	paneC.setEditable(false);
	//		rightSidebarPanel.add(paneC);

		// TODO: Pane D durch Variable ersetzen
	//		rightSidebarPanel.add(new JLabel("Pane D"));

	//		JTextArea paneD = new JTextArea("select all");
	//	paneD.setEditable(false);
	//	rightSidebarPanel.add(paneD);

		this.add(rightSidebarPanel, BorderLayout.EAST);

		/* ------------ create the footer ------------ */
		JPanel footerPanel = new JPanel(new GridLayout(2, 1));
		footerPanel.setPreferredSize(new Dimension(this.getWidth(), footerHeight));

		JPanel timelinePanel = new JPanel(new BorderLayout());
		JPanel timelineButtonPanel = new JPanel(new GridLayout(1, 3));
		this.back = new JButton("<<");
		this.forth = new JButton(">>");
		this.play = new JButton("PLAY");

		timelineButtonPanel.add(this.back);
		timelineButtonPanel.add(this.play);
		timelineButtonPanel.add(this.forth);

		this.back.addActionListener(new TimelinePreviousEvent(this));
		this.forth.addActionListener(new TimelineNextEvent(this));
		this.play.addActionListener(new TimelinePlayEvent(this));

		timelinePanel.add(timelineButtonPanel, BorderLayout.WEST);

		this.timeline = new JSlider(JSlider.HORIZONTAL);
		this.timeline.addChangeListener(new TimelineChangeEvent(this));
		timelinePanel.add(this.timeline, BorderLayout.CENTER);

		this.toggleTimeline(false, 0, 0, 0);

		footerPanel.add(timelinePanel);

		this.add(footerPanel, BorderLayout.SOUTH);

		/*
		 * ------------ create the menu bar at the top of the window
		 * ------------
		 */
		JMenuBar menuBar = new JMenuBar();

		// file menu
		JMenu fileMenu = new JMenu("Datei");
		JMenuItem loadFile = new JMenuItem("�ffnen...");
		loadFile.addActionListener(new LoadDataEvent(this));
		fileMenu.add(loadFile);
		JMenuItem loadFromAPI = new JMenuItem("Alles von API laden");
		loadFromAPI.addActionListener(new LoadFromAPIEvent(this));
		fileMenu.add(loadFromAPI);
		JMenuItem closeApp = new JMenuItem("Beenden");
		closeApp.addActionListener(new QuitApplicationEvent(this));
		fileMenu.addSeparator();
		fileMenu.add(closeApp);
		menuBar.add(fileMenu);

		// help menu
		JMenu helpMenu = new JMenu("Hilfe");
		JMenuItem help = new JMenuItem("Anleitung...");
		help.addActionListener(new OpenHelpEvent(this));
		helpMenu.add(help);
		menuBar.add(helpMenu);

		this.setJMenuBar(menuBar);

	}

	public Canvas getCanvasById(int id) {
		return canvases[id];
	}

	public void toggleTimeline(boolean active, int min, int max, int value) {
		this.back.setEnabled(active);
		this.play.setEnabled(active);
		this.forth.setEnabled(active);
		this.timeline.setEnabled(active);
		if (!active) {
			this.timeline.setMinimum(0);
			this.timeline.setMaximum(0);
			this.timeline.setValue(0);
		} else {
			this.timeline.setMinimum(min);
			this.timeline.setMaximum(max);
			this.timeline.setValue(value);
		}
	}

	public void changeTimelineValue(int delta) {
		this.timeline.setValue(this.timeline.getValue() + delta);
	}

	public void startTimeline() {
		this.timelineRunning.set(true);
		this.play.setText("PAUSE");
		new Thread() {
			public void run() {
				while (timelineRunning.get()) {
					try {
						changeTimelineValue((int) app.getTimelineStep());
						timelineStopCheck();
						Thread.sleep(Settings.getAnimationStep());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
	}

	public void stopTimeline() {
		this.play.setText("PLAY");
		this.timelineRunning.set(false);
	}

	public void timelineStopCheck() {
		int value = this.timeline.getValue();
		if (value == this.timeline.getMaximum() || value == this.timeline.getMinimum()) {
			this.stopTimeline();
		}
	}

}
