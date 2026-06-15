package com.final_work_spring_boot.controller;

import java.util.List;

import com.final_work_spring_boot.dto.request.brand.BrandUpdateDTO;
import com.final_work_spring_boot.dto.response.BrandResponseDTO;
import com.final_work_spring_boot.service.IBrandService;
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

import com.final_work_spring_boot.dto.request.brand.BrandCreateDTO;
import com.final_work_spring_boot.service.IGenericService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    @Autowired
    private IBrandService service;

    @GetMapping("/")
    public ResponseEntity<List<BrandResponseDTO>> getBrandList() {
        List<BrandResponseDTO> brands = service.getRecordsList();

        return brands != null
                ? ResponseEntity.ok(brands)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandResponseDTO> getBrandById(@PathVariable Long id) {
        BrandResponseDTO brand = service.getRecordById(id);
        return ResponseEntity.ok(brand);
    }

    @PostMapping("/")
    public ResponseEntity<BrandResponseDTO> saveBrand(@Valid @RequestBody BrandCreateDTO dto) {
        BrandResponseDTO saved = service.saveRecord(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandResponseDTO> updateBrand(@PathVariable Long id,
                                                        @Valid @RequestBody BrandUpdateDTO dto) {
        BrandResponseDTO updated = service.updateRecord(id, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteBrand(@PathVariable Long id) {
        boolean deleted = service.deleteRecord(id);

        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
