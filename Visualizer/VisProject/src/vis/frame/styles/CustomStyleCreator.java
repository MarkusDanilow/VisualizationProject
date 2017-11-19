package vis.frame.styles;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;

import com.toedter.calendar.JDateChooser;

import vis.frame.ColorChooserButton;

public class CustomStyleCreator {

	private static Map<Class<?>, IComponentStyle> styles = new HashMap<>();

	static {
		styles.put(JButton.class, new ButtonStyle());
		styles.put(JDateChooser.class, new ButtonStyle());
		styles.put(ColorChooserButton.class, new ButtonStyle());
		styles.put(JComboBox.class, new ButtonStyle());
	}

	public static void applyCustomStyle(JFrame window) {
		List<Component> compList = getAllComponents(window);
		for (Component c : compList) {
			applyCommonStyles(c);
			if (styles.containsKey(c.getClass())) {
				try {
					styles.get(c.getClass()).setStyle(c);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void applyCommonStyles(Component c) {
		c.setBackground(Color.DARK_GRAY);
		if (c instanceof JComponent) {
			((JComponent) c).setBorder(null);
		}
	}

	private static List<Component> getAllComponents(final Container c) {
		Component[] comps = c.getComponents();
		List<Component> compList = new ArrayList<Component>();
		for (Component comp : comps) {
			compList.add(comp);
			if (comp instanceof Container)
				compList.addAll(getAllComponents((Container) comp));
		}
		return compList;
	}

}
