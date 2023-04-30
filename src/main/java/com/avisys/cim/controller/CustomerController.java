package com.avisys.cim.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avisys.cim.Customer;
import com.avisys.cim.repository.CustomerRepository;

import jakarta.transaction.Transactional;

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

	@PostMapping
	public ResponseEntity<ApiResponse> createCustomer(@RequestBody Customer customer) {
		Optional<Customer> optional = customerRepository.findByMobileNumber(customer.getMobileNumber());
		if (!optional.isPresent()) {
			customerRepository.save(customer);
			return new ResponseEntity<ApiResponse>(new ApiResponse("customer added", true), HttpStatus.OK);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse("Mobile Number is already exist", false), HttpStatus.OK);

	}
	
	
	@DeleteMapping("/{mobileNumber}")
	public String deleteByMobileNumber(@PathVariable String mobileNumber){
		Optional<Customer> findByMobileNumber = customerRepository.findByMobileNumber(mobileNumber);
		findByMobileNumber.ifPresent(customer->customerRepository.delete(customer));
		return "Customer Deleted Successfully";
	}
	@DeleteMapping("/{id}/mobile")
	public ResponseEntity<String> deleteMobileNumber(@PathVariable Long id) {
	    Optional<Customer> optionalCustomer = customerRepository.findById(id);
	    if (optionalCustomer.isPresent()) {
	        Customer customer = optionalCustomer.get();
	        customer.setMobileNumber("");
	        customerRepository.save(customer);
	        return ResponseEntity.ok("Mobile number deleted successfully.");
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@PutMapping("/{id}/mobile")
	public ResponseEntity<String> deleteMobileNumber(@PathVariable Long id,@RequestBody String mobileNumber) {
		System.out.println(id);
	    Optional<Customer> optionalCustomer = customerRepository.findById(id);
	    if (optionalCustomer.isPresent()) {
	        Customer customer = optionalCustomer.get();
	        customer.setMobileNumber(mobileNumber);
	        customerRepository.save(customer);
	        return ResponseEntity.ok("Mobile number added successfully.");
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}

}
