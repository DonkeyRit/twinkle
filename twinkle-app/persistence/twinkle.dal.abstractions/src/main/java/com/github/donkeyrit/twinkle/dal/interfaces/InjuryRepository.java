package com.github.donkeyrit.twinkle.dal.interfaces;

import com.github.donkeyrit.twinkle.dal.common.repositories.GenericRepository;
import com.github.donkeyrit.twinkle.dal.models.Injury;

import java.util.Optional;

public interface InjuryRepository extends GenericRepository<Injury> {
	Optional<Injury> getByName(String injuryName);
}
