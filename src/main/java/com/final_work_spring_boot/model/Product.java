package com.final_work_spring_boot.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Long id;

    private String name;
    private String description;
    private Double price;

    // ONE product have ONE inventory
    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true)
    @JoinColumn(name = "id_inventory") // 'id_inventory' is the FK
    private Inventory inventory;

    // MANY products have ONE brand
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_brand") // 'id_brand' is the FK
    private Brand brand;

    // MANY products have ONE category
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category") // 'id_category' is the FK
    private Category category;

    // ONE product can be may MANY details
    @OneToMany(mappedBy = "product")
    private List<Detail> details;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}