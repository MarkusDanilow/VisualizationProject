package vis.frame;

import javax.swing.UIManager;

/**
 * Different kinds of {@link LookAndFeel}
 * 
 * @author Markus
 *
 */
public enum LookAndFeel {

	SYSTEM(UIManager.getSystemLookAndFeelClassName()), DEFAULT("javax.swing.plaf.metal.MetalLookAndFeel");
	public final String name;

	private LookAndFeel(final String name) {
		this.name = name;
	}
}
