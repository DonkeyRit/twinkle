package com.github.donkeyrit.twinkle.dal.jooq.repositories;

import com.github.donkeyrit.twinkle.dal.jooq.generated.tables.records.MarkRecord;
import com.github.donkeyrit.twinkle.dal.jooq.abstractions.JooqGenericRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.MarkOfCarRepository;
import com.github.donkeyrit.twinkle.dal.jooq.generated.tables.Mark;
import com.github.donkeyrit.twinkle.dal.models.MarkOfCar;

import com.google.inject.Inject;
import org.jooq.DSLContext;

public class JooqMarkOfCarRepository 
	extends JooqGenericRepository<MarkOfCar, MarkRecord> implements MarkOfCarRepository {

	@Inject
	public JooqMarkOfCarRepository(DSLContext dslContext) {
		super(dslContext, Mark.MARK);
	}

	@Override
	protected MarkOfCar mapRecordToEntity(MarkRecord record) {
		return new MarkOfCar(record.getId(), record.getMarkName(), record.getIdCountry());
	}

	@Override
	protected MarkRecord entityToRecord(MarkOfCar entity) {
		return new MarkRecord(entity.getId(), entity.getName(), entity.getCountry().getId());
	}
}
