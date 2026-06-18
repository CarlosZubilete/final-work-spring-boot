package com.final_work_spring_boot.dto.request.sale;

import com.final_work_spring_boot.dto.DetailDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleCreateDTO {
    @NotNull(message = "date is required")
    @PastOrPresent(message = "date of sale must be in past or present")
    private LocalDate date;

    @NotNull(message = "id of client is required")
    private Long idClient;

    @NotNull(message = "id of state sale is required")
    private Long idStateSale;

    @Valid
    @NotNull(message = "list of details is required")
    private List<DetailDTO> details;
}
