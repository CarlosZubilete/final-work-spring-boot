package com.final_work_spring_boot.dto.request.inventory;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryUpdateDTO {
    @PositiveOrZero(message = "stock must be positive or zero")
    private Integer stock;
}
