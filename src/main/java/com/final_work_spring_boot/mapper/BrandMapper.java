package com.final_work_spring_boot.mapper;

import com.final_work_spring_boot.dto.request.brand.BrandCreateDTO;
import com.final_work_spring_boot.dto.request.brand.BrandUpdateDTO;
import com.final_work_spring_boot.dto.response.BrandResponseDTO;
import com.final_work_spring_boot.model.Brand;

public class BrandMapper {

    public static BrandResponseDTO toDTO(Brand brand) {
        if (brand == null)
            return null;

        return BrandResponseDTO.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();
    }

    public static Brand toEntity(BrandCreateDTO dto) {
        if (dto == null)
            return null;

        return Brand.builder()
                .name(dto.getName().toUpperCase().trim())
                .build();
    }

    public static void updateEntity(Brand brand, BrandUpdateDTO dto) {
        if (dto == null)
            return;

        if (dto.getName() != null)
            brand.setName(dto.getName().toUpperCase().trim());
    }
}
