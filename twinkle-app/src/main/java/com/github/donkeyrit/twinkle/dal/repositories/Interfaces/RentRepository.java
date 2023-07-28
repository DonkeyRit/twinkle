package com.github.donkeyrit.twinkle.dal.repositories.interfaces;

import com.github.donkeyrit.twinkle.dal.contracts.CrudRepository;
import com.github.donkeyrit.twinkle.dal.models.Rent;

public interface RentRepository extends CrudRepository<Rent>  {
	
	boolean isTaken(int carId);
}
