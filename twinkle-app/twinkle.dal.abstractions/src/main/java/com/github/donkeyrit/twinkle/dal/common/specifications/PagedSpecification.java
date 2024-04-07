package com.github.donkeyrit.twinkle.dal.common.specifications;

import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;
import com.github.donkeyrit.twinkle.dal.common.models.Paging;
import java.util.Optional;

public abstract class PagedSpecification<T extends Identifiable> implements QuerySpecification<T> {

	//#region Fields

	private Optional<Paging> paging;

	public PagedSpecification() {
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
