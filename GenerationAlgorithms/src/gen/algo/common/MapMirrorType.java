package gen.algo.common;

import com.base.common.resources.DataInspector;

/** 
 * Enum that specifies the mirroring of a data map.
 * @author Markus
 *
 */
public enum MapMirrorType {

	NO_MIRROR,
	MIRROR_X,
	MIRROR_Y,
	MIRROR_BOTH; 
	
	/** 
	 * Adjusts the width according the {@link MapMirrorType}
	 * @param type
	 * @param w
	 * @return
	 */
	public static int adjustWidth(MapMirrorType type, int w){
		if(!DataInspector.notNull(type)) return w ; 
		return type == MIRROR_Y || type == MapMirrorType.MIRROR_BOTH ? w / 2 : w ; 
	}
	
	/** 
	 * Adjusts the height according to the {@link MapMirrorType}.
	 * @param type
	 * @param h
	 * @return
	 */
	public static int adjustHeight(MapMirrorType type, int h){
		if(!DataInspector.notNull(type)) return h ; 
		return type == MapMirrorType.MIRROR_X || type == MapMirrorType.MIRROR_BOTH ? h / 2 : h ; 
	}
	
}
