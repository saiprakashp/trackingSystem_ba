package com.egil.pts.util;

import java.util.List;

public interface Specification<T> {
	public boolean isValid(List<T> product);
}
