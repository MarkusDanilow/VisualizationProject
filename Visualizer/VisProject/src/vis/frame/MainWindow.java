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

import org.lwjgl.LWJGLException;

import com.base.engine.Settings;
import com.toedter.calendar.JDateChooser;

import vis.events.LoadDataEvent;
import vis.events.OpenHelpEvent;
import vis.events.QuitApplicationEvent;
import vis.events.TimelineChangeEvent;
import vis.events.TimelineNextEvent;
import vis.events.TimelinePlayEvent;
import vis.events.TimelinePreviousEvent;
import vis.frame.styles.CustomStyleCreator;
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

		// apply custom style to the window
		// CustomStyleCreator.applyCustomStyle(this);

		// display the window
		this.setVisible(true);
	}

	private void init() throws LWJGLException {

		this.setLayout(new BorderLayout());

		/* ------------ create the sidebar to the left ------------ */
		JPanel leftSidebarPanel = new JPanel();
		leftSidebarPanel.setPreferredSize(new Dimension(leftSidebarWidth, this.getHeight()));
		
		leftSidebarPanel.setLayout(new GridLayout(0, 1));
		JLabel arrangement = new JLabel("Arrangement");
		leftSidebarPanel.add(arrangement);
		Font schriftart = new Font("Arial", Font.BOLD + Font.PLAIN, 14);
		//arrangement.setBounds(100, 100, 200, 100);
		arrangement.setFont(schriftart);
		
		String view[] = { "3D", "2D", "1D", "ScatterPlot", "Spline", "Bar-Chart", "Aimed Target" };

		JPanel comboPanel = new JPanel();
		comboPanel.setLayout(new GridLayout(2, 2, 10, 10));

		JComboBox<String> combo1 = new JComboBox<String>(view);
		combo1.setEditable(false);
		comboPanel.add(combo1);

		JComboBox<String> combo2 = new JComboBox<String>(view);
		combo2.setEditable(false);
		comboPanel.add(combo2);

		JComboBox<String> combo3 = new JComboBox<String>(view);
		combo3.setEditable(false);
		comboPanel.add(combo3);

		JComboBox<String> combo4 = new JComboBox<String>(view);
		combo4.setEditable(false);
		comboPanel.add(combo4);

		leftSidebarPanel.add(comboPanel);

		JLabel parameter = new JLabel("Parameter-Filter (global)");
		leftSidebarPanel.add(parameter);
		Font schriftart2 = new Font("Arial", Font.BOLD + Font.PLAIN, 14);
		parameter.setFont(schriftart2);

		JPanel parameterFilter = new JPanel();
		parameterFilter.setLayout(new GridLayout(4, 2, 10, 10));

		JCheckBox xBox = new JCheckBox("x-Position");
		parameterFilter.add(xBox);

		// Initialfarbe w�hlen
		ColorChooserButton xChooser = new ColorChooserButton(Color.red);
		parameterFilter.add(xChooser);

		JCheckBox yBox = new JCheckBox("y-Position");
		parameterFilter.add(yBox);

		ColorChooserButton yChooser = new ColorChooserButton(Color.blue);
		parameterFilter.add(yChooser);

		JCheckBox zBox = new JCheckBox("z-Position");
		parameterFilter.add(zBox);

		ColorChooserButton zChooser = new ColorChooserButton(Color.green);
		parameterFilter.add(zChooser);

		JCheckBox tBox = new JCheckBox("Targer-Object");
		parameterFilter.add(tBox);

		ColorChooserButton tChooser = new ColorChooserButton(Color.yellow);
		parameterFilter.add(tChooser);

		leftSidebarPanel.add(parameterFilter);

		JPanel calendarPanel = new JPanel();
		calendarPanel.setLayout(new GridLayout(4, 1, 5, 5));

		JLabel calendarFromLabel = new JLabel("Von:");
		calendarPanel.add(calendarFromLabel);
		Font schriftart3 = new Font("Arial", Font.BOLD + Font.PLAIN, 14);
		calendarFromLabel.setFont(schriftart3);

		JDateChooser dateChooserFrom = new JDateChooser();
		calendarPanel.add(dateChooserFrom);

		JLabel calendarToLabel = new JLabel("Bis:");
		calendarPanel.add(calendarToLabel);
		Font schriftart4 = new Font("Arial", Font.BOLD + Font.PLAIN, 14);
		calendarToLabel.setFont(schriftart4);

		JDateChooser dateChooserTo = new JDateChooser();
		calendarPanel.add(dateChooserTo);

		leftSidebarPanel.add(calendarPanel);

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
			Canvas c = new Canvas();

			canvasPanel.add(cPanel);
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
		rightSidebarPanel.add(new JLabel("Common"));

		JTextArea common = new JTextArea("- live-view \n - freeze");
		common.setEditable(false);
		rightSidebarPanel.add(common);

		// TODO: Pane A durch Variable ersetzen
		rightSidebarPanel.add(new JLabel("Pane A"));

		JTextArea paneA = new JTextArea("- sigle dot / vector \n - plot all");
		paneA.setEditable(false);
		rightSidebarPanel.add(paneA);

		// TODO: Pane B durch Variable ersetzen
		rightSidebarPanel.add(new JLabel("Pane B"));

		JTextArea paneB = new JTextArea("- plot between *Button*");
		paneB.setEditable(false);
		rightSidebarPanel.add(paneB);

		// TODO: Pane C durch Variable ersetzen
		rightSidebarPanel.add(new JLabel("Pane C"));

		JTextArea paneC = new JTextArea("rotate \n - 90� left \n - 90� right");
		paneC.setEditable(false);
		rightSidebarPanel.add(paneC);

		// TODO: Pane D durch Variable ersetzen
		rightSidebarPanel.add(new JLabel("Pane D"));

		JTextArea paneD = new JTextArea("select all");
		paneD.setEditable(false);
		rightSidebarPanel.add(paneD);

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
		JMenuItem closeApp = new JMenuItem("Beenden");
		closeApp.addActionListener(new QuitApplicationEvent(this));
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
