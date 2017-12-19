package vis.controller;

import java.awt.Canvas;
import java.util.concurrent.atomic.AtomicReference;

import org.lwjgl.LWJGLException;

import com.base.engine.Settings;

import javafx.application.Platform;
import vis.frame.MainWindow;
import vis.main.VisApplication;

public class VisController {
	
	private static AtomicReference<MainWindow> window;
	
	public static void init(VisApplication visApp) throws LWJGLException{
		window = new AtomicReference<>();
		window.set(new MainWindow(visApp, Settings.getApplicationTitle(), Settings.getDisplayWidth(),
				Settings.getDisplayHeight()));
		
		//TODO: Threading Problem lösen.
//		changeAccordion(1, "C");
	}
	
	public static void changeAccordion(int paneType, String paneName) {
		//Aufruf von RightFX changeController(int, String)
		window.get().getFxPanelObjectRight().changeAccordion(paneType, paneName);
		
	}
	
	public static Canvas getCanvas() {
		Canvas c = window.get().getCanvasById(0);
		return c;
		
	}
	
}
