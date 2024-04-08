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
import org.jooq.impl.DSL;

public class JooqModelOfCarRepository 
	extends JooqFilterableRepository<ModelOfCar, QuerySpecification<ModelOfCar>, ModelRecord>
	implements ModelOfCarRepository{

	@Inject
	public JooqModelOfCarRepository(DataSource dataSource) {
		super(dataSource, Model.MODEL);
	}

	@Override
	public Condition toCondition(QuerySpecification<ModelOfCar> querySpecification) {
		return DSL.noCondition();
	}

	@Override
	protected ModelOfCar mapRecordToEntity(ModelRecord record) {
		return new ModelOfCar(record.getId(), record.getModelName(), record.getIdMark(), record.getIdBodyType());
	}

	@Override
	protected ModelRecord entityToRecord(ModelOfCar entity) {
		return new ModelRecord(entity.getId(), entity.getModelName(), entity.getMark().getId(), entity.getBodyType().getId());
	}
}
