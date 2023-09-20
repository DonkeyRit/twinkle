package com.github.donkeyrit.twinkle.dal.models.filters;

public class Paging {

	public Paging(int pageNumber, int pageSize) {
		this(pageNumber, pageSize, false);
	}

	private Paging(int pageNumber, int pageSize, boolean all) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.all = all;
	}

	// #region Fields

	private final int pageNumber;
	private final int pageSize;
	private final boolean all;

	// #endregion
	// #region Getters

	public int getPageNumber() {
		return pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public boolean isAll() {
		return all;
	}

	// #endregion Getters

	public Paging next() {
		return new Paging(this.pageNumber + 1, this.pageSize);
	}

	public Paging previous() {
		return new Paging(this.pageNumber - 1, this.pageSize);
	}

	public static Paging all() {
		return new Paging(0, 0, true);
	}
}
