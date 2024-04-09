package com.github.donkeyrit.twinkle.dal.jooq.abstractions;

import com.github.donkeyrit.twinkle.dal.common.repositories.FilterableRepository;
import com.github.donkeyrit.twinkle.dal.common.specifications.QuerySpecification;
import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;

import java.util.stream.Stream;
import java.util.Optional;

import org.jooq.TableRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Table;

public abstract class JooqFilterableRepository<T extends Identifiable, TSpecification extends QuerySpecification<T>, R extends TableRecord<R>>
		extends JooqGenericRepository<T, R> implements FilterableRepository<T, TSpecification> {

	public JooqFilterableRepository(DSLContext dslContext, Table<R> table) {
		super(dslContext, table);
	}

	@Override
	public Stream<T> getList(TSpecification querySpecification) {
		return this.context
			.selectFrom(this.table)
			.where(toCondition(querySpecification))
			.fetchStream()
			.map(this::mapRecordToEntity);
	}

	@Override
	public Optional<T> get(TSpecification querySpecification) {
		R record = this.context
			.selectFrom(this.table)
			.where(toCondition(querySpecification))
			.fetchOne();

		if (record != null) {
			return Optional.of(mapRecordToEntity(record));
		} else {
			return Optional.empty();
		}
	}

	public abstract Condition toCondition(TSpecification querySpecification);
}
