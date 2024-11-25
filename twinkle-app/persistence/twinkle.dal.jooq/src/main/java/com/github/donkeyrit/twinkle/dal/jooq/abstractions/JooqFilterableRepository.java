package com.github.donkeyrit.twinkle.dal.jooq.abstractions;

import com.github.donkeyrit.twinkle.dal.common.repositories.FilterableRepository;
import com.github.donkeyrit.twinkle.dal.common.specifications.QuerySpecification;
import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;
import com.github.donkeyrit.twinkle.dal.common.models.Paging;
import com.github.donkeyrit.twinkle.dal.common.models.Page;

import java.util.stream.Stream;
import java.util.Optional;
import java.util.List;

import org.jooq.TableRecord;
import org.jooq.Table;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

public abstract class JooqFilterableRepository<T extends Identifiable, TSpecification extends QuerySpecification<T>, R extends TableRecord<R>>
		extends JooqGenericRepository<T, R> implements FilterableRepository<T, TSpecification> {

	public JooqFilterableRepository(DSLContext dslContext, Table<R> table) {
		super(dslContext, table);
	}
	
	@Override
	public Page<T> getPagedResult(TSpecification filter, Optional<Paging> paging) {

		Condition condition = toCondition(filter);

		var query = this.context
			.selectFrom(this.table)
			.where(condition)
			.orderBy(DSL.field("id"));

		List<T> pageContent = null;
		int pageNumber = 1,
			pageSize = Integer.MAX_VALUE;

		if (paging.isPresent()) {
			pageNumber = paging.get().getPageNumber();
			pageSize = paging.get().getPageSize();
			int offset = (pageNumber - 1) * pageSize;

            pageContent = query
                .limit(pageSize)
                .offset(offset)
                .fetchStream()
				.map(this::mapRecordToEntity)
				.toList();
		}
		else {
			pageContent = query
				.fetchStream()
				.map(this::mapRecordToEntity)
				.toList();
		}
		
		int totalElements = this.context.fetchCount(table, condition);
        return new Page<T>(pageContent, pageNumber, pageSize, totalElements);
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

		return Optional.ofNullable(record).map(this::mapRecordToEntity);
	}
	
	public abstract Condition toCondition(TSpecification querySpecification);
}
