package com.final_work_spring_boot.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleDTO {
    private Long id;
    private LocalDate date;
    private Long idClient;
    private Long idStateSale;
    // Here we have the products by Sale.
    private List<DetailDTO> details;
    private Double total;
}
