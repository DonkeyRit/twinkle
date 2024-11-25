package com.github.donkeyrit.twinkle.dal.jooq.repositories;

import com.github.donkeyrit.twinkle.dal.jooq.generated.tables.records.RentRecord;
import com.github.donkeyrit.twinkle.dal.jooq.abstractions.JooqGenericRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.RentRepository;
import com.github.donkeyrit.twinkle.dal.models.Rent;

import com.google.inject.Inject;
import org.jooq.DSLContext;

public class JooqRentRepository
	extends JooqGenericRepository<Rent, RentRecord> implements RentRepository {

	@Inject
	public JooqRentRepository(DSLContext dslContext) {
		super(dslContext, com.github.donkeyrit.twinkle.dal.jooq.generated.tables.Rent.RENT);
	}

	@Override
	public boolean isTaken(long carId) {
		return false; //TODO: Update implementation
	}

	@Override
	protected Rent mapRecordToEntity(RentRecord record) {
		// return new Rent(
		// 	record.getId(),
		// 	record.getIdClient(),
		// 	record.getIdCar(), 
		// 	record.getStartDate(), 
		// 	record.getPlanDate(), 
		// 	record.getEndDate());

		return null;
	}

	@Override
	protected RentRecord entityToRecord(Rent entity) {
		return null;
	}
}
