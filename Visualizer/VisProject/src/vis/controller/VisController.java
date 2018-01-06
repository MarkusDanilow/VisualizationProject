package vis.controller;

import java.awt.Canvas;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.lwjgl.LWJGLException;

import com.base.common.resources.DataElement.DataType;
import com.base.common.resources.DataElement;
import com.base.common.resources.Range;
import com.base.common.resources.StatisticObject;
import com.base.engine.Settings;

import vis.data.DataBuffer;
import vis.data.DataHandler;
import vis.frame.MainWindow;
import vis.main.VisApplication;
import vis.statistics.Statistic;

public class VisController {

	private static AtomicReference<MainWindow> window;
	private static String ID;
	private static DataBuffer actData;
	private static Map<Float, DataElement> actMap;

	private static VisApplication application;

	public static void init(VisApplication visApp) throws LWJGLException {
		application = visApp;
		window = new AtomicReference<>();
		window.set(new MainWindow(visApp, Settings.getApplicationTitle(), Settings.getDisplayWidth(),
				Settings.getDisplayHeight()));

	}

	public static void changeAccordion(String paneType, String paneName, MainWindow wnd) {
		// Aufruf von RightFX changeController(int, String)
		window.get().getFxPanelObjectRight().changeAccordion(paneType, paneName, wnd);

	}

	public static Canvas getCanvas() {
		Canvas c = window.get().getCanvasById(0);
		return c;

	}

	public static MainWindow getWindow() {
		return window.get();
	}

	public static void einzelpunktVektor() {
		ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();

		actData = DataHandler.getCurrentBuffer();
		actMap = actData.getData();

		Map<Float, DataElement> partMap;
		partMap = DataHandler.getPartialData(actMap, new Range<Float>(window.get().getTimelineValue(), window.get().getTimelineValue()));

		int viewportIndex = convertIDToViewportIndex(ID);
		
		application.setPointCloudData(viewportIndex, partMap);
	}

	public static void plotAll() {
		ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();

		actData = DataHandler.getCurrentBuffer();
		actMap = actData.getData();
		
		int viewportIndex = convertIDToViewportIndex(ID);
		application.setPointCloudData(viewportIndex, actMap);
	}

	public static void plotFromTo(String from, String to) {
		ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();
		int f = Integer.parseInt(from);
		int t = Integer.parseInt(to);

		System.out.println("Von" + f + "bis" + t);

		actData = DataHandler.getCurrentBuffer();
		actMap = actData.getData();

		Map<Float, DataElement> partMap;
		partMap = DataHandler.getPartialData(actMap, new Range<Float>((float) f, (float) t));
		
		int viewportIndex = convertIDToViewportIndex(ID);
		application.setPointCloudData(viewportIndex, partMap);
	}

	public static void rotateRight() {
		ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();
		
		int viewportIndex = convertIDToViewportIndex(ID);
		
		application.rotateView(viewportIndex, 90);
	}

	public static void rotateLeft() {
		ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();

		int viewportIndex = convertIDToViewportIndex(ID);

		application.rotateView(viewportIndex, -90);
	}

	public static void setMinimum(String s) {
		ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();
		
		int viewportIndex = convertIDToViewportIndex(ID);

		// TODO: @Markus, wie bereinigen wir die Map um die zu kurzen Distanzen?
		
		actData = DataHandler.getCurrentBuffer();
		actMap = actData.getData();
		
		//TODO: In Klärung
		

	}

	public static void bdShowX() {
		ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();
		int viewportIndex = convertIDToViewportIndex(ID);
		application.setDataType(viewportIndex, DataType.X);
	}

	public static void bdShowY() {
		ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();
		int viewportIndex = convertIDToViewportIndex(ID);
		application.setDataType(viewportIndex, DataType.Y);
	}

	public static void bdShowZ() {
		ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();
		int viewportIndex = convertIDToViewportIndex(ID);
		application.setDataType(viewportIndex, DataType.Z);
	}

	public static void calculateMean(String s, String dimension) {
		ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();

		actData = DataHandler.getCurrentBuffer();
		actMap = actData.getData();

		Statistic.setMean(actMap, dimension, Integer.parseInt(s));

		StatisticObject result = Statistic.getMean();
		System.out.println(result);
		
		int viewportIndex = convertIDToViewportIndex(ID);
		application.setStatisticObject(viewportIndex, result);		
	}

	public static void ldShowX() {
		ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();
		int viewportIndex = convertIDToViewportIndex(ID);
		application.setDataType(viewportIndex, DataType.X);
	}

	public static void ldShowY() {
		ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();
		int viewportIndex = convertIDToViewportIndex(ID);
		application.setDataType(viewportIndex, DataType.Y);
	}

	public static void ldShowZ() {
		ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();
		int viewportIndex = convertIDToViewportIndex(ID);
		application.setDataType(viewportIndex, DataType.Z);
	}

	public static void trend(String s, String dimension) {
		ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();

		actData = DataHandler.getCurrentBuffer();
		actMap = actData.getData();

		Statistic.setRegAnalysis(actMap, dimension, Integer.parseInt(s));

		StatisticObject result = Statistic.getRegAnalysis();

		System.out.println(result);

		int viewportIndex = convertIDToViewportIndex(ID);
		application.setStatisticObject(viewportIndex, result);
	}

	public static void pkShowX() {
		ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();
		// TODO: @Markus: Wie switchen wir die Dimensionen beim Diagramm?
		switch (ID) {
		case "A":
			System.out.println("Es ist Pane A");
			break;
		case "B":
			System.out.println("Es ist Pane B");
			break;
		case "C":
			System.out.println("Es ist Pane C");
			break;
		case "D":
			System.out.println("Es ist Pane D");
			break;
		default:
			System.out.println("Alles falsch");
			break;
		}
	}

	public static void pkShowY() {
		ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();
		// TODO: @Markus: Wie switchen wir die Dimensionen beim Diagramm?
		switch (ID) {
		case "A":
			System.out.println("Es ist Pane A");
			break;
		case "B":
			System.out.println("Es ist Pane B");
			break;
		case "C":
			System.out.println("Es ist Pane C");
			break;
		case "D":
			System.out.println("Es ist Pane D");
			break;
		default:
			System.out.println("Alles falsch");
			break;
		}
	}

	public static void pkShowZ() {
		ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();
		// TODO: @Markus: Wie switchen wir die Dimensionen beim Diagramm?
		switch (ID) {
		case "A":
			System.out.println("Es ist Pane A");
			break;
		case "B":
			System.out.println("Es ist Pane B");
			break;
		case "C":
			System.out.println("Es ist Pane C");
			break;
		case "D":
			System.out.println("Es ist Pane D");
			break;
		default:
			System.out.println("Alles falsch");
			break;
		}
	}

	public static void showDistance() {
		ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();
		// TODO: @Markus: Wie switchen wir die Dimensionen beim Diagramm?
		switch (ID) {
		case "A":
			System.out.println("Es ist Pane A");
			break;
		case "B":
			System.out.println("Es ist Pane B");
			break;
		case "C":
			System.out.println("Es ist Pane C");
			break;
		case "D":
			System.out.println("Es ist Pane D");
			break;
		default:
			System.out.println("Alles falsch");
			break;
		}
	}

	private static int convertIDToViewportIndex(String id) {
		int viewportIndex = (int) (ID.charAt(0)) - 65;
		return (viewportIndex == 1 ? 2 : (viewportIndex == 2 ? 1 : viewportIndex));
	}

}
