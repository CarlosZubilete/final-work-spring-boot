package com.final_work_spring_boot.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.final_work_spring_boot.dto.request.sale.SaleCreateDTO;
import com.final_work_spring_boot.dto.request.sale.SaleUpdateDTO;
import com.final_work_spring_boot.dto.response.SaleResponseDTO;
import com.final_work_spring_boot.exception.BusinessException;
import com.final_work_spring_boot.repository.*;
import com.final_work_spring_boot.service.ISaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.final_work_spring_boot.dto.DetailDTO;
import com.final_work_spring_boot.exception.NotFoundException;

import com.final_work_spring_boot.mapper.DetailMapper;
import com.final_work_spring_boot.mapper.SaleMapper;
import com.final_work_spring_boot.model.Client;
import com.final_work_spring_boot.model.Product;
import com.final_work_spring_boot.model.Sale;
import com.final_work_spring_boot.model.Detail;
import com.final_work_spring_boot.model.StateSale;


@Service
public class SaleService implements ISaleService {

    @Autowired
    private ISaleRepository repository;

    @Autowired
    private IClientRepository clientRepo;

    @Autowired
    private IStateSaleRepository stateSaleRepo;

    @Autowired
    private IProductRepository productRepo;

    @Autowired
    private IDetailRepository detailRepos;

    @Override
    @Transactional(readOnly = true)
    public List<SaleResponseDTO> getRecordsList() {
        return repository.findAll().stream()
            .map(SaleMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SaleResponseDTO getRecordById(Long id) {
        return repository.findById(id).map(SaleMapper::toDTO)
            .orElseThrow(() -> new NotFoundException("Sale with this id: " + id + " not found."));
    }

    @Override
    public SaleResponseDTO saveRecord(SaleCreateDTO dto) {

        // Find the Client and StateSale
        Client existingClient = clientRepo.findByIdAndStatus(dto.getIdClient(), true)
            .orElseThrow(() -> new NotFoundException("Client with this id: " + dto.getIdClient() + " not found."));

        // Find the StateSale
        StateSale existingSaleState = stateSaleRepo.findById(dto.getIdStateSale())
            .orElseThrow(
                () -> new NotFoundException("State Sale with id: " + dto.getIdStateSale() + " not found."));

        // Build the sale
        Sale sale = SaleMapper.toEntity(dto, existingClient, existingSaleState);

        List<Detail> details = new ArrayList<>();

        for (DetailDTO detailDTO : dto.getDetails()) {

            Product product = productRepo.findByIdAndStatus(detailDTO.getIdProduct(), true)
                .orElseThrow(() -> new NotFoundException(
                    "Product with id: " + detailDTO.getIdProduct() + " not found."));

            Detail detail = DetailMapper.toEntity(detailDTO, product, sale);

            details.add(detail);
        }

        // Add details in the sale
        sale.setDetails(details);

        Double total = details.stream()
            .mapToDouble(detail -> detail.getQuantity() * detail.getUnitPrice())
            .sum();

        sale.setTotal(total);

        return SaleMapper.toDTO(repository.save(sale));

    }

    @Override
    public SaleResponseDTO updateRecord(Long id, SaleUpdateDTO dto) {

        Sale existingSale = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Sale whit this id: " + id + " not found."));

        // Update Client
        Client existingClient = null;
        if (dto.getIdClient() != null) {
            existingClient = clientRepo.findByIdAndStatus(dto.getIdClient(), true)
                .orElseThrow(() -> new NotFoundException
                    ("Client whit this id: " + dto.getIdClient() + " not found."));
        }

        // Update StateSale
        StateSale existingStateSale = null;
        if (dto.getIdStateSale() != null) {
            existingStateSale = stateSaleRepo.findById(dto.getIdStateSale())
                .orElseThrow(
                    () -> new NotFoundException("State Sale whit this id: " + dto.getIdStateSale() + " not found."));
        }

        List<Detail> updateDetails = new ArrayList<>();
        if (dto.getDetails() != null) {
            // Make sure if the state sale allows update
            boolean isPending;
            if (existingStateSale != null) {
                isPending = existingStateSale.getName().equals("pending");
            } else {
                isPending = existingSale.getStateSale().getName().equals("pending");
            }

            if (!isPending)
                throw new BusinessException("Only sales with a 'pending' status can be updated.");

            Detail detail;
            for (DetailDTO detailDTO : dto.getDetails()) {
                // find the product
                Product product = productRepo.findByIdAndStatus(detailDTO.getIdProduct(), true)
                    .orElseThrow(() -> new NotFoundException
                        ("Product with this id: " + detailDTO.getIdProduct() + " not found."));

                if (detailDTO.getId() != null) {
                    // Update existing detail
                    detail = detailRepos.findById(detailDTO.getId())
                        .orElseThrow(() -> new NotFoundException
                            ("Detail with this id: " + detailDTO.getId() + " not found."));

                    DetailMapper.updateEntity(detail, detailDTO, product);
                } else {
                    // Create new detail
                    detail = DetailMapper.toEntity(detailDTO, product, existingSale);
                }

                updateDetails.add(detail);
            }

            // Clean the current list
            existingSale.getDetails().clear();
            // Add the processed details
            existingSale.getDetails().addAll(updateDetails);

            Double total = updateDetails.stream()
                .mapToDouble(det -> det.getQuantity() * det.getUnitPrice())
                .sum();

            existingSale.setTotal(total);
        }

        SaleMapper.updateEntity(existingSale, dto, existingClient, existingStateSale);

        return SaleMapper.toDTO(repository.save(existingSale));
    }

    @Override
    public boolean deleteRecord(Long id) {
        if (!repository.existsById(id))
            throw new NotFoundException("Sale whit ID: " + id + " not found.");

        repository.deleteById(id);
        return true;
    }
}
