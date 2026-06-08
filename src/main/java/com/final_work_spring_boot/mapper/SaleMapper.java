package com.final_work_spring_boot.mapper;

import java.util.List;

import com.final_work_spring_boot.dto.SaleDTO;
import com.final_work_spring_boot.dto.DetailDTO;
import com.final_work_spring_boot.model.Client;
import com.final_work_spring_boot.model.Sale;
import com.final_work_spring_boot.model.StateSale;

public class SaleMapper {

    public static SaleDTO toDTO(Sale sale) {
        if (sale == null)
            return null;

        // Build each SaleDetail ...
        List<DetailDTO> details = sale.getDetails().stream()
                .map(detail -> DetailDTO.builder()
                        .id(detail.getId())
                        .idProduct(detail.getProduct().getId())
                        .unitPrice(detail.getUnitPrice())
                        .quantity(detail.getQuantity())
                        .subTotal(detail.getUnitPrice() * detail.getQuantity())
                        .build())
                .toList();

        // Build total with each subtotal from details ...
        Double total = details.stream()
                .mapToDouble(DetailDTO::getSubTotal)
                .reduce(0.0, Double::sum);

        return SaleDTO.builder()
                .id(sale.getId())
                .date(sale.getDate())
                .idClient(sale.getClient().getId())
                .idStateSale(sale.getStateSale().getId())
                .details(details)
                .total(total)
                .build();
    }

    public static Sale toEntity(SaleDTO dto, Client client, StateSale stateSale) {
        if (dto == null)
            return null;
        // Note: the total and details set in the service.
        return Sale.builder()
                .date(dto.getDate())
                .client(client)
                .stateSale(stateSale)
                .build();
    }

    public static void updateEntity(Sale sale, SaleDTO dto, Client client, StateSale stateSale) {
        if (dto == null)
            return;

        if (dto.getDate() != null)
            sale.setDate(dto.getDate());

        if (dto.getIdClient() != null)
            sale.setClient(client);

        if (dto.getIdStateSale() != null)
            sale.setStateSale(stateSale);

        // todo: If I have update the details .

        if (dto.getTotal() != null)
            sale.setTotal(dto.getTotal());

    }

}
