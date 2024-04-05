package com.github.donkeyrit.twinkle.dal.jooq.abstractions;

import com.github.donkeyrit.twinkle.dal.common.repositories.FilterableRepository;
import com.github.donkeyrit.twinkle.dal.common.specifications.QuerySpecification;
import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;
import com.github.donkeyrit.twinkle.dal.jooq.generated.tables.Users;

import java.util.stream.Stream;
import java.util.Optional;
import javax.sql.DataSource;
import org.jooq.Condition;

public class JooqFilterableRepository<T extends Identifiable, TSpecification extends QuerySpecification<T, Condition>> 
	extends JooqGenericRepository<T> 
	implements FilterableRepository<T, Condition, TSpecification> {

	public JooqFilterableRepository(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Stream<T> getList(TSpecification querySpecification) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getList'");
	}

	@Override
	public Optional<T> get(TSpecification querySpecification) {
		
		this.context
			.select()
			.from(Users.USERS)
			.where(querySpecification.toCondition())
			.fetch();

		return Optional.of(null);
	}
}
