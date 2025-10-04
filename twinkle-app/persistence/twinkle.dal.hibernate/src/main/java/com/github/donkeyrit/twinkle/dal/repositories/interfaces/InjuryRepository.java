package com.github.donkeyrit.twinkle.dal.repositories.interfaces;

import com.github.donkeyrit.twinkle.dal.interfaces.CrudRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.QueryFilter;
import com.github.donkeyrit.twinkle.dal.models.Injury1;

import java.util.Optional;

public interface InjuryRepository extends CrudRepository<Injury1, QueryFilter> {
	Optional<Injury1> getByName(String injuryName);
}
