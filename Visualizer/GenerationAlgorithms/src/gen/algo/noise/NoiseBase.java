package gen.algo.noise;

import com.base.common.resources.MathUtil;

import gen.algo.common.GenAlgoBase;

/** 
 * Base class for noise generation.
 * @author Markus
 *
 */
public abstract class NoiseBase extends GenAlgoBase implements INoise {
	
	/* -----------------------------------------------
	 * 		STATIC STUFF
	   ----------------------------------------------- */

	/** 
	 * Seed
	 */
	protected static int seed = 3;
	
	/**
	 * Specifies whether a seed shall be used.
	 */
	private static boolean seedGenerationEnabled = true ; 
	
	/** 
	 * Returns the current seed.
	 * @return {@link Integer}
	 */
	public static int getSeed() {
		return seed;
	}

	/** 
	 * Disables the use of a seed.
	 */
	public static void disableSeedGeneration() {
		setSeedGenerationEnabled(false) ; 
	}
	
	/** 
	 * Enables the use of a seed.
	 */
	public static void enableSeedGeneration() {
		setSeedGenerationEnabled(true) ; 
	}

	/** 
	 * Checks whether a seed shall be used.
	 * @return
	 */
	public static boolean isSeedGenerationEnabled() {
		return seedGenerationEnabled;
	}

	/**
	 * Sets the attribute that determines whether a seed shall be used.
	 * @param seedGenerationEnabled
	 */
	public static void setSeedGenerationEnabled(boolean seedGenerationEnabled) {
		NoiseBase.seedGenerationEnabled = seedGenerationEnabled;
	}

	/** 
	 * Creates a new seed if the use of a seed is enabled. 
	 * In case the use of a seed is disabled, it is being enabled again. 
	 * @return
	 */
	public static int handleSeed(){
		if(isSeedGenerationEnabled()){
			seed = MathUtil.getRandomPrime();
		}else{
			enableSeedGeneration();
		}
		return seed ; 
	}

	/* ------------------------------------------------
	 * 		Member Variables & Methods 
	   ------------------------------------------------ */
	
	/** 
	 * Number of octaves.
	 */
	protected int octaves = 10;
	
	/** 
	 * The minimum frequency.
	 */
	protected float f_min = 0.01f;
	
	/** 
	 * The maximum frequency.
	 */
	protected float f_max;	
	
	/** 
	 * The persistence.
	 */
	protected float persistence = 0.5f;
	
	/** 
	 * Determines the number of octaves and the frequencies.
	 * @param o
	 * @param f
	 */
	protected void handleOctavesAndFrequencies(int o, float f){
		octaves = o < 0 ? octaves : o ;
		f_min = f < 0 ? f_min : f ; 
		f_max = (float) (f_min * Math.pow(2, octaves - 1));
	}

	/** 
	 * Generates a 1D array of float values.
	 * @param w
	 * @param o
	 * @return
	 */
	public float[] generate(int w, int o){
		if(w > 0){
			handleSeed();
			initializeData1D(w);
			handleOctavesAndFrequencies(o, 0.01f);
			for(int x=0; x < w; x++){
				localNoiseModulation(x, 1);
			}
		}
		return this.getData1D();
	}
	
	/**
	 * Generates a 2D array of float values.
	 * @param w
	 * @param h
	 * @param o
	 * @return
	 */
	public float[][] generate(int w, int h, int o){
		return generate(w, h, o, 0.01f, 1);
	}
		
	/**
	 * Generates a 2D array of float values.
	 * The amplitude and frequency have to be specified as well.
	 * @param w
	 * @param h
	 * @param o
	 * @param f
	 * @param amplitude
	 * @return
	 */
	public float[][] generate(int w, int h, int o, float f, float amplitude) {
		if (w > 0 && h > 0) {
			handleSeed();
			initializeData2D(w, h);
			handleOctavesAndFrequencies(o, f);
			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					localNoiseModulation(x, y, amplitude);
				}
			}
		}
		return this.getData2D();
	}
	
	/** 
	 * Generates a 3D array of float values.
	 * The amplitude and frequency have to be specified as well.
	 * @param sx
	 * @param sy
	 * @param sz
	 * @param o
	 * @param f
	 * @param amplitude
	 * @return
	 */
	public float[][][] generate(int sx, int sy, int sz, int o, float f, float amplitude){
		if(sx > 0 && sy > 0 && sz > 0){
			handleSeed();
			initializeData3D(sx, sy, sz);
			handleOctavesAndFrequencies(o, f);
			for(int x = 0; x < sx; x++){
				for(int y = 0; y < sy; y++){
					for(int z = 0; z < sz; z++){
						localNoiseModulation(x, y, z, amplitude);
					}
				}
			}
		}
		return this.getData3D();
	}

	/** 
	 * Modulates the noise value at position (x) for 1D array of float values.
	 * @param x
	 * @param amplitude
	 */
	protected void localNoiseModulation(int x, float amplitude){
		float sum = 0.0f ;
		int n = 0;
		float freq = f_min ;
		float a = amplitude ;
		while(freq <= f_max && n < octaves){
			sum += a * noise1D(freq * x) ;
			n++ ;
			freq *= 2.0f ; 
			a *= persistence ;
		}
		this.setData1D(x, sum);
	}
	
	/**
	 * Modulates the noise value at position (x,y) for a 2D array of float values.
	 * @param x
	 * @param y
	 * @param amplitude
	 */
	protected void localNoiseModulation(int x, int y, float amplitude){
		float sum = 0.0f ;
		int n = 0;
		float freq = f_min ;
		float a = amplitude ;
		while(freq <= f_max && n < octaves){
			sum += a * noise2D(freq * x , freq * y) ;
			n++ ;
			freq *= 2.0f ; 
			a *= persistence ;
		}
		this.setData2D(x, y, sum);
		postProcessing(x, y);
	}

	/** 
	 * Modulates the noise value at position (x,y,z) for a 3D array of float values.
	 * @param x
	 * @param y
	 * @param z
	 * @param amplitude
	 */
	protected void localNoiseModulation(int x, int y, int z, float amplitude){
		float sum = 0.0f ;
		int n = 0;
		float freq = f_min ;
		float a = amplitude ;
		while(freq <= f_max && n < octaves){
			sum += a * noise3D(freq * x, freq * y, freq * z) ;
			n++ ;
			freq *= 2.0f ; 
			a *= persistence ;
		}
		this.setData3D(x, y, z, sum);
	}
	
}
