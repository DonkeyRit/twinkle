package com.github.donkeyrit.twinkle.dal.repositories.interfaces;

import com.github.donkeyrit.twinkle.dal.interfaces.CrudRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.QueryFilter;
import com.github.donkeyrit.twinkle.dal.models.Client1;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client1, QueryFilter> {
	Optional<Client1> getByUserId(int userId);
}
