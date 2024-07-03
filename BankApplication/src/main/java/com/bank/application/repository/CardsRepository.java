package com.bank.application.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bank.application.entity.Cards;

import java.util.*;

@Repository
public interface CardsRepository extends CrudRepository<Cards, Long> {
	List<Cards> findByCustomerId(int customerId);

}
