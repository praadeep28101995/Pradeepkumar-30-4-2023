package com.avisys.cim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avisys.cim.repository.CustomerRepository;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping
	public ResponseEntity<?> getCustomers(@RequestParam(required = false) String firstName,
			@RequestParam(required = false) String lastName, @RequestParam(required = false) String mobileNumber) {
		if (firstName != null && lastName != null && mobileNumber != null) {
			return new ResponseEntity<>(customerRepository
					.findByFirstNameContainingAndLastNameContainingAndMobileNumber(firstName, lastName, mobileNumber),
					HttpStatus.OK);
		} else if (firstName != null && lastName != null) {
			return new ResponseEntity<>(
					customerRepository.findByFirstNameContainingAndLastNameContaining(firstName, lastName),
					HttpStatus.OK);
		} else if (firstName != null && mobileNumber != null) {
			return new ResponseEntity<>(
					customerRepository.findByFirstNameContainingAndMobileNumber(firstName, mobileNumber),
					HttpStatus.OK);
		} else if (lastName != null && mobileNumber != null) {
			return new ResponseEntity<>(
					customerRepository.findByLastNameContainingAndMobileNumber(lastName, mobileNumber), HttpStatus.OK);
		} else if (firstName != null) {
			return new ResponseEntity<>(customerRepository.findByFirstNameContaining(firstName), HttpStatus.OK);
		} else if (lastName != null) {
			return new ResponseEntity<>(customerRepository.findByLastNameContaining(lastName), HttpStatus.OK);
		} else if (mobileNumber != null) {
			return new ResponseEntity<>(customerRepository.findByMobileNumber(mobileNumber), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(customerRepository.findAll(), HttpStatus.OK);
		}
	}

	
}
