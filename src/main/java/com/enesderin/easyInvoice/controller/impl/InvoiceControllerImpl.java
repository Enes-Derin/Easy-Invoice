package com.enesderin.easyInvoice.controller.impl;

import com.enesderin.easyInvoice.controller.InvoiceController;
import com.enesderin.easyInvoice.controller.RootEntity;
import com.enesderin.easyInvoice.dto.request.InvoiceRequest;
import com.enesderin.easyInvoice.dto.response.InvoiceResponse;
import com.enesderin.easyInvoice.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@AllArgsConstructor
public class InvoiceControllerImpl implements InvoiceController {

    private InvoiceService invoiceService;

    @GetMapping("/{companyId}/all")
    @Override
    public RootEntity<List<InvoiceResponse>> getAllInvoices(@PathVariable Long companyId) {
        return RootEntity.ok(this.invoiceService.getAllInvoices(companyId));
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<InvoiceResponse> getInvoiceById(@PathVariable Long id) {
        return RootEntity.ok(this.invoiceService.getInvoiceById(id));
    }

    @PostMapping
    @Override
    public RootEntity<InvoiceResponse> createInvoice(@Valid @RequestBody InvoiceRequest invoiceRequest) {
        return RootEntity.ok(this.invoiceService.createInvoice(invoiceRequest));
    }

    @PutMapping("/update/{id}")
    @Override
    public RootEntity<InvoiceResponse> updateInvoice(@PathVariable Long id,@Valid @RequestBody InvoiceRequest invoiceRequest) {
        return RootEntity.ok(this.invoiceService.updateInvoice(id, invoiceRequest));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public RootEntity<String> deleteInvoice(@PathVariable Long id) {
        return RootEntity.ok(this.invoiceService.deleteInvoice(id));
    }
}
