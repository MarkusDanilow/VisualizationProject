package vis.frame;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import javafx.embed.swing.JFXPanel;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class RightFXPanel {
	
	public void main () {
		
	}
	
	public JPanel getPanel (int rightSidebarWidth, int height) {
		
		JPanel rightSidebarPanel = new JPanel();
		JFXPanel rightSidebarPanelFX = new JFXPanel();
		rightSidebarPanel.add(rightSidebarPanelFX);
		
		rightSidebarPanel.setBounds(new Rectangle(0, 0, rightSidebarWidth, height));
		rightSidebarPanel.setPreferredSize(new Dimension(rightSidebarWidth, height));
		rightSidebarPanel.setVisible(true);

	     //TODO: Accoridion fertigstellen   
        final Label label = new Label("Einstellungen"); 
        label.setAlignment(Pos.CENTER); 
        String t1Text = "live-view:\freeze"; 
        String t2Text = ""; 
        String t3Text = ""; 
        String t4Text = ""; 
        String t5Text = ""; 
        String t6Text = ""; 

        
        TitledPane t1 = new TitledPane("Common", new CheckBox("live-view")); 
        TitledPane t2 = new TitledPane("Pane A: 3D", new CheckBox("single dot / vector"));        
        TitledPane t3 = new TitledPane("Rotate", new Label(t3Text)); 
        TitledPane t4 = new TitledPane("Pane B: Bar-Chart", new Label(t4Text));
        TitledPane t5 = new TitledPane("Pane C: Spline", new Label(t5Text));
        TitledPane t6 = new TitledPane("Pane D: Scatterplot", new Label(t6Text));

        
        Accordion accordion = new Accordion(); 
        accordion.getPanes().addAll(t1, t2, t3, t4); 
        accordion.setExpandedPane(t3); 
        
        
        GridPane grid = new GridPane(); 
        grid.setAlignment(Pos.TOP_CENTER); 
        grid.setHgap(5); 
        grid.setVgap(5); 
        grid.add(label, 0, 0); 
        grid.add(accordion, 0, 1); 
        grid.setGridLinesVisible(false);        
        
        Scene scene = new Scene(grid, rightSidebarWidth, height);
        scene.getStylesheets().add(RightFXPanel.class.getResource("style.css").toExternalForm());
        
	    rightSidebarPanelFX.setScene(scene);
		
	    
	    return rightSidebarPanel;
		
	}
}
