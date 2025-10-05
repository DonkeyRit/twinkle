package com.github.donkeyrit.twinkle.dal.interfaces;

import com.github.donkeyrit.twinkle.dal.common.repositories.GenericRepository;
import com.github.donkeyrit.twinkle.dal.models.Rent;

import java.util.Optional;

public interface RentRepository extends GenericRepository<Rent>  {
	boolean isTaken(long carId);

	Optional<Rent> getLastByCarId(int carId);

	Optional<Rent> getLastByClientId(int clientId);
}
