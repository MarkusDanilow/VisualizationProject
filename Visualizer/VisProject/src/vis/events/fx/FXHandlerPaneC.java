package vis.events.fx;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.value.ObservableValue;
import vis.frame.MainWindow;

public class FXHandlerPaneC extends FXChangeListenerObject {

	public FXHandlerPaneC(MainWindow wnd) {
		super(wnd);
	}

	@Override
	public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
		wnd.getFxPanelObjectRight();
		
		System.out.println("Hallo " + observable.getValue().toString());		
		
	    String stringToSearch = observable.getValue().toString();
	    
	    ArrayList<String> searchFor = new ArrayList<String>();
	    //3D
	    searchFor.add("single dot/vector");
	    searchFor.add("plot all");
	    searchFor.add("plot between");
	    searchFor.add("--------------txtPlotFrom");
	    searchFor.add("----------------txtPlotTo");
	    searchFor.add("left");
	    searchFor.add("right");
	    searchFor.add("Set minimum distance");
	    searchFor.add("--------------txtATDistance");
	    //Balken-Diagram
	    searchFor.add("BD: Zeige x");
	    searchFor.add("BD: Zeige y");
	    searchFor.add("BD: Zeige z");
	    searchFor.add("Mittelwert berechnen");
	    searchFor.add("--------------txtMeanFor");
	    //Linien-Diagramm
	    searchFor.add("LD: Zeige x");
	    searchFor.add("LD: Zeige y");
	    searchFor.add("LD: Zeige z");
	    searchFor.add("Trend berechnen");
	    searchFor.add("--------------txtTrendFor");
	    //Parallele Koordinaten
	    searchFor.add("PK: show x");
	    searchFor.add("PK: show y");
	    searchFor.add("PK: show z");
	    searchFor.add("Distanz anzeigen");

	    
	    
	    
	    Pattern p = Pattern.compile("plot all");   // the pattern to search for
	    
	    Matcher m = p.matcher(stringToSearch);
	    
	    // now try to find at least one match
	    if (m.find())
	      System.out.println("Found a match");
	    else
	      System.out.println("Did not find a match");
	  
		
//		VisController.event(observable, oldValue, newValue);
	}

}
