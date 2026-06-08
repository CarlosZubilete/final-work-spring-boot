package com.final_work_spring_boot.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.final_work_spring_boot.dto.SaleDTO;
import com.final_work_spring_boot.dto.DetailDTO;
import com.final_work_spring_boot.exception.NotFoundException;
import com.final_work_spring_boot.exception.BadRequestException;
import com.final_work_spring_boot.mapper.DetailMapper;
import com.final_work_spring_boot.mapper.SaleMapper;
import com.final_work_spring_boot.model.Client;
import com.final_work_spring_boot.model.Product;
import com.final_work_spring_boot.model.Sale;
import com.final_work_spring_boot.model.Detail;
import com.final_work_spring_boot.model.StateSale;
import com.final_work_spring_boot.repository.IClientRepository;
import com.final_work_spring_boot.repository.IProductRepository;
import com.final_work_spring_boot.repository.ISaleRepository;
import com.final_work_spring_boot.repository.IStateSaleRepository;
import com.final_work_spring_boot.service.IGenericService;

@Service
public class SaleService implements IGenericService<SaleDTO> {

    @Autowired
    private ISaleRepository repository;

    @Autowired
    private IClientRepository clientRepo;

    @Autowired
    private IStateSaleRepository stateSaleRepo;

    @Autowired
    private IProductRepository productRepo;

    @Override
    @Transactional(readOnly = true)
    public List<SaleDTO> getAll() {
        return repository.findAll().stream()
                .map(SaleMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SaleDTO getById(Long id) {
        return repository.findById(id).map(SaleMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Sale whit ID: " + id + " NOT FOUND."));
    }

    @Override
    public SaleDTO save(SaleDTO dto) {
        // todo : SYNTACTIC VALIDATION : it will be replace with @valid
        if (dto == null)
            throw new BadRequestException("Sale cannot be null");
        if (dto.getIdClient() == null)
            throw new BadRequestException("Client id cannot be null");
        if (dto.getIdStateSale() == null)
            throw new BadRequestException("State Sale id cannot be null");
        if (dto.getDetails() == null)
            throw new BadRequestException("At least one product must be included in the sale");

        // Find the Client and StateSale
        Client existingClient = clientRepo.findById(dto.getIdClient())
                .orElseThrow(() -> new NotFoundException("Client whit id: " + dto.getIdClient() + " not found."));

        StateSale existingSaleState = stateSaleRepo.findById(dto.getIdStateSale())
                .orElseThrow(
                        () -> new NotFoundException("State Sale whit id: " + dto.getIdStateSale() + " not found."));

        // Build the sale
        Sale sale = SaleMapper.toEntity(dto, existingClient, existingSaleState);

        List<Detail> details = new ArrayList<>();

        for (DetailDTO detailDTO : dto.getDetails()) {

            Product product = productRepo.findById(detailDTO.getIdProduct())
                    .orElseThrow(() -> new NotFoundException(
                            "Product with id: " + detailDTO.getIdProduct() + " not found."));

            Detail detail = DetailMapper.toEntity(detailDTO, product, sale);

            details.add(detail);
        }

        // Add details in the sale
        sale.setDetails(details);

        Double total = details.stream()
                .mapToDouble(detail -> detail.getQuantity() * detail.getQuantity())
                .sum();

        sale.setTotal(total);

        return SaleMapper.toDTO(repository.save(sale));

    }

    @Override
    public SaleDTO update(SaleDTO dto, Long id) {

        Sale existingSale = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sale whit ID: " + id + " NOT FOUND."));

        Client existingClient = clientRepo.findById(dto.getIdClient())
                .orElseThrow(() -> new NotFoundException("Client whit ID: " + dto.getIdClient() + " NOT FOUND."));

        StateSale existingStateSale = stateSaleRepo.findById(dto.getIdStateSale())
                .orElseThrow(
                        () -> new NotFoundException("State Sale whit ID: " + dto.getIdStateSale() + " NOT FOUND."));

        // todo: If I have update the a specific details.

        SaleMapper.updateEntity(existingSale, dto, existingClient, existingStateSale);

        return SaleMapper.toDTO(repository.save(existingSale));
    }

    @Override
    public boolean delete(Long id) {
        if (!repository.existsById(id))
            throw new NotFoundException("Sale whit ID: " + id + " NOT FOUND.");

        repository.deleteById(id);
        return true;
    }

}
