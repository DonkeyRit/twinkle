package com.github.donkeyrit.twinkle.dal.models.filters;

public class Paging {

	public Paging(int pageNumber, int pageSize) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	// #region Fields

	private final int pageNumber;
	private final int pageSize;

	// #endregion
	// #region Getters

	public int getPageNumber() {
		return pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	// #endregion Getters

	public Paging next() {
		return new Paging(this.pageNumber + 1, this.pageSize);
	}

	public Paging previous() {
		return new Paging(this.pageNumber - 1, this.pageSize);
	}
}
