package gen.algo.common;

import com.base.common.resources.Callback;
import com.base.common.resources.DataMap1D;
import com.base.common.resources.DataMap2D;
import com.base.common.resources.DataMap3D;

import gen.algo.GenAlgoUtil;

/** 
 * Base class for generation, providing methods for initializing and modifying the generated data.
 * All other generation-modules are derived from this class.
 * @author Markus
 *
 */
public abstract class GenAlgoBase {

	/** 
	 * The expected lower and upper bounds.
	 */
	protected float min, max;
	
	/**
	 * 1D array of float values.
	 */
	protected DataMap1D data1D;
	
	/** 
	 * 2D array of float values.
	 */
	protected DataMap2D data2D ;
	
	/**
	 * 3D array of float values.
	 */
	protected DataMap3D data3D;
	
	/** 
	 * Constructor.
	 */
	public GenAlgoBase() {
		this(-1, 1);
	}
	
	/** 
	 * Constructor taking in the expected lower and upper bounds.
	 * @param min
	 * @param max
	 */
	protected GenAlgoBase(float min, float max){
		this.data1D = new DataMap1D();
		this.data2D = new DataMap2D();
		this.data3D = new DataMap3D();
		setMin(min);
		setMax(max);
	}

	/** 
	 * Returns the lower bound.
	 * @return {@link Float}
	 */
	public float getMin() {
		return min;
	}

	/** 
	 * Sets the lower bound.
	 * @param min
	 */
	public void setMin(float min) {
		this.min = min;
	}

	/** 
	 * Returns the upper bound.
	 * @return {@link Float}
	 */
	public float getMax() {
		return max;
	}

	/** 
	 * Sets the upper bound.
	 * @param max
	 */
	public void setMax(float max) {
		this.max = max;
	}
	
	/** 
	 * Initializes the 1D array of float values.
	 * @param w
	 */
	protected void initializeData1D(int w) {
		this.setData1D(new float[w]) ;
	}
	
	/** 
	 * Initializes the 2D array of float values.
	 * @param w
	 * @param h
	 */
	protected void initializeData2D(int w, int h) {
		this.setData2D(new float[w][h]) ;
	}

	/**
	 * Initializes the 3D array of float values.
	 * @param sx
	 * @param sy
	 * @param sz
	 */
	protected void initializeData3D(int sx, int sy, int sz) {
		this.setData3D(new float[sx][sy][sz]) ;
	}
	
	/** 
	 * Returns the 2D array of float values.
	 * @return
	 */
	public float[][] getData2D() {
		return data2D.getData();
	}
	
	/** 
	 * Returns the float value at position (x,y) in the 2D array.
	 * @param x
	 * @param y
	 * @return
	 */
	public float getData2D(int x, int y){
		return this.data2D.getData()[x][y];
	}

	/**
	 * Sets the entire 1D array of float values.
	 * @param data
	 */
	public void setData1D(float[] data) {
		this.data1D.setData(data);
	}
	
	/**
	 * Sets the value at position (x) in the 1D array.
	 * @param x
	 * @param value
	 */
	public void setData1D(int x, float value){
		this.data1D.getData()[x] = value ; 
	}

	/** 
	 * Returns the 1D array of float values.
	 * @return
	 */
	public float[] getData1D(){
		return this.data1D.getData();
	}
	
	/** 
	 * Returns the float value at position (x) in the 1D array.
	 * @param x
	 * @return
	 */
	public float getData1D(int x){
		return this.getData1D()[x] ;
	}
	
	/**
	 * Sets the value at position (x,y) in the 2D array.
	 * @param x
	 * @param y
	 * @param value
	 */
	public void setData2D(int x, int y, float value){
		this.data2D.getData()[x][y] = value ;
	}

	/** 
	 * Sets the entire 2D array of float values.
	 * @param data
	 */
	public void setData2D(float[][] data) {
		this.data2D.setData(data);
	}

	/** 
	 * Starts the post-processing of a float value at position (x,y) in the 2D array of float values.
	 * This method triggers the {@link Callback}s in {@link GenAlgoUtil}.
	 * @param x
	 * @param y
	 */
	protected void postProcessing(int x, int y) {
		GenAlgoUtil.handleGenerationCallback(x, y, this.getData2D()[x][y]);
	}

	/** 
	 * Sets the entire 3D array of float values.
	 * @param data
	 */
	public void setData3D(float[][][] data) {
		this.data3D.setData(data);
	}

	/**
	 * Sets the value at position (x,y,z) in the 3D array  of float values.
	 * @param x
	 * @param y
	 * @param z
	 * @param value
	 */
	public void setData3D(int x, int y, int z, float value){
		this.data3D.getData()[x][y][z] = value ;
	}

	/**
	 * Returns the 3D array of float values.
	 * @return
	 */
	public float[][][] getData3D() {
		return data3D.getData();
	}	
	
}
