package com.base.common.resources;

/**
 * 
 * @author Markus
 *
 * @param <T>
 */
public class Range<T extends Comparable<T>> {

	private T loVal, hiVal;

	public T getLoVal() {
		return loVal;
	}

	public void setLoVal(T loVal) {
		this.loVal = loVal;
	}

	public T getHiVal() {
		return hiVal;
	}

	public void setHiVal(T hiVal) {
		this.hiVal = hiVal;
	}
	
	public Range(T lo, T hi) {
		if (!(lo.compareTo(hi) < 0))
			throw new IllegalArgumentException();
		this.setLoVal(lo);
		this.setHiVal(hi);
	}

	/**
	 * 
	 * @param value
	 * @return Checks if the argument being passed is in range, including the
	 *         lower and the upper limit themselves.
	 */
	public boolean isInRange(T value) {
		return this.loVal.compareTo(value) <= 0
				&& this.hiVal.compareTo(value) >= 0;
	}

	/**
	 * 
	 * @param value
	 * @return Checks if the argument being passed is between the lower and the
	 *         upper limit, excluding the limits themselves.
	 */
	public boolean isInBetween(T value) {
		return this.loVal.compareTo(value) < 0
				&& this.hiVal.compareTo(value) > 0;
	}

	/**
	 * 
	 * @param value
	 * @return Checks if the argument being passed is either the lower or the
	 *         upper limit.
	 */
	public boolean isLimit(T value) {
		return this.isLowerLimit(value) || this.isUpperLimit(value);
	}

	/**
	 * 
	 * @param value
	 * @return Checks if the argument being passed is the lower limit.
	 */
	public boolean isLowerLimit(T value) {
		return this.loVal.compareTo(value) == 0;
	}

	/**
	 * 
	 * @param value
	 * @return Checks if the argument being passed is the upper limit.
	 */
	public boolean isUpperLimit(T value) {
		return this.hiVal.compareTo(value) == 0;
	}
	
	@Override
	public String toString() {
		return "Range [min: "+loVal+", max: "+hiVal+"]" ;
	}

}
