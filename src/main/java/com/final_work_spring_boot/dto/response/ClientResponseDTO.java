package com.final_work_spring_boot.dto.response;

import java.time.LocalDateTime;
//import java.util.List;

//import com.final_work_spring_boot.model.Sale;

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
public class ClientResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String document;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

// todo: can I set each sales ?
// private List<Sale> sales;
