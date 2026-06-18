package com.final_work_spring_boot.mapper;

import com.final_work_spring_boot.dto.request.statesale.StateSaleCreateDTO;
import com.final_work_spring_boot.dto.request.statesale.StateSaleUpdateDTO;
import com.final_work_spring_boot.dto.response.StateSaleResponseDTO;
import com.final_work_spring_boot.model.StateSale;

public class StateSaleMapper {

    public static StateSaleResponseDTO toDTO(StateSale stateSale) {
        if (stateSale == null)
            return null;

        return StateSaleResponseDTO.builder()
            .id(stateSale.getId())
            .name(stateSale.getName())
            .build();

    }

    public static StateSale toEntity(StateSaleCreateDTO dto) {
        if (dto == null)
            return null;

        return StateSale.builder()
            .name(dto.getName().toLowerCase().trim())
            .build();
    }

    public static void updateEntity(StateSale stateSale, StateSaleUpdateDTO dto) {
        if (dto == null)
            return;

        if (dto.getName() != null)
            stateSale.setName(dto.getName().toLowerCase().trim());
    }
}
