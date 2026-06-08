package com.final_work_spring_boot.mapper;

import com.final_work_spring_boot.dto.ClientDTO;
import com.final_work_spring_boot.model.Client;

public class ClientMapper {

    // todo: can I set each sales ?
    // todo: should I format lastName and FirstName, with the first letter in
    // UpperCase.
    public static ClientDTO toDTO(Client client) {
        if (client == null)
            return null;

        return ClientDTO.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .document(client.getDocument())
                .email(client.getEmail())
                .phone(client.getPhone())
                .createdAt(client.getCreatedAt())
                .updatedAt(client.getUpdatedAt())
                .isActive(client.getIsActive())
                .build();
    }

    public static Client toEntity(ClientDTO dto) {
        if (dto == null)
            return null;

        return Client.builder()
                .firstName(dto.getFirstName().trim())
                .lastName(dto.getLastName().trim())
                .document(dto.getDocument().trim())
                .email(dto.getEmail().trim())
                .phone(dto.getPhone().trim())
                .isActive(true)
                .build();
    }

    public static void updateEntity(Client client, ClientDTO dto){
        if (dto == null) 
            return; 
        
        if (dto.getFirstName() != null) 
            client.setFirstName(dto.getFirstName().trim());

        if (dto.getLastName() != null) 
            client.setLastName(dto.getLastName().trim());

        if (dto.getDocument() != null) 
            client.setDocument(dto.getDocument().trim());

        if (dto.getEmail() != null)
            client.setEmail(dto.getEmail().trim());

        if (dto.getPhone() != null) 
            client.setPhone(dto.getPhone().trim());
    }

}
