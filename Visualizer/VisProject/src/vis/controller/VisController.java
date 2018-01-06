package vis.controller;

import java.awt.Canvas;
import java.util.concurrent.atomic.AtomicReference;

import org.lwjgl.LWJGLException;

import com.base.common.resources.DataElement.DataType;
import com.base.engine.Settings;

import vis.frame.MainWindow;
import vis.main.VisApplication;

public class VisController {

	private static AtomicReference<MainWindow> window;
	private static String ID;

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

	public static void event() {
		System.out.println(window.get().getFxPanelObjectRight().getActiveAccordion().getId());

	}

	public static void einzelpunktVektor() {
		ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();

		switch (ID) {
		case "A":
			System.out.println("Einzelpunkt/Vektor bei Pane A wurde gedr�ckt");
			break;
		case "B":
			System.out.println("Einzelpunkt/Vektor bei Pane B wurde gedr�ckt");
			break;
		case "C":
			System.out.println("Einzelpunkt/Vektor bei Pane C wurde gedr�ckt");
			break;
		case "D":
			System.out.println("Einzelpunkt/Vektor bei Pane D wurde gedr�ckt");
			break;
		default:
			System.out.println("Alles falsch");
			break;
		}

	}

	public static void plotAll() {
		ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();

		switch (ID) {
		case "A":
			System.out.println("PlotAll bei Pane A wurde gedr�ckt");
			break;
		case "B":
			System.out.println("PlotAll bei Pane B wurde gedr�ckt");
			break;
		case "C":
			System.out.println("PlotAll bei Pane C wurde gedr�ckt");
			break;
		case "D":
			System.out.println("PlotAll bei Pane D wurde gedr�ckt");
			break;
		default:
			System.out.println("Alles falsch");
			break;
		}
	}

	public static void plotFromTo(String from, String to) {
		ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();
		String f = from;
		String t = to;

		System.out.println("Von" + f + "bis" + t);

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

	public static void rotateRight() {
		ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();

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

	public static void rotateLeft() {
		ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();

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

	public static void setMinimum(String s) {
		ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();

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

	public static void calculateMean(String s) {
		ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();

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

	public static void trend(String s) {
		ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();

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

	public static void pkShowX() {
		ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();

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
