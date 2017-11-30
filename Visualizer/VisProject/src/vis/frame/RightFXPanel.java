package vis.frame;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JPanel;

import javafx.collections.FXCollections;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

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
        

        String t5Text = ""; 
        String t6Text = ""; 

        
        TitledPane t1 = new TitledPane();
		GridPane gridt1 = new GridPane();
		gridt1.setVgap(4);
		gridt1.setPadding(new Insets(5, 5, 5, 5));
		
		final ToggleGroup groupT1 = new ToggleGroup();
		
		RadioButton rbLive = new RadioButton("live-view");
		rbLive.setToggleGroup(groupT1);
		rbLive.setSelected(true);
		gridt1.add(rbLive, 0, 0);
		
		RadioButton rbFreeze = new RadioButton("freeze");
		rbFreeze.setToggleGroup(groupT1);
		gridt1.add(rbFreeze, 1, 0);        
		
		t1.setText("Common");
		t1.setContent(gridt1);
        
         
        TitledPane t2 = new TitledPane();        
        GridPane gridt2 = new GridPane();
        gridt2.setVgap(4);
        gridt2.setPadding(new Insets(5, 5, 5, 5));
        
		final ToggleGroup groupT2 = new ToggleGroup();
		
		RadioButton rbDotVector = new RadioButton("single dot /vector");
		rbDotVector.setToggleGroup(groupT2);
		rbDotVector.setSelected(true);
		gridt2.add(rbDotVector, 0, 0, 3, 1);
		
		RadioButton rbPlotAll = new RadioButton("plot all");
		rbPlotAll.setToggleGroup(groupT2);
		gridt2.add(rbPlotAll, 0, 1, 3, 1);
        
		RadioButton rbPlotBetween = new RadioButton("plot between");
		rbPlotBetween.setToggleGroup(groupT2);
		gridt2.add(rbPlotBetween, 0, 2, 3, 1);
		
		TextField txtPlotFrom = new TextField();
		txtPlotFrom.setMaxWidth(70);
		gridt2.add(txtPlotFrom, 0, 3);
		
		Label lblTo = new Label("to");
		lblTo.setMinWidth(23);
		gridt2.add(lblTo, 1, 3);
		
		TextField txtPlotTo = new TextField();
		txtPlotTo.setMaxWidth(70);
		gridt2.add(txtPlotTo, 2, 3);
		
		final Label lblRotate = new Label("Rotate");

		GridPane.setHalignment(lblRotate, HPos.CENTER);
		gridt2.add(lblRotate, 0, 4, 3, 1);
		
		Button rotateLeft = new Button("90° left");
		rotateLeft.setMinWidth(70);
		gridt2.add(rotateLeft, 0, 5);
		
		Button rotateRight = new Button("90° right");
		rotateRight.setMinWidth(70);
		gridt2.add(rotateRight, 2, 5);
		
        t2.setText("Pane A: 3D");
        t2.setContent(gridt2);
        
        
        TitledPane t3 = new TitledPane();
        GridPane gridt3 = new GridPane();
        gridt3.setVgap(4);
        gridt3.setPadding(new Insets(5, 5, 5, 5));
        
		final ToggleGroup groupT3 = new ToggleGroup();
		
		RadioButton rbShowX = new RadioButton("show x");
		rbShowX.setToggleGroup(groupT3);
		rbDotVector.setSelected(true);
		gridt3.add(rbShowX, 0, 0, 3, 1);
		
		RadioButton rbShowY = new RadioButton("show y");
		rbShowY.setToggleGroup(groupT3);
		gridt3.add(rbShowY, 0, 1, 3, 1);
        
		RadioButton rbShowZ = new RadioButton("show z");
		rbShowZ.setToggleGroup(groupT3);
		gridt3.add(rbShowZ, 0, 2, 3, 1);
        
        t3.setText("Pane B: Bar-Chart");
        t3.setContent(gridt3);
        
        TitledPane t4 = new TitledPane("Pane C: Spline", new Label(t5Text));
        TitledPane t5 = new TitledPane("Pane D: Scatterplot", new Label(t6Text));

        
        Accordion accordion = new Accordion(); 
        accordion.getPanes().addAll(t1, t2, t3, t4, t5); 
        accordion.setExpandedPane(t1); 
        accordion.setPrefSize(rightSidebarWidth-20, 200);
        
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
