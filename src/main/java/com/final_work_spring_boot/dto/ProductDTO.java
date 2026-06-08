package com.final_work_spring_boot.dto;

import java.time.LocalDateTime;

import com.final_work_spring_boot.model.Inventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    // private Long idInventory;
    private Inventory inventory;
    private Long idBrand;
    private Long idCategory;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;
}
