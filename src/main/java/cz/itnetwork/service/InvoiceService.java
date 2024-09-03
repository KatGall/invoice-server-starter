package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.filter.InvoiceFilter;

import java.util.List;

public interface InvoiceService {

    InvoiceDTO addInvoice(InvoiceDTO invoiceDTO);

    List<InvoiceDTO> getAll(InvoiceFilter invoiceFilter);

    List<InvoiceDTO> getInvoicesBySellerIc(String identificationNumber);

    List<InvoiceDTO> getInvoicesByBuyerIc(String identificationNumber);

    void removeInvoice(long id);

    InvoiceDTO editInvoice(Long invoiceId, InvoiceDTO invoiceDTO);

    InvoiceDTO getInvoiceById(Long invoiceId);

    InvoiceStatisticsDTO getInvoiceStatistics();

    InvoiceEntity fetchInvoiceById(long invoiceId);
}
