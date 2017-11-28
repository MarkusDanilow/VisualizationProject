package vis.frame;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.JPopupMenu;
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
			c.setBackground(Settings.getClearColor());
			canvases[i] = c;
		}

		getContentPane().add(canvasPanel, BorderLayout.CENTER);

		Font captionFont = new Font("Arial", Font.BOLD + Font.PLAIN, 14);

		/* ------------ create the sidebar to the left ------------ */
		JPanel leftSidebarPanel = new JPanel();
		leftSidebarPanel.setBounds(new Rectangle(0, 0, 200, 600));
		leftSidebarPanel.setPreferredSize(new Dimension(leftSidebarWidth, this.getHeight()));

		leftSidebarPanel.setBackground(new Color(83, 83, 83));

		leftSidebarPanel.setLayout(new BorderLayout(0, 0));

		getContentPane().add(leftSidebarPanel, BorderLayout.WEST);

		JPanel visTechnikGrid = new JPanel();
		visTechnikGrid.setBounds(new Rectangle(0, 0, 200, 400));
		leftSidebarPanel.add(visTechnikGrid, BorderLayout.NORTH);
		visTechnikGrid.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel visTechnikBag = new JPanel();
		visTechnikGrid.add(visTechnikBag);
		GridBagLayout gbl_visTechnikBag = new GridBagLayout();
		gbl_visTechnikBag.columnWidths = new int[] { 0, 92, 92, 0, 0 };
		gbl_visTechnikBag.rowHeights = new int[] { 0, 16, 0, 23, 0, 0, 0, 23, 20, 0 };
		gbl_visTechnikBag.columnWeights = new double[] { 1.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_visTechnikBag.rowWeights = new double[] { 1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		visTechnikBag.setLayout(gbl_visTechnikBag);
		visTechnikBag.setBackground(new Color(83, 83, 83));

		JPanel panel_14 = new JPanel();
		panel_14.setBackground(new Color(83, 83, 83));
		GridBagConstraints gbc_panel_14 = new GridBagConstraints();
		gbc_panel_14.gridwidth = 2;
		gbc_panel_14.insets = new Insets(0, 0, 5, 5);
		gbc_panel_14.fill = GridBagConstraints.BOTH;
		gbc_panel_14.gridx = 1;
		gbc_panel_14.gridy = 0;
		visTechnikBag.add(panel_14, gbc_panel_14);

		// TODO: Generisches String-Array view[] ersetzen
		// String view[] = { "3D", "2D", "1D", "ScatterPlot", "Spline",
		// "Bar-Chart", "Aimed Target" };

		// Visualisierungstechniken
		JLabel lblTechnik = new JLabel("Visualisierungstechnik");
		GridBagConstraints gbc_lblTechnik = new GridBagConstraints();
		gbc_lblTechnik.anchor = GridBagConstraints.NORTH;
		gbc_lblTechnik.insets = new Insets(0, 0, 5, 5);
		gbc_lblTechnik.gridwidth = 2;
		gbc_lblTechnik.gridx = 1;
		gbc_lblTechnik.gridy = 1;
		visTechnikBag.add(lblTechnik, gbc_lblTechnik);
		lblTechnik.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTechnik.setForeground(Color.WHITE);
		lblTechnik.setFont(new Font("SansSerif", Font.BOLD, 12));

		JPanel panelFiller = new JPanel();
		panelFiller.setBackground(new Color(83, 83, 83));
		GridBagConstraints gbc_panelFiller = new GridBagConstraints();
		gbc_panelFiller.gridwidth = 2;
		gbc_panelFiller.insets = new Insets(0, 0, 5, 5);
		gbc_panelFiller.fill = GridBagConstraints.BOTH;
		gbc_panelFiller.gridx = 1;
		gbc_panelFiller.gridy = 2;
		visTechnikBag.add(panelFiller, gbc_panelFiller);

		JLabel lblPaneA = new JLabel("Pane A:");
		lblPaneA.setForeground(Color.WHITE);
		lblPaneA.setFont(new Font("SansSerif", Font.BOLD, 12));
		GridBagConstraints gbc_lblPaneA = new GridBagConstraints();
		gbc_lblPaneA.anchor = GridBagConstraints.WEST;
		gbc_lblPaneA.insets = new Insets(0, 0, 5, 5);
		gbc_lblPaneA.gridx = 1;
		gbc_lblPaneA.gridy = 3;
		visTechnikBag.add(lblPaneA, gbc_lblPaneA);

		JComboBox cmbTechnik1 = new JComboBox();
		GridBagConstraints gbc_cmbTechnik1 = new GridBagConstraints();
		gbc_cmbTechnik1.anchor = GridBagConstraints.NORTHEAST;
		gbc_cmbTechnik1.insets = new Insets(0, 0, 5, 5);
		gbc_cmbTechnik1.gridx = 2;
		gbc_cmbTechnik1.gridy = 3;
		visTechnikBag.add(cmbTechnik1, gbc_cmbTechnik1);
		cmbTechnik1.setFont(new Font("SansSerif", Font.PLAIN, 11));
		cmbTechnik1.setModel(new DefaultComboBoxModel(
				new String[] { "3D", "2D", "1D", "Scatter plot", "Spline", "Bar-Chart", "Aimed Target" }));
		// cmbTechnik1.setBorder(BorderFactory.createLineBorder(Color.GREEN,
		// 2));

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(83, 83, 83));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.gridheight = 5;
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 3;
		gbc_panel_2.gridy = 3;
		visTechnikBag.add(panel_2, gbc_panel_2);

		JLabel lblPaneB = new JLabel("Pane B:");
		lblPaneB.setForeground(Color.WHITE);
		lblPaneB.setFont(new Font("SansSerif", Font.BOLD, 12));
		GridBagConstraints gbc_lblPaneB = new GridBagConstraints();
		gbc_lblPaneB.anchor = GridBagConstraints.WEST;
		gbc_lblPaneB.insets = new Insets(0, 0, 5, 5);
		gbc_lblPaneB.gridx = 1;
		gbc_lblPaneB.gridy = 4;
		visTechnikBag.add(lblPaneB, gbc_lblPaneB);

		JComboBox cmbTechnik2 = new JComboBox();
		GridBagConstraints gbc_cmbTechnik2 = new GridBagConstraints();
		gbc_cmbTechnik2.anchor = GridBagConstraints.NORTHEAST;
		gbc_cmbTechnik2.insets = new Insets(0, 0, 5, 5);
		gbc_cmbTechnik2.gridx = 2;
		gbc_cmbTechnik2.gridy = 4;
		visTechnikBag.add(cmbTechnik2, gbc_cmbTechnik2);
		cmbTechnik2.setFont(new Font("SansSerif", Font.PLAIN, 11));
		cmbTechnik2.setModel(new DefaultComboBoxModel(//
				new String[] { "Bar-Chart", "3D", "2D", "1D", "Scatter plot", "Spline", "Aimed Target" }));
		// cmbTechnik2.setBorder(BorderFactory.createLineBorder(Color.ORANGE,
		// 2));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(83, 83, 83));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridheight = 5;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 3;
		visTechnikBag.add(panel_1, gbc_panel_1);

		JLabel lblPaneC = new JLabel("Pane C:");
		lblPaneC.setForeground(Color.WHITE);
		lblPaneC.setFont(new Font("SansSerif", Font.BOLD, 12));
		GridBagConstraints gbc_lblPaneC = new GridBagConstraints();
		gbc_lblPaneC.anchor = GridBagConstraints.WEST;
		gbc_lblPaneC.insets = new Insets(0, 0, 5, 5);
		gbc_lblPaneC.gridx = 1;
		gbc_lblPaneC.gridy = 6;
		visTechnikBag.add(lblPaneC, gbc_lblPaneC);

		JComboBox cmbTechnik3 = new JComboBox();
		GridBagConstraints gbc_cmbTechnik3 = new GridBagConstraints();
		gbc_cmbTechnik3.insets = new Insets(0, 0, 5, 5);
		gbc_cmbTechnik3.anchor = GridBagConstraints.NORTHWEST;
		gbc_cmbTechnik3.gridx = 2;
		gbc_cmbTechnik3.gridy = 6;
		visTechnikBag.add(cmbTechnik3, gbc_cmbTechnik3);
		cmbTechnik3.setFont(new Font("SansSerif", Font.PLAIN, 11));
		cmbTechnik3.setModel(new DefaultComboBoxModel(
				new String[] { "Spline", "3D", "2D", "1D", "Scatter plot", "Bar-Chart", "Aimed Target" }));
		// cmbTechnik3.setBorder(BorderFactory.createLineBorder(new Color(128,
		// 0, 0), 2));

		JLabel lblPaneD = new JLabel("Pane D:");
		lblPaneD.setForeground(Color.WHITE);
		lblPaneD.setFont(new Font("SansSerif", Font.BOLD, 12));
		GridBagConstraints gbc_lblPaneD = new GridBagConstraints();
		gbc_lblPaneD.anchor = GridBagConstraints.WEST;
		gbc_lblPaneD.insets = new Insets(0, 0, 5, 5);
		gbc_lblPaneD.gridx = 1;
		gbc_lblPaneD.gridy = 7;
		visTechnikBag.add(lblPaneD, gbc_lblPaneD);

		JComboBox cmbTechnik4 = new JComboBox();
		GridBagConstraints gbc_cmbTechnik4 = new GridBagConstraints();
		gbc_cmbTechnik4.insets = new Insets(0, 0, 5, 5);
		gbc_cmbTechnik4.anchor = GridBagConstraints.NORTHEAST;
		gbc_cmbTechnik4.gridx = 2;
		gbc_cmbTechnik4.gridy = 7;
		visTechnikBag.add(cmbTechnik4, gbc_cmbTechnik4);
		cmbTechnik4.setFont(new Font("SansSerif", Font.PLAIN, 11));
		cmbTechnik4.setModel(new DefaultComboBoxModel(
				new String[] { "Scatter plot", "Aimed Target", "3D", "2D", "1D", "Spline", "Bar-Chart" }));
		// cmbTechnik4.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(83, 83, 83));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.gridwidth = 2;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 8;
		visTechnikBag.add(panel, gbc_panel);

		JPanel parameterGrid = new JPanel();
		leftSidebarPanel.add(parameterGrid, BorderLayout.CENTER);
		parameterGrid.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(83, 83, 83));
		parameterGrid.add(panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 0, 92, 92, 9, 0 };
		gbl_panel_3.rowHeights = new int[] { 16, 0, 23, 0, 0, 23, 20, 0 };
		gbl_panel_3.columnWeights = new double[] { 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_3.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_3.setLayout(gbl_panel_3);

		// TODO: Als eigene Panels definieren und dynamisch verschieben
		// Parameterfilter
		JLabel lblParameterfilter = new JLabel("Parameter-Filter (global)");
		GridBagConstraints gbc_lblParameterfilter = new GridBagConstraints();
		gbc_lblParameterfilter.gridwidth = 2;
		gbc_lblParameterfilter.insets = new Insets(0, 0, 5, 5);
		gbc_lblParameterfilter.gridx = 1;
		gbc_lblParameterfilter.gridy = 0;
		panel_3.add(lblParameterfilter, gbc_lblParameterfilter);
		lblParameterfilter.setForeground(Color.WHITE);
		lblParameterfilter.setFont(new Font("SansSerif", Font.BOLD, 12));

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(83, 83, 83));
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridwidth = 2;
		gbc_panel_4.insets = new Insets(0, 0, 5, 5);
		gbc_panel_4.gridx = 1;
		gbc_panel_4.gridy = 1;
		panel_3.add(panel_4, gbc_panel_4);

		JPanel panel_6 = new JPanel();
		panel_6.setBackground(new Color(83, 83, 83));
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.fill = GridBagConstraints.BOTH;
		gbc_panel_6.gridheight = 4;
		gbc_panel_6.insets = new Insets(0, 0, 5, 5);
		gbc_panel_6.gridx = 0;
		gbc_panel_6.gridy = 2;
		panel_3.add(panel_6, gbc_panel_6);

		JCheckBox chckbxXposition = new JCheckBox("x-Position");
		GridBagConstraints gbc_chckbxXposition = new GridBagConstraints();
		gbc_chckbxXposition.anchor = GridBagConstraints.WEST;
		gbc_chckbxXposition.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxXposition.gridx = 1;
		gbc_chckbxXposition.gridy = 2;
		panel_3.add(chckbxXposition, gbc_chckbxXposition);
		chckbxXposition.setFont(new Font("SansSerif", Font.PLAIN, 11));
		chckbxXposition.setOpaque(false);
		chckbxXposition.setForeground(Color.WHITE);

		ColorChooserButton xChooser = new ColorChooserButton(Color.red);
		xChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_xChooser = new GridBagConstraints();
		gbc_xChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_xChooser.insets = new Insets(0, 0, 5, 5);
		gbc_xChooser.gridx = 2;
		gbc_xChooser.gridy = 2;
		panel_3.add(xChooser, gbc_xChooser);

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(83, 83, 83));
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.gridheight = 4;
		gbc_panel_5.insets = new Insets(0, 0, 5, 0);
		gbc_panel_5.gridx = 3;
		gbc_panel_5.gridy = 2;
		panel_3.add(panel_5, gbc_panel_5);

		JCheckBox chckbxYposition = new JCheckBox("y-Position");
		GridBagConstraints gbc_chckbxYposition = new GridBagConstraints();
		gbc_chckbxYposition.anchor = GridBagConstraints.WEST;
		gbc_chckbxYposition.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxYposition.gridx = 1;
		gbc_chckbxYposition.gridy = 3;
		panel_3.add(chckbxYposition, gbc_chckbxYposition);
		chckbxYposition.setFont(new Font("SansSerif", Font.PLAIN, 11));
		chckbxYposition.setOpaque(false);
		chckbxYposition.setForeground(Color.WHITE);

		ColorChooserButton yChooser = new ColorChooserButton(Color.blue);
		GridBagConstraints gbc_yChooser = new GridBagConstraints();
		gbc_yChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_yChooser.insets = new Insets(0, 0, 5, 5);
		gbc_yChooser.gridx = 2;
		gbc_yChooser.gridy = 3;
		panel_3.add(yChooser, gbc_yChooser);

		JCheckBox chckbxZposition = new JCheckBox("z-Position");
		GridBagConstraints gbc_chckbxZposition = new GridBagConstraints();
		gbc_chckbxZposition.anchor = GridBagConstraints.WEST;
		gbc_chckbxZposition.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxZposition.gridx = 1;
		gbc_chckbxZposition.gridy = 4;
		panel_3.add(chckbxZposition, gbc_chckbxZposition);
		chckbxZposition.setFont(new Font("SansSerif", Font.PLAIN, 11));
		chckbxZposition.setOpaque(false);
		chckbxZposition.setForeground(Color.WHITE);

		ColorChooserButton zChooser = new ColorChooserButton(Color.green);
		GridBagConstraints gbc_zChooser = new GridBagConstraints();
		gbc_zChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_zChooser.insets = new Insets(0, 0, 5, 5);
		gbc_zChooser.gridx = 2;
		gbc_zChooser.gridy = 4;
		panel_3.add(zChooser, gbc_zChooser);

		JCheckBox chckbxTargetObjekt = new JCheckBox("Target Obejct");
		GridBagConstraints gbc_chckbxTargetObjekt = new GridBagConstraints();
		gbc_chckbxTargetObjekt.anchor = GridBagConstraints.WEST;
		gbc_chckbxTargetObjekt.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxTargetObjekt.gridx = 1;
		gbc_chckbxTargetObjekt.gridy = 5;
		panel_3.add(chckbxTargetObjekt, gbc_chckbxTargetObjekt);
		chckbxTargetObjekt.setFont(new Font("SansSerif", Font.PLAIN, 11));
		chckbxTargetObjekt.setOpaque(false);
		chckbxTargetObjekt.setForeground(Color.WHITE);

		ColorChooserButton tChooser = new ColorChooserButton(Color.yellow);
		GridBagConstraints gbc_tChooser = new GridBagConstraints();
		gbc_tChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_tChooser.insets = new Insets(0, 0, 5, 5);
		gbc_tChooser.gridx = 2;
		gbc_tChooser.gridy = 5;
		panel_3.add(tChooser, gbc_tChooser);

		JPanel panel_7 = new JPanel();
		panel_7.setBackground(new Color(83, 83, 83));
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.fill = GridBagConstraints.BOTH;
		gbc_panel_7.gridwidth = 2;
		gbc_panel_7.insets = new Insets(0, 0, 0, 5);
		gbc_panel_7.gridx = 1;
		gbc_panel_7.gridy = 6;
		panel_3.add(panel_7, gbc_panel_7);

		JPanel panel_8 = new JPanel();
		leftSidebarPanel.add(panel_8, BorderLayout.SOUTH);
		panel_8.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panel_9 = new JPanel();
		panel_9.setBackground(new Color(83, 83, 83));
		panel_8.add(panel_9);
		GridBagLayout gbl_panel_9 = new GridBagLayout();
		gbl_panel_9.columnWidths = new int[] { 0, 92, 92, 9, 0 };
		gbl_panel_9.rowHeights = new int[] { 16, 0, 23, 0, 178, 0 };
		gbl_panel_9.columnWeights = new double[] { 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_9.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		panel_9.setLayout(gbl_panel_9);

		// TODO: Als eigene Panels definieren und dynamisch verschieben
		// Datumsauswahl
		JLabel lblDatumsauswahl = new JLabel("Datumsauswahl");
		GridBagConstraints gbc_lblDatumsauswahl = new GridBagConstraints();
		gbc_lblDatumsauswahl.gridwidth = 2;
		gbc_lblDatumsauswahl.insets = new Insets(0, 0, 5, 5);
		gbc_lblDatumsauswahl.gridx = 1;
		gbc_lblDatumsauswahl.gridy = 0;
		panel_9.add(lblDatumsauswahl, gbc_lblDatumsauswahl);
		lblDatumsauswahl.setForeground(Color.WHITE);
		lblDatumsauswahl.setFont(new Font("SansSerif", Font.BOLD, 12));

		JPanel panel_10 = new JPanel();
		panel_10.setBackground(new Color(83, 83, 83));
		GridBagConstraints gbc_panel_10 = new GridBagConstraints();
		gbc_panel_10.fill = GridBagConstraints.BOTH;
		gbc_panel_10.gridwidth = 2;
		gbc_panel_10.insets = new Insets(0, 0, 5, 5);
		gbc_panel_10.gridx = 1;
		gbc_panel_10.gridy = 1;
		panel_9.add(panel_10, gbc_panel_10);

		JPanel panel_11 = new JPanel();
		panel_11.setBackground(new Color(83, 83, 83));
		GridBagConstraints gbc_panel_11 = new GridBagConstraints();
		gbc_panel_11.fill = GridBagConstraints.BOTH;
		gbc_panel_11.gridheight = 2;
		gbc_panel_11.insets = new Insets(0, 0, 5, 5);
		gbc_panel_11.gridx = 0;
		gbc_panel_11.gridy = 2;
		panel_9.add(panel_11, gbc_panel_11);

		JLabel lblVon = new JLabel("Von:");
		GridBagConstraints gbc_lblVon = new GridBagConstraints();
		gbc_lblVon.anchor = GridBagConstraints.WEST;
		gbc_lblVon.insets = new Insets(0, 0, 5, 5);
		gbc_lblVon.gridx = 1;
		gbc_lblVon.gridy = 2;
		panel_9.add(lblVon, gbc_lblVon);
		lblVon.setForeground(Color.WHITE);
		lblVon.setFont(new Font("SansSerif", Font.BOLD, 12));

		JDateChooser dateChooserFrom = new JDateChooser();
		GridBagConstraints gbc_dateChooserFrom = new GridBagConstraints();
		gbc_dateChooserFrom.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateChooserFrom.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooserFrom.gridx = 2;
		gbc_dateChooserFrom.gridy = 2;
		panel_9.add(dateChooserFrom, gbc_dateChooserFrom);

		JPanel panel_12 = new JPanel();
		panel_12.setBackground(new Color(83, 83, 83));
		GridBagConstraints gbc_panel_12 = new GridBagConstraints();
		gbc_panel_12.fill = GridBagConstraints.BOTH;
		gbc_panel_12.gridheight = 2;
		gbc_panel_12.insets = new Insets(0, 0, 5, 0);
		gbc_panel_12.gridx = 3;
		gbc_panel_12.gridy = 2;
		panel_9.add(panel_12, gbc_panel_12);

		JLabel lblBis = new JLabel("Bis:");
		GridBagConstraints gbc_lblBis = new GridBagConstraints();
		gbc_lblBis.anchor = GridBagConstraints.WEST;
		gbc_lblBis.insets = new Insets(0, 0, 5, 5);
		gbc_lblBis.gridx = 1;
		gbc_lblBis.gridy = 3;
		panel_9.add(lblBis, gbc_lblBis);
		lblBis.setForeground(Color.WHITE);
		lblBis.setFont(new Font("SansSerif", Font.BOLD, 12));

		JDateChooser dateChooserTo = new JDateChooser();
		GridBagConstraints gbc_dateChooserTo = new GridBagConstraints();
		gbc_dateChooserTo.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateChooserTo.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooserTo.gridx = 2;
		gbc_dateChooserTo.gridy = 3;
		panel_9.add(dateChooserTo, gbc_dateChooserTo);

		JPanel panel_13 = new JPanel();
		panel_13.setBackground(new Color(83, 83, 83));
		GridBagConstraints gbc_panel_13 = new GridBagConstraints();
		gbc_panel_13.fill = GridBagConstraints.BOTH;
		gbc_panel_13.gridwidth = 2;
		gbc_panel_13.insets = new Insets(0, 0, 0, 5);
		gbc_panel_13.gridx = 1;
		gbc_panel_13.gridy = 4;
		panel_9.add(panel_13, gbc_panel_13);

		/* ------------ create the sidebar to the right ------------ */
		JPanel rightSidebarPanel = new JPanel();

		RightFXPanel fxPanelObejct = new RightFXPanel();
		JPanel fxPanel = fxPanelObejct.getPanel(rightSidebarWidth, this.getHeight());
		
		rightSidebarPanel.add(fxPanel);
		
		getContentPane().add(rightSidebarPanel, BorderLayout.EAST);

		/* ------------ create the footer ------------ */
		JPanel footerPanel = new JPanel(new GridLayout(2, 1));
		footerPanel.setPreferredSize(new Dimension(this.getWidth(), footerHeight));
		footerPanel.setBackground(new Color(83,83,83));

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

		getContentPane().add(footerPanel, BorderLayout.SOUTH);

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

		System.out.println("finished creating frame");

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
}
