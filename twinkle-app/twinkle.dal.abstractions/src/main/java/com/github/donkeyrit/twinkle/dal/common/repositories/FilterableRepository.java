package com.github.donkeyrit.twinkle.dal.common.repositories;

import com.github.donkeyrit.twinkle.dal.common.specifications.QuerySpecification;
import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;

import java.util.stream.Stream;
import java.util.Optional;

public interface FilterableRepository<T extends Identifiable, TPredicate, TSpecification extends QuerySpecification<T, TPredicate>> extends GenericRepository<T> {
	Stream<T> getList(TSpecification querySpecification);
	Optional<T> get(TSpecification quSpecification);
}
