package com.github.donkeyrit.twinkle.dal.jooq.repositories;

import com.github.donkeyrit.twinkle.dal.jooq.generated.tables.ResultingInjury;
import com.github.donkeyrit.twinkle.dal.jooq.generated.tables.records.ResultingInjuryRecord;
import com.github.donkeyrit.twinkle.dal.jooq.abstractions.JooqGenericRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.ResultingInjuryRepository;

import com.google.inject.Inject;
import org.jooq.DSLContext;

public class JooqResultingInjuryRepository
	extends JooqGenericRepository<com.github.donkeyrit.twinkle.dal.models.ResultingInjury, ResultingInjuryRecord>
	implements ResultingInjuryRepository {

	@Inject
	public JooqResultingInjuryRepository(DSLContext dslContext) {
		super(dslContext, ResultingInjury.RESULTING_INJURY);
	}

	@Override
	protected com.github.donkeyrit.twinkle.dal.models.ResultingInjury mapRecordToEntity(ResultingInjuryRecord record) {
		return new com.github.donkeyrit.twinkle.dal.models.ResultingInjury(
			record.getId(), record.getIdRent(), record.getIdInjury());
	}

	@Override
	protected ResultingInjuryRecord entityToRecord(com.github.donkeyrit.twinkle.dal.models.ResultingInjury entity) {
		return new ResultingInjuryRecord(entity.getId(), entity.getIdRent(), entity.getIdInjury());
	}
}
