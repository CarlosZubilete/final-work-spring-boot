package com.final_work_spring_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.final_work_spring_boot.model.Product;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    @Modifying
    @Query("UPDATE Product p SET p.isActive = 'false' WHERE p.id = :id ")
    void logicDeleteById(@Param("id") Long id);

    boolean existsByInventoryId(Long inventoryId);
}
