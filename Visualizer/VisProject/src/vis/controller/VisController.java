package vis.controller;

import java.awt.Canvas;

import org.lwjgl.LWJGLException;

import com.base.engine.Settings;

import vis.frame.MainWindow;
import vis.main.VisApplication;

public class VisController {
	
	private static MainWindow window;
	
	public static void init(VisApplication visApp) throws LWJGLException{
		window = new MainWindow(visApp, Settings.getApplicationTitle(), Settings.getDisplayWidth(),
				Settings.getDisplayHeight());
	}
	
	public void changeAccordion(int paneType, String paneName) {
		
		//Aufruf von RightFX changeController(int, String)
		
	}
	
	public static Canvas getCanvas() {
		Canvas c = window.getCanvasById(0);
		return c;
		
	}
	
}
