package com.base.engine.interaction;

import com.base.common.resources.Callback;
import com.base.engine.interaction.data.Rectangle;

public class InteractableRectangle extends Rectangle {

	private Callback callback;

	public InteractableRectangle(float x1, float x2, float y1, float y2) {
		super(x1, x2, y1, y2);
	}

	public InteractableRectangle(float x1, float x2, float y1, float y2, Callback callback) {
		super(x1, x2, y1, y2);
		this.callback = callback;
	}

	public Callback getCallback() {
		return callback;
	}

	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	public Object execute(Object... data) {
		return callback.execute(data);
	}

}
