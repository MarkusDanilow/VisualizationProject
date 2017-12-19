package vis.frame;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JPanel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.Bloom;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;



public class RightFXPanel {

//	TODO: Eingabefelder TextFields nur auf Zahlen beschränken durch Validierung
	public JPanel getPanel (int rightSidebarWidth, int height) {
		
		JPanel rightSidebarPanel = new JPanel();
		JFXPanel rightSidebarPanelFX = new JFXPanel();
		rightSidebarPanel.add(rightSidebarPanelFX);
		
		rightSidebarPanel.setBounds(new Rectangle(0, 0, rightSidebarWidth, height));
		rightSidebarPanel.setPreferredSize(new Dimension(rightSidebarWidth, height));
		rightSidebarPanel.setVisible(true);

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
		gridt1.add(rbFreeze, 0, 1);        
		
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
		txtPlotFrom.setPromptText("e.g. 1");
		gridt2.add(txtPlotFrom, 0, 3);
		
		Label lblTo = new Label("to");
		lblTo.setMinWidth(23);
		gridt2.add(lblTo, 1, 3);
		
		TextField txtPlotTo = new TextField();
		txtPlotTo.setMaxWidth(70);
		txtPlotTo.setPromptText("e.g. 100");
		gridt2.add(txtPlotTo, 0, 4);
		
		final Label positionPlot = new Label ("positions");
		gridt2.add(positionPlot, 1, 4, 2, 1);
		
		final Label lblRotate = new Label("Rotate");

		GridPane.setHalignment(lblRotate, HPos.LEFT);
		gridt2.add(lblRotate, 0, 7, 3, 1);
		
		Button rotateLeft = new Button("90° left");
		rotateLeft.setMinWidth(70);
		gridt2.add(rotateLeft, 0, 8);
		
		Button rotateRight = new Button("90° right");
		rotateRight.setMinWidth(70);
		gridt2.add(rotateRight, 2, 8);
		
        t2.setText("Pane A: 3D");
        t2.setContent(gridt2);
        
        
        TitledPane t3 = new TitledPane();
        GridPane gridt3 = new GridPane();
        gridt3.setVgap(4);
        gridt3.setPadding(new Insets(5, 5, 5, 5));
        
		final ToggleGroup groupT3 = new ToggleGroup();
		
		RadioButton rbShowX = new RadioButton("show x");
		rbShowX.setToggleGroup(groupT3);
		rbShowX.setSelected(true);
		gridt3.add(rbShowX, 0, 0, 3, 1);
		
		RadioButton rbShowY = new RadioButton("show y");
		rbShowY.setToggleGroup(groupT3);
		gridt3.add(rbShowY, 0, 1, 3, 1);
        
		RadioButton rbShowZ = new RadioButton("show z");
		rbShowZ.setToggleGroup(groupT3);
		gridt3.add(rbShowZ, 0, 2, 3, 1);
        
		CheckBox meanLast = new CheckBox("Calculate mean for last");
		gridt3.add(meanLast, 0, 5, 3, 1);
		
		TextField txtMeanFor = new TextField();
		txtMeanFor.setMaxWidth(70);
		txtMeanFor.setPromptText("e.g. 100");
		gridt3.add(txtMeanFor, 0, 6);
		
		final Label positionMean = new Label ("positions");
		gridt3.add(positionMean, 1, 6);
		
		
        t3.setText("Pane B: Bar-Chart");
        t3.setContent(gridt3);
        
        TitledPane t4 = new TitledPane();
        GridPane gridt4 = new GridPane();
        gridt4.setVgap(4);
        gridt4.setPadding(new Insets(5, 5, 5, 5));
        
		final ToggleGroup groupT4 = new ToggleGroup();
		
		RadioButton rbShowXSpline = new RadioButton("show x");
		rbShowXSpline.setToggleGroup(groupT4);
		rbShowXSpline.setSelected(true);
		gridt4.add(rbShowXSpline, 0, 0, 3, 1);
		
		RadioButton rbShowYSpline = new RadioButton("show y");
		rbShowYSpline.setToggleGroup(groupT4);
		gridt4.add(rbShowYSpline, 0, 1, 3, 1);
        
		RadioButton rbShowZSpline = new RadioButton("show z");
		rbShowZSpline.setToggleGroup(groupT4);
		gridt4.add(rbShowZSpline, 0, 2, 3, 1);
        
		
		CheckBox trendLast = new CheckBox("Calculate trend for last");
		gridt4.add(trendLast, 0, 5, 3, 1);
		
		TextField txtTrendFor = new TextField();
		txtTrendFor.setMaxWidth(70);
		txtTrendFor.setPromptText("e.g. 100");
		gridt4.add(txtTrendFor, 0, 6);
		
		final Label positionTrend = new Label ("positions");
		gridt4.add(positionTrend, 1, 6);
		
        t4.setText("Pane C: Spline");
        t4.setContent(gridt4);
        
        
        TitledPane t5 = new TitledPane();
        GridPane gridt5 = new GridPane();
        gridt5.setVgap(4);
        gridt5.setPadding(new Insets(5, 5, 5, 5));
        
        CheckBox showXPL = new CheckBox ("show x");
        showXPL.setSelected(true);
        gridt5.add(showXPL, 0, 0);
        
        CheckBox showYPL = new CheckBox ("show y");
        showYPL.setSelected(true);
        gridt5.add(showYPL, 0, 1);
        
        CheckBox showZPL = new CheckBox ("show z");
        showZPL.setSelected(true);
        gridt5.add(showZPL, 0, 2);

        CheckBox showDistancePL = new CheckBox ("show distance");
        showDistancePL.setSelected(true);
        gridt5.add(showDistancePL, 0, 3);

        t5.setText("Pane D: Parallel Lines");
        t5.setContent(gridt5);
        
        
        TitledPane t6 = new TitledPane();
        GridPane gridt6 = new GridPane();
        gridt6.setVgap(4);
        gridt6.setPadding(new Insets(5, 5, 5, 5));
               
		CheckBox minDistanceAT = new CheckBox("Set minimum distance");
		gridt6.add(minDistanceAT, 0, 0, 3, 1);
		
		TextField txtATDistance = new TextField();
		txtATDistance.setMaxWidth(70);
		txtATDistance.setPromptText("e.g. 10");
		gridt6.add(txtATDistance, 0, 1);
		
		final Label unitDistance = new Label ("cm");
		gridt6.add(unitDistance, 1, 1);


		t6.setText("Pane X: Aimed Target");
		t6.setContent(gridt6);

		Accordion accordion = new Accordion();
		accordion.getPanes().addAll(t1, t2, t3, t4, t5, t6);
		accordion.setExpandedPane(t1);
		accordion.setPrefSize(rightSidebarWidth - 20, 200);

		GridPane gridMain = new GridPane();
		gridMain.setAlignment(Pos.TOP_CENTER);
		gridMain.setHgap(5);
		gridMain.setVgap(5);

		final Label labelMain = new Label("Settings");
		labelMain.setId("headline");
		gridMain.add(labelMain, 0, 0);

		gridMain.add(accordion, 0, 1);
		gridMain.setGridLinesVisible(false);

		Scene scene = new Scene(gridMain, rightSidebarWidth, height);
		scene.getStylesheets().add(RightFXPanel.class.getResource("style.css").toExternalForm());

		rightSidebarPanelFX.setScene(scene);

		/* ----------------------EventHandler---------------------- */

		//Buttons, Radiobuttons und Checkboxen
		
		rotateLeft.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("Hello World from rotateLeft");
			}
		});

		rotateRight.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("Hello World from rotateRight");
			}
		});

		rbLive.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("Hello World from Live");
			}
		});

		rbFreeze.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("Hello World from Freeze");
			}
		});

		rbDotVector.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("Hello World from rbDotVector");
			}
		});

		rbPlotAll.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("Hello World from rbPlotAll");
			}
		});

		rbPlotBetween.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("Hello World from rbPlotBetween");
			}
		});

		rbShowX.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("Hello World from rbShowX");
			}
		});

		rbShowY.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("Hello World from rbShowY");
			}
		});

		rbShowZ.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("Hello World from rbShowZ");
			}
		});

		meanLast.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("Hello World from meanLast");
			}
		});

		rbShowXSpline.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("Hello World from rbShowXSpline");
			}
		});

		rbShowYSpline.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("Hello World from rbShowYSpline");
			}
		});

		rbShowZSpline.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("Hello World from rbShowZSpline");
			}
		});

		trendLast.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("Hello World from trendLast");
			}
		});

		showXPL.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("Hello World from showXPL");
			}
		});

		showYPL.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("Hello World from showYPL");
			}
		});

		showZPL.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("Hello World from showZPL");
			}
		});

		showDistancePL.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("Hello World from showDistancePL");
			}
		});

		minDistanceAT.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("Hello World from minDistanceAT");
			}
		});

		//Textfelder
		
		txtPlotFrom.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> observable, final String oldValue,
					final String newValue) {
				System.out.println("Was changed");
			}
		});

		txtPlotTo.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> observable, final String oldValue,
					final String newValue) {
				System.out.println("Was changed");
			}
		});
		
		txtMeanFor.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> observable, final String oldValue,
					final String newValue) {
				System.out.println("Was changed");
			}
		});
		
		txtTrendFor.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> observable, final String oldValue,
					final String newValue) {
				System.out.println("Was changed");
			}
		});
		
		txtATDistance.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> observable, final String oldValue,
					final String newValue) {
				System.out.println("Was changed");
			}
		});
		
		// Testarea
		
	    rotateLeft.addEventHandler(MouseEvent.MOUSE_ENTERED,
	            new EventHandler<MouseEvent>() {
	              @Override
	              public void handle(MouseEvent e) {
	            	  Bloom bloom = new Bloom();
	            	  rotateLeft.setEffect(bloom);
	              }
	            });

		return rightSidebarPanel;

	}
}
