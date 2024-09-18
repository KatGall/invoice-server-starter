package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.filter.InvoiceFilter;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.PersonRepository;
import cz.itnetwork.entity.repository.specification.InvoiceSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public InvoiceDTO addInvoice(InvoiceDTO invoiceDTO) {
        InvoiceEntity entity = invoiceMapper.toEntity(invoiceDTO);

        PersonEntity sellerObject = personRepository.getReferenceById(invoiceDTO.getSeller().getId());
        entity.setSeller(sellerObject);
        PersonEntity buyerObject = personRepository.getReferenceById(invoiceDTO.getBuyer().getId());
        entity.setBuyer(buyerObject);

        entity = invoiceRepository.save(entity);
        return invoiceMapper.toDTO(entity);
    }

    @Override
    public List<InvoiceDTO> getAll(InvoiceFilter invoiceFilter) {
        InvoiceSpecification invoiceSpecification = new InvoiceSpecification(invoiceFilter);

        Pageable pageable = PageRequest.of(0, invoiceFilter.getLimit());

        Page<InvoiceEntity> invoicePage = invoiceRepository.findAll(invoiceSpecification, pageable);

        return invoicePage.getContent().stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDTO> getInvoicesBySellerIc(String identificationNumber) {
        List<InvoiceEntity> invoices = invoiceRepository.findAll();
        return invoices.stream()
                .filter(i -> i.getSeller().getIdentificationNumber().equals(identificationNumber))
                .map(i -> invoiceMapper.toDTO(i))
                .toList();
    }

    @Override
    public List<InvoiceDTO> getInvoicesByBuyerIc(String identificationNumber) {
        List<InvoiceEntity> invoices = invoiceRepository.findAll();
        return invoices.stream()
                .filter(i -> i.getBuyer().getIdentificationNumber().equals(identificationNumber))
                .map(i -> invoiceMapper.toDTO(i))
                .toList();
    }
    @Override
    public InvoiceDTO getInvoiceById(Long invoiceId) {
        InvoiceEntity invoiceEntity = invoiceRepository.findById(invoiceId).orElse(null);
        return invoiceEntity != null ? invoiceMapper.toDTO(invoiceEntity) : null;
    }

    @Override
    public InvoiceStatisticsDTO getInvoiceStatistics() {
        Long currentYearSum = invoiceRepository.getCurrentYearSum();
        Long allTimeSum = invoiceRepository.getAllTimeSum();
        Long invoicesCount = invoiceRepository.getInvoicesCount();
        return new InvoiceStatisticsDTO(currentYearSum, allTimeSum, invoicesCount);
    }

    @Override
    public void removeInvoice(long invoiceId) {
        InvoiceEntity invoice = fetchInvoiceById(invoiceId);
        invoiceRepository.delete(invoice);
    }

    @Override
    public InvoiceDTO editInvoice(Long invoiceId, InvoiceDTO invoiceDTO) {

        InvoiceEntity entity = fetchInvoiceById(invoiceId);

        invoiceDTO.setId(invoiceId);

        invoiceMapper.updateEntity(invoiceDTO, entity);

        if (invoiceDTO.getBuyer() != null && invoiceDTO.getBuyer().getId() != null) {
            PersonEntity buyerObject = personRepository.getReferenceById(invoiceDTO.getBuyer().getId());
            entity.setBuyer(buyerObject);
        }

        if (invoiceDTO.getSeller() != null && invoiceDTO.getSeller().getId() != null) {
            PersonEntity sellerObject = personRepository.getReferenceById(invoiceDTO.getSeller().getId());
            entity.setSeller(sellerObject);
        }

        InvoiceEntity saved = invoiceRepository.save(entity);

        return invoiceMapper.toDTO(saved);
    }
    @Override
    public InvoiceEntity fetchInvoiceById(long invoiceId) {
        return invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new EntityNotFoundException("Invoice with id " + invoiceId + "wasn´t found in the databes."));
    }
}



