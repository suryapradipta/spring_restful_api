package com.ksatria.spring_restful_api.repository;

import com.ksatria.spring_restful_api.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
}
