package com.github.donkeyrit.twinkle.models;

import com.github.donkeyrit.twinkle.dal.common.models.Paging;
import java.util.Optional;

public class PagingSearchFilterViewModel {
	
	//#region Fields

	private Optional<Paging> paging;

	public PagingSearchFilterViewModel() {
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
