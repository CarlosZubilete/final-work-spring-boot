package com.final_work_spring_boot.service;

import com.final_work_spring_boot.dto.request.sale.SaleCreateDTO;
import com.final_work_spring_boot.dto.request.sale.SaleUpdateDTO;
import com.final_work_spring_boot.dto.response.SaleResponseDTO;

import java.util.List;

public interface ISaleService {
    List<SaleResponseDTO> getRecordsList();

    SaleResponseDTO getRecordById(Long id);

    SaleResponseDTO saveRecord(SaleCreateDTO dto);

    SaleResponseDTO updateRecord(Long id, SaleUpdateDTO dto);

    boolean deleteRecord(Long id);
}
