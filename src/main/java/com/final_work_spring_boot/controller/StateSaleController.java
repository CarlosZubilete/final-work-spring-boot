package com.final_work_spring_boot.controller;

import com.final_work_spring_boot.dto.request.statesale.StateSaleCreateDTO;
import com.final_work_spring_boot.dto.request.statesale.StateSaleUpdateDTO;
import com.final_work_spring_boot.dto.response.StateSaleResponseDTO;
import com.final_work_spring_boot.service.IStateSale;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/state-sales")
public class StateSaleController {

    @Autowired
    private IStateSale service;

    @GetMapping("/")
    public ResponseEntity<List<StateSaleResponseDTO>> getStateSaleList() {
        List<StateSaleResponseDTO> stateSaleList = service.getRecordsList();
        return stateSaleList != null
            ? ResponseEntity.ok(stateSaleList)
            : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StateSaleResponseDTO> getStateSaleById(@Valid @PathVariable Long id) {
        StateSaleResponseDTO stateSale = service.getRecordById(id);
        return ResponseEntity.ok(stateSale);
    }

    @PostMapping("/")
    public ResponseEntity<StateSaleResponseDTO> createStateSale(@RequestBody StateSaleCreateDTO dto) {
        StateSaleResponseDTO created = service.saveRecord(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StateSaleResponseDTO> updateStateSale(@PathVariable Long id,
                                                                @Valid @RequestBody StateSaleUpdateDTO dto) {
        StateSaleResponseDTO updated = service.updateRecord(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteStateSale(@PathVariable Long id) {
        boolean deleted = service.deleteRecord(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
