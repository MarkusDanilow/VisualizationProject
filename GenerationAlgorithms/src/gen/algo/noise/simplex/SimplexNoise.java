package gen.algo.noise.simplex;

import com.base.common.resources.MathUtil;
import com.base.common.resources.Vector2f;

import gen.algo.noise.NoiseBase;

/** 
 * The Simplex Noise implementation.
 * @author Markus
 *
 */
public class SimplexNoise extends NoiseBase{
			  
	/** 
	 * Generates the noise value at position (x,y) for a 2D array of float values.
	 */
	public float noise2D(float x, float y) {
		
		float n0 = 0.0f, n1 = 0.0f, n2 = 0.0f;

		float s = (x + y) * MathUtil.F2;
		int i = (int) Math.floor(x + s);
		int j = (int) Math.floor(y + s);

		float t = (i + j) * MathUtil.G2;
		float X = i - t;
		float Y = j - t;
		float x0 = x - X;
		float y0 = y - Y;

		int i1 = 0, j1 = 1;
		if (x0 > y0) {
			i1 = 1;
			j1 = 0;
		}

		float x1 = x0 - i1 + MathUtil.G2;
		float y1 = y0 - j1 + MathUtil.G2;
		float x2 = x0 - 1.0f + 2.0f * MathUtil.G2;
		float y2 = y0 - 1.0f + 2.0f * MathUtil.G2;

		float t0 = 0.6f - (x0 * x0 + y0 * y0);
		if (t0 > 0)
			n0 = 8.0f * (float) (Math.pow(t0, 4) * Vector2f.dot(MathUtil.grad(i, j, getSeed()), new Vector2f(x0, y0)));
			
		float t1 = 0.6f - (x1 * x1 + y1 * y1);
		if (t1 > 0)
			n1 =  8.0f * (float) (Math.pow(t1, 4) * Vector2f.dot(MathUtil.grad(i + i1, j + j1, getSeed()), new Vector2f(x1, y1)));
					
		float t2 = 0.6f - (x2 * x2 + y2 * y2);
		if (t2 > 0)
			n2 =  8.0f * (float) (Math.pow(t2, 4) * Vector2f.dot(MathUtil.grad(i + 1, j + 1, getSeed()), new Vector2f(x2, y2)));
			
		return (n0 + n1 + n2);
	}

	@Override
	public float noise1D(float x) {
		return 0;
	}

	@Override
	public float noise3D(float x, float y, float z) {
		// TODO Auto-generated method stub
		return 0;
	}

}
