package com.base.common.resources;

import java.util.ArrayList;
import java.util.Random;

public class MathUtil {

	public static final int MAX_INT = 0x7fffffff;

	public static final float F2 = (float) (0.5 * (Math.sqrt(3.0) - 1.0));
	public static final float G2 = (float) ((3.0 - Math.sqrt(3.0)) / 6.0);

	public static final int MAX_PRIMES = 10000;

	protected static Integer[] smallPrimes;

	static {
		ArrayList<Integer> primes = new ArrayList<Integer>();
		for (int i = 1; i < MAX_PRIMES; i++) {
			int counter = 0;
			for (int num = i; num >= 1; num--) {
				if (i % num == 0)
					counter++;
			}
			if (counter == 2)
				primes.add(i);
		}
		smallPrimes = new Integer[primes.size()];
		smallPrimes = primes.toArray(smallPrimes);
	}

	public static Random random = new Random();

	public static float[] getMinAndMax(float[] data) {
		float max = data[0], min = data[0];
		for (int i = 0; i < data.length; i++) {
			float r = data[i];
			if (r < min)
				min = r;
			if (r > max)
				max = r;
		}
		return new float[] { min, max };
	}

	public static float[] getMinAndMax(float[][] data) {
		float max = data[0][0], min = data[0][0];
		for (int x = 0; x < data.length; x++) {
			for (int y = 0; y < data[x].length; y++) {
				float r = data[x][y];
				if (r < min)
					min = r;
				if (r > max)
					max = r;
			}
		}
		return new float[] { min, max };
	}

	public static float[] getMinAndMax(float[][][] data) {
		float max = data[0][0][0], min = data[0][0][0];
		for (int x = 0; x < data.length; x++) {
			for (int y = 0; y < data[x].length; y++) {
				for (int z = 0; z < data[x][y].length; z++) {
					float r = data[x][y][z];
					if (r < min)
						min = r;
					if (r > max)
						max = r;
				}
			}
		}
		return new float[] { min, max };
	}

	public static float[][] map(float[][] data, float min, float max, float precision) {
		float[] m = getMinAndMax(data);
		for (int x = 0; x < data.length; x++) {
			for (int y = 0; y < data[x].length; y++) {
				data[x][y] = map(data[x][y], m[0], m[1], min, max, precision);
			}
		}
		return data;
	}

	public static float[] map(float[] data, float min, float max, float precision) {
		float[] m = getMinAndMax(data);
		for (int i = 0; i < data.length; i++) {
			data[i] = map(data[i], m[0], m[1], min, max, precision);
		}
		return data;
	}

	public static float map(float value, float min1, float max1, float min2, float max2, float precision) {
		if (precision < 0)
			precision = 0.05f;
		float r = ((value - min1) * (max2 - min2)) / (max1 - min1) + min2;
		while (r < min2 || r > max2) {
			if (r < min2)
				r += precision;
			if (r > max2)
				r -= precision;
		}
		return r;
	}


	public static boolean isPowerOfTwo(int n) {
		return (n > 0) && (n & (n - 1)) == 0;
	}

	public static float generateRandomFloat(int min, int max) {
		return (random.nextInt(max) * 2.0f) - (min * (min < 0 ? -1.0f : 1.0f));
	}

	public static float generateWeightedFloat(int w0, float w1) {
		return random.nextInt(w0) - w1;
	}

	public static float blend(float x) {
		return x * x * x * (x * (x * 6 - 15) + 10);
	}

	public static float interpolate(float x, float w0, float w1) {
		float sx = blend(x);
		return (1 - sx) * w0 + sx * w1;
	}

	public static float interpolate(float x, float y, float w00, float w10, float w01, float w11) {
		float sx = blend(x);
		float sy = blend(y);
		float w0 = (1 - sx) * w00 + sx * w10;
		float w1 = (1 - sx) * w01 + sx * w11;
		return (1 - sy) * w0 + sy * w1;
	}

	public static float interpolate(float x, float y, float z, float w000, float w100, float w001, float w101,
			float w010, float w110, float w011, float w111) {
		float sx = blend(x);
		float sy = blend(y);
		float sz = blend(z);
		float w00 = (1 - sx) * w000 + sx * w100;
		float w10 = (1 - sx) * w010 + sx * w110;
		float w01 = (1 - sx) * w001 + sx * w101;
		float w11 = (1 - sx) * w011 + sx * w111;
		float w0 = (1 - sy) * w00 + sy * w10;
		float w1 = (1 - sy) * w01 + sy * w11;
		return (1 - sz) * w0 + sz * w1;
	}

	public static float grad(int x, int seed) {
		Vector2f hash = hash2D(getHashableInt(x, seed));
		return hash.length();
	}

	public static Vector2f grad(int x, int y, int seed) {
		Vector2f hash = hash2D(getHashableInt(x, y, seed));
		Vector2f grad = new Vector2f(2.0f * hash.getX() - 1, 2.0f * hash.getY() - 1);
		return grad;
	}

	public static Vector3f grad(int x, int y, int z, int seed) {
		Vector3f hash = hash3D(getHashableInt(x, y, z, seed));
		Vector3f grad = new Vector3f(2.0f * hash.getX() - 1, 2.0f * hash.getY() - 1, 2.0f * hash.getZ() - 1);
		return grad;
	}

	public static Vector2f hash2D(int hashInt) {
		final int M = 0x00000FFF;
		int h = hashInt2(hashInt);
		float hx = h & M;
		float hy = (h >> 12) & M;
		return new Vector2f(hx / M, hy / M);
	}

	public static Vector3f hash3D(int hashInt) {
		final int M = 0x00000FFF;
		int h = hashInt2(hashInt);
		float hx = h & M;
		float hy = (h >> 12) & M;
		float hz = (h >> 16) & M;
		return new Vector3f(hx / M, hy / M, hz / M);
	}

	public static int getHashableInt(int u, int seed) {
		return 59 * u + seed;
	}

	public static int getHashableInt(int u, int v, int seed) {
		return 59 * u + 67 * v + seed;
	}

	public static int getHashableInt(int u, int v, int w, int seed) {
		return 59 * u + 67 * v + 71 * w + seed;
	}

	public static int hashInt1(int key) {
		key = (key << 13) ^ key;
		return (key * (key * key * 15731 + 789221) + 1376312589) & MAX_INT;
	}

	public static int hashInt2(int key) {
		key = ~key + (key << 15);
		key = key ^ (key >> 12);
		key = key + (key << 2);
		key = key ^ (key >> 4);
		key = key * 2057;
		key = key ^ (key >> 16);
		return key & MAX_INT;
	}

	public static int getRandomPrime() {
		int index = random.nextInt(smallPrimes.length);
		return smallPrimes[index];
	}

	public static int generateRandomInt(int max) {
		return random.nextInt(max);
	}

	public static int generateRandomInt() {
		return random.nextInt();
	}

	public static float generateRandomFloat() {
		return random.nextFloat();
	}

	public static boolean generateRandomBoolean() {
		return random.nextBoolean();
	}

	public static double round(double value, int precision) {
		if (precision < 0)
			throw new IllegalArgumentException();
		long factor = (long) Math.pow(10, precision);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

}
