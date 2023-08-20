package com.github.donkeyrit.twinkle.dal.repositories.filters;

import com.github.donkeyrit.twinkle.dal.contracts.QueryFilter;
import com.github.donkeyrit.twinkle.dal.models.filters.Paging;

public class PagingFilter implements QueryFilter {

	//#region Fields

	private Paging paging;

	//#endregion
	//#region Getters/Setters

	public Paging getPaging() {
		return paging;
	}

	public void setPaging(Paging paging) {
		this.paging = paging;
	}

	//#endregion
}
