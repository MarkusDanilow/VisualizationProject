package gen.algo.noise;

/** 
 * Interface that specifies the different kinds of noise functions. 
 * @author Markus
 *
 */
public interface INoise {

	float noise1D(float x);
	float noise2D(float x, float y);
	float noise3D(float x, float y, float z);
	
	
}
