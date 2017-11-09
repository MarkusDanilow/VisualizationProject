package com.base.common.resources;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ClazzAnalyzer {

	private static final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();

	public static boolean isMultitude(Class<?> clazz){
		return isArray(clazz) || isCollection(clazz) || isSet(clazz) || isMap(clazz); 
	}
	
	public static boolean isArray(Class<?> clazz){
		return clazz != null && clazz.isArray();
	}
	
	public static boolean isSet(Class<?> clazz){
		return clazz != null && Set.class.isAssignableFrom(clazz);
	}
	
	public static boolean isMap(Class<?> clazz){
		return clazz != null && Map.class.isAssignableFrom(clazz);
	}
	
	public static boolean isCollection(Class<?> clazz){
		return clazz != null && Collection.class.isAssignableFrom(clazz);
	}

	public static boolean isPrimitiveType(Class<?> clazz){
		return clazz != null && clazz.isPrimitive();
	}
	
	public static boolean isString(Class<?> clazz){
		return clazz != null && (String.class.isAssignableFrom(clazz) || clazz.equals(String.class));
	}
	
	public static boolean isProperty(Class<?> clazz){
		return clazz != null && Properties.class.isAssignableFrom(clazz);
	}
	
	public static boolean isObject(Class<?> clazz){
		return clazz != null && Object.class.isAssignableFrom(clazz);
	}
	
	public static boolean isWrapperType(Class<?> clazz)
    {
        return WRAPPER_TYPES.contains(clazz);
    }

    public static Set<Class<?>> getWrapperTypes()
    {
        Set<Class<?>> ret = new HashSet<Class<?>>();
        ret.add(Boolean.class);
        ret.add(Character.class);
        ret.add(Byte.class);
        ret.add(Short.class);
        ret.add(Integer.class);
        ret.add(Long.class);
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(Void.class);
        return ret;
    }
    	
}
