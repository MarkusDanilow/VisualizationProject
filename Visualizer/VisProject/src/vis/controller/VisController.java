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

}
