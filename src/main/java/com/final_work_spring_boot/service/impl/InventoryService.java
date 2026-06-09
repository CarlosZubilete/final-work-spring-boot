package com.final_work_spring_boot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.final_work_spring_boot.dto.request.inventory.InventoryCreateDTO;
import com.final_work_spring_boot.exception.BusinessException;
import com.final_work_spring_boot.exception.NotFoundException;
import com.final_work_spring_boot.mapper.InventoryMapper;
import com.final_work_spring_boot.model.Inventory;
import com.final_work_spring_boot.repository.IInventoryRepository;
import com.final_work_spring_boot.service.IGenericService;

@Service
public class InventoryService implements IGenericService<InventoryCreateDTO> {

    @Autowired
    private IInventoryRepository repository;

    @Override
    public List<InventoryCreateDTO> getAll() {
        return repository.findAll().stream()
                .map(InventoryMapper::toDTO).toList();
    }

    @Override
    public InventoryCreateDTO getById(Long id) {
        return repository.findById(id).map(InventoryMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Inventory whit ID: " + id + " NOT FOUND."));
    }

    @Override
    public InventoryCreateDTO save(InventoryCreateDTO dto) {

        String isExistingCodeSKU = dto.getCodeSKU().toUpperCase().trim();

        if (repository.existsByCodeSKU(isExistingCodeSKU))
            throw new BusinessException("Inventory whit CODE SKU: " + isExistingCodeSKU + " ALREADY EXISTS.");

        Inventory inventory = InventoryMapper.toEntity(dto);

        return InventoryMapper.toDTO(repository.save(inventory));
    }

    @Override
    public InventoryCreateDTO update(InventoryCreateDTO dto, Long id) {

        Inventory existingInventory = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Inventory whit ID: " + id + " NOT FOUND."));

        String isExistingCodeSKU = dto.getCodeSKU().toUpperCase().trim();

        if (repository.existsByCodeSKU(isExistingCodeSKU))
            throw new BusinessException("Inventory whit CODE SKU: " + isExistingCodeSKU + " ALREADY EXISTS.");

        InventoryMapper.updateEntity(existingInventory, dto);

        return InventoryMapper.toDTO(repository.save(existingInventory));
    }

    @Override
    public boolean delete(Long id) {
        if (!repository.existsById(id))
            throw new NotFoundException("Inventory whit ID: " + id + " NOT FOUND.");

        repository.deleteById(id);
        return true;
    }

}
