package vis.frame;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JPanel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

public class LeftFXPanel {

	public JPanel getPanel (int leftSidebarWidth, int height) {
		
		JPanel leftSidebarPanel = new JPanel();
		JFXPanel leftSidebarPanelFX = new JFXPanel();
		leftSidebarPanel.add(leftSidebarPanelFX);
		
		leftSidebarPanel.setBounds(new Rectangle(0, 0, leftSidebarWidth, height));
		leftSidebarPanel.setPreferredSize(new Dimension(leftSidebarWidth, height));
		leftSidebarPanel.setVisible(true);
        
        GridPane gridMain = new GridPane(); 
        gridMain.setAlignment(Pos.TOP_CENTER); 
        gridMain.setHgap(5); 
        gridMain.setVgap(5); 
        gridMain.setGridLinesVisible(false);
        
        //Inhalte
        GridPane gridVis = new GridPane();
        gridVis.setVgap(4);
        gridVis.setPadding(new Insets(5, 5, 5, 5));
        
        //Vis-Techniken
        final Label visTech = new Label("Visualization techniques");
        visTech.setId("headline");
        gridVis.add(visTech, 0, 0, 2, 1);
        
        final Label paneA = new Label("Pane A:");
        gridVis.add(paneA, 0, 1);
        
        ObservableList<String> optionsPane = 
        		FXCollections.observableArrayList(
        				"3D",
        				"Bar-Chart",
        				"Spline",
        				"Parallel Lines"
        				//"Aimed-Target"
        				);        
        
        final ComboBox<String> cbPaneA = new ComboBox<String>(optionsPane);
        cbPaneA.setValue(optionsPane.get(0));
        gridVis.add(cbPaneA, 1, 1);
        
        final Label paneB = new Label("Pane B:");
        gridVis.add(paneB, 0, 2);

        final ComboBox<String> cbPaneB = new ComboBox<String>(optionsPane);
        cbPaneB.setValue(optionsPane.get(1));
        gridVis.add(cbPaneB, 1, 2);
        
        final Label paneC = new Label("Pane C:");
        gridVis.add(paneC, 0, 3);
        
        final ComboBox<String> cbPaneC = new ComboBox<String>(optionsPane);
        cbPaneC.setValue(optionsPane.get(2));
        gridVis.add(cbPaneC, 1, 3);
        
        final Label paneD = new Label("Pane D:");
        gridVis.add(paneD, 0, 4);
        
        final ComboBox<String> cbPaneD = new ComboBox<String>(optionsPane);
        cbPaneD.setValue(optionsPane.get(3));
        gridVis.add(cbPaneD, 1, 4);
        
        gridMain.add(gridVis, 0, 0);
        
        GridPane gridCommon = new GridPane();
        gridCommon.setVgap(4);
        gridCommon.setHgap(5);
        gridCommon.setPadding(new Insets(5, 5, 5, 5));
        
      //  final Label lblParameterFilter = new Label("Parameter filter (global)");
        final Label lblCommon = new Label("Common");
        lblCommon.setId("headline");
        gridCommon.add(lblCommon, 0, 0, 2, 1);
        
        final ToggleGroup groupT1 = new ToggleGroup();
        
        RadioButton rbLive = new RadioButton("live-view");
        rbLive.setToggleGroup(groupT1);
        gridCommon.add(rbLive, 0, 1);
        
        RadioButton rbFreeze = new RadioButton("freeze");
        rbFreeze.setToggleGroup(groupT1);
        gridCommon.add(rbFreeze, 0, 2);
        
        Button invertBackground = new Button("Invert Background");
        invertBackground.setMinWidth(70);
        gridCommon.add(invertBackground, 0, 5);
   
   /*     
        CheckBox cbXPosition = new CheckBox("x-Position");
        gridParameter.add(cbXPosition, 0, 1);
        
        ColorPicker xChooser = new ColorPicker(Color.RED);
        xChooser.setMaxWidth(100);
        gridParameter.add(xChooser, 1, 1);
        
        CheckBox cbYPosition = new CheckBox("y-Position");
        gridParameter.add(cbYPosition, 0, 2);
        
        ColorPicker yChooser = new ColorPicker(Color.BLUE);
        yChooser.setMaxWidth(100);
        gridParameter.add(yChooser, 1, 2);
        
        CheckBox cbZPosition = new CheckBox("z-Position");
        gridParameter.add(cbZPosition, 0, 3);
        
        ColorPicker zChooser = new ColorPicker(Color.GREEN);
        zChooser.setMaxWidth(100);
        gridParameter.add(zChooser, 1, 3);
        
        CheckBox cbTargetObject = new CheckBox("Target object");
        gridParameter.add(cbTargetObject, 0, 4);
        
        ColorPicker toChooser = new ColorPicker(Color.YELLOW);
        toChooser.setMaxWidth(100);
        gridParameter.add(toChooser, 1, 4);
     */   
        gridMain.add(gridCommon, 0, 1);
        
        GridPane gridDate = new GridPane();
        gridDate.setVgap(4);
        gridDate.setPadding(new Insets(5, 5, 5, 5));
        
        final Label lblDatePicker = new Label("Choose Date");
        lblDatePicker.setId("headline");
        gridDate.add(lblDatePicker, 0, 0, 2, 1);
        
        final Label lblDateFrom = new Label("From:");
        gridDate.add(lblDateFrom, 0, 1);
        
        DatePicker dateFrom = new DatePicker();
        gridDate.add(dateFrom, 1, 1);
        
        final Label lblDateTo = new Label("To:");
        gridDate.add(lblDateTo, 0, 2);
        
        DatePicker dateTo = new DatePicker();
        gridDate.add(dateTo, 1, 2);
        
        gridMain.add(gridDate, 0, 2);
        
        
        Scene scene = new Scene(gridMain, leftSidebarWidth, height);
        scene.getStylesheets().add(RightFXPanel.class.getResource("style.css").toExternalForm());
        
        leftSidebarPanelFX.setScene(scene);
        
	    return leftSidebarPanel;   
	}
}
