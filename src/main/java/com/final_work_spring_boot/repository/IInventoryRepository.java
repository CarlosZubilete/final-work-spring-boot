package com.final_work_spring_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.final_work_spring_boot.model.Inventory;

import java.util.Optional;

@Repository
public interface IInventoryRepository extends JpaRepository<Inventory, Long> {
    boolean existsByCodeSKU(String codeSKU);
}

// findBy -> return the entity
// existsBy -> return a boolean