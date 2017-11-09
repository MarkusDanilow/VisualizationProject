package vis.events;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;

import vis.frame.MainWindow;

public class LoadDataEvent extends AbstractActionEvent {

	public LoadDataEvent(MainWindow window) {
		super(window);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try {
				this.window.app.loadData(selectedFile.getAbsolutePath());
			} catch (Exception e1) {
				e1.printStackTrace();
				this.window.app.displayError(e1);
			}
		}
	}

}
