package gen.algo.glk;

import com.base.common.resources.Callback;
import com.base.common.resources.DataInspector;

import gen.algo.GenAlgoUtil;
import gen.algo.common.GenAlgoBase;

/**
 * Wrapper class for all the stuff that has to do with machine learning using
 * the so-called GLKs. The functionality still needs to be implemented.
 * 
 * @author Markus
 *
 */
public class GLKUtil {

	/**
	 * The base class for a single GLK. This class implements the
	 * {@link Callback}-Interface in order to be executed from inside
	 * {@link GenAlgoUtil} after a single generation-step.
	 * 
	 * @author Markus
	 *
	 */
	public static abstract class GLKBase implements Callback {

		/**
		 * The generation module that is being used for data generation
		 */
		protected final GenAlgoBase base;

		/**
		 * Constructor.
		 * 
		 * @param base
		 */
		public GLKBase(final GenAlgoBase base) {
			this.base = base;
		}

		/**
		 * Overridden method from {@link Callback}
		 */
		@SuppressWarnings("unused")
		@Override
		public Object execute(Object... data) {
			if (DataInspector.notNull(data) && data.length > 2) {
				int x = Integer.parseInt(String.valueOf(data[0]));
				int y = Integer.parseInt(String.valueOf(data[1]));
				float val = Float.parseFloat(String.valueOf(data[2]));
				/* 
				 * TODO: 
				 * Implement the GLK-functionality generically according to the GLK-class.
				 * The data must be stored in a separate JSON-file. 
				 */
				
			}
			return null;
		}

	}

	/**
	 * GLK for Perlin Noise
	 * 
	 * @author Markus
	 *
	 */
	public static class GLKPerlin extends GLKBase {

		public GLKPerlin(GenAlgoBase base) {
			super(base);
		}

	}

	/**
	 * GLK for Simplex Noise.
	 * 
	 * @author Markus
	 *
	 */
	public static class GLKSimplex extends GLKBase {

		public GLKSimplex(GenAlgoBase base) {
			super(base);
		}
	}

	/** 
	 * GLK for Midpoint Displacement.
	 * @author Markus
	 *
	 */
	public static class GLKMidpointDisplacement extends GLKBase {

		public GLKMidpointDisplacement(GenAlgoBase base) {
			super(base);
		}
	}

	/** 
	 * GLK for Diamond Square.
	 * @author Markus
	 *
	 */
	public static class GLKDiamondSquare extends GLKBase {

		public GLKDiamondSquare(GenAlgoBase base) {
			super(base);
		}
	}

}
