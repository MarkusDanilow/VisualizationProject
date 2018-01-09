
package vis.frame;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JPanel;

import com.base.engine.Settings;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import vis.controller.VisController;
import vis.events.fx.FXChangeView0;
import vis.events.fx.FXChangeView1;
import vis.events.fx.FXChangeView2;
import vis.events.fx.FXChangeView3;
import vis.events.fx.FXInvertColors;

public class LeftFXPanel {

	private ComboBox<String> cbPaneA;
	private ComboBox<String> cbPaneB;
	private ComboBox<String> cbPaneC;
	private ComboBox<String> cbPaneD;
	private RadioButton rbFreeze;
	private RadioButton rbLive;
	private Button invertBackground;
	private DatePicker dateFrom;
	private DatePicker dateTo;

	public JPanel getPanel(int leftSidebarWidth, int height, MainWindow wnd) {

		JPanel leftSidebarPanel = new JPanel();
		leftSidebarPanel.setBackground(Settings.WND_COLOR.toAwtColor());
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

		// Inhalte
		GridPane gridVis = new GridPane();
		gridVis.setVgap(4);
		gridVis.setPadding(new Insets(5, 5, 5, 5));

		// Vis-Techniken
		final Label visTech = new Label("Visualisierungstechniken");
		visTech.setId("headline");
		gridVis.add(visTech, 0, 0, 2, 1);

		ObservableList<String> optionsPane = FXCollections.observableArrayList(Settings.get3DView(),
				Settings.getBarChartView(), Settings.getLineChartView(), Settings.getParallelCoordinatesView());

		ObservableList<String> option3D = 
			    FXCollections.observableArrayList(
			        "3D"
			    );
		
		final Label paneA = new Label("Pane A:");
		gridVis.add(paneA, 0, 1);
		
		cbPaneA = new ComboBox<String>(option3D);		
		cbPaneA.setValue(optionsPane.get(0));
		
		cbPaneA.valueProperty().addListener(new FXChangeView0(wnd));
		gridVis.add(cbPaneA, 1, 1);

		final Label paneB = new Label("Pane B:");
		gridVis.add(paneB, 0, 2);

		cbPaneB = new ComboBox<String>(optionsPane);
		cbPaneB.setValue(optionsPane.get(1));
		cbPaneB.valueProperty().addListener(new FXChangeView2(wnd));

		gridVis.add(cbPaneB, 1, 2);

		final Label paneC = new Label("Pane C:");
		gridVis.add(paneC, 0, 3);

		cbPaneC = new ComboBox<String>(optionsPane);
		cbPaneC.setValue(optionsPane.get(2));
		cbPaneC.valueProperty().addListener(new FXChangeView1(wnd));

		gridVis.add(cbPaneC, 1, 3);

		final Label paneD = new Label("Pane D:");
		gridVis.add(paneD, 0, 4);

		cbPaneD = new ComboBox<String>(optionsPane);
		cbPaneD.setValue(optionsPane.get(3));
		cbPaneD.valueProperty().addListener(new FXChangeView3(wnd));

		gridVis.add(cbPaneD, 1, 4);

		gridMain.add(gridVis, 0, 0);

		GridPane gridCommon = new GridPane();
		gridCommon.setVgap(4);
		gridCommon.setHgap(5);
		gridCommon.setPadding(new Insets(5, 5, 5, 5));

		// final Label lblParameterFilter = new Label("Parameter filter
		// (global)");
		final Label lblCommon = new Label("Allgemeine Einstellungen");
		lblCommon.setId("headline");
		gridCommon.add(lblCommon, 0, 0, 2, 1);

		final ToggleGroup groupT1 = new ToggleGroup();

		rbFreeze = new RadioButton("Freeze-Ansicht");
		rbFreeze.setToggleGroup(groupT1);
		rbFreeze.setSelected(true);
		rbFreeze.setOnAction((event) -> {
			VisController.toggleLiveMode(!rbFreeze.isSelected());
		});

		gridCommon.add(rbFreeze, 0, 1);

		rbLive = new RadioButton("Live-Ansicht");
		rbLive.setToggleGroup(groupT1);
		rbLive.setOnAction((event) -> {
			VisController.toggleLiveMode(rbLive.isSelected());
		});

		gridCommon.add(rbLive, 0, 2);

		invertBackground = new Button("Farben invertieren");
		invertBackground.setMinWidth(70);
		invertBackground.setOnAction(new FXInvertColors(wnd));
		gridCommon.add(invertBackground, 0, 5);

		/*
		 * CheckBox cbXPosition = new CheckBox("x-Position");
		 * gridParameter.add(cbXPosition, 0, 1);
		 * 
		 * ColorPicker xChooser = new ColorPicker(Color.RED);
		 * xChooser.setMaxWidth(100); gridParameter.add(xChooser, 1, 1);
		 * 
		 * CheckBox cbYPosition = new CheckBox("y-Position");
		 * gridParameter.add(cbYPosition, 0, 2);
		 * 
		 * ColorPicker yChooser = new ColorPicker(Color.BLUE);
		 * yChooser.setMaxWidth(100); gridParameter.add(yChooser, 1, 2);
		 * 
		 * CheckBox cbZPosition = new CheckBox("z-Position");
		 * gridParameter.add(cbZPosition, 0, 3);
		 * 
		 * ColorPicker zChooser = new ColorPicker(Color.GREEN);
		 * zChooser.setMaxWidth(100); gridParameter.add(zChooser, 1, 3);
		 * 
		 * CheckBox cbTargetObject = new CheckBox("Target object");
		 * gridParameter.add(cbTargetObject, 0, 4);
		 * 
		 * ColorPicker toChooser = new ColorPicker(Color.YELLOW);
		 * toChooser.setMaxWidth(100); gridParameter.add(toChooser, 1, 4);
		 */
		gridMain.add(gridCommon, 0, 1);

		GridPane gridDate = new GridPane();
		gridDate.setVgap(4);
		gridDate.setPadding(new Insets(5, 5, 5, 5));

		final Label lblDatePicker = new Label("Zeitraum auswählen (geplante Funktion)");
		lblDatePicker.setId("headline");
		gridDate.add(lblDatePicker, 0, 0, 2, 1);

		final Label lblDateFrom = new Label("Von:");
		gridDate.add(lblDateFrom, 0, 1);

		dateFrom = new DatePicker();
		gridDate.add(dateFrom, 1, 1);

		final Label lblDateTo = new Label("Bis:");
		gridDate.add(lblDateTo, 0, 2);

		dateTo = new DatePicker();
		gridDate.add(dateTo, 1, 2);

		gridMain.add(gridDate, 0, 2);
		
		gridMain.add(new Separator(Orientation.HORIZONTAL), 0, 5);
		
		// Legende => Farben etc.
		GridPane gridLegend = new GridPane();
		gridLegend.setVgap(4);
		gridLegend.setPadding(new Insets(5,5,5,5));
		
		Label lblLegend = new Label("Legende");
		lblLegend.setId("headline");
		gridLegend.add(lblLegend, 0, 0);
		
		gridLegend.add(new Label("x:"), 0, 1);
		Label xColor = new Label();
		xColor.setStyle("-fx-background-color: #ff0000;");
		xColor.setMinWidth(120);
		gridLegend.add(xColor, 1, 1);
		
		gridLegend.add(new Label("y:"), 0, 2);
		Label yColor = new Label();
		yColor.setStyle("-fx-background-color: #0000ff;");
		yColor.setMinWidth(120);
		gridLegend.add(yColor, 1, 2);

		gridLegend.add(new Label("z:"), 0, 3);
		Label zColor = new Label();
		zColor.setStyle("-fx-background-color: #00ff00;");
		zColor.setMinWidth(120);
		gridLegend.add(zColor, 1, 3);

		gridLegend.add(new Label("älteste Daten:"), 0, 4);
		Label oldColor = new Label();
		oldColor.setStyle("-fx-background-color: #ffff00;");
		oldColor.setMinWidth(120);
		gridLegend.add(oldColor, 1, 4);

		gridLegend.add(new Label("neuste Daten:"), 0, 5);
		Label newColor = new Label();
		newColor.setStyle("-fx-background-color: #ff5500;");
		newColor.setMinWidth(120);
		gridLegend.add(newColor, 1, 5);
		
		gridMain.add(gridLegend, 0, 8);		

		Scene scene = new Scene(gridMain, leftSidebarWidth, height);
		scene.getStylesheets().add(RightFXPanel.class.getResource("style.css").toExternalForm());

		leftSidebarPanelFX.setScene(scene);

		return leftSidebarPanel;
	}

	public ComboBox<String> getCbPaneA() {
		return cbPaneA;
	}

	public ComboBox<String> getCbPaneB() {
		return cbPaneB;
	}

	public ComboBox<String> getCbPaneC() {
		return cbPaneC;
	}

	public ComboBox<String> getCbPaneD() {
		return cbPaneD;
	}

	public RadioButton getRbFreeze() {
		return rbFreeze;
	}

	public RadioButton getRbLive() {
		return rbLive;
	}

	public Button getInvertBackground() {
		return invertBackground;
	}

	public DatePicker getDateFrom() {
		return dateFrom;
	}

	public DatePicker getDateTo() {
		return dateTo;
	}

}
