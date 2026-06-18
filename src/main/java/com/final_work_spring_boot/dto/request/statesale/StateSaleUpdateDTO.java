package com.final_work_spring_boot.dto.request.statesale;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StateSaleUpdateDTO {
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;
}
