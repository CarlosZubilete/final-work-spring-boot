package com.final_work_spring_boot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.final_work_spring_boot.dto.StateSaleDTO;
import com.final_work_spring_boot.exception.BusinessException;
import com.final_work_spring_boot.exception.NotFoundException;
import com.final_work_spring_boot.mapper.StateSaleMapper;
import com.final_work_spring_boot.model.StateSale;
import com.final_work_spring_boot.repository.IStateSaleRepository;
import com.final_work_spring_boot.service.IGenericService;

@Service
public class StateSaleService implements IGenericService<StateSaleDTO> {

    @Autowired
    private IStateSaleRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<StateSaleDTO> getAll() {
        return repository.findAll().stream()
                .map(StateSaleMapper::toDTO).toList();
    }

    @Override
    public StateSaleDTO getById(Long id) {
        return repository.findById(id).map(StateSaleMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("State Sale with ID: " + id + " not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public StateSaleDTO save(StateSaleDTO dto) {
        String isExistingName = dto.getName().toLowerCase().trim();

        StateSale isRepeatedSateSale = repository.findByName(isExistingName).orElse(null);

        if (isRepeatedSateSale != null)
            throw new BusinessException("State Sale with name: " + isExistingName + " already exists.");

        StateSale stateSale = StateSaleMapper.toEntity(dto);

        return StateSaleMapper.toDTO(repository.save(stateSale));
    }

    @Override
    public StateSaleDTO update(StateSaleDTO dto, Long id) {
        StateSale existingStateSale = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("State Sale with ID: " + id + " not found."));

        String isExistingName = dto.getName().toLowerCase().trim();

        StateSale isRepeatedSateSale = repository.findByName(isExistingName).orElse(null);

        if (isRepeatedSateSale != null)
            throw new BusinessException("State Sale with name: " + isExistingName + " already exists.");

        StateSaleMapper.updateEntity(existingStateSale, dto);

        return StateSaleMapper.toDTO(repository.save(existingStateSale));
    }

    @Override
    public boolean delete(Long id) {
        if (!repository.existsById(id))
            throw new NotFoundException("State Sale with ID: " + id + " not found.");

        repository.deleteById(id);
        return true;
    }

}
