package vis.frame;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

public class TitledPaneObjects {
	
	private static TitledPane t1 = new TitledPane();
	private static TitledPane t2 = new TitledPane();
	private static TitledPane t3 = new TitledPane();
	private static TitledPane t4 = new TitledPane();
	
	private static RadioButton rbDotVector;
	private static RadioButton rbPlotAll;
	private static RadioButton rbPlotBetween;
	private static TextField txtPlotFrom;
	private static TextField txtPlotTo;
	private static Button rotateLeft;
	private static Button rotateRight;
	private static RadioButton rbShowX;
	private static RadioButton rbShowY;
	private static RadioButton rbShowZ;
	private static CheckBox meanLast;
	private static TextField txtMeanFor;
	private static RadioButton rbShowXSpline;
	private static RadioButton rbShowYSpline;
	private static RadioButton rbShowZSpline;
	private static CheckBox trendLast;
	private static TextField txtTrendFor;
	private static CheckBox showXPL;
	private static CheckBox showYPL;
	private static CheckBox showZPL;
	private static CheckBox showDistancePL;
	private static CheckBox minDistanceAT;
	private static TextField txtATDistance;
	
	
	public static void setTitledPane(int paneType, String paneName) {
		
		GridPane grid = new GridPane();
		grid.setVgap(4);
		grid.setPadding(new Insets(5, 5, 5, 5));
		
		switch(paneType) {
			case 1: //Pane 3D
				final ToggleGroup groupT1 = new ToggleGroup();
				
				rbDotVector = new RadioButton("single dot /vector");
				rbDotVector.setToggleGroup(groupT1);
				rbDotVector.setSelected(true);
				grid.add(rbDotVector, 0, 0, 3, 1);
				
				rbPlotAll = new RadioButton("plot all");
				rbPlotAll.setToggleGroup(groupT1);
				grid.add(rbPlotAll, 0, 1, 3, 1);
		        
				rbPlotBetween = new RadioButton("plot between");
				rbPlotBetween.setToggleGroup(groupT1);
				grid.add(rbPlotBetween, 0, 2, 3, 1);
				
				txtPlotFrom = new TextField();
				txtPlotFrom.setMaxWidth(70);
				txtPlotFrom.setPromptText("e.g. 1");
				grid.add(txtPlotFrom, 0, 3);
				
				Label lblTo = new Label("to");
				lblTo.setMinWidth(23);
				grid.add(lblTo, 1, 3);
				
				txtPlotTo = new TextField();
				txtPlotTo.setMaxWidth(70);
				txtPlotTo.setPromptText("e.g. 100");
				grid.add(txtPlotTo, 0, 4);
				
				final Label positionPlot = new Label ("positions");
				grid.add(positionPlot, 1, 4, 2, 1);
				
				final Label lblRotate = new Label("Rotate");

				GridPane.setHalignment(lblRotate, HPos.LEFT);
				grid.add(lblRotate, 0, 7, 3, 1);
				
				rotateLeft = new Button("90° left");
				rotateLeft.setMinWidth(70);
				grid.add(rotateLeft, 0, 8);
				
				rotateRight = new Button("90° right");
				rotateRight.setMinWidth(70);
				grid.add(rotateRight, 2, 8);
				
				minDistanceAT = new CheckBox("Set minimum distance");
				grid.add(minDistanceAT, 0, 9, 3, 1);
				
				txtATDistance = new TextField();
				txtATDistance.setMaxWidth(70);
				txtATDistance.setPromptText("e.g. 10");
				grid.add(txtATDistance, 0, 10);
				
				final Label unitDistance = new Label ("cm");
				grid.add(unitDistance, 1, 10);
				
				t1.setText("Pane " + paneName + ": 3D");
				t1.setContent(grid);
				break;
			
			case 2: //Pane Bar-Chart
				final ToggleGroup groupT2 = new ToggleGroup();
				
				rbShowX = new RadioButton("show x");
				rbShowX.setToggleGroup(groupT2);
				rbShowX.setSelected(true);
				grid.add(rbShowX, 0, 0, 3, 1);
				
				rbShowY = new RadioButton("show y");
				rbShowY.setToggleGroup(groupT2);
				grid.add(rbShowY, 0, 1, 3, 1);
		        
				rbShowZ = new RadioButton("show z");
				rbShowZ.setToggleGroup(groupT2);
				grid.add(rbShowZ, 0, 2, 3, 1);
		        
				meanLast = new CheckBox("Calculate mean for last");
				grid.add(meanLast, 0, 5, 3, 1);
				
				txtMeanFor = new TextField();
				txtMeanFor.setMaxWidth(70);
				txtMeanFor.setPromptText("e.g. 100");
				grid.add(txtMeanFor, 0, 6);
				
				final Label positionMean = new Label ("positions");
				grid.add(positionMean, 1, 6);
				
				
				t2.setText("Pane " + paneName + ": Bar-Chart");
				t2.setContent(grid);
				
				break;
		
			case 3: //Pane Spline
				final ToggleGroup groupT3 = new ToggleGroup();
				
				rbShowXSpline = new RadioButton("show x");
				rbShowXSpline.setToggleGroup(groupT3);
				rbShowXSpline.setSelected(true);
				grid.add(rbShowXSpline, 0, 0, 3, 1);
				
				rbShowYSpline = new RadioButton("show y");
				rbShowYSpline.setToggleGroup(groupT3);
				grid.add(rbShowYSpline, 0, 1, 3, 1);
		        
				rbShowZSpline = new RadioButton("show z");
				rbShowZSpline.setToggleGroup(groupT3);
				grid.add(rbShowZSpline, 0, 2, 3, 1);
		        
				
				trendLast = new CheckBox("Calculate trend for last");
				grid.add(trendLast, 0, 5, 3, 1);
				
				txtTrendFor = new TextField();
				txtTrendFor.setMaxWidth(70);
				txtTrendFor.setPromptText("e.g. 100");
				grid.add(txtTrendFor, 0, 6);
				
				final Label positionTrend = new Label ("positions");
				grid.add(positionTrend, 1, 6);
				
				t3.setText("Pane : " + paneName + ": Spline");
				t3.setContent(grid);
				
				break;
			
			case 4: //Pane Parallel lines
				showXPL = new CheckBox ("show x");
		        showXPL.setSelected(true);
		        grid.add(showXPL, 0, 0);
		        
		        showYPL = new CheckBox ("show y");
		        showYPL.setSelected(true);
		        grid.add(showYPL, 0, 1);
		        
		        showZPL = new CheckBox ("show z");
		        showZPL.setSelected(true);
		        grid.add(showZPL, 0, 2);

		        showDistancePL = new CheckBox ("show distance");
		        showDistancePL.setSelected(true);
		        grid.add(showDistancePL, 0, 3);

		        t4.setText("Pane " + paneName + " : Parallel lines");
		        t4.setContent(grid);
				break;
		}
	}

	public static TitledPane getT1() {
		return t1;
	}

	public static TitledPane getT2() {
		return t2;
	}

	public static TitledPane getT3() {
		return t3;
	}

	public static TitledPane getT4() {
		return t4;
	}



	public static RadioButton getRbDotVector() {
		return rbDotVector;
	}


	public static void setRbDotVector(RadioButton rbDotVector) {
		TitledPaneObjects.rbDotVector = rbDotVector;
	}


	public static RadioButton getRbPlotAll() {
		return rbPlotAll;
	}


	public static void setRbPlotAll(RadioButton rbPlotAll) {
		TitledPaneObjects.rbPlotAll = rbPlotAll;
	}


	public static RadioButton getRbPlotBetween() {
		return rbPlotBetween;
	}


	public static void setRbPlotBetween(RadioButton rbPlotBetween) {
		TitledPaneObjects.rbPlotBetween = rbPlotBetween;
	}


	public static TextField getTxtPlotFrom() {
		return txtPlotFrom;
	}


	public static void setTxtPlotFrom(TextField txtPlotFrom) {
		TitledPaneObjects.txtPlotFrom = txtPlotFrom;
	}


	public static TextField getTxtPlotTo() {
		return txtPlotTo;
	}


	public static void setTxtPlotTo(TextField txtPlotTo) {
		TitledPaneObjects.txtPlotTo = txtPlotTo;
	}


	public static Button getRotateLeft() {
		return rotateLeft;
	}


	public static void setRotateLeft(Button rotateLeft) {
		TitledPaneObjects.rotateLeft = rotateLeft;
	}


	public static Button getRotateRight() {
		return rotateRight;
	}


	public static void setRotateRight(Button rotateRight) {
		TitledPaneObjects.rotateRight = rotateRight;
	}


	public static RadioButton getRbShowX() {
		return rbShowX;
	}


	public static void setRbShowX(RadioButton rbShowX) {
		TitledPaneObjects.rbShowX = rbShowX;
	}


	public static RadioButton getRbShowY() {
		return rbShowY;
	}


	public static void setRbShowY(RadioButton rbShowY) {
		TitledPaneObjects.rbShowY = rbShowY;
	}


	public static RadioButton getRbShowZ() {
		return rbShowZ;
	}


	public static void setRbShowZ(RadioButton rbShowZ) {
		TitledPaneObjects.rbShowZ = rbShowZ;
	}


	public static CheckBox getMeanLast() {
		return meanLast;
	}


	public static void setMeanLast(CheckBox meanLast) {
		TitledPaneObjects.meanLast = meanLast;
	}


	public static TextField getTxtMeanFor() {
		return txtMeanFor;
	}


	public static void setTxtMeanFor(TextField txtMeanFor) {
		TitledPaneObjects.txtMeanFor = txtMeanFor;
	}


	public static RadioButton getRbShowXSpline() {
		return rbShowXSpline;
	}


	public static void setRbShowXSpline(RadioButton rbShowXSpline) {
		TitledPaneObjects.rbShowXSpline = rbShowXSpline;
	}


	public static RadioButton getRbShowYSpline() {
		return rbShowYSpline;
	}


	public static void setRbShowYSpline(RadioButton rbShowYSpline) {
		TitledPaneObjects.rbShowYSpline = rbShowYSpline;
	}


	public static RadioButton getRbShowZSpline() {
		return rbShowZSpline;
	}


	public static void setRbShowZSpline(RadioButton rbShowZSpline) {
		TitledPaneObjects.rbShowZSpline = rbShowZSpline;
	}


	public static CheckBox getTrendLast() {
		return trendLast;
	}


	public static void setTrendLast(CheckBox trendLast) {
		TitledPaneObjects.trendLast = trendLast;
	}


	public static TextField getTxtTrendFor() {
		return txtTrendFor;
	}


	public static void setTxtTrendFor(TextField txtTrendFor) {
		TitledPaneObjects.txtTrendFor = txtTrendFor;
	}


	public static CheckBox getShowXPL() {
		return showXPL;
	}


	public static void setShowXPL(CheckBox showXPL) {
		TitledPaneObjects.showXPL = showXPL;
	}


	public static CheckBox getShowYPL() {
		return showYPL;
	}


	public static void setShowYPL(CheckBox showYPL) {
		TitledPaneObjects.showYPL = showYPL;
	}


	public static CheckBox getShowZPL() {
		return showZPL;
	}


	public static void setShowZPL(CheckBox showZPL) {
		TitledPaneObjects.showZPL = showZPL;
	}


	public static CheckBox getShowDistancePL() {
		return showDistancePL;
	}


	public static void setShowDistancePL(CheckBox showDistancePL) {
		TitledPaneObjects.showDistancePL = showDistancePL;
	}


	public static CheckBox getMinDistanceAT() {
		return minDistanceAT;
	}


	public static void setMinDistanceAT(CheckBox minDistanceAT) {
		TitledPaneObjects.minDistanceAT = minDistanceAT;
	}


	public static TextField getTxtATDistance() {
		return txtATDistance;
	}


	public static void setTxtATDistance(TextField txtATDistance) {
		TitledPaneObjects.txtATDistance = txtATDistance;
	}
	
	
	
	
	
}
