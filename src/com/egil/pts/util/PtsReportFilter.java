package com.egil.pts.util;

import java.util.List;
import java.util.stream.Stream;

public interface PtsReportFilter<T> {
	public Stream<T> filterItem(List<T> product, Specification<T> spec);
}
