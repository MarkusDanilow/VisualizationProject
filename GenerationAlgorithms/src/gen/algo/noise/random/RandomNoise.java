package gen.algo.noise.random;

import java.util.Random;

import gen.algo.common.GenAlgoBase;

/**
 * Random data generation.
 */
public class RandomNoise extends GenAlgoBase {

	/**
	 * Generates a 2D array of random float values. 
	 * @param w
	 * @param h
	 * @return
	 */
	public float[][] generate(int w, int h){
		if(w > 0 && h > 0){
			initializeData2D(w, h);
			Random r = new Random();
			for(int x = 0; x < w; x++){
				for(int y = 0; y < h ; y++){
					this.setData2D(x, y, r.nextFloat());
					this.postProcessing(x, y);
				}
			}
		}
		return this.getData2D() ;
	}
	
}
