package com.final_work_spring_boot.service.impl;

import java.util.List;

import com.final_work_spring_boot.dto.request.category.CategoryUpdateDTO;
import com.final_work_spring_boot.dto.response.CategoryResponseDTO;
import com.final_work_spring_boot.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.final_work_spring_boot.dto.request.category.CategoryCreateDTO;
import com.final_work_spring_boot.exception.BusinessException;
import com.final_work_spring_boot.exception.NotFoundException;
import com.final_work_spring_boot.mapper.CategoryMapper;
import com.final_work_spring_boot.model.Category;
import com.final_work_spring_boot.repository.ICategoryRepository;


@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private ICategoryRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> getRecordsList() {
        return repository.findAll().stream()
                .map(CategoryMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDTO getRecordById(Long id) {
        return repository.findById(id).map(CategoryMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Category whit this id: " + id + " not found."));
    }

    @Override
    public CategoryResponseDTO saveRecord(CategoryCreateDTO dto) {

        String isExistingName = dto.getName().toUpperCase().trim();

        if (repository.existsByName(isExistingName))
            throw new BusinessException("Category whit this name: " + isExistingName + " already exists.");

        Category category = CategoryMapper.toEntity(dto);

        return CategoryMapper.toDTO(repository.save(category));
    }

    @Override
    public CategoryResponseDTO updateRecord(Long id, CategoryUpdateDTO dto) {

        Category existingCategory = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category whit ID: " + id + " NOT FOUND"));

        if (dto.getName() != null) {
            String newName = dto.getName().toUpperCase().trim();
            if (repository.existsByName(newName))
                throw new BusinessException("Category whit this name: " + newName + " already exists.");
        }

        CategoryMapper.updateEntity(existingCategory, dto);

        return CategoryMapper.toDTO(repository.save(existingCategory));
    }

    @Override
    public boolean deleteRecord(Long id) {
        if (!repository.existsById(id))
            throw new NotFoundException("Category whit ID: " + id + " NOT FOUND");

        repository.deleteById(id);
        return true;
    }
}
