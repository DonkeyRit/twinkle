package com.github.donkeyrit.twinkle.dal.interfaces;

import com.github.donkeyrit.twinkle.dal.common.repositories.GenericRepository;
import com.github.donkeyrit.twinkle.dal.models.Rent;

public interface RentRepository extends GenericRepository<Rent>  {
	boolean isTaken(long carId);
}
