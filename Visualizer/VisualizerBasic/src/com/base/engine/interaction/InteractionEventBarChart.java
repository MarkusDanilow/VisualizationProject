package com.base.engine.interaction;

public class InteractionEventBarChart extends AInteractionEvent {

	@Override
	public Object execute(Object... data) {
		return this.calcViewportCoords(data);
	}

}
