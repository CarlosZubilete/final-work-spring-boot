package com.final_work_spring_boot.service.impl;

import java.util.List;

import com.final_work_spring_boot.dto.request.brand.BrandUpdateDTO;
import com.final_work_spring_boot.dto.response.BrandResponseDTO;
import com.final_work_spring_boot.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.final_work_spring_boot.dto.request.brand.BrandCreateDTO;
import com.final_work_spring_boot.exception.BusinessException;
import com.final_work_spring_boot.exception.NotFoundException;
import com.final_work_spring_boot.mapper.BrandMapper;
import com.final_work_spring_boot.model.Brand;
import com.final_work_spring_boot.repository.IBranRepository;
import com.final_work_spring_boot.service.IGenericService;

@Service
public class BrandService implements IBrandService {

    @Autowired
    private IBranRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<BrandResponseDTO> getRecordsList() {
        return repository.findAll().stream()
                .map(BrandMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BrandResponseDTO getRecordById(Long id) {
        return repository.findById(id).map(BrandMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Brand with this id: " + id + " not found."));
    }

    @Override
    public BrandResponseDTO saveRecord(BrandCreateDTO dto) {

        String existingName = dto.getName().toUpperCase().trim();
        if (repository.existsByName(existingName))
            throw new BusinessException("Brand with this name: " + existingName + " already exits.");

        Brand brand = BrandMapper.toEntity(dto);

        return BrandMapper.toDTO(repository.save(brand));
    }

    @Override
    public BrandResponseDTO updateRecord(Long id, BrandUpdateDTO dto) {

        Brand existingBrand = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Brand with this id: " + id + " not found."));


        if (dto.getName() != null) {
            String newName = dto.getName().toUpperCase().trim();

            if (repository.existsByName(newName))
                throw new BusinessException("Brand with this name: " + newName + " already exists.");
        }

        BrandMapper.updateEntity(existingBrand, dto);

        return BrandMapper.toDTO(repository.save(existingBrand));
    }

    @Override
    public boolean deleteRecord(Long id) {
        if (!repository.existsById(id))
            throw new NotFoundException("Brand with this id: " + id + " not found.");

        repository.deleteById(id);
        return true;
    }

}
