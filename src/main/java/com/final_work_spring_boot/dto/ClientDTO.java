package com.final_work_spring_boot.dto;

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
public class ClientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String document;
    private String email;
    private String phone;
    // todo: can I set each sales ?
    // private List<Sale> sales;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;
}
