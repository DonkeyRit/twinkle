package com.github.donkeyrit.twinkle.dal.jooq.repositories;

import com.github.donkeyrit.twinkle.dal.jooq.generated.tables.records.RentRecord;
import com.github.donkeyrit.twinkle.dal.jooq.abstractions.JooqGenericRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.RentRepository;

import com.google.inject.Inject;
import org.jooq.Condition;
import org.jooq.DSLContext;

import java.util.Optional;

public class JooqRentRepository
	extends JooqGenericRepository<com.github.donkeyrit.twinkle.dal.models.Rent, RentRecord>
	implements RentRepository {

	private static final com.github.donkeyrit.twinkle.dal.jooq.generated.tables.Rent RENT =
		com.github.donkeyrit.twinkle.dal.jooq.generated.tables.Rent.RENT;

	@Inject
	public JooqRentRepository(DSLContext dslContext) {
		super(dslContext, RENT);
	}

	@Override
	public boolean isTaken(long carId) {
		return getLastByCarId((int) carId)
			.map(rent -> rent.getEndDate() == null)
			.orElse(false);
	}

	@Override
	public Optional<com.github.donkeyrit.twinkle.dal.models.Rent> getLastByCarId(int carId) {
		return findLast(RENT.ID_CAR.eq(carId));
	}

	@Override
	public Optional<com.github.donkeyrit.twinkle.dal.models.Rent> getLastByClientId(int clientId) {
		return findLast(RENT.ID_CLIENT.eq(clientId));
	}

	// The currently active rent (no end date yet) should win over any closed one, so open
	// rents sort first; among closed rents, the most recently ended one comes first. This
	// mirrors RentRepositoryImpl (twinkle.dal.hibernate) so both DAL implementations agree
	// on what "last" means.
	private Optional<com.github.donkeyrit.twinkle.dal.models.Rent> findLast(Condition condition) {
		RentRecord record = this.context
			.selectFrom(this.table)
			.where(condition)
			.orderBy(RENT.END_DATE.desc().nullsFirst(), RENT.PLAN_DATE.desc())
			.limit(1)
			.fetchOne();

		return Optional.ofNullable(record).map(this::mapRecordToEntity);
	}

	@Override
	protected com.github.donkeyrit.twinkle.dal.models.Rent mapRecordToEntity(RentRecord record) {
		return new com.github.donkeyrit.twinkle.dal.models.Rent(
			record.getId(),
			record.getIdClient(),
			record.getIdCar(),
			record.getStartDate(),
			record.getPlanDate(),
			record.getEndDate());
	}

	@Override
	protected RentRecord entityToRecord(com.github.donkeyrit.twinkle.dal.models.Rent entity) {
		return new RentRecord(
			entity.getId(),
			entity.getIdClient(),
			entity.getIdCar(),
			entity.getStartDate(),
			entity.getPlanDate(),
			entity.getEndDate());
	}
}
