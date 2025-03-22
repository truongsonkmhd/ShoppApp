package com.example.ShoppApp.repository;

import com.example.ShoppApp.model.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity,Long> {
    AddressEntity findByUserIdAndAddressType(Long userId, Integer addressType);
}