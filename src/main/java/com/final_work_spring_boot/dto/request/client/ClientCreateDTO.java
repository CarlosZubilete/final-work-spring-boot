package com.final_work_spring_boot.dto.request.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientCreateDTO {
    @NotBlank(message = "first name cannot be blank")
    @Size(min = 3, max = 50, message = "first name must be between 3 and 50 characters")
    private String firstName;

    @NotBlank(message = "last name cannot be blank")
    @Size(min = 3, max = 50, message = "last name must be between 3 and 50 characters")
    private String lastName;

    @NotBlank(message = "document cannot be blank")
    @Size(min = 8, max = 9, message = "document must 8 characters")
    // Allows only numbers (0-9)
    @Pattern(regexp = "^\\d+$", message = "Field must be a numeric")
    private String document;

    @NotBlank(message = "email cannot be blank")
    @Size(min = 3, max = 50, message = "email must be between 3 and 50 characters")
    private String email;

    @NotBlank(message = "phone cannot be blank")
    @Size(min = 8, max = 13, message = "phone must be between 8 and 13 characters")
    private String phone;
}
