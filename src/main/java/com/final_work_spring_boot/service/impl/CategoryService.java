package com.final_work_spring_boot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.final_work_spring_boot.dto.CategoryDTO;
import com.final_work_spring_boot.exception.BusinessException;
import com.final_work_spring_boot.exception.NotFoundException;
import com.final_work_spring_boot.mapper.CategoryMapper;
import com.final_work_spring_boot.model.Category;
import com.final_work_spring_boot.repository.ICategoryRepository;
import com.final_work_spring_boot.service.IGenericService;

@Service
public class CategoryService implements IGenericService<CategoryDTO> {

    @Autowired
    private ICategoryRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAll() {
        return repository.findAll().stream()
                .map(CategoryMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO getById(Long id) {
        return repository.findById(id).map(CategoryMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Category whit ID: " + id + " NOT FOUND"));
    }

    @Override
    public CategoryDTO save(CategoryDTO dto) {

        String isExistingName = dto.getName().toUpperCase().trim();

        Category isRepeatedCategory = repository.findByName(isExistingName).orElse(null);

        if (isRepeatedCategory != null)
            throw new BusinessException("Category whit name: " + isExistingName + " ALREADY EXISTS.");

        Category category = CategoryMapper.toEntity(dto);

        return CategoryMapper.toDTO(repository.save(category));
    }

    @Override
    public CategoryDTO update(CategoryDTO dto, Long id) {

        Category existingCategory = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category whit ID: " + id + " NOT FOUND"));

        String isExistingName = dto.getName().toUpperCase().trim();

        Category isRepeatedCategory = repository.findByName(isExistingName).orElse(null);

        if (isRepeatedCategory != null)
            throw new BusinessException("Category whit name: " + isExistingName + " ALREADY EXISTS.");

        CategoryMapper.updateEntity(existingCategory, dto);

        return CategoryMapper.toDTO(repository.save(existingCategory));
    }

    @Override
    public boolean delete(Long id) {
        if (!repository.existsById(id))
            throw new NotFoundException("Category whit ID: " + id + " NOT FOUND");

        repository.deleteById(id);
        return true;
    }
}
