package com.github.donkeyrit.twinkle.dal.jooq.abstractions;

import com.github.donkeyrit.twinkle.dal.common.repositories.FilterableRepository;
import com.github.donkeyrit.twinkle.dal.common.specifications.QuerySpecification;
import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;

import java.util.stream.Stream;
import java.util.Optional;
import javax.sql.DataSource;

import org.jooq.TableRecord;
import org.jooq.Table;
import org.jooq.Condition;

public abstract class JooqFilterableRepository<T extends Identifiable, TSpecification extends QuerySpecification<T>, R extends TableRecord<R>> 
    extends JooqGenericRepository<T, R> implements FilterableRepository<T, TSpecification> {

	public JooqFilterableRepository(DataSource dataSource, Table<R> table) {
		super(dataSource, table);
	}

	@Override
	public Stream<T> getList(TSpecification querySpecification) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getList'");
	}

	@Override
	public Optional<T> get(TSpecification querySpecification) {
		
		var result = this.context
			.select()
			.from(this.table)
			.where(toCondition(querySpecification))
			.fetch();

		return Optional.ofNullable(null);
	}

	public abstract Condition toCondition(TSpecification querySpecification);
}
