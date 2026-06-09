package com.final_work_spring_boot.dto.request.product;

import com.final_work_spring_boot.dto.request.inventory.InventoryCreateDTO;
import com.final_work_spring_boot.dto.request.inventory.InventoryUpdateDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductUpdateDTO {
    @Size(min = 3, max = 50, message = "name must be between 3 and 50 characters")
    private String name;

    @Size(min = 3, max = 150, message = "description must be between 3 and 150 characters")
    private String description;

    @Positive(message = "price must be positive")
    private Double price;

    @Valid // Enforce validation on nested objects or collections
    private InventoryUpdateDTO inventory;

    private Long idBrand;

    private Long idCategory;

}
