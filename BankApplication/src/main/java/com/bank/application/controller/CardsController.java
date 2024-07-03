package com.bank.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.application.entity.Cards;
import com.bank.application.repository.CardsRepository;

@RestController
public class CardsController {

	@Autowired
	private CardsRepository cardsRepository;

	@GetMapping("/myCard")
	public List<Cards> getCardDetails(@RequestParam int id) {

		List<Cards> cards = cardsRepository.findByCustomerId(id);
		if (cards != null) {
			return cards;
		} else {
			return null;
		}
	}

}
