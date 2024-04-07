package com.github.donkeyrit.twinkle.dal.jooq.repositories;

import com.github.donkeyrit.twinkle.dal.jooq.abstractions.JooqFilterableRepository;
import com.github.donkeyrit.twinkle.dal.jooq.generated.tables.Model;
import com.github.donkeyrit.twinkle.dal.jooq.generated.tables.records.ModelRecord;
import com.github.donkeyrit.twinkle.dal.common.specifications.QuerySpecification;
import com.github.donkeyrit.twinkle.dal.interfaces.ModelOfCarRepository;
import com.github.donkeyrit.twinkle.dal.models.ModelOfCar;
import com.google.inject.Inject;

import javax.sql.DataSource;

import org.jooq.Condition;

public class JooqModelOfCarRepository 
	extends JooqFilterableRepository<ModelOfCar, QuerySpecification<ModelOfCar>, ModelRecord>
	implements ModelOfCarRepository{

	@Inject
	public JooqModelOfCarRepository(DataSource dataSource) {
		super(dataSource, Model.MODEL);
	}

	@Override
	public Condition toCondition(QuerySpecification<ModelOfCar> querySpecification) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'toCondition'");
	}

	@Override
	protected ModelOfCar mapRecordToEntity(ModelRecord record) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'mapRecordToEntity'");
	}

}
