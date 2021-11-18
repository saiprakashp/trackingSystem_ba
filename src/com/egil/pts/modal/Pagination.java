package com.egil.pts.modal;

import java.io.Serializable;

public class Pagination implements Serializable{
	
	private static final long serialVersionUID = 4538419126947001697L;

	private int offset;

	private int size;

	public Pagination() {
	}

	public Pagination(int offset, int size) {
		this.offset = offset;
		this.size = size;
	}

	/**
	 * Gets the offset value for this PaginationType.
	 * 
	 * @return offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * Sets the offset value for this PaginationType.
	 * 
	 * @param offset
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * Gets the size value for this PaginationType.
	 * 
	 * @return size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Sets the size value for this PaginationType.
	 * 
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
	}

}
