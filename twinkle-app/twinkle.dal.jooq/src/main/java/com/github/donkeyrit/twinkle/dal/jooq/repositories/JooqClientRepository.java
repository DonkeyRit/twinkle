package com.github.donkeyrit.twinkle.dal.jooq.repositories;

import com.github.donkeyrit.twinkle.dal.jooq.generated.tables.records.ClientsRecord;
import com.github.donkeyrit.twinkle.dal.jooq.abstractions.JooqGenericRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.ClientRepository;
import com.github.donkeyrit.twinkle.dal.models.Client;

import com.google.inject.Inject;
import org.jooq.DSLContext;
import java.util.Optional;

public class JooqClientRepository
	extends JooqGenericRepository<Client, ClientsRecord> implements ClientRepository {
		
	@Inject
	public JooqClientRepository(DSLContext dslContext) {
		super(dslContext, com.github.donkeyrit.twinkle.dal.jooq.generated.tables.Clients.CLIENTS);
	}

	@Override
	public Optional<Client> getByUserId(int userId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented JooqClientRepository method 'getByUserId'");
	}

	@Override
	protected Client mapRecordToEntity(ClientsRecord record) {
		return new Client(
			record.getId(),
			record.getFirstName(), 
			record.getSecondName(), 
			record.getMiddleName(), 
			record.getAddress(),
			record.getPhoneNumber(), 
			record.getIdUser());
	}

	@Override
	protected ClientsRecord entityToRecord(Client entity) {
		return new ClientsRecord(
			entity.getId(),
			entity.getFirstName(), 
			entity.getSecondName(), 
			entity.getMiddleName(), 
			entity.getAddress(),
			entity.getPhoneNumber(), 
			entity.getUserId());
	}
}
