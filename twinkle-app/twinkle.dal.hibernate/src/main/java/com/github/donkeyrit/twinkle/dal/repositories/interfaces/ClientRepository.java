package com.github.donkeyrit.twinkle.dal.repositories.interfaces;

import com.github.donkeyrit.twinkle.dal.interfaces.CrudRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.QueryFilter;
import com.github.donkeyrit.twinkle.dal.models.Client;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, QueryFilter> {
	Optional<Client> getByUserId(int userId);
}
