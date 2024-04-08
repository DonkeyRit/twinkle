package com.github.donkeyrit.twinkle.dal.jooq.repositories;

import com.github.donkeyrit.twinkle.dal.jooq.generated.tables.records.MarkRecord;
import com.github.donkeyrit.twinkle.dal.jooq.abstractions.JooqGenericRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.MarkOfCarRepository;
import com.github.donkeyrit.twinkle.dal.jooq.generated.tables.Mark;
import com.github.donkeyrit.twinkle.dal.models.MarkOfCar;

import javax.sql.DataSource;

public class JooqMarkOfCarRepository 
	extends JooqGenericRepository<MarkOfCar, MarkRecord> implements MarkOfCarRepository {

	public JooqMarkOfCarRepository(DataSource dataSource) {
		super(dataSource, Mark.MARK);
	}

	@Override
	protected MarkOfCar mapRecordToEntity(MarkRecord record) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'mapRecordToEntity'");
	}

	@Override
	protected MarkRecord entityToRecord(MarkOfCar entity) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'entityToRecord'");
	}

	
}
