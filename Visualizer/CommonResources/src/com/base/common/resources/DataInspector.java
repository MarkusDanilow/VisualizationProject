package com.base.common.resources;

import java.util.ArrayList;
import java.util.List;

public final class DataInspector {

	public static final boolean notNull(Object... data){
		if(data != null){
			for(Object o: data){
				if(o == null){
					return false ; 
				}
			}
			return true ; 
		}
		return false ; 
	}
	
	public static final List<Object> extractSumbittedData(Object data) {
		List<Object> result = new ArrayList<Object>();
		if (ClazzAnalyzer.isArray(data.getClass())) {
			for (Object object1 : (Object[]) data) {
				if (ClazzAnalyzer.isArray(object1.getClass())) {
					for (Object object2 : (Object[]) object1) {
						result.add(object2);
					}
				} else {
					result.add(object1);
				}
			}
		} else {
			result.add(data);
		}
		return result;
	}
	
}
