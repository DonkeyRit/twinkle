package com.github.donkeyrit.twinkle.dal.jooq.repositories;

import com.github.donkeyrit.twinkle.dal.jooq.abstractions.JooqFilterableRepository;
import com.github.donkeyrit.twinkle.dal.common.specifications.PagedSpecification;
import com.github.donkeyrit.twinkle.dal.common.models.Page;
import com.github.donkeyrit.twinkle.dal.specifications.CarQuerySpecification;
import com.github.donkeyrit.twinkle.dal.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.dal.models.Car;

import javax.sql.DataSource;
import org.jooq.Condition;

public class JooqCarRepository 
	extends JooqFilterableRepository<Car, CarQuerySpecification<Condition>>
	implements CarRepository<Condition> {

	public JooqCarRepository(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Page<Car> getPagedResult(PagedSpecification<Car, Condition> filter) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getPagedResult'");
	}

	@Override
	public int getMaxPrice() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getMaxPrice'");
	}
	
	
}
