package com.final_work_spring_boot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "details")
// watchout: Sometimes <<details>> is a reserved word
public class Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detail")
    private Long id;

    // MANY sale_details have ONE sale
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sale") // 'id_sale' is the FK
    private Sale sale;

    // MANY sale_details have ONE product
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product") // 'id_product' is the FK
    private Product product;

    @Column(name = "quantity_product")
    private Integer quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    // Can I add a subtotal Column ? 
    // Not , because it's a calculated filed
}
