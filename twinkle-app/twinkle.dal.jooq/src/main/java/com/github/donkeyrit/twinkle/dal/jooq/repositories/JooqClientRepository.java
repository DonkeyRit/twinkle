package com.github.donkeyrit.twinkle.dal.jooq.repositories;

import com.github.donkeyrit.twinkle.dal.jooq.generated.tables.records.ClientsRecord;
import com.github.donkeyrit.twinkle.dal.jooq.abstractions.JooqGenericRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.ClientRepository;
import com.github.donkeyrit.twinkle.dal.models.Client;

import javax.sql.DataSource;
import java.util.Optional;

public class JooqClientRepository
	extends JooqGenericRepository<Client, ClientsRecord> implements ClientRepository {
		

	public JooqClientRepository(DataSource dataSource) {
		super(dataSource, com.github.donkeyrit.twinkle.dal.jooq.generated.tables.Clients.CLIENTS);
	}

	@Override
	public Optional<Client> getByUserId(int userId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getByUserId'");
	}

	@Override
	protected Client mapRecordToEntity(ClientsRecord record) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'mapRecordToEntity'");
	}

	@Override
	protected ClientsRecord entityToRecord(Client entity) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'entityToRecord'");
	}
}
