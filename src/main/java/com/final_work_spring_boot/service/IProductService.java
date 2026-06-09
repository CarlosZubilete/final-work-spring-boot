package com.final_work_spring_boot.service;

import com.final_work_spring_boot.dto.request.product.ProductCreateDTO;
import com.final_work_spring_boot.dto.request.product.ProductUpdateDTO;
import com.final_work_spring_boot.dto.response.ProductResponseDTO;

import java.util.List;

public interface IProductService {
    List<ProductResponseDTO> getRecordsList();

    ProductResponseDTO getRecordById(Long id);

    ProductResponseDTO saveRecord(ProductCreateDTO dto);

    ProductResponseDTO updateRecord(Long id, ProductUpdateDTO dto);

    boolean deleteRecord(Long id);
}
