package vis.controller;

import java.awt.Canvas;
import java.util.concurrent.atomic.AtomicReference;

import org.lwjgl.LWJGLException;

import com.base.engine.Settings;

import vis.frame.MainWindow;
import vis.main.VisApplication;

public class VisController {

	private static AtomicReference<MainWindow> window;

	public static void init(VisApplication visApp) throws LWJGLException {
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
	
	public static void EinzelpunktVektor() {
		String ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();
		
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
	
	public static void PlotAll() {
		String ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();
		
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
	
	public static void PlotFromTo() {
		String ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();
		
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
	
	public static void RotateRight() {
		String ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();
		
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
	
	public static void PlotLeft() {
		String ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();
		
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
	
	public static void SetMinimum() {
		String ID = window.get().getFxPanelObjectRight().getActiveAccordion().getId();
		
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
	

}
