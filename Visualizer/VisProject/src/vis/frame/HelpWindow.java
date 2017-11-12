package vis.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class HelpWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	public HelpWindow() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(800, 400);
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

		text.append("Steuerung der 3D Ansicht: \n " + "--------------------------\n\n"
				+ "Ansicht verschieben: Linke Maustaste gedrückt halten und Maus bewegen.\n"
				+ "Bewegung: W/A/S/D, Shift, Leertaste für die Bewegung im 3D-Raum.\n" + "Zoom: Mausrad.");

		this.add(text, BorderLayout.CENTER);
	}

}
