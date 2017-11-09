package gen.algo;

import java.util.ArrayList;
import java.util.List;

import com.base.common.resources.Callback;
import com.base.common.resources.DataInspector;

import gen.algo.common.GenAlgoBase;
import gen.algo.common.MapMirrorType;
import gen.algo.common.TerrainUtil;
import gen.algo.diamond.square.DiamondSquare;
import gen.algo.glk.GLKUtil.GLKBase;
import gen.algo.glk.GLKUtil.GLKDiamondSquare;
import gen.algo.glk.GLKUtil.GLKMidpointDisplacement;
import gen.algo.glk.GLKUtil.GLKPerlin;
import gen.algo.glk.GLKUtil.GLKSimplex;
import gen.algo.midpoint.displacement.MidpointDisplacement;
import gen.algo.noise.perlin.PerlinNoise;
import gen.algo.noise.random.RandomNoise;
import gen.algo.noise.simplex.SimplexNoise;

/** 
 * Interface that provides all the important functions for generating stuff.
 * @author Markus
 *
 */
public class GenAlgoUtil {

	/** 
	 * {@link List} of {@link Callback}s that are being executed after a single generation-step.
	 */
	private static List<Callback> callbacks;
	
	/** 
	 * Generates a random 2D array of float values.
	 * @param w
	 * @param h
	 * @param type
	 * @param callbackFns
	 * @return
	 */
	public static float[][] generateRandom(int w, int h, MapMirrorType type, Callback... callbackFns) {
		registerCallbacks(callbackFns);
		w = MapMirrorType.adjustWidth(type, w);
		h = MapMirrorType.adjustHeight(type, h);
		float[][] data = new RandomNoise().generate(w, h);
		return processMapType(data, type);
	}

	/** 
	 * Generates a 1D array of float values using Perlin Noise.
	 * @param w
	 * @param o
	 * @return
	 */
	public static float[] generatePerlin(int w, int o){
		return new PerlinNoise().generate(w, o);
	}
	
	/**
	 * Generates a 2D array of float values using Perlin Noise.
	 * @param w
	 * @param h
	 * @param o
	 * @param type
	 * @param callbackFns
	 * @return
	 */
	public static float[][] generatePerlin(int w, int h, int o, MapMirrorType type, Callback... callbackFns) {
		PerlinNoise base = new PerlinNoise();
		registerCallbacks(base, callbackFns);
		w = MapMirrorType.adjustWidth(type, w);
		h = MapMirrorType.adjustHeight(type, h);
		float[][] data = base.generate(w, h, o);
		return processMapType(data, type);
	}

	/** 
	 * Generates a 2D array of float values using Perlin Noise. 
	 * The frequency and amplitude have to be specified as well.
	 * @param w
	 * @param h
	 * @param o
	 * @param f
	 * @param a
	 * @param type
	 * @param callbackFns
	 * @return
	 */
	public static float[][] generatePerlin(int w, int h, int o, float f, float a, MapMirrorType type,
			Callback... callbackFns) {
		PerlinNoise base = new PerlinNoise();
		registerCallbacks(base, callbackFns);
		w = MapMirrorType.adjustWidth(type, w);
		h = MapMirrorType.adjustHeight(type, h);
		float[][] data = base.generate(w, h, o, f, a);
		return processMapType(data, type);
	}

	/** 
	 * Generates a 3D array of float values using Perlin Noise.
	 * The frequency and amplitude have to be specified as well. 
	 * @param sx
	 * @param sy
	 * @param sz
	 * @param o
	 * @param f
	 * @param a
	 * @param type
	 * @param callbackFns
	 * @return
	 */
	public static float[][][] generatePerlin(int sx, int sy, int sz, int o, float f, float a, MapMirrorType type, Callback...callbackFns){
		PerlinNoise base = new PerlinNoise();
		registerCallbacks(base, callbackFns);
		sx = MapMirrorType.adjustWidth(type, sx);
		sy = MapMirrorType.adjustHeight(type, sy);
		float[][][] data = base.generate(sx, sy, sz, o, f, a);
		return data ;
	}
	
	/**
	 * Generates a 2D array of float values using Simplex Noise. 
	 * @param w
	 * @param h
	 * @param o
	 * @param type
	 * @param callbackFns
	 * @return
	 */
	public static float[][] generateSimplex(int w, int h, int o, MapMirrorType type, Callback... callbackFns) {
		SimplexNoise base = new SimplexNoise();
		registerCallbacks(base, callbackFns);
		w = MapMirrorType.adjustWidth(type, w);
		h = MapMirrorType.adjustHeight(type, h);
		float[][] data = base.generate(w, h, o);
		return processMapType(data, type);
	}

	/**
	 * Generates a 2D array of float values using Simplex Noise. 
	 * The frequency and amplitude have to be specified as well. 
	 * @param w
	 * @param h
	 * @param o
	 * @param f
	 * @param a
	 * @param type
	 * @param callbackFns
	 * @return
	 */
	public static float[][] generateSimplex(int w, int h, int o, float f, float a, MapMirrorType type,
			Callback... callbackFns) {
		SimplexNoise base = new SimplexNoise();
		registerCallbacks(base, callbackFns);
		w = MapMirrorType.adjustWidth(type, w);
		h = MapMirrorType.adjustHeight(type, h);
		float[][] data = base.generate(w, h, o, f, a);
		return processMapType(data, type);
	}

	/** 
	 * Generates a 2D array of float values using Midpoint Displacement.
	 * @param w
	 * @param h
	 * @param type
	 * @param callbackFns
	 * @return
	 */
	public static float[][] generateMidpointDisplacement(int w, int h, MapMirrorType type, Callback... callbackFns) {
		MidpointDisplacement base = new MidpointDisplacement();
		registerCallbacks(base, callbackFns);
		float[][] data = base.generate(w > h ? w : h);
		data = TerrainUtil.extract(MapMirrorType.adjustWidth(type, w), MapMirrorType.adjustHeight(type, h), data);
		return processMapType(data, type);
	}

	/** 
	 * Generates a 2D array of float values using Diamond Square.
	 * @param w
	 * @param h
	 * @param type
	 * @param callbackFns
	 * @return
	 */
	public static float[][] generateDiamondSquare(int w, int h, MapMirrorType type, Callback... callbackFns) {
		DiamondSquare base = new DiamondSquare();
		registerCallbacks(base, callbackFns);
		float[][] data = base.generate(w > h ? w : h);
		data = TerrainUtil.extract(MapMirrorType.adjustWidth(type, w), MapMirrorType.adjustHeight(type, h), data);
		return processMapType(data, type);
	}

	/**
	 * Post-processes the generated data according to the specified {@link MapMirrorType}.
	 * @param data
	 * @param type
	 * @return
	 */
	private static float[][] processMapType(float[][] data, MapMirrorType type) {
		return TerrainUtil.processMapType(data, type);
	}

	/** 
	 * Registers multiple {@link Callback}s at once.
	 * @param callbackFns
	 */
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
	
	/** 
	 * Registers multiple {@link Callback}s at once for a certain {@link GenAlgoBase}.
	 * This method is used for registering a new {@link GLKBase} according the {@link GenAlgoBase}.
	 * @param base
	 * @param callbackFns
	 */
	private static void registerCallbacks(GenAlgoBase base, Callback... callbackFns) {
		registerCallbacks(callbackFns);
		Callback glkCallback = null ; 
		if(base.getClass().isAssignableFrom(PerlinNoise.class)){
			glkCallback = new GLKPerlin(base);
		}else if(base.getClass().isAssignableFrom(SimplexNoise.class)){
			glkCallback = new GLKSimplex(base);
		}else if(base.getClass().isAssignableFrom(MidpointDisplacement.class)){
			glkCallback = new GLKMidpointDisplacement(base);
		}else if(base.getClass().isAssignableFrom(DiamondSquare.class)){
			glkCallback = new GLKDiamondSquare(base);
		}
		callbacks.add(0, glkCallback);
	}

	/** 
	 * Executes all the {@link Callback}s that have been registered so far.
	 * @param data
	 */
	public static void handleGenerationCallback(Object... data) {
		if (DataInspector.notNull(data) && data.length > 2) {			
			if(callbacks != null){
				for (Callback callback : callbacks) {
					if (DataInspector.notNull(callback)) {
						callback.execute(data);
					}
				}
			}
		}
	}

}
