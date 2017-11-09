package gen.algo;

import java.util.ArrayList;
import java.util.List;

import com.base.common.resources.Callback;
import com.base.common.resources.DataInspector;

import gen.algo.common.MapMirrorType;
import gen.algo.common.TerrainUtil;
import gen.algo.diamond.square.DiamondSquare;
import gen.algo.midpoint.displacement.MidpointDisplacement;
import gen.algo.noise.perlin.PerlinNoise;
import gen.algo.noise.random.RandomNoise;
import gen.algo.noise.simplex.SimplexNoise;

public class Algy {

	private static List<Callback> callbacks;
	
	public static float[][] generateRandom(int w, int h, MapMirrorType type, Callback... callbackFns) {
		registerCallbacks(callbackFns);
		w = MapMirrorType.adjustWidth(type, w);
		h = MapMirrorType.adjustHeight(type, h);
		float[][] data = new RandomNoise().generate(w, h);
		return processMapType(data, type);
	}

	public static float[][] generatePerlin(int w, int h, int o, MapMirrorType type, Callback... callbackFns) {
		registerCallbacks(callbackFns);
		w = MapMirrorType.adjustWidth(type, w);
		h = MapMirrorType.adjustHeight(type, h);
		float[][] data = new PerlinNoise().generate(w, h, o);
		return processMapType(data, type);
	}

	public static float[] generatePerlin(int w, int o){
		return new PerlinNoise().generate(w, o);
	}
	
	public static float[][] generatePerlin(int w, int h, int o, float f, float a, MapMirrorType type,
			Callback... callbackFns) {
		registerCallbacks(callbackFns);
		w = MapMirrorType.adjustWidth(type, w);
		h = MapMirrorType.adjustHeight(type, h);
		float[][] data = new PerlinNoise().generate(w, h, o, f, a);
		return processMapType(data, type);
	}

	public static float[][][] generatePerlin(int sx, int sy, int sz, int o, float f, float a, MapMirrorType type, Callback...callbackFns){
		registerCallbacks(callbackFns);
		sx = MapMirrorType.adjustWidth(type, sx);
		sy = MapMirrorType.adjustHeight(type, sy);
		float[][][] data = new PerlinNoise().generate(sx, sy, sz, o, f, a);
		return data ;
	}
	
	public static float[][] generateSimplex(int w, int h, int o, MapMirrorType type, Callback... callbackFns) {
		registerCallbacks(callbackFns);
		w = MapMirrorType.adjustWidth(type, w);
		h = MapMirrorType.adjustHeight(type, h);
		float[][] data = new SimplexNoise().generate(w, h, o);
		return processMapType(data, type);
	}

	public static float[][] generateSimplex(int w, int h, int o, float f, float a, MapMirrorType type,
			Callback... callbackFns) {
		registerCallbacks(callbackFns);
		w = MapMirrorType.adjustWidth(type, w);
		h = MapMirrorType.adjustHeight(type, h);
		float[][] data = new SimplexNoise().generate(w, h, o, f, a);
		return processMapType(data, type);
	}

	public static float[][] generateMidpointDisplacement(int w, MapMirrorType type, Callback... callbackFns) {
		registerCallbacks(callbackFns);
		float[][] data = new MidpointDisplacement().generate(w);
		data = TerrainUtil.extract(MapMirrorType.adjustWidth(type, w), MapMirrorType.adjustHeight(type, w), data);
		return processMapType(data, type);
	}

	public static float[][] generateDiamondSquare(int w, MapMirrorType type, Callback... callbackFns) {
		registerCallbacks(callbackFns);
		float[][] data = new DiamondSquare().generate(w);
		data = TerrainUtil.extract(MapMirrorType.adjustWidth(type, w), MapMirrorType.adjustHeight(type, w), data);
		return processMapType(data, type);
	}

	public static float[][] smooth(float[][] data, int smoothness, Callback... callbackFns) {
		registerCallbacks(callbackFns);
		return TerrainUtil.smooth(data, smoothness);
	}

	private static float[][] processMapType(float[][] data, MapMirrorType type) {
		return TerrainUtil.processMapType(data, type);
	}

	private static void registerCallbacks(Callback... callbackFns) {
		callbacks = new ArrayList<Callback>();
		if (DataInspector.notNull((Object[]) callbackFns)) {
			for (Callback c : callbackFns) {
				if (DataInspector.notNull(c)) {
					callbacks.add(c);
				}
			}
		}
	}

	public static void handleGenerationCallback(Object... data) {
		if (DataInspector.notNull(data) && data.length > 2) {
			
			for (Callback callback : callbacks) {
				if (DataInspector.notNull(callback)) {
					callback.execute(data);
				}
			}
		}
	}

}
