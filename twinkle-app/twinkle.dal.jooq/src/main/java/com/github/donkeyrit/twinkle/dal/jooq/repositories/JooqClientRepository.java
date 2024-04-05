package com.github.donkeyrit.twinkle.dal.jooq.repositories;

import java.util.Optional;
import java.util.stream.Stream;

import com.github.donkeyrit.twinkle.dal.interfaces.ClientRepository;
import com.github.donkeyrit.twinkle.dal.models.Client;

public class JooqClientRepository implements ClientRepository {

	@Override
	public Client findById(int id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'findById'");
	}

	@Override
	public Stream<Client> findAll() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'findAll'");
	}

	@Override
	public boolean save(Client o) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'save'");
	}

	@Override
	public boolean delete(Client o) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'delete'");
	}

	@Override
	public boolean update(Client o) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'update'");
	}

	@Override
	public Optional<Client> getByUserId(int userId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getByUserId'");
	}
	
}
