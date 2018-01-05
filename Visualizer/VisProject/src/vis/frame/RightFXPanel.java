package vis.frame;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JPanel;

import com.base.engine.Settings;

import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;

public class RightFXPanel {

	private TitledPane t1;
	private TitledPane t2;
	private TitledPane t3;
	private TitledPane t4;
	private Accordion accordion;
	private JFXPanel rightSidebarPanelFX;
	private TitledPaneObjects pane;
	
//	TODO: Eingabefelder TextFields nur auf Zahlen beschränken durch Validierung
	public JPanel getPanel (int rightSidebarWidth, int height, MainWindow wnd) {
		
		JPanel rightSidebarPanel = new JPanel();
		rightSidebarPanel.setBackground(Settings.WND_COLOR.toAwtColor());
		
		rightSidebarPanelFX = new JFXPanel();
		rightSidebarPanel.add(rightSidebarPanelFX);
		
		rightSidebarPanel.setBounds(new Rectangle(0, 0, rightSidebarWidth, height));
		rightSidebarPanel.setPreferredSize(new Dimension(rightSidebarWidth, height));
		rightSidebarPanel.setVisible(true);

		pane = new TitledPaneObjects();
		//Pane A
		pane.setTitledPane(Settings.get3DView(), "A", wnd);
		t1 = pane.getT1();
				
        //Pane B
		pane.setTitledPane(Settings.getBarChartView(), "B", wnd);
		t2 = pane.getT2();
				
		//Pane C
		pane.setTitledPane(Settings.getLineChartView(), "C", wnd);
		t3 = pane.getT3();
		
		//Pane D
		pane.setTitledPane(Settings.getParallelCoordinatesView(), "D", wnd);
		t4 = pane.getT4();

		accordion = new Accordion();
		accordion.getPanes().addAll(t1, t2, t3, t4);
		accordion.setExpandedPane(t1);
		accordion.setPrefSize(rightSidebarWidth - 20, 200);

		GridPane gridMain = new GridPane();
		gridMain.setAlignment(Pos.TOP_CENTER);
		gridMain.setHgap(5);
		gridMain.setVgap(5);

		final Label labelMain = new Label("Einstellungen");
		labelMain.setId("headline");
		gridMain.add(labelMain, 0, 0);

		gridMain.add(accordion, 0, 1);
		gridMain.setGridLinesVisible(false);

		Scene scene = new Scene(gridMain, rightSidebarWidth, height);
		scene.getStylesheets().add(RightFXPanel.class.getResource("style.css").toExternalForm());

		rightSidebarPanelFX.setScene(scene);

		return rightSidebarPanel;

	}
	
	public void changeAccordion(String paneType, String paneName, MainWindow wnd) {
		TitledPane tmp = null;
		//Get new TitledPane and set it in the Accordion
		pane = new TitledPaneObjects();
		pane.setTitledPane(paneType, paneName, wnd);
		
		switch(paneType) {
		case "3D":
			tmp = pane.getT1();
			System.out.println(tmp);
			break;
			
		case "Balken-Diagramm":
			tmp = pane.getT2();
			System.out.println(tmp);
			break;
			
		case "Linien-Diagramm":
			tmp = pane.getT3();
			System.out.println(tmp);
			break;
			
		case "Parallele Koordinaten":
			tmp = pane.getT4();
			System.out.println(tmp);
			break;
		
		}
		
		switch(paneName) {
		case "A":
			this.accordion.getPanes().remove(0);
			this.accordion.getPanes().add(0, tmp);
			break;
		
		case "B":
			this.accordion.getPanes().remove(1);
			this.accordion.getPanes().add(1, tmp);
			break;
			
		case "C":
			this.accordion.getPanes().remove(2);
			this.accordion.getPanes().add(2, tmp);
			break;
			
		case "D":
			this.accordion.getPanes().remove(3);
			this.accordion.getPanes().add(3, tmp);
			break;
		}	
	}

	public TitledPane getPane(int ind) {
		return this.accordion.getPanes().get(ind);
	}
	
}
