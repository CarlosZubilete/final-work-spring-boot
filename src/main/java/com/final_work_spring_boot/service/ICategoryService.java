package com.final_work_spring_boot.service;

import com.final_work_spring_boot.dto.request.category.CategoryCreateDTO;
import com.final_work_spring_boot.dto.request.category.CategoryUpdateDTO;
import com.final_work_spring_boot.dto.response.CategoryResponseDTO;

import java.util.List;

public interface ICategoryService {
    List<CategoryResponseDTO> getRecordsList();

    CategoryResponseDTO getRecordById(Long id);

    CategoryResponseDTO saveRecord(CategoryCreateDTO dto);

    CategoryResponseDTO updateRecord(Long id, CategoryUpdateDTO dto);

    boolean deleteRecord(Long id);
}
