package com.final_work_spring_boot.dto.request.inventory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
public class InventoryCreateDTO {
    private Long id;

    @NotBlank(message = "codeSKU cannot be empty")
    // todo:
    @Size(max = 9, min = 8, message = "codeSKU must be 8 characters")
    private String codeSKU;

    @NotNull(message = "stock cannot be null")
    @PositiveOrZero(message = "stock must be positive or zero")
    private Integer stock;
}
