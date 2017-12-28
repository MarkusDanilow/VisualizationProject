package com.base.engine.interaction;

public class InteractionEventLineChart extends AInteractionEvent{

	@Override
	public Object execute(Object... data) {
		return this.calcViewportCoords(data);
	}

}
