package com.final_work_spring_boot.controller;

import java.util.List;

import com.final_work_spring_boot.dto.CategoryDTO;
import com.final_work_spring_boot.service.IGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private IGenericService<CategoryDTO> service;

    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> getCategoryList() {
        List<CategoryDTO> brandList = service.getAll();

        return brandList != null
                ? ResponseEntity.ok(brandList)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        CategoryDTO category = service.getById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping("/")
    public ResponseEntity<CategoryDTO> saveCategory(@Valid @RequestBody CategoryDTO dto) {
        CategoryDTO saved = service.save(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id,
                                                      @RequestBody CategoryDTO dto) {
        CategoryDTO updated = service.update(dto, id);

        return ResponseEntity.status(HttpStatus.CREATED).body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable Long id) {
        boolean deleted = service.delete(id);

        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
