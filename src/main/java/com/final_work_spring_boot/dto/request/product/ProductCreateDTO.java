package com.final_work_spring_boot.dto.request.product;

import java.time.LocalDateTime;

import com.final_work_spring_boot.dto.request.inventory.InventoryCreateDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
public class ProductCreateDTO {
    private Long id;

    @NotBlank(message = "name cannot be blank")
    @Size(min = 3, max = 50, message = "name must be between 3 and 50 characters")
    private String name;

    @NotBlank(message = "description cannot be blank")
    @Size(min = 3, max = 150, message = "description must be between 3 and 150 characters")
    private String description;

    @NotNull(message = "price is required")
    @Positive(message = "price must be positive")
    private Double price;

    @Valid // Enforce validation on nested objects or collections
    @NotNull(message = "inventory is required")
    private InventoryCreateDTO inventory;

    @NotNull(message = "idBrand  is required")
    private Long idBrand;

    @NotNull(message = "idCategory is required")
    private Long idCategory;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;
}
