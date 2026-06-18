package com.final_work_spring_boot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.final_work_spring_boot.model.Client;

@Repository
public interface IClientRepository extends JpaRepository<Client, Long> {
    @Modifying
    @Query("UPDATE Client cli SET cli.isActive = false WHERE cli.id = :id ")
    void logicDeleteById(@Param("id") Long id);

    boolean existsByDocument(String document);

    boolean existsByEmail(String email);
}

// findBy -> return the entity
// existsBy -> return a boolean
