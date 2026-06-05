package com.final_work_spring_boot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.final_work_spring_boot.model.Category;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {

}
