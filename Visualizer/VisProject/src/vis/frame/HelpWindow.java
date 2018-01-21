package vis.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class HelpWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	public HelpWindow() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(800, 530);
		this.setTitle("Hilfe");
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
		this.init();
		this.setVisible(true);
	}

	private void init() {
		this.setLayout(new BorderLayout());

		JTextArea text = new JTextArea();
		text.setEditable(false);
		text.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));

		text.append(
				"User Story: \n" + "--------------------------\n\n"
				+ "- Mithilfe des Prototyps sollen Bewegungsmuster des Trägers gemessen und analysiert werden\n" 
				+ "- Daten sollen On-Demand über verteiltes System verfügbar sein \n"
				+ "- Parkinsonkranker generiert Daten durch seine Körperbewegung \n"
				+ "- Prototyp erfasst Daten \n"
				+ "- Daten werden im Visualisierungstool verarbeitet und ausgegeben \n"
				+ "- Ableitung von Bewegungsmustern aus den generierten Daten \n"
				+ "- Interpretationsmöglichkeit bzgl. Fortschritt der Therapie \n\n"
				
				+ "Darstellungsparameter: \n" + "--------------------------\n\n"
				+ "- Kartografierte 3D Punktwolke (Längen- und Breitengrad) \n"
				+ "- Balkendiagramm mit Mittelwert (x-, y-, z-Rotation) \n"
				+ "- Liniendiagramm mit Trendfunktion (x-, y-, z-Rotation) \n"
				+ "- Parallele Koordinaten (x-, y-, z-Rotation, Distanz) \n\n"
				
				+ "Steuerung der 3D Ansicht: \n" + "--------------------------\n\n"
				+ "Ansicht verschieben: Linke Maustaste gedrückt halten und Maus bewegen.\n"
				+ "Bewegung: W/A/S/D, Shift, Leertaste für die Bewegung im 3D-Raum.\n" + "Zoom: Mausrad.");

		this.add(text, BorderLayout.CENTER);
	}

}
