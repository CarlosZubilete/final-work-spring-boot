package com.final_work_spring_boot.mapper;

import com.final_work_spring_boot.dto.request.inventory.InventoryCreateDTO;
import com.final_work_spring_boot.model.Inventory;

public class InventoryMapper {

    public static InventoryCreateDTO toDTO(Inventory inventory) {
        if (inventory == null)
            return null;

        return InventoryCreateDTO.builder()
                .id(inventory.getId())
                .codeSKU(inventory.getCodeSKU())
                .stock(inventory.getStock())
                .build();
    }

    public static Inventory toEntity(InventoryCreateDTO dto) {
        if (dto == null)
            return null;

        return Inventory.builder()
                .codeSKU(dto.getCodeSKU().toUpperCase().trim())
                .stock(dto.getStock())
                .build();
    }

    public static void updateEntity(Inventory inventory, InventoryCreateDTO dto) {
        if (dto == null)
            return;

        if (dto.getCodeSKU() != null)
            inventory.setCodeSKU(dto.getCodeSKU().toUpperCase().trim());

        if (dto.getStock() != null)
            inventory.setStock(dto.getStock());
    }
}
