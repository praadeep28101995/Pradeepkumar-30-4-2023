package com.avisys.cim.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avisys.cim.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	List<Customer> findByFirstNameContainingAndLastNameContainingAndMobileNumber(String firstName, String lastName,
			String mobileNumber);

	List<Customer> findByFirstNameContainingAndLastNameContaining(String firstName, String lastName);

	List<Customer> findByFirstNameContainingAndMobileNumber(String firstName, String mobileNumber);

	List<Customer> findByLastNameContainingAndMobileNumber(String lastName, String mobileNumber);

	List<Customer> findByFirstNameContaining(String firstName);

	List<Customer> findByLastNameContaining(String lastName);

	Optional<Customer> findByMobileNumber(String mobileNumber);
}
