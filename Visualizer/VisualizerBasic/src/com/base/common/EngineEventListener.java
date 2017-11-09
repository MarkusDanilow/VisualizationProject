package com.base.common;

public interface EngineEventListener {

	void notify(EngineEvent event, Object... data);
	
}
