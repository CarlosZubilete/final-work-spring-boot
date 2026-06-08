package com.final_work_spring_boot.mapper;

import com.final_work_spring_boot.dto.ProductDTO;
import com.final_work_spring_boot.model.Brand;
import com.final_work_spring_boot.model.Category;
import com.final_work_spring_boot.model.Inventory;
import com.final_work_spring_boot.model.Product;

public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        if (product == null)
            return null;

        return ProductDTO.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .inventory(product.getInventory())
                .idBrand(product.getBrand().getId())
                .idCategory(product.getCategory().getId())
                .build();
    }

    public static Product toEntity(ProductDTO dto, Inventory inventory, Brand brand, Category category) {
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

    public static void updateEntity(Product product, ProductDTO dto, Inventory inventory, Brand brand,
            Category category) {

        if (dto == null)
            return;

        if (dto.getName() != null)
            product.setName(dto.getName().toUpperCase().trim());

        if (dto.getDescription() != null)
            product.setDescription(dto.getDescription().toLowerCase().trim());

        if (dto.getPrice() != null)
            product.setPrice(dto.getPrice());

        if (dto.getInventory() != null)
            product.setInventory(inventory);

        if (dto.getIdBrand() != null)
            product.setBrand(brand);

        if (dto.getIdCategory() != null)
            product.setCategory(category);

        // Do I have a set the local date to updated field ?
    }

}
