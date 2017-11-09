package gen.algo.common;

import com.base.common.resources.DataInspector;
import com.base.common.resources.MathUtil;

import gen.algo.GenAlgoUtil;

/**
 * Utility functions for editing the terrain.
 * 
 * @author Markus
 *
 */
public class TerrainUtil {

	/**
	 * Extracts the values inside a rectangle with width w and height h from a
	 * given 2D array.
	 * 
	 * @param w
	 * @param h
	 * @param data
	 * @return
	 */
	public static float[][] extract(int w, int h, float[][] data) {
		if (DataInspector.notNull((Object[]) data) && DataInspector.notNull(data[0])) {
			float[][] result = new float[w][h];
			int x0 = (data.length - w) / 2;
			int y0 = (data[0].length - h) / 2;
			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					result[x][y] = data[x + x0][y + y0];
				}
			}
			return result;
		}
		return data;
	}

	/**
	 * Applies the mirroring to a 2D array of flaot values.
	 * 
	 * @param data
	 * @param type
	 * @return
	 */
	public static float[][] processMapType(float[][] data, MapMirrorType type) {
		if (!(type == null || data == null || data.length <= 0 || data[0] == null)) {
			switch (type) {
			case MIRROR_X:
				return mirrorX(data);
			case MIRROR_Y:
				return mirrorY(data);
			case MIRROR_BOTH:
				return mirrorBoth(data);
			default:
				break;
			}
		}
		return data;
	}

	/**
	 * Mirroring a 2D array of float values along the x-axis.
	 * 
	 * @param data
	 * @return
	 */
	private static float[][] mirrorX(float[][] data) {
		float[][] pData = new float[data.length][data.length];
		int u = pData.length - 1;
		int v = pData[0].length - 1;
		for (int x = 0; x < u; x++) {
			for (int y = 0; y < v / 2; y++) {
				if (x < u && y < v) {
					pData[x][y] = data[x][y];
					pData[x][v - y] = data[x][y];
				}
			}
		}
		return pData;
	}

	/**
	 * Mirroring a 2D array of float values along the y-axis.
	 * 
	 * @param data
	 * @return
	 */
	private static float[][] mirrorY(float[][] data) {
		float[][] pData = new float[data[0].length][data[0].length];
		int u = pData.length - 1;
		int v = pData[0].length - 1;
		for (int x = 0; x < u / 2; x++) {
			for (int y = 0; y < v; y++) {
				if (x < u && y < v) {
					pData[x][y] = data[x][y];
					pData[u - x][y] = data[x][y];
				}
			}
		}
		return pData;
	}

	/**
	 * Mirroring a 2D array of float values along both axes.
	 * 
	 * @param data
	 * @return
	 */
	private static float[][] mirrorBoth(float[][] data) {
		float[][] pData = new float[data.length * 2][data[0].length * 2];
		int u = pData.length - 1;
		int v = pData[0].length - 1;
		for (int x = 0; x < u / 2; x++) {
			for (int y = 0; y < v / 2; y++) {
				if (x < u && y < v) {
					pData[x][y] = data[x][y];
					pData[u - x][y] = data[x][y];
					pData[x][v - y] = data[x][y];
					pData[u - x][v - y] = data[x][y];
				}
			}
		}
		return pData;
	}

	public static float[][] smooth(float[][] data, int smoothness) {
		float[] m = MathUtil.getMinAndMax(data);
		if (smoothness > 0 && DataInspector.notNull(data, data[0])) {
			for (int x = 0; x < data.length; x++) {
				for (int y = 0; y < data[x].length; y++) {
					int count = 0;
					float total = 0;
					for (int i = x - smoothness; i <= x + smoothness; i++) {
						if (i >= x && i <= data.length - 1){
							for (int j = y - smoothness; j <= y + smoothness; j++) {
								if (j >= 0 && j <= data[x].length - 1){
									total += data[i][j];
									count++;
								}
							}
						}
					}
					if (count != 0 && total != 0) {
						data[x][y] = total / (float) count;
					}
					GenAlgoUtil.handleGenerationCallback(x, y, data[x][y], m[0], m[1]);
				}
			}
		}
		return data;
	}
	
}
