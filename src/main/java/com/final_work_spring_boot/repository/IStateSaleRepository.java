package com.final_work_spring_boot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.final_work_spring_boot.model.StateSale;

@Repository
public interface IStateSaleRepository extends JpaRepository<StateSale, Long> {
    Optional<StateSale> findByName(String name);
}
