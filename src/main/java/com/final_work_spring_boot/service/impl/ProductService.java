package com.final_work_spring_boot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.final_work_spring_boot.dto.ProductDTO;
import com.final_work_spring_boot.exception.NotFoundException;
import com.final_work_spring_boot.exception.BadRequestException;
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
public class ProductService implements IGenericService<ProductDTO> {

    @Autowired
    private IProductRepository repository;

    @Autowired
    private IBranRepository brandRepo;

    @Autowired
    private IInventoryRepository inventoryRepo;

    @Autowired
    private ICategoryRepository categoryRepo;

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getAll() {
        return repository.findAll().stream()
                .map(ProductMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getById(Long id) {
        return repository.findById(id).map(ProductMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Product whit ID: " + id + " NOT FOUND."));
    }

    @Override
    public ProductDTO save(ProductDTO dto) {

        // todo: SYNTACTIC VALIDATION: it will be replace with @valid
        if (dto == null)
            throw new BadRequestException("PRODUCT cannot be null");

        if (dto.getInventory() == null)
            throw new BadRequestException("ID INVENTORY cannot be null");

        if (dto.getIdBrand() == null)
            throw new BadRequestException("ID BRAND cannot be null");

        if (dto.getIdCategory() == null)
            throw new BadRequestException("ID CATEGORY cannot be null");

        // Make sure the INVENTORY ID it's not assigned to another products.
        if (repository.existsByInventoryId(dto.getInventory().getId()))
            throw new BusinessException(
                    "The Inventory (SKU) with ID " + dto.getInventory().getId()
                            + " is already assigned to another product.");

        // FIND : Inventory, Brand , Category
        Inventory existingInventory = inventoryRepo.findById(dto.getInventory().getId())
                .orElseThrow(() -> new NotFoundException(
                        "Product whit ID INVENTORY: " + dto.getInventory().getId() + " NOT FOUND."));

        Brand existingBrand = brandRepo.findById(dto.getIdBrand())
                .orElseThrow(() -> new NotFoundException(
                        "Product whit ID BRAND: " + dto.getIdBrand() + " NOT FOUND."));

        Category existingCategory = categoryRepo.findById(dto.getIdCategory())
                .orElseThrow(() -> new NotFoundException(
                        "Product whit ID CATEGORY: " + dto.getIdCategory() + " NOT FOUND."));

        // There's no validation because MANY PRODUCTS CAN HAVE THE SAME NAME.
        Product product = ProductMapper.toEntity(dto, existingInventory, existingBrand, existingCategory);

        return ProductMapper.toDTO(repository.save(product));
    }

    @Override
    public ProductDTO update(ProductDTO dto, Long id) {

        Product existingProduct = repository.findById(id).orElse(null);

        if (existingProduct == null)
            throw new NotFoundException("Product whit ID: " + id + " NOT FOUND.");

        // if the dto has a new relations , we looked it.

        Inventory inventory = null;

        if (dto.getInventory().getId() != null && existingProduct.getInventory().getId().equals(dto.getId())) {
            // Make sure the INVENTORY ID it's not assigned to another products.
            if (repository.existsByInventoryId(dto.getInventory().getId()))
                throw new BusinessException(
                        "The Inventory (SKU) with ID " + dto.getInventory().getId()
                                + " is already assigned to another product.");

            inventory = inventoryRepo.findById(dto.getInventory().getId())
                    .orElseThrow(() -> new NotFoundException(
                            "Product whit ID INVENTORY: " + dto.getInventory().getId() + " NOT FOUND."));
        }

        Brand brand = null;
        if (dto.getIdBrand() != null)
            brand = brandRepo.findById(dto.getIdBrand())
                    .orElseThrow(() -> new NotFoundException(
                            "Product whit ID BRAND: " + dto.getIdBrand() + " NOT FOUND."));

        Category category = null;
        if (dto.getIdCategory() != null)
            category = categoryRepo.findById(dto.getIdCategory())
                    .orElseThrow(() -> new NotFoundException(
                            "Product whit ID CATEGORY: " + dto.getIdCategory() + " NOT FOUND."));

        ProductMapper.updateEntity(existingProduct, dto, inventory, brand, category);

        return ProductMapper.toDTO(repository.save(existingProduct));
    }

    @Override
    public boolean delete(Long id) {
        if (!repository.existsById(id))
            throw new NotFoundException("Product whit ID: " + id + " NOT FOUND.");

        repository.logicDeleteById(id);
        return true;
    }

}