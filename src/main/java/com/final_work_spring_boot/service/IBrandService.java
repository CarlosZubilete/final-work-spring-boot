package com.final_work_spring_boot.service;

import com.final_work_spring_boot.dto.request.brand.BrandCreateDTO;
import com.final_work_spring_boot.dto.request.brand.BrandUpdateDTO;
import com.final_work_spring_boot.dto.response.BrandResponseDTO;

import java.util.List;

public interface IBrandService {

    List<BrandResponseDTO> getRecordsList();

    BrandResponseDTO getRecordById(Long id);

    BrandResponseDTO saveRecord(BrandCreateDTO dto);

    BrandResponseDTO updateRecord(Long id, BrandUpdateDTO dto);

    boolean deleteRecord(Long id);
}
