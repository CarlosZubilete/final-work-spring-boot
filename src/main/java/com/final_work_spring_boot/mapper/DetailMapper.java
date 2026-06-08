package com.final_work_spring_boot.mapper;

import com.final_work_spring_boot.dto.DetailDTO;
import com.final_work_spring_boot.model.Product;
import com.final_work_spring_boot.model.Sale;
import com.final_work_spring_boot.model.Detail;

public class DetailMapper {

    public static Detail toEntity(DetailDTO dto, Product product, Sale sale) {
        if (dto == null)
            return null;

        return Detail.builder()
                .product(product)
                // unit price may comes in dto, depends the logic.
                .unitPrice(product.getPrice())
                .quantity(dto.getQuantity())
                .sale(sale)
                .build();

    }
}
