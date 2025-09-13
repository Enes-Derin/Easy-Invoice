package com.enesderin.easyInvoice.controller;

import com.enesderin.easyInvoice.dto.request.InvoiceRequest;
import com.enesderin.easyInvoice.dto.response.InvoiceResponse;

import java.util.List;

public interface InvoiceController {
    RootEntity<List<InvoiceResponse>> getAllInvoices(Long companyId);
    RootEntity<InvoiceResponse> getInvoiceById(Long id);
    RootEntity<InvoiceResponse> createInvoice(InvoiceRequest invoiceRequest);
    RootEntity<InvoiceResponse> updateInvoice(Long id, InvoiceRequest invoiceRequest);
    RootEntity<String > deleteInvoice(Long id);
}
