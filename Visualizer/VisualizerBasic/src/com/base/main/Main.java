package com.base.main;

import com.base.engine.Engine;

/**
 * 
 * @author Markus
 *
 *	Brief description:
 *
 *	Press ESC to exit. 
 *	Press F1 to open/close console view.
 *	Press F3 to show/hide command line, when console is opened. 
 *	Press ENTER to execute commands.
 *
 */

public class Main {
	
	public static void main(String[] args) {
		new Engine(null, true);
	}

}
