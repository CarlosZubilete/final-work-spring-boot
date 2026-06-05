package com.final_work_spring_boot.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sale")
    private Long id;

    @Column(name = "date_sale")
    private LocalDate date;

    // MANY sales belong to ONE CLIENT
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_client") // 'id_client' is the FK 
    private Client client;

    // MANY sales may have the same (ONE) stateSale
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_state_sale") // 'id_state_sale' is the FK
    private StateSale stateSale;

    @Column(name = "total_sale")
    private Double total;

    @OneToMany(mappedBy = "sale")
    private List<SaleDetail> details; 

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
