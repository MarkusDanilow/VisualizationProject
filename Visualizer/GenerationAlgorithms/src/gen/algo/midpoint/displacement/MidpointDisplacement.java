package gen.algo.midpoint.displacement;

import com.base.common.resources.MathUtil;

import gen.algo.common.GenAlgoBase;
import gen.algo.common.TerrainUtil;

/** 
 * Class for generating data using Midpoint Displacement.
 * 
 * @author Markus
 *
 */
public class MidpointDisplacement extends GenAlgoBase{

	/** 
	 * Constructor setting the expected bounds.
	 */
	public MidpointDisplacement() {
		super(-128, 127);
	}
	
	/** 
	 * Starts the generation of a 2D array of float values with a quadratic size w.
	 * @param w
	 * @return
	 */
	public float[][] generate(int w) {
		if (w > 0) {
			int s = w ; 
			while(!MathUtil.isPowerOfTwo(s-1))
				s++ ;
			this.initializeData2D(s, s);
			this.displace( 0, 0, s-1 ,s-1, 0);
			this.setData2D(MathUtil.map(TerrainUtil.extract(w, w, this.getData2D()), -1.0f, 1.0f, -1));
			return this.getData2D();
		}
		return null;
	}

	/** 
	 * Overridden method from {@link GenAlgoBase}. 
	 * Initializes the 2D array of float values.
	 */
	@Override
	protected void initializeData2D(int w, int h) {
		this.setData2D(new float[w][w]);
		for(int x = 0; x < w; x++){
			for(int y = 0; y < w; y++){
				if((x % (w-1)) == 0 && (y % (w-1)) == 0){
//					this.setData2D(x, y, MathUtil.generateRandomInt((int) (2 * max)) - max) ; 
					this.setData2D(x, y, this.max);
				}else{
					this.setData2D(x, y, 0.0f);
				}
			}
		}			
	}
	
	/**
	 * Recursive function subdividing a square into smaller squares.
	 * @param x0
	 * @param y0
	 * @param x1
	 * @param y1
	 * @param depth
	 */
	protected void displace(int x0, int y0, int x1, int y1, int depth){

		int dx = x1 - x0;
		int dy = y1 - y0; 
		
		if (dx > 1 && dy > 1) {
			
			int d = dx + dy;
			float hd = d / 2.0f ;
			
			int x2 = (x1 + x0) / 2;
			int y2 = (y1 + y0) / 2;

			float w00 = this.getData2D(x0, y0);
			float w10 = this.getData2D(x1, y0);
			float w01 = this.getData2D(x0, y1);
			float w11 = this.getData2D(x1, y1);
			
			if (this.getData2D(x2,  y0) == 0) this.setData2D(x2, y0, (w00 + w10 + MathUtil.generateWeightedFloat(d, hd)) / 2.0f);
			if (this.getData2D(x0, y2) == 0) this.setData2D(x0, y2, (w00 + w01 + MathUtil.generateWeightedFloat(d, hd)) / 2.0f);
			if (this.getData2D(x1, y2) == 0) this.setData2D(x1, y2, (w10 + w11 + MathUtil.generateWeightedFloat(d, hd)) / 2.0f);
			if (this.getData2D(x2, y1) == 0) this.setData2D(x2, y1, (w01 + w11 + MathUtil.generateWeightedFloat(d, hd)) / 2.0f);
			this.setData2D(x2, y2, (w00 + w10 + w01 + w11 + MathUtil.generateWeightedFloat(d, hd)) / 4.0f);

			postProcessing(x0, x1, x2, y0, y1, y2);
			
			displace(x0, y0, x2, y2, depth+1);
			displace(x2, y0, x1, y2, depth+1);
			displace(x0, y2, x2, y1, depth+1);
			displace(x2, y2, x1, y1, depth+1);
			
		}
	}

	/** 
	 * Custom method for post-processing.
	 * @param x0
	 * @param x1
	 * @param x2
	 * @param y0
	 * @param y1
	 * @param y2
	 */
	protected void postProcessing(int x0, int x1, int x2, int y0, int y1, int y2){
		postProcessing(x2, y0);
		postProcessing(x2, y1);
		postProcessing(x0, y2);
		postProcessing(x1, y2);
		postProcessing(x2, y2);
	}
	

}
