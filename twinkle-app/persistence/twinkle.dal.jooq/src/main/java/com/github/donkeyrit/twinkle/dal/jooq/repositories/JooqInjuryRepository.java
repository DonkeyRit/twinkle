package com.github.donkeyrit.twinkle.dal.jooq.repositories;

import com.github.donkeyrit.twinkle.dal.jooq.generated.tables.Injury;
import com.github.donkeyrit.twinkle.dal.jooq.generated.tables.records.InjuryRecord;
import com.github.donkeyrit.twinkle.dal.jooq.abstractions.JooqGenericRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.InjuryRepository;

import com.google.inject.Inject;
import org.jooq.DSLContext;

import java.util.Optional;

public class JooqInjuryRepository
	extends JooqGenericRepository<com.github.donkeyrit.twinkle.dal.models.Injury, InjuryRecord>
	implements InjuryRepository {

	@Inject
	public JooqInjuryRepository(DSLContext dslContext) {
		super(dslContext, Injury.INJURY);
	}

	@Override
	public Optional<com.github.donkeyrit.twinkle.dal.models.Injury> getByName(String injuryName) {
		InjuryRecord record = this.context
			.selectFrom(this.table)
			.where(Injury.INJURY.INJURY_NAME.eq(injuryName))
			.fetchOne();

		return Optional.ofNullable(record).map(this::mapRecordToEntity);
	}

	@Override
	protected com.github.donkeyrit.twinkle.dal.models.Injury mapRecordToEntity(InjuryRecord record) {
		return new com.github.donkeyrit.twinkle.dal.models.Injury(record.getId(), record.getInjuryName());
	}

	@Override
	protected InjuryRecord entityToRecord(com.github.donkeyrit.twinkle.dal.models.Injury entity) {
		return new InjuryRecord(entity.getId(), entity.getInjuryName());
	}
}
