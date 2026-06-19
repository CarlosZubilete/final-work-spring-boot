package com.final_work_spring_boot.service.impl;

import java.util.List;

import com.final_work_spring_boot.dto.request.client.ClientCreateDTO;
import com.final_work_spring_boot.dto.request.client.ClientUpdateDTO;
import com.final_work_spring_boot.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.final_work_spring_boot.dto.response.ClientResponseDTO;
import com.final_work_spring_boot.exception.BusinessException;
import com.final_work_spring_boot.exception.NotFoundException;
import com.final_work_spring_boot.mapper.ClientMapper;
import com.final_work_spring_boot.model.Client;
import com.final_work_spring_boot.repository.IClientRepository;


@Service
public class ClientService implements IClientService {

    @Autowired
    private IClientRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponseDTO> getRecordsList() {
        return repository.findAllByStatus(true).stream()
            .map(ClientMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponseDTO getRecordById(Long id) {
        return repository.findByIdAndStatus(id, true).map(ClientMapper::toDTO)
            .orElseThrow(() -> new NotFoundException("Client with this id: " + id + " not found."));
    }


    @Override
    @Transactional
    public ClientResponseDTO saveRecord(ClientCreateDTO dto) {

        // Find document
        String existingDocument = dto.getDocument().trim();
        if (repository.existsByDocument(existingDocument))
            throw new BusinessException(
                "Client with this document: " + existingDocument + " is already exists.");

        // Find the email
        String existingEmail = dto.getEmail().trim();
        if (repository.existsByEmail(existingEmail))
            throw new BusinessException(
                "Client with this email: " + existingEmail + " is already exists.");


        Client client = ClientMapper.toEntity(dto);

        return ClientMapper.toDTO(repository.save(client));
    }

    @Override
    @Transactional
    public ClientResponseDTO updateRecord(Long id, ClientUpdateDTO dto) {

        Client existingClient = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Client with id: " + id + " not found."));

        // Valid the document
        if (dto.getDocument() != null) {
            String existingDocument = dto.getDocument().trim();
            if (repository.existsByDocument(existingDocument))
                throw new BusinessException(
                    "Client with this document: " + existingDocument + " is already exists.");
        }
        // Valid the email
        if (dto.getEmail() != null) {
            String existingEmail = dto.getEmail().trim();
            if (repository.existsByEmail(existingEmail))
                throw new BusinessException
                    ("Client with this email: " + existingEmail + " is already exists.");
        }

        ClientMapper.updateEntity(existingClient, dto);

        return ClientMapper.toDTO(repository.save(existingClient));
    }

    @Override
    @Transactional
    public boolean deleteRecord(Long id) {
        if (!repository.existsById(id))
            throw new NotFoundException("Client with this id: " + id + " not found.");

        repository.logicDeleteById(id);
        return true;
    }
}
