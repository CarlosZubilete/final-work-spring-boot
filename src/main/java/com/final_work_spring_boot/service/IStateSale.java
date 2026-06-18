package com.final_work_spring_boot.service;

import com.final_work_spring_boot.dto.request.statesale.StateSaleCreateDTO;
import com.final_work_spring_boot.dto.request.statesale.StateSaleUpdateDTO;
import com.final_work_spring_boot.dto.response.StateSaleResponseDTO;

import java.util.List;

public interface IStateSale {
    List<StateSaleResponseDTO> getRecordsList();

    StateSaleResponseDTO getRecordById(Long id);

    StateSaleResponseDTO saveRecord(StateSaleCreateDTO dto);

    StateSaleResponseDTO updateRecord(Long id, StateSaleUpdateDTO dto);

    boolean deleteRecord(Long id);
}
