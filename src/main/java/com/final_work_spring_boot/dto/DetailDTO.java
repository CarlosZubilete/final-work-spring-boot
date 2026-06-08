package com.final_work_spring_boot.dto;

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
    private Long idProduct;
    private Integer quantity;
    private Double unitPrice;
    private Double subTotal;

}
