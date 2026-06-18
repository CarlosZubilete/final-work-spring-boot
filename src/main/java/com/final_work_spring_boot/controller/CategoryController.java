package com.final_work_spring_boot.controller;

import java.util.List;

import com.final_work_spring_boot.dto.request.category.CategoryCreateDTO;
import com.final_work_spring_boot.dto.request.category.CategoryUpdateDTO;
import com.final_work_spring_boot.dto.response.CategoryResponseDTO;
import com.final_work_spring_boot.service.ICategoryService;
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
    private ICategoryService service;

    @GetMapping("/")
    public ResponseEntity<List<CategoryResponseDTO>> getCategoryList() {
        List<CategoryResponseDTO> brandList = service.getRecordsList();

        return brandList != null
            ? ResponseEntity.ok(brandList)
            : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id) {
        CategoryResponseDTO category = service.getRecordById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping("/")
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryCreateDTO dto) {
        CategoryResponseDTO created = service.saveRecord(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id,
                                                              @RequestBody CategoryUpdateDTO dto) {
        CategoryResponseDTO updated = service.updateRecord(id, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable Long id) {
        boolean deleted = service.deleteRecord(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
