package com.github.donkeyrit.twinkle.dal.jooq.repositories;

import java.util.stream.Stream;

import com.github.donkeyrit.twinkle.dal.interfaces.RentRepository;
import com.github.donkeyrit.twinkle.dal.models.Rent;

public class JooqRentRepository implements RentRepository {

	@Override
	public Rent findById(Long id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'findById'");
	}

	@Override
	public Stream<Rent> findAll() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'findAll'");
	}

	@Override
	public boolean save(Rent o) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'save'");
	}

	@Override
	public boolean delete(Rent o) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'delete'");
	}

	@Override
	public boolean update(Rent o) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'update'");
	}

	@Override
	public boolean isTaken(long carId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'isTaken'");
	}
	
}
