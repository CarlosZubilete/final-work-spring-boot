package com.final_work_spring_boot.mapper;

import com.final_work_spring_boot.dto.request.category.CategoryCreateDTO;
import com.final_work_spring_boot.dto.request.category.CategoryUpdateDTO;
import com.final_work_spring_boot.dto.response.CategoryResponseDTO;
import com.final_work_spring_boot.model.Category;

public class CategoryMapper {

    public static CategoryResponseDTO toDTO(Category category) {
        if (category == null)
            return null;

        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static Category toEntity(CategoryCreateDTO dto) {
        if (dto == null)
            return null;

        return Category.builder()
                .name(dto.getName().toUpperCase().trim())
                .build();
    }

    public static void updateEntity(Category category, CategoryUpdateDTO dto) {
        if (dto == null)
            return;

        if (dto.getName() != null)
            category.setName(dto.getName().toUpperCase().trim());

    }
}
