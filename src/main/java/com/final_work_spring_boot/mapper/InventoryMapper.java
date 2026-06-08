package com.final_work_spring_boot.mapper;

import com.final_work_spring_boot.dto.InventoryDTO;
import com.final_work_spring_boot.model.Inventory;

public class InventoryMapper {

    public static InventoryDTO toDTO(Inventory inventory) {
        if (inventory == null)
            return null;

        return InventoryDTO.builder()
                .id(inventory.getId())
                .codeSKU(inventory.getCodeSKU())
                .stock(inventory.getStock())
                .build();
    }

    public static Inventory toEntity(InventoryDTO dto) {
        if (dto == null)
            return null;

        return Inventory.builder()
                .codeSKU(dto.getCodeSKU().toUpperCase().trim())
                .stock(dto.getStock())
                .build();
    }

    public static void updateEntity(Inventory inventory, InventoryDTO dto) {
        if (dto == null)
            return;

        if (dto.getCodeSKU() != null)
            inventory.setCodeSKU(dto.getCodeSKU().toUpperCase().trim());

        if (dto.getStock() != null)
            inventory.setStock(dto.getStock());
    }
}
