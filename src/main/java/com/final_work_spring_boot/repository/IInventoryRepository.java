package com.final_work_spring_boot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.final_work_spring_boot.model.Inventory;

@Repository
public interface IInventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByCodeSKU(String codeSKU);
}