package vis.frame;

import javafx.application.Application; 
import javafx.geometry.Pos; 
import javafx.scene.Scene; 
import javafx.scene.control.Accordion; 
import javafx.scene.control.Label; 
import javafx.scene.control.TitledPane; 
import javafx.scene.layout.GridPane; 
import javafx.stage.Stage; 

public class TestAccordion extends Application { 

    @Override 
    public void start(Stage primaryStage) { 
        primaryStage.setTitle("Accordion Beispiel"); 
        final Label label = new Label("Klassiker"); 
        label.setAlignment(Pos.CENTER); 
        label.setId("title-label"); 
        String t1Text = "Hubraum:\t500ccm\nBaujahr:\t1954\nLeistung:\t53PS"; 
        String t2Text = "Hubraum:\t500ccm\nBaujahr:\t1957\nLeistung:\t72PS"; 
        String t3Text = "Hubraum:\t640ccm\nBaujahr:\t1924\nLeistung:\t14PS"; 
        String t4Text = "Hubraum:\t500ccm\nBaujahr:\t1953\nLeistung:\t60PS"; 
        TitledPane t1 = new TitledPane("Norton Manx", new Label(t1Text)); 
        TitledPane t2 = new TitledPane("Moto Guzzi V8", new Label(t2Text)); 
        TitledPane t3 = new TitledPane("Megola", new Label(t3Text)); 
        TitledPane t4 = new TitledPane("BMW RS", new Label(t4Text)); 
        Accordion accordion = new Accordion(); 
        accordion.getPanes().addAll(t1, t2, t3, t4); 
        accordion.setExpandedPane(t3); 

        GridPane grid = new GridPane(); 
        grid.setAlignment(Pos.TOP_CENTER); 
        grid.setHgap(5); 
        grid.setVgap(5); 
        grid.add(label, 0, 0); 
        grid.add(accordion, 0, 1); 
        grid.setGridLinesVisible(false); 

        Scene scene = new Scene(grid, 200, 230); 
//        scene.getStylesheets().add(TestAccordion.class 
//                .getResource("styles/AccordionClass.css").toExternalForm()); 
        primaryStage.setScene(scene); 
        primaryStage.show(); 
    } 

    public static void main(String[] args) { 
        launch(args); 
    } 
} 
