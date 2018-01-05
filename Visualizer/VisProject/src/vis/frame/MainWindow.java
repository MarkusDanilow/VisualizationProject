package vis.frame;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.border.BevelBorder;

import org.lwjgl.LWJGLException;

import com.base.engine.Settings;

import vis.events.LoadDataEvent;
import vis.events.LoadFromAPIEvent;
import vis.events.OpenHelpEvent;
import vis.events.QuitApplicationEvent;
import vis.events.TimelineChangeEvent;
import vis.events.TimelineNextEvent;
import vis.events.TimelinePlayEvent;
import vis.events.TimelinePreviousEvent;
import vis.events.ToggleCompleteParallelCoordinatesEvent;
import vis.interfaces.AppInterface;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	public static final int CANVAS_COUNT = 1;
	public static final int leftSidebarWidth = 300, rightSidebarWidth = 300, footerHeight = 50;

	public final AppInterface app;

	private Canvas[] canvases = new Canvas[CANVAS_COUNT];

	private JButton back, forth, play;
	private JSlider timeline;
	private JLabel numElements, fps, dateTime;

	protected TimerThread timerThread;

	private AtomicBoolean timelineRunning = new AtomicBoolean(false);

	private RightFXPanel fxPanelObjectRight;

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
	private void init() throws LWJGLException {

		getContentPane().setLayout(new BorderLayout());

		/*
		 * ------------ create the main panel in the center and all of the
		 * canvases that will be placed inside of it ------------
		 */
		// JPanel canvasPanel = new JPanel(new GridLayout((int)
		// Math.sqrt(CANVAS_COUNT), (int) Math.sqrt(CANVAS_COUNT)));
		JPanel canvasPanel = new JPanel(new GridLayout((int) Math.sqrt(CANVAS_COUNT), (int) Math.sqrt(CANVAS_COUNT)));

		for (byte i = 0; i < CANVAS_COUNT; i++) {
			JPanel cPanel = new JPanel();
			cPanel.setLayout(new GridLayout(1, 1));
			cPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

			Canvas c = new Canvas();

			canvasPanel.add(cPanel);
			cPanel.add(c);

			c.setIgnoreRepaint(true);
			c.setBackground(Settings.WND_COLOR.toAwtColor());
			canvases[i] = c;
		}

		this.setBackground(Settings.WND_COLOR.toAwtColor());

		getContentPane().add(canvasPanel, BorderLayout.CENTER);
		getContentPane().setBackground(Settings.WND_COLOR.toAwtColor());

		// Font captionFont = new Font("Arial", Font.BOLD + Font.PLAIN, 14);

		/* ------------ create the sidebar to the left ------------ */
		JPanel leftSidebarPanel = new JPanel();
		leftSidebarPanel.setBackground(Settings.WND_COLOR.toAwtColor());

		LeftFXPanel fxPanelObjectLeft = new LeftFXPanel();
		JPanel fxPanelLeft = fxPanelObjectLeft.getPanel(leftSidebarWidth, this.getHeight(), this);

		leftSidebarPanel.add(fxPanelLeft);

		getContentPane().add(leftSidebarPanel, BorderLayout.WEST);

		/* ------------ create the sidebar to the right ------------ */
		JPanel rightSidebarPanel = new JPanel();
		rightSidebarPanel.setBackground(Settings.WND_COLOR.toAwtColor());

		fxPanelObjectRight = new RightFXPanel();
		JPanel fxPanelRight = fxPanelObjectRight.getPanel(rightSidebarWidth, this.getHeight(), this);

		rightSidebarPanel.add(fxPanelRight);

		getContentPane().add(rightSidebarPanel, BorderLayout.EAST);

		/* ------------ create the footer ------------ */
		JPanel footerPanel = new JPanel(new GridLayout(2, 1));
		footerPanel.setPreferredSize(new Dimension(this.getWidth(), footerHeight));
		footerPanel.setBackground(Settings.getWindowColor().toAwtColor());

		JPanel timelinePanel = new JPanel(new BorderLayout());
		JPanel timelineButtonPanel = new JPanel(new GridLayout(1, 3));
		this.back = new JButton("<<");
		this.forth = new JButton(">>");
		this.play = new JButton("Abspielen");

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
		this.timeline.setBackground(Settings.getWindowColor().toAwtColor());
		this.back.setBackground(Settings.getWindowColor().toAwtColor());
		this.forth.setBackground(Settings.getWindowColor().toAwtColor());
		this.play.setBackground(Settings.getWindowColor().toAwtColor());

		this.toggleTimeline(false, 0, 0, 0);

		footerPanel.add(timelinePanel);

		// Status bar at the bottom
		JPanel statusBar = new JPanel();
		statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusBar.setLayout(new GridLayout(1, 4));

		this.numElements = new JLabel("Geladene Daten: -");
		statusBar.add(this.numElements);

		this.fps = new JLabel("FPS: -");
		statusBar.add(this.fps);

		statusBar.add(new JLabel(""));

		this.dateTime = new JLabel("");
		statusBar.add(this.dateTime);

		footerPanel.add(statusBar);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				timerThread.setRunning(false);
			}
		});

		timerThread = new TimerThread(this.dateTime, this.fps);
		timerThread.start();

		getContentPane().add(footerPanel, BorderLayout.SOUTH);

		/*
		 * ------------ create the menu bar at the top of the window
		 * ------------
		 */
		JMenuBar menuBar = new JMenuBar();

		// file menu
		JMenu fileMenu = new JMenu("Datei");
		JMenuItem loadFile = new JMenuItem("Öffnen...");
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

		// settings menu
		JMenu settingsMenu = new JMenu("Einstellungen");
		JCheckBoxMenuItem completeParallelCoordinates = new JCheckBoxMenuItem("Vollst. Parall. Koord.");
		completeParallelCoordinates.addActionListener(new ToggleCompleteParallelCoordinatesEvent(this));
		settingsMenu.add(completeParallelCoordinates);
		menuBar.add(settingsMenu);

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
		this.play.setText("Pausieren");
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
		this.play.setText("Abspielen");
		this.timelineRunning.set(false);
	}

	public void timelineStopCheck() {
		int value = this.timeline.getValue();
		if (value == this.timeline.getMaximum() || value == this.timeline.getMinimum()) {
			this.stopTimeline();
		}
	}

	public void setNumElements(int num) {
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.GERMAN);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator('.');
		formatter.setDecimalFormatSymbols(symbols);
		this.numElements.setText("Geladene Daten: " + formatter.format(num));
	}

	@SuppressWarnings("unused")
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	public RightFXPanel getFxPanelObjectRight() {
		return fxPanelObjectRight;
	}

}
