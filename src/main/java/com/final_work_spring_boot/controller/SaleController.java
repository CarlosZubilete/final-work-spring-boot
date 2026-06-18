package com.final_work_spring_boot.controller;

import com.final_work_spring_boot.dto.request.sale.SaleCreateDTO;
import com.final_work_spring_boot.dto.request.sale.SaleUpdateDTO;
import com.final_work_spring_boot.dto.response.SaleResponseDTO;
import com.final_work_spring_boot.service.ISaleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/sales")
public class SaleController {

    @Autowired
    private ISaleService service;


    @GetMapping("/")
    public ResponseEntity<List<SaleResponseDTO>> getSaleList() {
        List<SaleResponseDTO> saleList = service.getRecordsList();

        return saleList != null
            ? ResponseEntity.ok(saleList)
            : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> getSaleById(@PathVariable Long id) {
        SaleResponseDTO sale = service.getRecordById(id);
        return ResponseEntity.ok(sale);
    }

    @PostMapping("/")
    public ResponseEntity<SaleResponseDTO> createSale(@Valid @RequestBody SaleCreateDTO dto) {
        SaleResponseDTO created = service.saveRecord(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);

    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> updateSale(@PathVariable Long id,
                                                      @Valid @RequestBody SaleUpdateDTO dto) {
        SaleResponseDTO updated = service.updateRecord(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteSale(@PathVariable Long id) {
        boolean deleted = service.deleteRecord(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
















