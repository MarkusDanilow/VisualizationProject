package com.base.engine;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.base.common.EngineEvent;
import com.base.common.EngineEventListener;

final class InputHandler {

	private static EngineEventListener eventListener;

	private static boolean useSystemEvents = true;

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
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				eventListener.notify(EngineEvent.MOVE_LEFT);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				eventListener.notify(EngineEvent.MOVE_RIGHT);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				eventListener.notify(EngineEvent.MOVE_FORWARD);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				eventListener.notify(EngineEvent.MOVE_BACKWARD);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				eventListener.notify(EngineEvent.MOVE_DOWN);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				eventListener.notify(EngineEvent.MOVE_UP);
			}
		}
	}

	static void getMouseInput() {

		while (Mouse.next()) {
			if (!(Console.isVisible() && Console.isCommandLineFocused())) {

				float dx = Mouse.getEventDX();
				float dy = Mouse.getEventDY();

				eventListener.notify(EngineEvent.LOOK_X, dx);
				eventListener.notify(EngineEvent.LOOK_Y, -dy);

				if (Mouse.getEventButtonState()) {
					switch (Mouse.getEventButton()) {

					}
				}

				// if (useSystemEvents)
				Mouse.setGrabbed(true);

			} else {
				Mouse.setGrabbed(false);
			}
		}

	}

}
