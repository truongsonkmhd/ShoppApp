package com.example.ShoppApp.repository;

import com.example.ShoppApp.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    Address findByUserIdAndAddressType(Long userId, Integer addressType);
}