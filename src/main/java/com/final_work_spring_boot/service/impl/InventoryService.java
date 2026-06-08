package com.final_work_spring_boot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.final_work_spring_boot.dto.InventoryDTO;
import com.final_work_spring_boot.exception.BusinessException;
import com.final_work_spring_boot.exception.NotFoundException;
import com.final_work_spring_boot.mapper.InventoryMapper;
import com.final_work_spring_boot.model.Inventory;
import com.final_work_spring_boot.repository.IInventoryRepository;
import com.final_work_spring_boot.service.IGenericService;

@Service
public class InventoryService implements IGenericService<InventoryDTO> {

    @Autowired
    private IInventoryRepository repository;

    @Override
    public List<InventoryDTO> getAll() {
        return repository.findAll().stream()
                .map(InventoryMapper::toDTO).toList();
    }

    @Override
    public InventoryDTO getById(Long id) {
        return repository.findById(id).map(InventoryMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Inventory whit ID: " + id + " NOT FOUND."));
    }

    @Override
    public InventoryDTO save(InventoryDTO dto) {

        String isExistingSKU = dto.getCodeSKU().toUpperCase().trim();

        Inventory isRepeatedInventory = repository.findByCodeSKU(isExistingSKU).orElse(null);

        if (isRepeatedInventory != null)
            throw new BusinessException("Inventory whit CODE SKU: " + isExistingSKU + " ALREADY EXISTS.");

        Inventory inventory = InventoryMapper.toEntity(dto);

        return InventoryMapper.toDTO(repository.save(inventory));
    }

    @Override
    public InventoryDTO update(InventoryDTO dto, Long id) {

        Inventory existingInventory = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Inventory whit ID: " + id + " NOT FOUND."));

        String isExistingSKU = dto.getCodeSKU().toUpperCase().trim();

        Inventory isRepeatedInventory = repository.findByCodeSKU(isExistingSKU).orElse(null);

        if (isRepeatedInventory != null)
            throw new BusinessException("Inventory whit CODE SKU: " + isExistingSKU + " ALREADY EXISTS.");

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
