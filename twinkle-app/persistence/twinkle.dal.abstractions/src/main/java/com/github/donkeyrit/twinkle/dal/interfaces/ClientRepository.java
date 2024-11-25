package com.github.donkeyrit.twinkle.dal.interfaces;

import com.github.donkeyrit.twinkle.dal.common.repositories.GenericRepository;
import com.github.donkeyrit.twinkle.dal.models.Client;

import java.util.Optional;

public interface ClientRepository extends GenericRepository<Client> {
	Optional<Client> getByUserId(int userId);
}
