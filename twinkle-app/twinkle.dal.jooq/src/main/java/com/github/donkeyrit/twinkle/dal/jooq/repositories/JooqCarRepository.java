package com.github.donkeyrit.twinkle.dal.jooq.repositories;

import com.github.donkeyrit.twinkle.dal.jooq.abstractions.JooqFilterableRepository;
import com.github.donkeyrit.twinkle.dal.jooq.generated.tables.records.CarRecord;
import com.github.donkeyrit.twinkle.dal.common.specifications.PagedSpecification;
import com.github.donkeyrit.twinkle.dal.common.models.Page;
import com.github.donkeyrit.twinkle.dal.specifications.CarQuerySpecification;
import com.github.donkeyrit.twinkle.dal.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.dal.models.Car;

import com.google.inject.Inject;
import org.jooq.Condition;
import org.jooq.DSLContext;

public class JooqCarRepository 
	extends JooqFilterableRepository<Car, CarQuerySpecification, CarRecord>
	implements CarRepository {

	@Inject
	public JooqCarRepository(DSLContext dslContext) {
		super(dslContext, com.github.donkeyrit.twinkle.dal.jooq.generated.tables.Car.CAR);
	}

	@Override
	public Page<Car> getPagedResult(PagedSpecification<Car> filter) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented JooqCarRepository method 'getPagedResult'");
	}

	@Override
	public int getMaxPrice() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented JooqCarRepository method 'getMaxPrice'");
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
