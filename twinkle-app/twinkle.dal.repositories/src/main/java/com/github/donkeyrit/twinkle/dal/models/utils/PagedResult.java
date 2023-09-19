package com.github.donkeyrit.twinkle.dal.models.utils;

import java.util.stream.Stream;

public class PagedResult<T> {

	private final Stream<T> result;
	private final long totalCount;

	//#region Getters/Setters

	public Stream<T> getResult() {
		return result;
	}

	public long getTotalCount() {
		return totalCount;
	}
	
	//#endregion
	
	public PagedResult(Stream<T> result, long totalCount) {
		this.result = result;
		this.totalCount = totalCount;
	}
}
