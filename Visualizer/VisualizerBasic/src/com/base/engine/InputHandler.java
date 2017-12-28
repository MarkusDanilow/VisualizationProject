package com.base.engine;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.base.common.EngineEvent;
import com.base.common.EngineEventListener;
import com.base.engine.interaction.GraphicsHoverHandler;
import com.base.engine.interaction.InteractableRectangle;
import com.base.engine.interaction.InteractionEventPointCloud;
import com.base.engine.interaction.InteractionEventResult;

final class InputHandler {

	private static EngineEventListener eventListener;

	private static boolean useSystemEvents = false;

	private static float currentX, currentY;

	static void create(EngineEventListener listener, boolean systemEvents) {
		try {
			if (listener == null) {
				throw new Exception("Listener must not be NULL!");
			}
			eventListener = listener;
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

	static void getKeyboardInput(EngineInterfaces engine) {
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

			// mouse movement currently for the upper left display which show
			// the 3D view
			for (int i = 0; i < engine.getViewports().size(); i++) {
				InteractableRectangle r = engine.getViewports().get(i);
				if (r.isInside(currentX, currentY)
						&& r.getCallback().getClass().getSimpleName().equalsIgnoreCase(InteractionEventPointCloud.class.getSimpleName())) {
					if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
						eventListener.notify(EngineEvent.MOVE_LEFT, i);
					}
					if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
						eventListener.notify(EngineEvent.MOVE_RIGHT, i);
					}
					if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
						eventListener.notify(EngineEvent.MOVE_FORWARD, i);
					}
					if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
						eventListener.notify(EngineEvent.MOVE_BACKWARD, i);
					}
					if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
						eventListener.notify(EngineEvent.MOVE_DOWN, i);
					}
					if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
						eventListener.notify(EngineEvent.MOVE_UP, i);
					}
				}
			}
		}
	}

	static void getMouseInput(EngineInterfaces engine) {

		while (Mouse.next()) {
			if ((!(Console.isVisible() && Console.isCommandLineFocused())) || !useSystemEvents) {

				float dx = Mouse.getEventDX();
				float dy = Mouse.getEventDY();

				currentX = Mouse.getX();
				currentY = Mouse.getY();
				float wheelDelta = Mouse.getDWheel();

				float hWidth = Display.getWidth() / 2f;
				float hHeight = Display.getHeight() / 2f;

				InteractionEventResult result = null;
				for (int i = 0; i < engine.getViewports().size(); i++) {
					InteractableRectangle r = engine.getViewports().get(i);
					if (r.isInside(currentX, currentY)) {
						result = (InteractionEventResult) r.getCallback().execute(eventListener, dx, dy, wheelDelta,
								hWidth, hHeight, currentX, currentY, i);
						GraphicsHoverHandler.setCurrentMouseBufferIndex(i);
						GraphicsHoverHandler.handleHover(engine, result.getLx(), result.getLy());
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
