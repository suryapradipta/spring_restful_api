package com.ksatria.spring_restful_api.graphql.repository;

import com.ksatria.spring_restful_api.graphql.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, String> {
}
