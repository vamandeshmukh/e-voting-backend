package com.capgemini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capgemini.model.Pincode;

public interface PincodeRepository extends JpaRepository<Pincode, Integer> {
	
	@Query(value = "Select p From Pincode p Where p.state = :state")
	public List<Pincode> readAllPincodeByState(@Param("state") String state);

}
