package com.github.donkeyrit.twinkle.dal.jooq.repositories;

import com.github.donkeyrit.twinkle.dal.jooq.abstractions.JooqFilterableRepository;
import com.github.donkeyrit.twinkle.dal.jooq.generated.tables.records.CarRecord;
import com.github.donkeyrit.twinkle.dal.common.specifications.PagedSpecification;
import com.github.donkeyrit.twinkle.dal.common.models.Page;
import com.github.donkeyrit.twinkle.dal.specifications.CarQuerySpecification;
import com.google.inject.Inject;
import com.github.donkeyrit.twinkle.dal.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.dal.models.Car;

import javax.sql.DataSource;

import org.jooq.Condition;

public class JooqCarRepository 
	extends JooqFilterableRepository<Car, CarQuerySpecification, CarRecord>
	implements CarRepository {

	@Inject
	public JooqCarRepository(DataSource dataSource) {
		super(dataSource, com.github.donkeyrit.twinkle.dal.jooq.generated.tables.Car.CAR);
	}

	@Override
	public Page<Car> getPagedResult(PagedSpecification<Car> filter) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getPagedResult'");
	}

	@Override
	public int getMaxPrice() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getMaxPrice'");
	}

	@Override
	public Condition toCondition(CarQuerySpecification querySpecification) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'toCondition'");
	}

	@Override
	protected Car mapRecordToEntity(CarRecord record) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'mapRecordToEntity'");
	}

	@Override
	protected CarRecord entityToRecord(Car entity) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'entityToRecord'");
	}
	
	
}
