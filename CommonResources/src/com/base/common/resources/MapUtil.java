package com.base.common.resources;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*
 * FROM: 
 * http://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values-java
 */

/**
 * Utility functions for handling {@link Map}s.
 * 
 * @author Markus
 *
 */
public class MapUtil {

	/**
	 * Sorts a {@link Map} by its values.
	 * 
	 * @param map
	 * @return {@link Map}
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
}