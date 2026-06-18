package com.final_work_spring_boot.dto.request.client;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientUpdateDTO {
    @Size(min = 3, max = 50, message = "first name must be between 3 and 50 characters")
    private String firstName;

    @Size(min = 3, max = 50, message = "last name must be between 3 and 50 characters")
    private String lastName;

    @Size(min = 8, max = 9, message = "document must 8 characters")
    // Allows only numbers (0-9)
    @Pattern(regexp = "^\\d+$", message = "Field must be a numeric")
    private String document;

    @Size(min = 3, max = 50, message = "email must be between 3 and 50 characters")
    private String email;

    @Size(min = 8, max = 13, message = "phone must be between 8 and 13 characters")
    private String phone;
}
