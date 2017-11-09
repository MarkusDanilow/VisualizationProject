package com.base.common.resources;

import java.lang.reflect.Array;

public class Cache<E> {

	private E[]	data ; 
	private int cachePointer = 0 ; 
	
	@SuppressWarnings("unchecked")
	public Cache(int cacheSize, Class<E> clazz) {
		this.data = (E[]) Array.newInstance(clazz, cacheSize);
		this.resetCachePointer();
	}

	public void resetCachePointer(){
		this.cachePointer = 0 ; 
	}

	private void incrementCachePointer(){
		this.cachePointer++ ; 
		if(this.cachePointer >= data.length){
			this.resetCachePointer(); 
		}
	}
	
	public E get(){
		E element = this.data[this.cachePointer];
		this.incrementCachePointer(); 
		return element ; 
	}
	
	public void add(E element){
		this.data[this.cachePointer] = element ;
		this.incrementCachePointer(); 
	}
	
}
