package com.github.donkeyrit.twinkle.dal.common.repositories;

import java.util.stream.Stream;

import com.github.donkeyrit.twinkle.dal.common.Identifiable;
import com.github.donkeyrit.twinkle.dal.common.specifications.QuerySpecification;

public interface FilterableRepository<T extends Identifiable, TSpecification extends QuerySpecification<T>> extends GenericRepository<T> {
	Stream<T> getList(TSpecification querySpecification);
}
