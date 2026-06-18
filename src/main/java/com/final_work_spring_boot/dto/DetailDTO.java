package com.final_work_spring_boot.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class DetailDTO {
    private Long id;

    @NotNull(message = "id of product is required")
    private Long idProduct;

    @NotNull(message = "quantity is required")
    @Positive(message = "quantity must be positive")
    private Integer quantity;

    @Positive(message = "unit price must be greater than zero")
    // the price of the product is optional, as it can be calculated based on the
    // product information in the database.
    // If provided, it must be greater than zero.
    private Double unitPrice;

    @Positive(message = "sub total must be greater than zero")
    // the sub-total price is optional, as it can be calculated based on the
    // quantity and price of the product.
    // If provided, it must be greater than zero.
    private Double subTotal;
}
