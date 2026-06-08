package com.final_work_spring_boot.mapper;

import com.final_work_spring_boot.dto.BrandDTO;
import com.final_work_spring_boot.model.Brand;

public class BrandMapper {

    public static BrandDTO toDTO(Brand brand) {
        if (brand == null)
            return null;

        return BrandDTO.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();
    }

    public static Brand toEntity(BrandDTO dto) {
        if (dto == null)
            return null;

        return Brand.builder()
                .name(dto.getName().toUpperCase().trim())
                .build();
    }

    public static void updateEntity(Brand brand, BrandDTO dto) {
        if (dto == null)
            return;

        if (dto.getName() != null)
            brand.setName(dto.getName().toUpperCase().trim());
    }
}
