package com.github.donkeyrit.twinkle.dal.jooq.repositories;

import com.github.donkeyrit.twinkle.dal.jooq.generated.tables.BodyType;
import com.github.donkeyrit.twinkle.dal.jooq.generated.tables.records.BodyTypeRecord;
import com.github.donkeyrit.twinkle.dal.jooq.abstractions.JooqGenericRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.CarBodyTypeRepository;
import com.github.donkeyrit.twinkle.dal.models.CarBodyType;

import javax.sql.DataSource;

public class JooqCarBodyTypeRepository
	extends JooqGenericRepository<CarBodyType, BodyTypeRecord> implements CarBodyTypeRepository {

	public JooqCarBodyTypeRepository(DataSource dataSource) {
		super(dataSource, BodyType.BODY_TYPE);
	}

	@Override
	protected CarBodyType mapRecordToEntity(BodyTypeRecord record) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'mapRecordToEntity'");
	}

	@Override
	protected BodyTypeRecord entityToRecord(CarBodyType entity) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'entityToRecord'");
	}	
}
