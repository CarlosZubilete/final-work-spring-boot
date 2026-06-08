package com.final_work_spring_boot.mapper;

import com.final_work_spring_boot.dto.CategoryDTO;
import com.final_work_spring_boot.model.Category;

public class CategoryMapper {

    public static CategoryDTO toDTO(Category category) {
        if (category == null)
            return null;

        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static Category toEntity(CategoryDTO dto) {
        if (dto == null)
            return null;

        return Category.builder()
                .name(dto.getName().toUpperCase().trim())
                .build();
    }

    public static void updateEntity(Category category, CategoryDTO dto) {
        if (dto == null)
            return;

        if (dto.getName() != null)
            category.setName(dto.getName().toUpperCase().trim());

    }
}
