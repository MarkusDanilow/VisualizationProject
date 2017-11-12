package com.base.engine;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.base.common.EngineEvent;
import com.base.common.EngineEventListener;

final class InputHandler {

	private static EngineEventListener eventListener;

	private static boolean useSystemEvents = false;

	private static int currentX, currentY;

	static void create(EngineEventListener listener, boolean systemEvents) {
		try {
			if (listener == null) {
				throw new Exception("Listener must not be NULL!");
			}
			eventListener = listener;
			// useSystemEvents = systemEvents;
			Keyboard.create();
			Mouse.create();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void destroy() {
		Mouse.destroy();
		Keyboard.destroy();
	}

	static boolean isValidCharacter(Character character) {
		return character >= 32;
	}

	static void getKeyboardInput() {
		while (Keyboard.next() && useSystemEvents) {
			if (Keyboard.isKeyDown(Keyboard.KEY_F11)) {
				// eventListener.notify(EngineEvent.FULLSCREEN_TOGGLE);
			} else if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				if (Console.isCommandLineFocused()) {
					eventListener.notify(EngineEvent.COMMAND_LINE_TOGGLE);
				} else {
					eventListener.notify(EngineEvent.EXIT);
				}
			} else if (Keyboard.isKeyDown(Keyboard.KEY_F1)) {
				eventListener.notify(EngineEvent.CONSOLE_TOOGLE);
			} else if (Keyboard.isKeyDown(Keyboard.KEY_F3)) {
				if (Console.isVisible()) {
					eventListener.notify(EngineEvent.COMMAND_LINE_TOGGLE);
				}
			} else {
				if (Console.isVisible() && Console.isCommandLineFocused()) {
					if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
						eventListener.notify(EngineEvent.COMMAND_LINE_EXECUTE);
					} else if (Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
						eventListener.notify(EngineEvent.COMMAND_LINE_REMOVE_LAST);
					} else if (Keyboard.isKeyDown(Keyboard.KEY_TAB)) {
						eventListener.notify(EngineEvent.COMMAND_LINE_COMPLETE);
					} else if (Keyboard.getEventKeyState() && isValidCharacter(Keyboard.getEventCharacter())) {
						eventListener.notify(EngineEvent.COMMAND_LINE_TYPE, Keyboard.getEventCharacter());
					}
				}

			}
		}
		if ((!(Console.isVisible() && Console.isCommandLineFocused())) || (!useSystemEvents)) {
			
			// mouse movement currently for the upper left display which show the 3D view
			if ((currentX < Display.getWidth() / 2) && (currentY > Display.getHeight() / 2)) {
				if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
					eventListener.notify(EngineEvent.MOVE_LEFT, 0);
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
					eventListener.notify(EngineEvent.MOVE_RIGHT, 0);
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
					eventListener.notify(EngineEvent.MOVE_FORWARD, 0);
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
					eventListener.notify(EngineEvent.MOVE_BACKWARD, 0);
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					eventListener.notify(EngineEvent.MOVE_DOWN, 0);
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
					eventListener.notify(EngineEvent.MOVE_UP, 0);
				}
			}
		}
	}

	static void getMouseInput() {

		while (Mouse.next()) {
			if ((!(Console.isVisible() && Console.isCommandLineFocused())) || !useSystemEvents) {

				float dx = Mouse.getEventDX();
				float dy = Mouse.getEventDY();

				currentX = Mouse.getX();
				currentY = Mouse.getY();

				if ((currentX < Display.getWidth() / 2) && (currentY > Display.getHeight() / 2)) {
					if (Mouse.isButtonDown(0)) {
						eventListener.notify(EngineEvent.LOOK_X, dx, 0);
						eventListener.notify(EngineEvent.LOOK_Y, -dy, 0);
					}
					int wheelDelta = Mouse.getDWheel();
					if (wheelDelta != 0) {
						eventListener.notify(EngineEvent.SCALE, wheelDelta / 1000f, 0);
					}
				}

				if (useSystemEvents)
					Mouse.setGrabbed(true);

			} else {
				Mouse.setGrabbed(false);
			}
		}

	}

}
