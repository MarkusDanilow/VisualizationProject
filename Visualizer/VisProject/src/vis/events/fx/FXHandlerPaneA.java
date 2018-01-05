package vis.events.fx;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.value.ObservableValue;
import vis.frame.MainWindow;

public class FXHandlerPaneA extends FXChangeListenerObject {

	public FXHandlerPaneA(MainWindow wnd) {
		super(wnd);
	}

	@Override
	public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
		System.out.println(observable.getValue());
		System.out.println("Hallo " + observable.getValue().toString());		
		
	    String stringToSearch = observable.getValue().toString();
	    
	    ArrayList<String> searchFor = new ArrayList<String>();
	    searchFor.add("");
	    
	    
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
