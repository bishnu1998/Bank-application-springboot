package com.bank.application.controller;

import java.sql.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bank.application.entity.Contact;
import com.bank.application.repository.ContactRepository;

@RestController
public class ContactController {

	@Autowired
	private ContactRepository contactRepository;

	@PostMapping("/contact") 
	public Contact saveContactDetails(@RequestBody Contact contact) {

		contact.setContactId(getServiceRegNumber());
		contact.setCreateDt(new Date(System.currentTimeMillis()));
		return contactRepository.save(contact);

	}

	public String getServiceRegNumber() {
		Random random = new Random();
		int ranNum = random.nextInt(999999999 - 9999) + 9999;
		return "SR" + ranNum;
	}
}
