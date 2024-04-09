package com.github.donkeyrit.twinkle.dal.jooq.repositories;

import com.github.donkeyrit.twinkle.dal.jooq.generated.tables.records.BodyTypeRecord;
import com.github.donkeyrit.twinkle.dal.jooq.generated.tables.BodyType;
import com.github.donkeyrit.twinkle.dal.jooq.abstractions.JooqGenericRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.CarBodyTypeRepository;
import com.github.donkeyrit.twinkle.dal.models.CarBodyType;

import com.google.inject.Inject;
import org.jooq.DSLContext;

public class JooqCarBodyTypeRepository
	extends JooqGenericRepository<CarBodyType, BodyTypeRecord> implements CarBodyTypeRepository {

	@Inject
	public JooqCarBodyTypeRepository(DSLContext dslContext) {
		super(dslContext, BodyType.BODY_TYPE);
	}

	@Override
	protected CarBodyType mapRecordToEntity(BodyTypeRecord record) {
		return new CarBodyType(record.getId(), record.getBodyTypeName());
	}

	@Override
	protected BodyTypeRecord entityToRecord(CarBodyType entity) {
		return new BodyTypeRecord(entity.getId(), entity.getType());
	}	
}
