package com.final_work_spring_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.final_work_spring_boot.model.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.isActive = false WHERE p.id = :id ")
    int softDeleteById(@Param("id") Long id);

    @Query("SELECT p FROM Product p WHERE p.isActive = :isActive")
    List<Product> findAllByStatus(@Param("isActive") Boolean isActive);

    @Query("SELECT p FROM Product p WHERE p.id = :id AND p.isActive = :isActive")
    Optional<Product> findByIdAndStatus(@Param("id") Long id, @Param("isActive") Boolean isActive);

}
