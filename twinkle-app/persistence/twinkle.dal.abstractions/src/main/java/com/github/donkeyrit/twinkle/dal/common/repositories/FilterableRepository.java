package com.github.donkeyrit.twinkle.dal.common.repositories;

import com.github.donkeyrit.twinkle.dal.common.specifications.QuerySpecification;
import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;
import com.github.donkeyrit.twinkle.dal.common.models.Paging;
import com.github.donkeyrit.twinkle.dal.common.models.Page;

import java.util.stream.Stream;
import java.util.Optional;

public interface FilterableRepository<T extends Identifiable, TSpecification extends QuerySpecification<T>> extends GenericRepository<T> {
	Stream<T> getList(TSpecification querySpecification);
	Optional<T> get(TSpecification quSpecification);
	Page<T> getPagedResult(TSpecification filter, Optional<Paging> paging);
}
