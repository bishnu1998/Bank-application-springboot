package com.bank.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bank.application.entity.Accounts;
import com.bank.application.repository.AccountsRepository;

@RestController
public class AccountController {

	@Autowired
	private AccountsRepository accountsRepository;

	@GetMapping("myAccount")
	public Accounts getAccountDetails(@RequestBody int id) {
		Accounts accounts = accountsRepository.findByCustomerId(id);
		if (accounts != null) {
			return accounts;
		} else {
			return null;
		}

	}

}
