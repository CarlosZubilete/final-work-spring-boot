package com.final_work_spring_boot.service.impl;

import java.util.List;

import com.final_work_spring_boot.dto.request.statesale.StateSaleCreateDTO;
import com.final_work_spring_boot.dto.request.statesale.StateSaleUpdateDTO;
import com.final_work_spring_boot.dto.response.StateSaleResponseDTO;
import com.final_work_spring_boot.service.IStateSale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.final_work_spring_boot.exception.BusinessException;
import com.final_work_spring_boot.exception.NotFoundException;
import com.final_work_spring_boot.mapper.StateSaleMapper;
import com.final_work_spring_boot.model.StateSale;
import com.final_work_spring_boot.repository.IStateSaleRepository;


@Service
public class StateSaleService implements IStateSale {

    @Autowired
    private IStateSaleRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<StateSaleResponseDTO> getRecordsList() {
        return repository.findAll().stream()
            .map(StateSaleMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public StateSaleResponseDTO getRecordById(Long id) {
        return repository.findById(id).map(StateSaleMapper::toDTO)
            .orElseThrow(() -> new NotFoundException("State Sale with this id: " + id + " not found."));
    }

    @Override
    public StateSaleResponseDTO saveRecord(StateSaleCreateDTO dto) {
        String isExistingName = dto.getName().toLowerCase().trim();

        if (repository.existsByName(isExistingName))
            throw new BusinessException("State Sale with this name: " + isExistingName + " already exists.");

        StateSale stateSale = StateSaleMapper.toEntity(dto);

        return StateSaleMapper.toDTO(repository.save(stateSale));
    }

    @Override
    public StateSaleResponseDTO updateRecord(Long id, StateSaleUpdateDTO dto) {
        StateSale existingStateSale = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("State Sale with ID: " + id + " not found."));

        String isExistingName = dto.getName().toLowerCase().trim();

        if (repository.existsByName(isExistingName))
            throw new BusinessException("State Sale with this name: " + isExistingName + " already exists.");


        StateSaleMapper.updateEntity(existingStateSale, dto);

        return StateSaleMapper.toDTO(repository.save(existingStateSale));
    }

    @Override
    public boolean deleteRecord(Long id) {
        if (!repository.existsById(id))
            throw new NotFoundException("State Sale with ID: " + id + " not found.");

        repository.deleteById(id);
        return true;
    }

}
