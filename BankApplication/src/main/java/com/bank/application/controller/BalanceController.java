package com.bank.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.application.entity.AccountTransactions;
import com.bank.application.repository.AccountTransactionsRepository;

@RestController
public class BalanceController {

	@Autowired
	private AccountTransactionsRepository accountTransactionsReposistory;

	@GetMapping("/myBalance")
	public List<AccountTransactions> getBalanceDetails(@RequestParam int id) {

		List<AccountTransactions> accountTransactions = accountTransactionsReposistory
				.findByCustomerIdOrderByTransactionDtDesc(id);
		if (accountTransactions != null) {
			return accountTransactions;
		} else {
			return null;
		}

	}

}
