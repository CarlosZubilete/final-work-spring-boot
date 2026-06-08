package com.final_work_spring_boot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.final_work_spring_boot.dto.ClientDTO;
import com.final_work_spring_boot.exception.BusinessException;
import com.final_work_spring_boot.exception.NotFoundException;
import com.final_work_spring_boot.mapper.ClientMapper;
import com.final_work_spring_boot.model.Client;
import com.final_work_spring_boot.repository.IClientRepository;
import com.final_work_spring_boot.service.IGenericService;

@Service
public class ClientService implements IGenericService<ClientDTO> {

    @Autowired
    private IClientRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<ClientDTO> getAll() {
        return repository.findAll().stream()
                .map(ClientMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClientDTO getById(Long id) {
        return repository.findById(id).map(ClientMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Client with id: " + id + " not found."));
    }

    @Override
    public ClientDTO save(ClientDTO dto) {
        // find email and document
        Client isRepeatedClient = null;
        isRepeatedClient = repository.findByDocument(dto.getDocument().trim())
                .orElse(null);

        if (isRepeatedClient != null)
            throw new BusinessException(
                    "Client with this document: " + dto.getDocument().trim() + " is already exists.");

        isRepeatedClient = repository.findByEmail(dto.getEmail().trim())
                .orElse(null);

        if (isRepeatedClient != null)
            throw new BusinessException("Client with this email: " + dto.getEmail().trim() + " is already exists.");

        Client client = ClientMapper.toEntity(dto);

        return ClientMapper.toDTO(repository.save(client));
    }

    @Override
    public ClientDTO update(ClientDTO dto, Long id) {
        Client existingClient = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client with id: " + id + " not found."));

        Client isRepeatedClient = null;

        if (dto.getDocument() != null) {
            isRepeatedClient = repository.findByDocument(dto.getDocument().trim())
                    .orElse(null);

            if (isRepeatedClient != null)
                throw new BusinessException(
                        "Client with this document: " + dto.getDocument().trim() + " is already exists.");
        }

        if (dto.getEmail() != null) {
            isRepeatedClient = repository.findByEmail(dto.getEmail().trim())
                    .orElse(null);

            if (isRepeatedClient != null)
                throw new BusinessException("Client with this email: " + dto.getEmail().trim() + " is already exists.");
        }

        ClientMapper.updateEntity(existingClient, dto);

        return ClientMapper.toDTO(repository.save(existingClient));
    }

    @Override
    public boolean delete(Long id) {
        if (!repository.existsById(id))
            throw new NotFoundException("Client with id: " + id + " not found.");

        repository.logicDeleteById(id);
        return true;
    }
}
