package cz.itnetwork.controller;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.entity.filter.InvoiceFilter;
import cz.itnetwork.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/invoices")
    public InvoiceDTO addInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.addInvoice(invoiceDTO);
    }

    @GetMapping("/invoices")
    public List<InvoiceDTO> getAll(InvoiceFilter invoiceFilter) {
        return invoiceService.getAll(invoiceFilter);
    }

    @GetMapping("/identification/{identificationNumber}/sales")
    public List<InvoiceDTO> getInvoicesBySellerIc(@PathVariable String identificationNumber) {
        return invoiceService.getInvoicesBySellerIc(identificationNumber);

    }

    @GetMapping("/identification/{identificationNumber}/purchases")
    public List<InvoiceDTO> getInvoicesByBuyerIc(@PathVariable String identificationNumber) {
        return invoiceService.getInvoicesByBuyerIc(identificationNumber);

    }

    @GetMapping("/invoices/{invoiceId}")
    public ResponseEntity<InvoiceDTO> getInvoiceById(@PathVariable Long invoiceId) {
        InvoiceDTO invoice = invoiceService.getInvoiceById(invoiceId);
        return invoice != null ? ResponseEntity.ok(invoice) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/invoices/{invoiceId}")
    public void deleteInvoice(@PathVariable Long invoiceId) {
        invoiceService.removeInvoice(invoiceId);
    }

    @PutMapping("/invoices/{invoiceId}")
    public InvoiceDTO editInvoice(@PathVariable Long invoiceId, @RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.editInvoice(invoiceId, invoiceDTO);
    }

    @GetMapping("/invoices/statistics")
    public ResponseEntity<InvoiceStatisticsDTO> getInvoiceStatistics() {
        InvoiceStatisticsDTO statistics = invoiceService.getInvoiceStatistics();
        return ResponseEntity.ok(statistics);
    }

}




