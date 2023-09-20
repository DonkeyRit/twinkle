package com.github.donkeyrit.twinkle.dal.repositories.filters;

import com.github.donkeyrit.twinkle.dal.interfaces.QueryFilter;
import com.github.donkeyrit.twinkle.dal.models.filters.Paging;
import java.util.Optional;

public class PagingFilter implements QueryFilter {

	//#region Fields

	private Optional<Paging> paging;

	public PagingFilter() {
		paging = Optional.empty();
	}

	//#endregion
	//#region Getters/Setters

	public Optional<Paging> getPaging() {
		return paging;
	}

	public void setPaging(Paging paging) {
		this.paging = Optional.of(paging);
	}

	//#endregion
}
