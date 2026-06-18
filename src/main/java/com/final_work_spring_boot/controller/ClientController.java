package com.final_work_spring_boot.controller;

import com.final_work_spring_boot.dto.request.client.ClientCreateDTO;
import com.final_work_spring_boot.dto.request.client.ClientUpdateDTO;
import com.final_work_spring_boot.dto.response.ClientResponseDTO;
import com.final_work_spring_boot.service.IClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private IClientService service;

    @GetMapping("/")
    public ResponseEntity<List<ClientResponseDTO>> getClientList() {
        List<ClientResponseDTO> clientList = service.getRecordsList();
        return clientList != null
            ? ResponseEntity.ok(clientList)
            : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClientByID(@PathVariable Long id) {
        ClientResponseDTO client = service.getRecordById(id);
        return ResponseEntity.ok(client);
    }

    @PostMapping("/")
    public ResponseEntity<ClientResponseDTO> createClient(@Valid @RequestBody ClientCreateDTO dto) {
        ClientResponseDTO created = service.saveRecord(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> updateClient(@PathVariable Long id,
                                                          @Valid @RequestBody ClientUpdateDTO dto) {
        ClientResponseDTO updated = service.updateRecord(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteClient(@PathVariable Long id) {
        boolean deleted = service.deleteRecord(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
