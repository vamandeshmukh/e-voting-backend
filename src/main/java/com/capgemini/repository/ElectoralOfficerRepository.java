package com.capgemini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.model.ElectoralOfficer;

public interface ElectoralOfficerRepository extends JpaRepository<ElectoralOfficer, String> {

}
