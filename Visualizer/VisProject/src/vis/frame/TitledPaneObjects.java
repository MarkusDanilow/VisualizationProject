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
import vis.controller.VisController;

public class TitledPaneObjects {

	private  TitledPane t1 = new TitledPane();
	private  TitledPane t2 = new TitledPane();
	private  TitledPane t3 = new TitledPane();
	private  TitledPane t4 = new TitledPane();

	private RadioButton rbDotVector;
	private RadioButton rbPlotAll;
	private RadioButton rbPlotBetween;
	private TextField txtPlotFrom;
	private TextField txtPlotTo;
	private Button rotateLeft;
	private Button rotateRight;
	private RadioButton rbShowX;
	private RadioButton rbShowY;
	private RadioButton rbShowZ;
	private CheckBox meanLast;
	private TextField txtMeanFor;
	private RadioButton rbShowXSpline;
	private RadioButton rbShowYSpline;
	private RadioButton rbShowZSpline;
	private CheckBox trendLast;
	private TextField txtTrendFor;
	private CheckBox showXPL;
	private CheckBox showYPL;
	private CheckBox showZPL;
	private CheckBox showDistancePL;
	private CheckBox minDistanceAT;
	private TextField txtATDistance;

	public void setTitledPane(String paneType, String paneName, MainWindow wnd) {

		GridPane grid = new GridPane();
		grid.setVgap(4);
		grid.setPadding(new Insets(5, 5, 5, 5));
		
		//TODO: Bereinigen
//		FXHandlerPaneA aPHandler = new FXHandlerPaneA(wnd);
//		FXHandlerPaneB bPHandler = new FXHandlerPaneB(wnd);
//		FXHandlerPaneC cPHandler = new FXHandlerPaneC(wnd);
//		FXHandlerPaneD dPHandler = new FXHandlerPaneD(wnd);
		
		switch (paneType) {
		case "3D": // Pane 3D
			final ToggleGroup groupT1 = new ToggleGroup();

			rbDotVector = new RadioButton("Einzelpunkt/-vektor");
			rbDotVector.setToggleGroup(groupT1);
			rbDotVector.setSelected(true);
			grid.add(rbDotVector, 0, 0, 3, 1);

			rbDotVector.setOnAction((event) -> {
				VisController.einzelpunktVektor();
			});
			
			rbPlotAll = new RadioButton("Plotte alle");
			rbPlotAll.setToggleGroup(groupT1);
			grid.add(rbPlotAll, 0, 1, 3, 1);

			rbPlotAll.setOnAction((event) -> {
				VisController.plotAll();
			});
			
			rbPlotBetween = new RadioButton("Plotte");
			rbPlotBetween.setToggleGroup(groupT1);
			grid.add(rbPlotBetween, 0, 2, 3, 1);
			
			rbPlotBetween.setOnAction((event) -> {
				VisController.plotFromTo();
			});
			
			Label lblTo = new Label("bis");
			lblTo.setMinWidth(23);
			grid.add(lblTo, 1, 3, 3, 1);
			
			txtPlotFrom = new TextField("1");
			txtPlotFrom.setMaxWidth(80);
			txtPlotFrom.setPromptText("1");
			grid.add(txtPlotFrom, 0, 3);
			//Handler
			txtPlotFrom.textProperty().addListener((observable, oldValue, newValue) -> {
			    if(!newValue.matches("[0-9]+")) {
			    	newValue = newValue.replaceAll("[^0-9]","");
			    	txtPlotFrom.setText(newValue);
			    }
				
				System.out.println("PlotFrom changed from " + oldValue + " to " + newValue);
			});
			
			txtPlotTo = new TextField("100");
			txtPlotTo.setMaxWidth(80);
			txtPlotTo.setPromptText("100");
			grid.add(txtPlotTo, 0, 4);
			//Handler
			txtPlotTo.textProperty().addListener((observable, oldValue, newValue) -> {
			    if(!newValue.matches("[0-9]+")) {
			    	newValue = newValue.replaceAll("[^0-9]","");
			    	txtPlotTo.setText(newValue);
			    }
				
			    System.out.println("PlotTo changed from " + oldValue + " to " + newValue);
			});
			
			final Label positionPlot = new Label("Punkte");
			grid.add(positionPlot, 1, 4, 2, 1);

			final Label lblRotate = new Label("Rotate");

			GridPane.setHalignment(lblRotate, HPos.LEFT);
			grid.add(lblRotate, 0, 7, 3, 1);

			rotateLeft = new Button("90° links");
			rotateLeft.setMinWidth(70);
			grid.add(rotateLeft, 0, 8);
			//Handler
			rotateLeft.setOnAction((event) -> {
				System.out.println("Links");
			});

			rotateRight = new Button("90° rechts");
			rotateRight.setMinWidth(70);
			grid.add(rotateRight, 2, 8);
			//Handler
			rotateRight.setOnAction((event) -> {
				System.out.println("Rechts");
			});

			minDistanceAT = new CheckBox("Minimale Distanz");
			grid.add(minDistanceAT, 0, 9, 3, 1);
			//Handler
			minDistanceAT.setOnAction((event) -> {
				System.out.println("Distanz");
			});

			txtATDistance = new TextField("10");
			txtATDistance.setMaxWidth(80);
			txtATDistance.setPromptText("10");
			grid.add(txtATDistance, 0, 10);
			//Handler
			txtATDistance.textProperty().addListener((observable, oldValue, newValue) -> {
			    if(!newValue.matches("[0-9]+")) {
			    	newValue = newValue.replaceAll("[^0-9]","");
			    	txtATDistance.setText(newValue);
			    }
				
			    System.out.println("Distance changed from " + oldValue + " to " + newValue);
			});

			final Label unitDistance = new Label("cm");
			grid.add(unitDistance, 1, 10);

			t1.setText("Pane " + paneName + ": 3D");
			t1.setId(paneName);
			t1.setContent(grid);
			break;

		case "Balken-Diagramm": // Pane Bar-Chart
			final ToggleGroup groupT2 = new ToggleGroup();

			rbShowX = new RadioButton("x anzeigen");
			rbShowX.setToggleGroup(groupT2);
			rbShowX.setSelected(true);
			grid.add(rbShowX, 0, 0, 3, 1);

			rbShowX.setOnAction((event) -> {
				System.out.println("show x BK");
			});
			
			rbShowY = new RadioButton("y anzeigen");
			rbShowY.setToggleGroup(groupT2);
			grid.add(rbShowY, 0, 1, 3, 1);

			rbShowY.setOnAction((event) -> {
				System.out.println("show y BK");
			});
			
			rbShowZ = new RadioButton("z anzeigen");
			rbShowZ.setToggleGroup(groupT2);
			grid.add(rbShowZ, 0, 2, 3, 1);

			rbShowZ.setOnAction((event) -> {
				System.out.println("show z BK");
			});
						
			meanLast = new CheckBox("Durchschnitt für die letzten");
			grid.add(meanLast, 0, 5, 3, 1);
			meanLast.setOnAction((event) -> {
				System.out.println("Checkbox durchscnitt BK");
			});

			txtMeanFor = new TextField("100");
			txtMeanFor.setMaxWidth(80);
			txtMeanFor.setPromptText("100");
			grid.add(txtMeanFor, 0, 6);
			//Handler
			txtMeanFor.textProperty().addListener((observable, oldValue, newValue) -> {
			    if(!newValue.matches("[0-9]+")) {
			    	newValue = newValue.replaceAll("[^0-9]","");
			    	txtMeanFor.setText(newValue);
			    }
				
			    System.out.println("Durchschnitt " + oldValue + " to " + newValue);
			});

			final Label positionMean = new Label("Punkte anzeigen");
			grid.add(positionMean, 1, 6);

			t2.setText("Pane " + paneName + ": Balken-Diagramm");
			t2.setId(paneName);
			t2.setContent(grid);

			break;

		case "Linien-Diagramm": // Pane Spline
			final ToggleGroup groupT3 = new ToggleGroup();

			rbShowXSpline = new RadioButton("x anzeigen");
			rbShowXSpline.setToggleGroup(groupT3);
			rbShowXSpline.setSelected(true);
			grid.add(rbShowXSpline, 0, 0, 3, 1);
			
			rbShowXSpline.setOnAction((event) -> {
				System.out.println("Show x Spline");
			});

			rbShowYSpline = new RadioButton("y anzeigen");
			rbShowYSpline.setToggleGroup(groupT3);
			grid.add(rbShowYSpline, 0, 1, 3, 1);

			rbShowYSpline.setOnAction((event) -> {
				System.out.println("Show y Spline");
			});
			
			rbShowZSpline = new RadioButton("z anzeigen");
			rbShowZSpline.setToggleGroup(groupT3);
			grid.add(rbShowZSpline, 0, 2, 3, 1);
			
			rbShowZSpline.setOnAction((event) -> {
				System.out.println("Show z Spline");
			});
					
			trendLast = new CheckBox("Trend für die letzten");
			grid.add(trendLast, 0, 5, 3, 1);
			trendLast.setOnAction((event) -> {
				System.out.println("Trend LD");
			});

			txtTrendFor = new TextField("100");
			txtTrendFor.setMaxWidth(80);
			txtTrendFor.setPromptText("100");
			grid.add(txtTrendFor, 0, 6);
			txtTrendFor.textProperty().addListener((observable, oldValue, newValue) -> {
			    if(!newValue.matches("[0-9]+")) {
			    	newValue = newValue.replaceAll("[^0-9]","");
			    	txtTrendFor.setText(newValue);
			    }
				
			    System.out.println("Trend " + oldValue + " to " + newValue);
			});
			

			final Label positionTrend = new Label("Punkte anzeigen");
			grid.add(positionTrend, 1, 6);

			t3.setText("Pane " + paneName + ": Linien-Diagramm");
			t3.setId(paneName);
			t3.setContent(grid);

			break;

		case "Parallele Koordinaten": // Pane Parallel lines
			showXPL = new CheckBox("x anzeigen");
			showXPL.setSelected(true);
			grid.add(showXPL, 0, 0);
			
			showXPL.setOnAction((event) -> {
				System.out.println("Show x PL");
			});

			showYPL = new CheckBox("y anzeigen");
			showYPL.setSelected(true);
			grid.add(showYPL, 0, 1);
			
			showYPL.setOnAction((event) -> {
				System.out.println("Show y PL");
			});

			showZPL = new CheckBox("z anzeigen");
			showZPL.setSelected(true);
			grid.add(showZPL, 0, 2);
			
			showZPL.setOnAction((event) -> {
				System.out.println("Show z PL");
			});
			
			showDistancePL = new CheckBox("Distanz anzeigen");
			showDistancePL.setSelected(true);
			grid.add(showDistancePL, 0, 3);
			
			showDistancePL.setOnAction((event) -> {
				System.out.println("Distant PL");
			});

			t4.setText("Pane " + paneName + " : Parallele Koordinaten");
			t4.setId(paneName);
			t4.setContent(grid);
			break;
		}
	}

	public  TitledPane getT1() {
		return t1;
	}

	public  TitledPane getT2() {
		return t2;
	}

	public  TitledPane getT3() {
		return t3;
	}

	public  TitledPane getT4() {
		return t4;
	}

	public  RadioButton getRbDotVector() {
		return rbDotVector;
	}

	public  void setRbDotVector(RadioButton rbDotVector) {
		this.rbDotVector = rbDotVector;
	}

	public  RadioButton getRbPlotAll() {
		return rbPlotAll;
	}

	public  void setRbPlotAll(RadioButton rbPlotAll) {
		this.rbPlotAll = rbPlotAll;
	}

	public  RadioButton getRbPlotBetween() {
		return rbPlotBetween;
	}

	public  void setRbPlotBetween(RadioButton rbPlotBetween) {
		this.rbPlotBetween = rbPlotBetween;
	}

	public  TextField getTxtPlotFrom() {
		return txtPlotFrom;
	}

	public  void setTxtPlotFrom(TextField txtPlotFrom) {
		this.txtPlotFrom = txtPlotFrom;
	}

	public  TextField getTxtPlotTo() {
		return txtPlotTo;
	}

	public  void setTxtPlotTo(TextField txtPlotTo) {
		this.txtPlotTo = txtPlotTo;
	}

	public  Button getRotateLeft() {
		return rotateLeft;
	}

	public  void setRotateLeft(Button rotateLeft) {
		this.rotateLeft = rotateLeft;
	}

	public  Button getRotateRight() {
		return rotateRight;
	}

	public  void setRotateRight(Button rotateRight) {
		this.rotateRight = rotateRight;
	}

	public  RadioButton getRbShowX() {
		return rbShowX;
	}

	public  void setRbShowX(RadioButton rbShowX) {
		this.rbShowX = rbShowX;
	}

	public  RadioButton getRbShowY() {
		return rbShowY;
	}

	public  void setRbShowY(RadioButton rbShowY) {
		this.rbShowY = rbShowY;
	}

	public  RadioButton getRbShowZ() {
		return rbShowZ;
	}

	public  void setRbShowZ(RadioButton rbShowZ) {
		this.rbShowZ = rbShowZ;
	}

	public  CheckBox getMeanLast() {
		return meanLast;
	}

	public  void setMeanLast(CheckBox meanLast) {
		this.meanLast = meanLast;
	}

	public  TextField getTxtMeanFor() {
		return txtMeanFor;
	}

	public  void setTxtMeanFor(TextField txtMeanFor) {
		this.txtMeanFor = txtMeanFor;
	}

	public  RadioButton getRbShowXSpline() {
		return rbShowXSpline;
	}

	public  void setRbShowXSpline(RadioButton rbShowXSpline) {
		this.rbShowXSpline = rbShowXSpline;
	}

	public  RadioButton getRbShowYSpline() {
		return rbShowYSpline;
	}

	public  void setRbShowYSpline(RadioButton rbShowYSpline) {
		this.rbShowYSpline = rbShowYSpline;
	}

	public  RadioButton getRbShowZSpline() {
		return rbShowZSpline;
	}

	public  void setRbShowZSpline(RadioButton rbShowZSpline) {
		this.rbShowZSpline = rbShowZSpline;
	}

	public  CheckBox getTrendLast() {
		return trendLast;
	}

	public  void setTrendLast(CheckBox trendLast) {
		this.trendLast = trendLast;
	}

	public  TextField getTxtTrendFor() {
		return txtTrendFor;
	}

	public  void setTxtTrendFor(TextField txtTrendFor) {
		this.txtTrendFor = txtTrendFor;
	}

	public  CheckBox getShowXPL() {
		return showXPL;
	}

	public  void setShowXPL(CheckBox showXPL) {
		this.showXPL = showXPL;
	}

	public  CheckBox getShowYPL() {
		return showYPL;
	}

	public  void setShowYPL(CheckBox showYPL) {
		this.showYPL = showYPL;
	}

	public  CheckBox getShowZPL() {
		return showZPL;
	}

	public  void setShowZPL(CheckBox showZPL) {
		this.showZPL = showZPL;
	}

	public  CheckBox getShowDistancePL() {
		return showDistancePL;
	}

	public  void setShowDistancePL(CheckBox showDistancePL) {
		this.showDistancePL = showDistancePL;
	}

	public  CheckBox getMinDistanceAT() {
		return minDistanceAT;
	}

	public  void setMinDistanceAT(CheckBox minDistanceAT) {
		this.minDistanceAT = minDistanceAT;
	}

	public  TextField getTxtATDistance() {
		return txtATDistance;
	}

	public  void setTxtATDistance(TextField txtATDistance) {
		this.txtATDistance = txtATDistance;
	}

}
