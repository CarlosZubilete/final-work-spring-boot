package com.final_work_spring_boot.service;

import com.final_work_spring_boot.dto.request.client.ClientCreateDTO;
import com.final_work_spring_boot.dto.request.client.ClientUpdateDTO;
import com.final_work_spring_boot.dto.response.ClientResponseDTO;

import java.util.List;

public interface IClientService {
    List<ClientResponseDTO> getRecordsList();

    ClientResponseDTO getRecordById(Long id);

    ClientResponseDTO saveRecord(ClientCreateDTO dto);

    ClientResponseDTO updateRecord(Long id, ClientUpdateDTO dto);

    boolean deleteRecord(Long id);
}
