package gen.algo.noise.perlin;

import com.base.common.resources.MathUtil;
import com.base.common.resources.Vector2f;
import com.base.common.resources.Vector3f;

import gen.algo.noise.NoiseBase;

/** 
 * The Perlin Noise implementation.
 * @author Markus
 *
 */
public class PerlinNoise extends NoiseBase {

	/** 
	 * Generates the noise value at position (x) for a 1D array of float values.
	 */
	public float noise1D(float x) {
		int px = (int) Math.floor(x);
		float g0 = MathUtil.grad(px, getSeed());
		float g1 = MathUtil.grad(px + 1, getSeed());
		float x0 = x - px ; 
		float w0 = g0 * x0 ; 
		float w1 = g1 * (x0 - 1);
		return MathUtil.interpolate(x0, w0, w1);
	}

	/**
	 * Generates the noise value at position (x,y) for a 2D array of float values.
	 */
	public float noise2D(float x, float y) {
		int px = (int) x;
		int py = (int) y;
		float x0 = x - px;
		float y0 = y - py;
		Vector2f g00 = MathUtil.grad(px, py, getSeed());
		Vector2f g10 = MathUtil.grad(px + 1, py, getSeed());
		Vector2f g01 = MathUtil.grad(px, py + 1, getSeed());
		Vector2f g11 = MathUtil.grad(px + 1, py + 1, getSeed());
		float w00 = Vector2f.dot(g00, new Vector2f(x0, y0));
		float w10 = Vector2f.dot(g10, new Vector2f(x0 - 1, y0));
		float w01 = Vector2f.dot(g01, new Vector2f(x0, y0 - 1));
		float w11 = Vector2f.dot(g11, new Vector2f(x0 - 1, y0 - 1));
		return MathUtil.interpolate(x0, y0, w00, w10, w01, w11);
	}

	/**
	 * generates the noise value at position (x,y,z) for a 3D array of float values.
	 */
	public float noise3D(float x, float y, float z) {
		int px = (int) Math.floor(x);
		int py = (int) Math.floor(y);
		int pz = (int) Math.floor(z);
		Vector3f g000 = MathUtil.grad(px, py, pz, getSeed());
		Vector3f g100 = MathUtil.grad(px+1, py, pz, getSeed());
		Vector3f g001 = MathUtil.grad(px, py, pz+1, getSeed());
		Vector3f g101 = MathUtil.grad(px+1, py, pz+1, getSeed());
		Vector3f g010 = MathUtil.grad(px, py+1, pz, getSeed());
		Vector3f g110 = MathUtil.grad(px+1, py+1, pz, getSeed());
		Vector3f g011 = MathUtil.grad(px, py+1, pz+1, getSeed());
		Vector3f g111 = MathUtil.grad(px+1, py+1, pz+1, getSeed());
		float x0 = x - px ; 
		float y0 = y - py ; 
		float z0 = z - pz ;
		float w000 = Vector3f.dot(g000, new Vector3f(x0, y0, z0));
		float w100 = Vector3f.dot(g100, new Vector3f(x0-1, y0, z0));
		float w001 = Vector3f.dot(g001, new Vector3f(x0, y0, z0-1));
		float w101 = Vector3f.dot(g101, new Vector3f(x0-1, y0, z0-1));
		float w010 = Vector3f.dot(g010, new Vector3f(x0, y0-1, z0));
		float w110 = Vector3f.dot(g110, new Vector3f(x0-1, y0-1, z0));
		float w011 = Vector3f.dot(g011, new Vector3f(x0, y0-1, z0-1));
		float w111 = Vector3f.dot(g111, new Vector3f(x0, y0-1, z0-1));
		return MathUtil.interpolate(x0, y0, z0, w000, w100, w001, w101, w010, w110, w011, w111);
	}

}
