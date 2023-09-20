package com.github.donkeyrit.twinkle.bll.models;

import java.util.List;

public class PagedResultBll<T> {

	private final List<T> result;
	private final long totalCount;

	//#region Getters/Setters

	public List<T> getResult() {
		return result;
	}

	public long getTotalCount() {
		return totalCount;
	}
	
	//#endregion
	
	public PagedResultBll(List<T> result, long totalCount) {
		this.result = result;
		this.totalCount = totalCount;
	}
}
