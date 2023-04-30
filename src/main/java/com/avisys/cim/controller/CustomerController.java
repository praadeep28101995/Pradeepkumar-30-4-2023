package com.avisys.cim.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avisys.cim.Customer;
import com.avisys.cim.repository.CustomerRepository;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping
	public List<Customer> getCustomers(@RequestParam(required = false) String firstName,
			@RequestParam(required = false) String lastName, @RequestParam(required = false) String mobileNumber) {
		if (firstName != null && lastName != null && mobileNumber != null) {
			return customerRepository.findByFirstNameContainingAndLastNameContainingAndMobileNumber(firstName,
					lastName, mobileNumber);
		} else if (firstName != null && lastName != null) {
			return customerRepository.findByFirstNameContainingAndLastNameContaining(firstName, lastName);
		} else if (firstName != null && mobileNumber != null) {
			return customerRepository.findByFirstNameContainingAndMobileNumber(firstName, mobileNumber);
		} else if (lastName != null && mobileNumber != null) {
			return customerRepository.findByLastNameContainingAndMobileNumber(lastName, mobileNumber);
		} else if (firstName != null) {
			return customerRepository.findByFirstNameContaining(firstName);
		} else if (lastName != null) {
			return customerRepository.findByLastNameContaining(lastName);
		} else if (mobileNumber != null) {
			return customerRepository.findByMobileNumber(mobileNumber);
		} else {
			return customerRepository.findAll();
		}
	}

}

