package com.final_work_spring_boot.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String codeSKU;
    private Integer stock;
    private String category;
    private String brand;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
