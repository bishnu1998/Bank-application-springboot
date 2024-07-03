package com.bank.application.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bank.application.entity.Accounts;

@Repository
public interface AccountsRepository extends CrudRepository<Accounts, Long> {
	Accounts findByCustomerId(int castomerId);

}
