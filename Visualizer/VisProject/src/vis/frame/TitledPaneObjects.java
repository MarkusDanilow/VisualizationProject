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
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import vis.controller.VisController;

public class TitledPaneObjects {

	private TitledPane t1 = new TitledPane();
	private TitledPane t2 = new TitledPane();
	private TitledPane t3 = new TitledPane();
	private TitledPane t4 = new TitledPane();

	private final ToggleGroup groupT1 = new ToggleGroup();
	private final ToggleGroup groupT2 = new ToggleGroup();
	private final ToggleGroup groupT3 = new ToggleGroup();

	// Tooltips
	private final Tooltip tooltip = new Tooltip("Alles ausgeben");
	private final Tooltip tooltip1 = new Tooltip("Bestimmten Einzelpunkt/-vektor ausgeben");
	private final Tooltip tooltip2 = new Tooltip("Ausgabe zwischen zwei bestimmten Punkten");
	private final Tooltip tooltip3 = new Tooltip("Wert zwischen 1 und 100000000 eingeben. Buchstaben sind nicht erlaubt");
	private final Tooltip tooltip4 = new Tooltip("Koordinatenkreutz nach rechts rotieren");
	private final Tooltip tooltip5 = new Tooltip("Koordinatenkreutz nach rechts rotieren");
	//private final Tooltip tooltip6 = new Tooltip("Minimale Distanz setzen:");
	private final Tooltip tooltip7 = new Tooltip("x anzeigen");
	private final Tooltip tooltip8 = new Tooltip("y anzeigen");
	private final Tooltip tooltip9 = new Tooltip("z anzeigen");
	private final Tooltip tooltip10 = new Tooltip("Durchschnitt der letzten Punkte anzeigen");
	private final Tooltip tooltip11 = new Tooltip("Anzahl der letzten Punkte");
	private final Tooltip tooltip12 = new Tooltip("x anzeigen");
	private final Tooltip tooltip13 = new Tooltip("y anzeigen");
	private final Tooltip tooltip14 = new Tooltip("z anzeigen");
	private final Tooltip tooltip15 = new Tooltip("Trend der letzen Punkte anzeigen");
	private final Tooltip tooltip16 = new Tooltip("Anzahl der letzten Punkte");
	private final Tooltip tooltip17 = new Tooltip("x anzeigen");
	private final Tooltip tooltip18 = new Tooltip("y anzeigen");
	private final Tooltip tooltip19 = new Tooltip("z anzeigen");
	private final Tooltip tooltip20 = new Tooltip("Distanz anzeigen");
	private final Tooltip tooltip21 = new Tooltip("Zeigt die vollständigen parallelen Koordinaten an");
	
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
	private Label positionPlot = new Label("Punkte");
	private CheckBox fullParallel;


	public void setTitledPane(String paneType, String paneName, MainWindow wnd) {

		GridPane grid = new GridPane();
		grid.setVgap(4);
		grid.setPadding(new Insets(5, 5, 5, 5));

		// TODO: Bereinigen
		// FXHandlerPaneA aPHandler = new FXHandlerPaneA(wnd);
		// FXHandlerPaneB bPHandler = new FXHandlerPaneB(wnd);
		// FXHandlerPaneC cPHandler = new FXHandlerPaneC(wnd);
		// FXHandlerPaneD dPHandler = new FXHandlerPaneD(wnd);

		switch (paneType) {
		case "3D": // Pane 3D

			rbPlotAll = new RadioButton("Plotte alle");
			rbPlotAll.setToggleGroup(groupT1);
			rbPlotAll.setSelected(true);

			rbPlotAll.setTooltip(tooltip);

			grid.add(rbPlotAll, 0, 0, 3, 1);

			rbPlotAll.setOnAction((event) -> {
				VisController.plotAll();
			});

			rbDotVector = new RadioButton("Einzelpunkt/-vektor");
			rbDotVector.setToggleGroup(groupT1);

			rbDotVector.setTooltip(tooltip1);

			grid.add(rbDotVector, 0, 1, 3, 1);

			rbDotVector.setOnAction((event) -> {
				VisController.einzelpunktVektor();
			});

			rbPlotBetween = new RadioButton("Plotte");
			rbPlotBetween.setToggleGroup(groupT1);

			rbPlotBetween.setTooltip(tooltip2);

			grid.add(rbPlotBetween, 0, 2, 3, 1);

			rbPlotBetween.setOnAction((event) -> {
				if (!this.getTxtPlotFrom().isEmpty() && !this.getTxtPlotTo().isEmpty()) {					
					if(Integer.parseInt(this.getTxtPlotFrom()) >  Integer.parseInt(this.getTxtPlotTo())) {
						positionPlot.setTextFill(Color.RED);
						positionPlot.setText("Punkte (Wert falsch!)");
					} else {
						positionPlot.setTextFill(Color.WHITE);
						positionPlot.setText("Punkte");
						VisController.plotFromTo(this.getTxtPlotFrom(), this.getTxtPlotTo());
					}
				}
			});
			
			Label lblTo = new Label("bis");
			lblTo.setMinWidth(23);
			grid.add(lblTo, 1, 3, 3, 1);

			txtPlotFrom = new TextField();
			txtPlotFrom.setMaxWidth(80);
			txtPlotFrom.setPromptText("z. B. 1");

			txtPlotFrom.setTooltip(tooltip3);

			grid.add(txtPlotFrom, 0, 3);
			// Handler
			txtPlotFrom.textProperty().addListener((observable, oldValue, newValue) -> {
				if (!newValue.matches("[0-9]+")) {
					newValue = newValue.replaceAll("[^0-9]", "");
					txtPlotFrom.setText(newValue);
				}

				if (!this.getTxtPlotFrom().isEmpty() && !this.getTxtPlotTo().isEmpty() && this.getRbPlotBetween().isSelected()) {
				
					if(Integer.parseInt(newValue) >  Integer.parseInt(this.getTxtPlotTo())) {
						positionPlot.setTextFill(Color.RED);
						positionPlot.setText("Punkte (Wert falsch!)");
					} else {
						positionPlot.setTextFill(Color.WHITE);
						positionPlot.setText("Punkte");
						VisController.plotFromTo(this.getTxtPlotFrom(), this.getTxtPlotTo());
					}
				}
				
				if(this.getTxtPlotFrom().isEmpty()) {
					txtPlotTo.setDisable(true);
					txtPlotTo.setText("");
				}
				else {
					txtPlotTo.setDisable(false);
				}
				
			});

			txtPlotTo = new TextField();
			txtPlotTo.setMaxWidth(80);
			txtPlotTo.setPromptText("z. B. 100");
			txtPlotTo.setDisable(true);
			
			txtPlotTo.setTooltip(tooltip3);

			grid.add(txtPlotTo, 0, 4);
			
			
			// Handler
			txtPlotTo.textProperty().addListener((observable, oldValue, newValue) -> {
				if (!newValue.matches("[0-9]+")) {
					newValue = newValue.replaceAll("[^0-9]", "");
					txtPlotTo.setText(newValue);
				}
				

				
				if (!this.getTxtPlotFrom().isEmpty() && !this.getTxtPlotTo().isEmpty() && this.getRbPlotBetween().isSelected()) {
					
					if(Integer.parseInt(newValue) <  Integer.parseInt(this.getTxtPlotFrom())) {
						positionPlot.setTextFill(Color.RED);
						positionPlot.setText("Punkte (Wert falsch!)");
					} else {
						positionPlot.setTextFill(Color.WHITE);
						positionPlot.setText("Punkte");
						VisController.plotFromTo(this.getTxtPlotFrom(), this.getTxtPlotTo());
					}	
				}
			});

			grid.add(positionPlot, 1, 4, 3, 1);

			final Label lblRotate = new Label("Kamera rotieren");

			GridPane.setHalignment(lblRotate, HPos.LEFT);
			grid.add(lblRotate, 0, 7, 3, 1);

			rotateLeft = new Button("90° links");
			rotateLeft.setMinWidth(70);

			rotateLeft.setTooltip(tooltip4);

			grid.add(rotateLeft, 0, 8);
			// Handler
			rotateLeft.setOnAction((event) -> {
				VisController.rotateLeft();
			});

			rotateRight = new Button("90° rechts");
			rotateRight.setMinWidth(70);

			txtPlotFrom.setTooltip(tooltip5);

			grid.add(rotateRight, 2, 8);
			// Handler
			rotateRight.setOnAction((event) -> {
				VisController.rotateRight();
			});

			
			
			
			// minDistanceAT = new CheckBox("Minimale Distanz");
			//
			// minDistanceAT.setTooltip(tooltip6);
			//
			// grid.add(minDistanceAT, 0, 9, 3, 1);
			// //Handler
			// minDistanceAT.setOnAction((event) -> {
			// if(!this.getTxtATDistance().isEmpty()) {
			// VisController.setMinimum(this.getTxtATDistance());
			// }
			// });
			//
			// txtATDistance = new TextField();
			// txtATDistance.setMaxWidth(80);
			// txtATDistance.setPromptText("z. B. 10");
			//
			// txtATDistance.setTooltip(tooltip6);
			//
			// grid.add(txtATDistance, 0, 10);
			// //Handler
			// txtATDistance.textProperty().addListener((observable, oldValue,
			// newValue) -> {
			// if(!newValue.matches("[0-9]+")) {
			// newValue = newValue.replaceAll("[^0-9]","");
			// txtATDistance.setText(newValue);
			// }
			// if(!this.getTxtATDistance().isEmpty()) {
			// VisController.setMinimum(this.getTxtATDistance());
			// }
			// });
			//
			// final Label unitDistance = new Label("cm");
			// grid.add(unitDistance, 1, 10);

			t1.setText("Pane " + paneName + ": 3D");
			t1.setId(paneName);
			t1.setContent(grid);
			break;

		case "Balken-Diagramm": // Pane Bar-Chart
			rbShowX = new RadioButton("x anzeigen");
			rbShowX.setToggleGroup(groupT2);
			rbShowX.setSelected(true);
			rbShowX.setUserData("x");

			rbShowX.setTooltip(tooltip7);

			grid.add(rbShowX, 0, 0, 3, 1);

			rbShowX.setOnAction((event) -> {
				VisController.bdShowX();

				if (this.getMeanLast().isSelected() && !this.getTxtMeanFor().isEmpty()) {
					VisController.calculateMean(this.getTxtMeanFor(),
							this.getGroupT2().getSelectedToggle().getUserData().toString());
				}
			});

			rbShowY = new RadioButton("y anzeigen");
			rbShowY.setToggleGroup(groupT2);
			rbShowY.setUserData("y");

			rbShowY.setTooltip(tooltip8);

			grid.add(rbShowY, 0, 1, 3, 1);

			rbShowY.setOnAction((event) -> {
				VisController.bdShowY();

				if (this.getMeanLast().isSelected() && !this.getTxtMeanFor().isEmpty()) {
					VisController.calculateMean(this.getTxtMeanFor(),
							this.getGroupT2().getSelectedToggle().getUserData().toString());
				}
			});

			rbShowZ = new RadioButton("z anzeigen");
			rbShowZ.setToggleGroup(groupT2);
			rbShowZ.setUserData("z");

			rbShowZ.setTooltip(tooltip9);

			grid.add(rbShowZ, 0, 2, 3, 1);

			rbShowZ.setOnAction((event) -> {
				VisController.bdShowZ();

				if (this.getMeanLast().isSelected() && !this.getTxtMeanFor().isEmpty()) {
					VisController.calculateMean(this.getTxtMeanFor(),
							this.getGroupT2().getSelectedToggle().getUserData().toString());
				}
			});

			meanLast = new CheckBox("Durchschnitt für die letzten");

			meanLast.setTooltip(tooltip10);

			grid.add(meanLast, 0, 5, 3, 1);
			meanLast.setOnAction((event) -> {
				VisController.toggleStats(meanLast.isSelected());
				if (!this.getTxtMeanFor().isEmpty() && this.getMeanLast().isSelected()) {
					VisController.calculateMean(this.getTxtMeanFor(),
							this.getGroupT2().getSelectedToggle().getUserData().toString());
				}
			});

			txtMeanFor = new TextField();
			txtMeanFor.setMaxWidth(80);
			txtMeanFor.setPromptText("z. B. 100");

			txtMeanFor.setTooltip(tooltip11);

			grid.add(txtMeanFor, 0, 6);
			// Handler
			txtMeanFor.textProperty().addListener((observable, oldValue, newValue) -> {
				if (!newValue.matches("[0-9]+")) {
					newValue = newValue.replaceAll("[^0-9]", "");
					this.setTxtMeanFor(newValue);
				}

				if (!this.getTxtMeanFor().isEmpty() && this.getMeanLast().isSelected()) {
					VisController.calculateMean(this.getTxtMeanFor(),
							this.getGroupT2().getSelectedToggle().getUserData().toString());
				}
			});

			final Label positionMean = new Label("Punkte anzeigen");
			grid.add(positionMean, 1, 6);

			t2.setText("Pane " + paneName + ": Balken-Diagramm");
			t2.setId(paneName);
			t2.setContent(grid);

			break;

		case "Linien-Diagramm": // Pane Spline
			rbShowXSpline = new RadioButton("x anzeigen");
			rbShowXSpline.setToggleGroup(groupT3);
			rbShowXSpline.setSelected(true);
			rbShowXSpline.setUserData("x");

			rbShowXSpline.setTooltip(tooltip12);

			grid.add(rbShowXSpline, 0, 0, 3, 1);

			rbShowXSpline.setOnAction((event) -> {
				VisController.ldShowX();

				if (this.getTrendLast().isSelected() && !this.getTxtTrendFor().isEmpty()) {
					VisController.trend(this.getTxtTrendFor(),
							this.getGroupT3().getSelectedToggle().getUserData().toString());
				}
			});

			rbShowYSpline = new RadioButton("y anzeigen");
			rbShowYSpline.setToggleGroup(groupT3);
			rbShowYSpline.setUserData("y");

			rbShowYSpline.setTooltip(tooltip13);

			grid.add(rbShowYSpline, 0, 1, 3, 1);

			rbShowYSpline.setOnAction((event) -> {
				VisController.ldShowY();

				if (this.getTrendLast().isSelected() && !this.getTxtTrendFor().isEmpty()) {
					VisController.trend(this.getTxtTrendFor(),
							this.getGroupT3().getSelectedToggle().getUserData().toString());
				}
			});

			rbShowZSpline = new RadioButton("z anzeigen");
			rbShowZSpline.setToggleGroup(groupT3);
			rbShowZSpline.setUserData("z");

			rbShowZSpline.setTooltip(tooltip14);

			grid.add(rbShowZSpline, 0, 2, 3, 1);

			rbShowZSpline.setOnAction((event) -> {
				VisController.ldShowZ();

				if (this.getTrendLast().isSelected() && !this.getTxtTrendFor().isEmpty()) {
					VisController.trend(this.getTxtTrendFor(),
							this.getGroupT3().getSelectedToggle().getUserData().toString());
				}
			});

			trendLast = new CheckBox("Trend für die letzten");

			trendLast.setTooltip(tooltip15);

			grid.add(trendLast, 0, 5, 3, 1);
			trendLast.setOnAction((event) -> {
				VisController.toggleStats(trendLast.isSelected());
				if (!this.getTxtTrendFor().isEmpty() && this.getTrendLast().isSelected()) {
					VisController.trend(this.getTxtTrendFor(),
							this.getGroupT3().getSelectedToggle().getUserData().toString());
				}
			});

			txtTrendFor = new TextField();
			txtTrendFor.setMaxWidth(80);
			txtTrendFor.setPromptText("100");

			txtTrendFor.setTooltip(tooltip16);

			grid.add(txtTrendFor, 0, 6);
			txtTrendFor.textProperty().addListener((observable, oldValue, newValue) -> {
				if (!newValue.matches("[0-9]+")) {
					newValue = newValue.replaceAll("[^0-9]", "");
					txtTrendFor.setText(newValue);
				}

				if (!this.getTxtTrendFor().isEmpty() && this.getTrendLast().isSelected()) {
					VisController.trend(this.getTxtTrendFor(),
							this.getGroupT3().getSelectedToggle().getUserData().toString());
				}
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

			showXPL.setTooltip(tooltip17);

			grid.add(showXPL, 0, 0);

			showXPL.setOnAction((event) -> {
				VisController.pkShowX(showXPL.isSelected());
			});

			showYPL = new CheckBox("y anzeigen");
			showYPL.setSelected(true);

			showYPL.setTooltip(tooltip18);

			grid.add(showYPL, 0, 1);

			showYPL.setOnAction((event) -> {
				VisController.pkShowY(showYPL.isSelected());
			});

			showZPL = new CheckBox("z anzeigen");
			showZPL.setSelected(true);

			showZPL.setTooltip(tooltip19);

			grid.add(showZPL, 0, 2);

			showZPL.setOnAction((event) -> {
				VisController.pkShowZ(showZPL.isSelected());
			});

			showDistancePL = new CheckBox("Distanz anzeigen");
			showDistancePL.setSelected(true);

			showDistancePL.setTooltip(tooltip20);

			grid.add(showDistancePL, 0, 3);

			showDistancePL.setOnAction((event) -> {
				VisController.pkShowDistance(showDistancePL.isSelected());
			});
			
			fullParallel = new CheckBox("Vollst. Parall. Koord.");
			fullParallel.setSelected(false);

			fullParallel.setTooltip(tooltip21);

			grid.add(fullParallel, 0, 4, 3, 1);
		
			fullParallel.setOnAction((event) -> {
				VisController.showFullParallel();
			});

			t4.setText("Pane " + paneName + " : Parallele Koordinaten");
			t4.setId(paneName);
			t4.setContent(grid);
			break;
		}
	}

	public static void setTooltipLeft(MainWindow wnd) {
		final Tooltip toolPaneA = new Tooltip("Ansicht für Pane A (links oben) ändern");
		final Tooltip toolPaneB = new Tooltip("Ansicht für Pane B (rechts oben) ändern");
		final Tooltip toolPaneC = new Tooltip("Ansicht für Pane C (links unten) ändern");
		final Tooltip toolPaneD = new Tooltip("Ansicht für Pane D (rechts unten) ändern");
		final Tooltip toolLiveView = new Tooltip("Live-Ansicht für Live-Daten");
		final Tooltip toolFreeze = new Tooltip("Aktuelle Daten-Visualisiseren einfrieren zum genauereren Betrachten");
		final Tooltip toolColor = new Tooltip("Hintergrundfarbe ändern");
		final Tooltip toolTimeFrom = new Tooltip("Beginndatum");
		final Tooltip toolTimeTo = new Tooltip("Enddatum");

		wnd.getFxPanelObjectLeft().getCbPaneA().setTooltip(toolPaneA);
		wnd.getFxPanelObjectLeft().getCbPaneB().setTooltip(toolPaneB);
		wnd.getFxPanelObjectLeft().getCbPaneC().setTooltip(toolPaneC);
		wnd.getFxPanelObjectLeft().getCbPaneD().setTooltip(toolPaneD);
		wnd.getFxPanelObjectLeft().getRbFreeze().setTooltip(toolFreeze);
		wnd.getFxPanelObjectLeft().getRbLive().setTooltip(toolLiveView);
		wnd.getFxPanelObjectLeft().getInvertBackground().setTooltip(toolColor);
		wnd.getFxPanelObjectLeft().getDateFrom().setTooltip(toolTimeFrom);
		wnd.getFxPanelObjectLeft().getDateTo().setTooltip(toolTimeTo);

	}

	public TitledPane getT1() {
		return t1;
	}

	public TitledPane getT2() {
		return t2;
	}

	public TitledPane getT3() {
		return t3;
	}

	public TitledPane getT4() {
		return t4;
	}

	public RadioButton getRbDotVector() {
		return rbDotVector;
	}

	public void setRbDotVector(RadioButton rbDotVector) {
		this.rbDotVector = rbDotVector;
	}

	public RadioButton getRbPlotAll() {
		return rbPlotAll;
	}

	public void setRbPlotAll(RadioButton rbPlotAll) {
		this.rbPlotAll = rbPlotAll;
	}

	public RadioButton getRbPlotBetween() {
		return rbPlotBetween;
	}

	public void setRbPlotBetween(RadioButton rbPlotBetween) {
		this.rbPlotBetween = rbPlotBetween;
	}

	public String getTxtPlotFrom() {
		return txtPlotFrom.getText();
	}

	public void setTxtPlotFrom(TextField txtPlotFrom) {
		this.txtPlotFrom = txtPlotFrom;
	}

	public String getTxtPlotTo() {
		return txtPlotTo.getText();
	}

	public void setTxtPlotTo(TextField txtPlotTo) {
		this.txtPlotTo = txtPlotTo;
	}

	public Button getRotateLeft() {
		return rotateLeft;
	}

	public void setRotateLeft(Button rotateLeft) {
		this.rotateLeft = rotateLeft;
	}

	public Button getRotateRight() {
		return rotateRight;
	}

	public void setRotateRight(Button rotateRight) {
		this.rotateRight = rotateRight;
	}

	public RadioButton getRbShowX() {
		return rbShowX;
	}

	public void setRbShowX(RadioButton rbShowX) {
		this.rbShowX = rbShowX;
	}

	public RadioButton getRbShowY() {
		return rbShowY;
	}

	public void setRbShowY(RadioButton rbShowY) {
		this.rbShowY = rbShowY;
	}

	public RadioButton getRbShowZ() {
		return rbShowZ;
	}

	public void setRbShowZ(RadioButton rbShowZ) {
		this.rbShowZ = rbShowZ;
	}

	public CheckBox getMeanLast() {
		return meanLast;
	}

	public void setMeanLast(CheckBox meanLast) {
		this.meanLast = meanLast;
	}

	public String getTxtMeanFor() {
		return txtMeanFor.getText();
	}

	public void setTxtMeanFor(String txtMeanFor) {
		this.txtMeanFor.setText(txtMeanFor);
	}

	public RadioButton getRbShowXSpline() {
		return rbShowXSpline;
	}

	public void setRbShowXSpline(RadioButton rbShowXSpline) {
		this.rbShowXSpline = rbShowXSpline;
	}

	public RadioButton getRbShowYSpline() {
		return rbShowYSpline;
	}

	public void setRbShowYSpline(RadioButton rbShowYSpline) {
		this.rbShowYSpline = rbShowYSpline;
	}

	public RadioButton getRbShowZSpline() {
		return rbShowZSpline;
	}

	public void setRbShowZSpline(RadioButton rbShowZSpline) {
		this.rbShowZSpline = rbShowZSpline;
	}

	public CheckBox getTrendLast() {
		return trendLast;
	}

	public void setTrendLast(CheckBox trendLast) {
		this.trendLast = trendLast;
	}

	public String getTxtTrendFor() {
		return txtTrendFor.getText();
	}

	public void setTxtTrendFor(TextField txtTrendFor) {
		this.txtTrendFor = txtTrendFor;
	}

	public CheckBox getShowXPL() {
		return showXPL;
	}

	public void setShowXPL(CheckBox showXPL) {
		this.showXPL = showXPL;
	}

	public CheckBox getShowYPL() {
		return showYPL;
	}

	public void setShowYPL(CheckBox showYPL) {
		this.showYPL = showYPL;
	}

	public CheckBox getShowZPL() {
		return showZPL;
	}

	public void setShowZPL(CheckBox showZPL) {
		this.showZPL = showZPL;
	}

	public CheckBox getShowDistancePL() {
		return showDistancePL;
	}

	public void setShowDistancePL(CheckBox showDistancePL) {
		this.showDistancePL = showDistancePL;
	}

	public CheckBox getMinDistanceAT() {
		return minDistanceAT;
	}

	public void setMinDistanceAT(CheckBox minDistanceAT) {
		this.minDistanceAT = minDistanceAT;
	}

	public String getTxtATDistance() {
		return txtATDistance.getText();
	}

	public void setTxtATDistance(TextField txtATDistance) {
		this.txtATDistance = txtATDistance;
	}

	public ToggleGroup getGroupT1() {
		return groupT1;
	}

	public ToggleGroup getGroupT2() {
		return groupT2;
	}

	public ToggleGroup getGroupT3() {
		return groupT3;
	}

}
