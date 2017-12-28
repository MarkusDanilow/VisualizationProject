package com.base.engine.interaction;

public class InteractionEventParallelCoordinates extends AInteractionEvent{

	@Override
	public Object execute(Object... data) {
		return this.calcViewportCoords(data);
	}

}
