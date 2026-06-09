package com.final_work_spring_boot.controller;

import java.util.List;

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

import com.final_work_spring_boot.dto.BrandDTO;
import com.final_work_spring_boot.service.IGenericService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    @Autowired
    private IGenericService<BrandDTO> service;

    @GetMapping("/")
    public ResponseEntity<List<BrandDTO>> getBrandList() {
        List<BrandDTO> brands = service.getAll();

        return brands != null
                ? ResponseEntity.ok(brands)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandDTO> getBrandById(@PathVariable Long id) {
        BrandDTO brand = service.getById(id);
        return ResponseEntity.ok(brand);
    }

    @PostMapping("/")
    public ResponseEntity<BrandDTO> saveBrand(@Valid @RequestBody BrandDTO dto) {
        BrandDTO saved = service.save(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandDTO> updateBrand(@PathVariable Long id,
                                                @RequestBody BrandDTO dto) {
        BrandDTO updated = service.update(dto, id);

        return ResponseEntity.status(HttpStatus.CREATED).body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteBrand(@PathVariable Long id) {
        boolean deleted = service.delete(id);

        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
