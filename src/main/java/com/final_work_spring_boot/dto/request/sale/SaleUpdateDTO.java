package com.final_work_spring_boot.dto.request.sale;


import com.final_work_spring_boot.dto.DetailDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleUpdateDTO {
    @PastOrPresent(message = "date of sale must be in past or present")
    private LocalDate date;

    private Long idClient;
    private Long idStateSale;

    @Valid
    private List<DetailDTO> details;
}
