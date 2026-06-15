package com.final_work_spring_boot.dto.request.category;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryUpdateDTO {
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;
}
