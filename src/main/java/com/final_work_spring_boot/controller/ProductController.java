package com.final_work_spring_boot.controller;

import java.util.List;

import com.final_work_spring_boot.dto.request.product.ProductUpdateDTO;
import com.final_work_spring_boot.dto.response.ProductResponseDTO;
import com.final_work_spring_boot.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.final_work_spring_boot.dto.request.product.ProductCreateDTO;
import com.final_work_spring_boot.service.IGenericService;

@RestController
@RequestMapping("api/products")
public class ProductController {

    @Autowired
    private IProductService service;

    @GetMapping("/")
    public ResponseEntity<List<ProductResponseDTO>> getProductList() {
        List<ProductResponseDTO> productList = service.getRecordsList();

        return productList != null
                ? ResponseEntity.ok(productList)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        ProductResponseDTO product = service.getRecordById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/")
    public ResponseEntity<ProductResponseDTO> saveProduct(@Valid @RequestBody ProductCreateDTO dto) {
        ProductResponseDTO saved = service.saveRecord(dto);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id,
                                                            @Valid @RequestBody ProductUpdateDTO dto) {
        ProductResponseDTO updated = service.updateRecord(id, dto);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable Long id) {
        boolean deleted = service.deleteRecord(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }


}
