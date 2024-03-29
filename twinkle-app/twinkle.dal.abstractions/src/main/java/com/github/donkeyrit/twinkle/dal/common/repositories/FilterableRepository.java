package com.github.donkeyrit.twinkle.dal.common.repositories;

import com.github.donkeyrit.twinkle.dal.common.specifications.QuerySpecification;
import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;
import java.util.stream.Stream;

public interface FilterableRepository<T extends Identifiable, TSpecification extends QuerySpecification<T>> extends GenericRepository<T> {
	Stream<T> getList(TSpecification querySpecification);
}
