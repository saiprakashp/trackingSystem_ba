package com.egil.pts.dao.common;

public interface Actionable<T> {
	// if doThis changes the entity returns true
	// this is necessary to remain in the persistence context and do whatever we
	// need to do
	boolean doThis(T entity);
}
