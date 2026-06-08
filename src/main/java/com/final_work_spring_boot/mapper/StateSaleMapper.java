package com.final_work_spring_boot.mapper;

import com.final_work_spring_boot.dto.StateSaleDTO;
import com.final_work_spring_boot.model.StateSale;

public class StateSaleMapper {

    public static StateSaleDTO toDTO(StateSale stateSale) {
        if (stateSale == null)
            return null;

        return StateSaleDTO.builder()
                .id(stateSale.getId())
                .name(stateSale.getName())
                .build();

    }

    public static StateSale toEntity(StateSaleDTO dto) {
        if (dto == null)
            return null;

        return StateSale.builder()
                .name(dto.getName().toLowerCase().trim())
                .build();
    }

    public static void updateEntity(StateSale stateSale, StateSaleDTO dto) {
        if (dto == null)
            return;

        if (dto.getName() != null)
            stateSale.setName(dto.getName().toLowerCase().trim());
    }
}
