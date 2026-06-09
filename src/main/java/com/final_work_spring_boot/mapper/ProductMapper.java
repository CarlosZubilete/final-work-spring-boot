package com.final_work_spring_boot.mapper;

import com.final_work_spring_boot.dto.request.product.ProductCreateDTO;
import com.final_work_spring_boot.dto.request.product.ProductUpdateDTO;
import com.final_work_spring_boot.dto.response.ProductResponseDTO;
import com.final_work_spring_boot.model.Brand;
import com.final_work_spring_boot.model.Category;
import com.final_work_spring_boot.model.Inventory;
import com.final_work_spring_boot.model.Product;

public class ProductMapper {

    public static ProductResponseDTO toDTO(Product product) {
        if (product == null)
            return null;

        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .codeSKU(product.getInventory().getCodeSKU())
                .stock(product.getInventory().getStock())
                .brand(product.getBrand().getName())
                .category(product.getCategory().getName())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public static Product toEntity(ProductCreateDTO dto, Inventory inventory, Brand brand, Category category) {
        if (dto == null)
            return null;

        return Product.builder()
                .name(dto.getName().toUpperCase().trim())
                .description(dto.getDescription().toLowerCase().trim())
                .price(dto.getPrice())
                .inventory(inventory)
                .brand(brand)
                .category(category)
                .isActive(true)
                .build();
    }

    public static void updateEntity(Product product, ProductUpdateDTO dto, Brand brand,
                                    Category category) {

        if (dto == null)
            return;

        if (dto.getName() != null)
            product.setName(dto.getName().toUpperCase().trim());

        if (dto.getDescription() != null)
            product.setDescription(dto.getDescription().toLowerCase().trim());

        if (dto.getPrice() != null)
            product.setPrice(dto.getPrice());

        if (dto.getIdBrand() != null)
            product.setBrand(brand);

        if (dto.getIdCategory() != null)
            product.setCategory(category);
    }

}
