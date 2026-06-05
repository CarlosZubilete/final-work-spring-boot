package com.final_work_spring_boot.model;


import jakarta.persistence.*;
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
@Entity
@Table(name = "inventories")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventory")
    private Long id;
    @Column(name = "code_sku")
    private String codeSKU;
    private Integer stock;
    // ONE inventory has ONE product
    @OneToOne(mappedBy = "inventory")
    // 'inventory' is the property's name of Product class.
    // mappedBy -> (non-owning) indicates that the other entity is the owner of relationships.
    private Product product;
}