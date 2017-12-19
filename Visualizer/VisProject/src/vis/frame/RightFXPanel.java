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

	private TitledPane t1;
	private TitledPane t2;
	private TitledPane t3;
	private TitledPane t4;
	
	
//	TODO: Eingabefelder TextFields nur auf Zahlen beschränken durch Validierung
	public JPanel getPanel (int rightSidebarWidth, int height) {
		
		JPanel rightSidebarPanel = new JPanel();
		JFXPanel rightSidebarPanelFX = new JFXPanel();
		rightSidebarPanel.add(rightSidebarPanelFX);
		
		rightSidebarPanel.setBounds(new Rectangle(0, 0, rightSidebarWidth, height));
		rightSidebarPanel.setPreferredSize(new Dimension(rightSidebarWidth, height));
		rightSidebarPanel.setVisible(true);

		new TitledPaneObjects();
		//Pane A
		TitledPaneObjects.setTitledPane(1, "A");
		t1 = TitledPaneObjects.getT1();
				
        //Pane B
		TitledPaneObjects.setTitledPane(2, "B");
		t2 = TitledPaneObjects.getT2();
				
		//Pane C
		TitledPaneObjects.setTitledPane(3, "C");
		t3 = TitledPaneObjects.getT3();
		
		//Pane D
		TitledPaneObjects.setTitledPane(4, "D");
		t4 = TitledPaneObjects.getT4();

		Accordion accordion = new Accordion();
		accordion.getPanes().addAll(t1, t2, t3, t4);
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
		
		/*
		TitledPaneObjects.getRotateLeft().setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("Hello World from rotateLeft");
			}
		});
		
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
		*/
		return rightSidebarPanel;

	}
}
