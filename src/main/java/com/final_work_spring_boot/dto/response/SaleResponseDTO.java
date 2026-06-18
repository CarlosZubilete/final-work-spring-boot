package com.final_work_spring_boot.dto.response;

import com.final_work_spring_boot.dto.DetailDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleResponseDTO {
    private Long id;
    private LocalDate date;
    private String clientName;
    private String stateSale;
    private List<DetailDTO> details;
    private Double total;
}
