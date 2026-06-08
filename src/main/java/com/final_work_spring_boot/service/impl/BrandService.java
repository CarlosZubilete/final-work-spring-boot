package com.final_work_spring_boot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.final_work_spring_boot.dto.BrandDTO;
import com.final_work_spring_boot.exception.BusinessException;
import com.final_work_spring_boot.exception.NotFoundException;
import com.final_work_spring_boot.mapper.BrandMapper;
import com.final_work_spring_boot.model.Brand;
import com.final_work_spring_boot.repository.IBranRepository;
import com.final_work_spring_boot.service.IGenericService;

@Service
public class BrandService implements IGenericService<BrandDTO> {

    @Autowired
    private IBranRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<BrandDTO> getAll() {
        return repository.findAll().stream()
                .map(BrandMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BrandDTO getById(Long id) {
        return repository.findById(id).map(BrandMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Brand with ID: " + id + "NOT FOUND."));
    }

    @Override
    public BrandDTO save(BrandDTO dto) {
        String isExistingName = dto.getName().toUpperCase().trim();

        Brand isRepeatedBrand = repository.findByName(isExistingName).orElse(null);

        if (isRepeatedBrand != null)
            throw new BusinessException("Brand with name: " + isExistingName + " ALREADY EXISTS.");

        Brand brand = BrandMapper.toEntity(dto);

        return BrandMapper.toDTO(repository.save(brand));

    }

    @Override
    public BrandDTO update(BrandDTO dto, Long id) {

        Brand existingBrand = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Brand with ID: " + id + "NOT FOUND."));

        String isExistingName = dto.getName().toUpperCase().trim();

        Brand isRepeatedBrand = repository.findByName(isExistingName).orElse(null);

        if (isRepeatedBrand != null)
            throw new BusinessException("Brand with name: " + isExistingName + " ALREADY EXISTS.");

        BrandMapper.updateEntity(existingBrand, dto);

        return BrandMapper.toDTO(repository.save(existingBrand));
    }

    @Override
    public boolean delete(Long id) {
        if(!repository.existsById(id))
            throw new NotFoundException("Brand with ID: " + id + "NOT FOUND.");

        repository.deleteById(id);
        return true;
    }

}
