package com.final_work_spring_boot.service.impl;

import java.util.List;

import com.final_work_spring_boot.dto.request.product.ProductUpdateDTO;
import com.final_work_spring_boot.dto.response.ProductResponseDTO;
import com.final_work_spring_boot.exception.BadRequestException;
import com.final_work_spring_boot.mapper.InventoryMapper;
import com.final_work_spring_boot.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.final_work_spring_boot.dto.request.product.ProductCreateDTO;
import com.final_work_spring_boot.exception.NotFoundException;
import com.final_work_spring_boot.exception.BusinessException;
import com.final_work_spring_boot.mapper.ProductMapper;
import com.final_work_spring_boot.model.Brand;
import com.final_work_spring_boot.model.Category;
import com.final_work_spring_boot.model.Inventory;
import com.final_work_spring_boot.model.Product;
import com.final_work_spring_boot.repository.IBranRepository;
import com.final_work_spring_boot.repository.ICategoryRepository;
import com.final_work_spring_boot.repository.IInventoryRepository;
import com.final_work_spring_boot.repository.IProductRepository;
import com.final_work_spring_boot.service.IGenericService;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository repository;

    @Autowired
    private IBranRepository brandRepo;

    @Autowired
    private IInventoryRepository inventoryRepo;

    @Autowired
    private ICategoryRepository categoryRepo;

    // todo: get the product list with is_active = false;
    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getRecordsList() {
        return repository.findAllByStatus(true).stream()
                .map(ProductMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO getRecordById(Long id) {
        return repository.findByIdAndStatus(id, true).map(ProductMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Product whit ID: " + id + " NOT FOUND."));
    }

    @Override
    public ProductResponseDTO saveRecord(ProductCreateDTO dto) {

        String existingCodeSKU = dto.getInventory().getCodeSKU().toUpperCase().trim();

        if (inventoryRepo.existsByCodeSKU(existingCodeSKU))
            throw new BusinessException(
                    "The Inventory (SKU) with name " + existingCodeSKU
                            + " is already assigned to another product.");

        Brand existingBrand = brandRepo.findById(dto.getIdBrand())
                .orElseThrow(() -> new NotFoundException(
                        "Product whit ID BRAND: " + dto.getIdBrand() + " NOT FOUND."));

        Category existingCategory = categoryRepo.findById(dto.getIdCategory())
                .orElseThrow(() -> new NotFoundException(
                        "Product whit ID CATEGORY: " + dto.getIdCategory() + " NOT FOUND."));

        Inventory newInventory = InventoryMapper.toEntity(dto.getInventory());

        Product product = ProductMapper.toEntity(dto, newInventory, existingBrand, existingCategory);

        return ProductMapper.toDTO(repository.save(product));
    }

    @Override
    public ProductResponseDTO updateRecord(Long id, ProductUpdateDTO dto) {

        Product existingProduct = repository.findByIdAndStatus(id, true).orElse(null);

        if (existingProduct == null)
            throw new NotFoundException("Product whit ID: " + id + " NOT FOUND.");

        if (dto.getInventory() != null && dto.getInventory().getStock() != null) {
            // work directly with the object in memory
            existingProduct.getInventory().setStock(dto.getInventory().getStock());
        }

        Brand existingBrand = existingProduct.getBrand();

        if (dto.getIdBrand() != null)
            existingBrand = brandRepo.findById(dto.getIdBrand())
                    .orElseThrow(() -> new NotFoundException(
                            "Product whit ID BRAND: " + dto.getIdBrand() + " not found."));

        Category existingCategory = existingProduct.getCategory();
        if (dto.getIdCategory() != null)
            existingCategory = categoryRepo.findById(dto.getIdCategory())
                    .orElseThrow(() -> new NotFoundException(
                            "Product whit ID CATEGORY: " + dto.getIdCategory() + " not found."));

        ProductMapper.updateEntity(existingProduct, dto , existingBrand, existingCategory);

        return ProductMapper.toDTO(repository.save(existingProduct));
    }

    @Override
    public boolean deleteRecord(Long id) {
        if (!repository.existsById(id))
            throw new NotFoundException("Product whit ID: " + id + " NOT FOUND.");

        int result = repository.softDeleteById(id);

        return result == 1; // ? true : false;
    }
}
