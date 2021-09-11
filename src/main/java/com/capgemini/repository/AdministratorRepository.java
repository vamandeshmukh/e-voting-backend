package com.capgemini.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.model.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, String> {

}
