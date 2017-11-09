package gen.algo.diamond.square;

import com.base.common.resources.MathUtil;

import gen.algo.midpoint.displacement.MidpointDisplacement;

/** 
 * Class for generating data using Diamond Sqaure.
 * This class extends {@link MidpointDisplacement}. 
 * @author Markus
 *
 */
public class DiamondSquare extends MidpointDisplacement {
	
	/** 
	 * 
	 * Recursive function subdividing a square into smaller squares.
	 */
	protected void displace(int x0, int y0, int x1, int y1, int depth){

		int dx = x1 - x0 ; 
		int dy = y1 - y0; 
		
		if(dx > 1 && dy > 1){
			
			int d = dx + dy ;
			float hd = d / 2.0f ;
			 
			int x2 = (x0 + x1) / 2 ;
			int y2 = (y0 + y1) / 2 ;
			
			float w00 = this.getData2D(x0, y0);
			float w10 = this.getData2D(x1, y0);
			float w01 = this.getData2D(x0, y1);
			float w11 = this.getData2D(x1, y1);			
			float w = (w00 + w10 + w01 + w11 + MathUtil.generateWeightedFloat(d, hd)) / 4.0f ;
			
			this.setData2D(x2, y2, w); 			
			if(this.getData2D(x2, y0) == 0) this.setData2D(x2, y0, (w00 + w10 + w + MathUtil.generateWeightedFloat(d, hd)) / 3.0f) ; 
			if(this.getData2D(x2, y1) == 0) this.setData2D(x2, y1, (w01 + w11 + w + MathUtil.generateWeightedFloat(d, hd)) / 3.0f) ; 
			if(this.getData2D(x0, y2) == 0) this.setData2D(x0, y2, (w00 + w01 + w + MathUtil.generateWeightedFloat(d, hd)) / 3.0f) ; 
			if(this.getData2D(x1, y2) == 0) this.setData2D(x1, y2, (w10 + w11 + w + MathUtil.generateWeightedFloat(d, hd)) / 3.0f) ;

			postProcessing(x0, x1, x2, y0, y1, y2);
			
			displace(x0, y0, x2, y2, depth+1);
			displace(x2, y0, x1, y2, depth+1);
			displace(x0, y2, x2, y1, depth+1);
			displace(x2, y2, x1, y1, depth+1);
			
		}
	}
	
}
