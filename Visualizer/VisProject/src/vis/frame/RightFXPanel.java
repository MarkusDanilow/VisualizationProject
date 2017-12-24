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
	private Accordion accordion;
	private JFXPanel rightSidebarPanelFX;
	
	
//	TODO: Eingabefelder TextFields nur auf Zahlen beschränken durch Validierung
	public JPanel getPanel (int rightSidebarWidth, int height) {
		
		JPanel rightSidebarPanel = new JPanel();
		rightSidebarPanelFX = new JFXPanel();
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

		accordion = new Accordion();
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

		return rightSidebarPanel;

	}
	
	public void changeAccordion(int paneType, String paneName) {
		TitledPane tmp;
		//Get new TitledPane and set it in the Accordion
		TitledPaneObjects.setTitledPane(paneType, paneName);
		switch(paneType) {
		case 1:
			tmp = TitledPaneObjects.getT1();
			this.accordion.getPanes().remove(0);
			this.accordion.getPanes().add(tmp);
			break;
		
		case 2:
			tmp = TitledPaneObjects.getT2();
			this.accordion.getPanes().remove(1);
			this.accordion.getPanes().add(paneType, tmp);
			break;
			
		case 3:
			tmp = TitledPaneObjects.getT3();
			this.accordion.getPanes().remove(2);
			this.accordion.getPanes().add(paneType, tmp);
			break;
			
		case 4:
			tmp = TitledPaneObjects.getT4();
			this.accordion.getPanes().remove(3);
			this.accordion.getPanes().add(paneType, tmp);
			break;
		}
		
	}
	
	
}
