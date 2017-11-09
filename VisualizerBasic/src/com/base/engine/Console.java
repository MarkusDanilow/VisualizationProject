package com.base.engine;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.HashMap;
import java.util.Map;

import com.base.common.Renderable;
import com.base.engine.font.FontManager;
import com.base.engine.font.FontRenderer;

class Console implements Renderable {

	private static boolean visible = false, commandLineFocused = false,
			cursorVisible = false, hintsVisible = false;
	
	public static boolean isCommandLineFocused() {
		return commandLineFocused;
	}

	public static void setCommandLineFocused(boolean commandLineFocused) {
		Console.commandLineFocused = commandLineFocused;
	}

	public static boolean isVisible() {
		return visible;
	}

	public static void setVisible(boolean visible) {
		Console.visible = visible;
	}

	public static boolean isHintsVisible() {
		return hintsVisible;
	}

	public static void setHintsVisible(boolean hintsVisible) {
		Console.hintsVisible = hintsVisible ;
	}

	private Map<Integer, String> data;
	private StringBuilder commandLine;
	private String hints = "";

	Console() {
		data = new HashMap<Integer, String>();
		clearCommandLine();
	}

	@Override
	public void prepare() {
		RenderUtils.switch2D(0, 0, Engine.DISPLAY_WIDTH, Engine.DISPLAY_HEIGHT);
	}

	@Override
	public void render(float[][] pData) {
		if (isVisible()) {
			prepare();
			glPushMatrix();

			glColor4f(0, 0, 0, 0.4f);
			glBegin(GL_QUADS);
			glVertex2f(10, 200);
			glVertex2f(250, 200);
			glVertex2f(250, 10);
			glVertex2f(10, 10);
			glEnd();
			for (Integer index : data.keySet()) {
				String line = data.get(index);
				FontRenderer.renderText(15, 20 * index + 15, line, FontManager
						.getUFontByName(FontManager.DEBUGGING_TEXT_SPECIAL));
			}
			if (isCommandLineFocused()) {
				glColor4f(1, 1, 1, 0.15f);
				glBegin(GL_QUADS);
				glVertex2f(10, Engine.DISPLAY_HEIGHT - 10);
				glVertex2f(Engine.DISPLAY_WIDTH - 10,
						Engine.DISPLAY_HEIGHT - 10);
				glVertex2f(Engine.DISPLAY_WIDTH - 10,
						Engine.DISPLAY_HEIGHT - 30);
				glVertex2f(10, Engine.DISPLAY_HEIGHT - 30);
				glEnd();
				String cmdTxt = "> " + getCommandLine().toString()
						+ (cursorVisible ? "_" : "");
				FontRenderer
						.renderText(
								15,
								Engine.DISPLAY_HEIGHT - 29,
								cmdTxt,
								FontManager
										.getUFontByName(FontManager.DEBUGGING_TEXT));

				if (isHintsVisible()) {
					glColor4f(0, 0, 0, 0.4f);
					glBegin(GL_QUADS);
					glVertex2f(10, Engine.DISPLAY_HEIGHT - 40);
					glVertex2f(800, Engine.DISPLAY_HEIGHT - 40);
					glVertex2f(800, Engine.DISPLAY_HEIGHT - 650);
					glVertex2f(10, Engine.DISPLAY_HEIGHT - 650);
					glEnd();
					FontRenderer.renderText(15, Engine.DISPLAY_HEIGHT - 599, hints, 
							FontManager.getUFontByName(FontManager.DEBUGGING_TEXT));
				}
			}
			glPopMatrix();
			close();
		}
	}

	void update(String... data) {
		updateData(data);
		cursorVisible = !cursorVisible;
	}

	void updateHint(String... hints) {
		if (hints != null) {
			this.hints = "" ;
			for (String s : hints) {
				this.hints += s + "\n";
			}
		}
	}

	@Override
	public void close() {
		// RenderUtils.switch3D();
	}

	private void updateData(String... data) {
		if (data != null) {
			this.data.clear();
			for (int i = 0; i < data.length; i++) {
				this.data.put(i, data[i]);
			}
		}
	}

	void updateCommandLineInput(String characters) {
		commandLine.append(characters);
	}

	void setCommandLineInput(String cmdLine) {
		clearCommandLine();
		updateCommandLineInput(cmdLine);
	}

	void removeLastCommandLineCharacter() {
		if (commandLine.toString().length() > 0) {
			commandLine.deleteCharAt(getCommandLine().toString().length() - 1);
		}
	}

	void showCommandLine() {
		setCommandLineFocused(true);
	}

	void hideCommandLine() {
		setCommandLineFocused(false);
	}

	void clearCommandLine() {
		commandLine = new StringBuilder();
	}

	public String getCommandLine() {
		return commandLine.toString();
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

}
