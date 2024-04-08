package com.github.donkeyrit.twinkle.dal.jooq.repositories;

import com.github.donkeyrit.twinkle.dal.jooq.generated.tables.records.RentRecord;
import com.github.donkeyrit.twinkle.dal.jooq.abstractions.JooqGenericRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.RentRepository;
import com.github.donkeyrit.twinkle.dal.models.Rent;

import javax.sql.DataSource;

public class JooqRentRepository
	extends JooqGenericRepository<Rent, RentRecord> implements RentRepository {

	public JooqRentRepository(DataSource dataSource) {
		super(dataSource, com.github.donkeyrit.twinkle.dal.jooq.generated.tables.Rent.RENT);
	}

	@Override
	public boolean isTaken(long carId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'isTaken'");
	}

	@Override
	protected Rent mapRecordToEntity(RentRecord record) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'mapRecordToEntity'");
	}

	@Override
	protected RentRecord entityToRecord(Rent entity) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'entityToRecord'");
	}
}
