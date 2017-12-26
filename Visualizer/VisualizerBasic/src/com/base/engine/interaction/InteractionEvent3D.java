package com.base.engine.interaction;

import org.lwjgl.input.Mouse;

import com.base.common.EngineEvent;
import com.base.common.EngineEventListener;
import com.base.common.resources.Callback;

public class InteractionEvent3D implements Callback {

	@Override
	public Object execute(Object... data) {
		EngineEventListener eventListener = (EngineEventListener) data[0];
		float dx = (float) data[1];
		float dy = (float) data[2];
		float wheelDelta = (float) data[3];
		if (Mouse.isButtonDown(0)) {
			eventListener.notify(EngineEvent.LOOK_X, dx, (int) data[8]);
			eventListener.notify(EngineEvent.LOOK_Y, -dy, (int) data[8]);
		}
		if (wheelDelta != 0) {
			eventListener.notify(EngineEvent.SCALE, wheelDelta / 1000f, (int) data[8]);
		}
		return new InteractionEventResult((int) data[8], 0, 0);
	}

}
